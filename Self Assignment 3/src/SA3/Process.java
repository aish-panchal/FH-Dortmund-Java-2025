package SA3;

public abstract class Process {
	
	protected String id;
	protected IndustrialOperation[] operations;
	protected static int processCount_MP;
	protected static int processCount_IP;
	protected static int operationCount; 
	
	protected abstract void ProcessPrintInfo();
	
	
}
