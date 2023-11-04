# Multithreading-in-Java
## Performance Comparison Of A Single-Threaded Program With A Multithreaded Program 
This exercise involves running a program that uses numerical integration to calculate the elapsed time taken by a program using one processor versus several threads. The goal of the exercise is to compare the performance of a single-threaded program with a multithreaded program and analyze the impact of using multiple threads on the elapsed time and CPU usage. The results are shown below

## Steps Taken
1. NumInt.java file is compiled using the javac command.
  ```javac NumInt.java```
![javac NumInt code](https://github.com/oriyomibadmus/Multithreading-in-Java/assets/20837551/b29df220-c723-4416-8197-fbf2c9762de9)

2. Elapsed time is noted after running the java command.
   ```java NumInt```
3. CPU usage is observed via the Task Manager
   ![CPU usage](https://github.com/oriyomibadmus/Multithreading-in-Java/assets/20837551/f314ac30-f08d-4824-b12b-e56c1dc73604)

4. Noting the elapsed time, we compared the NumInt.java program with the multithreaded version (NumIntThreaded.java).
   
5. Compiled and ran the multithreaded program multiple times with a varying number of threads that matches the number of processors, while noting the elapsed time and CPU usage.
   ![number of threads](https://github.com/oriyomibadmus/Multithreading-in-Java/assets/20837551/741df379-b128-4c53-b9a4-ba395e18179d)

6. Created a graph to visualize the relationship between the number of threads and elapsed time.
   <img width="1033" alt="Graph" src="https://github.com/oriyomibadmus/Multithreading-in-Java/assets/20837551/f02c5732-6e5e-402c-8b9d-c7ed1b0d63c5">

## Discussion About The Observed Relationship Between The Number of Threads And Elapsed Time.

As observed in the graph, the elapsed time decreases as the number of threads increases, indicating that the application is benefiting from parallelization and efficiently utilizing the available resources. The burden is distributed among numerous threads as the number of threads increases, allowing them to perform concurrently and potentially reducing overall execution time.
