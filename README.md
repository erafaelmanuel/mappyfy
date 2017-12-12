# mapfierJ [![](https://jitpack.io/v/erafaelmanuel/mapfierJ.svg)](https://jitpack.io/#erafaelmanuel/mapfierJ)
 A simple mapping library that automatically maps objects to each other
 <br />
 <br />
 
# How to use
The class you 'mapTo' requires a contructor with no argument. It is needed when creating an instance of a class using reflection API
```js
 SimpleMapper mapper = new SimpleMapper();
 YourDto dto = mapper.set(new YourEntity()).mapTo(YourDto.class);
```
or
```js
 //You can use hashmap too
 Transaction transaction = new Transaction(new HashMap<String, Object>);
 ModelMapper mapper = new ModelMapper();
 
 mapper.setTransaction(transaction);
 YourDto dto = mapper.getTransaction().mapTo(YourDto.class);
```

# Download
Download the latest jar [here](https://jitpack.io/#erafaelmanuel/mapfierJ) or via:

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
   compile 'com.github.erafaelmanuel:mapfierJ:v1.0-beta.1'
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
    <artifactId>excelj</artifactId>
    <version>v1.0-beta.1</version>
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

