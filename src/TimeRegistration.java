import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TimeRegistration extends Profile{
	
	Path recordDirPath = Paths.get("res/history");
	Path recordFilePath = Paths.get("res/history/attendance-records.txt");
	
	ArrayList<String> recordHolder = new ArrayList<String>();		// Automatically reads all data from the text file
	
	ArrayList<String> time = new ArrayList<String>();				// Format: 10:41 04/16/2023 | Mercado, Jelmar | Timed In | 10110135
	ArrayList<String> date = new ArrayList<String>();				
	ArrayList<String> empName = new ArrayList<String>();
	ArrayList<String> attendance = new ArrayList<String>();	
	ArrayList<String> id = new ArrayList<String>();
	
	void createResDirectory() throws Exception{
		if (Files.exists(recordDirPath)) {
			System.out.println(">> Attendance records directories confirmed.");
		} else {
			Files.createDirectories(recordDirPath);
			System.out.println(">> Attendance records directories created.");
		}
	}
	
	void createResFile() throws Exception{
		if (Files.exists(recordFilePath)) {
			System.out.println(">> Attendance records resources confirmed.");
		} else {
			Files.createFile(recordFilePath);
			System.out.println(">> Attendance records resources created.");
		}
	}
	
	void readRecordsFile() throws IOException {					// Reads the profile-records.txt to userAccounts ArrayList!
		recordHolder.clear();
		Scanner recordsReader = new Scanner(recordFilePath);
		
		while (recordsReader.hasNextLine()) {
			recordHolder.add(recordsReader.nextLine());
		}
	}
	
	void setAttendance(String time, String date, String id, String empName, String attendance){
		
		String attendanceFormat = time + "," + date + "," + id + "," + empName + "," + attendance + "\n";
		
		try {
			Files.write(recordFilePath, attendanceFormat.getBytes(), StandardOpenOption.APPEND);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		this.time.add(time);
		this.date.add(date);
		this.id.add(id);
		this.empName.add(empName);
		this.attendance.add(attendance);
		*/
	}
	
	void displaySpecificAttendance(String currentAccId, String name){
		//String[] nameHolder = recordHolder.get
		
		if (name.equals("none")) {
			System.out.println("There are no records for this user yet!");
		} else {
			System.out.println("Name\t:\t" + name + "\nTime & Date\t    |\t Status\n");
			for(int i = recordHolder.size() - 1; i >= 0;i--) {
				
				String[] lineRecHolder = recordHolder.get(i).split(",");
				
				if (currentAccId.equals(lineRecHolder[2])) {
					System.out.println(lineRecHolder[0] + " " + lineRecHolder[1] + "\t " + lineRecHolder[5]);
				} else {
					continue;
				}
			}
		}
		
		
	}
	
	String getUserName(String currentAccId1) {
		String nameHolderStr;
		for (int i = 0; i < recordHolder.size(); i++) {
			String [] nameHolder = recordHolder.get(i).split(",");
			
			if (currentAccId1.equals(nameHolder[2])) {
				nameHolderStr = nameHolder[3] + " " + nameHolder[4];
				return nameHolderStr;
			} else {
				continue;
			}
		}
		return "none";
	}
	
	void displayAllAttendance() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Time & Date\t    |\t ID \t|    Status    |\t Name\n");
		int i = recordHolder.size() - 1;
		int k = 0;
		int j = 0;
		
		while(i >= 0) {
			
			String[] tempRecordArray = recordHolder.get(i).split(",");
			System.out.println(tempRecordArray[0] + " " + tempRecordArray[1] + "    " + tempRecordArray[2] + "\t   " + tempRecordArray[5] + "\t   " + tempRecordArray[3] + tempRecordArray[4] );
								// Time							Date						ID			                Status					Fname						Lname	
			k++;
			// New lines of codes
			if (k % 5 == 0 || i == 0) {
				k = 0;
				System.out.println("\n\t[1] Previous\t[2] Next\t[3] Exit\n");
				System.out.print(">> Input: ");
				char input = sc.nextLine().charAt(0);
				
				if (input == '1') {					
					
					if (i == 0) {
						i = j + 5;
					} else{
						i += 9;
					}

					if (i >= recordHolder.size()) {
						i = recordHolder.size() - 1;
						System.out.println("This is already the first page.");
						System.out.print("Press [ENTER] to continue.");
						sc.nextLine();
					} else {
						// DO NOTHING
					}
						
					System.out.println("---------------------------------------------------");
					System.out.println("Time & Date\t    |\t ID \t|    Status    |\t Name\n");
				} else if (input == '2') {
					i--;
					if (i >= 0 && i < 4) {
						j = i;
					} else {
						// Do nothing
					}
					System.out.println("---------------------------------------------------");
					System.out.println("Time & Date\t    |\t ID \t|    Status    |\t Name\n");
					continue;
				} else if (input == '3') {
					break;
				}
			} else {
				
				i--;
				continue;
			}
		}
	}
	
	/*
	 void displayAllAttendance() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Time & Date\t    |\t ID \t|    Status    |\t Name\n");
		
		for(int i = recordHolder.size() - 1; i >= 0;i--) {
			
			String[] tempRecordArray = recordHolder.get(i).split(",");
			System.out.println(tempRecordArray[0] + " " + tempRecordArray[1] + "    " + tempRecordArray[2] + "\t   " + tempRecordArray[5] + "\t   " + tempRecordArray[3] + tempRecordArray[4] );
								// Time							Date						ID			                Status					Fname						Lname	
			
			// New lines of codes
			if (i % 5 == 0) {
				System.out.println("\n\t[1] Previous\t[2] Next\t[3] Exit\n");
				System.out.print(">> Input: ");
				char input = sc.nextLine().charAt(0);
				
				if (input == '1') {
					i = i - 4;
				} else if (input == '2') {
					continue;
				} else if (input == '3') {
					break;
				}
			} else {
				continue;
			}
		}
	}
	 */
	
}
