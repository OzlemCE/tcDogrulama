
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoap;
import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

public class MailGonder {

	

            public static void main(String[] args) {
                        // TODO Auto-generated method stub
                   
            }
			
			public static void main(String tc, String email) throws SQLException {
				// TODO Auto-generated method stub
				//DB
				String kullaniciAdi= "root";
				String parola= "*****";
				final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
				final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
				String ad = null;
				String soyad = null;
				Integer dogumYil = null;
				Connection con=null; Statement st=null; ResultSet rs=null;

				
					try {
						con = DriverManager.getConnection(DB_URL, kullaniciAdi,parola);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					
				     final String username = "*****"; //gonderen�n gmail adresi
	                 final String password = "******";// gonderen sifresi

	                 // Bu k�s�mda ise sunucu ozell�kler�n� bel�rl�yoruz
	                 Properties props = new Properties();
	                 //smtp protokolunu kullan�yoruz
	                 props.put("mail.smtp.auth", "true");// sunucu ma�l gonder�rken s�fre �st�yor mu 
	                 props.put("mail.smtp.starttls.enable", "true");
	                 props.put("mail.smtp.host", "smtp.gmail.com"); // ma�l gondereceg�m�z sunucu adres�
	                 props.put("mail.smtp.port", "587"); // ma�l gondereceg�m�z port 

	                 Session session = Session.getInstance(props, // mail gonderme �c�n b� sess�on tan�ml�yoruz
	                   new javax.mail.Authenticator() {
	                             protected PasswordAuthentication getPasswordAuthentication() {
	                                         return new PasswordAuthentication(username, password);
	                             }
	                   });

	                 try {

	                             Message message = new MimeMessage(session);
	                             message.setFrom(new InternetAddress("******"));// gonderen ma�l adresi
	                             message.setRecipients(Message.RecipientType.TO,
	                                         InternetAddress.parse(email));// al�c� ma�l adresi
	                             message.setSubject("TC Do�rulama Bilgisi"); // ma�l basl�g�
	                            
	                             System.out.println(tc);
	                             try {
									st=con.createStatement();
								} catch (SQLException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
	                             ResultSet rst = null;
	                            String veri_cek="select * from tcdogruladb where tc='"+tc+"'"; 
	                         
									try {
									rst=st.executeQuery(veri_cek);
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								
								
	                            try {
									while(rst.next())
									{
										ad=rst.getString("TC");
									     ad=rst.getString("Ad");
										 soyad=rst.getString("Soyad");
										 dogumYil=rst.getInt("DogumYili");
										
									}
									
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                           
	                             
	                             message.setText(tc+" "+ ad+" "+" "+soyad+" "+dogumYil+" "+"Bilgilerine sahip kullan�c�n�n TC Kimlik Numaras� sistemimiz taraf�ndan do�rulanm��t�r."); // ma�l �cer�g�

	                             Transport.send(message); // ma�l gonderme �slem�n� gerceklest�recek nesnem�z� ekl�yoruz

	                             System.out.println("Done");
	                             JOptionPane.showMessageDialog(null,"Mail ba�ar�yla g�nderilmi�tir.");
	                             
	                            

	                 } catch (MessagingException e) {
	                             throw new RuntimeException(e);
	                 }
         		
         		
         		
         		
         		
         		
         		
				
			}

}
