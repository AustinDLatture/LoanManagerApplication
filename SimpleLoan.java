
public class SimpleLoan extends Loan {
	public SimpleLoan () {
		super("", 1, 1, 1);
	}
	
	public void calcMonthPayment() {
		//Calculate the monthly payment for the SimpleLoan
		double monthlyPayment;
		double monthlyRate = ((interestRate/100)/12);
		double i = principal * (monthlyRate * (length*12) + 1);
		this.monthlyPayment = (i / (length*12));
	}

	public String toString() {
		return "Simple Interest Loan";
	}
	
	@Override
	public int compareTo(Loan l2) {
		String loanName = this.getName();
		String loan2Name = l2.getName();
		
		return loanName.compareTo(loan2Name);
	}
}

