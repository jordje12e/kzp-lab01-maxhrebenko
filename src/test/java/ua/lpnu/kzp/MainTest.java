package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** Перевіряє обчислення показників і обробку хибних рядків без зупинки програми. */
class MainTest {

    @Test
    void countsOnlyValidRecords() {
        List<String> lines = List.of(
                "Lenovo IdeaPad 3;Ноутбук;21999.00;24;5",
                "Dell Inspiron 14;Ноутбук;24999.00;36;3");

        String report = Main.buildReport(lines);

        assertTrue(report.contains("Коректних записів: 2"));
        assertTrue(report.contains("Найдовша гарантія: 36 міс"));
        assertTrue(report.contains("Загальна кількість на складі: 8 шт"));
    }

    @Test
    void computesAverageWithDecimalPrecision() {
        List<String> lines = List.of(
                "A;Ноутбук;10.00;1;1",
                "B;Ноутбук;11.00;1;1");

        String report = Main.buildReport(lines);

        // (10 + 11) / 2 = 10.50, а не 10 через ціле ділення.
        assertTrue(report.contains("Середня ціна: 10.50 грн"));
    }

    @Test
    void emptyInputGivesZeroedReportWithoutDivisionByZero() {
        String report = Main.buildReport(List.of());

        assertTrue(report.contains("Коректних записів: 0"));
        assertTrue(report.contains("Середня ціна: 0.00 грн"));
    }

    @Test
    void blankLineIsReportedAsError() {
        String report = Main.buildReport(List.of(""));

        assertTrue(report.contains("Помилок: 1"));
        assertTrue(report.contains("порожній рядок"));
    }

    @Test
    void wrongFieldCountIsReportedWithLineNumber() {
        String report = Main.buildReport(List.of("JBL Charge 5;Периферія;1999.00;12"));

        assertTrue(report.contains("Рядок 1:"));
        assertTrue(report.contains("Помилок: 1"));
    }

    @Test
    void emptyNameIsRejected() {
        String report = Main.buildReport(List.of(";Периферія;999.00;6;5"));

        assertTrue(report.contains("порожнє обов'язкове текстове поле"));
    }

    @Test
    void negativePriceIsRejected() {
        String report = Main.buildReport(List.of("Razer DeathAdder;Периферія;-150.00;6;20"));

        assertTrue(report.contains("не можуть бути від'ємними"));
    }

    @Test
    void malformedNumberIsRejected() {
        String report = Main.buildReport(List.of("Acer Nitro 5;Ноутбук;31999.00;abc;2"));

        assertTrue(report.contains("помилковий формат"));
    }

    @Test
    void unknownCategoryIsRejected() {
        String report = Main.buildReport(List.of("Samsung Monitor 27;Монітор;5999.00;12;8"));

        assertTrue(report.contains("невідома категорія"));
    }

    @Test
    void oneInvalidLineDoesNotStopProcessingTheRest() {
        List<String> lines = List.of(
                "Lenovo IdeaPad 3;Ноутбук;21999.00;24;5",
                "Razer DeathAdder;Периферія;-150.00;6;20",
                "Kingston SSD 1TB;Периферія;2199.00;24;15");

        String report = Main.buildReport(lines);

        assertTrue(report.contains("Коректних записів: 2"));
        assertTrue(report.contains("Помилок: 1"));
    }
}
