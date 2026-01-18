import java.util.concurrent.*;
import java.util.Random;

class Item {
    private String name;
    private int weight;

    public Item(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }
}

class Loader implements Runnable {
    private int id;
    private BlockingQueue<Item> warehouse;
    private CyclicBarrier barrier;
    private static int totalWeight = 0;
    private static final Object lock = new Object();
    private static final int MAX_WEIGHT = 150;

    public Loader(int id, BlockingQueue<Item> warehouse, CyclicBarrier barrier) {
        this.id = id;
        this.warehouse = warehouse;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            while (!warehouse.isEmpty() || totalWeight > 0) {
                Item item = warehouse.poll(100, TimeUnit.MILLISECONDS);
                
                if (item != null) {
                    synchronized (lock) {
                        if (totalWeight + item.getWeight() <= MAX_WEIGHT) {
                            totalWeight += item.getWeight();
                            System.out.println("Грузчик " + id + " взял " + item.getName() + 
                                             " (" + item.getWeight() + " кг). Общий вес: " + totalWeight + " кг");
                        } else {
                            warehouse.offer(item);
                        }

                        if (totalWeight >= MAX_WEIGHT) {
                            System.out.println("\n>>> Грузчик " + id + " готов к отправке! Общий вес: " + totalWeight + " кг");
                            lock.notifyAll();
                        }
                    }

                    synchronized (lock) {
                        while (totalWeight < MAX_WEIGHT && !warehouse.isEmpty()) {
                            lock.wait(100);
                        }
                        
                        if (totalWeight >= MAX_WEIGHT) {
                            try {
                                System.out.println("Грузчик " + id + " ждёт у барьера...");
                                barrier.await();
                                
                                if (id == 1) {
                                    System.out.println("\n=== ВСЕ ГРУЗЧИКИ ОТПРАВИЛИСЬ НА ДРУГОЙ СКЛАД ===");
                                    System.out.println("=== РАЗГРУЗКА " + totalWeight + " кг ===\n");
                                    totalWeight = 0;
                                }
                                Thread.sleep(500);
                                
                            } catch (BrokenBarrierException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                
                Thread.sleep(50);
            }
            
            System.out.println("Грузчик " + id + " завершил работу.");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Task3Warehouse {
    public static void main(String[] args) {
        BlockingQueue<Item> warehouse = new LinkedBlockingQueue<>();
        Random random = new Random();

        String[] itemNames = {"Ящик", "Коробка", "Мешок", "Бочка", "Контейнер", "Упаковка"};
        for (int i = 1; i <= 20; i++) {
            int weight = 10 + random.nextInt(41);
            warehouse.offer(new Item(itemNames[random.nextInt(itemNames.length)] + " №" + i, weight));
        }

        System.out.println("=== СКЛАД ЗАГРУЖЕН ===");
        System.out.println("Всего товаров: " + warehouse.size());
        System.out.println("Грузчики начинают работу...\n");

        CyclicBarrier barrier = new CyclicBarrier(3);

        Thread[] loaders = new Thread[3];
        for (int i = 0; i < 3; i++) {
            loaders[i] = new Thread(new Loader(i + 1, warehouse, barrier));
            loaders[i].start();
        }

        for (Thread loader : loaders) {
            try {
                loader.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n=== ВСЕ ТОВАРЫ ПЕРЕНЕСЕНЫ ===");
    }
}
