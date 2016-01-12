# Simple text file dataset

This (very simple) parser assumes your dataset consists of a single file, with data consisting of text (String), one item per line.

## Example
```java
import info.debatty.java.datasets.textfile.Dataset;

public class MyClass {

    public static void main(String[] args) {
        Dataset text_dataset = new Dataset("/path/to/file/dataset");

        for (String line : text_dataset) {
            System.out.println(line);
        }
    }
}
```
