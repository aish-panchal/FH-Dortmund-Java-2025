import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class storageManagement {
	// change to private later
	public ConcurrentLinkedQueue<rawMaterial> stored_materials;
	public String storageLog;
	public int max_storage;

	public class storageException extends Exception {
	}

	public class storageOccupiedException extends storageException {
	}

	public class storageNotFoundException extends storageException {
	}

	public class materialNotFoundException extends storageException {
	}

	public class noFreeStorageSpaceException extends storageException {
	}

	public storageManagement(int n) {
		this.max_storage = n;
		Date thisday = new java.util.Date();
		String storageLog = (new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS").format(thisday) + " Storage"
				+ ".txt");
		file_ops.createUpdateLog(storageLog, "");// create log file when initialized
	}

	public ConcurrentLinkedQueue<rawMaterial> stored_materials() {
		return stored_materials;
	}

	public synchronized double[] free_space() throws noFreeStorageSpaceException {
		// return the location of a free storage space

		throw new noFreeStorageSpaceException();
	}

	// TODO make storage exceptions and specific ones for each error
	public synchronized rawMaterial retrieve_material(double[] location) throws materialNotFoundException {

		for (rawMaterial item : stored_materials) {
			if (item.location == location) {
				stored_materials.remove(item);
				// TODO add logging here

				return item;
			}
		}
		throw new materialNotFoundException();
	}

	public synchronized rawMaterial retrieve_material(String id) throws materialNotFoundException {
		for (rawMaterial item : stored_materials) {
			if (item.id == id) {
				stored_materials.remove(item);
				// this should be refactored out into a function

				// TODO add logging here
				return item;
			}
		}
		throw new materialNotFoundException();
	}

	public synchronized void store_material(rawMaterial item, double[] storage_location)
			throws storageOccupiedException, storageNotFoundException {

		// store a material at a storage location
		storageEquipment storageSpace = null;

		if (null == storageSpace) {
			throw new storageNotFoundException();
			// no storage space at location
		}
		item.location = storage_location;
		stored_materials.add(item);

		// TODO add logging here
	}

}
