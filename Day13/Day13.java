package Day13;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

class Point {   //A wrapper class for int[], allows me to make a HashSet which relies only on the values of the array
    final int vals[];
    Point(int mode, int x1, int x2){
        vals = new int [2];
        vals[mode] = x1;
        vals[(mode+1)%2] = x2;
    }

    Point(){
        vals = new int [2];
    }

    @Override
    public boolean equals(Object p){
        if (p instanceof Point){
            return (vals[0] == ((Point) p).vals[0] && vals[1] == ((Point) p).vals[1]);
        }
        return false;
    }
    @Override
    public int hashCode() {
        return vals[0]*53 + vals[1] * 271;
    }
}
public class Day13 {
    static String[] solve (String fileName) throws IOException{
        Scanner sc = new Scanner(new File(fileName));
        HashSet<Point> paper = new HashSet<>();
        int points = 0;
        while (sc.hasNextLine()){    //Fill original board
            String[] coords = sc.nextLine().split(",");
            if(coords.length == 1){
                break;
            }
            paper.add(new Point(0, Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
            points ++;
        }
        int ans1 = 0;
        int [] paperSize = new int [2];
        while(sc.hasNextLine()){    
            String [] command = sc.nextLine().split(" ");
            String [] fold = command[2].split("=");
            int crease = Integer.parseInt(fold[1]);
            int mode = 0;
            if (fold[0].equals("y")){
                mode = 1;
            }
            paperSize[mode] = crease;
            HashSet<Point> newPaper = new HashSet<>();
            for(Point point : paper){   //Using mode we can combine the cases
                int x = point.vals[mode];
                int y = point.vals[(mode+1)%2];
                if (x < crease){
                    newPaper.add(point);
                }
                else{
                    Point newP = new Point(mode, x - 2*(x - crease), y);
                    if (paper.contains(newP)){
                        points -= 1;
                    }
                    else {
                        newPaper.add(newP);
                    }
                }
            }
            if (ans1 == 0){ //Only do this for the first round
                ans1 = points;
            }
            paper = newPaper;
        }
        StringBuilder sb = new StringBuilder();
        for(int y = 0; y < paperSize[1]; y++){
            for(int x = 0; x < paperSize[0]; x ++){
                if (paper.contains(new Point(0, x, y))){
                    sb.append("#");
                }
                else{
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        return new String[] {String.valueOf(ans1),sb.toString()};
    }

    public static void main(String[] args) throws IOException {
        String fileName = "Inputs/Day13Input.txt";
        String[] ans = solve(fileName);
        System.out.println("Answer 1: " + ans[0] + "\nAnswer 2: \n" + ans[1]);
    }
}
