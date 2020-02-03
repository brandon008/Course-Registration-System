import java.util.ArrayList;
import java.io.*;

public class Student extends User implements CourseManagementStudent, Serializable {
	ArrayList<Course> registeredCourses = new ArrayList<Course>();

	public Student(String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName);

	}

	@Override
	public void viewAllCourses(ArrayList<Course> courses) {
		boolean found = false;
		for (int i = 0; i < courses.size(); i++) {
			System.out.println(courses.get(i).getCourseName());
		}
	}

	@Override
	public void viewAllNotFullCourses(ArrayList<Course> courses) {
		for (int i = 0; i < courses.size(); i++) {

			if (courses.get(i).currentNumberOfStudents < courses.get(i).maxStudentsInCourse) {
				System.out.println(courses.get(i).getCourseName());
			}
		}

	}

	@Override
	public void register(Course c1) {
		registeredCourses.add(c1);
		c1.registerStudent(firstName + " " + lastName);

	}

	@Override
	public void withdraw(Course c1) {
		registeredCourses.remove(c1);
		c1.withdrawStudent(firstName + " " + lastName);

	}

	@Override
	public void viewCoursesOfStudent() {
		for (int i = 0; i<registeredCourses.size(); i++) {
			System.out.println(registeredCourses.get(i).getCourseName());
		}
	}
	public ArrayList<Course> getRegisteredCourses (){
		return registeredCourses;
	}
}
