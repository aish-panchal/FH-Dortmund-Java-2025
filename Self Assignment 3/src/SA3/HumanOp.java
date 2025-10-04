package SA3;

public class HumanOp extends Operation {
	private String employees_pos; //employees position
	private float salary; //per hour
	private float HOptime; //working time of the employee per operation
	
	public HumanOp (String pos,float HRsalary, int nohr) {
		employees_pos = pos;
		salary = HRsalary;
		HOptime=8;
		Op_resources=nohr; //number of employees per operation
	}
	
	//setter
	public void operationtime(float time) {
		HOptime=time;
	}
	
	public float operationalCost () {
		return Op_resources*(HOptime*salary); //hourly salary of the employees
	}
	
	//getter
	public float getoptime() {
		return HOptime;
	}
	
	public int getOperationHR() {
		return Op_resources;
	}
	
}
