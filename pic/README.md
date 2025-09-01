# Дипломный проект по профессии «Тестировщик» в Нетологии.

***

## О проекте

Целью дипломного проекта является автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API
Банка.
Приложением является веб-сервис, который предлагает купить тур по определённой цене двумя способами:

1. Обычная оплата по дебетовой карте.
2. Уникальная технология: выдача кредита по данным банковской карты.

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:

* сервису платежей \(Payment Gate);
* кредитному сервису \(Credit Gate).

Приложение в собственной СУБД сохраняет информацию о том, успешно ли был совершён платёж и каким способом не сохраняя
данные карт.

[*Ссылка на полное описание требований к дипломному проекту](https://github.com/netology-code/qa-diploma)

### Настройка среды тестирования

1. Docker Desktop [(руководство по установке)](https://github.com/netology-code/aqa-homeworks/blob/master/docker/installation.md)
2. IntelliJ IDEA [(официальная страница с бесплатной версией)](https://www.jetbrains.com/idea/download/#section=windows)
3. Google Chrome

### Запуск тестов

1. Запустить Docker Desktop
2. Открыть новый проект в Intellij IDEA с настройками: Java (corretto-11), Gradle, Groovy
3. Клонировать репозиторий с GitHub набрав в окне терминала команду:

```
    git clone https://github.com/LebedevNikolay/diplom.git
```

3. Для запуска симулятора банковских сервисов с предопределенными ответами в новом окне терминала ввести команду:

```
   docker-compose up
```

5. В новом окне терминала запустить архив Java, содержащий приложение веб-сервиса:

* в MySQL

```
   java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
```

* в Postgres

```
    java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
```

7. В новом окне терминала запустить тестирование командой:

* для MySQL

```
   ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
```

* для Postgres

```
    ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"
```

### Отчёты о проведеном тестировании

* Для просмотра отчета Gradle, необходимо открыть файл index.html в браузере, который находится:

```
    ./build/reports/tests/test/index.html
```

* Для генерации отчёта Allure ввести в терминале команду:

```
    ./gradlew allureServe
```

*по окончании формирования отчет откроется в браузере автоматически

### Отчетная документация

* [План автоматизации тестирования веб-формы сервиса покупки тура](https://github.com/LebedevNikolay/diplom/blob/main/docs/Plan.md)
* [Отчёт по итогам автоматизации](https://github.com/LebedevNikolay/diplom/blob/main/docs/Summary.md)