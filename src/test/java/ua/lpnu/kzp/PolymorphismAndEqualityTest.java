package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PolymorphismAndEqualityTest {

    @Test
    void laptopAndPeripheralComputeWarrantyCostDifferently() {
        Product laptop = new Laptop("Lenovo IdeaPad 3", 20000.0, 24, 1);
        Product peripheral = new Peripheral("Logitech MX Master", 2000.0, 12, 1);

        assertEquals(2200.0, laptop.warrantyCost(), 0.001);
        assertEquals(220.0, peripheral.warrantyCost(), 0.001);
    }

    @Test
    void sameClassAndNameAreEqual() {
        Product first = new Laptop("Lenovo IdeaPad 3", 20000.0, 24, 1);
        Product second = new Laptop("Lenovo IdeaPad 3", 24999.0, 12, 5);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void differentSubtypesWithSameNameAreNotEqual() {
        Product laptop = new Laptop("Такса", 20000.0, 24, 1);
        Product peripheral = new Peripheral("Такса", 2000.0, 12, 1);

        assertNotEquals(laptop, peripheral);
    }

    @Test
    void hashSetTreatsEqualProductsAsDuplicates() {
        Set<Product> products = new HashSet<>();
        products.add(new Laptop("Lenovo IdeaPad 3", 20000.0, 24, 1));
        products.add(new Laptop("Lenovo IdeaPad 3", 24999.0, 12, 5));
        products.add(new Peripheral("Logitech MX Master", 2000.0, 12, 1));

        assertEquals(2, products.size());
    }

    @Test
    void productIsNotEqualToNullOrOtherType() {
        Product laptop = new Laptop("Lenovo IdeaPad 3", 20000.0, 24, 1);

        assertFalse(laptop.equals(null));
        assertFalse(laptop.equals("Lenovo IdeaPad 3"));
        assertTrue(laptop.equals(laptop));
    }
}
