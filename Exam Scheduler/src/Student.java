import java.util.ArrayList;

public class Student {
    int id;
    ArrayList<String> coursesTaken = new ArrayList<>();

    public Student(int id, ArrayList<String> tempList){
        this.id = id;
        this.coursesTaken = tempList;
    }

    public ArrayList<String> getCoursesTaken() {
        return coursesTaken;
    }
}
