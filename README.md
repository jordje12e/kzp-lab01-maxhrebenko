# КЗП — Лабораторна робота № 4

Варіант 10, домен "Магазин техніки".

## Що нового порівняно з лабою 3

Підсумкові показники й додаткові вибірки тепер рахуються через **Stream API**
(`java.util.stream`) у новому класі `ProductQueries`, замість звичайних циклів:

- `withPositiveStock` — `filter`
- `names` — `map` + `sorted`
- `stockValueByCategory` — `groupingBy` + `summingDouble`
- `priceStatistics` — `mapToDouble(...).summaryStatistics()`
- `topByPriceDescending` — `sorted` + `limit`
- `findByName` — `filter` + `findFirst()` -> `Optional<Product>`

## Збірка і запуск

```
./mvnw verify
java -jar target/lab04.jar --input data/input.csv --output out/report.txt
```

## Структура проєкту

- `src/main/java/ua/lpnu/kzp/Main.java` — читання файлу, розбір, формування звіту.
- `src/main/java/ua/lpnu/kzp/ProductQueries.java` — функціональні запити через Stream API.
- `src/main/java/ua/lpnu/kzp/Product.java`, `Laptop.java`, `Peripheral.java`, `ProductKind.java` — ієрархія товарів (з лаби 3).
- `src/main/java/ua/lpnu/kzp/WarrantyStock.java` — record "гарантія + залишок".
- `src/main/java/ua/lpnu/kzp/ProductParser.java` — статична фабрика розбору CSV.
- `src/main/java/ua/lpnu/kzp/VersionInfo.java` — версія й номер збірки.
- `src/test/java/ua/lpnu/kzp/` — модульні тести (JUnit 5).

## Дотримання академічної доброчесності

Робота виконана з використанням ШІ-асистента (Claude, Anthropic) як інструмента
допомоги у написанні коду, тестів і документації. Усі рішення щодо вимог
варіанта та структури проєкту узгоджені зі студентом.
