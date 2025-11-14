import java.text.SimpleDateFormat;
import java.util.Date;

public class storageManagement {
	// change to private later
	public String storageLog;
	private int max_storage;
	public int free_storage;
	private String log;
	private Date today;
	public int processed_materials_stored;
	public int raw_materials_stored;

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

	public synchronized int stored_raw_material() {
		return raw_materials_stored;
	}

	public synchronized int stored_processed_material() {
		return processed_materials_stored;
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

	public synchronized int retrieve_raw_material(int tons) throws materialNotFoundException {
		if (this.raw_materials_stored >= tons) {
			raw_materials_stored -= tons;
			free_storage += tons;
			String log_message = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.today)
					+ ": " + tons
					+ " tons of raw material have been retrieved. Free storage space: "
					+ free_storage);
			file_ops.createUpdateLog(this.log, log_message);
			return tons;
		}
		throw new materialNotFoundException();
	}

	public synchronized int retrieve_processed_material(int tons) throws materialNotFoundException {
		if (this.processed_materials_stored >= tons) {
			processed_materials_stored -= tons;
			free_storage += tons;
			String log_message = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.today)
					+ ": " + tons
					+ " tons of processed material have been retrieved. Free storage space: "
					+ free_storage);
			file_ops.createUpdateLog(this.log, log_message);
			return tons;
		}
		throw new materialNotFoundException();
	}

	public synchronized boolean raw_material_stored(int tons) {
		if (raw_materials_stored >= tons)
			return true;
		return false;
	}

	public synchronized boolean processed_material_stored(int tons) {
		if (processed_materials_stored >= tons)
			return true;
		return false;
	}

	public synchronized void store_processed_material(int tons) throws noFreeStorageSpaceException {
		if (free_storage < tons) {
			String log_message = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.today)
					+ ": failed to store " + tons
					+ " tons of processed material. Free storage space: " + free_storage);
			file_ops.createUpdateLog(this.log, log_message);
			throw new noFreeStorageSpaceException();
		} else {
			// System.out.println(tons + " tons of processed material put into storage");
			processed_materials_stored += tons;
			free_storage -= tons;
			String log_message = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.today) + ": "
					+ tons
					+ " tons of processed material have been stored. Free storage space: "
					+ free_storage);
			file_ops.createUpdateLog(this.log, log_message);
		}
	}

	public synchronized void store_raw_material(int tons) throws noFreeStorageSpaceException {
		if (free_storage < tons) {
			String log_message = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.today)
					+ ": failed to store " + tons
					+ " tons of processed material. Free storage space: " + free_storage);
			file_ops.createUpdateLog(this.log, log_message);
			throw new noFreeStorageSpaceException();
		} else {
			// System.out.println(tons + " tons of processed put into storage");
		        raw_materials_stored += tons;
			free_storage -= tons;
			String log_message = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.today) + ": "
					+ tons
					+ " tons of processed material have been stored. Free storage space: "
					+ free_storage);
			file_ops.createUpdateLog(this.log, log_message);
		}
	}
	
	public String toString() {
		return "Raw Materials inventory: " + raw_materials_stored + "\n\nProcessed goods inventory: " 
				+ processed_materials_stored + "\n\nRemaining warehouse space: " + free_storage;
	}

}
