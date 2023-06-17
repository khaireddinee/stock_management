package application;

import javafx.event.ActionEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import application.LoginCntrlr;
import application.server;

public class CommunicationCntrlr implements Initializable {

    private String quantite,fournisseur,libele;
	
	
	@FXML
    private TextField id;

    @FXML
    private TextField libelle;

    @FXML
    private TextField fournisseurs;

    @FXML
    private TextField qte;

    @FXML
    private Button ajouter;

    @FXML
    private Label nom1;

    @FXML
    private Label nom2;

    @FXML
    private Label nom3;

    @FXML
    private Label nom4;

    @FXML
    private Label nom5;

    @FXML
    private Label id1;

    @FXML
    private Label id2;

    @FXML
    private Label id3;

    @FXML
    private Label id4;

    @FXML
    private Label id5;

    @FXML
	private TextField chat;

    @FXML
    private Button confirmer;
public class client extends Thread {
		
		
		@Override
		public void run() {	   
		Socket socketC=null;
		try{socketC = new Socket("127.0.0.1",3750);
		InputStreamReader read=new InputStreamReader(socketC.getInputStream());
		PrintWriter out = new PrintWriter(socketC.getOutputStream());
		System.out.println("Client:Sending variable");
		String ID=chat.getText();
		System.out.printf("%s \n",ID);
		out.println(ID);
		out.flush();
		System.out.println("Variable Sent");
		System.out.println("waiting for server response");
		BufferedReader in = new BufferedReader(read);
		String info=in.readLine();
		System.out.printf("Server responded sucessfully  \n");
		String S[];
//Affichage sur UI
			id.setText(chat.getText());
			 S=info.split("/");
			libele=S[0];
			libelle.setText(libele);
			fournisseur=S[1];
			fournisseurs.setText(fournisseur);
			quantite=S[2];
			qte.setText(quantite);
	
		
		   
		}
		catch(Exception e1) {e1.printStackTrace();}
		finally {
			try {socketC.close();}catch(Exception e2) {e2.printStackTrace();}
		}}
}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
	
	
	@FXML
	public void AjouterA(ActionEvent e) throws IOException {
		String url = "jdbc:mysql://localhost:3306/Mat";
		String user = "root";
		String password = "tpjava2022";
		ajouter.setOnAction(event->{
		Connection cn = null;
		try { 
		String ID=id.getText();
		String LIB=libelle.getText();
		String FOURN=fournisseurs.getText();
		int QTE=Integer.parseInt(qte.getText());
		cn = DriverManager.getConnection(url, user, password);
		String sql = "INSERT INTO material(ID,NOM,FOURN,QUANT) VALUES (?,?,?,?)";
		PreparedStatement stmt = cn.prepareStatement(sql);
		stmt.setString(1, ID);
		stmt.setString(2, LIB);
		stmt.setString(3, FOURN);
		stmt.setInt(4, QTE);
		stmt.executeUpdate();}
		catch(Exception ev) {}
		
	});}
	
	
	@FXML
	public void confirmerA(ActionEvent e) throws IOException {
		confirmer.setOnAction(Event->{
			
		try{
		
		client c1=new client();		
	     c1.start();
	    
	    
		}	
		
		
		
		
		
		
		catch(Exception exp) 
		{exp.printStackTrace();}});}	

}
