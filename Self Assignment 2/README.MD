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

---AVG only charges at the Warehouse. Some AVG types will need to recharge because they consumed too much.
