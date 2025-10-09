abstract class Establishment {
    protected String name;
    protected String address;
    protected double area;

    public Establishment() {}

    public Establishment(String name, String address, double area) {
        this.name = name;
        this.address = address;
        this.area = area;
    }

    public abstract void open();

    public void close() {
        System.out.println(name + " закрывается.");
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }

    public String toString() {
        return name + " (" + address + "), площадь: " + area + " м²";
    }
}

class Cafe extends Establishment {
    private int tables;
    private static int count = 0;

    public Cafe(String name, String address, double area, int tables) {
        super(name, address, area);
        this.tables = tables;
        count++;
    }

    public Cafe() { count++; }

    public void open() {
        System.out.println(name + " открыто. Добро пожаловать в кафе!");
    }

    public void serve() {
        System.out.println(name + " обслуживает посетителей.");
    }

    public static int getCount() { return count; }

    public String toString() {
        return super.toString() + ", столов: " + tables;
    }
}

class Store extends Establishment {
    private String goodsType;

    public Store(String name, String address, double area, String goodsType) {
        super(name, address, area);
        this.goodsType = goodsType;
    }

    public Store() {}

    public void open() {
        System.out.println(name + " открыт. Добро пожаловать в магазин!");
    }

    public void restock() {
        System.out.println(name + " пополняет запасы.");
    }

    public String toString() {
        return super.toString() + ", тип товара: " + goodsType;
    }
}

class Library extends Establishment {
    private int books;

    public Library(String name, String address, double area, int books) {
        super(name, address, area);
        this.books = books;
    }

    public Library() {}

    public void open() {
        System.out.println(name + " открыта. Приглашаем читать!");
    }

    public void lendBook() {
        System.out.println(name + " выдает книгу читателю.");
    }

    public String toString() {
        return super.toString() + ", книг: " + books;
    }
}

public class Main {
    public static void main(String[] args) {
        Cafe cafe = new Cafe("Кафе 'ВОСТОЧНОЕ'", "ул. Авиамоторная, 8A", 100.0, 10);
        Store store = new Store("Магазин 'МТУСИСТ'", "пр. Мира, 7", 80.0, "продовольственные");
        Library library = new Library("Библиотека МТУСИ", "ул. Народного Ополчения, 32", 120.0, 5000);

        System.out.println("Информация о заведениях:");
        System.out.println(cafe);
        System.out.println(store);
        System.out.println(library);

        System.out.println("\nРабота заведений:");
        cafe.open();
        store.open();
        library.open();

        cafe.serve();
        store.restock();
        library.lendBook();

        System.out.println("\nКоличество созданных кафе: " + Cafe.getCount());
    }
}
