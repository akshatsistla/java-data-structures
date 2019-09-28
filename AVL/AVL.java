import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

public class AVL<T extends Comparable<? super T>> {
    private AVLNode<T> root;
    private int size;

    public AVL() {
    }

    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        size = 0;
        for (T temp: data) {
            if (temp == null) {
                throw new IllegalArgumentException("Data is null");
            }
            add(temp);
        }
    }

    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        root = addHelper(data, root);

        size++;
    }

    private AVLNode<T> addHelper(T data, AVLNode<T> check) {
        if (check == null) {
            return new AVLNode<T>(data);
        } else if (check.getData().compareTo(data) > 0) {
            check.setLeft(addHelper(data, check.getLeft()));
        } else if (check.getData().compareTo(data) < 0) {
            check.setRight(addHelper(data, check.getRight()));
        } else {
            size--;
            return check;
        }
        setHeightHelper(check);

        setBalanceHelper(check);

        if (check.getBalanceFactor() > 1 || check.getBalanceFactor() < -1) {
            return checkRotate(check);
        }

        return check;
    }

    private AVLNode<T> checkRotate(AVLNode<T> check) {
        int balance = check.getBalanceFactor();

        if (balance > 1 && check.getLeft().getBalanceFactor() >= 0) {
            return rightRotate(check);
        } else if (balance < -1 && check.getRight().getBalanceFactor() <= 0) {
            return leftRotate(check);
        } else if (balance > 1 && check.getLeft().getBalanceFactor() < 0) {
            check.setLeft(leftRotate(check.getLeft()));
            return rightRotate(check);
        } else {
            check.setRight(rightRotate(check.getRight()));
            return leftRotate(check);
        }
    }

    private AVLNode<T> rightRotate(AVLNode<T> change) {
        AVLNode<T> swap = change.getLeft();
        AVLNode<T> transfer = swap.getRight();

        swap.setRight(change);
        change.setLeft(transfer);

        setHeightHelper(change);
        setHeightHelper(swap);
        setBalanceHelper(change);
        setBalanceHelper(swap);

        return swap;
    }

    private AVLNode<T> leftRotate(AVLNode<T> change) {
        AVLNode<T> swap = change.getRight();
        AVLNode<T> transfer = swap.getLeft();

        swap.setLeft(change);
        change.setRight(transfer);

        setHeightHelper(change);
        setHeightHelper(swap);
        setBalanceHelper(change);
        setBalanceHelper(swap);

        return swap;
    }

    private void setHeightHelper(AVLNode<T> check) {
        if (check.getRight() == null) {
            if (check.getLeft() == null) {
                check.setHeight(0);
            } else {
                check.setHeight(1 + check.getLeft().getHeight());
            }
        } else if (check.getLeft() == null) {
            check.setHeight(1 + check.getRight().getHeight());
        } else {
            check.setHeight(1 + Math.max(check.getLeft().getHeight(),
                    check.getRight().getHeight()));
        }
    }

    private void setBalanceHelper(AVLNode<T> check) {
        if (check.getRight() == null) {
            if (check.getLeft() == null) {
                check.setBalanceFactor(0);
            } else {
                check.setBalanceFactor(check.getLeft().getHeight() + 1);
            }
        } else if (check.getLeft() == null) {
            check.setBalanceFactor(-1 - check.getRight().getHeight());
        } else {
            check.setBalanceFactor(check.getLeft().getHeight()
                    - check.getRight().getHeight());
        }
    }

    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        AVLNode<T> temp = new AVLNode<T>(data);
        root = removeHelper(data, root, temp);
        size--;
        return temp.getData();
    }

    private AVLNode<T> removeHelper(T data, AVLNode<T> check,
                                    AVLNode<T> temps) {
        if (check == null) {
            throw new NoSuchElementException("Data is not found");
        }
        if (check.getData().compareTo(data) > 0) {
            check.setLeft(removeHelper(data, check.getLeft(), temps));
        } else if (check.getData().compareTo(data) < 0) {
            check.setRight(removeHelper(data, check.getRight(), temps));
        } else {
            temps.setData(check.getData());
            if (check.getLeft() == null || check.getRight() == null) {
                AVLNode<T> temp;
                if (check.getLeft() == null) {
                    temp = check.getRight();
                } else {
                    temp = check.getLeft();
                }
                if (temp == null) {
                    check = null;
                } else {
                    check = temp;
                }
            } else {
                AVLNode<T> temp = successor(check.getRight());
                check.setData(temp.getData());
                check.setRight(removeHelper2(temp.getData(), check.getRight()));
            }
        }

        if (check == null) {
            return null;
        }
        setHeightHelper(check);
        setBalanceHelper(check);

        if (check.getBalanceFactor() > 1 || check.getBalanceFactor() < -1) {
            return checkRotate(check);
        }

        return check;
    }

    private AVLNode<T> removeHelper2(T data, AVLNode<T> check) {
        if (check == null) {
            throw new NoSuchElementException("Data is not found");
        }
        if (check.getData().compareTo(data) > 0) {
            check.setLeft(removeHelper2(data, check.getLeft()));
        } else if (check.getData().compareTo(data) < 0) {
            check.setRight(removeHelper2(data, check.getRight()));
        } else {
            check = check.getRight();
        }

        if (check == null) {
            return null;
        }
        setHeightHelper(check);
        setBalanceHelper(check);

        if (check.getBalanceFactor() > 1 || check.getBalanceFactor() < -1) {
            return checkRotate(check);
        }

        return check;
    }

    private AVLNode<T> successor(AVLNode<T> check) {
        AVLNode<T> curr = check;
        while (curr.getLeft() != null) {
            curr = curr.getLeft();
        }
        return curr;
    }

    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        return getHelper(data, root);
    }

    private T getHelper(T data, AVLNode<T> check) {
        if (check == null) {
            throw new NoSuchElementException("Data is not present");
        }
        if (check.getData().compareTo(data) > 0) {
            return getHelper(data, check.getLeft());
        } else if (check.getData().compareTo(data) < 0) {
            return getHelper(data, check.getRight());
        } else {
            return check.getData();
        }
    }

    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        return containsHelper(data, root);
    }

    private boolean containsHelper(T data, AVLNode<T> check) {
        if (check == null) {
            return false;
        }
        if (check.getData().compareTo(data) > 0) {
            return containsHelper(data, check.getLeft());
        } else if (check.getData().compareTo(data) < 0) {
            return containsHelper(data, check.getRight());
        } else {
            return true;
        }
    }

    public List<T> deepestBranches() {
        ArrayList<T> ret = new ArrayList<T>();
        if (size == 0) {
            return ret;
        }
        deepestBranchesHelper(ret, root);
        return ret;
    }

    private void deepestBranchesHelper(List<T> ret, AVLNode<T> check) {
        ret.add(check.getData());
        if (check.getLeft() != null) {
            if (check.getRight() != null) {
                if (check.getLeft().getHeight()
                        > check.getRight().getHeight()) {
                    deepestBranchesHelper(ret, check.getLeft());
                } else if (check.getLeft().getHeight()
                        < check.getRight().getHeight()) {
                    deepestBranchesHelper(ret, check.getRight());
                } else {
                    deepestBranchesHelper(ret, check.getLeft());
                    deepestBranchesHelper(ret, check.getRight());
                }
            } else {
                deepestBranchesHelper(ret, check.getLeft());
            }
        } else if (check.getRight() != null) {
            deepestBranchesHelper(ret, check.getRight());
        }
    }

    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null || data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("Data is unacceptable");
        }
        ArrayList<T> ret = new ArrayList<T>();
        if (size == 0) {
            return ret;
        }
        betweenHelper(ret, root, data1, data2);
        return ret;
    }

    private void betweenHelper(List<T> ret, AVLNode<T> check, T min, T max) {
        if (check.getData().compareTo(min) <= 0) {
            if (check.getRight() != null) {
                betweenHelper(ret, check.getRight(), min, max);
            }
        } else {
            if (check.getData().compareTo(max) >= 0) {
                if (check.getLeft() != null) {
                    betweenHelper(ret, check.getLeft(), min, max);
                }
            } else {
                if (check.getLeft() != null) {
                    betweenHelper(ret, check.getLeft(), min, max);
                }
                ret.add(check.getData());
                if (check.getRight() != null) {
                    betweenHelper(ret, check.getRight(), min, max);
                }
            }
        }

    }

    public void clear() {
        root = null;
        size = 0;
    }

    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }

    public int size() {
        return size;
    }

    public AVLNode<T> getRoot() {
        return root;
    }
}
