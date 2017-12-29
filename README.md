[![](https://jitpack.io/v/erafaelmanuel/mapfierJ.svg)](https://jitpack.io/#erafaelmanuel/mapfierJ)

# Overview
#### MapfierJ !!!
A simple mapping library that maps objects to another objects
 
* Reflection-based mappers
* By default it will only map the same field name
* Skip the field that same name, but different data type

# How to use

### [SimpleMapper]()

Person.java
```js
 public class Person {
  private String name;
  private int age;
 
  //getter and setter
 }
```

PersonDto.java
```js
 public class PersonDto {
  private String name;
  private int age;
  
  public PersonDto() {}
  
  //getter and setter
 }
```
Create a SimpleMapper
```js
 SimpleMapper mapper = new SimpleMapper();
```
Add your object by calling the set method and that will give you a Transaction.
```js
 Transaction transaction = mapper.set(new Person("Foo", 3));
```
* getMap
* mapTo
* mapToList
* mapToSet
* mapAllTo
```js
 PersonDto person = mapper.set(new Person("Foo", 3)).mapTo(PersonDto.class);
```
```js
 List<PersonDto> personList = mapper.set(new ArrayList<Animal>()).mapToList(PersonDto.class);
```
In order to map one or more fields to a different class. Follow the example [here](#maptovalueclass), or just simply use 'mapAllTo'
```js
 PersonDto dto = mapper.set(person).mapAllTo(PersonDto.class);
```

### [ModelMapper]()
```js
 ModelMapper mapper = new ModelMapepr();
```
```js
 Dog dog = new Dog();
```
In order to map different field between the two class:
```js
 maper.set(dog)
  .field("name", "title")
  .field("age", "year")
```
To ignore the field in both classes:
```js
 maper.set(dog).exclude("title") ...
```
To ignore the field of object inside a collection:
```js
 maper.set(dogs).excludeAll("title") ...
```
Use a converters out of the box (or your own [custom](##custom-typeconverter) converter) where the mapper can't handle mapping an instance of a source object into a specific destination type.
 
```js
 maper.set(dog)
  .converter("height", new IntegerStringConverter())
```

### [Annotations]()
#### @Excluded
Exclude the field of your dto class
```js
  @Excluded
  private List<PetDto> pets = new ArrayList<>();
```
#### @FieldName
The field will map as the value of the annotation
```js
  @FieldName("petDto")
  private List<Pet> pets = new ArrayList<>();
```
#### @MapTo(value=[class])
Map one or more fields to a different class
* value
* collection
* type
```js
  @MapTo(PetDto.class)
  private Pet pet;
```
```js
  @MapTo(value = PetDto.class, collection = true, type = List.class)
  private Set<Pet> pets = new HashSet<>(); // map to -> List<PetDto> pets = new ArrayList<>();
```
#### @NoRepeat
The mapper will ignore the recursive instances of a class
```js
  @NoRepeat
  public class Person { ...
```

### [Custom TypeConverter]()
In order to create your own custom converter you need to extends the TypeConverterAdapter and add the two generic type
```js
  @TypeConverter
  public class MyConverter extends TypeConverterAdapter<Long, Dog> { ...
```
You have to override and write your own implementations
```js
  @Override
  public Dog convertTo(Long id) {
     return dogRepository.findById(id);
  }
```
```js
  @Override
  public Long convertFrom(Dog dog) {
     return dog.getId();
  }
```
```js
  @Override
  public Object convert(Object o) {
     if(o instanceof Long)
       return convertTo(o);
     else
       return convertFrom(o);
  }
```
And that all, you just need to use your custom converter:
```js
  public class Person {
    String name;
    Long dogId;
  }
```
```js
  public class PersonDto {
    String name;
    Dog dog;
  }
```
```js
  mapper.set(person)
    .field("dogId", "dog")
    .converter("dog", new MyConverter())
    .getTransaction().mapAllTo(PersonDto.class);
```

# Download
Download the latest jar [here](https://github.com/erafaelmanuel/mapfierJ/archive/v1.0-beta.3.zip) or via:

* Gradle

```js
allprojects {
  repositories {

    maven { url 'https://jitpack.io' }
  }
}
```

```js
dependencies {
   compile 'com.github.erafaelmanuel:mapfierJ:v1.0-beta.4.2a'
}
```

* Maven

```html
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```

```html
<dependencies>
  <dependency>
    <groupId>com.github.erafaelmanuel</groupId>
    <artifactId>mapfierJ</artifactId>
    <version>v1.0-beta.4.2a</version>
  </dependency>
</dependencies>
```

# License

```
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

