import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class storageManagementTest {

    storageManagement sm;

    @BeforeEach
    void setup() {
        sm = new storageManagement(100); // 100 tons total capacity
    }

    @Test
    void testInitializationSetsMaxAndFreeStorage() {
        assertEquals(100, sm.max_storage(), "Max storage should be initialized correctly");
        assertDoesNotThrow(() -> assertTrue(sm.free_space(), "Should have free space after initialization"));
    }

    @Test
    void testStoreRawMaterialReducesFreeStorage() throws Exception {
        sm.store_raw_material(30);
        assertEquals(70, sm.max_storage() - sm.stored_raw_material(), "Free storage should decrease after storing");
        assertEquals(30, sm.stored_raw_material(), "Should have 30 tons of raw material stored");
    }

    @Test
    void testStoreProcessedMaterialReducesFreeStorage() throws Exception {
        sm.store_processed_material(20);
        assertEquals(20, sm.stored_processed_material(), "Should have 20 tons of processed material stored");
    }

    @Test
    void testStoreRawMaterialThrowsWhenFull() throws Exception {
        sm.store_raw_material(100);
        assertThrows(storageManagement.noFreeStorageSpaceException.class, () -> sm.store_raw_material(1),
                "Should throw when trying to exceed capacity");
    }

    @Test
    void testRetrieveRawMaterialDecreasesInventory() throws Exception {
        sm.store_raw_material(50);
        int retrieved = sm.retrieve_raw_material(20);
        assertEquals(20, retrieved, "Should retrieve 20 tons");
        assertEquals(30, sm.stored_raw_material(), "30 tons should remain");
    }

    @Test
    void testRetrieveRawMaterialThrowsIfNotEnough() {
        assertThrows(storageManagement.materialNotFoundException.class, () -> sm.retrieve_raw_material(10),
                "Should throw if trying to retrieve material not stored");
    }

    @Test
    void testRetrieveProcessedMaterialDecreasesInventory() throws Exception {
        sm.store_processed_material(60);
        int retrieved = sm.retrieve_processed_material(30);
        assertEquals(30, retrieved, "Should retrieve 30 tons");
        assertEquals(30, sm.stored_processed_material(), "Should have 30 tons left");
    }

    @Test
    void testRetrieveProcessedMaterialThrowsIfNotEnough() {
        assertThrows(storageManagement.materialNotFoundException.class,
                () -> sm.retrieve_processed_material(10),
                "Should throw if trying to retrieve nonexistent processed material");
    }

    @Test
    void testFreeSpaceReturnsFalseWhenFull() throws Exception {
        sm.store_raw_material(100);
        assertFalse(sm.free_space(), "Should report no free space when full");
    }

    @Test
    void testToStringReportsInventoryCorrectly() throws Exception {
        sm.store_raw_material(20);
        sm.store_processed_material(10);
        String s = sm.toString();
        assertTrue(s.contains("Raw Materials inventory: 20"), "Should include raw material amount");
        assertTrue(s.contains("Processed goods inventory: 10"), "Should include processed material amount");
    }
}
