import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

class testtaskManagement {
	
	@Test
	void testtaskeOrder() {
		
		taskManagement order = new taskManagement(new java.util.Date());
		
	}
	
	void testmanageOrder() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException, exception_handling, exception_handling.VehicleNotFoundException {
		double warehouse[]= {3,1};
	    double factory[]= {5,6};
	    double dispatch[]= {4,5};
		
		taskManagement order = new taskManagement(new java.util.Date());
		order.takeOrder(10, "toWarehouse");
		
		assertEquals(warehouse,order.move.location);//at the end of the order the location should be the destination
	}
	
	void testtaskManager(){
		Date testdate = new java.util.Date();
		taskManagement order = new taskManagement(testdate);
		
		String testname =("log."+new SimpleDateFormat("yyyy-MM-dd").format(testdate)+".txt");
		
		assertEquals(testname,order.file);//the file name follow format "log.yyyy-MM-dd.txt"
	}
	
	void testop_vehicles() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException, exception_handling, exception_handling.VehicleNotFoundException {
		Date testdate = new java.util.Date();
		taskManagement order = new taskManagement(testdate);
		order.takeOrder(10, "toFactory");
		
		assertEquals(5,order.vehicles.length);//a low_load need only 5 avgs
	}

}
