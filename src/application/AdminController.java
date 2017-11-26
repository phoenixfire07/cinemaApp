package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class AdminController extends Person implements Initializable {

	@FXML
	private Tab BookingTab;

	@FXML
	private ComboBox<String> SelectFilmCB;

	@FXML
	private ComboBox<String> SelectTimeCB;

	@FXML
	private DatePicker dateSelector;

	@FXML
	private Button seatsBtn;

	@FXML
	private AnchorPane SeatsPane;

	@FXML
	private GridPane seatsGrid;

	@FXML
	private Button A1;

	@FXML
	private Button A2;

	@FXML
	private Button A3;

	@FXML
	private Button A4;

	@FXML
	private Button B1;

	@FXML
	private Button B2;

	@FXML
	private Button B3;

	@FXML
	private Button C1;

	@FXML
	private Button C2;

	@FXML
	private Button C3;

	@FXML
	private Button B4;

	@FXML
	private Button C4;

	@FXML
	private Button D1;

	@FXML
	private Button D2;

	@FXML
	private Button D3;

	@FXML
	private Button D4;

	@FXML
	private Button E1;

	@FXML
	private Button E2;

	@FXML
	private Button E3;

	@FXML
	private Button E4;

	@FXML
	private Label seatsBooked;

	@FXML
	private Button startOver;

	@FXML
	void dateChosen(ActionEvent event) throws FileNotFoundException {
		// datePicked(dateSelector, SelectFilmCB);
		listFilms(dateSelector, SelectFilmCB);
	}

	@FXML
	void filmSelected(ActionEvent event) throws FileNotFoundException {
		filmPicked(SelectFilmCB, SelectTimeCB);

	}

	@FXML
	void seatsChosen(ActionEvent event) throws FileNotFoundException {
		SeatsPane.setOpacity(1);
		
		
		seatDisplay(dateSelector.getValue().toString(), SelectTimeCB.getValue(), A1, A2, A3, A4, B1, B2, B3, B4, C1, C2,
				C3, C4, D1, D2, D3, D4, E1, E2, E3, E4);

		seatsGone();

	}

	public void seatsGone() {
		int count = 0;
		if (A1.getOpacity() == 0)
			count++;
		if (A2.getOpacity() == 0)
			count++;
		if (A3.getOpacity() == 0)
			count++;
		if (A4.getOpacity() == 0)
			count++;
		if (B1.getOpacity() == 0)
			count++;
		if (B2.getOpacity() == 0)
			count++;
		if (B3.getOpacity() == 0)
			count++;
		if (B4.getOpacity() == 0)
			count++;
		if (C1.getOpacity() == 0)
			count++;
		if (C2.getOpacity() == 0)
			count++;
		if (C3.getOpacity() == 0)
			count++;
		if (C4.getOpacity() == 0)
			count++;
		if (D1.getOpacity() == 0)
			count++;
		if (D2.getOpacity() == 0)
			count++;
		if (D3.getOpacity() == 0)
			count++;
		if (D4.getOpacity() == 0)
			count++;
		if (E1.getOpacity() == 0)
			count++;
		if (E2.getOpacity() == 0)
			count++;
		if (E3.getOpacity() == 0)
			count++;
		if (E4.getOpacity() == 0)
			count++;

		seatsBooked.setText("Seats Booked: " + (count));
	}

	@FXML
	void timeSelected(ActionEvent event) {
		SelectTimeCB.setDisable(true);

	}

	@FXML
	void startsOver(ActionEvent event) {
		startAgain(dateSelector, SelectFilmCB, SelectTimeCB);
		SeatsPane.setOpacity(0);

	}

	@FXML
	private Tab FilmInfoTab;

	@FXML
	private TextField ImageURL;

	@FXML
	private TextArea FileDescription;

	@FXML
	private TextField filmRating;

	@FXML
	private TextField filmTitle;

	@FXML
	private ListView<String> CurrentFilmsList;

	@FXML
	private Label NewFilmLabel;

	@FXML
	private Button addFilm;

	@FXML
	private DatePicker startSelected;

	@FXML
	private DatePicker endSelected;

	@FXML
	private ComboBox<String> timePicked;

	@FXML
	private Label ClashLabel;

	@FXML
	private Label filmAddedLabel;

	@FXML
	void FilmToReplacePickedFilmToReplacePicked(ActionEvent event) {

		NewFilmLabel.setOpacity(1);
		filmTitle.setOpacity(1);
		filmRating.setOpacity(1);
		FileDescription.setOpacity(1);
		ImageURL.setOpacity(1);
		addFilm.setOpacity(1);
		startSelected.setOpacity(1);
		endSelected.setOpacity(1);
		timePicked.setOpacity(1);

	}

	@FXML
	void addFilmPressed(ActionEvent event) throws IOException {
		ClashLabel.setOpacity(0);
		FileReader file = new FileReader("FilmInfo.csv");
		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] lineArray = line.split(",");

			LocalDate startdate = LocalDate.parse(lineArray[5]);
			LocalDate enddate = LocalDate.parse(lineArray[6]);

			if (!filmTitle.getText().equals("") && !ImageURL.getText().equals("")
					&& !FileDescription.getText().equals("") && !filmRating.getText().equals("")
					&& !timePicked.getValue().equals("") && !startSelected.getValue().equals("")
					&& !endSelected.getValue().equals("")) {

				if (lineArray[4].equals(timePicked.getValue())) {
					if ((startSelected.getValue().isBefore(startdate) && endSelected.getValue().isBefore(startdate))
							|| ((startSelected.getValue().isAfter(enddate)
									&& endSelected.getValue().isAfter(enddate)))) {

						try (FileWriter fw = new FileWriter("FilmInfo.csv", true);
								BufferedWriter bw = new BufferedWriter(fw);
								PrintWriter out = new PrintWriter(bw)) {
							out.print("\n" + filmTitle.getText() + "," + ImageURL.getText() + ","
									+ FileDescription.getText() + "," + filmRating.getText() + ","
									+ timePicked.getValue() + "," + startSelected.getValue() + ","
									+ endSelected.getValue());
						} catch (IOException e) {
							System.out.println("issue with updating film info");
						}
						
						
						filmAddedLabel.setOpacity(1);
						ClashLabel.setOpacity(0);
						NewFilmLabel.setOpacity(0);
						filmTitle.setOpacity(0);
						filmRating.setOpacity(0);
						FileDescription.setOpacity(0);
						ImageURL.setOpacity(0);
						addFilm.setOpacity(0);
						startSelected.setOpacity(0);
						endSelected.setOpacity(0);
						timePicked.setOpacity(0);

					}

					else {
						ClashLabel.setText("DATES CLASH!");
						ClashLabel.setOpacity(1);
					}
				}

			}

			else {
				ClashLabel.setText("Fill All Fields");
				ClashLabel.setOpacity(1);
			}
		}

		FillFilms();
		
		
		filmTitle.setText("");
		startSelected.getEditor().clear();
		endSelected.getEditor().clear();
		filmRating.setText("");
		FileDescription.setText("");
		ImageURL.setText("");
		timePicked.getSelectionModel().clearSelection();

	}

	public void FillFilms() throws FileNotFoundException {

		FileReader file = new FileReader("FilmInfo.csv");
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] splitLine = line.split(",");
			CurrentFilmsList.getItems()
					.add(splitLine[0] + "," + splitLine[4] + ", " + splitLine[5] + " to " + splitLine[6]);
		}
	}

	public void FillTimes() throws FileNotFoundException {
		FileReader file = new FileReader("FilmTime.csv");
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] splitLine = line.split(",");
			timePicked.getItems().add(line);
		}
	}

	 	@FXML
	    private Tab ExportTab;

	    @FXML
	    private ComboBox<String> ExportTimeCB;

	    @FXML
	    private ComboBox<String> exportFilmCB;

	    @FXML
	    private DatePicker exportDate;

	    @FXML
	    private Button addExportBTN;

	    @FXML
	    private Button startOver1;

	    @FXML
	    private ListView<String> exportList;
	    
	    @FXML
	    void ExportDateChosen(ActionEvent event) {
	    	try {
				listFilms (exportDate, exportFilmCB);
			} catch (FileNotFoundException e) {
				System.out.println("Films not added correctly");
				e.printStackTrace();
			}

	    }

	    @FXML
	    void ExportFilmSelected(ActionEvent event) {
	    	try {
				filmPicked(exportFilmCB, ExportTimeCB);
			} catch (FileNotFoundException e) {
				System.out.println("Times not added correctly");
				e.printStackTrace();
			}

	    }

	    @FXML
	    void ExportTimeSelected(ActionEvent event) {
	    	ExportTimeCB.setDisable(true);

	    }

	    @FXML
	    void addToExportList(ActionEvent event) throws FileNotFoundException {
	    	SeatsPane.setOpacity(0);
		seatDisplay(exportDate.getValue().toString(), ExportTimeCB.getValue(), A1, A2, A3, A4, B1, B2, B3, B4, C1, C2,
					C3, C4, D1, D2, D3, D4, E1, E2, E3, E4);
		int count=0;
		
		if (A1.getOpacity() == 0)
			count++;
		if (A2.getOpacity() == 0)
			count++;
		if (A3.getOpacity() == 0)
			count++;
		if (A4.getOpacity() == 0)
			count++;
		if (B1.getOpacity() == 0)
			count++;
		if (B2.getOpacity() == 0)
			count++;
		if (B3.getOpacity() == 0)
			count++;
		if (B4.getOpacity() == 0)
			count++;
		if (C1.getOpacity() == 0)
			count++;
		if (C2.getOpacity() == 0)
			count++;
		if (C3.getOpacity() == 0)
			count++;
		if (C4.getOpacity() == 0)
			count++;
		if (D1.getOpacity() == 0)
			count++;
		if (D2.getOpacity() == 0)
			count++;
		if (D3.getOpacity() == 0)
			count++;
		if (D4.getOpacity() == 0)
			count++;
		if (E1.getOpacity() == 0)
			count++;
		if (E2.getOpacity() == 0)
			count++;
		if (E3.getOpacity() == 0)
			count++;
		if (E4.getOpacity() == 0)
			count++;

		exportList.getItems().add("\n"+exportFilmCB.getValue()+ "," +exportDate.getValue().toString() +","+ ExportTimeCB.getValue() +
				",Seats Booked:"+ count+ ",Seats Left: "+(20-count));

	    }
	    
	    
	  
		

	    @FXML
	    void exportFilms(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException {
	    	
	    	PrintWriter writer = new PrintWriter("FilmData.csv", "UTF-8");
	    	writer.print(exportList.getItems());
	    	writer.close();
	    	
	    	startAgain(exportDate,exportFilmCB, ExportTimeCB);
	    	exportList.getItems().clear();
	    	
	    	
	    	

	    }

	    @FXML
	    void startsOver1(ActionEvent event) {
	    	
	    	startAgain(exportDate,exportFilmCB, ExportTimeCB);
	    	

	    }
	    
	   
	    
	     

	@FXML
	private Tab UpdateTab;
	
	   @FXML
	    private TextField uName;

	    @FXML
	    private TextField pWord;

	    @FXML
	    private Label addedLBL;

	    @FXML
	    void doneAdding(ActionEvent event) throws FileNotFoundException {
	    	addedLBL.setOpacity(0);
	    	boolean canReg=true;
			   
			FileReader file = new FileReader("Login.csv");
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.contains("AName:" + uName.getText()+ " Password:" + pWord.getText())) {
					uName.setText("Try different combo.");
					canReg=false;

				}
				
			}
			
	    	if(!uName.equals("") && !pWord.equals("")) {
	    		if(canReg==true) {
	    		 try (FileWriter fw = new FileWriter("Login.csv", true);
							BufferedWriter bw = new BufferedWriter(fw);
							PrintWriter out = new PrintWriter(bw)) {
						out.print("\nAName:"+ uName.getText()+ " Password:"+ pWord.getText());
					} catch (IOException e) {
						System.out.println("issue with adding name and email");
					}
	    		 
	    		 addedLBL.setOpacity(1);
	    		 
	    	}
	    	}
	    	
	    	else {
	    		uName.setText("Fill all fields.");
	    	}

	    }
	    
	    
	    
	    
	    @FXML
	    private Tab feedbackTab;

	    @FXML
	    private ListView<String> feedbackList;
	    
	    
	    
	    
	    public void fillFeedback() throws FileNotFoundException {

			FileReader file = new FileReader("comments.txt");

			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!line.equals(null)) {
					feedbackList.getItems().add(line);
				}
			}

		}

	    
	  
	    @FXML
	    private Tab allActivityTab;

	    @FXML
	    private ListView<String> fillCustAct;
	    
	    public void fillCtsomerActivity() throws FileNotFoundException {

			FileReader file = new FileReader("UserData.csv");

			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!line.equals(null)) {
					fillCustAct.getItems().add(line);
				}
			}

		}
	    
	    

	@FXML
	private Tab LogoutTab;

	@FXML
	private Button LogoutBTN;

	@FXML
	void userLogsOut(ActionEvent event) throws Exception {
		LogOut(LogoutBTN);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
