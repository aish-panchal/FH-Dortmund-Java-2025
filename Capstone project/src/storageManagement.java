import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class storageManagement {
	// change to private later
	public ConcurrentLinkedQueue<rawMaterial> stored_materials;
	public String storageLog;
	private int max_storage;
	private int current_free_storage;
	private String log;
	private Date today;

	public class storageException extends Exception {
	}

	public class materialNotFoundException extends storageException {
	}

	public class noFreeStorageSpaceException extends storageException {
	}

	public storageManagement(int n) {
		this.max_storage = n;
		this.current_free_storage = max_storage;
		this.today = new java.util.Date();
		this.log = (new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS").format(this.today) + " Storage"
				+ ".txt");
		file_ops.createUpdateLog(storageLog, "");// create log file when initialized
	}

	public ConcurrentLinkedQueue<rawMaterial> stored_materials() {
		return stored_materials;
	}

	public int max_storage() {
		return max_storage;
	}

	public synchronized boolean free_space() throws noFreeStorageSpaceException {
		// return the location of a free storage space
		if (current_free_storage > 0) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized rawMaterial retrieve_material(int tons) throws materialNotFoundException {
		for (rawMaterial item : stored_materials) {
			if (item.amount == tons) {
				stored_materials.remove(item);
				current_free_storage += 1;
				String log_message = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.today)
						+ ": "
						+ item.id
						+ " has been retrieved. Free storage space: "
						+ current_free_storage);
				file_ops.createUpdateLog(this.log, log_message);
				return item;
			}
		}
		throw new materialNotFoundException();
	}

	public synchronized void store_material(rawMaterial item)
			throws noFreeStorageSpaceException {
		if (current_free_storage < 1) {
			String log_message = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.today) + ": "
					+ item.id
					+ " failed to store item. Free storage space: "
					+ current_free_storage);
			file_ops.createUpdateLog(this.log, log_message);
			throw new noFreeStorageSpaceException();
		} else {
			stored_materials.add(item);
			current_free_storage -= 1;
			String log_message = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.today) + ": "
					+ item.id
					+ " has been stored. Free storage space: "
					+ current_free_storage);
			file_ops.createUpdateLog(this.log, log_message);
		}
	}
}
