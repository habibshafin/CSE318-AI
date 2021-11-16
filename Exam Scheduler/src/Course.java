import java.util.ArrayList;

public class Course {
    String courseName;
    int numberOfStudents;
    ArrayList<Course> adjacentCourses;
    int color;

    public Course(String name, String students){
        this.courseName = name;
        this.numberOfStudents =Integer.parseInt(students);
        adjacentCourses = new ArrayList<>();
        color = 0;
    }
    public Course(Course course){
        this.courseName = course.getCourseName();
        this.numberOfStudents = course.getNumberOfStudents();
        this.color = course.getColor();
        this.adjacentCourses = course.getAdjacentCourses();

    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public ArrayList<Course> getAdjacentCourses() {
        return adjacentCourses;
    }
    public boolean hasColorInAdjacents(int color){
        for (int i=0; i<adjacentCourses.size();i++){
            if (adjacentCourses.get(i).getColor()==color)
                return true;
        }
        return false;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getDegree(){
        return adjacentCourses.size();
    }

    public void addAdjacent(Course course) {
        if (adjacentCourses.size()>0){
            int i;
            for (i=0; i<adjacentCourses.size(); i++) {
                if (adjacentCourses.get(i).getCourseName().equalsIgnoreCase(course.getCourseName()))
                    break;
            }
            if (i == adjacentCourses.size())
                adjacentCourses.add(course);
        }
        else {
            adjacentCourses.add(course);
        }
    }
}