/*
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.text.*;
import javafx.event.*;
import java.text.*;
import java.util.*;
import java.io.*;
import java.io.File.*;
import java.nio.file.*;
import java.util.regex.*;
import java.util.stream.Stream;



public class humanMachineInterface extends Application{
    //public double[] lastAvgLocation;
    //public String itemStatus;
    //public String orderStatus;
    //public String avgStatus;
	private static final String Logs_dir = "logs";
	private static final String archive_dir = "logs/archive";
	
	public static void main(String[] args) {
		launch(args);
	}
    
    public void start(Stage mainStage) {
    	Date today = new Date();
    	taskManagement workday = new taskManagement(today,200);
    	
    	// creating label for weight prompt
    	Label weightLabel = new Label("Enter order weight:");
    	
    	// creating text field to enter weight 
    	TextField weightField = new TextField();
    	weightField.setPromptText("Enter weight in tonnes");
    	weightField.setMaxWidth(200);
    	
    	// creating dropdown to select destination
    	ComboBox<String> operationBox = new ComboBox<>();
    	operationBox.getItems().addAll("toFactory", "toWarehouse", "toDelivery");
    	
    	// creating button to execute takeOrder
    	Button takeOrder = new Button("Create Order");
    	
    	// creating buttons for log files management
    	Button searchLog = new Button("Search Logs");
    	Button deleteLog = new Button("Delete Logs");
    	Button archiveLog = new Button("Archive Logs");
    	Button moveLog = new Button("Move Logs");
    	searchLog.setMaxWidth(120);
    	deleteLog.setMaxWidth(120);
    	archiveLog.setMaxWidth(120);
    	moveLog.setMaxWidth(120);
    	
    	// creating search field and button to search files
    	Label searchLabel = new Label("Search Log Files:");
    	TextField searchField = new TextField();
    	searchField.setMaxWidth(200);
    	searchField.setPromptText("Enter search term (equipment/date)");
    	
    	Label moveLabel = new Label("Move Log File:");
    	TextField targetField = new TextField();
    	targetField.setMaxWidth(200);
    	targetField.setPromptText("Enter target folder");
    	
    	// creating a listing of the log files
    	ListView<String> logListView = new ListView<>();
    	logListView.setMaxHeight(200);
    	logListView.setMaxWidth(200);
    	logListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	
    	// creating area to view log file contents
    	TextArea logContents = new TextArea();
    	logContents.setEditable(false);
    	logContents.setWrapText(false);
    	logContents.setMaxHeight(1000);
    	logContents.setMaxWidth(500);
    	
    	// section to trigger the method call to takeOrder
    	takeOrder.setOnAction(e -> {
    		try {
    			// get weight and operation from the text and drop down boxes
    			int weight = Integer.parseInt(weightField.getText());
    			String operation = operationBox.getValue();
    			
    			workday.takeOrder(weight, operation);
    		} catch (NumberFormatException excep) {
    			// exception for the bad weight input
    			System.out.println("Incorrect input! Enter a valid whole number weight.");
    		} catch (exception_handling.ZeroTonnesException e1) {
    			System.out.println("Zero tonnes exception: " + e1.getMessage());
			} catch (exception_handling.InvalidOrderException e1) {
				System.out.println("Invalid order exception: " + e1.getMessage());
			} catch (exception_handling e1) {
				System.out.println("General exception: " + e1.getMessage());
			} catch (exception_handling.VehicleNotFoundException e1) {
				System.out.println("Vehicle not found exception: " + e1.getMessage());
			}
    	});
    	
    	searchLog.setOnAction(e -> {
    		String searchTerm = searchField.getText();
    		if(!searchTerm.isEmpty()) {
    			file_ops.searchLog(searchTerm, logListView);
    		} else {
    			System.out.println("Please enter a search term.");
    		}
    	});
    	
    	deleteLog.setOnAction(e -> {
    		String file = logListView.getSelectionModel().getSelectedItem();
    		if (file != null) {
    			file_ops.deleteLog(file);
    			logListView.getItems().remove(file);
    		} else {
    			System.out.println("No file selected! Please select a file!");
    		}
    	});
    	
    	archiveLog.setOnAction(e -> {
    		String file = logListView.getSelectionModel().getSelectedItem();
    		if (file != null) {
    			file_ops.archiveLog(file);
    			logListView.getItems().remove(file);
    		} else {
    			System.out.println("No file selected! Please select a file!");
    		}
    	});

    	moveLog.setOnAction(e -> {
    		String file = logListView.getSelectionModel().getSelectedItem();
    		String targetDir = targetField.getText().trim();
    		if (file != null) {
    			file_ops.moveLog(file, targetDir);
    			logListView.getItems().remove(file);
    		} else {
    			System.out.println("No file selected! Please select a file!");
    		}
    	});
    	
    	logListView.setOnMouseClicked(event -> {
    		if (event.getClickCount() == 2) {
    			String file = logListView.getSelectionModel().getSelectedItem();
    			if (file != null) {
    				file_ops.openLog(file, logContents);
    			}
    		}
    	});
    	
    	GridPane grid = new GridPane();
    	grid.setVgap(15);
    	grid.setHgap(15);
    	grid.setPadding(new Insets(40));
    	
    	Pane border1 = new Pane();
    	border1.setStyle("-fx-border-color: black; -fx-border-width: 2px");
    	border1.setPadding(new Insets(5));
    	
    	Pane border2 = new Pane();
    	border2.setStyle("-fx-border-color: black; -fx-border-width: 2px");
    	border2.setPadding(new Insets(5));
    	
    	grid.add(border1, 0, 0, 4, 5);
    	grid.add(border2, 0, 9, 4, 10);
    	grid.add(weightLabel, 1, 1);
    	grid.add(weightField, 2, 1);
    	grid.add(operationBox, 2, 2);
    	grid.add(takeOrder, 2, 3);
    	grid.add(searchLabel, 1, 10);
    	grid.add(searchField, 2, 10);
    	grid.add(searchLog, 2, 11);
    	grid.add(deleteLog, 2, 12);
    	grid.add(archiveLog, 2, 13);
    	grid.add(moveLog, 2, 14);
    	grid.add(moveLabel, 1, 15);
    	grid.add(targetField, 2, 15);
    	grid.add(logListView, 1, 16, 2, 2);
    	grid.add(logContents, 4, 0, 10, 19);
    	
    	Scene scene = new Scene(grid, 400, 400);
    	mainStage.setTitle("Order Creation");
    	mainStage.setScene(scene);
    	mainStage.show();
    	
    	
    	
    	
    	
    	
    }
    
    
}
*/