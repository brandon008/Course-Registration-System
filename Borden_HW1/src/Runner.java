import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Runner {
	static boolean isAdmin = false; //Check if Admin
	static Scanner input = new Scanner(System.in);
	static ArrayList<Student> students = new ArrayList<Student>(); // Array of students
	static ArrayList<Course> courses = new ArrayList<Course>(); //Array of courses
	static Admin admin = new Admin("Admin", "Admin001", "Bob", "Hogarth"); 
	static Student loggedInStudent; //Keeps track of which student is currently logged in 

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
		new FileReader("src/SaveFile.ser"));
		if (br.readLine() == null) {
			System.out.println("No save file found, loading from CSV: ");

			/*
			 * File Reading
			 */
			String fileName = "src/MyUniversityCourses.csv";
			File file = new File(fileName);
			Scanner filein = new Scanner(file);
			int row = 0;

			// finds number of rows
			while (filein.hasNextLine()) {
				filein.nextLine();
				row++;
			}
			//
			String[][] multiArray = new String[row][];
			filein = new Scanner(file);
			int index = 0;
			while (filein.hasNextLine()) {
				String data[] = filein.nextLine().split(",");
				multiArray[index] = data;
				index++;
			}

			for (int i = 1; i < multiArray.length; i++) {
				String name = multiArray[i][0];
				String id = multiArray[i][1];
				int maxNumStudents = Integer.parseInt(multiArray[i][2]);
				int currentNumStudents = Integer.parseInt(multiArray[i][3]);
				String instructor = multiArray[i][5];
				int courseNum = Integer.parseInt(multiArray[i][6]);
				String location = multiArray[i][7];

				Course c = new Course(name, id, maxNumStudents, currentNumStudents, instructor, courseNum, location);
				courses.add(c);
			}
		} else {
			System.out.println("Loading from .SER file");
			loadAllData();
		}
		br.close();
		/*
		 * Login System
		 */
		System.out.println("Welcome to the Student Registration System (SRS)");
		System.out.println("Amount of Students Registered: " + students.size());


		boolean foundUser = false;
		boolean rightPassword = false;

		do {
			System.out.println("Enter Username: ");
			String username = input.next();
			User userToLogin = null;
			if (!admin.getUsername().equals(username)) // checks to see not admin
			{
				for (int i = 0; i < students.size() && !foundUser; i++) {
					if (students.get(i).getUsername().equals(username)) {
						foundUser = true;
						userToLogin = students.get(i);
						loggedInStudent = (Student) userToLogin;
						isAdmin = false;
					}
				}
			} else {
				userToLogin = admin;
				foundUser = true;
				isAdmin = true;
			}

			if (userToLogin == null) {
				System.out.println("Username not found!");
			} else {
				do {
					System.out.println("Enter Password: ");
					String password = input.next();
					if (userToLogin.getPassword().equals(password)) {
						rightPassword = true;
						System.out.println("login successful!");
					} else {
						System.out.println("Wrong password!");
					}
				} while (!rightPassword);

			}
		} while (!foundUser);

		if (isAdmin == true) {
			mainMenuAdmin();

		} else {
			mainMenuStudent();

		}
		saveAllData();
	}

	static void printCoursesFromCSV(String[][] courses) {
		for (int i = 0; i < courses.length; i++) {
			for (int j = 0; j < courses[i].length; j++) {
				System.out.print(courses[i][j] + ",");
			}
			System.out.println();
		}
	}

	static void printMainAdminMenu() {
		System.out.println("Admin Main Menu");
		System.out.println("(1) Course Managment");
		System.out.println("(2) Reports");
		System.out.println("(3) Exit");
	}

	static void printCourseManagementMenuAdmin() {
		System.out.println("Course Management Menu");
		System.out.println("(1) Create a new course");
		System.out.println("(2) Delete a course");
		System.out.println("(3) Edit a course");
		System.out.println("(4) Display info for given course");
		System.out.println("(5) Register a student");
		System.out.println("(6) Exit");

	}

	static void courseManagementMenuAdmin() {
		printCourseManagementMenuAdmin();
		int command;
		boolean validAnswer = false;

		do {
			command = input.nextInt();
			switch (command) {
			case 1: // creates a new course
				createNewCourseAdmin();
				printCourseManagementMenuAdmin();
				validAnswer = false;
				break;

			case 2: // delete course
				deleteCourseAdmin();
				printCourseManagementMenuAdmin();
				validAnswer = false;
				break;

			case 3: // edit course
				editCourseAdmin();
				printCourseManagementMenuAdmin();
				validAnswer = false;
				break;

			case 4: // display info of course
				displayCourseInfoAdmin();
				printCourseManagementMenuAdmin();
				validAnswer = false;
				break;

			case 5: // register a student
				registerStudentToDataBaseAdmin();
				printCourseManagementMenuAdmin();
				validAnswer = false;
				break;
			case 6:
				System.out.println("Exiting...");
				mainMenuAdmin();
				// save
				validAnswer = true;
				break;
			default:
				System.out.println("Not a valid command, please enter 1-3");
				validAnswer = false;
				break;
			}
		} while (!validAnswer);
	}

	static void displayCourseInfoAdmin() {
		System.out.println("Enter courseID: ");
		String ID = input.next();
		System.out.println("Enter Course Section Number: ");
		int sectionNumber = input.nextInt();

		admin.displayCourse(courses, ID, sectionNumber);

	}

	static void deleteCourseAdmin() {
		System.out.println("Enter courseID: ");
		String ID = input.next();
		System.out.println("Enter Course Section Number: ");
		int sectionNumber = input.nextInt();
		Course c = null;
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getCourseID().equals(ID) && courses.get(i).getCourseSectionNumber() == sectionNumber) {
				c = courses.get(i);
			}
		}
		admin.deleteCourse(courses, c);
		saveAllData();
	}

	static void editCourseAdmin() {
		Course currentCourse = null;

		System.out.println("Enter courseID: ");
		String ID = input.next();
		System.out.println("Enter Course Section Number: ");
		int sectionNumber = input.nextInt();
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getCourseID().equals(ID) && courses.get(i).getCourseSectionNumber() == sectionNumber) {
				currentCourse = courses.get(i);
			}
		}
		input.nextLine();
		System.out.println("Enter the new Max Number Of Students: (Enter '-' to not change)");
		String answer = input.nextLine();
		int maxNumStudents = 0;
		if (!answer.equals("-")) {
			maxNumStudents = Integer.parseInt(answer);
			currentCourse.setMaxStudentsInCourse(maxNumStudents);
		}

		System.out.println("Enter the new Current Number Of Students: (Enter '-' to not change)");
		answer = input.nextLine();
		int currentNumStudents = 0;
		if (!answer.equals("-")) {
			currentNumStudents = Integer.parseInt(answer);
			currentCourse.setCurrentNumberOfStudents(currentNumStudents);
		}

		System.out.println("Enter the new instructor: (Enter '-' to not change)");
		answer = input.nextLine();
		String instructor = "";
		if (!answer.equals("-")) {
			instructor = answer;
			currentCourse.setCourseInstructor(instructor);
		}
		System.out.println("Enter the new section number: (Enter '-' to not change)");
		answer = input.nextLine();
		int sectionNumber1 = 0;
		if (!answer.equals("-")) {
			sectionNumber1 = Integer.parseInt(answer);
			currentCourse.setCourseSectionNumber(sectionNumber1);
		}
		System.out.println("Enter the new location: (Enter '-' to not change)");
		answer = input.nextLine();
		String location = "";
		if (!answer.equals("-")) {
			location = answer;
			currentCourse.setCourseLocation(location);
		}
		saveAllData();

	}

	static void printReportMenuAdmin() {
		System.out.println("Admin Report Main Menu");
		System.out.println("(1) View all courses");
		System.out.println("(2) View all FULL courses");
		System.out.println("(3) Write full courses to file");
		System.out.println("(4) View student names registered in specific course");
		System.out.println("(5) View all currently enrolled courses of student");
		System.out.println("(6) Exit");

	}

	static void reportMenuAdmin() {
		printReportMenuAdmin();
		int command;
		boolean validAnswer = false;
		do {
			command = input.nextInt();
			switch (command) {
			case 1: // view all courses
				admin.viewAllCourses(courses);
				printReportMenuAdmin();
				validAnswer = false;

				break;

			case 2: // view all FULL courses
				admin.viewAllFullCourses(courses);
				printReportMenuAdmin();
				validAnswer = false;
				break;

			case 3: // Write Full courses to file 
				writeAllFullCoursesToFileAdmin();
				printReportMenuAdmin();

				validAnswer = false;
				break;

			case 4: // display student names registered in specific course
				displayCoursesOfStudentAdmin();
				printReportMenuAdmin();
				validAnswer = false;
				break;

			case 5: // view all currently enrolled courses of student
				viewStudentsCoursesAdmin();
				printReportMenuAdmin();
				validAnswer = false;
				break;
			case 6:
				System.out.println("Exiting...");
				mainMenuAdmin();
				// save
				validAnswer = true;
				break;
			default:
				System.out.println("Not a valid command, please enter 1-3");
				validAnswer = false;
				break;
			}
		} while (!validAnswer);
	}

	static void displayCoursesOfStudentAdmin() {
		System.out.println("Enter Course ID: ");
		String ID = input.next();
		System.out.println("Enter Section Number: ");
		int sectionNumber = input.nextInt();
		admin.viewStudentsInCourse(courses, ID, sectionNumber);

	}

	static void viewStudentsCoursesAdmin() {
		System.out.println("Enter Student First Name: ");
		String firstName = input.next();
		System.out.println("Enter Student Last Name:");
		String lastName = input.next();

		Student s = null;
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).getLastName().equals(lastName) && students.get(i).getFirstName().equals(firstName)) {
				s = students.get(i);
			}
		}
		admin.viewCoursesOfStudent(s);
	}

	static void createNewCourseAdmin() {
		input.nextLine();// clearing the buffer
		System.out.println("Enter course name: ");
		String name = input.nextLine();
		System.out.println("Enter courseID: ");
		String ID = input.next();
		System.out.println("Enter Max Number of Students: ");
		int maxNumOfStudents = input.nextInt();
		input.nextLine();
		System.out.println("Enter Name of Instructor: ");
		String profName = input.nextLine();
		System.out.println(profName);
		System.out.println("Enter Section Number: ");
		int sectionNumber = input.nextInt();
		input.nextLine();
		System.out.println("Enter Location of Course: ");
		String location = input.nextLine();
		Course c = new Course(name, ID, maxNumOfStudents, 0, profName, sectionNumber, location);
		admin.newCourse(courses, c);
		saveAllData();
	}

	static void mainMenuAdmin() {
		printMainAdminMenu();
		int command;
		boolean validAnswer = false;

		do {
			command = input.nextInt();
			switch (command) {
			case 1:
				// brings us to coursemanagementmenu
				courseManagementMenuAdmin();
				validAnswer = true;
				break;

			case 2:
				// brings us to reportsmenu
				reportMenuAdmin();
				validAnswer = true;
				break;

			case 3:
				System.out.println("Exiting...");
				validAnswer = true;
				break;

			default:
				System.out.println("Not a valid command, please enter 1-3");
				validAnswer = false;
				break;
			}
		} while (!validAnswer);
	}

	static void printStudentMenu() {
		System.out.println("Student Main Menu");
		System.out.println("(1) View all courses");
		System.out.println("(2) View all open courses");
		System.out.println("(3) Register for a course");
		System.out.println("(4) Withdraw from a course");
		System.out.println("(5) View all currently enrolled courses");
		System.out.println("(6) Exit");
	}

	static void mainMenuStudent() {
		printStudentMenu();
		int command;
		boolean validAnswer = false;

		do {
			command = input.nextInt();
			switch (command) {
			case 1:
				// view all courses
				loggedInStudent.viewAllCourses(courses);
				printStudentMenu();
				validAnswer = false;
				break;

			case 2:
				// view all open courses
				loggedInStudent.viewAllNotFullCourses(courses);
				printStudentMenu();
				validAnswer = false;
				break;

			case 3:
				// register to course
				registerStudentToCourseStudent();
				printStudentMenu();
				validAnswer = false;
				break;

			case 4:
				// withdraw from course
				withdrawStudentFromCourseStudent();
				printStudentMenu();
				validAnswer = false;
				break;
			case 5:
				// view all courses student is registered in
				loggedInStudent.viewCoursesOfStudent();
				printStudentMenu();
				validAnswer = false;
				break;

			case 6:
				System.out.println("Exiting...");
				validAnswer = true;
				break;

			default:
				System.out.println("Not a valid command, please enter 1-3");
				printStudentMenu();
				validAnswer = false;
				break;
			}
		} while (!validAnswer);
	}

	static void withdrawStudentFromCourseStudent() {
		Course currentCourse = null;

		System.out.println("Enter courseID: ");
		String ID = input.next();
		System.out.println("Enter Course Section Number: ");
		int sectionNumber = input.nextInt();
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getCourseID().equals(ID) && courses.get(i).getCourseSectionNumber() == sectionNumber) {
				currentCourse = courses.get(i);
			}
		}
		loggedInStudent.withdraw(currentCourse);
		saveAllData();
	}

	static void registerStudentToCourseStudent() {
		Course currentCourse = null;

		System.out.println("Enter courseID: ");
		String ID = input.next();
		System.out.println("Enter Course Section Number: ");
		int sectionNumber = input.nextInt();
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getCourseID().equals(ID) && courses.get(i).getCourseSectionNumber() == sectionNumber) {
				currentCourse = courses.get(i);
			}
		}
		loggedInStudent.register(currentCourse);
		saveAllData();
	}

	static void registerStudentToDataBaseAdmin() {
		System.out.println("Register Student Menu");
		System.out.println("Enter Student First Name: ");
		String firstName = input.next();
		System.out.println("Enter Student Last Name: ");
		String lastName = input.next();
		System.out.println("Enter Username for Student: ");
		String userName = input.next();
		System.out.println("Enter Password for Student: ");
		String password = input.next();
		Student s = new Student(userName, password, firstName, lastName);
		students.add(s);
		saveAllData();
	}

	static void saveAllData() {
		try {
			FileOutputStream fos = new FileOutputStream("src/SaveFile.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(students);
			oos.writeObject(courses);
			oos.close();
			fos.close();
			System.out.println("Saving complete!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void loadAllData() {
		try {
			FileInputStream fis = new FileInputStream("src/SaveFile.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);

			students = (ArrayList) ois.readObject();
			courses = (ArrayList) ois.readObject();
			ois.close();
			fis.close();
			System.out.println("Loading complete!");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
	}

	static void writeAllFullCoursesToFileAdmin() {
		try {
			FileWriter fStream = new FileWriter("src/ListOfFullCourses.txt");
			BufferedWriter out = new BufferedWriter(fStream);
			boolean found = false;
			for (int i = 0; i < courses.size() && !found; i++) {

				if (courses.get(i).currentNumberOfStudents == courses.get(i).maxStudentsInCourse) {
					out.write(courses.get(i).getCourseName());
					out.newLine();
					found = true;
				}
			}
			out.flush();
			out.close();
			
			System.out.println("FULL Courses have been written to file");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
