

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RunProgram {
	
	void Welcome() {
		System.out.println("\n\t-- Welcome to Time In/Out System! --\n\n"
				+ "What do you want to do?\n"
				+ "\t[1] Register\n"
				+ "\t[2] Log In\n"
				+ "\t[3] Check Attendance\n"
				+ "\t[4] Exit\n");
		System.out.print("=> Input: ");
	}
	
	
	public static void main(String[] args) {
		
		// Objects are declared here !!
		RunProgram mainObj= new RunProgram();
		Scanner sc = new Scanner(System.in);
		Profile profile = new Profile();
		TimeRegistration time = new TimeRegistration();
		Date currentDate = new Date();
		SimpleDateFormat currTime = new SimpleDateFormat("hh:mm aa");
		SimpleDateFormat currDate = new SimpleDateFormat("MM/dd/yyyy");
		
		
		char choice;
		boolean loopBreaker = true;
		
		try { 			// ------------>> Creates folders for text file of profile records!
			
			profile.createResDirectory();
			profile.createResFile();
			
			time.createResDirectory();
			time.createResFile();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		do {
			try {								// Reads the text file to update records
				profile.readProfileFile();
				
				time.readRecordsFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			mainObj.Welcome();
			
			do {
				try {
					choice = sc.nextLine().charAt(0);
					break;
				} catch (StringIndexOutOfBoundsException e) {
					System.out.print("=> Please enter a number from 1 to 4: ");
				}
			} while (true);
			
			switch(choice) {			// Choose Register, Login, or Check Attendance
				case '1':				// Register
					
					
					String fName, lName, mName, uName, passWord;
					int newUID;
						
					System.out.println("\n\t-- Register an Account --");
					System.out.println("Enter the required details.\n");
					
					System.out.print(">> First name: ");
					fName = sc.nextLine();
					
					System.out.print(">> Middle Name: ");
					mName = sc.nextLine();
					
					System.out.print(">> Last name: ");
					lName = sc.nextLine();
					
					do {
						System.out.print(">> Desired Username: ");
						uName = sc.nextLine();
						if (profile.uidExists(uName)) {
							System.out.println("\t* Username already taken! *");
						}
					} while (profile.uidExists(uName));
					
						
					System.out.print(">> Desired Password: ");
					passWord = sc.nextLine();
					
					String fullName = lName + "-" + fName + "-" + mName;
					
					profile.addProfile(fullName, uName, passWord);
					
					System.out.print("\nYou have successfully registered your account!"
							+ "\n\nName: " + fullName + " \nID Number: ");
					
					newUID = profile.getLastUserID() + 1;
					
					System.out.print(newUID);
					
					System.out.println("\n=> Press [ENTER] to continue!");
					sc.nextLine();
					
					break;
				case '2':				// Login Phase
					String logUsername, logPassword;
					int logID;
					
					System.out.println("\n\t\t-- Account Login --");
					
					System.out.print("Username: ");
					logUsername = sc.nextLine();
					
					System.out.print("Password: ");
					logPassword = sc.nextLine();
					
					logID = profile.verifyProfile(logUsername, logPassword);
					
					
					if (logID == -1) {	// -1 means no account existing
						System.out.println("\n!! Your username or password is incorrect. !!");
						
					} else {			// Proceeds to user's account menu
						
						System.out.println("\n\t\t -- Account Menu --");
						System.out.println("Account Name ||\t" + profile.getMyName());
						System.out.println("\nUtilities: ");
						System.out.println("\t[1] Time In\n\t[2] Time Out\n\t[3] Log Out");
						System.out.print("\n=> Input: ");
						char accChoice = sc.nextLine().charAt(0);
						boolean timeInLoop = true;
						
						if (accChoice == '1' || accChoice == '2') {
							do {
								System.out.println("~ ");
								if (accChoice == '1') {
									System.out.println("Do you want to time in now? (Y/N)");
								} else {
									System.out.println("Do you want to time out now? (Y/N)");
								}
								
								System.out.print("=> Input: ");
								char timeChoice = sc.nextLine().charAt(0);
								
								if (Character.toUpperCase(timeChoice) == 'Y') {				
									
									if (accChoice == '1') {
										time.setAttendance(currTime.format(currentDate), currDate.format(currentDate), profile.getMyId(), profile.getMyName(), "Timed In");
										System.out.println(">> "+ profile.getMyName() + " has successfully timed in at " + currTime.format(currentDate) + " " + currDate.format(currentDate) + ".");
									} else {
										time.setAttendance(currTime.format(currentDate), currDate.format(currentDate), profile.getMyId(), profile.getMyName(), "Timed Out");
										System.out.println(">> "+ profile.getMyName() + " has successfully timed out at " + currTime.format(currentDate) + " " + currDate.format(currentDate) + ".");
									}
									timeInLoop = false;
								} else if (Character.toUpperCase(timeChoice) == 'N') {
									System.out.println(">> Time in cancelled!");
									timeInLoop = false;
									
								} else {
									System.out.println(">> Invalid input! Proceeding to menu...\n");
									continue;
								}
								
							} while(timeInLoop);
						} else if (accChoice == '3') {
							System.out.println(">> Your account has been logged out successfully!");
						} else {
							System.out.println(">> Invalid input! Proceeding to menu...");
						}
					}
					
					System.out.println("\n=> Press [ENTER] to continue!");
					sc.nextLine();
					
					break;
				case '3':				// Check Attendance	selection															
					
					System.out.println("------ Employee's Attendance -------");
					System.out.println("[1] All Employees\n[2] Specific Employee");
					System.out.print("\n=> Input: ");
					char checkChoice = sc.nextLine().charAt(0);
					
					switch (checkChoice) {
					case '1':
						System.out.println("-------------- All Attendances -------------");
						
						try {
							time.displayAllAttendance();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						System.out.println("\nPress [ENTER] to continue...");
						sc.nextLine();
						break;
					case '2':
						String chooseEmp;
						int counter = 0;
						
						
						while(true) {
							System.out.println("\n------------ Choose an Employee ------------\n");
							profile.displayProfileNames(counter);
							//profile.getProfileNames();
							
							
							do {
								
								System.out.println("\n[1] Previous\t[2] Next\t[3] Quit\t[ID] View User");
								System.out.print("\n=> Input: ");
								chooseEmp = sc.nextLine();
								
							} while (!chooseEmp.matches("[0-9]*"));
							
							
							if (chooseEmp.equals("1")) {
								counter -=5;
								
							}else if (chooseEmp.equals("2")) {
								
								counter += 5;
								
							}else if (chooseEmp.equals("3")) {
								break;
							} else if (Integer.parseInt(chooseEmp) >= 10021) {
								
								time.displaySpecificAttendance(chooseEmp, time.getUserName(chooseEmp));
								System.out.println("\nPress [ENTER] to continue...");
								sc.nextLine();
								
							} else {
								System.out.println("ID does not exist. Please try again!");
							}
							
							if (counter > profile.getUserAccLength()) {
								System.out.println("This is the last page.");
								System.out.println("Press [ENTER] to continue...");
								sc.nextLine();
								counter -= 5;
							} else if (counter < 0) {
								System.out.println("This is the first page.");
								counter += 5;
								System.out.println("Press [ENTER] to continue...");
								sc.nextLine();
							}
							
						}
						/*
						while (true) {
							try {
								chooseEmp = sc.nextLine();
								Integer.parseInt(chooseEmp);
								break;
							} catch (Exception e) {
								System.out.println("Only numerical inputs are allowed.");
								continue;
							}
						}
						*/
						//time.displaySpecificAttendance(chooseEmp);
						
						System.out.println("\nPress [ENTER] to continue...");
						sc.nextLine();
						break;
					default:
						
					}
					
					
					break;
				case '4':
					System.out.println("Program closed. Thank you for using!");
					sc.close();
					loopBreaker = false;
					break;
				default:
					System.out.println("Incorrect input, please try again!");
					continue;
					
			}
		} while (loopBreaker);
	}
}
