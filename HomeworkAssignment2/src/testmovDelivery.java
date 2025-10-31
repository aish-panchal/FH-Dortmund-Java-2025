import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class testmovDelivery {
	double warehouse[]= {3,1};
    double factory[]= {5,6};
    double dispatch[]= {4,5};
	Date testdate;
	rawMaterial testmat = new rawMaterial("test item","raw",10,warehouse);
	avg[] testvehicle= new avg[5];
	
	@Test
	@DisplayName ("Creating file and avgs")
	void testmovementStorage(){
		testdate = new java.util.Date();
		for(int i=0; i<5;i++) {
			testvehicle[i]= new avg("avg."+i, 0.25);//id,consump %/h
			testvehicle[i].setActSpeed(5);
			testvehicle[i].avgfile=(new SimpleDateFormat("yyyy-MM-dd").format(testdate)+testvehicle[i].id+".txt");//create/update vehicle file
		    file_ops.createUpdateLog(testvehicle[i].avgfile, "Started task.");
		}	
		movDelivery moveavg = new movDelivery("toWarehouse",testdate,"log.txt",this.testvehicle,warehouse, dispatch,this.testmat);
		
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
		movDelivery moveavg = new movDelivery("toWarehouse",testdate,"log.txt",this.testvehicle,warehouse, dispatch,this.testmat);
		
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
		movDelivery moveavg = new movDelivery("toWarehouse",testdate,"log.txt",this.testvehicle,warehouse, dispatch,this.testmat);
		
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
		movDelivery moveavg = new movDelivery("toWarehouse",testdate,"log.txt",this.testvehicle,warehouse, dispatch,this.testmat);
		
	}
	
	@Test
	@DisplayName ("Get current location")
	void testgetlocation(){
		testdate = new java.util.Date();
		for(int i=0; i<5;i++) {
			testvehicle[i]= new avg("avg."+i, 0.25);//id,consump %/h
			testvehicle[i].setActSpeed(5);
			testvehicle[i].avgfile=(new SimpleDateFormat("yyyy-MM-dd").format(testdate)+testvehicle[i].id+".txt");//create/update vehicle file
		    file_ops.createUpdateLog(testvehicle[i].avgfile, "Started task.");
		}	
		movDelivery moveavg = new movDelivery("toDelivery",testdate,"log.txt",this.testvehicle,warehouse, dispatch,this.testmat);
		
		double[] testloc =moveavg.getlocation();
		assertEquals(dispatch, testloc);//the location should be the destination
	}

}
