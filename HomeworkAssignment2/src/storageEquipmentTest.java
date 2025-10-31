import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class storageEquipmentTest{
    @Test
    void testGetSet(){
	double[] loc = {1,1};
	storageEquipment a = new storageEquipment(loc);
	assertEquals(loc, a.equipmentLocation());
	double[] loc2 = {2,2};
	a.setLocation(loc2);
	assertEquals(loc2, a.equipmentLocation());
    }

    @Test
    void testLoadUnload(){
	double[] loc = {1,1};
	storageEquipment a = new storageEquipment(loc);
	assertEquals(false, a.is_occupied());
	a.load();
	assertEquals(true, a.is_occupied());
	a.unload();
	assertEquals(false, a.is_occupied());
    }
}
