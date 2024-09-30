package performanceOptimization;

import java.util.ArrayList;
import java.util.List;

class Order {
    private String id;
    private List<Product> products;
    private String status;

    public Order(String id) {
        this.id = id;
        this.products = new ArrayList<>();
        this.status = "NEW";
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public double calculateTotal() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
            // Bottleneck: Unnecessary delay in calculation
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    // Getters and setters
    public String getId() { return id; }
    public List<Product> getProducts() { return products; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}