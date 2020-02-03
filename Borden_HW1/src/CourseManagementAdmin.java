import java.util.ArrayList;

public interface CourseManagementAdmin {
	
	public abstract void newCourse(ArrayList<Course> currentCourses, Course courseToAdd);
	public abstract void deleteCourse(ArrayList<Course> currentCourses, Course courseToRemove);
	void viewAllCourses(ArrayList<Course> courses);
	void viewAllFullCourses(ArrayList<Course> courses);
	void viewStudentsInCourse(ArrayList<Course> courses, String courseID, int courseSectionNumber);
	void viewCoursesOfStudent(Student s);
	void displayCourse(ArrayList<Course> courses, String courseID, int courseSectionNumber);
}