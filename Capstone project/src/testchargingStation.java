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

	@Test
	@DisplayName("Creating log file")
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
	}

	@Test
	@DisplayName("Filling charging queue")
	void testchargingQ() throws exception_handling.VehicleNotFoundException {
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

		assertEquals(5, testchargingstation.chargingQ.size());
	}

	@Test
	@DisplayName("Get station status")
	void testgetStationStatues() throws exception_handling.ZeroTonnesException,
			exception_handling.InvalidOrderException, exception_handling, exception_handling.VehicleNotFoundException {
		// there's no avg's on the charging queue
		testQ = new ConcurrentLinkedQueue();
		returnQ = new ConcurrentLinkedQueue();

		testchargingstation = new chargingStation("taskfile", returnQ, testQ);

		String testStatus = ("Currently charging: 0 vehicles.\n\n" + "Stations available: 10");
		String accStatus = testchargingstation.getStationStatus();

		assertEquals(testStatus, accStatus); // station should be unoccupied
	}

}
