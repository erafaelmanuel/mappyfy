[![](https://jitpack.io/v/erafaelmanuel/mapfierj.svg)](https://jitpack.io/#erafaelmanuel/mappyfy)

### Overview
A Reflection-based mappers library that maps objects to another objects. It can be very useful when developing multi-layered applications.
 
* Map complex and deeply structured objects
* Create converters for complete control over the mapping of a specific set of objects anywhere in the object graph
* Easy-to-use

### Download
```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```

```xml
<dependency>
  <groupId>com.github.erafaelmanuel</groupId>
  <artifactId>mappyfy</artifactId>
  <version>1.0.5</version>
</dependency>
```

### How to use
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
  final Mapper mapper = new Mapper();
```
```java
  Person person = new Person("Foo", 3);
  PersonDto dto = mapper.from(person).bind("name", "fullname").toInstanceOf(PersonDto.class);
 ```
 ### TypeConverter
In order to create your own custom converter you need to extends the TypeConverter and add the two generic type.
```java
 public class MyCustomTypeConverter extends TypeConverter<Integer, Date> {
 
   @Override
   public Date convertTo(Long o) {
      // implementation
   }

   @Override
   public Long convertFrom(Date o) {
      // implementation
   }
}   
```
Use converters where the mapper can't handle mapping an instance of a source object into a specific destination type. Example:
```java
 public class Bar {
  String name;
  Date date;
 }
 
 public class Foo {
  String name;
  String date;
 }
```
```java
  final Mapper mapper = new Mapper();
``` 
```java
  Bar bar = new Bar("I love PHP!", new Date());
  Foo foo = mapper
               .from(bar)
               .parseFieldWith("date", new MyCustomTypeConverter())
               .toInstanceOf(Foo.class);
 ```
