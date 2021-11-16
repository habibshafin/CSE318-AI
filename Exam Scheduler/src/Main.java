import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc1 = new Scanner(new File("src\\yor-f-83.crs"));
        Scanner sc2 = new Scanner(new File("src\\yor-f-83.stu"));
        //Scanner sc1 = new Scanner(new File("src\\sta-f-83.crs"));
        //Scanner sc2 = new Scanner(new File("src\\sta-f-83.stu"));

        ArrayList<Course> courseList = new ArrayList<>();
        ArrayList<Student> studentList = new ArrayList<>();
        while(sc1.hasNextLine()){
            Scanner t1 = new Scanner(sc1.nextLine());
            //course_count = course_count + 1;
            while(t1.hasNext()) {
                String s1 = t1.next();
                String s2 = t1.next();
                courseList.add(new Course(s1,s2));
            }
        }
        /*for (int i=0; i<courseList.size(); i++){
            System.out.println(courseList.get(i).getCourseName()+"   " + courseList.get(i).getNumberOfStudents());
        }*/
        int studentCount = 0;
        while(sc2.hasNextLine()) {
            studentCount++;
            Scanner t2 = new Scanner(sc2.nextLine());
            ArrayList<String> temp = new ArrayList<>();
            while(t2.hasNext()){
                String s = t2.next();
                temp.add(s);
            }
            studentList.add(new Student(studentCount, temp));
        }
        /*for (int i=0; i<studentList.size(); i++){
            ArrayList<String> crs = studentList.get(i).getCoursesTaken();
            for (int j=0; j<crs.size(); j++){
                System.out.print(crs.get(j)+" ");
            }
            System.out.println();
        }*/
        Graph graph = new Graph(courseList,studentList);
    }
}
