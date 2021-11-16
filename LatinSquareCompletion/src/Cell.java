import java.util.ArrayList;

public class Cell {
    boolean isAssigned;
    ArrayList<Integer> domain;
    int row;
    int col;
    public int val;
    public int fwdDegree;

    public Cell(int val, int row, int col){
        this.val = val;
        this.row = row;
        this.col = col;
        if(val==0)
            isAssigned = false;
        else isAssigned = true;
        domain = new ArrayList<>();
        fwdDegree = 0;
    }

    public Cell(Cell cell) {
        this.val = cell.val;
        this.isAssigned = cell.isAssigned;
        this.row = cell.row;
        this.col = cell.col;
        this.fwdDegree = cell.fwdDegree;
        domain= new ArrayList<>();
        for(int i=0; i<cell.domain.size(); i++)
            domain.add(cell.domain.get(i));
    }

    @Override
    public String toString() {
        return "{"+
                "(" + row +","+
                + col +") "+
                ", val=" + val +
                ", domain=" + domain +
                '}'+"\n";
    }

    public boolean equals(Cell cell){
        if(cell.row==row && cell.col==col)
            return true;
        else return false;
    }
}
