# Отчет о проведенном тестировании
***
В результате автоматизации тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка согласно [плану тестирования](https://github.com/LebedevNikolay/diplom/blob/main/docs/Plan.md), основанному на предварительном анализе исследовательного тестирования, с применением двух баз данных  MySQL и PostgreSQL  получено:

- [общее колличество тест-кейсов: 76 шт](https://github.com/LebedevNikolay/diplom/blob/main/docs/total%20number%20of%20test%20cases.png)
- [успешных тест-кейсов: 81.57 % (62 тест-кейса)](https://github.com/LebedevNikolay/diplom/blob/main/docs/test.png)
- [не успешных тест-кейсов: 18.42 % (14 тест-кейсов)](https://github.com/LebedevNikolay/diplom/blob/main/docs/failed%20test%20cases.png)

### Oбщие рекомендации по результатам тестирования:

* Оформлены баг-репорты на тесты в статусе Failed в разделе issue проекта на GitHub
* Отсутствие тестовых атрибутов в HTML-элементах ухудшает устойчивость авто-тестов
* Ошибку в полях "Неверный формат" необходимо детализировать
* Продолжительность отправки данных и получения уведомления о результате операции не стабильна и варьируется [от 0 сек до
  22 сек](https://github.com/LebedevNikolay/diplom/blob/main/docs/status%20and%20duration.png)