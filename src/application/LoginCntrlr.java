package application;

import javafx.event.ActionEvent;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import application.server;
public class LoginCntrlr implements Initializable {
	
	@FXML
    private TextField usernameid;

    @FXML
    private PasswordField passwordid;

    @FXML
    private Button loginB;

    @FXML
    private Button registerB;

    @FXML
    private ImageView loading;
	
    private PreparedStatement st;
  
	@Override
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		loading.setVisible(true);
		
	}
	public static void start_server() {server s=new server();
	s.start();}
	
	@FXML
	public void loginAction(ActionEvent e) {
		String url = "jdbc:mysql://localhost:3306/Probase";
		String user = "root";
		String password = "tpjava2022";
		loginB.setOnAction(event->{
			Connection cn = null;
			try { cn = DriverManager.getConnection(url, user, password);
			String sql = "SELECT username,pass from base where username=? and pass=?";
		    st= cn.prepareStatement(sql);
			st.setString(1,usernameid.getText());
			st.setString(2,passwordid.getText());
			ResultSet rs=st.executeQuery();
			int count=0;
			while(rs.next()) {
				count++;
			}
				
				if(count==1) {
				loginB.getScene().getWindow().hide();
				Stage registered = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/application/Communication.fxml")) ;
			registered.setTitle("Gestion de stock");
				Scene scene = new Scene(root);
				registered.setScene(scene);
				registered.show();
				start_server();
				registered.setResizable(false);}
				else {
				
			javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText("Error:");
			alert.setContentText("Username/Password is incorrect or doesn't figure in the database");
			alert.showAndWait();	}}
			catch(Exception ev) {ev.printStackTrace();}
	});}

	
	@FXML
	public void RegisterA(ActionEvent e) throws IOException {
		
		loginB.getScene().getWindow().hide();
		Stage register = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Register.fxml")) ;
		Scene scene = new Scene(root);
		register.setTitle("Signup");
		register.setScene(scene);
		register.show();
		register.setResizable(false);
		
	}
	
	
}
