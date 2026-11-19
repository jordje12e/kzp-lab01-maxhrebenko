package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductKindTest {

    @Test
    void fromLabelFindsLaptop() {
        assertEquals(ProductKind.LAPTOP, ProductKind.fromLabel("Ноутбук"));
    }

    @Test
    void fromLabelFindsPeripheral() {
        assertEquals(ProductKind.PERIPHERAL, ProductKind.fromLabel("Периферія"));
    }

    @Test
    void labelMatchesEnumConstant() {
        assertEquals("Ноутбук", ProductKind.LAPTOP.label());
        assertEquals("Периферія", ProductKind.PERIPHERAL.label());
    }

    @Test
    void unknownLabelIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> ProductKind.fromLabel("Монітор"));
    }
}
