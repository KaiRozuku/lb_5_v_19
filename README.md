Task1:
В програмі Task1 використовувались такі методи класу CompletableFuture і виконували наступні дії:
supplyAsync() — створює нове асинхронне завдання і повертає результат.
thenCombine() — комбінує два CompletableFuture після завершення обох завдань.
thenCompose() — виконує додаткову асинхронну операцію, використовуючи результат попереднього.
anyOf() — чекає на завершення хоча б одного з переданих завдань і повертає його результат.
allOf() — чекає на завершення всіх переданих завдань і дає можливість дочекатися їх усіх.

Результат кожного методу виводиться в консоль.


Task2:
В програмі Task2 використовувались такі методи класу CompletableFuture і виконували наступні дії:
supplyAsync() — отримання ціни, рейтингу.
allOf() — використовується для того, щоб чекати, поки всі асинхронні завдання завершаться.
thenRun() — виконується після завершення всіх завдань. У даному випадку, коли всі оцінки завершилися, виводиться початкова оцінка і знаходиться найкраще програмне забезпечення.
thenCombine() — комбінує результати двох асинхронних завдань (ціна та функціональність).
thenCompose() — використовується для створення наступного асинхронного завдання після того, як попереднє завдання завершилось.
anyOf() — отримується програмне забезпечення, яке перше завершило свою оцінку.
