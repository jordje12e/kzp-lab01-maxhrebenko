# КЗП — Лабораторна робота № 3

Варіант 10, домен "Магазин техніки".

## Що нового порівняно з лабою 2

- `Product` став **абстрактним** базовим класом.
- З'явились конкретні підтипи `Laptop` і `Peripheral`, кожен по-своєму
  реалізує `warrantyCost()` — приклад **поліморфізму**.
- Категорія товару тепер `enum ProductKind` замість рядка.
- `Product` отримав фінальні `equals`/`hashCode` на основі класу й назви.

## Збірка і запуск

```
./mvnw verify
java -jar target/lab03.jar --input data/input.csv --output out/report.txt
```

## Структура проєкту

- `src/main/java/ua/lpnu/kzp/Main.java` — читання файлу, розбір, підрахунок показників.
- `src/main/java/ua/lpnu/kzp/Product.java` — абстрактний базовий клас товару.
- `src/main/java/ua/lpnu/kzp/Laptop.java`, `Peripheral.java` — конкретні підтипи.
- `src/main/java/ua/lpnu/kzp/ProductKind.java` — enum категорій.
- `src/main/java/ua/lpnu/kzp/WarrantyStock.java` — record "гарантія + залишок".
- `src/main/java/ua/lpnu/kzp/ProductParser.java` — статична фабрика розбору CSV.
- `src/main/java/ua/lpnu/kzp/VersionInfo.java` — версія й номер збірки.
- `src/test/java/ua/lpnu/kzp/` — модульні тести (JUnit 5).

## Дотримання академічної доброчесності

Робота виконана з використанням ШІ-асистента (Claude, Anthropic) як інструмента
допомоги у написанні коду, тестів і документації. Усі рішення щодо вимог
варіанта та структури проєкту узгоджені зі студентом.
