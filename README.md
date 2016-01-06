# java-datasets
Java library for parsing various datasets:
* [DBLP dataset](./src/main/java/info/debatty/java/datasets/dblp/)
* [Reuters-21578 dataset](./src/main/java/info/debatty/java/datasets/reuters/)
* [Text file dataset](./src/main/java/info/debatty/java/datasets/textfile/)
* [ENRON email dataset](./src/main/java/info/debatty/java/datasets/enron/)
* [Wikipedia web pages](./src/main/java/info/debatty/java/datasets/wikipedia/)

These parsers are implemented using an iterator, which make them suitable for processing large datasets. You may also use it to process subparts of the dataset, as you can process items "on-the-fly", without reading the entire data.