package Day4;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Day4 {
    static int[] solve (String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        String drawString = sc.next();
        //Make map of draw order
        HashMap<Integer, Integer> drawPos = new HashMap<>();
        String draw[] = drawString.split(",");
        int k = 0;
        for (String x : draw){
            drawPos.put(Integer.valueOf(x), k);
            k++;
        }

        //Find the best card
        int minWin = Integer.MAX_VALUE;
        int maxWin = -1;
        int bestBoardIndex = -1;
        int worstBoardIndex = -1;
        int currBoard = 0;
        LinkedList<int[][]> boards = new LinkedList<>();
        while(sc.hasNext()){    //For each board
            int[][] board = new int[5][5];
            int[] rowCosts = new int[5];
            int[] colCosts = new int[5];
            for (int i = 0; i < 5; i++){    //Set up board and win times for columns and rows
                for (int j = 0; j < 5; j++){
                    int next = sc.nextInt();
                    board[i][j] = next;
                    maxAssign(rowCosts, i, drawPos.get(next));
                    maxAssign(colCosts, j, drawPos.get(next));
                }
            }
            boards.add(board);

            int winTime = draw.length;
            for (int i = 0; i < 5; i++){    //Find when the board wins
                if (rowCosts[i] < winTime){
                    winTime = rowCosts[i];
                }
                if (colCosts[i] < winTime){
                    winTime = colCosts[i];
                }
            }

            //See if this board is the best or worse
            if (winTime < minWin){
                minWin = winTime;
                bestBoardIndex = currBoard; 
            }
            if (winTime > maxWin){
                maxWin = winTime;
                worstBoardIndex = currBoard;
            }
            currBoard ++;
        }

        // Find scores for best and worst boards
        int[][] bestBoard = boards.get(bestBoardIndex);
        int[][] worstBoard = boards.get(worstBoardIndex);
        int bestScore = 0;
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if(drawPos.get(bestBoard[i][j]) > minWin){
                    bestScore += bestBoard[i][j];
                }
            }
        }
        int worstScore = 0;
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if(drawPos.get(worstBoard[i][j]) > maxWin){
                    worstScore += worstBoard[i][j];
                }
            }
        }
        return new int[] {bestScore * Integer.valueOf(draw[minWin]), worstScore * Integer.valueOf((draw[maxWin]))};
    }

    static void maxAssign (int[] costs, int index, int v){
        if (v > costs[index]){
            costs[index] = v;
        }
    }
    public static void main(String[] args) throws IOException{
        int[] ans = solve("Inputs/Day4Input.txt");
        System.out.println("Answer 1: " + ans[0] + "\nAnswer 2: " + ans[1]);
    }
}