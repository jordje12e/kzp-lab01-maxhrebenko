package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductParserTest {

    @Test
    void parsesValidLine() {
        Product product = ProductParser.fromCsv("Lenovo IdeaPad 3;Ноутбук;24999.00;24;5");

        assertEquals("Lenovo IdeaPad 3", product.name());
        assertEquals("Ноутбук", product.category());
        assertEquals(24999.00, product.price());
        assertEquals(24, product.warrantyMonths());
        assertEquals(5, product.stock());
    }

    @Test
    void blankLineIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> ProductParser.fromCsv("   "));
    }

    @Test
    void wrongFieldCountIsRejected() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ProductParser.fromCsv("Товар;Ноутбук;1000;12"));

        assertTrue(exception.getMessage().contains("5"));
    }

    @Test
    void malformedNumberIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> ProductParser.fromCsv("Товар;Ноутбук;сто;12;1"));
    }

    @Test
    void unknownCategoryIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> ProductParser.fromCsv("Монітор Dell;Монітор;1000;12;1"));
    }
}
