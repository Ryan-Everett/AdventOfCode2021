package Day1;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/*
Comparing methods to solve the problems:

This program can solve the problem in four ways:
All at once using a cyclic array,
All at once using variables to hold window
Move the whole input into an array and then solve by iterating through the array
Move the whole input into a LinkedList and then solve by iterating through the list

Benchmarking average time taken for 15000 solves:
        Quick took   0.944 ms
        Cyclic took  0.949 ms
        Array took   1.009 ms
        List took    1.052 ms

So it is quicker to not use a cyclic array

The set up time for the array version is slow but the benefit of much quicker solving makes it slightly faster overall to use than the linked list version
*/
public class BonusBenchmarking{

    static int[] quickSolve(String fileName) throws IOException{
        int[] ans = new int[2];
        Scanner sc = new Scanner(new File(fileName));
        int back = sc.nextInt();
        int middle = sc.nextInt();
        if (back < middle) {
            ans[0] ++;
        }
        int front = sc.nextInt();
        if (middle < front) {
            ans[0] ++;
        }
        while (sc.hasNextInt()) {
            int next = sc.nextInt();
            if (front < next) {
                ans[0] ++;
            }
            if (back < next) {
                ans[1] ++;
            }
            back = middle;
            middle = front;
            front = next;
        }
        return ans;
    }

    //Uses cyclic array to represent window
    static int[] quickSolveCyclic(String fileName) throws IOException{
        int[] ans = new int[2];
        int[] window = new int[3];
        Scanner sc = new Scanner(new File(fileName));
        window[0] = sc.nextInt();
        window[1] = sc.nextInt();
        if (window[0] < window[1]) {
            ans[0] ++;
        }
        window[2] = sc.nextInt();
        if (window[1] < window[2]) {
            ans[0] ++;
        }
        int frontIndex = 2;
        while (sc.hasNextInt()) {
            int next = sc.nextInt();
            if (window[frontIndex] < next) {
                ans[0] ++;
            }
            if (window[(frontIndex+2)%3] < next) {  //(frontIndex+2)%3 = backIndex
                ans[1] ++;
            }
            frontIndex = (frontIndex+2) %3;
            window[frontIndex] = next; //Add next to the window, at what will become the front
        }
        return ans;
    }

    static int[] solveArr(String fileName) throws IOException{
        Path path = Paths.get(fileName);
        int length = (int)Files.lines(path).count();
        int[] xs = new int[length];

        Scanner sc = new Scanner(new File(fileName));
        int j = 0;
        while (sc.hasNextInt()) {
            xs[j] = sc.nextInt();
            j += 1;
        }

        int[] ans = new int[2];
        if (xs[0] < xs[1]){
            ans[0] ++;
        }
        if (xs[1] < xs[2]){
            ans[0] ++;
        }
        for (int i = 3; i < xs.length; i++){
            if (xs[i-1] < xs[i]){
                ans[0] ++;
            }
            if (xs[i-3] < xs[i]) {
                ans[1] ++;
            }
        }
        return ans;
    }

    static int[] solveList (String fileName) throws IOException{
        LinkedList<Integer> numList = new LinkedList<Integer>();

        Scanner sc = new Scanner(new File(fileName));
        while (sc.hasNextInt()) {
            numList.add(sc.nextInt());
        }
        int[] ans = new int[2];
        Iterator<Integer> iter = numList.iterator();

        int back = iter.next();
        int middle = iter.next();
        int front = iter.next();

        if (back < middle){
            ans[0] ++;
        }
        if (middle < front){
            ans[0] ++;
        }
        while (iter.hasNext()){
            int next = iter.next();
            if (front < next){
                ans[0] ++;
            }
            if (back < next) {
                ans[1]++;
            }
            back = middle;
            middle = front;
            front = next;
        }
        return ans;
    }

    static void benchMark(String fileName) throws IOException{
        long t, t1=0, t2 =0, t3=0,t4=0;
    
        for (int i =0; i< 15000; i++){
            t= System.currentTimeMillis();
            quickSolve(fileName);
            t1 += System.currentTimeMillis()-t;
    
    
            t= System.currentTimeMillis();
            quickSolveCyclic(fileName);
            t2+= System.currentTimeMillis()-t;
            t= System.currentTimeMillis();
            solveArr(fileName);
            t3+= System.currentTimeMillis()-t;
            t= System.currentTimeMillis();
            solveList(fileName);
            t4+= System.currentTimeMillis()-t;
        }
    
    
        System.out.println("Benchmarking average time taken for 15000 solves:\n\tQuick took   "+(t1/15000.0d)+" ms\n\tCyclic took  "+(t2/15000.0d)+" ms\n\tArray took   "+(t3/15000.0d)+" ms\n\tList took    "+(t4/15000.0d)+ " ms");
    }
    public static void main(String[] args) throws IOException{
        String fileName = "Day1/Day1Input.txt";
        int[] ans = quickSolve(fileName);
        System.out.println("Answers: Part 1 = " + ans[0] + ", Part 2 = " + ans[1]);
        benchMark(fileName);
        

    }
}
