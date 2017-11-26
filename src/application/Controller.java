package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {

	@FXML
	private AnchorPane anchor_LoginPage;

	@FXML
	private AnchorPane anchor2_LoginPage;

	@FXML
	private Button LoginBtn;

	@FXML
	private TextField textfield_LoginPage;

	@FXML
	private PasswordField PswdField_LoginPage;

	@FXML
	private Label label1_LoginPage;
	
	@FXML
	private Label regLabel;
	
	@FXML
    private Button RegBtn;

    @FXML
    private AnchorPane regPane;

    @FXML
    private TextField lName;

    @FXML
    private TextField email;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField fName;


	@FXML
	void Login(ActionEvent event) throws Exception {
		Checksfile();
		regLabel.setOpacity(0);

	}
	
	
	   @FXML
	    void doneRegister(ActionEvent event) throws FileNotFoundException {
		   boolean canReg=true;
		   
			FileReader file = new FileReader("Login.csv");
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.contains("UName:" + username.getText()+ " Password:" + password.getText())) {
					username.setText("Try different combo.");
					canReg=false;

				}
				
			}
			
		   
		   
		   
			 if(!fName.getText().equals("") && !lName.getText().equals("") 
				   && !email.getText().equals("") && !username.getText().equals("")
				   && !password.getText().equals("")) {
			   if(canReg==true) {
			   try (FileWriter fw = new FileWriter("Login.csv", true);
						BufferedWriter bw = new BufferedWriter(fw);
						PrintWriter out = new PrintWriter(bw)) {
					out.print("\nUName:"+ username.getText() +" Password:"+ password.getText());
				} catch (IOException e) {
					System.out.println("issue with adding login details");
				}
			   
			   try (FileWriter fw = new FileWriter("UserNameEmail.csv", true);
						BufferedWriter bw = new BufferedWriter(fw);
						PrintWriter out = new PrintWriter(bw)) {
					out.print("\nUName:"+ username.getText()+ ", First name:"+ fName.getText()
					+ ", Last name:" + lName.getText()+ ", Email:"+ email.getText());
				} catch (IOException e) {
					System.out.println("issue with adding name and email");
				}
			   
			   regPane.setOpacity(0);
			   regLabel.setOpacity(1);
			   }   
		   }
		   
		   else {
			   fName.setText("FILL ALL FIELDS!");
		   }
		   

	    }
	   
	   @FXML
	    void registerNow(ActionEvent event) {
		   regPane.setOpacity(1);
		   
	   }
	   
	    @FXML
	    void closeX(ActionEvent event) {
	    	regPane.setOpacity(0);

	    }

	public void Checksfile() throws Exception {
		if (textfield_LoginPage.getText().equals("")) {
			textfield_LoginPage.setText("Try Again.");

		}

		if (PswdField_LoginPage.getText().equals("")) {
			textfield_LoginPage.setText("Try Again.");

		}

		boolean isValid = false;

		File file = new File("Login.csv");
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
		

			if (line.equals(("UName:" + textfield_LoginPage.getText()+ " Password:" + PswdField_LoginPage.getText() ))) {
					isValid = true;

					openUserPage();

			} else if (line.equals(("AName:" + textfield_LoginPage.getText()+ " Password:" + PswdField_LoginPage.getText() ))) {
				
					isValid = true;

					openAdminPage();

			}

		}
		scanner.close();

		if (isValid == false) {
			textfield_LoginPage.setText("Try Again.");
		}
	}

	public void openUserPage() throws Exception {

		Stage stage = (Stage) LoginBtn.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserPage.fxml"));
		Region root = (Region) loader.load();
		Scene scene = new Scene(root, 700, 460);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);

		UserController userController = loader.<UserController>getController();
		userController.setUsername(textfield_LoginPage.getText());
		userController.FillActivity();
		userController.fillInfoCB();

		primaryStage.show();

	}

	public void openAdminPage() throws Exception {
		
		Stage stage = (Stage) LoginBtn.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPage.fxml"));
		Region root = (Region) loader.load();
		Scene scene = new Scene(root, 700, 465);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);

		AdminController adminController = loader.<AdminController>getController();
		adminController.FillFilms();
		adminController.FillTimes();
		adminController.fillFeedback();
		adminController.fillCtsomerActivity();

		primaryStage.show();
		
		

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
