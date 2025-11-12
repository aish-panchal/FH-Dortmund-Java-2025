import java.text.SimpleDateFormat;
import java.util.Date;

public class storageManagement {
	// change to private later
	public String storageLog;
	private int max_storage;
	private int free_storage;
	private String log;
	private Date today;
	private int tons_stored;

	public class storageException extends Exception {
	}

	public class materialNotFoundException extends storageException {
	}

	public class noFreeStorageSpaceException extends storageException {
	}

	public storageManagement(int n) {
		this.max_storage = n;
		this.free_storage = max_storage;
		this.today = new java.util.Date();
		this.log = (new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS").format(this.today) + " Storage" + ".txt");
		file_ops.createUpdateLog(this.log, "");// create log file when initialized
	}

	public synchronized int stored_materials() {
		return tons_stored;
	}

	public synchronized int max_storage() {
		return max_storage;
	}

	public synchronized boolean free_space() throws noFreeStorageSpaceException {
		// return the location of a free storage space
		if (free_storage > 0) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized int retrieve_material(int tons) throws materialNotFoundException {
		if (this.tons_stored == tons) {
			tons_stored -= tons;
			free_storage += tons;
			String log_message = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.today)
					+ ": " + tons + " tons have been retrieved. Free storage space: "
					+ free_storage);
			file_ops.createUpdateLog(this.log, log_message);
			return tons;
		}
		throw new materialNotFoundException();
	}

	public synchronized boolean material_stored(int tons) {
		if (tons_stored >= tons)
			return true;
		return false;
	}

	public synchronized void store_material(int tons) throws noFreeStorageSpaceException {
		if (free_storage < tons) {
			String log_message = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.today)
					+ ": failed to store " + tons + " tons. Free storage space: " + free_storage);
			file_ops.createUpdateLog(this.log, log_message);
			throw new noFreeStorageSpaceException();
		} else {
			tons_stored += tons;
			free_storage -= tons;
			String log_message = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.today) + ": "
					+ tons
					+ " tons have been stored. Free storage space: " + free_storage);
			file_ops.createUpdateLog(this.log, log_message);
		}
	}

	// public String toString() {
	// String data = "";
	// for (rawMaterial m : stored_materials) {
	// data += "Type: " + m.type + "\n\nAmount: " + m.amount + " tonnes\n\nLocation:
	// (" + m.location[0]
	// + ", " + m.location[1] + ")\n";
	// }
	// return data;
	// }
}
