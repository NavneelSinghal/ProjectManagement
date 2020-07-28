package RedBlack;

import java.util.ArrayDeque;
import java.util.List;
@SuppressWarnings("unchecked")
public class RBTree<T extends Comparable, E> implements RBTreeInterface<T, E> {
    public RedBlackNode<T, E> root = null;

    public boolean isBlack(RedBlackNode<T, E> node) {
        if (node != null && node.colour == 0) {
            return true;
        } else
            return false;
    }

    @Override
    public void insert(T key, E value) {
        // System.out.println("Inserting");
        // if(key == null) {
        //	System.out.println("Key is null");
        //	return;
        //}
        RedBlackNode<T, E> n = root, r = search1(key);
        if (r != null) {
            // we have found an element with key equal to key
            r.values.add(value);
        } else {
            if (root == null) {
                root = new RedBlackNode<T, E>();
                root.values.add(value);
                root.key = key;
                root.colour = 1; // 1 is black
                return;
            }
            RedBlackNode<T, E> here = root, parent = null;
            while (here != null) {
                parent = here;
                if (key.compareTo(here.key) < 0) {
                    here = here.left;
                } else {
                    here = here.right;
                }
            }
            // parent is where we need to insert the node now
            RedBlackNode<T, E> node = new RedBlackNode<T, E>();
            node.key = key;
            node.values.add(value);

            node.parent = parent;
            if (key.compareTo(parent.key) < 0) {
                node.parent.left = node;
            } else {
                node.parent.right = node;
            }
            // now we need to check if the parent is red or not

            while (node != null && node != root && node.colour == 0) {
                if (node.parent.colour == 1) {
                    break;
                } else {
                    // parent of the node is red now, so the grandparent always exists.
                    // when you rotate, just check if the node you rotate is null or not.

                    // first check if uncle is not null and uncle is red
                    RedBlackNode<T, E> uncle;
                    if (node.parent == node.parent.parent.left) {
                        uncle = node.parent.parent.right;
                    } else
                        uncle = node.parent.parent.left;

                    if (uncle != null && uncle.colour == 0) { // red uncle case
                        node.parent.colour = 1;
                        uncle.colour = 1;
                        node.parent.parent.colour = 0;
                        node = node.parent.parent;
                        continue;
                    }
                    // now the uncle is guaranteed black
                    if (node.parent == node.parent.parent.left) {
                        if (node == node.parent.right) {
                            leftRot(node.parent);
                            node = node.left; // reduction to the left line case
                        }
                        // only the left line case to be managed
                        rightRot(node.parent.parent);
                        node = node.parent;
                        node.colour = 1;
                        node.right.colour = 0;
                    } else {
                        if (node == node.parent.left) {
                            rightRot(node.parent);
                            node = node.right; // reduction to the right line case
                        }
                        // only the right line case to be managed
                        leftRot(node.parent.parent);
                        node = node.parent;
                        node.colour = 1;
                        node.left.colour = 0;
                    }
                }
            }

            root.colour = 1;
        }
    }

    public void leftRot(RedBlackNode<T, E> n) {
        // we are guaranteed that the right node exists
        RedBlackNode<T, E> r = n.right; // r is clearly not null
        // case 1 - when parent of n is null, id est, n is the root
        if (n.parent == null) {
            n.parent = r;
            r.parent = null;
            n.right = r.left;
            if (n.right != null) {
                n.right.parent = n;
            }
            r.left = n;
            root = r;
        } else {
            // case 2 - n.parent is not null
            r.parent = n.parent;
            n.parent = r;
            if (r.parent.left == n) {
                r.parent.left = r;
            } else {
                r.parent.right = r;
            }
            n.right = r.left;
            r.left = n;
            if (n.right != null) {
                n.right.parent = n;
            }
        }
    }

    public void rightRot(RedBlackNode<T, E> n) {
        // we are guaranteed that the right node exists
        RedBlackNode<T, E> r = n.left; // r is clearly not null
        // case 1 - when parent of n is null, id est, n is the root
        if (n.parent == null) {
            n.parent = r;
            r.parent = null;
            n.left = r.right;
            if (n.left != null) {
                n.left.parent = n;
            }
            r.right = n;
            root = r;
        } else {
            // case 2 - n.parent is not null
            r.parent = n.parent;
            n.parent = r;
            if (r.parent.right == n) {
                r.parent.right = r;
            } else {
                r.parent.left = r;
            }
            n.left = r.right;
            r.right = n;
            if (n.left != null) {
                n.left.parent = n;
            }
        }
    }

    @Override
    public RedBlackNode<T, E> search(T key) {
        RedBlackNode<T, E> n = root;
        while (n != null) {
            if (n.key.compareTo(key) > 0) {
                n = n.left;
            } else if (n.key.compareTo(key) < 0) {
                n = n.right;
            } else {
                break;
            }
        }
        if (n != null) {
            return n;
        } else {
            RedBlackNode<T, E> a = new RedBlackNode<T, E>();
            a.values = null;
            return a;
        }
    }

    public RedBlackNode<T, E> search1(T key) {
        RedBlackNode<T, E> n = root;
        while (n != null) {
            if (n.key.compareTo(key) > 0) {
                n = n.left;
            } else if (n.key.compareTo(key) < 0) {
                n = n.right;
            } else {
                return n;
            }
        }
        return n;
    }
}
