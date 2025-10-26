
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;

public class file_ops {
	
	private static final String Logs_dir = "logs";
	
	// to create or update a log file with the event being performed
	public static void createUpdateLog(String fileName, String loggingEvent) {
		Path log_path = Paths.get(Logs_dir, fileName);
		
		try {
			// creating the directory if its the first time a log file is created
			if (Files.notExists(Paths.get(Logs_dir))) {
				Files.createDirectories(Paths.get(Logs_dir));
			}
			
			// creating a new file if one doesn't already exist
			if (Files.notExists(log_path)) {
				Files.createFile(log_path);
				try (BufferedWriter writer = Files.newBufferedWriter(log_path)){
					writer.write("Log event: " + loggingEvent + " Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()) + "\n");		
				}
			} else {
				try (BufferedWriter writer = Files.newBufferedWriter(log_path, StandardOpenOption.APPEND)){
					writer.write("Log event: " + loggingEvent + " Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()) + "\n");
				}
			}
		} catch (IOException e) {
			System.out.println("Error creating/writing to log file: " + e.getMessage());
		}
			
	}
	
	// moving a log file from one location to another in the project
	public static void moveLog(String fileName, String targetDirectory) {
		Path log_path = Paths.get(Logs_dir, fileName);
		Path target_dir_path = Paths.get(Logs_dir, targetDirectory);
		
		try {
			if (Files.exists(log_path)) { // check if log file exists
				if (Files.notExists(target_dir_path)) {
					Files.createDirectories(target_dir_path); // create target directory if it doesn't exist
				}
				
				Path target_file_path = target_dir_path.resolve(fileName);
				Files.move(log_path, target_file_path, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			System.out.println("Error moving log file: " + e.getMessage());
		}
	}
	
	// to archive a log file i.e. move it to archive folder
	public static void archiveLog(String fileName) {
		Path log_path = Paths.get(Logs_dir, fileName);
		Path archive_dir_path = Paths.get(Logs_dir, "archive");
		
		try {
			if (Files.exists(log_path)) { // check if log file exists
				if (Files.notExists(archive_dir_path)) {
					Files.createDirectories(archive_dir_path);
				}
				
				Path archive_file_path = archive_dir_path.resolve(fileName.replace(".log", "_archive.log"));
				Files.move(log_path, archive_file_path, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			System.out.println("Error moving log file: " + e.getMessage());
		}
	}
	
	// to delete a log file
	public static void deleteLog(String fileName) {
		Path log_path = Paths.get(Logs_dir, fileName);
		
		try {
			if (Files.exists(log_path)) {
				Files.delete(log_path);
			}
		} catch (IOException e) {
			System.out.println("Error deleting log file: " + e.getMessage());
		}
	}
	
	// to open a log file with log name and its relevant extension
	public static void openLog (String equipmentDateTerm) {
		Path log_path = Paths.get(Logs_dir, equipmentDateTerm + ".log");
		
		if(Files.exists(log_path)) {
			try (BufferedReader reader = Files.newBufferedReader(log_path)){
				String data;
				while ((data = reader.readLine()) != null) {
					System.out.println(data);
				}
			} catch (IOException e) {
				System.out.println("Error reading log file: " + e.getMessage());
			}
		}
	}
	
	// to simulate data exchange using byte and character streams
	public static void simulateDataXchange(String fileName) {
		Path log_path = Paths.get(Logs_dir, fileName);
		
		try {
			// byte stream data exchange simulation
			try (FileInputStream in_strm = new FileInputStream(log_path.toFile()); FileOutputStream out_strm = new FileOutputStream("logs/byte_strm_output.log")) {
				byte[] buffer = new byte[10240]; // arbitrary value of 10 KB chosen for efficient read and memory usage
				int bytesRead;
				while ((bytesRead = in_strm.read(buffer)) != -1){
					out_strm.write(buffer, 0, bytesRead);
				}
				System.out.println("byte stream transfer data simulation complete");
				
				// character stream data exchange data simulation
				try (FileReader reader = new FileReader(log_path.toFile()); FileWriter writer = new FileWriter("logs/char_strm_output.log")){
					int charactersRead;
					while ((charactersRead = reader.read()) != -1) {
						writer.write(charactersRead);
					}
					System.out.println("character stream transfer data simulation complete");
				}
			} 	
		} catch (IOException e) {
			System.out.println("Data exchange simulation error: " + e.getMessage());
		}
	}
}
