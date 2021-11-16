package State;

public class State {
    public int [] puzzleBlock;
    public int zeroPos;
    public int g;
    public State previous;

    public int getZeroPos() {
        return zeroPos;
    }

    public State(int [] arr){
        puzzleBlock = new int[arr.length];
        for(int i=0; i<arr.length; i++)
            puzzleBlock[i] = arr[i];
        g = 0;
        for(int i=0; i<puzzleBlock.length; i++){
            if(puzzleBlock[i]==0){
                zeroPos = i;
                break;
            }
        }
        previous = null;
    }
    public int getFM(int[] array){
        return g + getManhattanHeuristic(array);
    }

    public int getFH(int[] array){
        return g + getHeuristic(array);
    }
    public int getManhattanHeuristic(int [] array){
        int result = 0;
        int size = (int) Math.sqrt(puzzleBlock.length);
        for(int i=0;i<puzzleBlock.length ;i++){
            if(puzzleBlock[i]!=0){
                int pos = 0;
                for(int j=0; j<array.length; j++){
                    if(array[j]==puzzleBlock[i])

                        pos= j;
                }
                int presentColoumn = (int) (i/Math.sqrt(puzzleBlock.length));
                int goalColoumn = (int) (pos/Math.sqrt(puzzleBlock.length));
                int presentRow = i%size;
                int goalRow = pos%size;
                int manD = Math.abs(goalColoumn-presentColoumn)+Math.abs(goalRow-presentRow);
                //System.out.println(puzzleBlock[i] +" : "+ manD);
                result=result+ manD;
            }
        }
        return result;
    }
    public int getHeuristic(int [] array){
        int result =0;
        for(int i=0; i<puzzleBlock.length; i++){
            if(puzzleBlock[i]!=array[i]&& puzzleBlock[i]!=0)
                result++;
        }
        return result;
    }

    public void print() {
        int gridSize = (int) Math.sqrt(puzzleBlock.length);
        if(previous==null){
            for(int i=0; i<puzzleBlock.length; i++){
                System.out.print(puzzleBlock[i]+"  ");
                if(i%gridSize==gridSize-1)
                    System.out.println();

            }
            System.out.println();
            return;
        }else previous.print();
        for(int i=0; i<puzzleBlock.length; i++){
            System.out.print(puzzleBlock[i]+"  ");
            if(i%gridSize==gridSize-1)
                System.out.println();

        }
        System.out.println();

    }
}
