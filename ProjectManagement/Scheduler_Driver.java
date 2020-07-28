package ProjectManagement;

import PriorityQueue.MaxHeap;
import RedBlack.RBTree;
import RedBlack.RedBlackNode;
import Trie.Trie;
import Trie.TrieNode;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Scheduler_Driver extends Thread implements SchedulerInterface {
    int totaljobs = 0;
    int done = 0;
    int timenow = 0;
    int unf = 0;
    int job_id = 0;
    MaxHeap<Job> jobs = new MaxHeap<Job>();
    Trie<Job> jobtrie = new Trie<Job>();
    Trie<Project> projects = new Trie<Project>();
    Trie<User> users = new Trie<User>();
    RBTree<String, Job> unfinishedjobs = new RBTree<String, Job>();
    ArrayList<Job> finishedjobs = new ArrayList<Job>();
    ArrayList<Job> alljobs = new ArrayList<Job>();
    ArrayList<User> allusers = new ArrayList<User>();
    ArrayList<Job> push = new ArrayList<Job>();
    ArrayList<Project> allprojects = new ArrayList<Project>();

    public static void main(String[] args) throws IOException {
        //

        Scheduler_Driver scheduler_driver = new Scheduler_Driver();
        File file;
        if (args.length == 0) {
            URL url = Scheduler_Driver.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }

    public void execute(File commandFile) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(commandFile));

            String st;
            while ((st = br.readLine()) != null) {
                String[] cmd = st.split(" ");
                if (cmd.length == 0) {
                    System.err.println("Error parsing: " + st);
                    return;
                }
                String project_name, user_name;
                Integer start_time, end_time;

                long qstart_time, qend_time;

                switch (cmd[0]) {
                    case "PROJECT":
                        handle_project(cmd);
                        break;
                    case "JOB":
                        handle_job(cmd);
                        break;
                    case "USER":
                        handle_user(cmd[1]);
                        break;
                    case "QUERY":
                        handle_query(cmd[1]);
                        break;
                    case "": // HANDLE EMPTY LINE
                        handle_empty_line();
                        break;
                    case "ADD":
                        handle_add(cmd);
                        break;
                    //--------- New Queries
                    case "NEW_PROJECT":
                    case "NEW_USER":
                    case "NEW_PROJECTUSER":
                    case "NEW_PRIORITY":
                        timed_report(cmd);
                        break;
                    case "NEW_TOP":
                        System.out.println("Top query");
                        qstart_time = System.nanoTime();
                        timed_top_consumer(Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    case "NEW_FLUSH":
                        qstart_time = System.nanoTime();
                        timed_flush(Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Flush query");
                        for (int i = 0; i < push.size(); i++) {
                            System.out.println("Flushed: " + push.get(i).toString());
                        }
                        // System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    default:
                        System.err.println("Unknown command: " + cmd[0]);
                }
            }

            run_to_completion();
            print_stats();

        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. " + commandFile.getAbsolutePath());
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }
    }

    @Override
    public ArrayList<JobReport_> timed_report(String[] cmd) {
        long qstart_time, qend_time;
        ArrayList<JobReport_> res = null;
        switch (cmd[0]) {
            case "NEW_PROJECT":
                // System.out.println("Project query");
                qstart_time = System.nanoTime();
                res = handle_new_project(cmd);
                qend_time = System.nanoTime();
                // System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_USER":
                // System.out.println("User query");
                qstart_time = System.nanoTime();
                res = handle_new_user(cmd);
                qend_time = System.nanoTime();
                // System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

                break;
            case "NEW_PROJECTUSER":
                // System.out.println("Project User query");
                qstart_time = System.nanoTime();
                res = handle_new_projectuser(cmd);
                qend_time = System.nanoTime();
                // System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_PRIORITY":
                // System.out.println("Priority query");
                qstart_time = System.nanoTime();
                res = handle_new_priority(cmd[1]);
                qend_time = System.nanoTime();
                // System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
        }

        return res;
    }

    public ArrayList<UserReport_> handle_new_top(String num) {
        return timed_top_consumer(Integer.parseInt(num));
    }

    @Override public ArrayList<UserReport_> timed_top_consumer(int top) { // no print here done
        ArrayList<UserReport_> submit = new ArrayList<UserReport_>();
        MaxHeap<User> userord = new MaxHeap<User>();
        for (User u : allusers) {
            userord.insert(u);
        }
        for (int i = 0; i < top; i++) {
            // submit.add((UserReport_) userord.extractMax());
            submit.add(userord.extractMax());
        }
        return submit;
    }

    @Override public void timed_flush(int waittime) { // no print here done
        int exectime = 0;
        push = new ArrayList<Job>();
        MaxHeap<Job> temp = new MaxHeap<Job>();
        while (jobs.size > 0) {
            Job j = jobs.extractMax();
            if (!j.iscompleted && timenow - j.arrival_time >= waittime
                && j.project.budget >= j.time) {
                j.project.budget -= j.time;
                j.iscompleted = true;
                exectime += j.time;
                j.end_time = timenow + exectime;
                j.user.consumedtime += j.time;
                j.user.lasttime = j.end_time;
                push.add(j);
                totaljobs--;
                finishedjobs.add(j);
            } else {
                temp.insert(j);
            }
        }
        timenow += exectime;
        jobs = temp;
    }

    public ArrayList<JobReport_> handle_new_priority(String s) { // no print here done
        ArrayList<JobReport_> submit = new ArrayList<JobReport_>();
        int a = Integer.parseInt(s);
        for (int i = 0; i < alljobs.size(); i++) {
            if (!alljobs.get(i).iscompleted && alljobs.get(i).getpriority() >= a) {
                submit.add(alljobs.get(i));
            }
        }
        return submit;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<JobReport_> handle_new_projectuser(String[] cmd) { // no print here done
        TrieNode<Project> p = (TrieNode<Project>) projects.search(cmd[1]);
        TrieNode<User> u = (TrieNode<User>) users.search(cmd[2]);
        if (p == null || u == null) {
            return new ArrayList<JobReport_>();
        } else {
            //    		ArrayList<JobReport_> a = p.value.findjobs(Integer.parseInt(cmd[3]),
            //    Integer.parseInt(cmd[4])); 		ArrayList<JobReport_> submit = new
            //    ArrayList<JobReport_>(); 		for(int i = 0; i<a.size(); i++) { 			if(a.get(i).user() ==
            //    u.value.name) { 				submit.add(a.get(i));
            //    			}
            //    		}
            ArrayList<JobReport_> submit = p.value.findjobsuser(
                Integer.parseInt(cmd[3]), Integer.parseInt(cmd[4]), u.getValue());
            Job.mergesort(submit, 0, submit.size() - 1);
            return submit;
        }
    }

    public ArrayList<JobReport_> handle_new_user(String[] cmd) { // no print here done
        @SuppressWarnings("unchecked") int cmd2 = Integer.parseInt(cmd[2]);
        int cmd3 = Integer.parseInt(cmd[3]);

        TrieNode<User> u = (TrieNode<User>) users.search(cmd[1]);
        if (u != null) {
            return u.value.findjobs(cmd2, cmd3);
        } else
            return new ArrayList<JobReport_>();
    }

    public ArrayList<JobReport_> handle_new_project(String[] cmd) { // no print here done
        @SuppressWarnings("unchecked") int cmd2 = Integer.parseInt(cmd[2]);
        int cmd3 = Integer.parseInt(cmd[3]);

        TrieNode<Project> u = (TrieNode<Project>) projects.search(cmd[1]);
        if (u != null) {
            return u.value.findjobs(cmd2, cmd3);
        } else
            return new ArrayList<JobReport_>();
    }

    public void schedule() {
        execute_a_job();
    }

    public void run_to_completion() {
        while (jobs.size > 0) {
            System.out.println("Running code");
            System.out.println("Remaining jobs: " + totaljobs);
            int a = 1;
            // MaxHeap<Job> tempjobs = new MaxHeap<Job>();
            while (a > 0 && jobs.size > 0) {
                Job j = jobs.extractMax();
                totaljobs--;
                System.out.println("Executing: " + j.name + " from: " + j.project.name);
                // System.out.println(j.time + " " + j.project.budget);
                if (j.time <= j.project.budget) {
                    // now the job is done
                    finishedjobs.add(j);
                    j.project.budget -= j.time;
                    j.iscompleted = true;
                    j.user.consumedtime += j.time;
                    System.out.println(
                        "Project: " + j.project.name + " budget remaining: " + j.project.budget);
                    System.out.println("System execution completed");
                    a--;
                    done++;
                    timenow += j.time;
                    j.end_time = timenow;
                    j.user.lasttime = timenow;
                } else {
                    System.out.println("Un-sufficient budget.");
                    unfinishedjobs.insert(j.project.name, j);
                }
            }
        }
    }

    @Override
    public void timed_run_to_completion() {
        while (jobs.size > 0) {
            int a = 1;
            while (a > 0 && jobs.size > 0) {
                Job j = jobs.extractMax();
                totaljobs--;
                if (j.time <= j.project.budget) {
                    finishedjobs.add(j);
                    j.project.budget -= j.time;
                    j.iscompleted = true;
                    j.user.consumedtime += j.time;
                    a--;
                    done++;
                    timenow += j.time;
                    j.user.lasttime = timenow;
                    j.end_time = timenow;
                } else {
                    unfinishedjobs.insert(j.project.name, j);
                }
            }
        }
    }

    public void print_stats() {
        // first show all finished jobs and then show all the unfinished jobs
        System.out.println("--------------STATS---------------");
        System.out.println("Total jobs done: " + finishedjobs.size());
        for (int i = 0; i < finishedjobs.size(); i++) {
            System.out.println(finishedjobs.get(i).toString());
        }
        System.out.println("------------------------\n"
            + "Unfinished jobs: ");
        // traverse the list of all jobs, and if they are not completed, just print them in the
        // order of their occurrence.
        int unfi = 0;
        //    	for(int i = 0; i<alljobs.size(); i++) {
        //    		if(!alljobs.get(i).iscompleted) {
        //    			System.out.println(alljobs.get(i).toString());
        //    			unfi++;
        //    		}
        //    	}
        // first traverse the list of projects in order of their priority.
        // so when we are making the projects, we need to store them in a heap

        Project.mergesort(allprojects, 0, allprojects.size() - 1);
        for (int i = 0; i < allprojects.size(); i++) {
            for (int j = 0; j < allprojects.get(i).projectsjobs.size(); j++) {
                if (!allprojects.get(i).projectsjobs.get(j).iscompleted) {
                    System.out.println(allprojects.get(i).projectsjobs.get(j).toString());
                    unfi++;
                }
            }
        }
        System.out.println("Total unfinished jobs: " + unfi);
        System.out.println("--------------STATS DONE---------------");
    }

    public void handle_add(String[] cmd) {
        Project p = (Project) projects.search(cmd[1]).value;
        if (p == null) {
            System.out.println("No such project exists. " + cmd[1]);
            return;
        } else {
            System.out.println("ADDING Budget");
            p.budget += Integer.parseInt(cmd[2]);
            // now check if it is in the redblacktree, and if it is there, we push all the values
            // into the heap.
            RedBlackNode<String, Job> n = unfinishedjobs.search(cmd[1]);
            if (n.values != null && n.values.size() != 0) {
                for (int i = 0; i < n.values.size(); i++) {
                    jobs.insert(n.values.get(i));
                    totaljobs++;
                }
                n.values = new ArrayList<Job>();
            }
        }
    }

    public void handle_empty_line() {
        schedule();
    }

    public void handle_query(String key) {
        System.out.println("Querying");
        if (jobtrie.search(key) == null) {
            System.out.println(key + ": NO SUCH JOB");
            return;
        }
        Object o = jobtrie.search(key).value;
        if (o == null) {
            System.out.println(key + ": NO SUCH JOB");
            return;
        }
        Job j = (Job) jobtrie.search(key).value;
        if (j != null) {
            if (j.iscompleted == false) {
                System.out.println(key + ": NOT FINISHED");
            } else {
                System.out.println(key + ": COMPLETED");
            }
        } else {
            System.out.println(key + ": NO SUCH JOB");
        }
    }

    public void handle_user(String name) {
        User n = new User();
        n.name = name;
        users.insert(name, n);
        allusers.add(n);
        System.out.println("Creating user");
        return;
    }

    @Override
    public void timed_handle_user(String name) {
        User n = new User();
        n.name = name;
        users.insert(name, n);
        allusers.add(n);
        return;
    }

    @SuppressWarnings("unchecked")
    public void handle_job(String[] cmd) {
        Job j = new Job();
        System.out.println("Creating job");
        TrieNode<Project> p = projects.search(cmd[2]);
        if (p == null) {
            System.out.println("No such project exists. " + cmd[2]); // check if this is fine
            return;
        } else {
            j.project = p.value;
            j.name = cmd[1];
            TrieNode<User> u = users.search(cmd[3]);
            if (u == null) {
                System.out.println("No such user exists: " + cmd[3]);
                return;
            }
            j.id = job_id;
            job_id++;
            j.user = u.value;
            j.time = Integer.parseInt(cmd[4]);
            j.arrival_time = timenow;
            jobs.insert(j);
            jobtrie.insert(cmd[1], j);
            alljobs.add(j);
            u.value.usersjobs.add(j);
            p.value.projectsjobs.add(j);
            totaljobs++;
            return;
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public void timed_handle_job(String[] cmd) {
        Job j = new Job();

        TrieNode<Project> p = projects.search(cmd[2]);
        if (p == null) {
            return;
        } else {
            j.project = p.value;
            j.name = cmd[1];
            TrieNode<User> u = users.search(cmd[3]);
            if (u == null) {
                return;
            }
            j.id = job_id;
            job_id++;
            j.user = u.value;
            j.time = Integer.parseInt(cmd[4]);
            j.arrival_time = timenow;
            jobs.insert(j);
            jobtrie.insert(cmd[1], j);
            alljobs.add(j);
            u.value.usersjobs.add(j);
            p.value.projectsjobs.add(j);
            totaljobs++;
            return;
        }
    }

    public void handle_project(String[] cmd) {
        Project p = new Project();
        p.name = cmd[1];
        p.priority = Integer.parseInt(cmd[2]);
        p.budget = Integer.parseInt(cmd[3]);
        projects.insert(p.name, p);
        System.out.println("Creating project");
        allprojects.add(p);
    }

    @Override
    public void timed_handle_project(String[] cmd) {
        Project p = new Project();
        p.name = cmd[1];
        p.priority = Integer.parseInt(cmd[2]);
        p.budget = Integer.parseInt(cmd[3]);
        projects.insert(p.name, p);
        allprojects.add(p);
    }

    public void execute_a_job() {
        System.out.println("Running code");
        System.out.println("Remaining jobs: " + (totaljobs));
        int a = 1;
        // MaxHeap<Job> tempjobs = new MaxHeap<Job>();
        while (a > 0 && jobs.size > 0) {
            Job j = jobs.extractMax();
            totaljobs--;
            System.out.println("Executing: " + j.name + " from: " + j.project.name);
            if (j.time <= j.project.budget) {
                finishedjobs.add(j);
                j.project.budget -= j.time;
                j.iscompleted = true;
                j.user.consumedtime += j.time;
                System.out.println(
                    "Project: " + j.project.name + " budget remaining: " + j.project.budget);
                a--;
                done++;
                timenow += j.time;
                j.user.lasttime = timenow;
                j.end_time = timenow;
            } else {
                System.out.println("Un-sufficient budget.");
                unfinishedjobs.insert(j.project.name, j);
            }
        }
        System.out.println("Execution cycle completed"); // changed this a bit
    }

    ArrayList<Job> unfinishedend = new ArrayList<Job>();
    public void inorder(RedBlackNode<String, Job> n) {
        if (n == null)
            return;

        inorder(n.left);
        for (int i = 0; i < n.values.size(); i++) {
            unfinishedend.add(n.values.get(i));
            unf++;
        }
        inorder(n.right);
    }
}
