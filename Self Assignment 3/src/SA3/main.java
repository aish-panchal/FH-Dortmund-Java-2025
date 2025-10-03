package SA3;

/*This code presents Industrial Processes, showing the duration of the process,
 * the amount of AVGs it requires and its energy consumption.
 */
public class main {
    public static void main (String[] args)
    {
    	
    ManagementProcess MP_first = new ManagementProcess("1", 20);
    
    ManagementProcess MP_second = new ManagementProcess("2", 100);
    
    ManagementProcess MP_third = new ManagementProcess("3", 200);
    
    System.out.println(Process.processCount_MP + " Management Process created.");
    System.out.println(Process.processCount_IP + " Industrial Process created consisting of " + Process.operationCount + " Industrial Operations");
	
/*
	ManagementProcess MP_second = new ManagementProcess("2");
    MP_second.ProcessPrintInfo();
	IndustrialProcess second = new IndustrialProcess(100, "2");
	second.ProcessPrintInfo();
	
	ManagementProcess MP_third = new ManagementProcess("3");
    MP_third.ProcessPrintInfo();
	IndustrialProcess third = new IndustrialProcess(200, "3");
	third.ProcessPrintInfo();*/
    }
    
    
	
}
