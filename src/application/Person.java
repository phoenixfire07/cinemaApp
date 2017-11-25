package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Person {

	AnchorPane SeatsPane;
	String username;
	String date;
	String time;
	String film;
	Button A1;
	Button A2;
	Button A3;
	Button A4;
	Button B1;
	Button B2;
	Button B3;
	Button B4;
	Button C1;
	Button C2;
	Button C3;
	Button C4;
	Button D1;
	Button D2;
	Button D3;
	Button D4;
	Button E1;
	Button E2;
	Button E3;
	Button E4;
	Button btn;

	public Person() {

	}

	public void LogOut(Button btn) throws Exception {
		Stage stage = (Stage) btn.getScene().getWindow();
		stage.close();

		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
		Region root = (Region) loader.load();
		Scene scene = new Scene(root, 600, 350);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);

		primaryStage.show();

	}

	public void seatDisplay(String date, String time, Button A1, Button A2, Button A3, Button A4, Button B1, Button B2,
			Button B3, Button B4, Button C1, Button C2, Button C3, Button C4, Button D1, Button D2, Button D3,
			Button D4, Button E1, Button E2, Button E3, Button E4) throws FileNotFoundException {
		setSeatsOpacity(A1, A2, A3, A4, B1, B2, B3, B4, C1, C2,C3, C4, D1, D2, D3, D4, E1, E2, E3, E4);
		FileReader file = new FileReader("UserData.csv");
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.contains(date) && line.contains(time)) {
				
				if (line.contains("A1")) {
					A1.setOpacity(0);
					A1.setDisable(true);
				}
				if (line.contains("A2")) {
					A2.setOpacity(0);
					A2.setDisable(true);
				}
				if (line.contains("A3")) {
					A3.setOpacity(0);
					A3.setDisable(true);
				}
				if (line.contains("A4")) {
					A4.setOpacity(0);
					A4.setDisable(true);
				}
				if (line.contains("B1")) {
					B1.setOpacity(0);
					B1.setDisable(true);
				}
				if (line.contains("B2")) {
					B2.setOpacity(0);
					B2.setDisable(true);
				}
				if (line.contains("B3")) {
					B3.setOpacity(0);
					B3.setDisable(true);
				}
				if (line.contains("B4")) {
					B4.setOpacity(0);
					B4.setDisable(true);
				}
				if (line.contains("C1")) {
					C1.setOpacity(0);
					C1.setDisable(true);
				}
				if (line.contains("C2")) {
					C2.setOpacity(0);
					C2.setDisable(true);
				}
				if (line.contains("C3")) {
					C3.setOpacity(0);
					C3.setDisable(true);
				}
				if (line.contains("C4")) {
					C4.setOpacity(0);
					C4.setDisable(true);
				}
				if (line.contains("D1")) {
					D1.setOpacity(0);
					D1.setDisable(true);
				}
				if (line.contains("D2")) {
					D2.setOpacity(0);
					D2.setDisable(true);
				}
				if (line.contains("D3")) {
					D3.setOpacity(0);
					D3.setDisable(true);
				}
				if (line.contains("D4")) {
					D4.setOpacity(0);
					D4.setDisable(true);
				}
				if (line.contains("E1")) {
					E1.setOpacity(0);
					E1.setDisable(true);
				}
				if (line.contains("E2")) {
					E2.setOpacity(0);
					E2.setDisable(true);
				}
				if (line.contains("E3")) {
					E3.setOpacity(0);
					E3.setDisable(true);
				}
				if (line.contains("E4")) {
					E4.setOpacity(0);
					E4.setDisable(true);
				}

			}

		}

	}



	public void filmPicked(ComboBox SelectFilmCB, ComboBox SelectTimeCB) throws FileNotFoundException {
		SelectFilmCB.setDisable(true);

		FileReader file = new FileReader("FilmInfo.csv");
		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] lineArray = line.split(",");
			if (lineArray[0].equals(SelectFilmCB.getValue())) {
				SelectTimeCB.getItems().add(lineArray[4]);
			}

		}
	}
		
		
		
		
		
		
		
	public void listFilms (DatePicker dateSelector, ComboBox SelectFilmCB) throws FileNotFoundException {
		dateSelector.setDisable(true);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		FileReader file = new FileReader("FilmInfo.csv");
		Scanner scanner = new Scanner(file);
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] lineArray = line.split(",");
			LocalDate startdate = LocalDate.parse(lineArray[5]);
			LocalDate enddate = LocalDate.parse(lineArray[6]);
		

		if (((dateSelector.getValue()).isAfter(startdate)) && ((dateSelector.getValue()).isBefore(enddate))) {
			SelectFilmCB.getItems().add(lineArray[0]);
		}
		
		}
	
	}
	
	public void startAgain(DatePicker dateSelector,ComboBox SelectFilmCB, ComboBox SelectTimeCB) {
		 dateSelector.setDisable(false);
		 dateSelector.getEditor().clear();
		 SelectFilmCB.getItems().clear();
		 SelectFilmCB.setDisable(false);
		 SelectTimeCB.getItems().clear();
		 SelectTimeCB.setDisable(false);
		 
	}
	
	
	
	public void removeLineFromFile(String file, String lineToRemove) {

		try {
			File inFile = new File(file);
			if (!inFile.isFile()) {
				System.out.println("Parameter is not an existing file");
				return;
			}
			// Construct the new file that will later be renamed to the original filename.
			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
			BufferedReader br = new BufferedReader(new FileReader(file));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
			String line;
			// Read from the original file and write to the new
			// unless content matches data to be removed.
			while ((line = br.readLine()) != null) {
				if (!line.trim().contains(lineToRemove)) {
					pw.println(line);
					pw.flush();
				}
			}
			pw.close();
			br.close();

			// Delete the original file
			if (!inFile.delete()) {
				System.out.println("Could not delete file");
				return;
			}
			// Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile))
				System.out.println("Could not rename file");

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	public void setSeatsOpacity(Button A1, Button A2, Button A3, Button A4, Button B1, Button B2,
			Button B3, Button B4, Button C1, Button C2, Button C3, Button C4, Button D1, Button D2, Button D3,
			Button D4, Button E1, Button E2, Button E3, Button E4) {
		A1.setOpacity(1);
		A2.setOpacity(1);
		A3.setOpacity(1);
		A4.setOpacity(1);
		B1.setOpacity(1);
		B2.setOpacity(1);
		B3.setOpacity(1);
		B4.setOpacity(1);
		B1.setOpacity(1);
		B2.setOpacity(1);
		B3.setOpacity(1);
		B4.setOpacity(1);
		C1.setOpacity(1);
		C2.setOpacity(1);
		C3.setOpacity(1);
		C4.setOpacity(1);
		D1.setOpacity(1);
		D2.setOpacity(1);
		D3.setOpacity(1);
		D4.setOpacity(1);
		E1.setOpacity(1);
		E2.setOpacity(1);
		E3.setOpacity(1);
		E4.setOpacity(1);
	}
}
