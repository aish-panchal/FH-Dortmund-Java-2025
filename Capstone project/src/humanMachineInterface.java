
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.application.*;
import java.text.*;
import java.util.*;
import java.io.*;
import java.io.File.*;
import java.nio.file.*;
import java.util.regex.*;
import java.util.stream.Stream;



public class humanMachineInterface extends Application{
    
	
	public static void main(String[] args) {
		launch(args);
	}
    
    public void start(Stage mainStage) {
    	// creation of the taskmanagement object to start taking orders
    	Date today = new Date();
    	taskManagement workday = new taskManagement(today,50);
    	
    	// creating label for weight prompt
    	Label weightLabel = new Label("Enter order weight:");
    	
    	// creating label for AVG information
    	Label avgInfoLabel = new Label("AVG information and status");
    	
    	// creating label for charging station information
    	Label chargeStationLabel = new Label("Charging station information and status");
    	
    	// creating label for storage inventory
    	Label inventoryLabel = new Label("Storage Inventory information");
    	
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
    	
    	// create label for file moving operation
    	Label moveLabel = new Label("Move Log File:");
    	TextField targetField = new TextField();
    	targetField.setMaxWidth(200);
    	targetField.setPromptText("Enter target folder");
    	
    	// creating a listing of the log files
    	ListView<String> logListView = new ListView<>();
    	logListView.setMaxHeight(200);
    	logListView.setMaxWidth(200);
    	logListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	
    	// creating a listing of the created avgs
    	ListView<avg> vehiclesListView = new ListView<>();
    	vehiclesListView.setMaxHeight(200);
    	vehiclesListView.setMaxWidth(200);
    	vehiclesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    
    	
    	// creating area to view log file contents
    	TextArea logContents = new TextArea();
    	logContents.setEditable(false);
    	logContents.setWrapText(false);
    	logContents.setMaxHeight(1100);
    	logContents.setMaxWidth(500);
    	
    	// creating area to view avg information
    	TextArea vehiclesInfo = new TextArea();
    	vehiclesInfo.setEditable(false);
    	vehiclesInfo.setMaxHeight(200);
    	vehiclesInfo.setMaxWidth(200);
    	
    	// creating area to view charging station information
    	TextArea chargeStationInfo = new TextArea();
    	chargeStationInfo.setEditable(false);
    	chargeStationInfo.setMaxHeight(80);
    	chargeStationInfo.setMaxWidth(300);
    	
    	// creating area to view storage inventory information
    	TextArea inventoryInfo = new TextArea();
    	inventoryInfo.setEditable(false);
    	inventoryInfo.setMaxHeight(120);
    	inventoryInfo.setMaxWidth(300);
    	
    	// show information of selected avg in the relevant text box
    	vehiclesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldAvg, newAvg) -> {
    		if (newAvg != null) {
    			vehiclesInfo.setText(newAvg.getInfo());
    		}
    	});
    	
    	// creating timer to run constantly to update vehicle and inventory info in the GUI
    	Timer timer = new Timer();
    	timer.scheduleAtFixedRate(new TimerTask() {
    		public void run() {
    			Platform.runLater(() -> {
    				chargeStationInfo.setText(workday.charge.getInfo());
    				inventoryInfo.setText(workday.store.toString());
    				
    			});
    		}
    	}, 0, 100);
    	
    	
    	// section to trigger the method call to takeOrder
    	takeOrder.setOnAction(e -> {
    		try {
    			// get weight and operation from the text and drop down boxes
    			int weight = Integer.parseInt(weightField.getText());
    			String operation = operationBox.getValue();
    			
    			
    			workday.takeOrder(weight, operation);
    			
    			if (weight > workday.store.raw_materials_stored && operation == "toFactory") {
    				Alert alert1 = new Alert(AlertType.ERROR);
    				alert1.setTitle("Error");
    				alert1.setHeaderText("Insufficient stock");
    				alert1.setContentText("Please enter a weight lower than the raw material inventory");
    				
    				alert1.showAndWait();
    			} 
    			
    			if (weight > workday.store.processed_materials_stored && operation == "toDelivery") {
    				Alert alert2 = new Alert(AlertType.ERROR);
    				alert2.setTitle("Error");
    				alert2.setHeaderText("Insufficient stock");
    				alert2.setContentText("Please enter a weight lower than the processed materials inventory");
    				
    				alert2.showAndWait();
    			}
    			
    			if (weight > workday.store.free_storage && operation == "toWarehouse") {
    				Alert alert3 = new Alert(AlertType.ERROR);
    				alert3.setTitle("Error");
    				alert3.setHeaderText("Insufficient warehouse space");
    				alert3.setContentText("Please enter a weight lower than the remaining warehouse space");
    				
    				alert3.showAndWait();
    			}
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
			} catch (InterruptedException e1) {
				System.out.println("Interrupted exception: " + e1.getMessage());
				e1.printStackTrace();
			} 
    	});
    	
    	// section to trigger call to search log
    	searchLog.setOnAction(e -> {
    		String searchTerm = searchField.getText();
    		if(!searchTerm.isEmpty()) {
    			file_ops.searchLog(searchTerm, logListView);
    		} else {
    			System.out.println("Please enter a search term.");
    		}
    	});
    	
    	// section to trigger call to delete log
    	deleteLog.setOnAction(e -> {
    		String file = logListView.getSelectionModel().getSelectedItem();
    		if (file != null) {
    			file_ops.deleteLog(file);
    			logListView.getItems().remove(file);
    		} else {
    			System.out.println("No file selected! Please select a file!");
    		}
    	});
    	
    	// section to trigger call to archive log
    	archiveLog.setOnAction(e -> {
    		String file = logListView.getSelectionModel().getSelectedItem();
    		if (file != null) {
    			file_ops.archiveLog(file);
    			logListView.getItems().remove(file);
    		} else {
    			System.out.println("No file selected! Please select a file!");
    		}
    	});
    	
    	// section to trigger call to move log
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
    	
    	// section to trigger call to open the log file by double click
    	logListView.setOnMouseClicked(event -> {
    		if (event.getClickCount() == 2) {
    			String file = logListView.getSelectionModel().getSelectedItem();
    			if (file != null) {
    				file_ops.openLog(file, logContents);
    			}
    		}
    	});
    	
    	// adding all the created avgs to the list view
    	ObservableList<avg> observableVehicles = FXCollections.observableArrayList(workday.vehicles);
    	vehiclesListView.setItems(observableVehicles);
    	
    	// creation of grid pane to set the base for the GUI
    	GridPane grid = new GridPane();
    	grid.setVgap(15);
    	grid.setHgap(15);
    	grid.setPadding(new Insets(40));
    	
    	// creation of the borders for each individual section of the GUI
    	Pane border1 = new Pane();
    	border1.setStyle("-fx-border-color: black; -fx-border-width: 2px");
    	border1.setPadding(new Insets(5));
    	
    	Pane border2 = new Pane();
    	border2.setStyle("-fx-border-color: black; -fx-border-width: 2px");
    	border2.setPadding(new Insets(5));
    	
    	Pane border3 = new Pane();
    	border3.setStyle("-fx-border-color: black; -fx-border-width: 2px");
    	border3.setPadding(new Insets(5));
    	
    	Pane border4 = new Pane();
    	border4.setStyle("-fx-border-color: black; -fx-border-width: 2px");
    	border4.setPadding(new Insets(5));
    	
    	Pane border5 = new Pane();
    	border5.setStyle("-fx-border-color: black; -fx-border-width: 2px");
    	border5.setPadding(new Insets(5));
    	
    	// adding each individual element to the GUI
    	grid.add(border1, 0, 0, 4, 5);
    	grid.add(border2, 0, 9, 4, 10);
    	grid.add(border3, 7, 0, 8, 7);
    	grid.add(border4, 7, 9, 8, 5);
    	grid.add(border5, 7, 15, 8, 5);
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
    	grid.add(logContents, 4, 0, 3, 19);
    	grid.add(vehiclesListView, 8, 2, 2, 2);
    	grid.add(vehiclesInfo, 11, 2, 2, 2);
    	grid.add(avgInfoLabel, 8, 1);
    	grid.add(chargeStationLabel, 8, 10);
    	grid.add(chargeStationInfo, 8, 11, 2, 2);
    	grid.add(inventoryLabel, 8, 16);
    	grid.add(inventoryInfo, 8, 17, 2, 2);
    	
    	// creating the scene which is displayed when the code is run 
    	Scene scene = new Scene(grid, 500, 500);
    	mainStage.setTitle("Order Creation");
    	mainStage.setScene(scene);
    	mainStage.show();
    	
    }
    
}
