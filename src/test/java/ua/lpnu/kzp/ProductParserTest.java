package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductParserTest {

    @Test
    void parsesLaptopLine() {
        Product product = ProductParser.fromCsv("Lenovo IdeaPad 3;Ноутбук;24999.00;24;5");

        assertInstanceOf(Laptop.class, product);
        assertEquals("Lenovo IdeaPad 3", product.name());
        assertEquals(ProductKind.LAPTOP, product.kind());
    }

    @Test
    void parsesPeripheralLine() {
        Product product = ProductParser.fromCsv("Logitech MX Master;Периферія;2000.00;12;3");

        assertInstanceOf(Peripheral.class, product);
        assertEquals(ProductKind.PERIPHERAL, product.kind());
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
