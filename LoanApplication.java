import java.util.*;

public class LoanApplication {
	private Loan loan;
	Scanner scnr = new Scanner(System.in);
	
	public void run() {
		//Using two loops
		//Nested loop does all the assignment and printing
		//Outer loop allows program to respond in real time to errors

		String type = "";
		boolean done = false;
		boolean done2 = false;
		
		while (done2 == false) { //Outer loop to process errors without program terminating
			try {

				while (done == false) { //Taking user input and throwing errors
					if (type != null && loan == null) {
						System.out.println("Please type 'Simple' or 'Amortized' for your loan type. Or type 'Q' to quit.");
						type = scnr.next();
					}
					if (type.equals("Simple") || type.equals("simple") && loan == null) {
						loan = new SimpleLoan();
					}
					if (type.equals("Amortized") || type.equals("amortized") && loan == null) {
						loan = new AmortizedLoan();
					}
					if (type.equals("q") || type.equals("Q")) {
						done = true;
						break;
					}
					else if (loan == null) {
						continue;
					}

			System.out.println("Enter applicant name: ");
			loan.name = scnr.next();
			System.out.println("Enter interest rate as a percentage (not a decimal): ");
			loan.interestRate = scnr.nextDouble();
			System.out.println("Enter length of loan in years: ");
			loan.length = scnr.nextInt();
			System.out.println("Enter principal amount: ");
			loan.principal = scnr.nextDouble();
			String summary = loan.process();
			loan = null;
		}
		
		System.out.print("Loan application processing complete.");
		done2 = true;
		
		}
		catch (NullPointerException e) {
			System.out.println("Only specify simple or amortized as loan type. Try again.");
			continue;
		}
		catch (InputMismatchException e) {
			System.out.println("Input specified is in the wrong format. Try again.");
			scnr.next();
		}
		}

	}
}
