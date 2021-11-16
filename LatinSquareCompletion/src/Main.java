import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Scanner inputFile = new Scanner(new File("data/sample.txt"));
        //Scanner inputFile = new Scanner(new File("data/d-10-01.txt.txt"));
        String firstLine = inputFile.nextLine();
        String reqLine = firstLine.substring(0,firstLine.length()-1);
        int size = Integer.parseInt(reqLine.split("=")[1]);

        for (int i=0; i<2; i++)
            inputFile.nextLine();
        LatinSquare latinSquare = new LatinSquare(size);
        String strArr[];
        ArrayList<Cell> temp;
        int i=0;
        int j=0;
        while (inputFile.hasNext()){
            reqLine = inputFile.nextLine();
            reqLine = reqLine.substring(0,reqLine.length()-2);
            if(!inputFile.hasNext()){
                reqLine = reqLine.substring(0,reqLine.length()-2);


            }
            strArr = reqLine.split(",");
            temp = new ArrayList<Cell>();
            for(int iter=0; iter<size; iter++){
                //System.out.print(strArr[iter]+" ");
                Cell cell = new Cell(Integer.parseInt(strArr[iter].trim()),i,j);
                temp.add(cell);
                if(Integer.parseInt(strArr[iter].trim())==0)
                    latinSquare.unassignedCells.add(cell);
                j++;
            }
            latinSquare.cellList.add(temp);
            i++;
            j=0;
        }
        latinSquare.printCellList();
        latinSquare.initialiseDomain();
        //System.out.println(latinSquare.solve(1));
        System.out.println(latinSquare.solveMAC());
    }
}
