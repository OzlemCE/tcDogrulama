import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.awt.Window.Type;
import java.awt.Font;

import javax.swing.JButton;

import java.awt.Color;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

import tr.gov.nvi.tckimlik.WS.*;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Anasayfa {

	
	//DB
	private static String kullaniciAdi= "root";
	private static String parola= "*****";
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
	
	//logging -- log4j
	private static final Logger LOGGER = LoggerFactory.getLogger(Anasayfa.class);
	
	private JFrame frmTcDorulamaListeleme;
	private JTextField txtAd;
	private JTextField txtTC;
	private JTextField txtSoyad;
	private JTextField txtDogumYil;

	
	static boolean deneme = false ;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Anasayfa window = new Anasayfa();
					window.frmTcDorulamaListeleme.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	Connection con=null; 
	Statement st=null; 
	ResultSet rs=null;
	private JTable table;
	private JTextField txtTcNo;
	private JTextField txtMail;

	public Anasayfa() {
	
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTcDorulamaListeleme = new JFrame();
		frmTcDorulamaListeleme.getContentPane().setBackground(new Color(255, 255, 255));
		frmTcDorulamaListeleme.setType(Type.UTILITY);
		frmTcDorulamaListeleme.setTitle("TC DO\u011ERULAMA / L\u0130STELEME");
		frmTcDorulamaListeleme.setBounds(100, 100, 617, 540);
		frmTcDorulamaListeleme.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTcDorulamaListeleme.getContentPane().setLayout(null);
		
		try {
			con = DriverManager.getConnection(DB_URL, kullaniciAdi,parola);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			st = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 599, 493);
		frmTcDorulamaListeleme.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("TC Doðrulama", null, panel, null);
		panel.setLayout(null);
		
		
		txtAd = new JTextField();
		txtAd.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtAd.setText("");
		txtAd.setBounds(170, 136, 332, 35);
		panel.add(txtAd);
		txtAd.setColumns(10);
		
		txtTC = new JTextField();
		txtTC.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtTC.setBounds(170, 65, 335, 35);
		panel.add(txtTC);
		txtTC.setColumns(10);
		
		txtSoyad = new JTextField();
		txtSoyad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtSoyad.setBounds(170, 204, 332, 35);
		panel.add(txtSoyad);
		txtSoyad.setColumns(10);
		
		txtDogumYil = new JTextField();
		txtDogumYil.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtDogumYil.setBounds(170, 272, 332, 35);
		panel.add(txtDogumYil);
		txtDogumYil.setColumns(10);
		
		JLabel lblAd = new JLabel("AD :");
		lblAd.setForeground(new Color(95, 158, 160));
		lblAd.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblAd.setBounds(38, 136, 30, 21);
		panel.add(lblAd);
		
		JLabel lblTC = new JLabel("TC K\u0130ML\u0130K NO :");
		lblTC.setForeground(new Color(95, 158, 160));
		lblTC.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblTC.setBounds(38, 65, 132, 21);
		panel.add(lblTC);
		
		JLabel lblSoyad = new JLabel("SOYAD :");
		lblSoyad.setForeground(new Color(95, 158, 160));
		lblSoyad.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblSoyad.setBounds(38, 210, 58, 21);
		panel.add(lblSoyad);
		
		JLabel lbldogumYil = new JLabel("DO\u011EUM YILI :");
		lbldogumYil.setForeground(new Color(95, 158, 160));
		lbldogumYil.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lbldogumYil.setBounds(38, 278, 97, 21);
		panel.add(lbldogumYil);
		
		
		JButton btnDogrula = new JButton("DO\u011ERULA");
		btnDogrula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				KPSPublicSoap tc = new KPSPublicSoapProxy();
				  try {
				   deneme = tc.TCKimlikNoDogrula(Long.parseLong(txtTC.getText()), 
						   txtAd.getText().toUpperCase(),txtSoyad.getText().toUpperCase(), Integer.parseInt(txtDogumYil.getText()));
				  } catch (NumberFormatException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
				  } catch (RemoteException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
				  }
				  try
				  {
				  if (deneme == true) {
					  JOptionPane.showMessageDialog(null,"TC Kimlik Numarasý Doðrudur.");
				    System.out.println(String.valueOf("TC Doðrudur."));
				  } else {
					  JOptionPane.showMessageDialog(null,"TC Kimlik Numarasý Yanlýþtýr.");
				   System.out.println("TC Yanlýþtýr.");
				  }
				  }
				  catch(Exception e)
				  {
					  LOGGER.error("Exceptions happen!", e); 
				  }
	
			}
		});
		btnDogrula.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnDogrula.setForeground(new Color(95, 158, 160));
		btnDogrula.setBounds(170, 370, 148, 42);
		panel.add(btnDogrula);
		
		JButton btnKaydet = new JButton("KAYDET");
		btnKaydet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					if(deneme ==true)
					{	
				    String veri_ekle= "INSERT INTO tcdogruladb(TC,Ad,Soyad,DogumYili) VALUES('"+txtTC.getText()+"','"+txtAd.getText()+"','"+txtSoyad.getText()+"','"+Integer.parseInt(txtDogumYil.getText())+"')";
					st.execute(veri_ekle);
					JOptionPane.showMessageDialog(null,"Yeni kiþi eklendi.");
					txtTC.setText("");
					txtAd.setText("");
					txtSoyad.setText("");
					txtDogumYil.setText("");
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Yeni kiþinin eklenebilmesi için TC doðrulamasý yapýlmalýdýr.");
					}
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,"Hata oluþtu, yeni kiþi eklenemedi !");
					e.printStackTrace();
				}
				
			}
		});
		btnKaydet.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnKaydet.setForeground(new Color(95, 158, 160));
		btnKaydet.setBounds(344, 370, 161, 42);
		panel.add(btnKaydet);
		
		

		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Kiþi Listeleme", null, panel_1, null);
		panel_1.setLayout(null);
		
		table = new JTable();
		table.setBounds(0, 46, 594, 417);
		JScrollPane sc=new JScrollPane(table);
		sc.setSize(594, 416);
		sc.setLocation(0, 47);
		panel_1.add(sc);
	
	         
	         JButton btnKisiListele = new JButton("Ki\u015Fileri Listele");
	         btnKisiListele.addActionListener(new ActionListener() {
	         	public void actionPerformed(ActionEvent arg0) {
	         		
	         		
	         		
	       		 // kiþiler tablosundaki tum verileri alýyoruz   
	       	      try {
	       			rs=st.executeQuery("Select * from tcdogruladb ");
	       		} catch (SQLException e) {
	       			// TODO Auto-generated catch block
	       			e.printStackTrace();
	       		}

	       	      //Veri tabanýndaki sutunlarin sayisini alýyoruz.
	       	      int sutunSayisi = 0;
	       		try {
	       			sutunSayisi = rs.getMetaData().getColumnCount();
	       		} catch (SQLException e) {
	       			// TODO Auto-generated catch block
	       			e.printStackTrace();
	       		}
	       		

	       	       //Table tipinde model olusturuyoruz.
	       	        DefaultTableModel tb=new DefaultTableModel();

	       	       //table referansýna veritabanýndaki sutunlarý ekliyoruz.
	       	        for(int i=1;i<=sutunSayisi;i++)
	       				try {
	       					tb.addColumn(rs.getMetaData().getColumnName(i));
	       				} catch (SQLException e) {
	       					// TODO Auto-generated catch block
	       					e.printStackTrace();
	       				}
	       	      
	       	       //Veritabanindaki tum satir ve sutunlari tarayip Table'a ekliyoruz.
	       	         try {
	       				while(rs.next()){
	       				      Object[] row=new Object[sutunSayisi];
	       				       for(int i=1;i<=sutunSayisi;i++)
	       				          row[i-1]=rs.getObject(i);
	       				      tb.addRow(row);
	       				 }
	       			} catch (SQLException e) {
	       				// TODO Auto-generated catch block
	       				e.printStackTrace();
	       			}

	       	        //table referansýna eklediklerimizi jtable1'e atýyoruz.
	       	         table.setModel(tb);
	       	      
	       	      sc.setViewportView(table);
	         		
	         	}
	         });
	         btnKisiListele.setForeground(new Color(95, 158, 160));
	         btnKisiListele.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 14));
	         btnKisiListele.setBounds(375, 0, 207, 34);
	         panel_1.add(btnKisiListele);
	         
	         JPanel panel_2 = new JPanel();
	         panel_2.setBackground(new Color(255, 255, 255));
	         tabbedPane.addTab("Mail Gönder", null, panel_2, null);
	         panel_2.setLayout(null);
	         
	         JLabel lblNewLabel = new JLabel("TC Kimlik No :");
	         lblNewLabel.setForeground(new Color(95, 158, 160));
	         lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
	         lblNewLabel.setBounds(27, 121, 125, 16);
	         panel_2.add(lblNewLabel);
	         
	         txtTcNo = new JTextField();
	         txtTcNo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
	         txtTcNo.setBounds(164, 115, 284, 33);
	         panel_2.add(txtTcNo);
	         txtTcNo.setColumns(10);
	         
	         JLabel lblNewLabel_1 = new JLabel("E-Mail Adresi :");
	         lblNewLabel_1.setForeground(new Color(95, 158, 160));
	         lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
	         lblNewLabel_1.setBounds(27, 196, 109, 27);
	         panel_2.add(lblNewLabel_1);
	         
	         txtMail = new JTextField();
	         txtMail.setFont(new Font("Segoe UI", Font.PLAIN, 15));
	         txtMail.setBounds(164, 195, 284, 33);
	         panel_2.add(txtMail);
	         txtMail.setColumns(10);
	         
	         JButton btnGonder = new JButton("G\u00D6NDER");
	         btnGonder.addActionListener(new ActionListener() {
	         	public void actionPerformed(ActionEvent arg0) {
	         		
	         		try {
						MailGonder.main(txtTcNo.getText(),txtMail.getText());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	         		
	         		
	         	}
	         });
	         btnGonder.setForeground(new Color(95, 158, 160));
	         btnGonder.setFont(new Font("Segoe UI", Font.BOLD, 15));
	         btnGonder.setBounds(212, 301, 224, 42);
	         panel_2.add(btnGonder);
	         
	         JLabel lblBilgi = new JLabel("<html>** Bilgilerini g\u00F6ndermek istedi\u011Finiz kullan\u0131c\u0131n\u0131n TC Kimlik Numaras\u0131 ve E-Mail adresini giriniz.</html>");
	         lblBilgi.setForeground(new Color(95, 158, 160));
	         lblBilgi.setFont(new Font("Segoe UI", Font.BOLD, 15));
	         lblBilgi.setBounds(28, 29, 554, 42);
	         panel_2.add(lblBilgi);
		
	
	}  
	}
	

