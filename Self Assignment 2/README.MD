BASIC README

1st IndustrialProcess:

1- Fetch raw materials
2 -Delivery to Factory
3 - From Factory to Customer


AGV
constructors:
id (set value)
mxspeed (set value)
comsuptionrate
chargetime
battery load: initially 100%
Position (0,0)
distance from Warehouse to Factory (set value)

1st - Fetch raw material
SetPosition (to destination)
Time=Distance(destination) & MaxSpeed
EnergyConsumed= ComsuptionRate & time
BatteryLoad= get once it get to the position

--- We have different type of AVG per IOperation, which will consume different amounts of energy and time. Some will need to recharge, which will affect the duration of the operation
Some formulas:

Distance btw (x1,y1) and (x2,y2) (Assuming here that the AGV travels in X and Y only)
d=[abs(x2-x1) + abs(y2-y1)]

Time with distance and speed
t= d/V

Energy = time and consumption rate
E=P*t


