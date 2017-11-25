package application;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class UserController extends Person implements Initializable {

	String textfield_LoginPage;

	ArrayList<String> seatsArray = new ArrayList<String>();

	@FXML
	private ComboBox<String> SelectFilmCB;

	@FXML
	private ComboBox<String> SelectTimeCB;

	@FXML
	private DatePicker dateSelector;

	@FXML
	private Label Uname;

	@FXML
	private Button startOver;

	@FXML
	private Label DateLabel;

	@FXML
	void startsOver(ActionEvent event) {
		startAgain(dateSelector, SelectFilmCB, SelectTimeCB);
		SeatsPane.setOpacity(0);

	}

	@FXML
	void dateChosen(ActionEvent event) throws FileNotFoundException {
		LocalDate today = LocalDate.now();

		if (dateSelector.getValue().isBefore(today)) {
			DateLabel.setOpacity(1);
		} else {
			listFilms(dateSelector, SelectFilmCB);
			DateLabel.setOpacity(0);
		}

	}

	@FXML
	void filmSelected(ActionEvent event) throws FileNotFoundException {
		filmPicked(SelectFilmCB, SelectTimeCB);

	}

	@FXML
	void timeSelected(ActionEvent event) {
		SelectTimeCB.setDisable(true);

	}

	@FXML
	private Button FilmInfoBtn;

	@FXML
	private Button seatsBtn;

	@FXML
	void seatsChosen(ActionEvent event) throws IOException, FileNotFoundException {
		SeatsPane.setOpacity(1);

		seatDisplay(dateSelector.getValue().toString(), SelectTimeCB.getValue(), A1, A2, A3, A4, B1, B2, B3, B4, C1, C2,
				C3, C4, D1, D2, D3, D4, E1, E2, E3, E4);

		try (FileWriter fw = new FileWriter("UserData.csv", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.print("\n" + "Username:" + textfield_LoginPage + "," + dateSelector.getValue().toString() + ","
					+ SelectTimeCB.getValue() + "," + SelectFilmCB.getValue() + ",");
		} catch (IOException e) {
			System.out.println("issue with updating user data");
		}

	}

	@FXML
	private Tab ActivityTab;

	@FXML
	private Button EditBTN;

	@FXML
	private Label Uname1;

	@FXML
	private ListView<String> ActivityList;

	@FXML
	private Label pastBookingLabel;

	@FXML
	private Label futureBookingLabel;

	@FXML
	private Label thankYou;

	@FXML
	private ComboBox<Integer> RatingCB;

	@FXML
	private Button RatingDoneBTN;

	@FXML
	void RateFilm(ActionEvent event) throws ParseException {
		RatingCB.setOpacity(1);
		RatingDoneBTN.setOpacity(1);
		thankYou.setOpacity(0);
		futureBookingLabel.setOpacity(0);

		RatingCB.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

	}

	@FXML
	void RatingDone(ActionEvent event) throws ParseException {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date today = new Date(System.currentTimeMillis());
		String[] splitArray = (ActivityList.getSelectionModel().getSelectedItem()).split(",");
		String dateString = splitArray[1].toString();
		Date selectedDate = df.parse(dateString);

		if (selectedDate.before(today)) {
			futureBookingLabel.setOpacity(0);

			try (FileWriter fw = new FileWriter("Ratings.csv", true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter(bw)) {
				out.print("\n" + splitArray[3] + "," + RatingCB.getSelectionModel().getSelectedItem());
			} catch (IOException e) {
				System.out.println("issue with rating film");
			}

			RatingCB.setOpacity(0);
			RatingDoneBTN.setOpacity(0);
			thankYou.setOpacity(1);
		}

		else if (selectedDate.after(today)) {
			futureBookingLabel.setOpacity(1);
		}

	}

	@FXML
	void EditBookingPressed(ActionEvent event) throws ParseException, FileNotFoundException {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date today = new Date(System.currentTimeMillis());
		String[] splitArray = (ActivityList.getSelectionModel().getSelectedItem()).split(",");
		String dateString = splitArray[1].toString();
		Date selectedDate = df.parse(dateString);
		System.out.println(ActivityList.getSelectionModel().getSelectedItem());
		String selectedLine = splitArray[0].toString() + "," + splitArray[1].toString() + "," + splitArray[2].toString()
				+ "," + splitArray[3].toString();

		if (selectedDate.before(today)) {
			System.out.println("this date is in the past");
			pastBookingLabel.setOpacity(1);
		}

		else if (selectedDate.after(today)) {
			pastBookingLabel.setOpacity(0);
			System.out.println("this date is in the future");
			removeLineFromFile("UserData.csv", selectedLine);
			refresh();
		}

	}

	public void FillActivity() throws FileNotFoundException {

		FileReader file = new FileReader("UserData.csv");

		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.contains("Username:" + textfield_LoginPage)) {
				ActivityList.getItems().add(line);
			}
		}

	}

	public void refresh() throws FileNotFoundException {
		ActivityList.getItems().clear();

		FileReader file = new FileReader("UserData.csv");
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.contains("Username:" + textfield_LoginPage)) {
				ActivityList.getItems().add(line);
			}
		}
	}

	@FXML
	private Tab BookingTab;

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
	private Button doneBTN;

	@FXML
	void finishedSelection(ActionEvent event) throws IOException {
		refresh();

		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Confirm.fxml"));
		Region root = (Region) loader.load();
		Scene scene = new Scene(root, 395, 466);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);

		ConfirmController confirmController = loader.<ConfirmController>getController();
		confirmController.setConfirmation(dateSelector.getValue().toString(), SelectTimeCB.getValue(),
				String.join(", ", seatsArray), SelectFilmCB.getValue());
		primaryStage.show();

		startAgain(dateSelector, SelectFilmCB, SelectTimeCB);
		SeatsPane.setOpacity(0);

	}

	@FXML
	void seatClicked(ActionEvent event) {

		Button source = (Button) event.getSource();
		String buttonID = source.getId();

		try (FileWriter fw = new FileWriter("UserData.csv", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.print(buttonID + " ");
			seatsArray.add(buttonID);
		} catch (IOException e) {

		}
		source.setVisible(false);
	}

	@FXML
	private Tab UpdateTab;

	@FXML
	public Label Uname2;

	@FXML
	public Label updated;

	@FXML
	private TextField firstName;

	@FXML
	private TextField LastName;

	@FXML
	private TextField Email;

	@FXML
	private Label updatedInfo;

	@FXML
	private Button UpdateBtn;

	@FXML
	private AnchorPane newPassword;

	@FXML
	private TextField newPasswordText;

	@FXML

	void updateInfoPressed(ActionEvent event) throws Exception {

		if ((!firstName.getText().equals("")) && (!LastName.getText().equals("")) && (!Email.getText().equals(""))) {
			updatedInfo.setOpacity(1);
			updateMyInfo();
			updatedInfo.setText("New Info: \n First name: " + firstName.getText() + "\n Last name: "
					+ LastName.getText() + "\n Email: " + Email.getText());

		} else {
			System.out.println("presses");
			updatedInfo.setText("Please complete all fields");
		}

	}

	public void updateMyInfo() throws Exception {

		List<String> fileContent = new ArrayList<>(
				Files.readAllLines(Paths.get("UserNameEmail.csv"), StandardCharsets.UTF_8));

		for (int i = 0; i < fileContent.size(); i++) {
			if (fileContent.get(i).contains("UName:" + textfield_LoginPage)) {
				fileContent.set(i, "UName:" + textfield_LoginPage + ", First name:" + firstName.getText()
						+ ", Last name:" + LastName.getText() + ", Email:" + Email.getText());
				break;
			}
		}

		Files.write(Paths.get("UserNameEmail.csv"), fileContent, StandardCharsets.UTF_8);
	}

	@FXML
	void changePassword(ActionEvent event) {
		newPassword.setOpacity(1);

	}

	@FXML
	void donePasswordChange(ActionEvent event) throws FileNotFoundException {
		if (newPasswordText.getText().equals("")) {
			newPasswordText.setText("FILL IN NEW PASSWORD");
		} else {
			FileReader file = new FileReader("Login.csv");
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.contains("UName:" + textfield_LoginPage)) {
					removeLineFromFile("Login.csv", line);

					try (FileWriter fw = new FileWriter("Login.csv", true);
							BufferedWriter bw = new BufferedWriter(fw);
							PrintWriter out = new PrintWriter(bw)) {
						out.print("\nUName:" + textfield_LoginPage + " Password:" + newPasswordText.getText());
					} catch (IOException e) {
						System.out.println("issue with updating user password");
					}

					newPassword.setOpacity(0);
					newPasswordText.clear();
					updated.setOpacity(1);

				}
			}

		}

	}

	@FXML
	private ComboBox<String> filminfoCB;

	@FXML
	private Tab FilmInfoTab;

	@FXML
	private ImageView film1Image;

	@FXML
	private Button testbtn;

	@FXML
	private Label filmTitle;

	@FXML
	private Label filmDescription;

	@FXML
	private Label ratingLabel;

	@FXML
	private Label Uname3;

	@FXML
	void test(ActionEvent event) throws FileNotFoundException {
		ratingLabel.setOpacity(0);

		FileReader file = new FileReader("FilmInfo.csv");
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();

			if (line.contains(filminfoCB.getValue())) {
				String[] array = line.split(",");

				filmTitle.setOpacity(1);
				filmTitle.setText(array[0]);
				filmTitle.setWrapText(true);
				filmDescription.setOpacity(1);
				filmDescription.setText(array[2] + "\n" + array[3] + "\nRun Dates: " + array[5] + " to " + array[6]);
				filmDescription.setWrapText(true);

				String pictureurl = array[1];
				Image image1 = new Image(pictureurl);
				film1Image.setImage(image1);

			}

		}

		FileReader file2 = new FileReader("Ratings.csv");
		Scanner scanner2 = new Scanner(file2);
		ArrayList<Integer> list = new ArrayList<Integer>();
		Integer allAdded = 0;

		while (scanner2.hasNextLine()) {
			String line = scanner2.nextLine();
			if (line.contains(filminfoCB.getValue())) {

				System.out.println(line);
				String[] array = line.split(",");
				allAdded+= Integer.parseInt(array[1]);
//				list.add(rating);
				System.out.println(allAdded);
				list.add(allAdded);	
			}
		}

		if(list.size()>0) {
		Integer average = (allAdded / list.size());
		System.out.println(average);
		ratingLabel.setText("Customer Rating: " + average + "/10");
		ratingLabel.setOpacity(1);
		}

	}

	public void fillInfoCB() throws FileNotFoundException {
		ArrayList<String> movies = new ArrayList();
		FileReader file = new FileReader("FilmInfo.csv");
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] data = line.split(",");
			movies.add(data[0]);
		}
		filminfoCB.getItems().addAll(movies);
	}

	@FXML
	private Tab LogoutTab;

	@FXML
	private Button LogoutBTN;

	@FXML
	void userLogsOut(ActionEvent event) throws Exception {

		LogOut(LogoutBTN);

	}

	public void setUsername(String usrname) {
		this.textfield_LoginPage = usrname;
		Uname.setText("Welcome, " + usrname);
		Uname1.setText("Welcome, " + usrname);
		Uname2.setText("Welcome, " + usrname);
		Uname3.setText("Welcome, " + usrname);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		Uname.setText("Welcome, " + this.textfield_LoginPage);
		Uname1.setText("Welcome, " + this.textfield_LoginPage);
		Uname2.setText("Welcome, " + this.textfield_LoginPage);
		Uname3.setText("Welcome, " + this.textfield_LoginPage);
	}

}
