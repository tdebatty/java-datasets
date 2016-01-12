# Reuters-21578 dataset
https://archive.ics.uci.edu/ml/datasets/Reuters-21578+Text+Categorization+Collection

The documents in the Reuters-21578 collection appeared on the Reuters newswire in 1987. It contains a total of 21578 news.

## Download
The dataset consists of a folder containing multiple .sgm files, and can be downloaded at https://archive.ics.uci.edu/ml/machine-learning-databases/reuters21578-mld/

## Example
```java
import info.debatty.java.datasets.reuters.*;

public class MyClass {

    public static void main(String[] args) {
        Dataset reuters_dataset = new Dataset("/path/to/reuters/folder");

        for (News news : reuters_dataset) {
            System.out.println(news.title);
        }
    }
}
```
