import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class rawMaterialtest{

    @Test
    void testConstructor(){
	double[] loc = {1,3};
	rawMaterial material = new rawMaterial("testID", "gravel", 200, loc);
    }
    
}
