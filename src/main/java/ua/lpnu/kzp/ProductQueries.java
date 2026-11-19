package ua.lpnu.kzp;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

/**
 * Набір функціональних запитів над списком товарів, побудованих через
 * Stream API (лабораторна робота № 4).
 */
public final class ProductQueries {

    private ProductQueries() {
    }

    /** Повертає товари, яких ще є в наявності (залишок більший за нуль). */
    public static List<Product> withPositiveStock(List<Product> products) {
        return products.stream()
                .filter(product -> product.stock() > 0)
                .toList();
    }

    /** Повертає назви всіх товарів у відсортованому порядку. */
    public static List<String> names(List<Product> products) {
        return products.stream()
                .map(Product::name)
                .sorted()
                .toList();
    }

    /** Повертає сумарну вартість залишку товарів, згруповану за категорією. */
    public static Map<ProductKind, Double> stockValueByCategory(List<Product> products) {
        return products.stream()
                .collect(groupingBy(Product::kind, () -> new EnumMap<>(ProductKind.class),
                        summingDouble(Product::stockValue)));
    }

    /** Повертає статистику за ціною (кількість, мінімум, максимум, середнє, сума). */
    public static DoubleSummaryStatistics priceStatistics(List<Product> products) {
        return products.stream()
                .mapToDouble(Product::price)
                .summaryStatistics();
    }

    /** Повертає назви {@code limit} найдорожчих товарів, від найдорожчого до дешевшого. */
    public static List<String> topByPriceDescending(List<Product> products, int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Кількість елементів не може бути від'ємною: " + limit);
        }
        return products.stream()
                .sorted(Comparator.comparingDouble(Product::price).reversed()
                        .thenComparing(Product::name))
                .limit(limit)
                .map(Product::name)
                .toList();
    }

    /** Шукає перший товар із заданою назвою. */
    public static Optional<Product> findByName(List<Product> products, String name) {
        return products.stream()
                .filter(product -> product.name().equals(name))
                .findFirst();
    }
}
