import java.util.Scanner;

public class AccountManagement{
	
	Scanner sc = new Scanner(System.in);
	Profile profile = new Profile();
	
	void createAccount() {
		String fName, lName, mName, uName, passWord;
		
		System.out.println("\n\t-- Register an Account --");
		System.out.println("Enter the required details.\n");
		
		System.out.print("First name: ");
		fName = sc.nextLine();
		
		System.out.print("Middle Name: ");
		mName = sc.nextLine();
		
		System.out.print("Last name: ");
		lName = sc.nextLine();
		
		System.out.print("Desired Username: ");
		uName = sc.nextLine();
			
		System.out.print("Desired Password: ");
		passWord = sc.nextLine();
		
		String fullName = lName + "-" + fName + "-" + mName;
		
		profile.addProfile(fullName, uName, passWord);
		
		System.out.print("\nYou have successfully registered your account!"
				+ "\n\nName: " + fullName + " \nID Number: ");
		//System.out.print(profile.lastUserID() + 1);
		
		System.out.println("\n=> Press [ENTER] to continue!");
		sc.nextLine();
	}
	
	boolean loginAccount() {
		String logUsername, logPassword;
		int logID;
		
		System.out.println("\n\t\t-- Account Login --");
		
		System.out.print("Username: ");
		logUsername = sc.nextLine();
		
		System.out.print("Password: ");
		logPassword = sc.nextLine();
		
		logID = profile.verifyProfile(logUsername, logPassword);
		
		if (logID >= 0) {
			return false;
		} else {
			return true;
		}
	}
}
