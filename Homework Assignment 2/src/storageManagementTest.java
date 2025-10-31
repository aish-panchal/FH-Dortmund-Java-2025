import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class storageManagementTest {

    storageManagement sm;

    @BeforeEach
    void setup() {
        sm = new storageManagement();
        sm.equipment = new ArrayList<>();
        sm.stored_materials = new ArrayList<>();
        sm.initialize_storage_equipment(2); // 2x2 grid
    }

    @Test
    void testInitializationCreatesEquipment() {
        assertEquals(4, sm.equipment.size(), "Should initialize 4 storageEquipment objects for 2x2 grid");
    }

    @Test
    void testFreeSpaceReturnsUnoccupiedLocation() throws Exception {
        double[] free = sm.free_space();
        assertNotNull(free, "free_space() should return a non-null location");
        assertEquals(2, free.length, "Location should have two coordinates");
    }

    @Test
    void testFreeSpaceThrowsIfNoneAvailable() throws Exception {
        // occupy all
        for (storageEquipment s : sm.equipment) {
            s.load();
        }
        assertThrows(storageManagement.noFreeStorageSpaceException.class, () -> sm.free_space());
    }

    @Test
    void testStoreMaterialSuccess() throws Exception {
        double[] loc = sm.equipment.get(0).equipmentLocation();
        rawMaterial mat = new rawMaterial("ID1", "Steel", 10, null);

        sm.store_material(mat, loc);

        assertTrue(sm.equipment.get(0).is_occupied(), "Storage should be marked occupied after storing");
        assertTrue(sm.stored_materials.contains(mat), "Stored material should be in stored_materials list");
        assertArrayEquals(loc, mat.location, "Stored material should record its storage location");
    }

    @Test
    void testStoreMaterialThrowsWhenOccupied() throws Exception {
        double[] loc = sm.equipment.get(0).equipmentLocation();
        rawMaterial mat1 = new rawMaterial("ID1", "Steel", 10, null);
        rawMaterial mat2 = new rawMaterial("ID2", "Aluminum", 5, null);

        sm.store_material(mat1, loc);
        assertThrows(storageManagement.storageOccupiedException.class, () -> sm.store_material(mat2, loc));
    }

    @Test
    void testStoreMaterialThrowsWhenLocationNotFound() {
        rawMaterial mat = new rawMaterial("ID1", "Plastic", 2, null);
        double[] badLoc = {99.0, 99.0};
        assertThrows(storageManagement.storageNotFoundException.class, () -> sm.store_material(mat, badLoc));
    }

    @Test
    void testRetrieveMaterialById() throws Exception {
        double[] loc = sm.equipment.get(0).equipmentLocation();
        rawMaterial mat = new rawMaterial("ID1", "Copper", 5, null);
        sm.store_material(mat, loc);

        rawMaterial retrieved = sm.retrieve_material("ID1");

        assertEquals(mat, retrieved, "Should retrieve the same material object");
        assertFalse(sm.stored_materials.contains(mat), "Retrieved material should be removed from stored list");
        assertFalse(sm.equipment.get(0).is_occupied(), "Storage should be freed after retrieval");
    }

    @Test
    void testRetrieveMaterialByIdThrowsIfNotFound() {
        assertThrows(storageManagement.materialNotFoundException.class,
                     () -> sm.retrieve_material("NON_EXISTENT"));
    }

    @Test
    void testRetrieveMaterialByLocation() throws Exception {
        double[] loc = sm.equipment.get(0).equipmentLocation();
        rawMaterial mat = new rawMaterial("ID2", "Wood", 12, loc);
        sm.stored_materials.add(mat);

        rawMaterial retrieved = sm.retrieve_material(loc);
        assertEquals(mat, retrieved, "Should retrieve material by matching location");
        assertFalse(sm.stored_materials.contains(mat), "Material should be removed after retrieval");
    }

    @Test
    void testRetrieveMaterialByLocationThrowsIfNotFound() {
        double[] loc = {0, 0};
        assertThrows(storageManagement.materialNotFoundException.class, () -> sm.retrieve_material(loc));
    }
}
