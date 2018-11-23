import java.text.*;
import java.util.*;

//Loan SuperClass
public abstract class Loan implements Comparable <Loan> {
	protected String name;
	protected double interestRate;
	protected int length;
	protected double principal;
	protected double monthlyPayment;
	protected String loanType;
	
	//Using java utilities to format output as USD
	Locale locale = new Locale("en", "US");  
	NumberFormat usd = NumberFormat.getCurrencyInstance(locale);
	
		//Constructor
	public Loan (String name, double rate, int years, double amount) {
		this.name = name;
		this.interestRate = rate;
		this.length = years;
		this.principal = amount;
	}
	
	public String process () {
		//Calculates monthly payment and returns summary 
		calcMonthPayment();
		String loanSum = makeSummary();
		return loanSum;
	}
	
	abstract public void calcMonthPayment();
		
	public String makeSummary() {
		//Create and return summary based on results of process()
		loanType = this.toString();
		String nameOfApplicant = ("Name of applicant: " + name);
		String appInterestRate = ("Interest rate:  %" + interestRate);
		String appLength = ("Length of loan: " + length*12 + " months");
		String appPrincipal = ("Principle amount: " + usd.format(principal));
		String monthly = ("Monthly payment on loan: " + usd.format(monthlyPayment));
	
		String sum = "" + loanType + "\n" + nameOfApplicant + "\n" + appInterestRate + "\n" + appLength + "\n" + appPrincipal + "\n" + monthly;
		return sum;
	}
	
	public String toString() {
		String sum = "" + makeSummary();
		return sum;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	public static Comparator<Loan> loanComparator = new Comparator<Loan>() {
		
		public int compare (Loan l1, Loan l2) {
			String loanName1 = l1.name.toUpperCase();
			String loanName2 = l2.name.toUpperCase();
			
			return loanName1.compareTo(loanName2);
			
			
		}		
};

}