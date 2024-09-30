package memoryManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemoryProfileDemo {
    private static final int MB = 1024 * 1024;
    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        List<byte[]> list = new ArrayList<>();
        System.out.println("Starting memory allocation...");

        for (int i = 0; i < 1000; i++) {
            // Allocate a random sized array between 1MB and 10MB
            int size = (1 + random.nextInt(10)) * MB;
            byte[] b = new byte[size];
            list.add(b);
            System.out.println("Iteration " + (i + 1) + ": Allocated " + (size / MB) + "MB");

            if (i % 10 == 0) {
                // Clear a random number of elements (between 25% and 75% of the list)
                int clearCount = list.size() * (25 + random.nextInt(51)) / 100;
                for (int j = 0; j < clearCount; j++) {
                    if (!list.isEmpty()) {
                        list.remove(0);
                    }
                }
                System.out.println("Cleared " + clearCount + " elements from the list");
            }

            // Simulate some work
            Thread.sleep(100);

            // Occasionally force GC
            if (i % 50 == 0) {
                System.out.println("Requesting garbage collection");
                System.gc();
            }
        }

        System.out.println("Memory allocation completed");
    }
}