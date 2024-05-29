## Dubuisson Samuel

# Research projet : Android App Similarity Study

## Supervised by Romain Rouvoy

### Research subject

The subject is [available here](http://projets-info.univ-lille.fr/master/etu/projects/31e02aad-7795-4194-98b4-f61ff4744123) (written in french).

### Why? 

The goal of the project is to provide a tool that is able to compare two android apps (using their apk). 
The implemented program must provide similarity/distance scores between a group of apps. 
Then, it would be possible to cluster the apps in different groups. But how this project would
be useful ?  
- First, by computing similarity scores between the apps, it would be possible to define what apps in our
phone devices would be similar, in terms of frameworks or languages used for example. That may lead to some
deduplication algorithm to gain memory in a given device: if two apps share the same library version, why would we stock it twice in two different places in the devide?
- Then, by creating some clusters, some groups of apps using the same technologies will be identified. That may lead to many improves on AI trainings: it would be possible
to retrieve all apps using a given framework, to train a generative model on that dataset, and to get some "specialized copilots". That brings concrete and important improvements:
the generative models will be better (because they are specialized), and more frugal (because the training dataset will be reduced).

### How?

The algorithm used to perform such a thing is greatly inspired from a work performed some years earlier in the same research team by Sacha Brisset. Here is
a link to the [concerned paper](https://hal.science/hal-03774245). The authors created an algorithm to compare two web pages, using tree comparisons. They named their algorithm
Similarity-Based Flexible Tree Matching. I tried to adapt their algorithm to compare android apps instead of web pages. This is meaningful because the android apps are easily represented as trees (using the file structure of the apk).

### Implementation

The algorithm is implemented in Java, with the project management tool Maven.  

All the distances computed are between 0 and 1 (0 represents two identical apps, and 1 totally different ones).  

All the generated files can be found on the `results` folder. Here are all the files that may be generated:
- `algorithmResults.ser` that stores the class that represents the computed distances. That may allow to rerun the program, without computing the 
same distances a new time. See the run section for more info.
- `distanceMatrix.csv` that offers a csv file representing the distance matrix. It is not used in the program, but is given as a vision, or exploitation tool.
- `neo4j.txt` that offers a file that represents a [Neo4J](https://neo4j.com/) query file. It permits to create the graph representing all the computed distances
- `cluster.txt` that represents all the computed clusters, according to the given threshold.

#### Requirements 

The project uses Java version 17.

#### How to?

##### Compilation

To compile the project, run: `mvn package`

##### Run

To run the project, run: `java -jar target/PJI-1.jar` with some available options:
- `-neo4j` that generates the neo4j file.
- `-cluster $threshold$` that generates the cluster file. The given threshold value is used to create the cluster. Hence, the value must be between 0 and 1. All
apks in the same cluster cannot have distance superior to this cluster.
- `-already` that skips the distance computation part. It deserializes the `algorithmResults` class that stores the distance.

