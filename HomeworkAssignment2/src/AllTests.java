import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ file_ops_Test.class, rawMaterialTest.class, storageEquipmentTest.class, storageManagementTest.class,
		testchargingStation.class, testmovDelivery.class, testmovStorage.class, testtaskManagement.class })
public class AllTests {

}
