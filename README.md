# Text Analytics (Java)

## Environment
```
$ mkdir bin
$ export CLASSPATH=bin/calctfidf/:bin/k_means/:bin/knn/:bin/TextAnalytics:bin:lib/JavaML/:lib/stanford-corenlp-full/:lib/Jama/*:.
```

## TF-IDF / Topics per Folder
### Run
```
$ javac -d bin -sourcepath src src/calctfidf/Main.java && java calctfidf.Main Data/DataSet
```

## K-Means Clustering
### Run
```
$ javac -d bin -sourcepath src src/k_means/Main.java && java k_means.Main
```

## K-nearest Neighbour
### Run
```
$ javac -d bin -sourcepath src src/knn/Main.java && java knn.Main Data/TestDataUnlabeled
```

