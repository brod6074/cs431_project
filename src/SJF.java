// Programmer:  Roberto Rodriguez
// Class:       CS 431 - Operating Systems
// Date Due:    5/21/2015
// Project:     Quarter Project
//
// File: SJF.java

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

// Shortest Job First class
public class SJF {
    public static final int MAX_JOBS = 20;

    /**
     * Implementation of Shortest Job First scheduling algorithm
     * @param filename Name of file containing data
     * @throws Exception Thrown if file/io error
     */
    public static void runAlgorithm(String filename) throws Exception {
        System.out.println("*** Using file: " + filename + " ***");

        Queue<Job> readyQueue = getJobQueue(filename);  // Queue of jobs
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

            ganttData.add(new Job(jobName, totalRunTime));
            System.out.println(jobName + " finished running (Ending burst time: " + (remainingJobTime - jobTime) + " ms)");
            System.out.println("Total run time: " + totalRunTime + " ms\n");
            avgTurnAround += totalRunTime;
        }

        avgTurnAround /= totalJobs;
        System.out.println("Average completion time for SJF: ");
        System.out.printf ("%.2f ms\n", avgTurnAround);

        displayGanttChart(ganttData);
        System.out.println();
        System.out.println();
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

    /**
     * Creates and initializes the ready queue for SJF
     * @param filename Name of the file containing the data
     * @return The ready queue loaded with jobs
     * @throws Exception Thrown if file not found/error reading file
     */
    private static Queue<Job> getJobQueue(String filename) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
        Queue<Job> readyQueue = new PriorityQueue<>(MAX_JOBS, new Job.JobComparator());

        String name, burstTime;
        while (((name = br.readLine()) != null) &&
                ((burstTime = br.readLine()) != null)) {
            readyQueue.add(new Job(name, Integer.parseInt(burstTime)));
        }
        br.close();
        return readyQueue;
    }
}
