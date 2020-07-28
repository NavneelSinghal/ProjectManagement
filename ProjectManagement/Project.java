package ProjectManagement;
import java.util.ArrayList;
@SuppressWarnings("unchecked")
public class Project {
    public Integer priority, budget;
    public String name;
    public ArrayList<Job> projectsjobs = new ArrayList<Job>();

    public ArrayList<JobReport_> findjobs(int t1, int t2) {
        ArrayList<JobReport_> submit = new ArrayList<JobReport_>();

        int index1 = projectsjobs.size();
        int index2 = -1;

        int left = 0;
        int right = projectsjobs.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (projectsjobs.get(mid).arrival_time >= t1) {
                index1 = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        left = 0;
        right = projectsjobs.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (projectsjobs.get(mid).arrival_time <= t2) {
                index2 = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        for (int i = index1; i <= index2; i++) {
            submit.add(projectsjobs.get(i));
        }

        return submit;
    }

    public ArrayList<JobReport_> findjobsuser(int t1, int t2, User u) {
        ArrayList<JobReport_> submit = new ArrayList<JobReport_>();

        int index1 = projectsjobs.size();
        int index2 = -1;

        int left = 0;
        int right = projectsjobs.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (projectsjobs.get(mid).arrival_time >= t1) {
                index1 = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        left = 0;
        right = projectsjobs.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (projectsjobs.get(mid).arrival_time <= t2) {
                index2 = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        for (int i = index1; i <= index2; i++) {
            if (projectsjobs.get(i).user.name.equals(u.name))
                submit.add(projectsjobs.get(i));
        }

        return submit;
    }

    public static void mergesort(ArrayList<Project> a, int left, int right) {
        if (left >= right)
            return;
        int mid = left + (right - left) / 2;
        mergesort(a, left, mid);
        mergesort(a, mid + 1, right);
        merge(a, left, mid, right);
    }
    // need to sort in decreasing order of priority
    public static void merge(ArrayList<Project> a, int left, int mid, int right) {
        int size1 = mid - left + 1;
        int size2 = right - mid;
        Project lef[] = new Project[size1];
        Project righ[] = new Project[size2];
        for (int i = 0; i < size1; i++) {
            lef[i] = a.get(i + left);
        }
        for (int i = 0; i < size2; i++) {
            righ[i] = a.get(i + mid + 1);
        }
        int idx1 = 0;
        int idx2 = 0;
        while (idx1 < size1 && idx2 < size2) {
            if (lef[idx1].priority >= righ[idx2].priority) {
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
}
