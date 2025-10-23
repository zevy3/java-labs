import java.util.HashMap;

class Car {
    private String brand;
    private String model;
    private int year;

    public Car(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    @Override
    public String toString() {
        return brand + " " + model + " (" + year + ")";
    }
}

public class CarRegistry {
    private HashMap<String, Car> cars = new HashMap<>();

    public void addCar(String plate, Car car) {
        cars.put(plate, car);
    }

    public Car findCar(String plate) {
        return cars.get(plate);
    }

    public void removeCar(String plate) {
        cars.remove(plate);
    }

    public void printAll() {
        for (String plate : cars.keySet()) {
            System.out.println(plate + " -> " + cars.get(plate));
        }
    }

    public static void main(String[] args) {
        CarRegistry registry = new CarRegistry();
        registry.addCar("А777МР", new Car("Dodge", "Challenger", 2020));
        registry.addCar("В888ОР", new Car("BMW", "X5", 2022));

        System.out.println("Поиск: " + registry.findCar("А777МР"));
        registry.printAll();

        registry.removeCar("В888ОР");
        System.out.println("После удаления:");
        registry.printAll();
    }
}
