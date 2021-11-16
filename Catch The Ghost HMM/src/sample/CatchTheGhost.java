package sample;

import java.util.ArrayList;

public class CatchTheGhost {
    int size;
    double[][] probabilityTable;
    double[][] tempProbTable;
    Position ghostPosition;
    double straightmoveProb = 0.8;
    double diagonalmoveProb = 0.2;
    int RedMaxDist = 2;
    int OrangeMaxDist = 4;
    public CatchTheGhost(int s) {
        this.size = s;
        probabilityTable = new double[s][s];
        tempProbTable = new double[s][s];
        double initprob = new Double(1.000 / new Double(s * s));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                probabilityTable[i][j] = initprob;
        }
        int row = (int) (Math.random()*size);
        int col = (int) (Math.random()*size);
        ghostPosition = new Position(row, col);
        //System.out.println(row+"   "+col);
        printProbability();
    }

    public void printProbability() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++){
                System.out.print(String.format("%.04f", probabilityTable[i][j])+"  ");
            }
            System.out.println();
        }
        System.out.println("Ghost Position: ("+ghostPosition.row+" , "+ghostPosition.col+")");
    }

    public void TimeIncrement() {
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                updateProbability(i, j);
            }
        }

        for(int i=0; i<size; i++) {
            for (int j = 0; j < size; j++) {
                probabilityTable[i][j] = tempProbTable[i][j];
            }
        }
        changeGhostPosition();
        printProbability();
    }

    public void changeGhostPosition(){
        double checkVal = Math.random();
        System.out.println(checkVal);
        if(checkVal<diagonalmoveProb){
            //diagonal Moves
            checkVal = Math.random();
            ArrayList<Position> possibleMoves = possibleDiagMoves(ghostPosition.row, ghostPosition.col);
            System.out.println("Diag: checkval: "+checkVal+ "   moves: "+ possibleMoves.size());
            int index = (int) (checkVal*possibleMoves.size());
            ghostPosition.row = possibleMoves.get(index).row;
            ghostPosition.col = possibleMoves.get(index).col;
        }else{
            checkVal = Math.random();
            ArrayList<Position> possibleMoves = possibleStraightMoves(ghostPosition.row, ghostPosition.col);
            System.out.println("Diag: checkval: "+checkVal+ "   moves: "+ possibleMoves.size());
            int index = (int) (checkVal*possibleMoves.size());
            ghostPosition.row = possibleMoves.get(index).row;
            ghostPosition.col = possibleMoves.get(index).col;
        }
    }
    public ArrayList<Position> possibleDiagMoves(int row, int col){
        ArrayList<Position> temp = new ArrayList<>();
        temp.add(new Position(row,col));
        if (row!=0 && col!=0)
            temp.add(new Position(row-1,col-1));
        if (row!=0 && col!=size-1)
            temp.add(new Position(row-1,col+1));
        if (row!=size-1 && col!=0)
            temp.add(new Position(row+1,col-1));
        if (row!=size-1 && col!=size-1)
            temp.add(new Position(row+1,col+1));
        return temp;
    }
    public ArrayList<Position> possibleStraightMoves(int row, int col){
        ArrayList<Position> temp = new ArrayList<>();
        if(row!=0)
            temp.add(new Position(row-1, col));
        if (row!=size-1)
            temp.add(new Position(row+1, col));
        if(col!=0)
            temp.add(new Position(row, col-1));
        if (col!=size-1)
            temp.add(new Position(row, col+1));
        return temp;
    }
    public void updateProbability(int row, int col) {
        double probability = 0;
        if(row!=0){
            //double moves = getStraightmoveNum(row-1,col);
            double moves = possibleStraightMoves(row-1,col).size();
            probability = probability + probabilityTable[row-1][col] * straightmoveProb/moves;
        }
        if (row!=size-1){
            //double moves = getStraightmoveNum(row+1,col);
            double moves = possibleStraightMoves(row+1,col).size();
            probability = probability + probabilityTable[row+1][col] * straightmoveProb/moves;
        }
        if(col!=0) {
            //double moves = getStraightmoveNum(row,col-1);
            double moves = possibleStraightMoves(row,col-1).size();
            probability = probability + probabilityTable[row][col-1] * straightmoveProb/moves;
        }
        if (col!=size-1) {
            //double moves = getStraightmoveNum(row,col+1);
            double moves = possibleStraightMoves(row,col+1).size();
            probability = probability + probabilityTable[row][col+1] * straightmoveProb/moves;
        }
        //System.out.println("After straight moves :"+  probability);
        if (row!=0 && col!=0){
            //double moves = getDiagonalmoveNum(row-1, col-1);
            double moves = possibleDiagMoves(row-1,col-1).size();
            probability = probability + probabilityTable[row-1][col-1] * diagonalmoveProb/moves;
        }
        if (row!=0 && col!=size-1){
            //double moves = getDiagonalmoveNum(row-1, col+1);
            double moves = possibleDiagMoves(row-1,col+1).size();
            probability = probability + probabilityTable[row-1][col+1] * diagonalmoveProb/moves;
        }
        if (row!=size-1 && col!=0){
            //double moves = getDiagonalmoveNum(row+1, col-1);
            double moves = possibleDiagMoves(row+1,col-1).size();
            probability = probability + probabilityTable[row+1][col-1] * diagonalmoveProb/moves;
        }
        if (row!=size-1 && col!=size-1){
            //double moves = getDiagonalmoveNum(row+1, col+1);
            double moves = possibleDiagMoves(row+1,col+1).size();
            probability = probability + probabilityTable[row+1][col+1] * diagonalmoveProb/moves;
        }
        //System.out.println("After diag moves :" + probability);
        //double moves = getDiagonalmoveNum(row, col);
        double moves = possibleDiagMoves(row,col).size();
        probability = probability + probabilityTable[row][col] * diagonalmoveProb/moves;
        //probabilityTable[row][col] = probability;
        tempProbTable[row][col] = probability;
    }
    public double getStraightmoveNum(int row, int col){
        double moves = 0;
        if(row!=0)
            moves++;
        if (row!=size-1)
            moves++;
        if(col!=0)
            moves++;
        if (col!=size-1)
            moves++;
        return moves;
    }
    public double getDiagonalmoveNum(int row, int col){
        double moves = 1;
        if (row!=0 && col!=0)
            moves++;
        if (row!=0 && col!=size-1)
            moves++;
        if (row!=size-1 && col!=0)
            moves++;
        if (row!=size-1 && col!=size-1)
            moves++;
        return moves;
    }

    public String getSensorReading(Position p) {
        int manhatDistance = getManhattanDistance(p.row, p.col);
        if(manhatDistance<=RedMaxDist){
            return "Red";
        }else if(manhatDistance<=OrangeMaxDist){
            return "Orange";
        }else{
            return "Green";
        }
    }
    public int getManhattanDistance(int row, int col){
        int retVal = Math.abs(row-ghostPosition.row);
        retVal = retVal + Math.abs(col-ghostPosition.col);
        return retVal;
    }

    public void updateProbabilityAfterSensor(Position p, String sensonReading) {
        double total = 0;
        if(sensonReading.equalsIgnoreCase("green")){
            for (int i=0; i<size; i++){
                for(int j=0; j<size; j++){
                    int dist = getManhattanDistance(i, j,p);
                    if(dist<=OrangeMaxDist)
                        probabilityTable[i][j] = 0;
                    else
                        total = total + probabilityTable[i][j];
                }
            }
            for (int i=0; i<size; i++) {
                for (int j = 0; j < size; j++) {
                    if(probabilityTable[i][j]!=0){
                        probabilityTable[i][j] = probabilityTable[i][j]/total;
                    }
                }
            }
        }else if(sensonReading.equalsIgnoreCase("orange")){
            for (int i=0; i<size; i++){
                for(int j=0; j<size; j++){
                    int dist = getManhattanDistance(i, j, p);
                    if(dist > OrangeMaxDist || dist <= RedMaxDist)
                        probabilityTable[i][j] = 0;
                    else
                        total = total + probabilityTable[i][j];
                }
            }
            for (int i=0; i<size; i++) {
                for (int j = 0; j < size; j++) {
                    if(probabilityTable[i][j]!=0){
                        probabilityTable[i][j] = probabilityTable[i][j]/total;
                    }
                }
            }
        }else{
            for (int i=0; i<size; i++){
                for(int j=0; j<size; j++){
                    int dist = getManhattanDistance(i, j, p);
                    if(dist>RedMaxDist)
                        probabilityTable[i][j] = 0;
                    else
                        total = total + probabilityTable[i][j];
                }
            }
            for (int i=0; i<size; i++) {
                for (int j = 0; j < size; j++) {
                    if(probabilityTable[i][j]!=0){
                        probabilityTable[i][j] = probabilityTable[i][j]/total;
                    }
                }
            }
        }
        printProbability();
    }
    public int getManhattanDistance(int row, int col, Position p){
        int dist = Math.abs(p.row-row);
        dist = dist + Math.abs(p.col-col);
        return dist;
    }
}

