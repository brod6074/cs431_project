// Programmer:  Roberto Rodriguez
// Class:       CS 431 - Operating Systems
// Date Due:    5/21/2015
// Project:     Quarter Project
//
// File: FCFS.java

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class FCFS {

    /**
     * Implements the First Come First Serve scheduling algorithm
     * @param filename Name of file containing data
     * @throws Exception Thrown if file/io error
     */
    public static void runAlgorithm(String filename) throws Exception {
        System.out.println("*** Using file: " + filename + " ***");

        Queue<Job> readyQueue = getJobQueue(filename);
        List<Job> ganttData = new ArrayList<>();        // Data of each job switch for display

        int totalJobs = readyQueue.size();
        double avgTurnAround = 0;
        int totalRunTime = 0;

        while (!readyQueue.isEmpty()) {
            Job job = readyQueue.remove();
            String jobName = job.getName();
            int remainingJobTime = job.getBurstTime();

            System.out.println(jobName + " executing (Starting burst time: " + remainingJobTime + " ms)");

            int jobTime = 0;
            while (jobTime < remainingJobTime) {
                jobTime++;
                totalRunTime++;
            }

            System.out.println(jobName + " finished running (Ending burst time: " + (remainingJobTime - jobTime) + " ms)");
            System.out.println("Total run time: " + totalRunTime + " ms\n");
            avgTurnAround += totalRunTime;
            ganttData.add(new Job(jobName, totalRunTime));
        }

        avgTurnAround /= totalJobs;
        System.out.println("Average completion time for FCFS: ");
        System.out.printf("%.2f ms\n", avgTurnAround);

        displayGanttChart(ganttData);
        System.out.println();
        System.out.println();
    }

    /**
     *
     * @param filename Name of file containing data
     * @return Queue of Jobs to be executed
     * @throws Exception Thrown if file/io error
     */
    private static Queue<Job> getJobQueue(String filename) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
        Queue<Job> readyQueue = new LinkedList<>();

        String name, burstTime;
        while (((name = br.readLine()) != null) &&
                ((burstTime = br.readLine()) != null)) {
            readyQueue.add(new Job(name, Integer.parseInt(burstTime)));
        }
        br.close();
        return readyQueue;
    }

    /**
     * Displays the data in a Gantt chart
     * @param ganttData List of Job data for the gantt chart
     */
    private static void displayGanttChart(List<Job> ganttData) {
        for (int i = 0; i < ganttData.size(); i++) {
            System.out.print("---------");
        }

        System.out.println();
        for (Job j : ganttData) {
            System.out.print("| " + j.getName() + " ");
        }

        System.out.print("|\n");
        for (int i = 0; i < ganttData.size(); i++) {
            System.out.print("---------");
        }

        System.out.print("\n" + "0");
        for (Job j : ganttData) {
            System.out.print("       " + j.getBurstTime());
        }
    }
}
