package ProjectManagement;
//@SuppressWarnings("unchecked")
import java.util.ArrayList;
public class User implements Comparable<User>, UserReport_ {
    public String name;
    public Integer consumedtime = 0;
    public Integer lasttime = 0;
    public ArrayList<Job> usersjobs = new ArrayList<Job>();
    @Override
    public int compareTo(User a) {
        if (this.consumedtime.compareTo(a.consumedtime) == 0) {
            return a.lasttime.compareTo(
                this.lasttime); // this sorts in increasing order of last time -- note that the heap
                                // order due to the max thing messes up the order
        } else
            return this.consumedtime.compareTo(
                a.consumedtime); // this sorts in decreasing of consumed time
    }
    public String user() {
        return this.name;
    }
    public int consumed() {
        return this.consumedtime;
    }

    public ArrayList<JobReport_> findjobs(int t1, int t2) {
        ArrayList<JobReport_> submit = new ArrayList<JobReport_>();

        int index1 = usersjobs.size();
        int index2 = -1;

        int left = 0;
        int right = usersjobs.size() - 1;

        while (left <= right) {
            int mid = left + (-left + right) / 2;
            if (usersjobs.get(mid).arrival_time >= t1) {
                index1 = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        left = 0;
        right = usersjobs.size() - 1;

        while (left <= right) {
            int mid = left + (-left + right) / 2;
            if (usersjobs.get(mid).arrival_time <= t2) {
                index2 = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        for (int i = index1; i <= index2; i++) {
            submit.add(usersjobs.get(i));
        }

        return submit;
    }
}
