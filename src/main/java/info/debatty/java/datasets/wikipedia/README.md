# Wikipedia dataset
The dataset consists of a dump of static HTML pages of wikipedia.

## Download
There are actually multiple dumps available, for different dates, and different domains (en.wikipedia.org, fr.wikipedia.org etc.). These may be downloaded at https://dumps.wikimedia.org/other/static_html_dumps/

## Example
```java
import info.debatty.java.datasets.wikipedia.Dataset;
import info.debatty.java.datasets.wikipedia.Page;

public class MyClass {

    public static void main(String[] args) {
         Dataset wiki_dataset = new Dataset("/path/to/wiki/directory);

        for (Page page : wiki_dataset) {
            System.out.println(page.html.substring(0, 100));
            System.out.println(page.getText());
        }
    }
}
```
