# MapfierJ [![](https://jitpack.io/v/erafaelmanuel/mapfierJ.svg)](https://jitpack.io/#erafaelmanuel/mapfierJ)
 A simple mapping library that maps objects to another objects
 
# How to use
* Add a no-arg constructor to your dto class
* It will only map the same field name
* It will skip the field that same name, but not same data type
* Example :

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
Map a simple object :
```js
 SimpleMapper mapper = new SimpleMapper();
 PersonDto person = mapper.set(new Person("Foo", 3)).mapTo(PersonDto.class);
```
<br />

or a collection :
```js
 Transaction transaction = mapper.set(new ArrayList<Animal>());
 List<PersonDto> list = transaction.mapToList(PersonDto.class); // or mapToSet
```
<br />

To be able to map the field that same name, but not same data type follow the example [here](#maptovalueclass), <br/>
or just simply use mapAllTo() :
```js
 PersonDto dto = transaction.mapAllTo(PersonDto.class);
```
<br />

## @Excluded
To exclude a field just add an @Excluded annotation to your dto
```js
  ...
  @Excluded
  private List<Pet> pets = new ArrayList<>();
```
## @MapTo(value=[class])
To map a field or (fields of a field) of an object to a certain class
* value
* collection
* type
```js
  ...
  @MapTo(PetDto.class)
  private Pet pet;
```
Example with a collection field:
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
   compile 'com.github.erafaelmanuel:mapfierJ:v1.0-beta.4.0'
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
    <version>v1.0-beta.4.0</version>
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

