import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class file_ops_Test {
	
	private static final String test_dir = "logs"; // for testing purposes
	private static final String archive_dir = "logs/archive"; // for archiving purposes

	@BeforeEach
	void setUp() throws Exception {
		try {
			Files.createDirectories(Paths.get(test_dir));
			Files.createDirectories(Paths.get(archive_dir));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@AfterEach
	void tearDown() throws Exception {
		try {
			Files.walk(Paths.get(test_dir)).forEach(path -> {
				try {
					Files.deleteIfExists(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			Files.walk(Paths.get(archive_dir)).forEach(path -> {
				try {
					Files.deleteIfExists(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testCreateUpdateLog() {
		String fileName = "logFile.txt";
		String logEvent = "Test event";
		
		file_ops.createUpdateLog(fileName, logEvent);
		
		Path log_path = Paths.get(test_dir, fileName);
		assertTrue(Files.exists(log_path), "Log file should be created");
	}
	
	@Test
	void testMoveLog() {
		String fileName = "logFile.txt";
		String logEvent = "moving the log";
		String targetDirectory = "tar_dir";
		file_ops.createUpdateLog(fileName, logEvent);
		
		file_ops.moveLog(fileName, targetDirectory);
		
		Path log_path = Paths.get(test_dir, targetDirectory, fileName);
		assertTrue(Files.exists(log_path), "moved log file should exist in the target directory");
	}
	
	@Test
	void testArchiveLog() {
		String fileName = "logFile.txt";
		String logEvent = "archiving the log";
		file_ops.createUpdateLog(fileName, logEvent);
		
		file_ops.archiveLog(fileName);
		
		Path log_path = Paths.get(archive_dir, fileName.replace(".txt", "_archive.txt"));
		assertTrue(Files.exists(log_path), "Archived log file should exist in the archive directory");
	}
	
	@Test
	void testDeleteLog() {
		String fileName = "logFile.txt";
		String logEvent = "deleting the log";
		file_ops.createUpdateLog(fileName, logEvent);
		
		file_ops.deleteLog(fileName);
		
		Path log_path = Paths.get(test_dir, fileName);
		assertFalse(Files.exists(log_path), "log file should be deleted");
	}
	
	@Test
	void testOpenLog() {
		String fileName = "logFile.txt";
		String logEvent = "opening the log";
		file_ops.createUpdateLog(fileName, logEvent);
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		file_ops.openLog("Fil");
		
		assertTrue(output.toString().contains(logEvent), "log events should be printed to console");
	}
	

}
