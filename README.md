# Low Level Design

Small, self-contained Java modules demonstrating common LLD patterns (strategy, factory, decorator, state, and simple domain models). Each folder is its own Maven project under `org.example`.

## Modules

| Module | Focus |
|--------|--------|
| [Splitwise](Splitwise/) | Expense splitting with strategy (`Equal` / `Exact`) |
| [VendingMachine](VendingMachine/) | Vending flow with **State** pattern |
| [MeetingScheduler](MeetingScheduler/) | Rooms, calendars, booking |
| [NotificationSystem](NotificationSystem/) | Factory + **Decorator** (retry) for channels |
| [RideBooking](RideBooking/) | Nearest-driver + pricing **strategies** |
| [InMemoryKeyValue](InMemoryKeyValue/) | TTL store + background **Cleaner** thread |
| [FlipkarMinute](FlipkarMinute/) | Delivery / order lifecycle (demo types in `Main.java`) |

---

## Splitwise

```mermaid
classDiagram
    direction TB
    class SplitService {
        +users
        +strategies
        +balance
        +expenses
        +addExpense(paidBy, amount, type, splits) Expense
        +getBalanceSheet()
    }
    class User {
        +name
        +id
    }
    class Expense {
        +amount
        +paidBy
        +splits
        +type
    }
    class Split {
        +user
        +amount
    }
    class SplitType {
        <<enumeration>>
        Equal
        Exact
    }
    class SplitStrategy {
        <<interface>>
        calculateSplit(splits, amount)
    }
    class EqualSplit
    class ExactSplit

    SplitService o-- SplitType : uses
    SplitService o-- SplitStrategy : strategies
    SplitStrategy <|.. EqualSplit
    SplitStrategy <|.. ExactSplit
    SplitService ..> Expense : creates
    Expense --> User
    Expense --> Split
    Expense --> SplitType
    Split --> User
```

---

## VendingMachine

```mermaid
classDiagram
    direction TB
    class VendingMachine {
        +inventory
        +state
        +balance
        +selectedProduct
        +insertCoin(coin)
        +selectProduct(product)
        +dispense()
        +refund()
        +setState(state)
    }
    class State {
        <<interface>>
        insertCoin(machine, coin)
        selectProduct(machine, product)
        dispenseProduct(machine)
        refund(machine)
    }
    class IdleState
    class HasMoneyState
    class DispenseState
    class RefundState
    class Inventory {
        +stock
        addProduct(product)
        dispense(product)
    }
    class Product {
        +id
        +name
        +price
    }
    class Coin {
        <<enumeration>>
        ONE TWO FIVE TEN
        +getValue()
    }

    VendingMachine *-- Inventory
    VendingMachine o-- State
    VendingMachine ..> Product
    State <|.. IdleState
    State <|.. HasMoneyState
    State <|.. DispenseState
    State <|.. RefundState
    Inventory ..> Product
    VendingMachine ..> Coin
```

**State transitions (State pattern)**

```mermaid
stateDiagram-v2
    [*] --> Idle
    Idle --> HasMoney : insertCoin
    HasMoney --> HasMoney : insertCoin
    HasMoney --> Dispense : selectProduct (valid)
    Dispense --> Refund : dispenseProduct
    Refund --> Idle : refund
```

---

## MeetingScheduler

```mermaid
classDiagram
    direction TB
    class MeetingScheduler {
        +addMeetingRoom(id, capacity)
        +bookRoom(users, start, end, capacity)
    }
    class MeetingRoomManager {
        +addRoom(id, capacity)
        +getAvailableRoom(capacity, start, end) MeetingRoom
        +book(room, users, start, end)
    }
    class MeetingRoom {
        +id
        +capacity
        +users
    }
    class MeetingRoomCalendar {
        +startDate
        +endDate
    }
    class User {
        +id
        +name
    }

    MeetingScheduler *-- MeetingRoomManager
    MeetingRoomManager "1" o-- "*" MeetingRoom
    MeetingRoomManager ..> MeetingRoomCalendar : maps room to
    MeetingRoom ..> User
    MeetingScheduler ..> User
```

---

## NotificationSystem

```mermaid
classDiagram
    direction TB
    class NotificationService {
        +notify(user, message)
    }
    class NotificationFactory {
        getChannel(type) NotificationChannel
    }
    class NotificationChannel {
        <<interface>>
        send(to, message)
    }
    class EmailNotification
    class PushNotification
    class SmsNotification
    class RetryNotification {
        +channel
        +maxRetries
        +send(to, message)
    }
    class User {
        +name email userName mobileNumber
        +type
    }
    class NotificationType {
        <<enumeration>>
        SMS EMAIL PUSH
    }

    NotificationService ..> NotificationFactory
    NotificationService ..> RetryNotification
    NotificationFactory ..> NotificationChannel : creates
    NotificationChannel <|.. EmailNotification
    NotificationChannel <|.. PushNotification
    NotificationChannel <|.. SmsNotification
    NotificationChannel <|.. RetryNotification
    RetryNotification o-- NotificationChannel : wraps
    User --> NotificationType
    NotificationService ..> User
```

---

## RideBooking

```mermaid
classDiagram
    direction TB
    class RideBookingService {
        +addRider(id)
        +addDriver(id, x, y)
        +book(riderId, source, dest) Ride
        +endRide(rideId)
        +getRideHistory(riderId)
    }
    class Ride {
        +id
        +rider driver
        +source destination
        +price
        +rideStatus
    }
    class Rider {
        +id
        +ridehistory
    }
    class Driver {
        +id
        +location
        +is_available
    }
    class Location {
        +x y
        +distance(other)
    }
    class RideStatus {
        <<enumeration>>
        CREATED_AT ONGOING COMPLETED
    }
    class FindDriverStrategy {
        <<interface>>
        findDriver(source, drivers, maxDist)
    }
    class NearestDriverStrategy
    class PricingStrategy {
        <<interface>>
        calculatePrice(source, dest)
    }
    class PerKiloMeter

    RideBookingService o-- FindDriverStrategy
    RideBookingService o-- PricingStrategy
    FindDriverStrategy <|.. NearestDriverStrategy
    PricingStrategy <|.. PerKiloMeter
    RideBookingService ..> Ride
    Ride --> Rider
    Ride --> Driver
    Ride --> Location
    Ride --> RideStatus
    Driver --> Location
    Rider o-- Ride
```

---

## InMemoryKeyValue

```mermaid
classDiagram
    direction TB
    class KeyValueStore {
        <<interface>>
        put(key, value)
        get(key) Entry
    }
    class InMemoryKeyValueStore {
        +map
        +put(key, value)
        +get(key) Entry
    }
    class Entry {
        +value
        +expiryTime
    }
    class Cleaner {
        +keyStore
        +run()
    }

    KeyValueStore <|.. InMemoryKeyValueStore
    InMemoryKeyValueStore *-- Entry : stores
    InMemoryKeyValueStore ..> Cleaner : starts
    Cleaner ..> Entry : expires
```

---

## FlipkarMinute

Core types for the quick-commerce demo live in [`FlipkarMinute/src/main/java/org/example/Main.java`](FlipkarMinute/src/main/java/org/example/Main.java) (single file).

```mermaid
classDiagram
    direction TB
    class User {
        +id name
    }
    class DeliveryPartner {
        +status
        +deliveryCount ratings
        +currentOrderId
        +getAverageRating()
    }
    class Order {
        +orderId customerId itemName
        +status assignedPartnerId
        +createdAt lock
    }
    class FlipkartMinutesService {
        +onboardCustomer(id, name)
        +onboardPartner(id, name)
        +createOrder(customerId, itemName)
        +cancelOrder(orderId)
        +pickupOrder(partnerId, orderId)
        +completeOrder(partnerId, orderId, rating)
        +showOrderStatus(orderId)
        +showDashboard()
    }
    class OrderStatus {
        <<enumeration>>
        CREATED ASSIGNED PICKED_UP DELIVERED CANCELLED
    }
    class PartnerStatus {
        <<enumeration>>
        AVAILABLE BUSY
    }

    User <|-- DeliveryPartner
    FlipkartMinutesService o-- User
    FlipkartMinutesService o-- DeliveryPartner
    FlipkartMinutesService o-- Order
    Order --> OrderStatus
    DeliveryPartner --> PartnerStatus
```

---

## Rendering

GitHub (and many Markdown viewers) render **Mermaid** natively. In VS Code / Cursor, use a Mermaid preview extension if diagrams do not show inline.
