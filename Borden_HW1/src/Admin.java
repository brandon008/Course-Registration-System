import java.util.ArrayList;

public class Admin extends User implements CourseManagementAdmin {

	public Admin(String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void newCourse(ArrayList<Course> courses, Course courseToAdd) {
		courses.add(courseToAdd);

	}

	@Override
	public void deleteCourse(ArrayList<Course> courses, Course courseToRemove) {
		courses.remove(courseToRemove);
	}

	@Override 
	public void displayCourse(ArrayList<Course> courses, String courseID, int courseSectionNumber) {
		Course c1 = null;
		boolean found = false;
		for (int i = 0; i < courses.size() && !found; i++) {
			if (courses.get(i).getCourseID().equals(courseID)
					&& courses.get(i).getCourseSectionNumber() == courseSectionNumber) {
				c1 = courses.get(i);
				found = true;
			}
		}
		System.out.println("Course Name:\n"+ c1.getCourseName() + "\nCourse ID:\n" + c1.getCourseID() + "\nMax number of students in course:\n" + c1.getMaxStudentsInCourse() + "\nCurrent Number of students enrolled:\n"
				+ c1.getCurrentNumberOfStudents() + "\nCourse Section Number:\n" + c1.getCourseSectionNumber() + "\nCourse Location:\n" + c1.getCourseLocation() + "\nCourse Instructor:\n" +c1.getCourseInstructor());
	}



	@Override
	public void viewAllCourses(ArrayList<Course> courses) {
		boolean found = false;
		for (int i = 0; i < courses.size(); i++) {
			System.out.println(courses.get(i).getCourseName());
		}
	}

	@Override
	public void viewAllFullCourses(ArrayList<Course> courses) {
		boolean found = false;
		for (int i = 0; i < courses.size() && !found; i++) {

			if (courses.get(i).currentNumberOfStudents == courses.get(i).maxStudentsInCourse) {
				System.out.println(courses.get(i).getCourseName());
				found = true;
			}
		}
	}

	@Override
	public void viewStudentsInCourse(ArrayList<Course> courses, String courseID, int courseSectionNumber) {
		boolean found = false;
		Course c = null;
		for (int i = 0; i < courses.size() && !found; i++) {
			if (courses.get(i).getCourseID().equals(courseID)
					&& courses.get(i).getCourseSectionNumber() == courseSectionNumber) {
				c = courses.get(i);
				found = true;
			
			}
		}
		ArrayList<String> students = c.getListOfStudentsInCourse();
		for (int i = 0; i< students.size(); i++) {
			System.out.println(students.get(i));
		}
	}

	@Override
	public void viewCoursesOfStudent(Student s) {
		// TODO Auto-generated method stub
		
		System.out.printf("Student %s is currently registered in: \n", s.getFirstName());
		ArrayList<Course> courses = s.getRegisteredCourses();
		
		for (int i = 0; i < courses.size(); i++) {
			System.out.println(courses.get(i).getCourseName());
		}

	}

}
