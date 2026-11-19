package ua.lpnu.kzp;

/**
 * Категорія товару магазину техніки (варіант 10).
 *
 * <p>Лабораторна робота № 3: категорія стає перелічуваним типом замість
 * звичайного рядка, і водночас визначає, який підтип {@link Product}
 * слід створити.</p>
 */
public enum ProductKind {

    LAPTOP("Ноутбук"),
    PERIPHERAL("Периферія");

    private final String label;

    ProductKind(String label) {
        this.label = label;
    }

    /** Повертає текстову мітку категорії, як вона записана у вхідному CSV. */
    public String label() {
        return label;
    }

    /**
     * Знаходить категорію за її текстовою міткою.
     *
     * @throws IllegalArgumentException якщо мітка не відповідає жодній категорії
     */
    public static ProductKind fromLabel(String label) {
        for (ProductKind kind : values()) {
            if (kind.label.equals(label)) {
                return kind;
            }
        }
        throw new IllegalArgumentException("Невідома категорія товару: " + label);
    }
}
