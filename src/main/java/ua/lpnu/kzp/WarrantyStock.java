package ua.lpnu.kzp;

/**
 * Незмінна пара "гарантія - залишок на складі" для одного товару.
 *
 * @param warrantyMonths строк гарантії у місяцях (не менше 0)
 * @param stock          кількість одиниць на складі (не менше 0)
 */
public record WarrantyStock(int warrantyMonths, int stock) {

    /** Компактний конструктор перевіряє інваріанти запису. */
    public WarrantyStock {
        if (warrantyMonths < 0) {
            throw new IllegalArgumentException("Гарантія не може бути від'ємною: " + warrantyMonths);
        }
        if (stock < 0) {
            throw new IllegalArgumentException("Залишок на складі не може бути від'ємним: " + stock);
        }
    }
}
