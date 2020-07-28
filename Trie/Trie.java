package Trie;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Trie<T> implements TrieInterface<T> {
    int height = 0;
    public TrieNode<T> root = new TrieNode<T>();

    @Override
    public boolean delete(String word) {
        TrieNode<T> last = search(word); // changed here
        if (last == null) {
            return false;
        }
        // TrieNode<T> last = search(word);
        int asd = 0;
        for (int i = 0; i < 96; i++) {
            if (last.arr[i] != null) {
                asd++;
            }
        }
        if (asd > 0) {
            last.isend = false;
            return true;
        }

        int latest = -1;
        TrieNode<T> lat = null;
        TrieNode<T> t = root;

        for (int i = 0; i < word.length(); i++) {
            t = t.arr[word.charAt(i) - ' '];
            int num = 0;
            for (int j = 0; j < 96; j++) {
                if (t.arr[j] != null) {
                    num++;
                }
            }
            if (num > 1) {
                latest = i;
                lat = t;
            }
        }

        if (latest == -1) {
            root.arr[word.charAt(0) - ' '] = null;
        } else {
            t = root;
            for (int j = 0; j <= latest; j++) {
                t = t.arr[word.charAt(j) - ' '];
            }
            t.arr[word.charAt(latest + 1) - ' '] = null;
        }

        return true;
    }

    @Override
    public TrieNode search(String word) {
        // return search(root, word, 0);
        TrieNode<T> t = root;
        int i = 0;
        while (i < word.length()) {
            if (t == null || t.arr[word.charAt(i) - ' '] == null) {
                return null;
            } else {
                t = t.arr[word.charAt(i) - ' '];
            }
            i++;
        }
        if (t == null || t.isend == false) {
            return null;
        } else {
            return t;
        }
    }

    @Override
    public TrieNode startsWith(String prefix) {
        TrieNode<T> t = root;
        int i = 0;
        while (i < prefix.length()) {
            if (t == null || t.arr[prefix.charAt(i) - ' '] == null) {
                return null;
            } else {
                t = t.arr[prefix.charAt(i) - ' '];
            }
            i++;
        }
        if (t == null) {
            return null;
        } else {
            return t;
        }
    }

    /*
     * public ArrayList<String> mergeSort(ArrayList<String> x) { int mid =
     * x.size()/2; if(x.size()<=1) { return x; } ArrayList<String> a = new
     * ArrayList<String>(); ArrayList<String> b = new ArrayList<String>(); for(int i
     * = 0; i<mid; i++) { a.add(x.get(i)); } for(int i = mid; i<x.size(); i++) {
     * b.add(x.get(i)); } return merge(mergeSort(a), mergeSort(b)); }
     *
     * public ArrayList<String> merge(ArrayList<String>a, ArrayList<String> b){
     * ArrayList<String> ans = new ArrayList<String>(); int i = 0; int j = 0;
     * while(i<a.size() && j<b.size()) { if(a.get(i).compareTo(b.get(j)) < 0) {
     * ans.add(a.get(i)); i++; } else { ans.add(b.get(j)); j++; } }
     * while(i<a.size()) { ans.add(a.get(i)); i++; } while(j<b.size()) {
     * ans.add(b.get(j)); j++; } return ans; }
     */

    @Override
    public void printTrie(TrieNode trieNode) {
        ArrayList<String> arr = new ArrayList<String>();
        // Queue<TrieNode<T>> f = new LinkedList<TrieNode<T>>();
        // f.add(trieNode);
        Stack<TrieNode<T>> f = new Stack<TrieNode<T>>();
        f.push(trieNode);
        while (f.size() > 0) {
            // TrieNode<T> w = f.remove();
            TrieNode<T> w = f.pop();
            if (w.value != null) {
                arr.add(w.value.toString());
            }
            for (int i = 0; i < 96; i++) {
                if (w.arr[i] != null) {
                    f.push(w.arr[i]);
                    // f.add(w.arr[i]);
                }
            }
        }
        // arr = mergeSort(arr);
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(arr.size() - 1 - i));
        }
    }

    @Override
    public boolean insert(String word, Object val) {
        T value = (T) val;

        if (word.length() > height)
            height = word.length();

        if (word.length() == 0)
            return false;

        if (search(word) != null)
            return false;

        // now check if the word is a prefix or not, if it is, make the value of the isend equal to
        // true

        TrieNode<T> sss = startsWith(word);
        if (sss != null) {
            sss.isend = true;
            sss.value = value;
            return true;
        }

        // word is not in there

        TrieNode<T> t = root;
        int i = 0;
        while (i < word.length() && t.arr[word.charAt(i) - ' '] != null) {
            t = t.arr[word.charAt(i) - ' '];
            i++;
        }

        for (int j = i; j < word.length(); j++) {
            t.arr[word.charAt(j) - ' '] = new TrieNode<T>();
            t = t.arr[word.charAt(j) - ' '];
        }
        t.isend = true;
        t.value = value;

        return true;
    }

    @Override
    public void printLevel(int level) {
        Queue<TrieNode<T>> f = new LinkedList<TrieNode<T>>();
        Queue<Character> q = new LinkedList<Character>();
        f.add(root);
        q.add(' ');
        int i = 1;
        int nodeshere = 1;
        while (f.size() > 0 && i <= level) {
            nodeshere = f.size();
            while (nodeshere > 0) {
                TrieNode<T> t = f.remove();
                q.remove();
                for (int j = 0; j < 96; j++) {
                    if (t.arr[j] != null) {
                        f.add(t.arr[j]);
                        char x = (char) (' ' + j);
                        q.add(x);
                    }
                }
                nodeshere--;
            }
            i++;
        }
        ArrayList<Character> aaa = new ArrayList<Character>();
        while (q.size() > 0) {
            aaa.add(q.remove());
        }

        int freq[] = new int[96];
        for (int r = 0; r < 96; r++) {
            freq[r] = 0;
        }
        while (aaa.size() > 0) {
            char c = aaa.remove(aaa.size() - 1);
            freq[c - ' ']++;
        }
        for (int r = 1; r < 96; r++) {
            char c = (char) (' ' + r);
            while (freq[r] > 0) {
                aaa.add(c);
                freq[r]--;
            }
        }
        System.out.print("Level " + level + ": ");
        if (aaa.size() == 0) {
            System.out.println("");
            return;
        }
        for (int j = 0; j < aaa.size() - 1; j++) {
            System.out.print(aaa.get(j));
            System.out.print(',');
        }
        System.out.println(aaa.get(aaa.size() - 1));
    }

    @Override
    public void print() {
        System.out.println("-------------\nPrinting Trie");
        Queue<TrieNode<T>> f = new LinkedList<TrieNode<T>>();
        Queue<Character> q = new LinkedList<Character>();
        f.add(root);
        q.add(' ');

        int i = 1;
        int nodeshere = 1;

        while (f.size() > 0 && i <= height + 1) {
            nodeshere = f.size();
            while (nodeshere > 0) {
                TrieNode<T> t = f.remove();
                q.remove();
                for (int j = 0; j < 96; j++) {
                    if (t.arr[j] != null) {
                        f.add(t.arr[j]);
                        char x = (char) (' ' + j);
                        q.add(x);
                    }
                }
                nodeshere--;
            }
            Queue<Character> q2 = new LinkedList<Character>(q);
            int freq[] = new int[96];
            for (int r = 0; r < 96; r++) {
                freq[r] = 0;
            }
            while (q2.size() > 0) {
                char c = q2.remove();
                freq[c - ' ']++;
            }
            ArrayList<Character> aaa = new ArrayList<Character>();
            for (int r = 1; r < 96; r++) {
                char c = (char) (' ' + r);
                while (freq[r] > 0) {
                    aaa.add(c);
                    freq[r]--;
                }
            }
            System.out.print("Level " + i + ": ");
            if (aaa.size() == 0) {
                System.out.println("");
            } else {
                for (int j = 0; j < aaa.size() - 1; j++) {
                    System.out.print(aaa.get(j));
                    System.out.print(',');
                }
                System.out.println(aaa.get(aaa.size() - 1));
            }
            i++;
            if (i > height + 1) {
                System.out.println("-------------");
            }
        }

        // System.out.println("");
    }
}
