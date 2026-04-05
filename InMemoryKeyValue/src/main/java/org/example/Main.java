package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        InMemoryKeyValueStore store = new InMemoryKeyValueStore();

        store.put("Hello", "World");
        System.out.println(store.get("Hello").value);

        try {
            Thread.sleep(100000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(store.get("Hello").value); // should be null

    }
}

