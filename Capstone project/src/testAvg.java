import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class testAvg {

	@Test
	@DisplayName("Creating file")
	void testavg() {

		avg testavg = new avg("testavg", 0.15);
		testavg.avgfile = ("test avg file");
		assertNotNull(testavg.avgfile);
	}

	@Test
	@DisplayName("Change avg postion")
	void testchangepos() {
		avg testavg = new avg("testavg", 0.15);
		double newposition[] = { 1.0, 1.0 };
		double testposition[] = new double[2];
		testposition = testavg.changepos(newposition);

		assertEquals(newposition[0], testposition[0]);
		assertEquals(newposition[1], testposition[1]);
	}

	@Test
	@DisplayName("Change avg postion")
	void testSetActSpeed() {
		avg testavg = new avg("testavg", 0.15);
		testavg.setActSpeed(5);

		assertEquals(5, testavg.actspeed);
	}

	@Test
	@DisplayName("Charge a percentage of battery")
	void testchargepercentage() {
		boolean sameload = false;
		avg testavg = new avg("testavg", 0.15);
		while (testavg.getConsump() < 1.0) {
			testavg.wait_at_pos(30);
		}
		double percentage = 0.30;
		testavg.chargeBatteryPercentage(percentage);
		double batteryload = 1 - testavg.getConsump();
		if (batteryload >= 0.30) {
			sameload = true;
		}
		assertEquals(true, sameload);
	}

	@Test
	@DisplayName("Comsumption by waiting at a position")
	void testWaitAtPosition() {

		boolean avgDischarged = true;
		boolean testdischarge;

		avg testavg = new avg("testavg", 0.15);
		testavg.wait_at_pos(10);
		if (testavg.getConsump() > 0) {
			testdischarge = true;
		} else {
			testdischarge = false;
		}
		assertEquals(avgDischarged, testdischarge);
	}
}
