package ua.lpnu.kzp;

import java.util.Locale;
import java.util.Objects;

/**
 * Абстрактний товар магазину техніки (варіант 10).
 *
 * <p>Лабораторна робота № 3: клас стає абстрактним базовим типом, від якого
 * успадковуються конкретні підтипи {@link Laptop} та {@link Peripheral}.
 * Кожен підтип по-своєму рахує вартість гарантійного обслуговування
 * ({@link #warrantyCost()}) - це приклад поліморфізму.</p>
 */
public abstract class Product {

    private final String name;
    private final ProductKind kind;
    private final double price;
    private final int warrantyMonths;
    private final int stock;

    /**
     * Створює товар, перевіряючи всі інваріанти. Доступний лише підкласам.
     *
     * @throws IllegalArgumentException якщо хоч одне поле порушує обмеження
     */
    protected Product(String name, ProductKind kind, double price, int warrantyMonths, int stock) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Назва товару не може бути порожньою");
        }
        if (kind == null) {
            throw new IllegalArgumentException("Категорія товару не може бути відсутньою");
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
        this.kind = kind;
        this.price = price;
        this.warrantyMonths = warrantyMonths;
        this.stock = stock;
    }

    public final String name() {
        return name;
    }

    public final ProductKind kind() {
        return kind;
    }

    public final double price() {
        return price;
    }

    public final int warrantyMonths() {
        return warrantyMonths;
    }

    public final int stock() {
        return stock;
    }

    /** Повертає гарантію і залишок одним незмінним записом. */
    public final WarrantyStock warrantyStock() {
        return new WarrantyStock(warrantyMonths, stock);
    }

    /** Повертає вартість усього залишку цього товару на складі. */
    public final double stockValue() {
        return price * stock;
    }

    /**
     * Вартість гарантійного обслуговування - рахується по-різному для
     * кожного підтипу товару (поліморфна поведінка).
     */
    public abstract double warrantyCost();

    @Override
    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Product product = (Product) other;
        return name.equals(product.name);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getClass(), name);
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "%s{name='%s', kind=%s, price=%.2f, warrantyMonths=%d, stock=%d}",
                getClass().getSimpleName(), name, kind, price, warrantyMonths, stock);
    }
}
