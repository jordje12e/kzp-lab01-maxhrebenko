package ua.lpnu.kzp;

/**
 * Периферійний пристрій - гарантійне обслуговування дешевше, ніж для
 * ноутбуків, оскільки ремонт простіший і менш трудомісткий.
 */
public final class Peripheral extends Product {

    private static final double PRICE_RATE = 0.02;
    private static final double MONTH_RATE = 15.0;

    public Peripheral(String name, double price, int warrantyMonths, int stock) {
        super(name, ProductKind.PERIPHERAL, price, warrantyMonths, stock);
    }

    @Override
    public double warrantyCost() {
        return price() * PRICE_RATE + warrantyMonths() * MONTH_RATE;
    }
}
