import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;

class Profile {
	Path res = Paths.get("res/profiles/");
	Path profileTxtPath = Paths.get("res/profiles/profile-records.txt");
	private ArrayList<String> userAccounts = new ArrayList<String>();
	private String[] lastUser;	// Determines the last ID for regID to adjust on
	private String[] currUser;	
	
	int regID = 100021;
	int currRegID;
	String [][] userAccs = new String[1000][50];
	
	/* For easier understanding,
	 userAccs array [index] : 	0 = ID
	 				  			1 = Full Name
	 				  			2 = User Name
	 				  			3 = Password
	 */
	
	void createResDirectory() throws Exception{	// ---------->> Creates a directory under condition
		if (Files.exists(res)) {
			System.out.println(">> Profile directories confirmed.");
		} else {
			Files.createDirectories(res);
			System.out.println(">> Profile directories created.");
		}
	}
	
	void createResFile() throws Exception{		// ----------->> Creates a file under condition
		if (Files.exists(profileTxtPath)) {
			System.out.println(">> Profile resources confirmed.");
		} else {
			Files.createFile(profileTxtPath);
			System.out.println(">> Profile resources created.");
		}
	}
	
	void addProfile(String fullName, String uName, String passWord) {	// Registers a user account based on their given information
		int newUserID = getLastUserID() + 1;
		
		String userProfile = newUserID + "," + fullName + "," + uName +"," + passWord + "\n";
		try {
			
			Files.write(profileTxtPath, userProfile.getBytes(), StandardOpenOption.APPEND);
			
		} catch (IOException ioe) {
			
			ioe.printStackTrace();
		}
	}
	
	void readProfileFile() throws IOException {					// Reads the profile-records.txt to userAccounts ArrayList!
		userAccounts.clear();
		Scanner profileReader = new Scanner(profileTxtPath);
		
		while (profileReader.hasNextLine()) {
			userAccounts.add(profileReader.nextLine());
		}
	}
	
	int getLastUserID() {										// Reads the last registered user then update the registration ID
		
		try {
			String [] lastUser = userAccounts.get(userAccounts.size() - 1).split(",");
			return Integer.parseInt(lastUser[0]);
			
		} catch (Exception e) {
			return 10020;
			
		}
	}
	
	void displayProfileNames(int lister) {	//////////////////////////////// im here now
		
		System.out.println("ID    | Full Name");
		
		for (int i = lister; i < lister + 5; i++) {
			
			if (i >= userAccounts.size() || i < 0) {
				break;
			} else {
				String[] userAccHolder = userAccounts.get(i).split(",");
				System.out.println(userAccHolder[0] + "\t" + userAccHolder[1]);
			}
		}
	}
	
	
	
	int getUserAccLength() {
		return userAccounts.size();
	}
	
	
	
	int getID(String uName) {
		for (int i = 0; i < userAccs.length; i++) {
			if (uName.equals(userAccs[i][2])) {
				return Integer.parseInt(userAccs[i][0]);
			} else {
				continue;
			}
		}
		return -1;
	}
	
	int verifyProfile(String uName, String passWord) {			// This function verifies the user's log in details
		for (int i = 0; i < userAccounts.size(); i++) {
			
			if (userAccounts.get(i).equals("")) {
				break;
			} else {
				lastUser = userAccounts.get(i).split(",");
				if (uName.equals(lastUser[2]) && passWord.equals(lastUser[3])) {
					currUser = userAccounts.get(i).split(",");
					return Integer.parseInt(lastUser[0]);
				} else {
					continue;
				}
			}
		}
		return -1;		// -1 means login details did not match
	}
	
	boolean uidExists(String userName) {
		for (int i = 0; i < userAccounts.size(); i++) {
			String[] tempAccsHolder = userAccounts.get(i).split(",");
			if (tempAccsHolder[2].equals(userName)) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}
	
	// ####################################### -----> Methods that are related to current logged in user
	
	String getMyId() {
		return currUser[0];
	}
	
	String getMyName() {
		String[] myNameArray = currUser[1].split("-");
		String myName = myNameArray[0] + ", " + myNameArray[1] + " " + myNameArray[2];
		return myName;
	}
	
	void getProfileNames() {	
		int tracker = 0;
		System.out.println("ID|\tFull Name");
		for (int i = 0; i < userAccounts.size(); i++) {
			
			String[] nameHolder = userAccounts.get(i).split(",");
			System.out.println(nameHolder[0] + "\t" + nameHolder[1]);
			
		}
	} 
}
