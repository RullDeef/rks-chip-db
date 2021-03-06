# Создание фреймов для композитной формы Типономинала.

​	Для создания фрейма, который в последствии будет входить в состав формы Типономинала необходимо зайти в `Cuba Studio` на вкладку `Generic UI`, выбрать папку для хранения фрейма, желательно выбрать папку, подходящую тематике фрейма, не создавать в стандартные папки, вроде `screens`.
​	После создания шаблона формы необходимо в контроллере указать, что данный фрейм будет реализовывать интерфейс `ru.spacecorp.mobdekbkp.web.typonominal.TyponominalFrame` и переопределить его методы:
- `initFrame(Typonominal tn)` - вызывается формой когда необходимо обновить информацию на вкладках и фреймах. При переопределении данного метода желательно добавить проверку на то, что передаваемый типономинал не `null` и выбросить исключение, если это так, поскольку отсутствие типономинала в этом методе является нештатным поведением:
```java
@Override
public void initFrame(Typonominal tn) {
    if (tn == null)
        throw new RuntimeException("Unexpectedly typonominal in TnBasicInfo frame is null");
}
```
- `clearFrame(String labelValue)` - вызывается формой когда необходимо очистить вкладки и фреймы. Вызывается формой Типономинала при старте, в методе `ready`. Такое поведение позволяет гарантированно инициализировать все фреймы пустыми данными и указать пользователю на необходимость выбора типономинала из списков. *Данный метод будет вызван при старте формы Типономинала, поэтому необходимо учесть, что при первом вызове метода внутри фрейма типономинал будет пустым объектом, и это штатная ситуация. Один из вариантов обработки, например, осуществлять проверку `if (typonominal == null) return;`*. Данный метод должен не только очищать все внутренние компоненты от данных, но и формировать Label с надписью, которая передана в аргументе метода, например:
```java
vboxMain.removeAll();
Label noDsLabel = componentsFactory.createComponent(Label.class);
noDsLabel.setValue(labelValue);
noDsLabel.setVisible(true);
noDsLabel.setAlignment(Alignment.TOP_CENTER);
vboxMain.add(noDsLabel);
```
​	Где `componentsFactory` это инжектированный компонент платформы CUBA типа `ComponentFactory`. Следует обратить особенное внимание на то, что базовый метод инициализации фрейма `public void init(Map<String, Object> params)` не будет дополнительно вызываться, поэтому все компоненты на фрейме либо должны устанавливаться в режим невидимости, либо формироваться заново при вызове метода `initFrame();`, либо надпись, переданная в аргументе должна быть расположена внутри одного из существующих компонентов, после их очистки. Базовый метод инициализации также можно использовать для создания лейбла, который будет обновляться при вызове метода `clearFrame();`. Все вспомогательные поля и методы фрейма следует создавать с модификатором доступа `private`. Таким образом будет обеспечена инкапсуляция внутренних механизмов фрейма.

​	Далее необходимо добавить фрейм на форму. В XML разметке необходимо в нужном месте добавить тег `frame` с указанием идентификатора и вызываемого окна, например
```xml
<frame id="fBasicInfo" screen="TnBasicInfo" />
```
​	Идентификатор предлагается начинать с префикса `f` или `frame` чтобы поддержать единообразие в коде контроллера Типономинала. В контроллер Типономинала необходимо инжектировать фрейм и добавить его к массиву обновляемых формой фреймов, например:
```java
@Inject private TnBasicInfo fBasicInfo;
private TyponominalFrame[] frames;

@Override
public void ready() {
    frames = new TyponominalFrame[]{ ..., ..., ..., fBasicInfo };
}
```
​	Добавление в массив необходимо осуществить именно в методе `ready`, в противном случае в массив будут добавлены `null` значения.
	Таким образом будет обеспечено единообразное поведение всех фреймов, поскольку форма будет в нужные моменты вызывать необходимые ей методы, проходя по всем элементам массива `frames`.
​	Примечания:
- Если на создаваемом фрейме есть необходимость автоматического обновления из каких либо слушателей изменений сущности, следует делать это вызывая метод `initFrame()`, например:
```java
ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn) );
```
где:
- `ed` это идентификатор открытого окна редактировани сущности;
- `thisframe` это идентификатор обновляемого фрейма, сохранённый в методе `public void init(Map<String, Object> params)` простым присваиванием `thisframe = this;`
- `tn` это переменная, содержащая ссылку на переданный во фрейм типономинал.