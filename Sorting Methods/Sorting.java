import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class Sorting {

    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Inputs are null");
        }

        for (int i = 0; i < arr.length; i++) {
            for (int n = i; n > 0; n--) {
                if (comparator.compare(arr[n], arr[n - 1]) >= 0) {
                    n = 0;
                } else {
                    T temp = arr[n - 1];
                    arr[n - 1] = arr[n];
                    arr[n] = temp;
                }
            }
        }
    }

    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Inputs are null");
        }
        for (int i = arr.length - 1; i > 0; i--) {
            int max = 0;
            for (int n = 1; n <= i; n++) {
                if (comparator.compare(arr[n], arr[max]) > 0) {
                    max = n;
                }
            }
            T temp = arr[i];
            arr[i] = arr[max];
            arr[max] = temp;
        }
    }

    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Inputs are null");
        }
        if (arr.length != 1) {
            T[] temp1 = (T[]) new Object[arr.length / 2];
            T[] temp2 = (T[]) new Object[arr.length - temp1.length];
            for (int i = 0; i < temp1.length; i++) {
                temp1[i] = arr[i];
            }
            for (int i = 0; i < temp2.length; i++) {
                temp2[i] = arr[i + temp1.length];
            }

            mergeSort(temp1, comparator);
            mergeSort(temp2, comparator);

            int i = 0;
            int j = 0;

            while (i < temp1.length && j < temp2.length) {
                if (comparator.compare(temp1[i], temp2[j]) > 0) {
                    arr[i + j] = temp2[j];
                    j++;
                } else {
                    arr[i + j] = temp1[i];
                    i++;
                }
            }

            if (i == temp1.length) {
                while (j < temp2.length) {
                    arr[i + j] = temp2[j];
                    j++;
                }
            } else {
                while (i < temp1.length) {
                    arr[i + j] = temp1[i];
                    i++;
                }
            }
        }


    }

    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Inputs are null");
        }
        quickSort(arr, comparator, rand, 0, arr.length);
    }

    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand, int left, int right) {
        if (left < right) {
            int pivotIndex = rand.nextInt(right - left) + left;
            T pivot = arr[pivotIndex];
            arr[pivotIndex] = arr[left];
            arr[left] = pivot;
            int ogLeft = left;
            int ogRight = right;
            left++;
            right--;
            while (left <= right) {
                while (left <= right && comparator.compare(arr[left], pivot)
                        <= 0) {
                    left++;
                }
                while (left <= right && comparator.compare(arr[right], pivot)
                        >= 0) {
                    right--;
                }
                if (left <= right) {
                    T temp = arr[left];
                    arr[left] = arr[right];
                    arr[right] = temp;
                    left++;
                    right--;
                }
            }
            arr[ogLeft] = arr[right];
            arr[right] = pivot;
            if (ogLeft < right && right < ogRight - 1) {
                quickSort(arr, comparator, rand, ogLeft, right);
                quickSort(arr, comparator, rand, right + 1, ogRight);
            }
        }
    }

    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Inputs are null");
        }

        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<Integer>();
        }

        int max = Math.abs(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            if (Math.abs(arr[i]) > max) {
                max = Math.abs(arr[i]);
            }
        }
        int iterations = 0;
        while (max != 0) {
            iterations++;
            max = max / 10;
        }

        int divide = 1;
        for (int i = 1; i <= iterations; i++) {
            for (int j = 0; j <= arr.length - 1; j++) {
                int bucket = (arr[j] / divide) % 10 + 9;
                buckets[bucket].add(arr[j]);
            }
            int index = 0;
            for (int bucket = 0; bucket < 19; bucket++) {
                while (!buckets[bucket].isEmpty()) {
                    arr[index] = buckets[bucket].remove();
                    index++;
                }
            }
            divide = divide * 10;
        }
    }
}
