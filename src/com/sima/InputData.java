package com.sima;

import java.util.Random;

/**
 * This class represents the input data required for the KLowest algorithm - n, k, array with random numbers
 * (integers between 0-1023) and 4 checkpoints.
 *
 * @author Sima Margulis Davidi
 * @version 30-07-2017
 */

public class InputData {

    // Instance variables - 4 checkpoints, array, n and k
    private int _checkpointN1, _checkpointN2, _checkpointN3, _checkpointN4, _k, _n;
    private int[] _array;

    /** Constructor - sets n and k to the input values, sets checkpoints and creates an array filled with
     *  random numbers, by using a method called createRandomArray.
     *
     * @param n the number of elements in the array
     * @param k the number of elements in my chosen data structure
     */
    public InputData(int n, int k) {
        _n = n;
        _k = k;
        _checkpointN1 = n / 4;
        _checkpointN2 = n / 2;
        _checkpointN3 = 3 * n / 4;
        _checkpointN4 = n;
        _array = createRandomArray(n);
    }

    // Getters for all instance variables
    public int getN() {
        return _n;
    }

    public int getK() {
        return _k;
    }

    public int[] getArray() {
        return _array;
    }

    public int getCheckpointN1() {
        return _checkpointN1;
    }

    public int getCheckpointN2() {
        return _checkpointN2;
    }

    public int getCheckpointN3() {
        return _checkpointN3;
    }

    public int getCheckpointN4() {
        return _checkpointN4;
    }

    /**
     * Creates an array filled with random numbers between 0 and 1023, as requested in the assignment.
     * Prints the array during the creation process (prints 25 elements per line).
     * For this method integer numbers where chosen, which increases the probability of multiple elements in the array
     * with the same key. Therefore, it will work in a similar way for floating point numbers (after a small modification).
     * Time complexity: Θ(n), where n is the total number of elements in the array (passes once over the array elements).
     *
     * @param size the number of elements of the array
     * @return the array filled with random numbers
     */
    private int[] createRandomArray(int size){
        Random random = new Random();
        int[] arr = new int[size];
        System.out.println("Input array (n=" + size + "):");
        for (int i = 0; i < size; i++){
            arr[i] = random.nextInt(1024); // set a random number in index i of the array
            // print the element in index i
            if (i == size - 1){
                System.out.print(arr[i]);
            } else {
                System.out.print(arr[i] + ",");
                if ((i+1) % 25 == 0){
                    System.out.println("");
                }
            }
        }
        System.out.println("");
        return arr;
    }
}