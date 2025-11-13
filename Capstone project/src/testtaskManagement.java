import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class testtaskManagement {

	Date testdate = new java.util.Date();
	taskManagement order = new taskManagement(testdate, 25);

	@Test
	@DisplayName("Constructor, creates log file and vehicles")
	void testtaskManager() {
		taskManagement order1 = new taskManagement(testdate, 25);
		String testname = ("log." + new SimpleDateFormat("yyyy-MM-dd").format(testdate) + ".txt");

		assertEquals(testname, order1.file);// the file name follow format "log.yyyy-MM-dd.txt"
		assertEquals(25, order1.vehicles.size()); // there are 25 vehicles available to use
	}

	@Test
	@DisplayName("Vehicles available after an order")
	void testavailable_vehicles()
			throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException, exception_handling,
			exception_handling.VehicleNotFoundException, InterruptedException {
		order.takeOrder(10, "toDelivery");// a low_load need only 5 avgs

		int remainingAvgs = 20;
		assertEquals(remainingAvgs, order.vehicles.size());// should remain 20 for other tasks
	}

	@Test
	@DisplayName("Valid load ordered and operating avg amount")
	void testop_vehicles() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException,
			exception_handling, exception_handling.VehicleNotFoundException, InterruptedException {
		taskManagement order5 = new taskManagement(testdate, 25);
		order.takeOrder(10, "toFactory");// 10 tones only require 5 avg
		assertEquals(5, order.move.avgsToBeUsed.size());// a low_load need only 5 avgs

	}

	@Test
	@DisplayName("Order with valid name")
	void testtaskeOrder() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException,
			exception_handling, exception_handling.VehicleNotFoundException, InterruptedException {

		order.takeOrder(10, "toWarehouse");// valid order
		// order.takeOrder(25, "delivery"); //invalid order
		order.takeOrder(10, "toDelivery");// valid order
		int testorderno = 3;// orderno increase after each order

		assertEquals(testorderno, order.orderno);
	}

	@Test
	@DisplayName("Order managed correctly")
	void testmanageOrder() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException,
			exception_handling, exception_handling.VehicleNotFoundException, InterruptedException {
		double warehouse[] = { 3, 1 };
		double factory[] = { 5, 6 };
		double dispatch[] = { 4, 5 };

		order.takeOrder(10, "toWarehouse");
		int testorderno = 2;
		assertEquals(testorderno, order.orderno);
		assertNotNull(order.move);// move object should be initialized after order is managed
	}

	@Test
	@DisplayName("Charging queue after a task")
	void testcallchargingStation()
			throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException, exception_handling,
			exception_handling.VehicleNotFoundException, InterruptedException {

		taskManagement order2 = new taskManagement(testdate, 50);
		// taskManagement order3 = new taskManagement(testdate, 25);
		order2.takeOrder(10, "toWarehouse");// 5 vehicles end up in need of charging
		// order3.takeOrder(10, "toWarehouse");//5 vehicles end up in need of charging

		int avgToCharge = 5;

		assertEquals(avgToCharge, order2.charge.chargingQ.size());// there should be 5 vehicles on the queue
		// assertEquals(0, order3.vehiclesInNeedOfCharging.size());// there should be
		// vehicles in the queue
	}
}
