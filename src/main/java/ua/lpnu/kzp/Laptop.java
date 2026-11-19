package ua.lpnu.kzp;

/**
 * Ноутбук - гарантійне обслуговування дорожче, ніж для периферії,
 * оскільки ремонт ноутбука складніший.
 */
public final class Laptop extends Product {

    private static final double PRICE_RATE = 0.05;
    private static final double MONTH_RATE = 50.0;

    public Laptop(String name, double price, int warrantyMonths, int stock) {
        super(name, ProductKind.LAPTOP, price, warrantyMonths, stock);
    }

    @Override
    public double warrantyCost() {
        return price() * PRICE_RATE + warrantyMonths() * MONTH_RATE;
    }
}
