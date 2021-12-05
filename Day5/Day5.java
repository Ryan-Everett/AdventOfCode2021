package Day5;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day5 {
    static int solve1(String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        ArrayList<ArrayList<Integer> > grid= new ArrayList<ArrayList<Integer> >();
        int ans = 0;
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            String[] ends = line.split("\\s+->\\s+");
            String[] start = ends[0].split(",");
            String[] end = ends[1].split(",");

            //As we only consider the line being horizontical or vertical we can say it starts at the lowest x and y and ends at the highest
            int x1 = Math.min(Integer.valueOf(start[0]), Integer.valueOf(end[0]));
            int y1 = Math.min(Integer.valueOf(start[1]), Integer.valueOf(end[1]));
            int x2 = Math.max(Integer.valueOf(start[0]), Integer.valueOf(end[0]));
            int y2 = Math.max(Integer.valueOf(start[1]), Integer.valueOf(end[1]));


            while(grid.size() <= x2){   //Extend the grid to cover the new line
                grid.add(new ArrayList<Integer>());
            }

            if ((x1 == x2 )|| (y1 == y2)){  //Don't consider diagonal lines
                for (int x = x1; x <= x2; x++ ){
                    ArrayList<Integer> xCol = grid.get(x);
                    for (int y = y1; y <= y2; y++){
                        while (xCol.size() <= y){
                            xCol.add(0);
                        }
                        int currValue = xCol.get(y);
                        xCol.set(y, currValue + 1);
                        if (currValue == 1){
                            ans ++;
                        }
                    }
                }
            }
        }
        return ans;
    }

    static int solve2 (String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        ArrayList<ArrayList<Integer> > grid= new ArrayList<ArrayList<Integer> >();
        int ans = 0;
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            String[] ends = line.split("\\s+->\\s");
            String[] start = ends[0].split(",");
            String[] end = ends[1].split(",");
            int x1 = Integer.valueOf(start[0]);
            int y1 = Integer.valueOf(start[1]);
            int x2 = Integer.valueOf(end[0]);
            int y2 = Integer.valueOf(end[1]);

            while(grid.size() <= Math.max(x1,x2)){  //Extend the grid to cover the new line
                grid.add(new ArrayList<Integer>());
            }
            int gx = Integer.signum(x2 - x1);   //Gradients of x and y
            int gy = Integer.signum(y2 - y1);
            int x = x1 - gx; int y = y1 - gy;
            do {
                x += gx;
                y += gy;
                ArrayList<Integer> xCol = grid.get(x);
                while(xCol.size() <= y){
                    xCol.add(0);
                }
                int v = xCol.get(y);
                if (v == 1){
                    ans++;
                }
                xCol.set(y, v+1);
            } while ((gx * x) < (gx * x2) || (gy * y)  < (gy * y2));
        }
        return ans;
    }
    public static void main(String[] args) throws IOException{
        String fileName = "Inputs/Day5Input.txt";
        System.out.println(solve1(fileName));
        System.out.println(solve2(fileName));
    }
}
