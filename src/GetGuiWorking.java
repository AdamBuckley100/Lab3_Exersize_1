import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GetGuiWorking extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	// IO streams
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	private JLabel lblRadius;
	private JTextField textField_4;
	private JButton btnRadiusSubmit;
	private JTextField textField_5;
	private JLabel lblNewLabel;
	private JLabel lblCalculationOutputbox;

	public GetGuiWorking() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 698, 444);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JLabel lblAnnualInterestRate = new JLabel("Annual Interest Rate");
		GridBagConstraints gbc_lblAnnualInterestRate = new GridBagConstraints();
		gbc_lblAnnualInterestRate.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnnualInterestRate.gridx = 1;
		gbc_lblAnnualInterestRate.gridy = 1;
		getContentPane().add(lblAnnualInterestRate, gbc_lblAnnualInterestRate);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				
				// the button has been pressed, take what the user put into the field and 
				// do the calculations (on the server side). After that, output the result 
				// to the user via a test box.

				try {
					
					double theAnnualInterestRate = Double.valueOf( textField.getText() );
					double theNumberOfYears = Double.valueOf( textField_1.getText() );
					double theLoanAmount = Double.valueOf( textField_2.getText() );
					
					// now pass those doubles variable to the server.
					toServer.writeDouble(theAnnualInterestRate);
					toServer.writeDouble(theNumberOfYears);
					toServer.writeDouble(theLoanAmount);
					
					// this flush might have to go after each of the three above lines?
					toServer.flush();

					// Get area from the server
					double theResultingTotal = fromServer.readDouble();

					String theTotalAsAString = String.valueOf(theResultingTotal);
					
					double theNumberOfMonthsOfTheYears = theNumberOfYears * 12;
					
					double theMonthlyInstallments = theResultingTotal/theNumberOfMonthsOfTheYears;

					// Display to the text area the area itself (displaying to textField_3)
					textField_3.setText("Total amount you have to repay is: €" + theTotalAsAString +
							". Pay this in monthly installments of: €" + theMonthlyInstallments);
				}
				catch (IOException ex) {
					System.out.println("error");
				}
				
				
			}
		});
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 0);
		gbc_btnSubmit.gridx = 6;
		gbc_btnSubmit.gridy = 1;
		getContentPane().add(btnSubmit, gbc_btnSubmit);

		JLabel lblNumberOfYears = new JLabel("Number of Years");
		GridBagConstraints gbc_lblNumberOfYears = new GridBagConstraints();
		gbc_lblNumberOfYears.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfYears.gridx = 1;
		gbc_lblNumberOfYears.gridy = 2;
		getContentPane().add(lblNumberOfYears, gbc_lblNumberOfYears);

		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.anchor = GridBagConstraints.WEST;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 2;
		getContentPane().add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);

		JLabel lblLoanAmount = new JLabel("Loan Amount");
		GridBagConstraints gbc_lblLoanAmount = new GridBagConstraints();
		gbc_lblLoanAmount.insets = new Insets(0, 0, 5, 5);
		gbc_lblLoanAmount.gridx = 1;
		gbc_lblLoanAmount.gridy = 3;
		getContentPane().add(lblLoanAmount, gbc_lblLoanAmount);

		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.anchor = GridBagConstraints.WEST;
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.gridx = 3;
		gbc_textField_2.gridy = 3;
		getContentPane().add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		lblCalculationOutputbox = new JLabel("Calculation Output (box below):");
		GridBagConstraints gbc_lblCalculationOutputbox = new GridBagConstraints();
		gbc_lblCalculationOutputbox.insets = new Insets(0, 0, 5, 5);
		gbc_lblCalculationOutputbox.gridx = 1;
		gbc_lblCalculationOutputbox.gridy = 4;
		contentPane.add(lblCalculationOutputbox, gbc_lblCalculationOutputbox);

		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.gridwidth = 4;
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 5;
		getContentPane().add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);

		lblRadius = new JLabel("Radius");
		GridBagConstraints gbc_lblRadius = new GridBagConstraints();
		gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
		gbc_lblRadius.gridx = 1;
		gbc_lblRadius.gridy = 7;
		contentPane.add(lblRadius, gbc_lblRadius);

		textField_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.anchor = GridBagConstraints.WEST;
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.gridx = 3;
		gbc_textField_4.gridy = 7;
		contentPane.add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);

		// Equivalent action performed is pressing the button
		btnRadiusSubmit = new JButton("Radius Submit");
		btnRadiusSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				// the button has been pressed, take what the user put into the field and 
				// do the calculations (on the server side). After that, output the result 
				// to the user via a test box.

				try {
					// get what the user put in for radius.
					double theRadius = Double.valueOf( textField_4.getText() );

					// now pass that double variable to the server.
					toServer.writeDouble(theRadius);
					toServer.flush();

					// Get area from the server
					double area = fromServer.readDouble();

					String theAreaAsAString = String.valueOf(area);

					// Display to the text area the area itself (displaying to textField_5)
					textField_5.setText(theAreaAsAString);

				}
				catch (IOException ex) {
					System.out.println("error");
				}

			}
		});
		GridBagConstraints gbc_btnRadiusSubmit = new GridBagConstraints();
		gbc_btnRadiusSubmit.insets = new Insets(0, 0, 5, 0);
		gbc_btnRadiusSubmit.gridx = 6;
		gbc_btnRadiusSubmit.gridy = 7;
		contentPane.add(btnRadiusSubmit, gbc_btnRadiusSubmit);
				
				lblNewLabel = new JLabel("The Area of the inputted radius is: ");
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel.gridx = 1;
				gbc_lblNewLabel.gridy = 9;
				contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
				textField_5 = new JTextField();
				GridBagConstraints gbc_textField_5 = new GridBagConstraints();
				gbc_textField_5.insets = new Insets(0, 0, 5, 5);
				gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField_5.gridx = 3;
				gbc_textField_5.gridy = 9;
				contentPane.add(textField_5, gbc_textField_5);
				textField_5.setColumns(10);

		// Below -> to make this class the client:
		try {
			// Create a socket to connect to the server
			Socket socket = new Socket("localhost", 8000);
			// Socket socket = new Socket("130.254.204.36", 8000);
			// Socket socket = new Socket("drake.Armstrong.edu", 8000);

			// Create an input stream to receive data from the server
			fromServer = new DataInputStream(socket.getInputStream());

			// Create an output stream to send data to the server
			toServer = new DataOutputStream(socket.getOutputStream());
		}
		catch (IOException ex) {
			System.out.println("error of try catch");
		}
	}

	public static void main(String[] args) {

		GetGuiWorking theGui = new GetGuiWorking();
		//theGui.runn(); // db set up (should only have to happen once).

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//MyGui frame = new MyGui();
					theGui.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		new GetGuiWorking();
	}

}