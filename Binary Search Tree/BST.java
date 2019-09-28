import java.util.*;

public class BST<T extends Comparable<? super T>> {
    private BSTNode<T> root;
    private int size;

    public BST() {}

    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data input is null");
        }
        for (T temp: data) {
            if(temp == null) {
                throw new IllegalArgumentException("A value in input data is null");
            }
            add(temp);
        }
    }

    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input value is null");
        }
        if (size == 0) {
           root = new BSTNode<T>(data);
           size++;
           return;
        }
        addHelper(data , root);
        size++;
    }

    private void addHelper(T data, BSTNode<T> check) {
        if (check.getData().compareTo(data) > 0) {
            if (check.getLeft() == null) {
                check.setLeft(new BSTNode<T>(data));
            } else {
                addHelper(data, check.getLeft());
            }
        } else if (check.getData().compareTo(data) < 0) {
            if (check.getRight() == null) {
                check.setRight(new BSTNode<T>(data));
            } else {
                addHelper(data, check.getRight());
            }
        } else {
            size--;
        }
    }

    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null");
        }

        if (size == 0) {
            throw new NoSuchElementException("The tree is empty");
        }

        T ret;
        BSTNode<T> temp;
        BSTNode<T> prev;
        T save;
        int side = 0;
        if (size == 1) {
            if (root.getData().compareTo(data) != 0) {
                throw new NoSuchElementException("The data is not found");
            }
            ret = root.getData();
            clear();
            return ret;
        } else if (root.getData().compareTo(data) == 0) {
            temp = root;
            ret = temp.getData();
            if (temp.getLeft() == null) {
                root = root.getRight();
                size--;
            } else if (temp.getRight() == null) {
                root = root.getLeft();
                size--;
            } else {
                save = predecessor(temp.getLeft());
                remove(save);
                temp.setData(save);
            }
        } else {
            temp = removeHelper(data, root);
            prev = temp;
            if (temp.getLeft() == null) {
                ret = temp.getRight().getData();
                temp = temp.getRight();
                side = 1;
            } else if (temp.getLeft().getData().compareTo(data) != 0) {
                ret = temp.getRight().getData();
                temp = temp.getRight();
                side = 1;
            } else {
                ret = temp.getLeft().getData();
                temp = temp.getLeft();
            }
            if (temp.getLeft() == null) {
                if (temp.getRight() == null) {
                    if (side == 0) {
                        prev.setLeft(null);
                        size--;
                    } else {
                        prev.setRight(null);
                        size--;
                    }
                } else {
                    if (side == 0) {
                        prev.setLeft(temp.getRight());
                        size--;
                    } else {
                        prev.setRight(temp.getRight());
                        size--;
                    }
                }
            } else if (temp.getRight() == null) {
                if (side == 0) {
                    prev.setLeft(temp.getLeft());
                    size--;
                } else {
                    prev.setRight(temp.getLeft());
                    size--;
                }
            } else {
                save = predecessor(temp.getLeft());
                remove(save);
                temp.setData(save);
            }
        }

        return ret;
    }

    private BSTNode<T> removeHelper(T data, BSTNode<T> check) {
        if (check == null) {
            return null;
        }
        if (check.getData().compareTo(data) > 0) {
            if (check.getLeft() == null) {
                throw new NoSuchElementException("The data is not found");
            }
            if (check.getLeft().getData().compareTo(data) == 0) {
                return check;
            }
            return removeHelper(data, check.getLeft());
        } else {
            if (check.getRight() == null) {
                throw new NoSuchElementException("The data is not found");
            }
            if (check.getRight().getData().compareTo(data) == 0) {
                return check;
            }
            return removeHelper(data, check.getRight());
        }
    }

    private T predecessor(BSTNode<T> check) {
        if (check.getData() == null) {
            throw new IllegalArgumentException("Input value is null");
        }
        if (check.getRight() == null) {
            return check.getData();
        }
        return predecessor(check.getRight());
    }

    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null");
        }
        if (size == 0) {
            throw new NoSuchElementException("The tree is empty");
        }
        return getHelper(data, root);
    }

    private T getHelper(T data , BSTNode<T> temp) {
        if (temp == null) {
            throw new NoSuchElementException("The value is not present in the tree");
        }
        if (temp.getData().compareTo(data) > 0) {
            return getHelper(data, temp.getLeft());
        } else if (temp.getData().compareTo(data) < 0) {
            return getHelper(data, temp.getRight());
        }
        return temp.getData();
    }

    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is null");
        }
        if (size == 0) {
            return false;
        }
        return containsHelper(data, root);
    }

    private boolean containsHelper(T data , BSTNode<T> temp) {
        if (temp == null) {
            return false;
        }
        if (temp.getData().compareTo(data) > 0) {
            return containsHelper(data, temp.getLeft());
        } else if (temp.getData().compareTo(data) < 0) {
            return containsHelper(data, temp.getRight());
        }
        return true;
    }

    public List<T> preorder() {
        if (size == 0) {
            return null;
        }
        return preorderHelper(root);
    }

    private List<T> preorderHelper(BSTNode<T> temp) {
        List<T> ret = new ArrayList<T>();
        if (temp == null) {
            return ret;
        }
        ret.add(temp.getData());
        ret.addAll(preorderHelper(temp.getLeft()));
        ret.addAll(preorderHelper(temp.getRight()));
        return ret;
    }

    public List<T> inorder() {
        if (size == 0) {
            return null;
        }
        return inorderHelper(root);
    }

    private List<T> inorderHelper(BSTNode<T> temp) {
        List<T> ret = new ArrayList<T>();
        if (temp == null) {
            return ret;
        }
        ret.addAll(inorderHelper(temp.getLeft()));
        ret.add(temp.getData());
        ret.addAll(inorderHelper(temp.getRight()));
        return ret;
    }

    public List<T> postorder() {
        if (size == 0) {
            return null;
        }
        return postorderHelper(root);
    }

    private List<T> postorderHelper(BSTNode<T> temp) {
        List<T> ret = new ArrayList<T>();
        if (temp == null) {
            return ret;
        }
        ret.addAll(postorderHelper(temp.getLeft()));
        ret.addAll(postorderHelper(temp.getRight()));
        ret.add(temp.getData());
        return ret;
    }

    public List<T> levelorder() {
        Queue<BSTNode> check = new LinkedList<BSTNode>();
        List<T> ret = new ArrayList<T>();
        if (root == null) {
            return ret;
        }
        check.add(root);
        while(!check.isEmpty()){
            BSTNode<T> temp = ((LinkedList<BSTNode>) check).pop();
            ret.add(temp.getData());

            if (temp.getLeft() != null) {
                check.add(temp.getLeft());
            }
            if (temp.getRight() != null) {
                check.add(temp.getRight());
            }
        }
        return ret;
    }

    public static <T extends Comparable<? super T>> boolean isBST(
            BSTNode<T> treeRoot) {
        if (treeRoot == null) {
            return true;
        }
        return isBSTHelper(treeRoot, null, null);
    }

    private static <T extends Comparable<? super T>> boolean isBSTHelper(BSTNode<T> temp, T min, T max)
    {
        if (temp == null) {
            return true;
        }
        if (min == null) {
            if (max != null) {
                if (temp.getData().compareTo(max) > 0) {
                    return false;
                }
            }
        } else if (max == null) {
            if (temp.getData().compareTo(min) < 0) {
                return false;
            }
        } else {
            if (temp.getData().compareTo(max) > 0 || temp.getData().compareTo(min) < 0) {
                return false;
            }
        }
        return isBSTHelper(temp.getLeft(), min, temp.getData()) && isBSTHelper(temp.getRight(), temp.getData(), max);
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public int height() {
        if (size == 0) {
            return -1;
        }
        if (size == 1) {
            return 0;
        }
        return Math.max(heightHelper(root.getRight()) , heightHelper(root.getLeft())) + 1;
    }

    private int heightHelper(BSTNode<T> temp) {
        if (temp == null) {
            return -1;
        }
        return Math.max(heightHelper(temp.getRight()) , heightHelper(temp.getLeft())) + 1;
    }

    public int size() {
        return size;
    }

    public BSTNode<T> getRoot() {

        return root;
    }
}
