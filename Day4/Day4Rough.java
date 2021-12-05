package Day4;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class Day4Rough {
    static int solve (String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        String drawString = sc.next();
        //Make map of draw order
        HashMap<Integer, Integer> drawPos = new HashMap<>();
        String arr[] = drawString.split(",");
        int k = 0;
        for (String x : arr){
            drawPos.put(Integer.valueOf(x), k);
            k++;
        }

        //Find the best card
        int opt = Integer.MAX_VALUE;
        int optNum = -1;
        int bestBoard = -1;
        int currBoard = 0;
        LinkedList<int[][]> boards = new LinkedList<>();
        while(sc.hasNext()){
            int[][] board = new int[5][5];
            int[] rowCosts = new int[5];
            int[] colCosts = new int[5];
            for (int i = 0; i < 5; i++){
                for (int j = 0; j < 5; j++){
                    int next = sc.nextInt();
                    //System.out.print(", " + next);
                    board[i][j] = next;
                    if (maxAssign(rowCosts, i, drawPos.get(board[i][j])) | maxAssign(colCosts, j, drawPos.get(board[i][j]))){
                    }
                }
            }
            boards.add(board);
            for (int i = 0; i < 5; i++){
                if (rowCosts[i] < opt){
                    opt = rowCosts[i];
                    bestBoard = currBoard;
                }
                if (colCosts[i] < opt){
                    opt = colCosts[i];
                    bestBoard = currBoard;
                }
            }
            currBoard ++;
        }
        //Just need to iterate through board and count up
        int sum = 0;
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if(drawPos.get(boards.get(bestBoard)[i][j]) > opt){
                    sum += boards.get(bestBoard)[i][j];
                }
                if (drawPos.get(boards.get(bestBoard)[i][j]) == opt){
                    optNum = boards.get(bestBoard)[i][j];
                }
            }
        }

        System.out.println("Best board" + bestBoard + " Best cost" + opt);
        System.out.println("Sum " + sum + " mrc " + optNum + " tot " + (sum * optNum));
        return 0;
    }

    static int solve2 (String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        String drawString = sc.next();
        //Make map of draw order
        HashMap<Integer, Integer> drawPos = new HashMap<>();
        String arr[] = drawString.split(",");
        int k = 0;
        for (String x : arr){
            drawPos.put(Integer.valueOf(x), k);
            k++;
        }

        //Find the best card
        int opt = -1;
        int optNum = -1;
        int bestBoard = -1;
        int currBoard = 0;
        LinkedList<int[][]> boards = new LinkedList<>();
        while(sc.hasNext()){
            int[][] board = new int[5][5];
            int[] rowCosts = new int[5];
            int[] colCosts = new int[5];
            for (int i = 0; i < 5; i++){
                for (int j = 0; j < 5; j++){
                    int next = sc.nextInt();
                    //System.out.print(", " + next);
                    board[i][j] = next;
                    maxAssign(rowCosts, i, drawPos.get(board[i][j]));
                    maxAssign(colCosts, j, drawPos.get(board[i][j]));
                }
                //System.out.print("\n");
            }
            boards.add(board);
            int winTime = Integer.MAX_VALUE;
            for (int i = 0; i < 5; i++){
                if (rowCosts[i] < winTime){
                    winTime = rowCosts[i];
                }
                if (colCosts[i] < winTime) {
                    winTime = colCosts[i];
                }
            }
            if (winTime > opt){
                opt = winTime;
                bestBoard = currBoard;
            }
            currBoard ++;
        }
        //Just need to iterate through board and count up
        int sum = 0;
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if(drawPos.get(boards.get(bestBoard)[i][j]) > opt){
                    sum += boards.get(bestBoard)[i][j];
                }
                if (drawPos.get(boards.get(bestBoard)[i][j]) == opt){
                    optNum = boards.get(bestBoard)[i][j];
                }
            }
        }

        System.out.println("Best board" + bestBoard + " Best cost" + opt);
        System.out.println("Sum " + sum + " mrc " + optNum + " tot " + (sum * optNum));
        return 0;
    }
    static boolean maxAssign (int[] costs, int index, int v){
        if (v > costs[index]){
            costs[index] = v;
            return true;
        }
        return false;
    }
    public static void main(String[] args) throws IOException{
        solve("In4/Day4Input.txt");
        solve2("Day4/Day4Input.txt");
    }
}
