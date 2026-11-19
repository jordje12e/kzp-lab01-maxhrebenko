package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void countsOnlyValidRecords() {
        String report = Main.buildReport(List.of(
                "Товар A;Ноутбук;1000.00;12;1",
                "Товар B;Периферія;2000.00;6;2"));

        assertTrue(report.contains("Коректних записів: 2"));
        assertTrue(report.contains("Помилок: 0"));
    }

    @Test
    void computesAverageWithDecimalPrecision() {
        String report = Main.buildReport(List.of(
                "Товар A;Ноутбук;10.00;12;1",
                "Товар B;Периферія;11.00;6;1"));

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
        String report = Main.buildReport(List.of("   "));

        assertTrue(report.contains("Помилок: 1"));
        assertTrue(report.contains("Рядок 1:"));
    }

    @Test
    void wrongFieldCountIsReportedWithLineNumber() {
        String report = Main.buildReport(List.of("Товар;Ноутбук;1000;12"));

        assertTrue(report.contains("Рядок 1:"));
    }

    @Test
    void emptyNameIsRejected() {
        String report = Main.buildReport(List.of(" ;Ноутбук;1000;12;1"));

        assertTrue(report.contains("Помилок: 1"));
    }

    @Test
    void negativePriceIsRejected() {
        String report = Main.buildReport(List.of("Товар;Ноутбук;-1;12;1"));

        assertTrue(report.contains("Помилок: 1"));
    }

    @Test
    void malformedNumberIsRejected() {
        String report = Main.buildReport(List.of("Товар;Ноутбук;сто;12;1"));

        assertTrue(report.contains("Помилок: 1"));
    }

    @Test
    void unknownCategoryIsRejected() {
        String report = Main.buildReport(List.of("Монітор Dell;Монітор;1000;12;1"));

        assertTrue(report.contains("Помилок: 1"));
    }

    @Test
    void warrantyCostDependsOnProductKind() {
        String report = Main.buildReport(List.of(
                "Ноутбук A;Ноутбук;20000.00;24;1"));

        assertTrue(report.contains("Загальна вартість гарантійного обслуговування: 2200.00 грн"));
    }

    @Test
    void oneInvalidLineDoesNotStopProcessingTheRest() {
        String report = Main.buildReport(List.of(
                "Товар A;Ноутбук;1000.00;12;1",
                "Товар;Монітор;1000;12;1",
                "Товар B;Периферія;2000.00;6;2"));

        assertTrue(report.contains("Коректних записів: 2"));
        assertTrue(report.contains("Помилок: 1"));
    }
}
