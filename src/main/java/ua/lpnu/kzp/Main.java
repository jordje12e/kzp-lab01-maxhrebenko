package ua.lpnu.kzp;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Консольна програма обробки товарів магазину техніки (варіант 10).
 *
 * <p>Лабораторна робота № 1: рядок CSV розбирається прямо в циклі, без
 * окремих класів предметної області - вони з'являться в наступних роботах.
 * Формат одного запису: {@code назва;категорія;ціна;гарантія(міс);залишок(шт)}.</p>
 */
public final class Main {

    private static final int FIELD_COUNT = 5;
    private static final Path DEFAULT_INPUT = Path.of("data", "input.csv");
    private static final Path DEFAULT_OUTPUT = Path.of("out", "report.txt");

    /** Забороняє створення екземплярів класу запуску. */
    private Main() {
    }

    /**
     * Точка входу до програми.
     *
     * @param args {@code --help}, {@code --version}, {@code --input <файл>},
     *             {@code --output <файл>}
     */
    public static void main(String[] args) {
        // Явно задаємо UTF-8 для консолі, бо кодування Windows за замовчуванням
        // може спотворювати українські символи.
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        if (containsFlag(args, "--help")) {
            printUsage();
            return;
        }
        if (containsFlag(args, "--version")) {
            System.out.println(VersionInfo.current());
            return;
        }

        Path input = argumentValue(args, "--input").map(Path::of).orElse(DEFAULT_INPUT);
        Path output = argumentValue(args, "--output").map(Path::of).orElse(DEFAULT_OUTPUT);

        List<String> lines;
        try {
            lines = Files.readAllLines(input, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.out.printf("Не вдалося прочитати файл %s: %s%n", input, exception.getMessage());
            return;
        }

        String report = buildReport(lines);
        System.out.print(report);
        writeReport(output, report);
    }

    /**
     * Обробляє всі рядки вхідного файлу й формує текст звіту.
     *
     * @param lines рядки вхідного CSV-файлу
     * @return готовий, відформатований текст звіту
     */
    static String buildReport(List<String> lines) {
        List<String> errors = new ArrayList<>();

        int validCount = 0;
        double totalPrice = 0.0;
        int maxWarranty = 0;
        int totalStock = 0;

        for (int lineNumber = 1; lineNumber <= lines.size(); lineNumber++) {
            String line = lines.get(lineNumber - 1);
            if (line.isBlank()) {
                errors.add("Рядок %d: порожній рядок".formatted(lineNumber));
                continue;
            }

            String[] fields = line.split(";", -1);
            if (fields.length != FIELD_COUNT) {
                errors.add("Рядок %d: очікується %d полів, отримано %d"
                        .formatted(lineNumber, FIELD_COUNT, fields.length));
                continue;
            }

            String name = fields[0].trim();
            String category = fields[1].trim();
            if (name.isEmpty() || category.isEmpty()) {
                errors.add("Рядок %d: порожнє обов'язкове текстове поле".formatted(lineNumber));
                continue;
            }
            if (!category.equals("Ноутбук") && !category.equals("Периферія")) {
                errors.add("Рядок %d: невідома категорія товару \"%s\"".formatted(lineNumber, category));
                continue;
            }

            double price;
            int warrantyMonths;
            int stock;
            try {
                price = Double.parseDouble(fields[2].trim());
                warrantyMonths = Integer.parseInt(fields[3].trim());
                stock = Integer.parseInt(fields[4].trim());
            } catch (NumberFormatException exception) {
                errors.add("Рядок %d: числове поле має помилковий формат".formatted(lineNumber));
                continue;
            }

            if (price < 0 || !Double.isFinite(price) || warrantyMonths < 0 || stock < 0) {
                errors.add("Рядок %d: числові поля не можуть бути від'ємними".formatted(lineNumber));
                continue;
            }

            validCount++;
            totalPrice += price;
            maxWarranty = Math.max(maxWarranty, warrantyMonths);
            totalStock += stock;
        }

        // Явне приведення до double не дає цілочисловому діленню відкинути дробову частину.
        double averagePrice = validCount == 0 ? 0.0 : totalPrice / validCount;

        StringBuilder report = new StringBuilder();
        report.append(line("Коректних записів: %d", validCount));
        report.append(line("Середня ціна: %.2f грн", averagePrice));
        report.append(line("Найдовша гарантія: %d міс", maxWarranty));
        report.append(line("Загальна кількість на складі: %d шт", totalStock));
        report.append(line("Помилок: %d", errors.size()));
        for (String error : errors) {
            report.append(error).append(System.lineSeparator());
        }
        return report.toString();
    }

    private static String line(String pattern, Object... args) {
        return String.format(Locale.ROOT, pattern, args) + System.lineSeparator();
    }

    private static void writeReport(Path output, String report) {
        Path parent = output.toAbsolutePath().getParent();
        try {
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.writeString(output, report, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new UncheckedIOException("Не вдалося записати звіт у " + output, exception);
        }
    }

    private static void printUsage() {
        System.out.printf("Використання: java -jar lab01.jar [--help] [--version] "
                + "[--input <файл>] [--output <файл>]%n");
    }

    private static boolean containsFlag(String[] args, String flag) {
        for (String arg : args) {
            if (flag.equals(arg)) {
                return true;
            }
        }
        return false;
    }

    private static java.util.Optional<String> argumentValue(String[] args, String name) {
        for (int i = 0; i < args.length - 1; i++) {
            if (name.equals(args[i])) {
                return java.util.Optional.of(args[i + 1]);
            }
        }
        return java.util.Optional.empty();
    }
}
