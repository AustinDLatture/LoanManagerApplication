import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.io.*;

//Make save functionality sort it
//write method in LoanManager to sort loanList with return type ArrayList


public class LoanGUI extends JFrame {
	private JPanel mainPanel;
	
	private ButtonGroup btnGroup;
	private JRadioButton simpleButton;
	private JRadioButton amortizedButton;
	private JTextField nameField;
	private JTextField principalField;
	private JTextField lengthField;
	
	private JButton submitButton;
	private JComboBox interestRateBox;
	private JLabel loanLabel;
	
	private JTextField searchField;
	private JButton searchButton;
	
	private JButton deleteButton;
	
	private JTextArea summary;
	private JButton summaryButton;
	
	private String[] interestOptions = {"3", "4", "5", "6", "7"};
	
	private JButton saveButton;
	
	GridBagConstraints gbc = new GridBagConstraints();
	private Dimension d = new Dimension(450,605);
	private Dimension d2 = new Dimension(150, 30);
	private Dimension searchD = new Dimension(220, 30);

	private Loan myLoan;
	private LoanManager myManager;
	
	Locale locale = new Locale("en", "US");  
	NumberFormat usd = NumberFormat.getCurrencyInstance(locale);
		
	public LoanGUI () { 
			myManager = new LoanManager();
			MyBackground background = new MyBackground("/users/austinlatture/Programming/Java/eclipse-workspace/Project4/LoanApplicationBackground.jpg");
			setPreferredSize(d);
			
			this.setResizable(false);
			setContentPane(background);
			ActionListener b = new ButtonListener();
			
			mainPanel = new JPanel();
				mainPanel.setLayout(new GridBagLayout());
				gbc.anchor = GridBagConstraints.NORTHWEST;
				gbc.gridy = 4;
				add(mainPanel);
				mainPanel.setOpaque(false);
			
			simpleButton = new JRadioButton("Simple");						
			amortizedButton = new JRadioButton("Amortized");	
			btnGroup = new ButtonGroup();
			gbc.gridwidth = 3;
				btnGroup.add(simpleButton);
				btnGroup.add(amortizedButton);
				gbc.gridx = 0;
				gbc.gridy = 0;
				mainPanel.add(simpleButton, gbc);
				gbc.gridx = 2;
				gbc.gridy = 0;
				mainPanel.add(amortizedButton, gbc);
				
			gbc.anchor = GridBagConstraints.CENTER; //center remaining components	
			gbc.gridwidth = 1; 
			nameField = new JTextField("Name");
				gbc.gridx = 0; 
				gbc.gridy = 1;
				nameField.setPreferredSize(d2);
				mainPanel.add(nameField, gbc);
			
			principalField = new JTextField("Principal Amount");
				gbc.gridx = 1;
				gbc.gridy = 1;
				principalField.setPreferredSize(d2);
				mainPanel.add(principalField, gbc);
			
			lengthField = new JTextField("Length in years");
				gbc.gridx = 2;
				gbc.gridy = 1;
				lengthField.setPreferredSize(d2);
				mainPanel.add(lengthField, gbc);
						
			JLabel interestLabel = new JLabel("Interest (%): ");
				gbc.gridx = 0;
				gbc.gridy = 2;
				mainPanel.add(interestLabel, gbc);
	
			interestRateBox = new JComboBox(interestOptions);
				gbc.gridx = 1;
				gbc.gridy = 2;
				mainPanel.add(interestRateBox, gbc);
					
			submitButton = new JButton("Submit");
				gbc.gridx = 1;
				gbc.gridy = 3;
				submitButton.addActionListener(b);
				mainPanel.add(submitButton, gbc);
					
			loanLabel = new JLabel("   ");
				gbc.gridwidth = 4;
				gbc.gridx = 0;
				gbc.gridy = 4;
				mainPanel.add(loanLabel, gbc);
						
			searchField = new JTextField("Enter loan to search for by name");
				gbc.gridx = 0;
				gbc.gridy = 5;
				searchField.setPreferredSize(searchD);
				mainPanel.add(searchField, gbc);
					
			searchButton = new JButton("Search");
				gbc.gridwidth = 1;
				gbc.gridx = 1;
				gbc.gridy = 6;
				searchButton.addActionListener(b);
				mainPanel.add(searchButton,gbc);
				
			deleteButton = new JButton("Delete this loan");
				gbc.gridx = 1;
				gbc.gridy = 7;
				deleteButton.addActionListener(b);
				mainPanel.add(deleteButton, gbc);
			
			summaryButton = new JButton("Summarize");
				gbc.gridx = 0;
				gbc.gridy = 8;
				summaryButton.addActionListener(b);
				mainPanel.add(summaryButton, gbc);
				
			saveButton = new JButton("Save to Text");
				gbc.gridx = 2;
				gbc.gridy = 8;
				saveButton.addActionListener(b);
				mainPanel.add(saveButton, gbc);
			
			summary = new JTextArea("                                                        ");
				summary.setEditable(false);
				gbc.gridx = 0;
				gbc.gridy = 9;
				gbc.gridwidth = 4;
				mainPanel.add(summary, gbc); 
				
				pack();
	}
		
		private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == submitButton) {  //adds loan to list and shows monthly payment on screen	
				if (simpleButton.isSelected()) {
					myLoan = new SimpleLoan();
					myLoan.name = nameField.getText();
					myLoan.principal = Double.parseDouble(principalField.getText());
					myLoan.length = Integer.parseInt(lengthField.getText());
					myLoan.interestRate = Double.parseDouble((String)interestRateBox.getSelectedItem());
					myLoan.calcMonthPayment();
				}
				else if (amortizedButton.isSelected()) {
					myLoan = new AmortizedLoan();
					myLoan.name = nameField.getText();
					myLoan.principal = Double.parseDouble(principalField.getText());
					myLoan.length = Integer.parseInt(lengthField.getText());
					myLoan.interestRate = Double.parseDouble((String)interestRateBox.getSelectedItem());
					myLoan.calcMonthPayment();
				}
				
				myLoan.calcMonthPayment();
				loanLabel.setText("Monthly Payment: " + usd.format(myLoan.monthlyPayment));				
				myManager.add(myLoan);
				myLoan = null;  //setting to null afterward to "reset" myLoan
				summary.setText("Loan added to system.");
				clearFields();
				
			}

			if (e.getSource() == summaryButton) {  //summarizes total loans in the system
				int numLoans = 0;
				int numSimple = 0;
				int numAmortized = 0;
				double totalBorrowed = 0;
				
				for (int i = 0; i < myManager.size(); i++) {
					numLoans++;
					totalBorrowed += myManager.getLoan(i).principal;
					
					if (myManager.getLoan(i).toString().equals("Simple Interest Loan")) {
						numSimple++;
					}
					else if (myManager.getLoan(i).toString().equals("Full Amortized Loan")) {
						numAmortized++;
					}
				}
				summary.setText("Number of Loans: " + numLoans + "\n" + 
								"Number of Simple Loans: " + numSimple + "\n" + 
								"Number of Amortized Loans: " + numAmortized + "\n" + 
								"Total Borrowed: " + usd.format(totalBorrowed));
				clearFields();
			}
			
			if (e.getSource() == searchButton) {  //searches for specified loan in search bar
				boolean done = false;
				int i = 0;
				while (done == false) {		
					if (myManager.getLoan(i).name.equals(searchField.getText())) {
							myLoan = myManager.findLoan(searchField.getText());
							summary.setText(myLoan.makeSummary());
							done = true;
					}
					else {
						i++;
						if (i == myManager.size() ) {
							break;
						}
					}
					}
				if (i == myManager.size()) { 
					JOptionPane.showMessageDialog(null, "No loan by that name in the system.", "No Loan", JOptionPane.ERROR_MESSAGE);
				}
				clearFields();
			}
			
			if (e.getSource() == deleteButton) {	//deletes loan specified in search bar		
				myLoan = myManager.findLoan(searchField.getText());
						//set myLoan to equal the loan they've searched for
				if (summary.getText().equals(myLoan.makeSummary())) {
					int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this loan?");
					if (input == 0) {
						myManager.delete(myLoan);
						summary.setText("Loan removed from system.");
					}
				}
				clearFields();
				
			}
			
			if (e.getSource() == saveButton) {
			try {	FileWriter writer = new FileWriter("Loan Manager Output.txt");
			
				ArrayList <Loan> sortedList = myManager.sortList();
				for (Loan writeLoan: sortedList) {
					writer.write(writeLoan.makeSummary() + "\n");
				}
				writer.flush();
				writer.close();
				summary.setText("File saved.");
			}
			catch (IOException io) {
				JOptionPane.showMessageDialog(null, "Input/Output error, try again.", "IO Exception", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	public void clearFields() {
		nameField.setText("Name");
		principalField.setText("Principal Amount");
		lengthField.setText("Length in years");
	}
		
		
}
class MyBackground extends JPanel{

		    private BufferedImage image;
		    public MyBackground(String fname){ 
		    	File img = new File(fname);
		        try {
		            image = ImageIO.read(img);

		        } catch (IOException io) {
		            System.out.println("Cannot find image");
		        }
		    }
		    //draw background
		    public void paintComponent(Graphics g){
		        super.paintComponent(g);
		        g.drawImage(image,0,0,this);
		    }
		}
}