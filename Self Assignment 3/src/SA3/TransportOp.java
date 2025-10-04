package SA3;


public class TransportOp extends Operation {
	private float TransOptime;
	private int Transamount; //number of AVGS required for the operation
	private float TransOpCost; //cost of energy consumed
	private float energycostRate; 
	private float Openergy; 

	public TransportOp (avg[] opavg,float consump){
		energycostRate = (float)0.75; //Euros per Kwh
		Transamount = opavg.length;
		TransOpCost=0;
		Openergy = consump;
		TransOptime = 0;
	}
	
	//setter
	public void operationtime(float time){
		TransOptime=time;
	}
	
	//getter
	public float getoptime(){
		return TransOptime;
	}
	
	public float operationalCost (){
		return energycostRate*Openergy; //cost of electricity per operation
	}
	
}
