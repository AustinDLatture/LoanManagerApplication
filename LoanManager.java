import java.util.*;

public class LoanManager {
	private ArrayList <Loan> loanList;
	private Loan tempLoan;
	private ArrayList <Loan> sortedList = new ArrayList <Loan>();

	public LoanManager () {
		loanList = new ArrayList<Loan>();
	}
	
	public ArrayList <Loan> makeVisible() {
		return loanList;
	}
	
	public ArrayList <Loan> sortList() {	
		Collections.sort(loanList, Loan.loanComparator);
		for (Loan compareLoan: loanList) {
			sortedList.add(compareLoan);
		}
	return sortedList;
	}		

	public void add(Loan e) {
		loanList.add(e);
	}
	
	public int size() {
		return loanList.size();
	}
	
	public Loan getLoan(int j) {
		return loanList.get(j);
	}
	
	public void delete(Loan e) {
		loanList.remove(e);
	}

	public Loan findLoan(String id) {
		for (int i = 0; i < loanList.size(); i++) {
			if (loanList.get(i).name.equals(id)) {
				tempLoan = loanList.get(i);
			}
		}
	return tempLoan;
	}
		
	public String toString () {
		return "" + loanList;
	}
	
	
}
