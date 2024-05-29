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

#### Requirements 