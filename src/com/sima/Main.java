package com.sima;

/**
 * This class represents the main method. It receives the n and k values and runs the algorithm which
 * prints the k lowest numbers for each of the n and k combinations.
 *
 * @author Sima Margulis Davidi
 * @version 30-07-2017
 */

public class Main {

    public static void main(String[] args) {
        int[] nValues = {200, 400, 800};
        int[] kValues = {10, 50, 100};

        for (int i = 0; i < nValues.length; i++){
            for (int j = 0; j < kValues.length; j++){
                InputData input = new InputData(nValues[i], kValues[j]);
                KLowest algorithm = new KLowest();
                algorithm.run(input);
                System.out.println("_______________________________________________________________________________________________________________________________________________________________________________________");
            }
        }
    }

}