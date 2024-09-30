package performanceOptimization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*class performanceOptimization.Inventory {
    private List<performanceOptimization.Product> products;

    public performanceOptimization.Inventory() {
        this.products = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            products.add(new performanceOptimization.Product("P" + i, "performanceOptimization.Product " + i, Math.random() * 100, "Description " + i));
        }
    }

    public List<performanceOptimization.Product> searchProducts(String keyword) {
        // Bottleneck: Inefficient search method
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        p.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public performanceOptimization.Product getProductById(String id) {
        for (performanceOptimization.Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }
}*/

class Inventory {
    private Map<String, Product> productsMap;
    private Map<String, List<Product>> searchIndex;

    public Inventory() {
        this.productsMap = new ConcurrentHashMap<>();
        this.searchIndex = new ConcurrentHashMap<>();
        // Solution 2: Implement lazy loading
        loadProductsBatch(0, 1000);
    }

    private void loadProductsBatch(int start, int batchSize) {
        for (int i = start; i < start + batchSize; i++) {
            Product product = new Product("P" + i, "performanceOptimization.Product " + i, Math.random() * 100, "Description " + i);
            productsMap.put(product.getId(), product);
            updateSearchIndex(product);
        }
    }

    private void updateSearchIndex(Product product) {
        String[] keywords = (product.getName() + " " + product.getDescription()).toLowerCase().split("\\s+");
        for (String keyword : keywords) {
            searchIndex.computeIfAbsent(keyword, k -> new ArrayList<>()).add(product);
        }
    }

    public List<Product> searchProducts(String keyword) {
        // Solution 3: Use indexing for faster searches
        return searchIndex.getOrDefault(keyword.toLowerCase(), Collections.emptyList());
    }

    public Product getProductById(String id) {
        // Solution 4: Use HashMap for O(1) lookup
        return productsMap.get(id);
    }
}


