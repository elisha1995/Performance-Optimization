package performanceOptimization;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Print current working directory
        System.out.println("Current working directory: " + System.getProperty("user.dir"));

        // Attempt to create orders.txt in the current directory
        try {
            Path orderFile = Paths.get("orders.txt");
            Files.createFile(orderFile);
            System.out.println("Created orders.txt at: " + orderFile.toAbsolutePath());
        } catch (FileAlreadyExistsException e) {
            System.out.println("orders.txt already exists");
        } catch (IOException e) {
            System.err.println("Error creating orders.txt: " + e.getMessage());
        }

        Inventory inventory = new Inventory();
        OrderProcessor orderProcessor = new OrderProcessor();

        // Simulate creating and processing orders
        for (int i = 0; i < 100; i++) {
            Order order = new Order("O" + i);
            List<Product> searchResults = inventory.searchProducts("product");
            for (int j = 0; j < 5; j++) {
                order.addProduct(searchResults.get(j));
            }
            orderProcessor.addOrder(order);
        }

        long startTime = System.currentTimeMillis();
        orderProcessor.processOrders();
        long endTime = System.currentTimeMillis();

        System.out.println("Time taken to process orders: " + (endTime - startTime) + "ms");

        orderProcessor.shutdown();
    }
}