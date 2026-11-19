package ua.lpnu.kzp;

import java.util.Locale;

/**
 * Товар магазину техніки (варіант 10).
 *
 * <p>Лабораторна робота № 2: з'являється перший клас предметної області.
 * Усі поля - {@code private final}, об'єкт неможливо створити в недійсному
 * стані - конструктор одразу перевіряє всі обмеження.</p>
 */
public final class Product {

    private final String name;
    private final String category;
    private final double price;
    private final int warrantyMonths;
    private final int stock;

    /**
     * Створює товар, перевіряючи всі інваріанти.
     *
     * @throws IllegalArgumentException якщо хоч одне поле порушує обмеження
     */
    public Product(String name, String category, double price, int warrantyMonths, int stock) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Назва товару не може бути порожньою");
        }
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Категорія товару не може бути порожньою");
        }
        if (!category.equals("Ноутбук") && !category.equals("Периферія")) {
            throw new IllegalArgumentException("Невідома категорія товару: " + category);
        }
        if (price < 0 || !Double.isFinite(price)) {
            throw new IllegalArgumentException("Ціна має бути скінченним невід'ємним числом: " + price);
        }
        if (warrantyMonths < 0) {
            throw new IllegalArgumentException("Гарантія не може бути від'ємною: " + warrantyMonths);
        }
        if (stock < 0) {
            throw new IllegalArgumentException("Залишок на складі не може бути від'ємним: " + stock);
        }

        this.name = name;
        this.category = category;
        this.price = price;
        this.warrantyMonths = warrantyMonths;
        this.stock = stock;
    }

    public String name() {
        return name;
    }

    public String category() {
        return category;
    }

    public double price() {
        return price;
    }

    public int warrantyMonths() {
        return warrantyMonths;
    }

    public int stock() {
        return stock;
    }

    /** Повертає гарантію і залишок одним незмінним записом. */
    public WarrantyStock warrantyStock() {
        return new WarrantyStock(warrantyMonths, stock);
    }

    /** Повертає вартість усього залишку цього товару на складі. */
    public double stockValue() {
        return price * stock;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "Product{name='%s', category='%s', price=%.2f, warrantyMonths=%d, stock=%d}",
                name, category, price, warrantyMonths, stock);
    }
}
