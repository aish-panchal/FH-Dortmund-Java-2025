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

		assertNotNull(testchargingstation.logf);
		assertEquals(5, testchargingstation.chargingQ.size());
	}

	@Test
	@DisplayName("Take vehicles from queue into charging")
	void testchargingvehicles() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException,
			exception_handling, exception_handling.VehicleNotFoundException {
		testQ = new ConcurrentLinkedQueue();
		returnQ = new ConcurrentLinkedQueue();
		double pos[] = { 4, 2 };
		for (int i = 0; i < 15; i++) {
			avg a = new avg("avg." + i, 0.25);// id,consump %/h
			// discharge avg
			while (a.getConsump() < 0.50) {
				a.changepos(pos);
			}
			testQ.add(a);
		}

		testchargingstation = new chargingStation("taskfile", returnQ, testQ);
		// there's only 10 stations
		assertEquals(10, testchargingstation.charging.size());// there should be only 10 vehicles charging
		assertEquals(5, testchargingstation.chargingQ.size());// 5 vehicles remain on the queue
	}

	@Test
	@DisplayName("Put vehicles back to ready queue")
	void testcharging() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException,
			exception_handling, exception_handling.VehicleNotFoundException {
		testQ = new ConcurrentLinkedQueue();
		returnQ = new ConcurrentLinkedQueue();
		double pos[] = { 4, 2 };
		for (int i = 0; i < 15; i++) {
			avg a = new avg("avg." + i, 0.25);// id,consump %/h
			// discharge avg
			while (a.getConsump() < 0.50) {
				a.changepos(pos);
			}
			testQ.add(a);
		}

		testchargingstation = new chargingStation("taskfile", returnQ, testQ);
		int readyqSize = returnQ.size();
		assertEquals(10, readyqSize); // avg should return to
	}

	@Test
	@DisplayName("Get station status")
	void testgetStationStatues() throws exception_handling.ZeroTonnesException,
			exception_handling.InvalidOrderException, exception_handling, exception_handling.VehicleNotFoundException {
		
		testQ = new ConcurrentLinkedQueue();
		returnQ = new ConcurrentLinkedQueue();
		
		testchargingstation = new chargingStation("taskfile", returnQ, testQ);
		
		String testStatus=("Currently charging: 0 vehicles.\n\n" + "Stations available: ");
		String accStatus =;
		boolean isOccupy = false;
		assertEquals(isOccupy, teststation.station_status); // station should be unoccupy
	}

}
