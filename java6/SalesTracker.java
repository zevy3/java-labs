import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;

public class SalesTracker {
    private ConcurrentHashMap<String, AtomicInteger> salesCount;
    private ConcurrentHashMap<String, Double> productPrices;
    private List<Product> soldProducts;

    public SalesTracker() {
        salesCount = new ConcurrentHashMap<>();
        productPrices = new ConcurrentHashMap<>();
        soldProducts = Collections.synchronizedList(new ArrayList<>());
    }

    public void addSale(Product product) {
        soldProducts.add(product);
        salesCount.putIfAbsent(product.getName(), new AtomicInteger(0));
        salesCount.get(product.getName()).incrementAndGet();
        productPrices.put(product.getName(), product.getPrice());
        System.out.println("Продан товар: " + product);
    }

    public void printSoldProducts() {
        System.out.println("\n=== Список проданных товаров ===");
        for (Product product : soldProducts) {
            System.out.println(product);
        }
    }

    public double getTotalSales() {
        double total = 0;
        for (Product product : soldProducts) {
            total += product.getPrice();
        }
        return total;
    }

    public String getMostPopularProduct() {
        String mostPopular = null;
        int maxCount = 0;
        
        for (Map.Entry<String, AtomicInteger> entry : salesCount.entrySet()) {
            int count = entry.getValue().get();
            if (count > maxCount) {
                maxCount = count;
                mostPopular = entry.getKey();
            }
        }
        
        return mostPopular != null ? mostPopular + " (продано: " + maxCount + " шт.)" : "Нет данных";
    }

    public void printStatistics() {
        System.out.println("\n=== Статистика продаж ===");
        System.out.println("Общая сумма продаж: " + getTotalSales() + " руб.");
        System.out.println("Самый популярный товар: " + getMostPopularProduct());
        System.out.println("\nКоличество продаж по товарам:");
        for (Map.Entry<String, AtomicInteger> entry : salesCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().get() + " шт.");
        }
    }

    public static void main(String[] args) {
        SalesTracker tracker = new SalesTracker();

        tracker.addSale(new Product("Хлеб", 50.0));
        tracker.addSale(new Product("Молоко", 80.0));
        tracker.addSale(new Product("Хлеб", 50.0));
        tracker.addSale(new Product("Сыр", 300.0));
        tracker.addSale(new Product("Молоко", 80.0));
        tracker.addSale(new Product("Хлеб", 50.0));
        tracker.addSale(new Product("Масло", 150.0));

        tracker.printSoldProducts();
        tracker.printStatistics();
    }
}
