import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class testmovStorage {
	double warehouse[] = { 3, 1 };
	double factory[] = { 5, 6 };
	double dispatch[] = { 4, 5 };
	Date testdate;
	taskManagement task;

	@Test
	@DisplayName("Creating avgs")
	void testmovementStorage() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException,
			exception_handling, exception_handling.VehicleNotFoundException, InterruptedException {
		testdate = new java.util.Date();
		task = new taskManagement(testdate, 25);
		task.takeOrder(10, "toWarehouse");

		assertEquals(5, task.move.avgsToBeUsed.size());
	}

	@Test
	@DisplayName("Moving to location")
	void testmovingtolocation() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException,
			exception_handling, exception_handling.VehicleNotFoundException, InterruptedException {
		testdate = new java.util.Date();
		task = new taskManagement(testdate, 25);
		task.takeOrder(50, "toWarehouse");
		Thread.sleep(500);
		double testavgpos[] = task.move.avgsToBeUsed.get(0).getPosition();

		assertEquals(task.move.destination[0], testavgpos[0]);
		assertEquals(task.move.destination[1], testavgpos[1]);
	}

	@Test
	@DisplayName("Moving vehicles to chargin queue")
	void testavgToChargeQueue()
			throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException, exception_handling,
			exception_handling.VehicleNotFoundException, InterruptedException, storageManagement.storageException {
		testdate = new java.util.Date();
		task = new taskManagement(testdate, 25);
		task.takeOrder(10, "toWarehouse");// this process requires 5 avgs
//		task.move.loading(1);
//		task.move.movingtolocation(1);
		task.move.unloading(1);

		int testQsize = 5;

		assertEquals(testQsize, task.move.chargeQ.size());
		// assertEquals(testQsize, task.move.readyVehicleQ.size());
	}

	@Test
	@DisplayName("Calculate duration of task after unloading")
	void testunloading() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException,
			exception_handling, exception_handling.VehicleNotFoundException, InterruptedException {
		testdate = new java.util.Date();
		boolean endOfTask = false;
		task = new taskManagement(testdate, 25);
		task.takeOrder(10, "toWarehouse");// this process requires 5 avgs
		Thread.sleep(500);
		double testduration = task.move.overallduration;

		if (testduration > 0) {
			endOfTask = true;
		}
		assertEquals(true, endOfTask);
	}

	@Test
	@DisplayName("Get location")
	void testgetlocation() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException,
			exception_handling, exception_handling.VehicleNotFoundException, InterruptedException {

		testdate = new java.util.Date();
		testdate = new java.util.Date();
		task = new taskManagement(testdate, 25);
		task.takeOrder(50, "toFactory");
		task.move.movingtolocation(1);
		Thread.sleep(1000);
		double testavgpos[] = task.move.getlocation();

		assertEquals(factory[0], testavgpos[0]);
		assertEquals(factory[1], testavgpos[1]);

	}

}
