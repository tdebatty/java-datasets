# java-datasets
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/info.debatty/java-datasets/badge.svg)](https://maven-badges.herokuapp.com/maven-central/info.debatty/java-datasets) [![API Documentation](http://api123.web-d.be/api123-head.svg)](http://api123.web-d.be/api/java-datasets/head/index.html)

Java library for parsing various datasets:
* [DBLP dataset](./src/main/java/info/debatty/java/datasets/dblp/)
* [Reuters-21578 dataset](./src/main/java/info/debatty/java/datasets/reuters/)
* [Text file dataset](./src/main/java/info/debatty/java/datasets/textfile/)
* [ENRON email dataset](./src/main/java/info/debatty/java/datasets/enron/) (TODO)
* [Wikipedia web pages](./src/main/java/info/debatty/java/datasets/wikipedia/)
* [Synthetic gaussian mixture](./src/main/java/info/debatty/java/datasets/gaussian/)

These parsers are implemented using an iterator, which make them suitable for processing large datasets. You may also use it to process subparts of the dataset, as you can process items "on-the-fly", without reading the entire data.

## Download
Using maven:
```
<dependency>
    <groupId>info.debatty</groupId>
    <artifactId>java-datasets</artifactId>
    <version>RELEASE</version>
</dependency>
```

Or check the [GitHub releases](https://github.com/tdebatty/java-datasets/releases).

## Quickstart

Usually, you simply have to:
- Initialize the Dataset object, using the path to the folder or directory containing the data
- Iterate over the dataset items as long as you want...

```java
import info.debatty.java.datasets.reuters.*;

public class MyClass {

    public static void main(String[] args) {
        // We will use reuters news dataset
        Dataset reuters_dataset = new Dataset("/path/to/reuters/folder");

		// Iterate over news
        for (News news : reuters_dataset) {
            System.out.println(news.title);
        }
    }
}
```

For the other datasets, check [the examples](./src/main/java/info/debatty/java/datasets/examples), or [the documentation](http://api123.web-d.be/api/java-datasets/head/index.html).