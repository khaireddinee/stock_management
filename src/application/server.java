package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



	public class server extends Thread{	
	  
		
		private PreparedStatement st;
		@Override
				public void run(){
			    
					 ServerSocket service = null;
					 Socket socketSRV = null;	  
				try{
					BufferedReader in;
					PrintWriter out;
					String url = "jdbc:mysql://localhost:3306/Mat";
					String user = "root";
					String password = "tpjava2022";
					Connection cn = null;
				service = new ServerSocket(3750);
				System.out.println("Server démarré : en attente de demande de connexion !");
				while(true) {
				socketSRV = service.accept();
				System.out.println("Connexion établie avec un client ! ");
				in = new BufferedReader(new InputStreamReader(socketSRV.getInputStream()));
				System.out.println("Server :Reading variable \n");
			    String ID=in.readLine();
			    System.out.printf("Variable received from client :%s ",ID);
			    cn = DriverManager.getConnection(url, user, password);
			    System.out.println("Connected to material \n");
				String sql = "SELECT * from material where ID=?";
			    st= cn.prepareStatement(sql);
				st.setInt(1,Integer.parseInt(ID));
				ResultSet rs=st.executeQuery();
				out = new PrintWriter(socketSRV.getOutputStream());
				
				if(rs.next()) {
					String info=rs.getString("NOM")+"/"+rs.getString("FOURN")+"/"+rs.getString("QUANT");
					 out.println(info);
					 out.flush();
					
				}
				else {out.println("NOVALUE/NOVALUE/NOVALUE");
				out.flush();}
				 out.close();
				in.close();}}
				catch(Exception e1) {e1.printStackTrace();}
				//finally {
					//try {socketSRV.close();}catch(Exception e2) {e2.printStackTrace();}
				//}
				}};


