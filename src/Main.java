// Programmer:  Roberto Rodriguez
// Class:       CS 431 - Operating Systems
// Date Due:    5/21/2015
// Project:     Quarter Project
//
// File: Main.java

public class Main {

    /**
     * Entry point for the program. User must provide at least 1 command line argument containing program data.
     * @param args Name(s) of the text file(s) containing the data
     * @throws Exception Thrown if error if file IO
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("ERROR: At least 1 command-line string required.");
            System.exit(1);
        }

        System.out.println("===============================================");
        System.out.println("First Come First Serve (FCFS) test");
        System.out.println("===============================================");
        for (String s : args) {
            FCFS.runAlgorithm(s);
        }

        System.out.println("\n===============================================");
        System.out.println("Shortest Job First (SJF) test");
        System.out.println("===============================================");
        for (String s : args) {
            SJF.runAlgorithm(s);
        }

        System.out.println("\n===============================================");
        System.out.println("Round Robin (RR3) test");
        System.out.println("===============================================");
        for (String s : args) {
            RR.runAlgorithm(s, 3);
        }

        System.out.println("\n===============================================");
        System.out.println("Round Robin (RR4) test");
        System.out.println("===============================================");
        for (String s : args) {
            RR.runAlgorithm(s, 4);
        }
    }
}
