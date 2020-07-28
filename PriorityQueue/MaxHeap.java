package PriorityQueue;
import java.util.ArrayList;
@SuppressWarnings("unchecked")
public class MaxHeap<T extends Comparable> implements PriorityQueueInterface<T> {
    public ArrayList<T> heap = new ArrayList<T>();
    public ArrayList<Integer> times = new ArrayList<Integer>();
    public ArrayList<pair<T, Integer>> heapt = new ArrayList<pair<T, Integer>>();
    public int size = 0;
    public int time = 0;

    @Override
    public void insert(T element) {
        time++;
        if (size == 0) {
            // heap.add(element);
            // times.add(time);
            heapt.add(new pair<T, Integer>(element, time));
            size++;
            return;
        } else {
            // heap.add(element);
            // times.add(time);
            heapt.add(new pair<T, Integer>(element, time));
            size++;
            // children of i are 2i+1 and 2i+2, and the parent of i is (i-1)/2
            int here = size - 1;
            // now bubble up the element to its rightful place
            while (here > 0) {
                pair<T, Integer> h = heapt.get(here);
                pair<T, Integer> p = heapt.get((here - 1) / 2);
                // Integer thh = times.get(here);
                // Integer thp = times.get((here - 1)/2);
                if (h.F.compareTo(p.F) > 0
                    || (h.F.compareTo(p.F) == 0
                        && h.S.compareTo(p.S) < 0)) { // if the parent of the index here is smaller
                                                      // than the element here, then swap
                    //    				T temp = (heap.get(here));
                    //    				T temp2 = (heap.get((here-1)/2));
                    //    				Integer tempt = times.get(here);
                    //    				Integer tempt2 = times.get((here-1)/2);
                    heapt.set(here, p);
                    heapt.set((here - 1) / 2, h);
                    //    				heap.set(here, hp);
                    //    				heap.set((here-1)/2, hh);
                    //    				times.set(here, thp);
                    //    				times.set((here-1)/2, thh);
                    here = (here - 1) / 2;
                } else {
                    break;
                }
            }
        }
    }

    @Override
    public T extractMax() {
        if (size == 0) {
            return null;
        }
        pair<T, Integer> ans = heapt.get(0);
        heapt.set(0, heapt.get(size - 1));
        // times.set(0, times.get(size-1));
        heapt.remove(size - 1);
        // times.remove(size-1);
        size--;
        // now bubble down the smallest element to its rightful place
        int here = 0;
        int left = 1;
        while (left < size) {
            int right = left + 1;
            int maxchild = left;

            pair<T, Integer> max = heapt.get(left);
            // Integer t = times.get(left);
            pair<T, Integer> h = heapt.get(here);
            // Integer th = times.get(here);

            if (right < size) {
                pair<T, Integer> l = heapt.get(left);
                pair<T, Integer> r = heapt.get(right);
                //    			Integer tl = times.get(left);
                //    			Integer tr = times.get(right);

                if ((l.F.compareTo(r.F) < 0
                        || (l.F.compareTo(r.F) == 0 && l.S.compareTo(r.S) > 0))) {
                    maxchild = right;
                    max = r;
                    // t = tr;
                }
            }

            if (h.F.compareTo(max.F) < 0
                || (h.F.compareTo(max.F) == 0 && h.S.compareTo(max.S) > 0)) {
                // now swap
                //    			heap.set(maxchild, hh);
                //    			heap.set(here, max);
                //    			times.set(maxchild, th);
                //    			times.set(here, t);
                heapt.set(maxchild, h);
                heapt.set(here, max);
            } else {
                break;
            }
            here = maxchild;
            left = 2 * maxchild + 1;
        }
        return ans.F;
    }
}

class pair<T, E> {
    T F;
    E S;
    pair(T t, E e) {
        F = t;
        S = e;
    }
}
