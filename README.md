# TEAM-3-TOURNAMENT
## Эндпойнты (турнир)


#### Создание турнира
###### Http запрос
```POST /tournament```
###### Тело запроса
```TournamentDTO``` - информация о турнире
Пример:
```
{
  "creatorId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "startDateTime": "2022-06-02T17:55:20.393Z",
  "teams": [
    "3fa85f64-5717-4562-b3fc-2c963f66afa6"
  ],
  "tournamentType": "ROUND_ROBIN"
}
```
###### Возвращаемое значение
```UUID id``` - id созданного турнира



#### Получение турнира по id
###### Http запрос
```GET /tournament/{id}```
###### Переменные пути
```UUID id``` - id турнира
###### Возвращаемое значение
```Tournament tournament``` - информация о турнире
Пример:
```
{
  "creatorId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "startDateTime": "2022-06-02T17:56:41.158Z",
  "teams": [
    "3fa85f64-5717-4562-b3fc-2c963f66afa6"
  ],
  "tournamentStatus": "CANCELLED",
  "tournamentType": "ROUND_ROBIN"
}
```



#### Добавление команды в турнир по id
###### Http запрос
```PUT /tournament/{id}/add-team```
###### Переменные пути
```UUID id``` - id турнира
###### Параметры запроса
```UUID teamId``` - id команды



#### Исключение команды из турнира по id
###### Http запрос
```DELETE /tournament/{id}/remove-team```
###### Переменные пути
```UUID id``` - id турнира
###### Параметры запроса
```UUID teamId``` - id команды



#### Досрочное начало турнира
###### Http запрос
```POST /tournament/{id}/start```
###### Переменные пути
```UUID id``` - id турнира



#### Отмена турнира
###### Http запрос
```POST /tournament/{id}/cancel```
###### Переменные пути
```UUID id``` - id турнира


## Эндпойнты (матчи)

#### Получение матча по id
###### Http запрос
```GET /match/id```
###### Переменные пути
```UUID id``` - id матча
###### Возвращаемое значение
```Match match``` - информация о матче



#### Получение результата матча по id матча

###### Http запрос
```GET /by-match/id```
###### Переменные пути
```UUID id``` - id матча
###### Возвращаемое значение
```MatchResult matchResult``` - информация о результате матча



#### Получение результата матча по id матча

###### Http запрос
```GET /by-result/id```
###### Переменные пути
```UUID id``` - id результата
###### Возвращаемое значение
```MatchResult matchResult``` - информация о результате матча


#### Получение списка матчей, проводимых в данном турнире

###### Http запрос
```GET /get-by-tournament-id```
###### Параметры запроса
```UUID id``` - id турнира
###### Возвращаемое значение
```List<Match> matches``` - список матчей



#### Завершение матча

###### Http запрос
```PUT /id/finish```
###### Параметры запроса
```UUID id``` - id матча
###### Тело запроса
```MatchResultDto matchResultDto``` - информация о результате матча



## Сущности
#### Турнир (Tournament)
| Поле | Описание |
| - | - |
| ```UUID id``` | id турнира |
| ```UUID creatorID``` | id создателя |
| ```TournamentType tournamentType``` | тип турнира ```[ ROUND_ROBIN, SINGLE_ELIMINATION ]``` |
| ```List<UUID> teams``` | зарегистрированные команды |
| ```LocalDateTime startDateTime``` | дата и время начала турнира |
| ```TournamentStatus tournamentStatus``` | статус турнира ```[ CANCELLED, FINISHED, ONGOING, PENDING ]``` |


### TournamentDto
| Поле | Описание |
| - | - |
| ```UUID id``` | id турнира |
| ```UUID creatorID``` | id создателя |
| ```TournamentType tournamentType``` | тип турнира ```[ ROUND_ROBIN, SINGLE_ELIMINATION ]``` |
| ```List<UUID> teams``` | зарегистрированные команды |

### Match
| Поле | Описание |
| - | - |
| ```UUID id``` | id матча |
| ```UUID resultId``` | id результата |
| ```int round``` | номер круга |
| ```LocalDateTime startDateTime``` | дата и время начала матча |
| ```UUID team1Id``` | id первой команды |
| ```UUID team2Id``` | id второй команды |

### MatchDto
| Поле | Описание |
| - | - |
| ```int round``` | номер круга |
| ```LocalDateTime startDateTime``` | дата и время начала матча |
| ```UUID team1Id``` | id первой команды |
| ```UUID team2Id``` | id второй команды |


check trello