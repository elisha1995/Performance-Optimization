package performanceOptimization;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

class OrderProcessor {
    private List<Order> orders;
    private ExecutorService executorService;
    private static final String ORDER_FILE = "orders.txt";

    public OrderProcessor() {
        // Solution: Use parallel processing with a thread pool
        this.orders = new ArrayList<>();
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        createOrderFile();
    }

    private void createOrderFile() {
        try {
            Files.createFile(Paths.get(ORDER_FILE));
            System.out.println("Created orders.txt file");
        } catch (FileAlreadyExistsException e) {
            // File already exists, no need to create
            System.out.println("orders.txt file already exists");
        } catch (IOException e) {
            System.err.println("Error creating order file: " + e.getMessage());
        }
    }

    public void processOrders() {
        // Solution: Use parallel processing with CompletableFuture
        List<CompletableFuture<Void>> futures = orders.stream()
                .filter(order -> order.getStatus().equals("NEW"))
                .map(this::processOrderAsync)
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private CompletableFuture<Void> processOrderAsync(Order order) {
        return CompletableFuture.runAsync(() -> {
            double total = order.calculateTotal();
            if (saveOrderToFile(order, total)) {
                order.setStatus("PROCESSED");
            }
        }, executorService);
    }

    private boolean saveOrderToFile(Order order, double total) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ORDER_FILE), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write("performanceOptimization.Order ID: " + order.getId() + ", Total: $" + total);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error writing order to file: " + e.getMessage());
            return false;
        }
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}