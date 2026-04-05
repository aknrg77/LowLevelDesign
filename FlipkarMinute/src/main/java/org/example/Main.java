package org.example;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

// --- Enums & Models ---

enum OrderStatus {
    CREATED, ASSIGNED, PICKED_UP, DELIVERED, CANCELLED
}

enum PartnerStatus {
    AVAILABLE, BUSY
}

class User {
    String id, name;
    User(String id, String name) { this.id = id; this.name = name; }
}

class DeliveryPartner extends User {
    PartnerStatus status = PartnerStatus.AVAILABLE;
    int deliveryCount = 0;
    List<Integer> ratings = new ArrayList<>();
    String currentOrderId = null;

    DeliveryPartner(String id, String name) { super(id, name); }

    double getAverageRating() {
        return ratings.stream().mapToInt(i -> i).average().orElse(0.0);
    }
}

class Order {
    String orderId, customerId, itemName;
    OrderStatus status;
    String assignedPartnerId;
    long createdAt;
    final ReentrantLock lock = new ReentrantLock();

    Order(String orderId, String customerId, String itemName) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.itemName = itemName;
        this.status = OrderStatus.CREATED;
        this.createdAt = System.currentTimeMillis();
    }
}

// --- Service Layer ---

class FlipkartMinutesService {
    private final Map<String, User> customers = new ConcurrentHashMap<>();
    private final Map<String, DeliveryPartner> partners = new ConcurrentHashMap<>();
    private final Map<String, Order> orders = new ConcurrentHashMap<>();
    private final BlockingQueue<String> pendingOrders = new LinkedBlockingQueue<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final AtomicInteger orderCounter = new AtomicInteger(100);

    // --- Onboarding ---
    public void onboardCustomer(String id, String name) {
        customers.put(id, new User(id, name));
        System.out.println("[System] Customer onboarded: " + name);
    }

    public void onboardPartner(String id, String name) {
        partners.put(id, new DeliveryPartner(id, name));
        System.out.println("[System] Partner onboarded: " + name);
        tryAssignOrders();
    }

    // --- Order Lifecycle ---
    public String createOrder(String customerId, String itemName) {
        String orderId = "ORD" + orderCounter.incrementAndGet();
        Order order = new Order(orderId, customerId, itemName);
        orders.put(orderId, order);
        pendingOrders.add(orderId);

        System.out.println("[Order] " + orderId + " created for " + itemName);

        // Bonus: Auto-cancel after 30 mins (simulated here as 30 seconds for demo)
        scheduler.schedule(() -> autoCancelOrder(orderId), 30, TimeUnit.MINUTES);

        tryAssignOrders();
        return orderId;
    }

    public void cancelOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) return;

        order.lock.lock();
        try {
            if (order.status == OrderStatus.PICKED_UP || order.status == OrderStatus.DELIVERED) {
                System.out.println("[Error] Cannot cancel. Order " + orderId + " is already picked up.");
                return;
            }

            OrderStatus oldStatus = order.status;
            order.status = OrderStatus.CANCELLED;

            if (oldStatus == OrderStatus.ASSIGNED) {
                DeliveryPartner p = partners.get(order.assignedPartnerId);
                if (p != null) {
                    p.status = PartnerStatus.AVAILABLE;
                    p.currentOrderId = null;
                }
            }
            pendingOrders.remove(orderId);
            System.out.println("[Order] " + orderId + " cancelled successfully.");
        } finally {
            order.lock.unlock();
            tryAssignOrders();
        }
    }

    private void autoCancelOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order != null && order.status != OrderStatus.PICKED_UP && order.status != OrderStatus.DELIVERED) {
            System.out.println("[System] Auto-cancelling " + orderId + " (Timeout)");
            cancelOrder(orderId);
        }
    }

    // --- Fulfillment ---
    private synchronized void tryAssignOrders() {
        for (DeliveryPartner partner : partners.values()) {
            if (partner.status == PartnerStatus.AVAILABLE && !pendingOrders.isEmpty()) {
                String orderId = pendingOrders.poll();
                if (orderId != null) {
                    Order order = orders.get(orderId);
                    order.lock.lock();
                    try {
                        if (order.status == OrderStatus.CREATED) {
                            order.status = OrderStatus.ASSIGNED;
                            order.assignedPartnerId = partner.id;
                            partner.status = PartnerStatus.BUSY;
                            partner.currentOrderId = orderId;
                            System.out.println("[Assign] " + orderId + " assigned to " + partner.name);
                        }
                    } finally {
                        order.lock.unlock();
                    }
                }
            }
        }
    }

    public void pickupOrder(String partnerId, String orderId) {
        Order order = orders.get(orderId);
        DeliveryPartner partner = partners.get(partnerId);

        if (order == null || partner == null) return;

        order.lock.lock();
        try {
            if (order.status == OrderStatus.ASSIGNED && order.assignedPartnerId.equals(partnerId)) {
                order.status = OrderStatus.PICKED_UP;
                System.out.println("[Pickup] Partner " + partnerId + " picked up " + orderId);
            } else {
                System.out.println("[Error] Invalid pickup attempt for " + orderId);
            }
        } finally {
            order.lock.unlock();
        }
    }

    public void completeOrder(String partnerId, String orderId, int rating) {
        Order order = orders.get(orderId);
        DeliveryPartner partner = partners.get(partnerId);

        if (order == null || partner == null) return;

        order.lock.lock();
        try {
            if (order.status == OrderStatus.PICKED_UP) {
                order.status = OrderStatus.DELIVERED;
                partner.status = PartnerStatus.AVAILABLE;
                partner.currentOrderId = null;
                partner.deliveryCount++;
                partner.ratings.add(rating);
                System.out.println("[Done] " + orderId + " delivered! Rating: " + rating);
            }
        } finally {
            order.lock.unlock();
            tryAssignOrders();
        }
    }

    // --- Status & Dashboard ---
    public void showOrderStatus(String orderId) {
        Order o = orders.get(orderId);
        System.out.println("[Status] Order: " + orderId + " | Status: " + (o != null ? o.status : "Not Found"));
    }

    public void showDashboard() {
        System.out.println("\n--- TOP PARTNERS ---");
        partners.values().stream()
                .sorted((p1, p2) -> Double.compare(p2.getAverageRating(), p1.getAverageRating()))
                .limit(5)
                .forEach(p -> System.out.println(p.name + " | Deliveries: " + p.deliveryCount + " | Rating: " + String.format("%.1f", p.getAverageRating())));
        System.out.println("--------------------\n");
    }
}

// --- Driver Program ---
public class FlipkartMinutesApp {
    public static void main(String[] args) throws InterruptedException {
        FlipkartMinutesService system = new FlipkartMinutesService();

        // 1. Onboarding
        system.onboardCustomer("C1", "Alice");
        system.onboardPartner("P1", "Bob");
        system.onboardPartner("P2", "Charlie");

        // 2. Scenario: Concurrent Ordering
        String o1 = system.createOrder("C1", "iPhone 15");
        String o2 = system.createOrder("C1", "Maggi");
        String o3 = system.createOrder("C1", "Coke"); // This should queue

        system.showOrderStatus(o1);
        system.showOrderStatus(o3);

        // 3. Pickup and Delivery
        system.pickupOrder("P1", o1);
        system.cancelOrder(o1); // Should fail (already picked up)

        system.completeOrder("P1", o1, 5); // P1 becomes free, should pick up o3

        system.pickupOrder("P2", o2);
        system.completeOrder("P2", o2, 4);

        // 4. Show Dashboard
        system.showDashboard();

        // Cleanup
        System.exit(0);
    }
}
