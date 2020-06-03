# Traveling Salesman Problem (TSP) - Improved Nearest Neighbour Java
Use of an improved nearest neighbour algorithm to determine the shortest path for the salesman to visit all cities.


## Properties of Dataset
* 8 datasets with different combinations of city coordinates and varied number of cities being accounted.
* Cities do not have a particular order.

## Algorithm
- Mixture of Nearest Neighbour algorithm and Mexican Hat (Used to improve the performance of the NN algorithm) algorithm to determine the different order of cities which a traveling salesman needs to go.
- Use of Euclidean distance to calculate unit distance between the different cities.
- The algorithm separated the different cities into 3 classes A, B, and C based on the distance between the inital starting city and the other cities.
- The cities were classified using various combinations of inter boundary distances between the starting city and the furthest city from that city.

## Results
- Algorithm is able to obtain optimal results.
- All datasets are processed under a minute, which shows the algorithm was very fast.


# Take Aways
* Nearest Neighbour and Mexican Hat algorithms are suitable in applications were a reasonable level of accuracy is to be achieve at a very fast speed.
* The algorithm can be further improved by manipulating the distance metrics used and by incorporating supervised learning methods such as neural nets. This is commonly known as the Self-Organising Maps algorithm but would in term require more processing resources.

<b>Please refer to `TSP Rémi Wong.pdf` documentation to read more on the Traveling Salesman Problem completed</b>
