Project description: 

- A system to store raw materials as well as processed goods.​
- Fully automated for storage handling and moving of materials within the warehouse.​
- The key components of the system are the task management system, the storage management system and a human machine interface to create and manage the orders.​
- The system allows to create orders, view the logs of the different systems, view status of vehicles, charging station and inventory in the GUI.​
- This would fit a raw material processing industry, like raw iron ore processing​

Project screencast: 
https://drive.google.com/file/d/1dz7-kFBFue5I1ZApXy2fqdgLDTrtPB9g/view?usp=sharing

Team description: 

- Smart Storage Management System: Abdelrahman Abdin​
- Charging Stations: Girsy Guzmán and Abdin​
- Task management system: Girsy Guzmán​
- Human Machine Interface: Aishwary Panchal​
- File logging operations: Aishwary Panchal​

Special requirements: 

- To ensure the system works perfectly, the material first needs to be sent to the warehouse for storage using the "toWarehouse" order type. Once inventory is updated, the other operations can be carried out​
- Subsequent operations like "toFactory" and "toDelivery" need to be executed with weight quantities lesser than the weights present in the inventory ​
- To run unit tests, Junit must be added to the classpath in the build path of the project​
- To run the GUI, Javafx must be added to the project. There are 2 main ways of doing this: ​
  - Add Javafx libraries manually to the classpath in the build path, OR​
  - Make use of a JDK that has Javafx already bundled with it​
