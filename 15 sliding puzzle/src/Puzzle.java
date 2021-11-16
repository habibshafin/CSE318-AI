import State.State;

import java.io.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Puzzle {
    public int[] initialState ;
    public int[] goalState;
    public int size;
    public State state;
    public int gridSize;
    public final HashSet<State> visited = new HashSet<State>();
    public Puzzle(int size){
        this.size =size;
        initialState = new int[size];
        goalState = new int[size];
        gridSize = (int) Math.sqrt(size);
    }
    public int findZero(){
        for (int i=0; i<size; i++){
            if(initialState[i]==0)
                return i;
        }
        return -1;
    }
    public boolean isSolvable(){
        int invCount = 0;
        for (int i = 0; i < size - 1; i++)
            for (int j = i+1; j < size; j++)
                if (initialState[j]!=0 && initialState[i]!=0 &&  (initialState[i] > initialState[j])) {
                    invCount++;
                }
        //System.out.println(invCount);
        if(Math.sqrt(size)%2==1){
            return (invCount%2==0);
        }else {
            int zeroPos = findZero();
            if((zeroPos/4)%2==0)
                return (invCount%2==1);
            else
                return (invCount%2==0);
        }
    }
    public final PriorityQueue<State> queue = new PriorityQueue<State>(99999999, new Comparator<State>() {
        @Override
        public int compare(State o1, State o2) {
            return o1.getFM(goalState) - o2.getFM(goalState);
        }
    });
    public final PriorityQueue<State> queue2 = new PriorityQueue<State>(99999999, new Comparator<State>() {
        @Override
        public int compare(State o1, State o2) {
            return o1.getFH(goalState) - o2.getFH(goalState);
        }
    });
    private void solve() {
        queue.clear();
        queue.add(state);
        while(!queue.isEmpty()){
            State st = queue.poll();
            if(isSolved(st)){
                System.out.println("Manhattan DIstance H : Moves taken : " + st.g);
                System.out.println("Manhattan DIstance H : Expanded Nodes :"+visited.size());
                System.out.println();
                st.print();
                return;
            }
            visited.add(st);
            if(st.getZeroPos()/gridSize!=0){
                //System.out.println(st.zeroPos);
                // it means not in the first row so up move is possible
                State temp = upMove(st);
                if(!visited.contains(temp))
                    queue.add(temp);
                //temp.print();

                //st.print();
                //System.out.println();
            }
            //System.out.println(st.zeroPos);
            if(st.getZeroPos()/gridSize < gridSize-1){
                //it means in not in the last row so move down is possible
                State temp = downMove(st);
                if(!visited.contains(temp))
                    queue.add(temp);
                //temp.print();
                //System.out.println();
            }

            //System.out.println(st.zeroPos);
            if (st.getZeroPos()%gridSize!=gridSize-1){
                //not in the last column
                State temp = rightMove(st);
                if(!visited.contains(temp))
                    queue.add(temp);

            }

            if (st.getZeroPos()%gridSize!=0){
                //not in the first column
                State temp = leftMove(st);
                if(!visited.contains(temp))
                    queue.add(temp);
                //if(queue.size()==99999998)
                    //System.out.println("mara");
            }

        }
    }
    private boolean isSolved(State st) {
        for(int i=0; i<st.puzzleBlock.length; i++){
            if(st.puzzleBlock[i]!=goalState[i]) return false;
        }
        return true;
    }

    private State upMove(State st) {
        State result = new State(st.puzzleBlock);
        result.zeroPos = st.zeroPos - gridSize;
        result.puzzleBlock[st.zeroPos] = result.puzzleBlock[result.zeroPos];
        result.puzzleBlock[result.zeroPos] = 0;
        result.g = st.g + 1;
        result.previous = st;
        return result;
    }
    private State downMove(State st){
        State result = new State(st.puzzleBlock);
        result.zeroPos = st.zeroPos + gridSize;
        result.puzzleBlock[st.zeroPos] = result.puzzleBlock[result.zeroPos];
        result.puzzleBlock[result.zeroPos] = 0;
        result.g = st.g + 1;
        result.previous = st;
        return result;
    }
    private State rightMove(State st){
        State result = new State(st.puzzleBlock);
        result.zeroPos = st.zeroPos+1;
        result.puzzleBlock[st.zeroPos] = result.puzzleBlock[result.zeroPos];
        result.puzzleBlock[result.zeroPos] = 0;
        result.g = st.g + 1;
        result.previous = st;
        return result;
    }
    private State leftMove(State st){
        State result = new State(st.puzzleBlock);
        result.zeroPos = st.zeroPos-1;
        result.puzzleBlock[st.zeroPos] = result.puzzleBlock[result.zeroPos];
        result.puzzleBlock[result.zeroPos] = 0;
        result.g = st.g + 1;
        result.previous = st;
        return result;
    }

    public static void main(String[] args) throws IOException {
        //Scanner scanner = new Scanner(System.in);
        Scanner scanner = new Scanner(new File("src\\input"));
        int nos = scanner.nextInt();
        Puzzle puzzle = new Puzzle(16);
        for(int j=0; j<puzzle.size; j++){
            puzzle.goalState[j]=scanner.nextInt();
        }
        for(int i=0; i<nos-1; i++){
            for(int j=0; j<16; j++){
                puzzle.initialState[j] = scanner.nextInt();
            }
            puzzle.state = new State(puzzle.initialState);
            if(puzzle.isSolvable()) {
                System.out.println("The puzzle is solvable");
                puzzle.solve();
                puzzle.queue.clear();
                puzzle.visited.clear();
                puzzle.solveH();
                puzzle.queue2.clear();
                puzzle.visited.clear();
            }
            else
                System.out.println("The puzzle is not solvable");
        }

        /*System.out.println("Enter the initial state");
        for(int i=0; i<puzzle.size; i++){
            puzzle.initialState[i]=scanner.nextInt();
        }
        System.out.println("Enter the goal state");
        for(int i=0; i<puzzle.size; i++){
            puzzle.goalState[i]=scanner.nextInt();
        }
        puzzle.state = new State(puzzle.initialState);
        if(puzzle.isSolvable()) {
        System.out.println("The puzzle is solvable");
            puzzle.solve();
            puzzle.queue.clear();
            puzzle.visited.clear();
            puzzle.solveH();
        }
        else
            System.out.println("The puzzle is not solvable");
*/
    }

    private void solveH() {
        queue2.clear();
        queue2.add(state);
        while(!queue2.isEmpty()){
            State st = queue2.poll();
            if(isSolved(st)){
                System.out.println("Misplaced Tiles H : Moves taken : " + st.g);
                System.out.println("Misplaced Tiles H : Expanded Nodes :"+visited.size());
                System.out.println();
                st.print();
                return;
            }
            visited.add(st);
            if(st.getZeroPos()/gridSize!=0){
                // it means not in the first row so up move is possible
                State temp = upMove(st);
                if(!visited.contains(temp))
                    queue2.add(temp);
                //System.out.println();
            }
            //System.out.println(st.zeroPos);
            if(st.getZeroPos()/gridSize < gridSize-1){
                //it means in not in the last row so move down is possible
                State temp = downMove(st);
                if(!visited.contains(temp))
                    queue2.add(temp);
                //temp.print();
                //System.out.println();
            }

            //System.out.println(st.zeroPos);
            if (st.getZeroPos()%gridSize!=gridSize-1){
                //not in the last column
                State temp = rightMove(st);
                if(!visited.contains(temp))
                    queue2.add(temp);

            }

            if (st.getZeroPos()%gridSize!=0){
                //not in the first column
                State temp = leftMove(st);
                if(!visited.contains(temp))
                    queue2.add(temp);
                //if(queue.size()==99999998)
                //System.out.println("mara");
            }

        }
    }

}
