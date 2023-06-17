package application;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RegisterCntrlr implements Initializable {

	
	@FXML
    private TextField usrnme;

    @FXML
    private TextField pswd;

    @FXML
    private RadioButton male;

    @FXML
    private RadioButton female;

    @FXML
    private TextField mail;

    @FXML
    private Button create;

    @FXML
    private Button LoginR;
    
    @FXML
    private ImageView loading;

	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		loading.setVisible(false);
		
	}
	
	
	@FXML
	public void RegisterAction(ActionEvent e1) {
		String url = "jdbc:mysql://localhost:3306/Probase";
		String user = "root";
		String password = "tpjava2022";
		create.setOnAction(event->{
		Connection cn = null;
		try { 
		String usr=usrnme.getText();
		String pas=pswd.getText();
		String email=mail.getText();
		String sexe ;
		if (male.isSelected()) {sexe="male";}else {sexe="female";}
		cn = DriverManager.getConnection(url, user, password);
		Statement st=cn.createStatement();
		String sql = "INSERT INTO Base(username,pass,sexe,mail) VALUES (?,?,?,?)";
		PreparedStatement stmt = cn.prepareStatement(sql);
		stmt.setString(1, usr);
		stmt.setString(2, pas);
		stmt.setString(3, sexe);
		stmt.setString(4, email);
		stmt.executeUpdate();
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.INFORMATION);
		alert.setTitle("Creation");
		alert.setHeaderText("Creation:");
		alert.setContentText("Account creation has been completed successfully");

		alert.showAndWait();
		} catch (SQLException ex) {
		ex.printStackTrace();
		}finally {
		if (cn != null)
		 try { cn.close();}
		catch(Exception E1) {E1.printStackTrace();}

		}});
	}
	
	@FXML
	public void LoginA(ActionEvent e2) throws IOException {
		
		loading.setVisible(true);
		LoginR.getScene().getWindow().hide();
		Stage login = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml")) ;
		Scene scene = new Scene(root);
		login.setScene(scene);
		login.show();
		login.setResizable(false);
		
	}

}
