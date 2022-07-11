# Модуль "Отзывы"

## Установка

### Установка локально

`**gradlew insall**`

### Загрузка в удаленный Nexus

`**gradlew clean uploadArchives**`

## Подключение к проекту

Модуль загружен в изолированный репозиторий:

http://1.0.0.137:8010/nexus/content/groups/cuba-group/

### Подключение с использование Studio:

*Project Properties -> Edit -> App components -> Custom components -> Add -> Reviews*

`com.company.reviews:reviews-global:<current_version>`

## Использование

Для того, чтобы использовать модуль для конкретного объекта необходимо включить его как *frame* в экран объекта.

`Reviewsframe`

### Сервисы

Модуль имеет сервисы, автоматизирующие некторые типовые задачи.

#### calculateRating - получение средней оценки

Примеры использования:

```java
public class TestEdit extends AbstractEditor<Test> {

    @Inject
    private ReviewService reviewService;

    @Override
    protected boolean preCommit() {
        getItem().setGrade(reviewService.calculateRating(getItem()));
        return true;
    }

}

```

```java
public class TestBrowse extends AbstractLookup {

    @Inject
    private GroupDatasource<Test, UUID> testsDs;

    @Inject
    private ReviewService reviewService;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        testsDs.addItemChangeListener(e -> {
            showNotification(reviewService.calculateRating(e.getItem()).toString());
        });
    }

}
```

#### getAverageRating - получение хеш-таблицы с количеством каждых оценок

Пример использования:

 ```java
 Map<Integer, Integer> map = reviewService.getAverageRating(entity);
 ```

### Экраны

#### ratingFrame - отображение оценки в виде диаграммы

![График](docs/images/rating.png)

 Для установки значения используется метод setData

 Пример использования:

 ```java
  @Inject Ratingframe ratingFrame;

      @Override
      public void ready() {
          super.ready();
          testsDs.addItemChangeListener(e -> {
              ratingFrame.setData(testsDs.getItem());
          });
      }
 ```

# redmie wiki
Модуль "Отзывы"
Актуальная информация находится в readme репозитория
http://1.0.0.137:3033/l.terekhov/Reviews

Редактировать эту секцию
Модерация отзывов
Модуль поддерживает модерирование отзывов с помощью служебных экранов ModerationProperty, ReviewModerator, ReviewReport.

Настройка параметров модерирования обеспечивается с помощью экрана ModerationProperty, данный экран позволяет задать сущности, которые будут модерироваться, а также их отдельные экземпляры. Экран позволяет задать модераторов по-умолчанию для всех экземпляров сущности, а также отдельных модераторов для отдельных экземпляров.

Для настройки модерации необходимо создать новую запись ModerationProperty, в которой указать объекты для модерации, тип модерации (пре-, пост-, все типы) и модераторов.

Редактировать эту секцию
Логика работы ModerationProperty
Если для сущности не задано модераторов по-умолчанию, и не выбраны отдельные экземпляры со своими модераторами, то модерация будет функционировать, но не будет пользователей, которые могут ее производить.
Если не выбраны отдельные экземпляры сущности для модерации, то модерация функционирует для всех экземпляров, ее могут выполнять модераторы по-умолчанию.
Если выбраны отдельные экземпляры сущности для модерации, то модерация функционирует только для этих экземпляров, ее могут выполнять модераторы заданные для экземпляров соответственно или модераторы по-умолчанию для всей сущности, если модераторов для отдельного экземпляра не задано.
Включенная премодерация скрывает сообщение и не учитывает оценку, пока отзыв не пройдет проверку модератором.
Включенная постмодерация позволяет модератору редактировать содержание отзыва (после модерации соответствующее сообщение будет видно всем пользователям с указанием причины), оценка не может быть изменена.
Автор отзыва может редактировать модерированный отзыв, если при этом включена премодерация, то отзыв будет скрыт до проверки модератором, если премодерация выключена, то модерированный отзыв будет заменен на новый отзыв автора.
Тип модерации "Все типы" включает премодерацию и постмодерацию одновременно.
Редактировать эту секцию
Модерирование отзывов
Модерирование отзывов происходит на экране ReviewModerator. Экран содержит две вкладки: "Премодерация" и "Постмодерация", на которых отображаются отзывы, если включен соответствующий тип модерации. Для каждого пользователя отображаются только те отзывы, для которых он назначен модератором. Модератор может подтвердить или скрыть отзыв при премодерации и редактировать или удалить отзыв при постмодерации. Если отзыв будет скрыт при премодерации, то он не будет виден пользователям на стандартных экранах, но останется в базе и на панели модерации. Если отзыв будет удален при постмодерации, то отзыв будет отображаться для пользователей как удаленный. Оценка отзыва учитывается только для подтвержденных (прошедших премодерацию) и модерированных (редактированных модератором после подтверждения) отзывов.

Редактировать эту секцию
Отчеты по отзывам
Для просмотра отчета по отзывам следует использовать экран ReviewReport. Экран содержит две кнопки для отображения отклоненных отзывов и отзывов, не прошедших модерацию соответственно. При открытии экрана по-умолчанию отображаются отклоненные отзывы.

Редактировать эту секцию
Разграничение доступа к функциям модерирования
Все функции модерирования находятся на описанных выше экранах. Необходимо ввести специальные роли модераторов в приложение, ограничивающую доступ к ним. При этом распределение отзывов по модераторам осуществляется в соответствии с внутренней логикой работы модуля (с помощью ModerationProperty). Необходимо назначить отдельных пользователей для настройки модерации (ModerationProperty) и непосредственно модерации (ReviewModerator). Пользователи, заданные в модуле как модераторы, должны иметь роль для доступа к экранам модерации. Экран ReviewReport несет только информационные функции, доступ к нему может быть предоставлен контролирующему пользователю.