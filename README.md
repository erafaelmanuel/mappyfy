# MapfierJ [![](https://jitpack.io/v/erafaelmanuel/mapfierJ.svg)](https://jitpack.io/#erafaelmanuel/mapfierJ)
 A simple mapping library that maps objects to another objects
 
# How to use
* Add a no-arg constructor to your dto class
* It will only map the same field name
* It will skip the field that same name, but not same data type
* Example :

1) #### SimpleMapper

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
Map a simple object or a collection
```js
 SimpleMapper mapper = new SimpleMapper();
```
```js
 PersonDto person = mapper.set(new Person("Foo", 3)).mapTo(PersonDto.class);
```
```js
 List<PersonDto> list = mapper.set(new ArrayList<Animal>()).mapToList(PersonDto.class); // or mapToSet
```
In order to map a field that same name, but not same data type follow the example [here](#maptovalueclass), or just simply use:
```js
 PersonDto dto = transaction.mapAllTo(PersonDto.class);
```
2) #### ModelMapper
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
Exclude a field without annotation:
```js
 maper.set(dog)
  .excluded("title")
  .excluded("year")
```
Use a converter where mapper can't handle mapping an instance of a source object into a specific destination type.
```js
 maper.set(dog)
  .converter("height", new IntegerStringConverter())
```

3) #### Annotations
## @Excluded
In order to exclude a field just add an @Excluded annotation to your dto
```js
  ...
  @Excluded
  private List<Pet> pets = new ArrayList<>();
```
## @MapTo(value=[class])
In order to map a field or (fields of a field) of an object to a certain class
* value
* collection
* type
```js
  ...
  @MapTo(PetDto.class)
  private Pet pet;
```
```js
  ...
  @MapTo(value = PetDto.class, collection = true, type = List.class)
  private Set<Pet> pets = new HashSet<>(); // map to -> List<PetDto> pets = new ArrayList<>();
```
## @NoRepeat
The class will only map once into a transaction and to it's descendants.
```js
  @NoRepeat
  public class Person { ...
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
   compile 'com.github.erafaelmanuel:mapfierJ:v1.0-beta.4.1'
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
    <version>v1.0-beta.4.1</version>
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

