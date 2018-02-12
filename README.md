[![](https://jitpack.io/v/erafaelmanuel/mapfierJ.svg)](https://jitpack.io/#erafaelmanuel/mapfierJ)

### Overview
A Reflection-based mappers library that maps objects to another objects. It can be very useful when developing multi-layered applications.
 
* Map complex and deeply structured objects
* Create converters for complete control over the mapping of a specific set of objects anywhere in the object graph
* Easy to use

### Download
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
    <version>v1.0-beta.6.3</version>
  </dependency>
</dependencies>
```

### Brief Example
Suppose we have some instances of class Person that we’d like to map to instances of another class PersonDto.
```java
 public class Person {
  String name;
  int age;
 }
 
 public class PersonDto {
  String fullname;
  int age;
 }
```
```java
  Mapper mapper = new Mapper();
  
  Person person = new Person("Foo", 3);
  PersonDto dto = mapper.set(person).field("name", "fullname").mapTo(PersonDto.class);
 ```
 ### Converters and Custom Converters
Use converters where the mapper can't handle mapping an instance of a source object into a specific destination type.
```java
 public class Dog {
  String name;
  Date bod;
 }
 
 public class Pet {
  String name;
  int age;
 }
```
```java
  Mapper mapper = new Mapper();
  mapper.getConverter().register(new MyConverter());
``` 
```java
  Dog dog = new Dog("Bar", new Date());
  Pet pet = mapper.set(dog)
                  .field("bod", "age")
                  .convertFieldToType("age", Integer.class)
                  .mapTo(Pet.class);
 ```
In order to create your own custom converter you need to extends the TypeConverterAdapter and add the two generic type
```java
 @TypeConverter
 public class MyConverter extends TypeConverterAdapter<Integer, Date> {
 
  @Override
  public Date convertTo(Long o) {
     // implementations
  }

  @Override
  public Long convertFrom(Date o) {
     // implementations
  }
 }
```

