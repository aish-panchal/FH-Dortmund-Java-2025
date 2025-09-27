The following README showcases the flow of the entire industrial process: 

The overall industrial process consists of the following: 

1. The Industrial Process: 

A process is started and within one process, 3 different operations are conducted from fetching the raw materials, sending to factory for processing, and then delivering it to customer. Within this process, information like the duration of the process, resource and AGV consumption.

2. The Industrial Operation: 

A base operation class which returns the time, energy, and AVGs consumed during the specific operations. This is extended to three specific operations: 

    1. Fetch materials: 
        This specific operation sends out AGVs to fetch raw materials from a mine and get them back to the warehouse.

    2. Factory: 
        This specific operation sends out AGVs to send the raw materials to the factory for processing and then brings the processed goods back to the warehouse.

    3. Customer: 
        This specific operation sends out AGVs to deliver the processed goods to the customer and then the AGVs return back to the warehouse.

3. The AGV: 

The base class for the AGV wherein a vehicle is created with a set max speed, consumption rate, and always starting at the warehouse with a fully charged battery. 

The speed of the AGV and position changes (and its related calculations for time and battery are done here)



Task distribution: 

All task planning was done in planning meetings to create a layout of the code and how it should be implemented. 

1. Girsy started with the foundations of the code for the overall process. 
2. Aishwary took on completing the AGV code with all calculations and AGV behavior. 
3. Abdel completed the last part of the code with the industrial operations code. 
4. Final run of the code done by Aishwary with this updated README.

Output: 

In the main, we run three industrial processes and output the information of all three processes. Here is the output of the code: 

```
Process 1 needed 256 minutes
Process 1 used 28 kilowatts of energy
Process 1 used 58 AVGs
Process 1 processed 20 tonnes

Process 2 needed 1280 minutes
Process 2 used 139 kilowatts of energy
Process 2 used 58 AVGs
Process 2 processed 100 tonnes

Process 3 needed 2560 minutes
Process 3 used 279 kilowatts of energy
Process 3 used 58 AVGs
Process 3 processed 200 tonnes
```