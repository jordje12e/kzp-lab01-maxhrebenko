package ua.lpnu.kzp;

/**
 * Розбирає один рядок вхідного CSV-файлу у об'єкт {@link Product}.
 *
 * <p>Формат рядка: {@code назва;категорія;ціна;гарантія(міс);залишок(шт)}.</p>
 */
public final class ProductParser {

    private static final int FIELD_COUNT = 5;

    private ProductParser() {
    }

    /**
     * Статичний фабричний метод: перетворює рядок CSV на товар.
     *
     * @param line один рядок вхідного файлу
     * @return готовий, перевірений об'єкт {@link Product}
     * @throws IllegalArgumentException якщо рядок має неправильний формат
     *                                  або якесь поле порушує обмеження
     */
    public static Product fromCsv(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("Порожній рядок");
        }

        String[] fields = line.split(";", -1);
        if (fields.length != FIELD_COUNT) {
            throw new IllegalArgumentException(
                    "Очікується %d полів, отримано %d".formatted(FIELD_COUNT, fields.length));
        }

        String name = fields[0].trim();
        String category = fields[1].trim();

        double price;
        int warrantyMonths;
        int stock;
        try {
            price = Double.parseDouble(fields[2].trim());
            warrantyMonths = Integer.parseInt(fields[3].trim());
            stock = Integer.parseInt(fields[4].trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Числове поле має помилковий формат", exception);
        }

        return new Product(name, category, price, warrantyMonths, stock);
    }
}
