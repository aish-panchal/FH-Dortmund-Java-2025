
For Self Assignment 3, the division of work was done as follows: 

1. Creation of Abstract Class Process and relevant sub classes - Aishwary Panchal
2. Creation of Abstract Class Operation and relevant sub classes - Girzy Guzman
3. Creation of Abstract Class Resource and relevant sub classes - Abdel Rehman


The major components of the code are same as Self Assignment 2 but the following newer components were added: 

1. The abstract class Process introduces a new management process that is responsible for receiving the order and creates an industrial process for the same.

2. The abstract class Operation introduces a transport and human operation. The relevant costs as well as the resources for these operations are calculated here. The industrial operation class is the same as before responsible for the actual industrial operations. 

3. The abstract class Resource introduces human and non human resources. Under non human resources, there are material, software, and hardware resources. The unchanged AVG class remains under hardware resources. 


Output:

The resource reports are printed when running the main showing the resources consumed, its related costs and information about the processes. Here is what it looks like: 

```
Order 1 recevied. Industrial Process 1 created!
EXECUTING!
Industrial Process 1 completed. Resource report: 

Process 1 needed 286 hours
Process 1 used 28 kilowatts of energy
Process 1 used 58 AVGs
Process 1 processed 20 tonnes

Process 1 costs 581.1125 Euros

Order 2 recevied. Industrial Process 2 created!
EXECUTING!
Industrial Process 2 completed. Resource report: 

Process 2 needed 1332 hours
Process 2 used 139 kilowatts of energy
Process 2 used 58 AVGs
Process 2 processed 100 tonnes

Process 2 costs 665.11255 Euros

Order 3 recevied. Industrial Process 3 created!
EXECUTING!
Industrial Process 3 completed. Resource report: 

Process 3 needed 2640 hours
Process 3 used 279 kilowatts of energy
Process 3 used 58 AVGs
Process 3 processed 200 tonnes

Process 3 costs 770.11255 Euros

3 Management Process created.
3 Industrial Process created consisting of 9 Industrial Operations
```
