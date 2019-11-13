/*
 * File:	Process.java
 * Course: 	Operating Systems
 * Code: 	1DV512
 * Author: 	Suejb Memeti
 * Modified : Ahmad Anbarje
 * Date Modified: 	November, 2019
 */

import java.util.ArrayList;
import java.util.Comparator;

public class FCFS {

    // The list of processes to be scheduled
    public ArrayList<Process> processes;

    // Class constructor
    public FCFS(ArrayList<Process> processes) {
        this.processes = processes;
    }
    // TAT = CT – AT
    // WT = TAT – BT
    public void run() {
        // TODO Implement the FCFS algorithm here
        sortArrivalTime();
        int WaitingTime = 0;
        int CT = processes.get(0).getBurstTime();
        processes.get(0).setCompletedTime(CT);
        processes.get(0).setTurnaroundTime(processes.get(0).getCompletedTime() - processes.get(0).getArrivalTime());
        processes.get(0).setWaitingTime(processes.get(0).getTurnaroundTime()-processes.get(0).getBurstTime());
        for (int i = 1; i < processes.size(); i++) {
            if(processes.get(i).getArrivalTime() > CT){
                WaitingTime = processes.get(i).getArrivalTime() - CT;
            }
            processes.get(i).setCompletedTime(processes.get(i).getBurstTime() + CT + WaitingTime);
            processes.get(i).setTurnaroundTime(processes.get(i).getCompletedTime() - processes.get(i).getArrivalTime());
            processes.get(i).setWaitingTime(processes.get(i).getTurnaroundTime()-processes.get(i).getBurstTime());
            WaitingTime =0;
            CT = processes.get(i).getCompletedTime();
        }
        printTable();
        printGanttChart();
    }

    private void sortArrivalTime() {
        processes.sort(new Comparator<Process>() {
            @Override
            public int compare(Process process1, Process process2) {
                return process1.getArrivalTime() - process2.getArrivalTime();
            }
        });
    }

    public void printTable() {
        // TODO Print the list of processes in form of a table here
        System.out.format("%-10s%-10s%-10s%-10s%-10s%-10s\n"
                , "PID", "AT", "BT", "CT", "TAT","WT");
        for(Process process: processes){
            System.out.format("%-10s%-10s%-10s%-10s%-10s%-10s\n"
            ,process.getProcessId(),process.getArrivalTime(),process.getBurstTime()
                    ,process.getCompletedTime(),process.turnaroundTime,process.waitingTime);
        }
    }

    public void printGanttChart() {
        StringBuffer buff = new StringBuffer();
        System.out.println("\n\n%%%%%%%%% GANTT CHART %%%%%%%%%");
        for (int i = 0; i < 150; i++) {
            buff.append("=");
        }
        buff.append("\n");
        for (Process process : processes) {
            buff.append("|");
            buff.append("P").append(process.getProcessId());
            for (int i = 0; i < process.getCompletedTime(); i++) {
                buff.append(" ");
            }
            buff.append("|");
            if (process.getWaitingTime() != 0) {
                buff.append("|");
                for (int i = 0; i < process.getWaitingTime(); i++) {
                    buff.append("~");
                }
                buff.append("|");
            }
        }
        buff.append("\n");
        for (int i = 0; i < 150; i++) {
            buff.append("=");
        }
        buff.append("\n");
        buff.append("0");
        for (Process process : processes) {
            for (int i = 0; i < process.getCompletedTime()+3; i++) {
                buff.append(" ");
            }
            buff.append(process.getCompletedTime());
            if(process.getWaitingTime() != 0){
                for (int i = 0; i < process.getWaitingTime(); i++) {
                    buff.append(" ");
                }
                buff.append(process.getWaitingTime());
            }
        }
        System.out.println(buff);
    }
}
