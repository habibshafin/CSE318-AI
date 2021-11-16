import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LatinSquare {
    public ArrayList<ArrayList<Cell>> cellList;
    int size;
    public ArrayList<Cell> unassignedCells;
    public static int NodeCount = 0;
    public static int BacktrackCount = 0;

    LatinSquare(int size) {
        cellList = new ArrayList<ArrayList<Cell>>();
        this.size = size;
        unassignedCells = new ArrayList<>();
    }

    public LatinSquare() {
        cellList = new ArrayList<>();
        size = 0;
        unassignedCells = new ArrayList<>();
    }

    public void printCellList() {
        System.out.println("Size : " + size);
        for (int i = 0; i < cellList.size(); i++) {
            for (int j = 0; j < cellList.get(i).size(); j++) {
                System.out.print(cellList.get(i).get(j).val + "  ");
            }
            System.out.println();
        }
    }

    public void printDomainDegree(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(cellList.get(i).get(j).domain+" "+cellList.get(i).get(j).fwdDegree +"  ");
            }
            System.out.println();
        }
    }
    public int initialiseDomain() {
        int unassigned = 0;
        for(int iter=0; iter<unassignedCells.size(); iter++){
            int i = unassignedCells.get(iter).row;
            int j = unassignedCells.get(iter).col;
            cellList.get(i).get(j).domain = new ArrayList<>();
            unassigned++;
            for (int m = 1; m <= size; m++)
                cellList.get(i).get(j).domain.add(m);
            int fwdDeg = 0;
            int value;
            //checking rows for domain generation
            for (int m = 0; m < size; m++) {
                value = cellList.get(i).get(m).val;
                if (cellList.get(i).get(j).domain.contains(value)) {
                    cellList.get(i).get(j).domain.remove(new Integer(value));
                }
                if (value == 0 && m != j)
                    fwdDeg++;
            }
            //checking columns
            for (int m = 0; m < size; m++) {
                value = cellList.get(m).get(j).val;
                if (cellList.get(i).get(j).domain.contains(value)) {
                    cellList.get(i).get(j).domain.remove(new Integer(value));
                }
                if (value == 0 && m != i)
                    fwdDeg++;
            }
            cellList.get(i).get(j).fwdDegree = fwdDeg;
       }
    return unassigned;
    }
    public void initialiseDomainCell(Cell cell){
        for(int iter=0; iter<unassignedCells.size(); iter++){
            int i = unassignedCells.get(iter).row;
            int j = unassignedCells.get(iter).col;
            if(i==cell.row || j==cell.col){
                cellList.get(i).get(j).domain = new ArrayList<>();
                for (int m = 1; m <= size; m++)
                    cellList.get(i).get(j).domain.add(m);
                int fwdDeg = 0;
                int value;
                //checking rows for domain generation
                for (int m = 0; m < size; m++) {
                    value = cellList.get(i).get(m).val;
                    if (cellList.get(i).get(j).domain.contains(value)) {
                        cellList.get(i).get(j).domain.remove(new Integer(value));
                    }
                    if (value == 0 && m != j)
                        fwdDeg++;
                }
                //checking columns
                for (int m = 0; m < size; m++) {
                    value = cellList.get(m).get(j).val;
                    if (cellList.get(i).get(j).domain.contains(value)) {
                        cellList.get(i).get(j).domain.remove(new Integer(value));
                    }
                    if (value == 0 && m != i)
                        fwdDeg++;
                }
                cellList.get(i).get(j).fwdDegree = fwdDeg;
            }
        }
    }


    public LatinSquare(LatinSquare latinSquare) {
        this.size = latinSquare.size;
        this.cellList = new ArrayList<ArrayList<Cell>>();
        this.unassignedCells = new ArrayList<>();
        ArrayList<Cell> temp;
        for (int i = 0; i < size; i++) {
            temp = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                Cell cell = new Cell(latinSquare.cellList.get(i).get(j));
                temp.add(cell);
                if(cell.val==0)
                    this.unassignedCells.add(cell);
            }
            cellList.add(temp);
        }
    }

    public ArrayList<Cell> brelaz() {
        ArrayList<Cell> temp = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cellList.get(i).get(j).val == 0) {
                    temp.add(new Cell(cellList.get(i).get(j)));
                }
            }
        }
        Collections.sort(temp, brelazComparator);
        return temp;
    }

    public Comparator<Cell> brelazComparator = new Comparator<Cell>() {
        @Override
        public int compare(Cell o1, Cell o2) {
            if (o1.domain.size() < o2.domain.size())
                return -1;
            else if (o1.domain.size() > o2.domain.size())
                return 1;
            else {
                if (o1.fwdDegree > o2.fwdDegree)
                    return -1;
                else if (o1.fwdDegree < o2.fwdDegree)
                    return 1;
                else return 0;
            }
        }
    };

    public ArrayList<Cell> domdeg() {
        ArrayList<Cell> temp = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cellList.get(i).get(j).val == 0) {
                    temp.add(new Cell(cellList.get(i).get(j)));
                }
            }
        }
        Collections.sort(temp, domdegComparator);
        return temp;
    }

    public Comparator<Cell> domdegComparator = new Comparator<Cell>() {
        @Override
        public int compare(Cell o1, Cell o2) {
            double o1size = o1.domain.size();
            double o2size = o2.domain.size();
            double deg = size * 2.0 - 2.0;
            if ((o1size / deg) < (o2size / deg))
                return -1;
            else if ((o1size / deg) > (o2size / deg))
                return 1;
            return 0;
        }
    };

    public boolean solve(int choice) throws InterruptedException {
        //int unassigned = initialiseDomain();
        int unassigned = unassignedCells.size();
        if (unassigned == 0) {
            System.out.println();
            System.out.println("-------------FINAL RES------------");
            System.out.println();
            System.out.println(NodeCount);
            System.out.println(cellList.size());
            printCellList();
            System.out.println();
            return true;
        }
        ArrayList<Cell> unassignedList;
        if(choice == 1)
            unassignedList = brelaz();
        else if (choice==2)
            unassignedList = domdeg();
        else
            unassignedList = this.unassignedCells;

        for (int i = 0; i < unassignedList.size(); i++) {
            NodeCount++;
            LatinSquare t2 = new LatinSquare(this);
            for(int j=0; j< unassignedList.get(i).domain.size(); j++){
                LatinSquare temp = new LatinSquare(t2);
                temp.assignValue(unassignedList.get(i), unassignedList.get(i).domain.get(j));
                //System.out.println(unassignedList.get(i).row+" "+unassignedList.get(i).col);
                //temp.printCellList();
                //printCellList();
                temp.initialiseDomainCell(unassignedList.get(i));
                BacktrackCount++;
                if(temp.FwdChecking()){
                boolean res = temp.solve(choice);
                if(res)
                    return true;
                }
                temp = null;
            }
        }
        return false;
    }

    public void assignValue(Cell cell, int val) {
        cellList.get(cell.row).get(cell.col).val = val;
        cellList.get(cell.row).get(cell.col).isAssigned = true;
        cellList.get(cell.row).get(cell.col).domain.clear();
        cellList.get(cell.row).get(cell.col).fwdDegree = 0;
        for(int i=0; i< unassignedCells.size(); i++){
            if(unassignedCells.get(i).row==cell.row && unassignedCells.get(i).col==cell.col) {
                unassignedCells.remove(i);
                break;
            }
        }
    }

    public boolean FwdChecking() {
        LatinSquare tempLS = new LatinSquare(this);
        tempLS.initialiseDomain();
        for (int i = 0; i < tempLS.size; i++) {
            for (int j = 0; j < tempLS.size; j++) {
                if (tempLS.cellList.get(i).get(j).val == 0 && tempLS.cellList.get(i).get(j).domain.size() == 0)
                    return false;
            }
        }
        tempLS = null;
        return true;
    }

    public ArrayList<Cell> SDF() {
        ArrayList<Cell> temp = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cellList.get(i).get(j).val == 0) {
                    temp.add(new Cell(cellList.get(i).get(j)));
                }
            }
        }
        Collections.sort(temp, SDFComparator);
        return temp;
    }

    public Comparator<Cell> SDFComparator = new Comparator<Cell>() {
        @Override
        public int compare(Cell o1, Cell o2) {
            if(o1.domain.size()<o2.domain.size())
                return -1;
            else if(o1.domain.size()>o2.domain.size())
                return 1;
            return 0;
        }
    };
    public boolean MAC(){
        LatinSquare tempLS = new LatinSquare(this);
        ArrayList<Cell> tempList = new ArrayList();
        tempLS.initialiseDomain();
        tempList = tempLS.SDF();
        while (!tempList.isEmpty() && tempList.get(0).domain.size()==1){
            int row = tempList.get(0).row;
            int col = tempList.get(0).col;
            tempLS.assignValue(tempList.get(0),tempList.get(0).domain.get(0));
            //System.out.println("Assigning: ("+row+","+col+")"+tempList.get(0).domain.size());
            tempLS.initialiseDomain();
            tempList = tempLS.SDF();
        }
        if(tempList.size()==0 || tempList.get(0).domain.size()!=0)
            return true;
        else
            return false;
    }
    public boolean solveMAC(){
        int unassigned = initialiseDomain();
        //System.out.println("Unassigned: "+unassigned);
        if (unassigned == 0) {
            System.out.println();
            System.out.println("-------------FINAL RES------------");
            System.out.println();
            System.out.println(cellList.size());
            printCellList();
            System.out.println();
            return true;
        }
        ArrayList<Cell> unassignedList = new ArrayList<>();
        unassignedList = SDF();
        //System.out.println(unassignedList);
        for (int i = 0; i < unassignedList.size(); i++) {
            NodeCount++;
            for (int j = 0; j < unassignedList.get(i).domain.size(); j++) {
                LatinSquare temp = new LatinSquare(this);
                temp.assignValue(unassignedList.get(i), unassignedList.get(i).domain.get(j));
                //printCellList();
                BacktrackCount++;
                boolean mac = temp.MAC();
                //System.out.println("After MAC");
                //printCellList();

                if (mac) {
                    if(temp.solveMAC())
                        return true;
                }
                temp.deassignValue(unassignedList.get(i));
            }
        }
        return false;
    }
    public boolean solve2(){
        System.out.println("unassigned : "+unassignedCells.size());

        if(this.unassignedCells.size()==0){
            printCellList();
            return true;
        }
        this.initialiseDomain();
        ArrayList<Cell> unass = brelaz();
        for(int i=0; i<unass.size(); i++){
            for (int j=0; j<unass.get(i).domain.size(); j++){
                printCellList();
                assignValue(unass.get(i), unass.get(i).domain.get(j));
                boolean mac = MAC();
                if (mac) {
                    if(solve2())
                    return true;
                }
                deassignValue(unass.get(i));
            }
        }
        return false;
    }

    private void deassignValue(Cell cell) {
        cellList.get(cell.row).get(cell.col).val = 0;
        cellList.get(cell.row).get(cell.col).isAssigned = false;
        cellList.get(cell.row).get(cell.col).domain = new ArrayList<>();
        cellList.get(cell.row).get(cell.col).fwdDegree = 0;
        unassignedCells.add(cellList.get(cell.row).get(cell.col));
        initialiseDomain();
        //System.out.println("Value de assigned from ("+cell.row+","+cell.col+")");
    }
    public boolean solveBacktrack(){
        //initialiseDomain();
        int unassigned = initialiseDomain();
        System.out.println("Unassigned: "+unassigned);
        if (unassigned == 0) {
            System.out.println();
            System.out.println("-------------FINAL RES------------");
            System.out.println();
            System.out.println(cellList.size());
            printCellList();
            System.out.println();
            return true;
        }
        for (int i=0; i<unassigned;i++){
            System.out.println("domain size: "+unassignedCells.get(i).domain.size());
            for(int j=unassignedCells.get(i).domain.size()-1;j>=0 ; j--){
                int val = unassignedCells.get(i).domain.get(j);
                LatinSquare lt = new LatinSquare(this);
                lt.assignValue(unassignedCells.get(i),val );
                if(lt.MAC()){
                    if (lt.solveBacktrack())
                        return true;
                }
                lt = null;
            }
        }
        return false;
    }
}