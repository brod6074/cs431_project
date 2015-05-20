// Programmer:  Roberto Rodriguez
// Class:       CS 431 - Operating Systems
// Date Due:    5/21/2015
// Project:     Quarter Project
//
// File: Job.java

import java.util.Comparator;

/**
 * Class representing jobs.
 */
public class Job {
    private String name;
    private int burstTime;

    public Job(String name, int burstTime) {
        this.name = name;
        this.burstTime = burstTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public String getName() {
        return name;
    }

    /**
     *  Implements Comparator interface to allow Job objects to be sorted and used in a priority queue.
     */
    public static class JobComparator implements Comparator<Job> {
        public int compare(Job job1, Job job2) {
            return job1.getBurstTime() - job2.getBurstTime();
        }
    }
}


