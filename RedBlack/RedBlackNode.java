package RedBlack;

import Util.RBNodeInterface;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("unchecked")
public class RedBlackNode<T extends Comparable, E> implements RBNodeInterface<E> {
    public T key;
    public int colour = 0; // red is 0
    // by default, every node is red
    public List<E> values = new ArrayList<E>();

    public RedBlackNode<T, E> left = null, right = null, parent = null;

    @Override
    public E getValue() {
        return null;
    }

    @Override
    public List<E> getValues() {
        return values;
    }
}
