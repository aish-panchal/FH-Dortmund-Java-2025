import java.util.ArrayList;

public class storageManagement{
    // change to private later
    public ArrayList<storageEquipment> equipment;
    public ArrayList<rawMaterial> stored_materials;

    public void initialize_storage_equipment(int n){
	// initialize a grid of n*n storage devices
	for(int i = 0; i < n; i++){
	    for(int j = 0; j < n; j++){
		equipment.add(new storageEquipment(new double[] {i, j}));
	    }
	}
    }
    public ArrayList<storageEquipment> equipment(){
	return equipment;
    }
    public ArrayList<rawMaterial> stored_materials(){
	return stored_materials;
    }
    public double[] free_space() throws Exception{
	// return the location of a free storage space
	for(storageEquipment s: equipment){
	    if (s.is_occupied() == false){
		return s.equipmentLocation();
	    }	 
	}
	throw new Exception();
	// TODO change to actual "no free space" exception
    }
    //TODO make storage exceptions and specific ones for each error
    public rawMaterial retrieve_material(double[] location) throws Exception{
	for(rawMaterial item: stored_materials){
	    if(item.location == location){
		stored_materials.remove(item);
		// TODO add logging here
		return item;
	    }
	}
	throw new Exception();
    }
    public rawMaterial retrieve_material(String id) throws Exception{
	for(rawMaterial item: stored_materials){
	    if(item.id == id){
		stored_materials.remove(item);
		// TODO add logging here
		return item;
	    }
	}
	throw new Exception();
    }
    
    public void store_material(rawMaterial item, double[] storage_location) throws Exception{
	// store a material at a
	storageEquipment storageSpace = null;
	for(storageEquipment s: equipment){
	    if(s.equipmentLocation() == storage_location){
		if(s.is_occupied()){
		    throw new Exception();
		    // space is not empty
		}
		storageSpace = s;
		break;
	    }
	}
	if(null == storageSpace){
	    throw new Exception();
	    // no storage space at location
	}
	storageSpace.load();
	item.location = storage_location;
	stored_materials.addLast(item);
	// TODO add logging here
    }
}


