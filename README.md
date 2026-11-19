# kzp-lab01 — Магазин техніки

Консольна Java-програма, лабораторна робота № 1 (варіант 10, «Магазин техніки»)
курсу «Кросплатформні засоби програмування».

## Формат запису

Один рядок `data/input.csv` (UTF-8, роздільник `;`, без заголовка):

```
назва;категорія;ціна;гарантія(міс);залишок(шт)
Lenovo IdeaPad 3;Ноутбук;21999.00;24;5
```

Категорія — текст `Ноутбук` або `Периферія`. Програма обчислює: кількість
коректних записів, середню ціну, найдовшу гарантію, сумарний залишок на складі.

## Збірка та запуск

Перед першим запуском згенеруй Maven Wrapper (потрібні встановлені Maven і
інтернет; виконується один раз, результат комітиться в репозиторій):

```bash
mvn -N wrapper:wrapper -Dmaven=3.9.9
```

Далі:

```bash
./mvnw test                     # лише тести
./mvnw verify                   # тести + SpotBugs
./mvnw package                  # + виконуваний jar у target/
java -jar target/lab01.jar --version
java -jar target/lab01.jar --input data/input.csv --output out/report.txt
java -jar target/lab01.jar --help
```

У Windows замість `./mvnw` використовуй `mvnw.cmd`.

## Структура проєкту

```
src/main/java/ua/lpnu/kzp/
├── Main.java          — читання CSV, валідація, обчислення, звіт
└── VersionInfo.java   — читає версію і номер збірки для --version
src/test/java/ua/lpnu/kzp/
└── MainTest.java       — тести обчислень і обробки хибних рядків
```
