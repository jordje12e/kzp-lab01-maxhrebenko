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
 * <p>Лабораторна робота № 3: товари тепер представлені конкретними
 * підтипами ({@link Laptop}, {@link Peripheral}), а вартість гарантійного
 * обслуговування рахується поліморфно - через {@link Product#warrantyCost()}.</p>
 */
public final class Main {

    private static final Path DEFAULT_INPUT = Path.of("data", "input.csv");
    private static final Path DEFAULT_OUTPUT = Path.of("out", "report.txt");

    private Main() {
    }

    public static void main(String[] args) {
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
     * Розбирає всі рядки у товари, збирає помилки й формує текст звіту.
     *
     * @param lines рядки вхідного CSV-файлу
     * @return готовий, відформатований текст звіту
     */
    static String buildReport(List<String> lines) {
        List<Product> products = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (int lineNumber = 1; lineNumber <= lines.size(); lineNumber++) {
            String line = lines.get(lineNumber - 1);
            try {
                products.add(ProductParser.fromCsv(line));
            } catch (IllegalArgumentException exception) {
                errors.add("Рядок %d: %s".formatted(lineNumber, exception.getMessage()));
            }
        }

        int validCount = products.size();
        double totalPrice = 0.0;
        double totalWarrantyCost = 0.0;
        int maxWarranty = 0;
        int totalStock = 0;
        for (Product product : products) {
            totalPrice += product.price();
            totalWarrantyCost += product.warrantyCost();
            maxWarranty = Math.max(maxWarranty, product.warrantyMonths());
            totalStock += product.stock();
        }
        double averagePrice = validCount == 0 ? 0.0 : totalPrice / validCount;

        StringBuilder report = new StringBuilder();
        report.append(line("Коректних записів: %d", validCount));
        report.append(line("Середня ціна: %.2f грн", averagePrice));
        report.append(line("Найдовша гарантія: %d міс", maxWarranty));
        report.append(line("Загальна кількість на складі: %d шт", totalStock));
        report.append(line("Загальна вартість гарантійного обслуговування: %.2f грн", totalWarrantyCost));
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
        System.out.printf("Використання: java -jar lab03.jar [--help] [--version] "
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
