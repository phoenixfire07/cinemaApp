package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ConfirmController implements Initializable {

	String date;
	String filmInfo;
	String seats;
	String title; 

	@FXML
	private Label ConfirmLabel;

	public void setConfirmation(String date, String filmInfo, String seats, String title) {
		this.date = date;
		this.filmInfo = filmInfo;
		this.seats = seats;

		ConfirmLabel.setText(
				"Booking Confirmed\n\n" + "Date: \n" + date + "\n\nFilm Info: \n" + title+"\n"+filmInfo + "\n\nSeats:\n" + seats);
		ConfirmLabel.setWrapText(true);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
