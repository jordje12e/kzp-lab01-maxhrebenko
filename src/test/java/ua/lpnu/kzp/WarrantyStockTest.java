package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WarrantyStockTest {

    @Test
    void storesBothFields() {
        WarrantyStock stock = new WarrantyStock(12, 5);

        assertEquals(12, stock.warrantyMonths());
        assertEquals(5, stock.stock());
    }

    @Test
    void equalRecordsAreEqual() {
        assertEquals(new WarrantyStock(12, 5), new WarrantyStock(12, 5));
    }

    @Test
    void negativeWarrantyIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new WarrantyStock(-1, 5));
    }

    @Test
    void negativeStockIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new WarrantyStock(12, -1));
    }
}
