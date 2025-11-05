import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class testtaskManagement {
	
	@Test
	@DisplayName ("Log file creation")
	void testtaskManager(){
		Date testdate = new java.util.Date();
		taskManagement order = new taskManagement(testdate);
		
		String testname =("log."+new SimpleDateFormat("yyyy-MM-dd").format(testdate)+".txt");
		
		assertEquals(testname,order.file);//the file name follow format "log.yyyy-MM-dd.txt"
	}
	
	@Test
	@DisplayName ("Valid load ordered and operating avg amount")
	void testop_vehicles() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException, exception_handling, exception_handling.VehicleNotFoundException {
		Date testdate = new java.util.Date();
		taskManagement order = new taskManagement(testdate);
		order.takeOrder(10, "toFactory");
		
		assertEquals(5,order.move.avgs.length);//a low_load need only 5 avgs
	}
	
	@Test
	@DisplayName ("Order with valid name")
	void testtaskeOrder() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException, exception_handling, exception_handling.VehicleNotFoundException {
		
		Date testdate = new java.util.Date();
		taskManagement order = new taskManagement(testdate);
		order.takeOrder(10, "toWarehouse");
		int testorderno=2;
		
		assertEquals(testorderno,order.orderno); //if order was written properly then move is not null
	}
	
	@Test
	@DisplayName ("Order managed correctly")
	void testmanageOrder() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException, exception_handling, exception_handling.VehicleNotFoundException {
		double warehouse[]= {3,1};
	    double factory[]= {5,6};
	    double dispatch[]= {4,5};
		
	    Date testdate = new java.util.Date();
		taskManagement order = new taskManagement(testdate);
		order.takeOrder(10, "toWarehouse");
		
		assertNotNull(order.move);//move object should be initialized after order is managed

	}
	
	@Test
	@DisplayName ("Calling of chargingStation")
	void testcallchargingStation() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException, exception_handling, exception_handling.VehicleNotFoundException {
		Date testdate = new java.util.Date();
		taskManagement order = new taskManagement(testdate);
		order.takeOrder(10, "toDelivery");
		
		assertNotNull(order.charge);//charge object should be initialized after avg ran long process
	}

}
