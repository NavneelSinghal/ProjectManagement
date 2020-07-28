package Trie;

import Util.NodeInterface;
import java.util.HashMap;

public class TrieNode<T> implements NodeInterface<T> {
    // public HashMap<Character, TrieNode<T>> map = new HashMap<Character, TrieNode<T>>();
    public TrieNode<T> arr[] = new TrieNode[96];
    public boolean isend = false;
    public T value;

    @Override
    public T getValue() {
        return value;
    }
}
