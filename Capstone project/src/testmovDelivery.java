import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class testmovDelivery {
	double warehouse[] = { 3, 1 };
	double factory[] = { 5, 6 };
	double dispatch[] = { 4, 5 };
	taskManagement delivery;
	Date testdate;

	@Test
	@DisplayName("Creating avgs")
	void testmovementStorage() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException,
			exception_handling, exception_handling.VehicleNotFoundException, InterruptedException {
		testdate = new java.util.Date();
		delivery = new taskManagement(testdate, 25);
		delivery.takeOrder(10, "toDelivery");

		assertEquals(5, delivery.move.avgsToBeUsed.size());
	}

	@Test
	@DisplayName("Moving to dispatch area")
	void testmovingtolocation() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException,
			exception_handling, exception_handling.VehicleNotFoundException, InterruptedException {
		testdate = new java.util.Date();
		delivery = new taskManagement(testdate, 25);
		delivery.takeOrder(10, "toDelivery");
		Thread.sleep(500);

		double testavgpos[] = delivery.move.avgsToBeUsed.get(0).getPosition();

		assertEquals(dispatch[0], testavgpos[0]);
		assertEquals(dispatch[1], testavgpos[1]);
	}

	@Test
	@DisplayName("Calculate duration of task after unloading")
	void testunloading() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException,
			exception_handling, exception_handling.VehicleNotFoundException, InterruptedException {
		testdate = new java.util.Date();
		boolean endOfTask = false;
		delivery = new taskManagement(testdate, 25);
		delivery.takeOrder(10, "toWarehouse");
		Thread.sleep(500);

		double testduration = delivery.move.overallduration;

		if (testduration > 0) {
			endOfTask = true;
		}
		assertEquals(true, endOfTask);
	}

	@Test
	@DisplayName("Moving vehicles to chargin queue")
	void testavgToChargeQueue()
			throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException, exception_handling,
			exception_handling.VehicleNotFoundException, InterruptedException, storageManagement.storageException {
		testdate = new java.util.Date();
		delivery = new taskManagement(testdate, 25);
		delivery.takeOrder(20, "toWarehouse");
		Thread.sleep(500);
		delivery.takeOrder(20, "toFactory");
		Thread.sleep(5000);
		delivery.takeOrder(10, "toDelivery");// this process requires 5 avgs
		delivery.move.unloading(2);
		int chargeQ = delivery.move.chargeQ.size();
		int testQsize = 5;

		assertEquals(testQsize, chargeQ);
	}

	@Test
	@DisplayName("Get current location")
	void testgetlocation() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException,
			exception_handling, exception_handling.VehicleNotFoundException, InterruptedException {
		testdate = new java.util.Date();
		testdate = new java.util.Date();
		delivery = new taskManagement(testdate, 25);
		delivery.takeOrder(10, "toDelivery");
		delivery.move.movingtolocation(2);
		double testloc[] = delivery.move.getlocation();

		assertEquals(dispatch[0], testloc[0]);
		assertEquals(dispatch[1], testloc[1]);// the location should be the destination
	}

}
