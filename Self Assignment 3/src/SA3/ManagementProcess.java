package SA3;

public class ManagementProcess extends Process {
	
	public ManagementProcess(String processID, int tonnes_processed) {
		id = processID;
		this.ProcessPrintInfo();
		IndustrialProcess first = new IndustrialProcess(tonnes_processed, id);
		first.ProcessPrintInfo();
		processCount_MP++;
		processCount_IP++;
		operationCount += 3;
	}
	
	protected void ProcessPrintInfo() {
		System.out.println("Order " + id + " recevied. Industrial Process " + id + " created!");
		System.out.println("EXECUTING!");
		System.out.println("Industrial Process " + id + " completed. Resource report: \n");
	}
	
	public static int getMPCount() {
		return processCount_MP;
	}
	
	public static int getIPCount() {
		return processCount_IP;
	}
	
	public static int getIOCount() {
		return operationCount;
	}
	
}
