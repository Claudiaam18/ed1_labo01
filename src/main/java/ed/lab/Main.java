package ed.lab;

public class Main {
    private static final ArrayGenerator<Integer> sortedArrayGenerator = (size) -> {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    };

    private static final ArrayGenerator<Integer> invertedArrayGenerator = (size) -> {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i - 1;
        }
        return array;
    };

    private static final ArrayGenerator<Integer> randomArrayGenerator = (size) -> {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * size);
        }
        return array;
    };

    private static final QuickSort<Integer> highPivotQuickSort = SortingAlgorithms::sortWithHighPivot;
    private static final QuickSort<Integer> lowPivotQuickSort = SortingAlgorithms::sortWithLowPivot;
    private static final QuickSort<Integer> randomPivotQuickSort = SortingAlgorithms::sortWithRandomPivot;


    public static QuickSort<Integer> getHighPivotQuickSort() {
        return highPivotQuickSort;
    }

    public static QuickSort<Integer> getLowPivotQuickSort() {
        return lowPivotQuickSort;
    }

    public static QuickSort<Integer> getRandomPivotQuickSort() {
        return randomPivotQuickSort;
    }

    public static ArrayGenerator<Integer> getSortedArrayGenerator() {
        return sortedArrayGenerator;
    }

    public static ArrayGenerator<Integer> getInvertedArrayGenerator() {
        return invertedArrayGenerator;
    }

    public static ArrayGenerator<Integer> getRandomArrayGenerator() {
        return randomArrayGenerator;
    }

    public static void main(String[] args) {
        final SortingTester<Integer> tester = new SortingTester<>();

        System.out.println("Ordenando un arreglo ordenado:");
        System.out.println("\tUtilizando el último elemento como pivote: ");
        tester.testSorting(sortedArrayGenerator, highPivotQuickSort);
        System.out.println("\tUtilizando el primer elemento como pivote: ");
        tester.testSorting(sortedArrayGenerator, lowPivotQuickSort);
        System.out.println("\tUtilizando un elemento aleatorio como pivote: ");
        tester.testSorting(sortedArrayGenerator, randomPivotQuickSort);
        System.out.println("================================");

        System.out.println("Ordenando un arreglo invertido:");
        System.out.println("\tUtilizando el último elemento como pivote: ");
        tester.testSorting(invertedArrayGenerator, highPivotQuickSort);
        System.out.println("\tUtilizando el primer elemento como pivote: ");
        tester.testSorting(invertedArrayGenerator, lowPivotQuickSort);
        System.out.println("\tUtilizando un elemento aleatorio como pivote: ");
        tester.testSorting(invertedArrayGenerator, randomPivotQuickSort);
        System.out.println("================================");

        System.out.println("Ordenando un arreglo aleatorio:");
        System.out.println("\tUtilizando el último elemento como pivote: ");
        tester.testSorting(randomArrayGenerator, highPivotQuickSort);
        System.out.println("\tUtilizando el primer elemento como pivote: ");
        tester.testSorting(randomArrayGenerator, lowPivotQuickSort);
        System.out.println("\tUtilizando un elemento aleatorio como pivote: ");
        tester.testSorting(randomArrayGenerator, randomPivotQuickSort);
        System.out.println("================================");
    }
}

class SortingAlgorithms {
    public static <T extends Comparable<T>> void sortWithHighPivot(T[] array) {
        quickSort(array, 0, array.length - 1, "high");
    }

    public static <T extends Comparable<T>> void sortWithLowPivot(T[] array) {
        quickSort(array, 0, array.length - 1, "low");
    }

    public static <T extends Comparable<T>> void sortWithRandomPivot(T[] array) {
        quickSort(array, 0, array.length - 1, "random");
    }

    private static <T extends Comparable<T>> void quickSort(T[] array, int low, int high, String pivotType) {
        if (low < high) {
            int pi = partition(array, low, high, pivotType);
            if (pi > low) quickSort(array, low, pi - 1, pivotType);
            if (pi < high) quickSort(array, pi + 1, high, pivotType);
        }
    }

    private static <T extends Comparable<T>> int partition(T[] array, int low, int high, String pivotType) {
        int pivotIndex = switch (pivotType) {
            case "low" -> low;
            case "random" -> low + (int) (Math.random() * (high - low + 1));
            default -> high;
        };

        T pivot = array[pivotIndex];
        swap(array, pivotIndex, high);
        int i = low;

        for (int j = low; j < high; j++) {
            if (array[j].compareTo(pivot) < 0) {
                swap(array, i, j);
                i++;
            }
        }
        swap(array, i, high);
        return i;
    }

    private static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
