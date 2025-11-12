import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class testchargingStation {

	public ConcurrentLinkedQueue<avg> testQ;
	public ConcurrentLinkedQueue<avg> returnQ;
	public chargingStation testchargingstation;
	public Date testdate;
	// avg testvehicle = new avg[5];
	// Date testdate;

	@Test
	@DisplayName("Creating log file and charging queue")
	void testchargestation() throws exception_handling.VehicleNotFoundException {
		testQ = new ConcurrentLinkedQueue();
		returnQ = new ConcurrentLinkedQueue();
		double pos[] = { 4, 2 };
		for (int i = 0; i < 5; i++) {
			avg a = new avg("avg." + i, 0.25);// id,consump %/h
			// discharge avg
			while (a.getConsump() < 0.50) {
				a.changepos(pos);
			}
			testQ.add(a);
		}
		testchargingstation = new chargingStation("taskfile", returnQ, testQ);
//		testdate = new java.util.Date();
//		String testfile = (new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(testdate) + "ChargingStation" + ".txt");

		assertNotNull(testchargingstation.logf);
		assertEquals(5, testchargingstation.chargingQ.size());
	}

	@Test
	@DisplayName("Take vehicles to the station")
	void testchargingvehicles() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException,
			exception_handling, exception_handling.VehicleNotFoundException {
		testdate = new java.util.Date();
		for (int i = 0; i < 5; i++) {
			testvehicle[i] = new avg("avg." + i, 0.25);// id,consump %/h
		}
		chargingStation teststation = new chargingStation("log.txt", testdate, testvehicle);

		assertNotNull(teststation.chargingavg);// there are vehicles in the station
	}

	@Test
	@DisplayName("Charging vehicles")
	void testcharging() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException,
			exception_handling, exception_handling.VehicleNotFoundException {
		testdate = new java.util.Date();
		double warehouse[] = { 3, 1 };
		double factory[] = { 5, 6 };
		double dispatch[] = { 4, 5 };
		for (int i = 0; i < 5; i++) {
			testvehicle[i] = new avg("avg." + i, 0.25);// id,consump %/h
			testvehicle[i].changepos(warehouse);
		}
		chargingStation teststation = new chargingStation("log.txt", testdate, testvehicle);
		teststation.charging(testvehicle);
		taskManagement order = new taskManagement(testdate);
		order.takeOrder(10, "toDelivery");

		assertEquals(1, order.charge.chargingavg[1].getComsup()); // avg should have 100% battery
	}

	@Test
	@DisplayName("Get status after done")
	void testgetStationStatues() throws exception_handling.ZeroTonnesException,
			exception_handling.InvalidOrderException, exception_handling, exception_handling.VehicleNotFoundException {
		testdate = new java.util.Date();
		for (int i = 0; i < 5; i++) {
			testvehicle[i] = new avg("avg." + i, 0.25);// id,consump %/h
		}
		chargingStation teststation = new chargingStation("log.txt", testdate, testvehicle);
		boolean isOccupy = false;
		assertEquals(isOccupy, teststation.station_status); // station should be unoccupy
	}

}
