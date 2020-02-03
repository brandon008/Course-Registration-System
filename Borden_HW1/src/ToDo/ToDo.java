package ToDo;

public class ToDo {
	display welcome message
	ask for login
	    first check if admin
	    loop thru all students and find that user
	    if username not found, say user doesnt exist
	    if username IS found but password wrong, then display error message saying username or password is wrong

	User currentUser == either admin or the current student

	display main menu based on if admin or student
	    
	    EX: STUDENT MAIN MENU
	        1. View All Courses
	        2. View all course that are not full
	        etc.


	command handler method(boolean isAdmin, int commandEntered)
	{
	    if(isAdmin) //admin commands
	    {
	        //do stuff based on commandEntered
	    }
	    else //student commands
	    {
	        //do stuff based on commandEntered
	    }
	}


	int commandEntered; 
	do
	{
	    System.out.println("Please enter a command option:");
	    commandEntered = Integer.parseInt(input.nextLine());
	    
	    if(commandEntered < 1 || commandEntered > 4)
	    {
	        System.out.println("Not a valid command");
	    }
	}while(commandEntered < 1 || commandEntered > 4);

	finish all (or at least as much as possible) admin related commands and methods
	figure out serialization and saving and loading
	add students to database
	test saving and loading (students added by admin should save and load properly)
	finish all remaining student related commands and methods
	test all student and admin commands



	String something = input.nextLine();
	String somethingElse = input.nextLine();
	input.nextLine();
	String test = input.next(); //something other than .nextLine();
	int test1 = input.nextInt();
	String test2 = input.nextLine();
	input.nextLine();
	String test3 = input.next();
	

}
