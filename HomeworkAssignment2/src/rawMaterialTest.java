import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class rawMaterialTest{

    @Test
    void testConstructor(){
	double[] loc = {1,3};
	rawMaterial material = new rawMaterial("testID", "gravel", 200, loc);
	assertEquals("testID", material.id);
	assertEquals("gravel", material.type);
	assertEquals(200, material.amount);
	assertEquals(loc, material.location);
    }
    
}
