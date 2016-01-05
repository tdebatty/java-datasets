# DBLP dataset

http://dblp.uni-trier.de/

The dblp computer science bibliography is an on-line reference for bibliographic information on major computer science publications. dblp currently indexes over 2.6 million publications, published by more than 1.4 million authors.

## Download
The whole dblp dataset is available as one big XML file, which you can download at http://dblp.uni-trier.de/xml/

## Example
```java
import info.debatty.java.datasets.dblp.*;

public class DBLP {

    public static void main(String[] args) {
        Dataset dblp_dataset = new Dataset(
                DBLP.class.getClassLoader().getResource("mini-dblp.xml")
                        .getFile());

        for (Publication publication : dblp_dataset) {
            System.out.println(publication.type + " : " + publication.title);
        }
    }
}
```