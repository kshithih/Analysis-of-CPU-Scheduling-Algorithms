import javax.swing.*;

class cpuschedule {
    int n; //number_of_process
    int Burst_time[] = new int[26]; // array to store burst time of process
    int Arrival_time[] = new int[26]; //array to store arrival time of process
    int Finish_time[] = new int[26]; //array to store finish time of process
    int fcfs[][] = new int[26][26];
    int Process[] = new int[26]; // array to store process number
    int turnaround[] = new int[26]; // array to store turnaround time
    int waiting[] = new int[26]; // array to store waiting time
    int sum = 0;
    double avg_tt; //average turnaround time
    double avg_wt; //average waiting time
    int temp2, temp, temp3; // temporary variables used to swap burst time, priorities of process
    int time_quantum; //used in round robin scheduling algorithm
    int j, i; //variable used for iterating through loops
    int total;
    //time complexity of First come first serve is O(n)
    void Fcfs() {
        n = Integer.parseInt(JOptionPane.showInputDialog("Enter Number of Processes:"));
        for (int i = 1; i <= n; i++) {
            Burst_time[i] = Integer.parseInt(JOptionPane.showInputDialog("Enter The BurstTime for Process p" + i + ""));
        }
        Finish_time[1] = Burst_time[1];
        for (int i = 2; i <= n; i++) {
            Finish_time[i] = Finish_time[i - 1] + Burst_time[i];
        }
        for (int i = 1; i <= n; i++) {
            turnaround[i] = (int)(Finish_time[i] - Arrival_time[i]);
        }
        for (int i = 1; i <= n; i++) {
            waiting[i] = turnaround[i] - Burst_time[i];
        }
        for (int i = 1; i <= n; i++) {
            sum = sum + (turnaround[i]);
        }

        System.out.println("PROCESS" + "\t\t" + "BURST TIME" + "\t   " + "FINISH TIME" + "\t\t" + "TURNAROUND TIME" + "\t\t\t" + "WAITING TIME");
        for (int i = 1; i <= n; i++) {
            System.out.println("P" + "[" + i + "]" + "\t\t\t" + Burst_time[i] + "\t\t " + Finish_time[i] + "\t\t" + turnaround[i] + "\t\t\t\t\t" + waiting[i]);
        }
        avg_tt = sum / n;
        sum = 0;
        for (int i = 1; i <= n; i++) {
            sum = sum + (waiting[i]);
        }
        avg_wt = sum / n;
        JOptionPane.showMessageDialog(null, "Total turnaround  time is:" + avg_tt + "\n " + "Average waiting time is:" + avg_wt + "ms" + "\n" + "Time complexity of this algorithm is O(n) ");

    }

    //time complexity of Shortest Job First is O(n^2)
    void sjsf() {
        n = Integer.parseInt(JOptionPane.showInputDialog("Enter Number of Processes:"));
        for (int i = 1; i <= n; i++) {
            Process[i] = Integer.parseInt(JOptionPane.showInputDialog("Enter The  Process ID " + i + ""));
            Burst_time[i] = Integer.parseInt(JOptionPane.showInputDialog("Enter The BurstTime for Process p " + i + ""));


        }
        System.out.println("INPUT");
        System.out.println("PROCESS NUMBER\t\t" + "BURST TIME");
        for (int i = 1; i <= n; i++) {

            System.out.println(i + "\t\t\t\t" + Burst_time[i]);

        }
        System.out.println("-------------------------------------------------------");


        for (int i = 1; i <= n; i++) { //Bubble sort
            for (int j = 1; j < n; j++) {
                if (Burst_time[j + 1] < Burst_time[j]) {
                    temp2 = Burst_time[j + 1];
                    Burst_time[j + 1] = Burst_time[j];
                    Burst_time[j] = temp2;
                    temp = Process[j + 1];
                    Process[j + 1] = Process[j];
                    Process[j] = temp;
                }

            }
        }
        Finish_time[1] = Burst_time[1];
        turnaround[1] = 0;
        waiting[1] = 0;
        for (int i = 2; i <= n; i++) {
            Finish_time[i] = Finish_time[i - 1] + Burst_time[i];
        }
        for (int i = 2; i <= n; i++) {
            turnaround[i] = Finish_time[i];
        }

        for (int i = 2; i <= n; i++) {
            waiting[i] = turnaround[i] - Burst_time[i];
        }
        for (int i = 1; i <= n; i++) {
            sum = sum + (turnaround[i]);
        }
        System.out.println("PROCESS NUMBER" + "\t" + "BURST TIME" + "\t" + "FINISH TIME" + "\t" + "TURNAROUND TIME" + "\t\t" + "WAITING TIME");
        for (int i = 1; i <= n; i++) {
            System.out.println(Process[i] + "\t\t" + Burst_time[i] + "\t\t " + Finish_time[i] + "\t\t" + turnaround[i] + "\t\t\t" + waiting[i]);
        }
        avg_tt = sum / n;
        sum = 0;
        for (int i = 1; i <= n; i++) {
            sum = sum + (waiting[i]);
        }
        avg_wt = sum / n;
        JOptionPane.showMessageDialog(null, "Total turnaround  time is:" + avg_tt + "\n" + "Average waiting time is:" + avg_wt + "ms" + "\n" + "Time complexity of this algorithm is O(n^2) ");
    }

    //time complexity of Round Robin is O(n)
    public void robin() {
        n = Integer.parseInt(JOptionPane.showInputDialog("Enter number of process"));
        int quantum = Integer.parseInt(JOptionPane.showInputDialog("Enter time quantum value"));
        for (int i = 0; i < n; i++) {
            Burst_time[i] = Integer.parseInt(JOptionPane.showInputDialog("Enter The BurstTime for Process p" + i + ""));
        }
        int rem_bt[] = new int[n];
        for (int i = 0; i < n; i++) {
            rem_bt[i] = Burst_time[i];
        }
        int t = 0;
        while (true) {
            boolean done = true;
            for (int i = 0; i < n; i++) {
                if (rem_bt[i] > 0) {
                    done = false;
                    if (rem_bt[i] > quantum) {
                        t += quantum;
                        rem_bt[i] -= quantum;
                    } else {
                        t = t + rem_bt[i];
                        waiting[i] = t - Burst_time[i];
                        rem_bt[i] = 0;
                    }
                }
            }
            if (done == true)
                break;
        }
        for (int i = 0; i < n; i++) {
            turnaround[i] = Burst_time[i] + waiting[i];
        }
        System.out.println("Processes " + " Burst time " + " Waiting time " + " Turn around time");

        int total_tat = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + waiting[i];
            total_tat = total_tat + turnaround[i];
            System.out.println(" " + (i + 1) + "\t\t" + Burst_time[i] + "\t " + waiting[i] + "\t\t " + turnaround[i]);
        }

        JOptionPane.showMessageDialog(null, "Total turnaround  time is:" + sum / n + "\n " + "Average waiting time is:" + total_tat / n + "ms" + "\n" + "Time complexity of this algorithm is O(n) ");
    }

    //time complexity of Priority Scheduling is O(n^2)
    public void priority_scheduling() {
        n = Integer.parseInt(JOptionPane.showInputDialog("Enter number of process\n "));
        int[] priority = new int[26];
        for (int i = 1; i <= n; i++) {
            Process[i] = Integer.parseInt(JOptionPane.showInputDialog("Enter Process ID  " + (i)));
            Burst_time[i] = Integer.parseInt(JOptionPane.showInputDialog("Enter Burst time of " + (i)));
            priority[i] = Integer.parseInt(JOptionPane.showInputDialog("Enter priority number " + (i)));
        }
        System.out.println("INPUT");
        System.out.println("Proocess Number" + "\t\t" + "Priority number\t\t\t\t" + "Burst time");
        for (int i = 1; i <= n; i++) {

            System.out.println(i + "\t\t\t\t" + priority[i] + "\t\t\t\t" + Burst_time[i]);

        }
        System.out.println("---------------------------------------------------------------------");
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n - i; j++) {
                if (priority[j + 1] < priority[j]) {
                    temp2 = Burst_time[j + 1];
                    Burst_time[j + 1] = Burst_time[j];
                    Burst_time[j] = temp2;
                    temp3 = priority[j + 1];
                    priority[j + 1] = priority[j];
                    priority[j] = temp3;
                    temp = Process[j + 1];
                    Process[j + 1] = Process[j];
                    Process[j] = temp;
                } else if (priority[j + 1] == priority[j]) {
                    if (Burst_time[j + 1] < Burst_time[j]) {
                        temp2 = Burst_time[j + 1];
                        Burst_time[j + 1] = Burst_time[j];
                        Burst_time[j] = temp2;
                        temp3 = priority[j + 1];
                        priority[j + 1] = priority[j];
                        priority[j] = temp3;
                        temp = Process[j + 1];
                        Process[j + 1] = Process[j];
                        Process[j] = temp;
                    }
                }
            }
        }
        waiting[1] = 0;
        for (i = 2; i <= n; i++) {
            waiting[i] = 0;
            for (j = 1; j < i; j++) {
                waiting[i] += Burst_time[j];
            }
            total += waiting[i];
        }
        avg_wt = total / n;
        int sum = 0;
        System.out.println("Process ID\t" + "Priority\t" + "Burst Time\t\t" + "Turnaround time\t\t" + "Waiting time");

        for (i = 1; i <= n; i++) {
            turnaround[i] = Burst_time[i] + waiting[i];
            sum += turnaround[i];
            System.out.println(Process[i] + "\t\t" + priority[i] + "\t\t" + Burst_time[i] + "\t\t\t\t" + turnaround[i] + "\t\t\t" + waiting[i]);
        }
        JOptionPane.showMessageDialog(null, "Average turnaround time is:" + sum / n + "\nAverage waiting time is:" + avg_wt + "\tms" + "\n" + "Time complexity of this algorithm is O(n^2) ");
    }
}

public class schedule {
    public static void main(String[] args) {
        cpuschedule op = new cpuschedule();
        int select;
        select = Integer.parseInt(JOptionPane.showInputDialog("Select An Option:\n1.Shortest Job First (Non Premptive scheduling) \n2.Priority Scheduling(Non Premptive Scheduling) \n3.FCFS(Non-Preemptive scheduling)\n4.Round Robin\n"));
        while (true) {
            switch (select) {
                case 1:
                    System.out.println("Shortest Job First (SJF)");
                    System.out.println("-------------------");
                    op.sjsf();
                    break;
                case 2:
                    System.out.println("Priority Scheduling");
                    System.out.println("-------------------");
                    op.priority_scheduling();
                    break;
                case 3:
                    System.out.println("FCFS ");
                    System.out.println("-------------------");
                    op.Fcfs();
                    break;
                case 4:
                    System.out.println("Round Robin ");
                    System.out.println("-------------------");
                    op.robin();
                    break;
                case 5:
                    break;
            }
        }
    }
}