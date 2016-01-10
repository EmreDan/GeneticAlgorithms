# Two different Genetic Algorithm Applications

This project consists of 2 different Genetic Algorithms applications. 
One for the Asymmetric Travelling Salesman Problem and the other is Function Minimization with binary values. 

#Asymmetric Travelling Salesman Problem
The map for the consists of 10 cities. Purpose of the application is to find the minimum total cost(time). 
Every population consists of 8 chromosomes with randomly assigned initial values.
For each chromosome, the fitness function value is the sum of the costs within a whole route. 
-> Please check initilize_map function to see default route costs.

Methodologies used in this Genetich Algorithm application:
*Tournament Selection method is used to select parents. Tournament size can be changed as long as not bigger than population size.
*Order One crossover method is used for Chromosome Crossover. 
*For mutation, Insert Mutation is used.

Best results were achieved with Mutation rate of 0.2 and Tournament size of 3 

#Function Fitness Minimization

The population consists of 6 chromosomes.
Each chromosome consists of 8 bits (boolean values) and since it is signed values the variance is -128 <-> 127.
The fitness value is result of the function F(x) = x^2 + 16*x

Methodologies used in this Genetich Algorithm application:
*Tournament Selection method with Elitism is used to select parents. Tournament size can be changed as long as not bigger than population size.
-->Elitism with 2 parent is the default. To change it see function "Replace_Worst_Two" 
*Single point crossover method is used for Chromosome Crossover. 
*For mutation, Binary (swap) Mutation is used. With the rate of 0.2
