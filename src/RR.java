// Programmer:  Roberto Rodriguez
// Class:       CS 431 - Operating Systems
// Date Due:    5/21/2015
// Project:     Quarter Project
//
// File: RR.java

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class RR {

    /**
     * Implementation of the Round Robin scheduling algorithm
     * @param filename Name of file containing data
     * @param quantum The time quantum to be used for preempting jobs
     * @throws Exception Thrown if file/io error
     */
    public static void runAlgorithm(String filename, int quantum) throws Exception {
        System.out.println("*** Using file: " + filename + " ***");

        final int QUANTUM = quantum;    // Should be 3 for RR-3, 4 for RR-4

        List<Job> ganttData = new ArrayList<>();
        Queue<Job> readyQueue = getJobQueue(filename);
        int totalJobs = readyQueue.size();
        double avgTurnAround = 0;
        int totalRunTime = 0;

        while (!readyQueue.isEmpty()) {
            Job job = readyQueue.remove();
            String jobName = job.getName();
            int remainingJobTime = job.getBurstTime();

            System.out.println(jobName + " executing (Starting burst time: " + remainingJobTime + " ms)");

            int jobTime = 0;
            int rrCounter = 0;

            while (jobTime < remainingJobTime && rrCounter < QUANTUM) {
                jobTime++;
                rrCounter++;
                totalRunTime++;
            }

            // The job completed before or right at the end of the time quantum
            if (jobTime == remainingJobTime) {
                System.out.println(jobName + " finished running (Ending burst time: " + (remainingJobTime - jobTime) + " ms)");
                avgTurnAround += totalRunTime;
            } else {
                System.out.println(jobName + " is being preempted and placed back in the queue (Ending burst time: " + (remainingJobTime - jobTime) + " ms)");
                job.setBurstTime(remainingJobTime - jobTime);
                readyQueue.add(job); // Place unfinished job back into ready queue
            }
            System.out.println("Total run time: " + totalRunTime + " ms\n");
            ganttData.add(new Job(jobName, jobTime));
        }

        avgTurnAround /= totalJobs;
        System.out.println("Average completion time for RR" + QUANTUM + ":");
        System.out.printf ("%.2f ms\n", avgTurnAround);

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
