//@author Colin Rayburn
// ID: 014603522

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.math.BigInteger;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class Main {
    // GCD is a point assignment program based off of a divisor and two numbers
    // arguments are the arraylist of divisors, the left number, the right number, arraylist of points accrued by each divisor and the current position that is an int
    public static void GCD(ArrayList<BigInteger> divisors, BigInteger leftNum, BigInteger rightNum, ArrayList<Integer> counters, int position){

        // if divisor can divide both do it and add points to counter and call function again with same divisor
        if(leftNum.remainder(divisors.get(position)) == BigInteger.ZERO && rightNum.remainder(divisors.get(position)) == BigInteger.ZERO){
            counters.set(position, counters.get(position) + 2);
            GCD(divisors, leftNum.divide(divisors.get(position)), rightNum.divide(divisors.get(position)), counters, position);

        }
        //else if divisor can only divide one or the other divide the one that works add points to counter call function on same number
        else if(leftNum.remainder(divisors.get(position)) == BigInteger.ZERO){
            counters.set(position, counters.get(position) + 1);
            GCD(divisors, leftNum.divide(divisors.get(position)), rightNum, counters, position);

        }
        //else if divisor can only divide one or the other divide the one that works add points to counter call function on same number
        else if(rightNum.remainder(divisors.get(position)) == BigInteger.ZERO){
            counters.set(position, counters.get(position) + 1) ;
            GCD(divisors, leftNum, rightNum.divide(divisors.get(position)), counters, position);

        }
        // if the divisor cant divide either number move to next divisor in the list
        else{
            if(position != 0){
                GCD(divisors, leftNum, rightNum , counters, position -1);
            }
        }
    }

    //insert sort takes an array adds all elements of that array into a new array and sorts them as it adds them
    public static ArrayList<BigInteger> insertSort(ArrayList<BigInteger> original){
        ArrayList<BigInteger> sortedList = new ArrayList<>();
        //itterate through the given array
        for(int i = 0; i < original.size(); i++){
            //if it is the first element add it to the list
          if(i == 0){
              sortedList.add(original.get(i));
              // if it isnt the first element find position and insert it
          }else{
              int j = 0;
              while(sortedList.size() < i + 1){
                  if(sortedList.get(j).compareTo(original.get(i)) == 1){
                      sortedList.add(j,original.get(i));
                  }else if (j == sortedList.size() - 1 && sortedList.get(j).compareTo(original.get(i)) == -1) {
                      sortedList.add(original.get(i));
                  }else {
                      j++;
                  }
              }
          }
        }
    // return sorted arraylist
        return sortedList;
    }

    //this is a function to get the divisor counters or points into the original unsorted order
    public static ArrayList<Integer> origOrderCounters(ArrayList<BigInteger> orig, ArrayList<BigInteger> sorted, ArrayList<Integer> counters){
        ArrayList<Integer> retCounters = new ArrayList<>();
        // for each element in the original unsorted array of divisors locate that element in the sorted array and add the corresponding counter to the appropriate place in the new counter list
        for(int i = 0; i < orig.size(); i++){
            retCounters.add(counters.get(sorted.indexOf(orig.get(i))));
        }
        // return the new counter list
        return retCounters;
    }

    public static void main(String[] args) throws IOException {
        // create a file object to take input
        File input = new File("input.txt");
        Scanner in = new Scanner(input);
        // create a counter so that we can itterate through the list and add to it in the while loop
        int divCounter = 0;
        int numDiv = in.nextInt();
        ArrayList<BigInteger> originalDiv = new ArrayList<>();
        // get all the divisors unsorted into a list
        while(divCounter < numDiv){
            BigInteger div = new BigInteger(in.next());
            originalDiv.add(div);
            divCounter++;
        }
        // sort the divisors into a new list
        ArrayList<BigInteger> sortDiv = insertSort(originalDiv);
        ArrayList<Integer> sortCounters = new ArrayList<>();
        //create an "empty" list for the counter/ points to be kept track of
        for(int i = 0; i < sortDiv.size(); i ++){
            sortCounters.add(0);
        }
        // itterate through the rest of the file and run GCD on every pair
        while(in.hasNext()){

            BigInteger left = new BigInteger(in.next());
            BigInteger right = new BigInteger(in.next());

            GCD(sortDiv, left, right, sortCounters, sortDiv.size() - 1);
        }
        // return to original order of the divisors and adjust the counters/ points to the appropriate positions using the origordercounters method
        ArrayList<Integer> origOrderCount = origOrderCounters(originalDiv, sortDiv, sortCounters);
        FileWriter outWriter = new FileWriter("output.txt");
        // write to a file in the appropriate format
        for(int j = 0; j < originalDiv.size(); j++){
            String answer = originalDiv.get(j) + " " + origOrderCount.get(j);
            outWriter.write(answer + "\n");
        }
        outWriter.close();
    }
}