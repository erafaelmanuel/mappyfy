[![](https://jitpack.io/v/erafaelmanuel/mapfierJ.svg)](https://jitpack.io/#erafaelmanuel/mapfierJ)

# Overview
#### MapfierJ !!!
A Reflection-based mappers library that maps objects to another objects. It can be very useful when developing multi-layered applications.
 
* Map complex and deeply structured objects
* Create converters for complete control over the mapping of a specific set of objects anywhere in the object graph
* Easy to use

# Usage

### [SimpleMapper]()

Suppose we have some instances of class Person that we’d like to map to instances of another class PersonDto.
```java
 public class Person {
  @FieldName("fullname")
  String name;
  int age;
 }
 
 public class PersonDto {
  String fullname;
  int age;
 }
```
In order to map between these classes, we’ll need to first create a mapper.
```java
 SimpleMapper mapper = new SimpleMapper();
```
By default it will only map the same field name and data type
```java
 Person person = new Person("Foo", 3);
 PersonDto dto = mapper.set(person).mapTo(PersonDto.class);
```
```java
 Collection<Person> persons = new HashSet<>();
 List<PersonDto> personDtos = mapper.set(persons).mapToList(PersonDto.class);
```
In order to map one or more fields to a different type. Follow the example [here](#maptovalueclass), or just simply use:
```java
 PersonDto dto = mapper.set(person).mapAllTo(PersonDto.class);
```

### [ModelMapper]()
Basically, to use ModelMapper first instantiate it with or without base package of your custom converters
```java
 ModelMapper mapper = new ModelMapper();
```
```java
 ModelMapper mapper = new ModelMaper("my.package");
```
In order to map different field between the two class:
```java
 mapper.set(dog)
  .field("name", "title")
  .field("age", "year")
```
To explicitly exclude a field from mapping:
```java
 mapper.set(dog).exclude("title")
```
To explicitly exclude a field (of object within a collection) from mapping:
```java
 mapper.set(dogs).excludeAll("title")
```
Use a converters out of the box (or your own [custom](#custom-typeconverter) converter) where the mapper can't handle mapping an instance of a source object into a specific destination type.
 
```java
 mapper.set(dog).convertFieldByConverter("height", new IntegerStringConverter())
```
It'll search the converter that match the type and automatically convert it to the specific type. By default it will only look for built-in converters or the classes inside the packages that you've scanned.
```java
 mapper.set(dog).convertFieldToType("height", String.class)
```

### [Annotations]()
#### @ConvertTo(value=[class])
Convert a field to a certain type
* value
* converter
* scanPackages
```java
  @ConvertTo(Integer.class)
  private Pet pet;

  @ConvertTo(value = Pet.class, converter = IntegerPetConverter.class)
  private int petId;
```
#### @Excluded
Exclude the field of your dto class
```java
  @Excluded
  private List<PetDto> pets = new ArrayList<>();
```
#### @FieldName
The field will map as the value of the annotation
```java
  @FieldName("petDto")
  private List<Pet> pets = new ArrayList<>();
```
#### @MapTo(value=[class])
Map one or more fields to a different class
* value
* collection
* type
```java
  @MapTo(PetDto.class)
  private Pet pet;

  @MapTo(value = PetDto.class, collection = true)
  private Set<Pet> pets = new HashSet<>();
```
#### @NoRepeat
The mapper will ignore the recursive instances of a class
```java
  @NoRepeat
  public class Person { ...
```

### [Custom TypeConverter]()
In order to create your own custom converter you need to extends the TypeConverterAdapter and add the two generic type
```java
  @TypeConverter
  public class MyConverter extends TypeConverterAdapter<Long, Dog> { ...
```
You have to override and write your own implementations
```java 
  @Override
  public Dog convertTo(Long id) {
     return dogRepository.findById(id);
  }

  @Override
  public Long convertFrom(Dog dog) {
     return dog.getId();
  }
```
And that's all, you just need to use your custom converter:
```java
  public class Person {
    String name;
    Long dogId;
  }
  
  public class PersonDto {
    String name;
    Dog dog;
  }
```
```java
  ModelMappr mapper = new ModelMapper("my.package");
  
  mapper.set(person)
    .field("dogId", "dog")
    .convertFieldToType("dog", Dog.class)
    .getTransaction().mapTo(PersonDto.class);
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
   compile 'com.github.erafaelmanuel:mapfierJ:v1.0-beta.6.0'
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
    <version>v1.0-beta.6.0</version>
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

