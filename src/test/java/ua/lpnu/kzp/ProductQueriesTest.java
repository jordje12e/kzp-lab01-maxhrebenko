package ua.lpnu.kzp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductQueriesTest {

    private List<Product> products;

    @BeforeEach
    void setUp() {
        products = List.of(
                new Laptop("Lenovo IdeaPad 3", 24999.0, 24, 5),
                new Laptop("Lenovo IdeaPad 3", 19999.0, 12, 0),
                new Laptop("Asus VivoBook 15", 21999.0, 24, 3),
                new Peripheral("Logitech MX Master", 2199.0, 12, 10),
                new Peripheral("Razer DeathAdder", 1499.0, 6, 0));
    }

    @Test
    void withPositiveStockFiltersOutZeroStock() {
        List<Product> inStock = ProductQueries.withPositiveStock(products);

        assertEquals(3, inStock.size());
        assertTrue(inStock.stream().allMatch(product -> product.stock() > 0));
    }

    @Test
    void namesAreSortedAlphabetically() {
        List<String> names = ProductQueries.names(products);

        assertEquals(List.of(names.stream().sorted().toArray(String[]::new)), names);
        assertEquals(products.size(), names.size());
    }

    @Test
    void stockValueByCategoryGroupsBySubtype() {
        var byCategory = ProductQueries.stockValueByCategory(products);

        double expectedLaptopStockValue = 24999.0 * 5 + 19999.0 * 0 + 21999.0 * 3;
        double expectedPeripheralStockValue = 2199.0 * 10 + 1499.0 * 0;
        assertEquals(expectedLaptopStockValue, byCategory.getOrDefault(ProductKind.LAPTOP, 0.0), 0.001);
        assertEquals(expectedPeripheralStockValue, byCategory.getOrDefault(ProductKind.PERIPHERAL, 0.0), 0.001);
    }

    @Test
    void priceStatisticsComputesMinAndMax() {
        var stats = ProductQueries.priceStatistics(products);

        assertEquals(1499.0, stats.getMin(), 0.001);
        assertEquals(24999.0, stats.getMax(), 0.001);
        assertEquals(5, stats.getCount());
    }

    @Test
    void topByPriceDescendingReturnsMostExpensiveFirst() {
        List<String> top = ProductQueries.topByPriceDescending(products, 2);

        assertEquals(List.of("Lenovo IdeaPad 3", "Asus VivoBook 15"), top);
    }

    @Test
    void topByPriceDescendingWithZeroLimitReturnsEmptyList() {
        assertEquals(List.of(), ProductQueries.topByPriceDescending(products, 0));
    }

    @Test
    void topByPriceDescendingWithNegativeLimitIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> ProductQueries.topByPriceDescending(products, -1));
    }

    @Test
    void topByPriceDescendingWithOversizedLimitReturnsAll() {
        List<String> top = ProductQueries.topByPriceDescending(products, 100);

        assertEquals(products.size(), top.size());
    }

    @Test
    void findByNameReturnsFirstMatch() {
        Optional<Product> found = ProductQueries.findByName(products, "Lenovo IdeaPad 3");

        assertTrue(found.isPresent());
        assertEquals(24999.0, found.get().price());
    }

    @Test
    void findByNameReturnsEmptyWhenMissing() {
        assertEquals(Optional.empty(), ProductQueries.findByName(products, "Невідомий товар"));
    }
}
