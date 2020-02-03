import java.util.ArrayList;
import java.io.*;
public class Course implements Serializable {
	String courseName;
	String courseID;
	int maxStudentsInCourse;
	int currentNumberOfStudents;
	ArrayList<String> listOfStudentsInCourse;
	String courseInstructor;
	int courseSectionNumber;
	String courseLocation;
	
	public Course (String courseName, String courseID, int maxStudentsInCourse, int currentNumberOfStudents, String courseInstructor, int courseSectionNumber, String courseLocation) {
		
		this.courseName = courseName;
		this.courseID = courseID;
		this.maxStudentsInCourse = maxStudentsInCourse;
		this.currentNumberOfStudents = currentNumberOfStudents;
		this.courseInstructor = courseInstructor;
		this.courseSectionNumber = courseSectionNumber;
		this.courseLocation = courseLocation;
		listOfStudentsInCourse = new ArrayList<String>();

	}
	
	
	
	public void registerStudent(String firstLastName) { //add first name and last name
		listOfStudentsInCourse.add(firstLastName);
		currentNumberOfStudents++;
	}
	
	public void withdrawStudent(String firstLastName) {
		listOfStudentsInCourse.remove(firstLastName);
		currentNumberOfStudents--;
	}
	 
	public int getCurrentNumberOfStudents() {
		return currentNumberOfStudents;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public int getMaxStudentsInCourse() {
		return maxStudentsInCourse;
	}

	public void setMaxStudentsInCourse(int maxStudentsInCourse) {
		this.maxStudentsInCourse = maxStudentsInCourse;
	}

	public ArrayList<String> getListOfStudentsInCourse() {
		return listOfStudentsInCourse;
	}

	public String getCourseInstructor() {
		return courseInstructor;
	}

	public void setCourseInstructor(String courseInstructor) {
		this.courseInstructor = courseInstructor;
	}

	public int getCourseSectionNumber() {
		return courseSectionNumber;
	}

	public void setCourseSectionNumber(int courseSectionNumber) {
		this.courseSectionNumber = courseSectionNumber;
	}

	public String getCourseLocation() {
		return courseLocation;
	}

	public void setCourseLocation(String courseLocation) {
		this.courseLocation = courseLocation;
	}



	public void setCurrentNumberOfStudents(int currentNumberOfStudents) {
		this.currentNumberOfStudents = currentNumberOfStudents;
	}



	public void setListOfStudentsInCourse(ArrayList<String> listOfStudentsInCourse) {
		this.listOfStudentsInCourse = listOfStudentsInCourse;
	}
			
	
}
