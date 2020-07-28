package ProjectManagement;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Job implements Comparable<Job>, JobReport_ {
    public Project project;
    public String name;
    public User user;
    public Integer time;
    public boolean iscompleted = false;
    public Integer end_time = Integer.MAX_VALUE;
    public Integer arrival_time = 0;
    public boolean isflushed = false;
    public Integer id = 0;
    @Override
    public int compareTo(Job job) {
        int a = this.project.priority.compareTo(
            job.project.priority); // check this, if this is not correct, then replace prioirity by
                                   // the getpriority function
        if (a != 0)
            return a;
        else {
            return job.id.compareTo(this.id);
        }
    }
    //", priority="+getpriority()+
    @Override
    public String toString() {
        return "Job{user='" + user.name + "', project='" + project.name + "', jobstatus="
            + ((iscompleted) ? "COMPLETED" : "REQUESTED") + ", execution_time=" + time
            + ", end_time=" + (iscompleted ? end_time : "null") + ", name='" + name + "'}";
    }

    public Integer getpriority() {
        if (isflushed) {
            return 9999;
        } else
            return this.project.priority;
    }
    public String user() {
        return this.user.name;
    }
    public String project_name() {
        return this.project.name;
    }
    public int budget() {
        return this.time; // check this
    }
    public int arrival_time() {
        return this.arrival_time;
    }
    public int completion_time() {
        return this.end_time;
    }

    public static void mergesort(ArrayList<JobReport_> a, int left, int right) { // check this
        if (left < right) {
            //			int part = partition(a, a.get(left), left+1, right);
            //			JobReport_ temp = a.get(left);
            //			a.set(left, a.get(part));
            //			a.set(part,  temp);
            //			quicksort(a, part+1, right);
            //			quicksort(a, left, part-1);
            if (left >= right)
                return;
            int mid = left + (right - left) / 2;
            mergesort(a, left, mid);
            mergesort(a, mid + 1, right);
            merge(a, left, mid, right);
        }
    }

    public static boolean comp(JobReport_ x, JobReport_ el) { // returns true if x <= el
        int check = 0; // a.get(i) <= el iff check is 0
        if (x.completion_time() == Integer.MAX_VALUE && el.completion_time() == Integer.MAX_VALUE) {
            if (x.arrival_time() <= el.arrival_time()) {
                check = 0;
            } else {
                check = 1;
            }
        } else if (x.completion_time() == Integer.MAX_VALUE
            && el.completion_time() != Integer.MAX_VALUE) {
            check = 1;
        } else if (x.completion_time() != Integer.MAX_VALUE
            && el.completion_time() == Integer.MAX_VALUE) {
            check = 0;
        } else {
            if (x.completion_time() > el.completion_time()) {
                check = 1;
            }
        }
        return check == 0;
    }

    public static void merge(ArrayList<JobReport_> a, int left, int mid, int right) {
        int size1 = mid - left + 1;
        int size2 = right - mid;
        JobReport_ lef[] = new JobReport_[size1];
        JobReport_ righ[] = new JobReport_[size2];
        for (int i = 0; i < size1; i++) {
            lef[i] = a.get(i + left);
        }
        for (int i = 0; i < size2; i++) {
            righ[i] = a.get(i + mid + 1);
        }
        int idx1 = 0;
        int idx2 = 0;
        while (idx1 < size1 && idx2 < size2) {
            if (comp(lef[idx1], righ[idx2])) {
                a.set(idx1 + idx2 + left, lef[idx1]);
                idx1++;
            } else {
                a.set(idx1 + idx2 + left, righ[idx2]);
                idx2++;
            }
        }
        if (idx1 != size1) {
            for (int i = idx1; i < size1; i++) {
                a.set(idx1 + idx2 + left, lef[idx1]);
                idx1++;
            }
        }
        if (idx2 != size2) {
            for (int i = idx2; i < size2; i++) {
                a.set(idx1 + idx2 + left, righ[idx2]);
                idx2++;
            }
        }
    }

    //	public static int partition(ArrayList<JobReport_> a, JobReport_ el, int left, int right) {
    //		int i = left;
    //		int j = right;
    //		while(i <= j) {
    //			JobReport_ x = a.get(i);
    //			int check = 0; //a.get(i) <= el
    //			if(x.completion_time() == Integer.MAX_VALUE && el.completion_time() ==
    //Integer.MAX_VALUE) { 				if(x.arrival_time() <= el.arrival_time()) { 					check = 0;
    //				}
    //				else {
    //					check = 1;
    //				}
    //			}
    //			else if(x.completion_time() == Integer.MAX_VALUE && el.completion_time() !=
    //Integer.MAX_VALUE) { 				check = 1;
    //			}
    //			else if(x.completion_time() != Integer.MAX_VALUE && el.completion_time() ==
    //Integer.MAX_VALUE) { 				check = 0;
    //			}
    //			else {
    //				if(x.completion_time() > el.completion_time()) {
    //					check = 1;
    //				}
    //			}
    //
    //			if(check == 0) {
    //				i++;
    //			}
    //			else {
    //				JobReport_ temp = a.get(i);
    //				a.set(i, a.get(j));
    //				a.set(j,  temp);
    //				j--;
    //			}
    //		}
    //		int p = j;
    //		return p;
    //	}
}