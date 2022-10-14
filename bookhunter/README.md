### BookHunter

В рамках домашнего задания было развёрнуто приложение с помощью Spring Initializr.

Приложение можно запустить через командную строку, для этого нужно прописать следующую команду в корневой папке проекта:

> gradlew bootRun

Чтобы узнать текущую версию приложения, необходимо обратиться к эндпоинту ```[host]:[port]/actuator/info```

Если приложение запущено локально, достаточно написать в строке браузера ```localhost:8080/actuator/info```, либо
напрямую сделать GET запрос:

> curl -X GET localhost:8080/actuator/info

В качестве ответа приложение вернёт следующий JSON:

```
{
    "build": {
        "artifact": "bookhunter",
        "name": "bookhunter",
        "time": "2022-10-14T02:08:04.078Z",
        "version": "0.0.1-SNAPSHOT",
        "group": "ru.sharipovar"
    }
}
```

### Spring Probes

По данным эндпоинтам дополнительно можно узнать следующие характеристики приложения:

* liveness ```[host]:[port]/system/liveness```
* readiness ```[host]:[port]/system/readiness```
* version ```[host]:[port]/system/version```

Также некоторую информацию можно узнать в Swagger:

```[host]:[port]/swagger-ui/index.html#/```

### Поиск ближайших пользователей

Чтобы найти пользователей поблизости, нужно обратиться по адерсу
```[host]:[port]/user/{id}/nearest```, чтобы найти пользователей по указанному id, либо ```[host]:[port]/user/nearest?latitude=a&longitude=b``` по географическим координатам, где a - широта, b - долгота.

Также в обоих случаях можно указать параметры amount и distance, по умолчанию равные 50 и 100 соответственно (amount - максимальное количество пользователей, distance - максимальное расстояние). 

#### Запрос №1
> http://localhost:8080/user/798d7372-f136-48ba-abbb-bc60ae0782e3/nearest?distance=1100

Ответ приложения:

```
[
    {
        "nick": "layla1986",
        "name": "Layla Will",
        "age": "36",
        "gender": "hidden",
        "location": "79.16326387177045, -12.379713842865321"
    },
    {
        "nick": "dorothy2003",
        "name": "Dorothy Stracke DVM",
        "age": "19",
        "gender": "hidden",
        "location": "84.28981352792343, 27.94729982098645"
    },
    {
        "nick": "maryjane2003",
        "name": "Maryjane Leuschke",
        "age": "19",
        "gender": "hidden",
        "location": "86.50080488175769, -47.51239558901368"
    },
    {
        "nick": "bulah1998",
        "name": "Bulah Hyatt",
        "age": "24",
        "gender": "hidden",
        "location": "75.90870799911723, -11.904487222283146"
    },
    {
        "nick": "raphaelle2003",
        "name": "Raphaelle Graham",
        "age": "19",
        "gender": "hidden",
        "location": "73.49846991463848, 0.11287469954600837"
    }
]
```

#### Запрос №2

> http://localhost:8080/user/nearest?latitude=75.90870799911723&longitude=-11.904487222283146&distance=1199

Ответ приложения:

```
[
    {
        "nick": "bulah1998",
        "name": "Bulah Hyatt",
        "age": "24",
        "gender": "hidden",
        "location": "75.90870799911723, -11.904487222283146"
    },
    {
        "nick": "layla1986",
        "name": "Layla Will",
        "age": "36",
        "gender": "hidden",
        "location": "79.16326387177045, -12.379713842865321"
    },
    {
        "nick": "raphaelle2003",
        "name": "Raphaelle Graham",
        "age": "19",
        "gender": "hidden",
        "location": "73.49846991463848, 0.11287469954600837"
    },
    {
        "nick": "maudie2001",
        "name": "Maudie Greenfelder",
        "age": "21",
        "gender": "hidden",
        "location": "82.61289774506696, -11.04057798622992"
    },
    {
        "nick": "dorothy2003",
        "name": "Dorothy Stracke DVM",
        "age": "19",
        "gender": "hidden",
        "location": "84.28981352792343, 27.94729982098645"
    }
]
```