import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class testmovStorage {
	double warehouse[]= {3,1};
    double factory[]= {5,6};
    double dispatch[]= {4,5};
	Date testdate;
	rawMaterial testmat = new rawMaterial("test item","raw",10,warehouse);
	avg[] testvehicle= new avg[5];
	
	@Test
	@DisplayName ("Creating avgs")
	void testmovementStorage() throws exception_handling.ZeroTonnesException, exception_handling.InvalidOrderException, exception_handling, exception_handling.VehicleNotFoundException{
		 testdate = new java.util.Date();
		for(int i=0; i<5;i++) {
			testvehicle[i]= new avg("avg."+i, 0.25);//id,consump %/h
			testvehicle[i].setActSpeed(5);
			testvehicle[i].avgfile=(new SimpleDateFormat("yyyy-MM-dd").format(testdate)+testvehicle[i].id+".txt");//create/update vehicle file
		    file_ops.createUpdateLog(testvehicle[i].avgfile, "Started task.");
		}	
		movStorage moveavg = new movStorage("toWarehouse",testdate, warehouse, this.testvehicle, "log.txt", factory,"src","dst", this.testmat);

		assertEquals(5, moveavg.avgs.length);
	}	
	
	@Test
	@DisplayName ("Loading")
	void testloading(){
		testdate = new java.util.Date();
		for(int i=0; i<5;i++) {
			testvehicle[i]= new avg("avg."+i, 0.25);//id,consump %/h
			testvehicle[i].setActSpeed(5);
			testvehicle[i].avgfile=(new SimpleDateFormat("yyyy-MM-dd").format(testdate)+testvehicle[i].id+".txt");//create/update vehicle file
		    file_ops.createUpdateLog(testvehicle[i].avgfile, "Started task.");
		}	
		movStorage moveavg = new movStorage("toWarehouse",testdate, warehouse, this.testvehicle, "log.txt", factory,"src","dst", this.testmat);
	
		assertEquals(5, moveavg.avgs.length);
	}
	
	@Test
	@DisplayName ("Unloading")
	void testunloading(){
		testdate = new java.util.Date();
		for(int i=0; i<5;i++) {
			testvehicle[i]= new avg("avg."+i, 0.25);//id,consump %/h
			testvehicle[i].setActSpeed(5);
			testvehicle[i].avgfile=(new SimpleDateFormat("yyyy-MM-dd").format(testdate)+testvehicle[i].id+".txt");//create/update vehicle file
		    file_ops.createUpdateLog(testvehicle[i].avgfile, "Started task.");
		}	
		movStorage moveavg = new movStorage("toWarehouse",testdate, warehouse, this.testvehicle, "log.txt", factory,"src","dst", this.testmat);
	
		assertEquals(5, moveavg.avgs.length);
	}

	@Test
	@DisplayName ("moving")
	void testmoving(){
		testdate = new java.util.Date();
		for(int i=0; i<5;i++) {
			testvehicle[i]= new avg("avg."+i, 0.25);//id,consump %/h
			testvehicle[i].setActSpeed(5);
			testvehicle[i].avgfile=(new SimpleDateFormat("yyyy-MM-dd").format(testdate)+testvehicle[i].id+".txt");//create/update vehicle file
		    file_ops.createUpdateLog(testvehicle[i].avgfile, "Started task.");
		}	
		movStorage moveavg = new movStorage("toWarehouse",testdate, warehouse, this.testvehicle, "log.txt", factory,"src","dst", this.testmat);
	
		assertEquals(5, moveavg.avgs.length);
	}
	
	@Test
	@DisplayName ("moving")
	void testgetlocation(){
		testdate = new java.util.Date();
		for(int i=0; i<5;i++) {
			testvehicle[i]= new avg("avg."+i, 0.25);//id,consump %/h
			testvehicle[i].setActSpeed(5);
			testvehicle[i].avgfile=(new SimpleDateFormat("yyyy-MM-dd").format(testdate)+testvehicle[i].id+".txt");//create/update vehicle file
		    file_ops.createUpdateLog(testvehicle[i].avgfile, "Started task.");
		}	
		movStorage moveavg = new movStorage("toWarehouse",testdate, warehouse, this.testvehicle, "log.txt", factory,"src","dst", this.testmat);
		double testloc[] =moveavg.getlocation();
		assertEquals(warehouse, testloc);//the location should be the destination
	}
}
