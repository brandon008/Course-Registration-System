import java.util.ArrayList;

public interface CourseManagementStudent {
	public abstract void viewCoursesOfStudent();
	void viewAllCourses(ArrayList<Course> courses);
	void viewAllNotFullCourses(ArrayList<Course> courses);
	void register(Course c1);
	void withdraw(Course c1);

}
