package SA2;

/*This code presents Industrial Processes, showing the duration of the process,
 * the amount of AVGs it requires and its energy consumption.
 */
public class main {
    public static void main (String[] args){
	IndustrialProcess first = new IndustrialProcess(20, "1");
	first.ProcessPrintInfo();

	IndustrialProcess second = new IndustrialProcess(100, "2");
	second.ProcessPrintInfo();
	
	IndustrialProcess third = new IndustrialProcess(200, "3");
	third.ProcessPrintInfo();
    }
}
