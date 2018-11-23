import java.math.*;

public class AmortizedLoan extends Loan {
	public AmortizedLoan () {
		super("", 1, 1, 1);
	}
	public void calcMonthPayment() {
		//Calculate the monthly payment for the AmortizedLoan
		double monthlyPayment;
		double monthlyRate = ((interestRate/100)/12);
		double n = Math.pow((1 + monthlyRate), length*12);
		this.monthlyPayment =  (principal * (monthlyRate) * n) / (n - 1);
	}
	
	public String toString() {
		return "Full Amortized Loan";
	}
	@Override
	public int compareTo(Loan l2) {
		String loanName = this.getName();
		String loan2Name = l2.getName();
		
		return loanName.compareTo(loan2Name);
		
}
}