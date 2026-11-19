package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    @Test
    void validProductStoresAllFields() {
        Product product = new Product("Lenovo IdeaPad 3", "Ноутбук", 24999.0, 24, 5);

        assertEquals("Lenovo IdeaPad 3", product.name());
        assertEquals("Ноутбук", product.category());
        assertEquals(24999.0, product.price());
        assertEquals(24, product.warrantyMonths());
        assertEquals(5, product.stock());
    }

    @Test
    void stockValueMultipliesPriceByStock() {
        Product product = new Product("Logitech MX Master", "Периферія", 2000.0, 12, 3);

        assertEquals(6000.0, product.stockValue());
    }

    @Test
    void warrantyStockWrapsBothFields() {
        Product product = new Product("Logitech MX Master", "Периферія", 2000.0, 12, 3);

        assertEquals(new WarrantyStock(12, 3), product.warrantyStock());
    }

    @Test
    void blankNameIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("  ", "Ноутбук", 1000.0, 12, 1));
    }

    @Test
    void unknownCategoryIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("Монітор Dell", "Монітор", 1000.0, 12, 1));
    }

    @Test
    void negativePriceIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("Товар", "Ноутбук", -1.0, 12, 1));
    }

    @Test
    void negativeWarrantyIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("Товар", "Ноутбук", 1000.0, -1, 1));
    }

    @Test
    void negativeStockIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("Товар", "Ноутбук", 1000.0, 12, -1));
    }
}
