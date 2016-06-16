# SIFT dataset
The Scale Invariant Feature Transform (SIFT) is an algorithm that (as the name says) transforms an image into a set of scale and rotation independant features. This may be roughly compared to vector representation of an image, and is usually used to perform image classification, object recognition or panorama stitching.

![SIFT matching on different pictures of the same building](./sift_matching.png)

https://en.wikipedia.org/wiki/Scale-invariant_feature_transform

This dataset will provide you with a stream of descriptors from a directory containing pictures (one descriptor per picture).

## Download
A lot of picture datasets can be downloaded from the Internet. The most used ones for reaseach are:
* [Caltech 101](http://www.vision.caltech.edu/Image_Datasets/Caltech101/) contains images from 101 categories
* [Caltech 256](http://www.vision.caltech.edu/Image_Datasets/Caltech256/) contains 30607 images from 256 categories
* [MICC-Flickr101](http://www.micc.unifi.it/vim/datasets/micc-flickr-101/) consists of Flick images
* [Oxord buildings dataset](http://www.robots.ox.ac.uk/~vgg/data/oxbuildings/index.html)


## Example
```java
import info.debatty.java.datasets.sift.Dataset;
import info.debatty.java.datasets.sift.SiftDescriptor;
import info.debatty.java.datasets.sift.SimpleSiftSimilarity;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Thibault Debatty
 */
public class SIFT {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Dataset dataset = new Dataset(
                SIFT.class.getClassLoader().getResource("mini-caltech101")
                .getFile());

        LinkedList<SiftDescriptor> images = new LinkedList<SiftDescriptor>();
        for (SiftDescriptor descriptor : dataset) {
            images.add(descriptor);
        }

        Random rand = new Random();
        SiftDescriptor query = images.remove(rand.nextInt(images.size()));
        System.out.println("Query: " + query.getFile());

        SimpleSiftSimilarity sift_similarity = new SimpleSiftSimilarity();
        for (SiftDescriptor candidate : images) {
            System.out.println(candidate.getFile());

            System.out.printf("Similarity: %f\n",
                            sift_similarity.similarity(query, candidate));
        }
    }
}
```

Will display something like

```
Query: /path/to/mini-caltech101/image_0001.jpg
/path/to/mini-caltech101/image_0003.jpg
Similarity: 0.008701
/path/to/mini-caltech101/image_0007.jpg
Similarity: 0.006386
/path/to/mini-caltech101/image_0013.jpg
Similarity: 0.006460
/path/to/mini-caltech101/image_0005.jpg
Similarity: 0.011335
/path/to/mini-caltech101/image_0010.jpg
Similarity: 0.009647
/path/to/mini-caltech101/image_0011.jpg
Similarity: 0.005236
/path/to/mini-caltech101/image_0009.jpg
Similarity: 0.009802
/path/to/mini-caltech101/image_0014.jpg
Similarity: 0.007369
/path/to/mini-caltech101/image_0015.jpg
Similarity: 0.016110
/path/to/mini-caltech101/image_0012.jpg
Similarity: 0.004879
/path/to/mini-caltech101/image_0002.jpg
Similarity: 0.008584
/path/to/mini-caltech101/image_0004.jpg
Similarity: 0.007169
/path/to/mini-caltech101/image_0008.jpg
Similarity: 0.007908
/path/to/mini-caltech101/image_0006.jpg
Similarity: 0.007107
```
