import java.util.*;

public class Graph {
    ArrayList<Course> courseList = new ArrayList<>();
    ArrayList<Student> studentList = new ArrayList<>();
    ArrayList<Course> backupCourseList = new ArrayList<>();
    ArrayList<Course> visitedCourses = new ArrayList<>();
    ArrayList<Course> tochange;

    public Graph(ArrayList<Course> courseList, ArrayList<Student> studentList){
        this.courseList = courseList;
        this.studentList = studentList;
        timeTabling();
    }

    public Course getCourse(String courseName){
        for (int i=0; i<courseList.size(); i++){
            if (courseList.get(i).getCourseName().equalsIgnoreCase(courseName)){
                return courseList.get(i);
            }
        }
        return null;
    }

    public void timeTabling(){
        for (int i=0; i<studentList.size(); i++){
            for (int j=0; j<studentList.get(i).getCoursesTaken().size(); j++){
                Course course1 = getCourse(studentList.get(i).getCoursesTaken().get(j));
                for (int k=0; k<studentList.get(i).getCoursesTaken().size();k++){
                    if(j!=k){
                        Course course2 = getCourse(studentList.get(i).getCoursesTaken().get(k));
                        course1.addAdjacent(course2);
                        course2.addAdjacent(course1);
                    }
                }
            }
        }
        Collections.sort(courseList, new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                if(o1.getDegree()==o2.getDegree())
                    return 0;
                else if (o1.getDegree() > o2.getDegree())
                    return -1;
                else
                    return 1;
            }
        });
        /*System.out.println(courseList.size());
        for(int i=0; i<courseList.size(); i++){
            System.out.println(courseList.get(i).getCourseName()+"     "+courseList.get(i).getDegree());
        }*/

        int totalSlots = coloringGraph();
        System.out.println();
        System.out.println("Total slots : "+totalSlots);
        /*Collections.sort(courseList, new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return o1.getColor()-o2.getColor();
            }
        });*/
        /*for(int i=0; i<courseList.size(); i++){
            System.out.println(courseList.get(i).getCourseName()+"     "+courseList.get(i).getColor());
        }*/
        for (int i=0; i<courseList.size(); i++){
            backupCourseList.add(new Course(courseList.get(i)));
        }

        //System.out.println(calculatePenalty());
        for (int count = 0; count<1000; count++){
            kempeFunction();
        }
        //System.out.println();
        //System.out.println();
        System.out.println("Penalty : "+calculatePenalty());
        Collections.sort(backupCourseList, new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return o1.getColor()-o2.getColor();
            }
        });

        int index = 0;
        int color = 0;
        while(index<backupCourseList.size()){
            if (backupCourseList.get(index).getColor()!=color){
                System.out.println();
                color = backupCourseList.get(index).getColor();
                System.out.print(color+" : "+backupCourseList.get(index).getCourseName()+"  ");
                index++;
            }
            else {
                System.out.print(backupCourseList.get(index).getCourseName() + "  ");
                index++;
            }
        }
    }

    private void kempeFunction() {
        Double previousPenalty = calculatePenalty();
        int randomNum = (int)(Math.random() * (((courseList.size())-1 - 0) + 1));
        //System.out.println(randomNum);
        int firstSelectedColor = courseList.get(randomNum).getColor();
        int randomNum2 = (int)(Math.random() * ((((courseList.get(randomNum).getAdjacentCourses().size())-1) - 0) + 1));
        //System.out.println(randomNum2);
        //System.out.println("size : "+courseList.get(randomNum).getAdjacentCourses().size());
        int secondSelectedColor = courseList.get(randomNum).getAdjacentCourses().get(randomNum2).getColor();
        //System.out.println(firstSelectedColor+"   "+secondSelectedColor);

        //visitedCourses.clear();
        Course selectedCourse = courseList.get(randomNum);
        //localSearch(selectedCourse, secondSelectedColor);
        //System.out.println(selectedCourse.getColor()+"  prev: "+firstSelectedColor+"  "+secondSelectedColor);

        int index = 0;
        while (index<courseList.size()){
            if(courseList.get(index).getColor()==firstSelectedColor)
                courseList.get(index).setColor(secondSelectedColor);
            else if (courseList.get(index).getColor()==secondSelectedColor)
                courseList.get(index).setColor(firstSelectedColor);
            index++;
        }


        Double afterKempePenalty = calculatePenalty();
        if(afterKempePenalty<previousPenalty){
            //System.out.println("penalty kom asce. prev: "+previousPenalty+"  present"+afterKempePenalty);
            backupCourseList.clear();
            for (int i=0; i<courseList.size(); i++){
                backupCourseList.add(new Course(courseList.get(i)));
            }
            //System.out.println(backupCourseList.size());

        }else{
            //System.out.println("penalty beshi asce. prev: "+previousPenalty+"  present"+afterKempePenalty);
            courseList.clear();
            for (int i=0; i<backupCourseList.size(); i++){
                courseList.add(new Course(backupCourseList.get(i)));
            }
            //System.out.println(courseList.size());
        }
    }

    /*private void localSearch2(Course selectedCourse, int secondSelectedColor) {
        for(int idx=0; idx<selectedCourse.getAdjacentCourses().size(); idx++){
            if (selectedCourse.getAdjacentCourses().get(idx).getColor()==secondSelectedColor){
                int num;
                for( num=0; num<tochange.size(); num++){
                    if (tochange.get(num).getCourseName().equalsIgnoreCase(selectedCourse.getAdjacentCourses().get(idx).getCourseName())){
                        break;
                    }
                }
                if (num == tochange.size()){
                    tochange.add(selectedCourse.getAdjacentCourses().get(idx));
                    localSearch2(selectedCourse.getAdjacentCourses().get(idx),selectedCourse.getColor());
                }
            }
        }
    }*/

    private void localSearch(Course course, int secondSelectedColor) {
        int color  = course.getColor();
        course.setColor(secondSelectedColor);
        for(int i=0; i<course.getAdjacentCourses().size(); i++){
            if(course.getAdjacentCourses().get(i).getColor()==secondSelectedColor ){
                localSearch(course.getAdjacentCourses().get(i), color );
            }
        }
    }


    private Double calculatePenalty() {
        int totalPenalty = 0;
        for (int i=0; i<studentList.size(); i++){
            ArrayList<Course> studentCourses = new ArrayList<>();
            for (int j=0; j<studentList.get(i).getCoursesTaken().size(); j++){
                studentCourses.add(getCourse(studentList.get(i).getCoursesTaken().get(j)));
            }
            Collections.sort(studentCourses, new Comparator<Course>() {
                @Override
                public int compare(Course o1, Course o2) {
                    return o1.getColor()-o2.getColor();
                }
            });

            for (int j=1; j<studentCourses.size(); j++){
                int gap = studentCourses.get(j).getColor() - studentCourses.get(j-1).getColor();
                int penalty = 0;
                if (gap==1) penalty = 16;
                else if (gap==2) penalty = 8;
                else if (gap==3) penalty = 4;
                else if (gap==4) penalty = 2;
                else if (gap==5) penalty = 1;
                totalPenalty = totalPenalty + penalty;
            }
        }
        return Math.round(Double.valueOf(totalPenalty)/Double.valueOf(studentList.size())*100.0)/100.0;
    }

    private int coloringGraph() {
        int maxColor = 0;
        for (int i=0; i<courseList.size();i++){
            int color = 1;
            while (true){
                if (!courseList.get(i).hasColorInAdjacents(color)){
                    courseList.get(i).setColor(color);
                    break;
                }else {
                    color++;
                }
            }
            if(color>maxColor)
                maxColor = color;
        }
        return maxColor;
        /*for (int i=0; i<courseList.size(); i++){
            System.out.println(courseList.get(i).getCourseName()+"   "+courseList.get(i).getColor());
        }*/
    }

}
