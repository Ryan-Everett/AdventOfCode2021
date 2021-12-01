package Day1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/*
Can solve the problem in three ways:
All at once using a cyclic array,
All at once using variables
Split into sections using an array
Split into sections using a linked list

Benchmarking average time taken for a solve (5000 trials ran):
        QS1  took 0.981 ms
        QS2  took 0.9758 ms
        Arr  took 1.0632 ms
        List took 1.1134 ms

So it is quicker to not use a cyclic array
*/
public class Day1 {

    /*
    For the second part: 
            Let A = x[i-1] + x[i] + x[i+1], and B = x[i] + x[i+1] + x[i+2]. 
                (A < B) iff (x[i-1] + x[i] + x[i+1]) < (x[i] + x[i+1] + x[i+2]) 
                        iff x[i-1] < x[i+1]
    
    */
    //Uses cyclic array to represent window
    static int[] quickSolve(String fileName) throws IOException{
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

    static int[] quickSolve2(String fileName) throws IOException{
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

    // static int numIncs2(LinkedList<Integer> list) {
    //     Iterator<Integer> iter = list.iterator();
    //     int back = iter.next();
    //     int middle = iter.next();
    //     int front = iter.next();
    //     int count = 0;
    //     while (iter.hasNext()){
    //         int next = iter.next();
    //         if(back < next){   //If A < B
    //             count++;
    //         }
    //         back = middle;
    //         middle = front;
    //         front = next;
            
    //     }
    //     return count;
    // }
    //Doing stuff separately 
    static int[] readArrFromFile(String fileName) throws IOException{
        Path path = Paths.get(fileName);
        int length = (int)Files.lines(path).count();
        int[] xs = new int[length];

        Scanner sc = new Scanner(new File(fileName));
        int i = 0;
        while (sc.hasNextInt()) {
            xs[i] = sc.nextInt();
            i += 1;
        }
        return xs;
    }

    static LinkedList<Integer> readListFromFile(String fileName) throws IOException{
        LinkedList<Integer> numList = new LinkedList<Integer>();

        Scanner sc = new Scanner(new File(fileName));
        while (sc.hasNextInt()) {
            numList.add(sc.nextInt());
        }
        return numList;
    }
    static int numIncs1(int[] xs) {
        int count = 0;
        for (int i = 1; i < xs.length; i++){ 
            if (xs[i-1] < xs[i]){
                count ++;
            }
        }
        return count;
    }

    static int numIncs1(LinkedList<Integer> list) {
        Iterator<Integer> iter = list.iterator();
        int prev = iter.next();
        int count = 0;
        while (iter.hasNext()){
            int curr = iter.next();
            if(prev < curr){
                count++;
            }
            prev = curr;
        }
        return count;
    }


    static int numIncs2(int[] xs){
        int count = 0;
        for (int i = 3; i < xs.length; i++){
            if(xs[i-3] <xs[i]){
                count++;
            }
        }
        return count;
    }

    static int numIncs2(LinkedList<Integer> list) {
        Iterator<Integer> iter = list.iterator();
        int back = iter.next();
        int middle = iter.next();
        int front = iter.next();
        int count = 0;
        while (iter.hasNext()){
            int next = iter.next();
            if(back < next){   //If A < B
                count++;
            }
            back = middle;
            middle = front;
            front = next;
            
        }
        return count;
    }
    static void benchMark(String fileName) throws IOException{

        long t, t1=0, t2 =0, t3=0,t4=0;
    
        for (int i =0; i< 5000; i++){
            t= System.currentTimeMillis();
            quickSolve(fileName);
            t1 += System.currentTimeMillis()-t;
    
    
            t= System.currentTimeMillis();
            quickSolve2(fileName);
            t2+= System.currentTimeMillis()-t;
            t= System.currentTimeMillis();
            int[]xs = readArrFromFile(fileName);
            numIncs1(xs);
            numIncs2(xs);
            t3+= System.currentTimeMillis()-t;
            t= System.currentTimeMillis();
            LinkedList<Integer> numList = readListFromFile(fileName);
            numIncs1(numList);
            numIncs2(numList);
            t4+= System.currentTimeMillis()-t;
        }
    
    
        System.out.println("Benchmarking average time taken for 5000 solves:\n\tQS1 took  "+(t1/5000.0d)+" ms\n\tQS2 took  "+(t2/5000.0d)+" ms\n\tArr took  "+(t3/5000.0d)+" ms\n\tList took "+(t4/5000.0d)+ " ms");
    }
    public static void main(String[] args) throws IOException{
        String fileName = "Day1Input.txt";
        int[] ans = quickSolve2(fileName);
        System.out.println("Answers: Part 1 = " + ans[0] + ", Part 2 = " + ans[1]);
        benchMark(fileName);
        

    }
}