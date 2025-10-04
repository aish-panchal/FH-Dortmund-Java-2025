package SA3;

/* All industrial operations are conform of Transport operations and Human operations 
 * Transport operation cost energy (kwh consumed), while Human operations cost an hourly salary
 * Both operations consume time
 * */

abstract class Operation {
	
	protected double Op_time;
	protected int Op_resources;
	
	abstract float operationalCost();
	abstract float getoptime();
	
}

