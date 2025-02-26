package ed.lab;

public class Main {
    private static final ArrayGenerator<String> sortedArrayGenerator = (size) -> {
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = String.format("%05d", i); // Convertir números a strings con formato fijo
        }
        return array;
    };

    private static final ArrayGenerator<String> invertedArrayGenerator = (size) -> {
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = String.format("%05d", size - i - 1);
        }
        return array;
    };

    private static final ArrayGenerator<String> randomArrayGenerator = (size) -> {
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = String.format("%05d", (int) (Math.random() * size));
        }
        return array;
    };

    static class SortingAlgorithms {
        public static void sortWithHighPivot(String[] array) {
            quickSort(array, 0, array.length - 1, "high");
        }

        public static void sortWithLowPivot(String[] array) {
            quickSort(array, 0, array.length - 1, "low");
        }

        public static void sortWithRandomPivot(String[] array) {
            quickSort(array, 0, array.length - 1, "random");
        }

        private static void quickSort(String[] array, int low, int high, String pivotType) {
            if (low < high) {
                int pi = partition(array, low, high, pivotType);
                quickSort(array, low, pi - 1, pivotType);
                quickSort(array, pi + 1, high, pivotType);
            }
        }

        private static int partition(String[] array, int low, int high, String pivotType) {
            int pivotIndex = switch (pivotType) {
                case "low" -> low;
                case "random" -> low + (int) (Math.random() * (high - low + 1));
                default -> high;
            };

            String pivot = array[pivotIndex];
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

        private static void swap(String[] array, int i, int j) {
            if (i != j) {
                String temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
    }

    private static final QuickSort<String> highPivotQuickSort = SortingAlgorithms::sortWithHighPivot;
    private static final QuickSort<String> lowPivotQuickSort = SortingAlgorithms::sortWithLowPivot;
    private static final QuickSort<String> randomPivotQuickSort = SortingAlgorithms::sortWithRandomPivot;

    public static QuickSort<String> getHighPivotQuickSort() {
        return highPivotQuickSort;
    }

    public static QuickSort<String> getLowPivotQuickSort() {
        return lowPivotQuickSort;
    }

    public static QuickSort<String> getRandomPivotQuickSort() {
        return randomPivotQuickSort;
    }

    public static ArrayGenerator<String> getSortedArrayGenerator() {
        return sortedArrayGenerator;
    }

    public static ArrayGenerator<String> getInvertedArrayGenerator() {
        return invertedArrayGenerator;
    }

    public static ArrayGenerator<String> getRandomArrayGenerator() {
        return randomArrayGenerator;
    }

    public static void main(String[] args) {
        final SortingTester<String> tester = new SortingTester<>();

        System.out.println("Ordenando un arreglo ordenado:");
        System.out.println("\tUtilizando el último elemento como pivote: ");
        measureTime(sortedArrayGenerator, highPivotQuickSort);
        System.out.println("\tUtilizando el primer elemento como pivote: ");
        measureTime(sortedArrayGenerator, lowPivotQuickSort);
        System.out.println("\tUtilizando un elemento aleatorio como pivote: ");
        measureTime(sortedArrayGenerator, randomPivotQuickSort);
        System.out.println("================================");

        System.out.println("Ordenando un arreglo invertido:");
        System.out.println("\tUtilizando el último elemento como pivote: ");
        measureTime(invertedArrayGenerator, highPivotQuickSort);
        System.out.println("\tUtilizando el primer elemento como pivote: ");
        measureTime(invertedArrayGenerator, lowPivotQuickSort);
        System.out.println("\tUtilizando un elemento aleatorio como pivote: ");
        measureTime(invertedArrayGenerator, randomPivotQuickSort);
        System.out.println("================================");

        System.out.println("Ordenando un arreglo aleatorio:");
        System.out.println("\tUtilizando el último elemento como pivote: ");
        measureTime(randomArrayGenerator, highPivotQuickSort);
        System.out.println("\tUtilizando el primer elemento como pivote: ");
        measureTime(randomArrayGenerator, lowPivotQuickSort);
        System.out.println("\tUtilizando un elemento aleatorio como pivote: ");
        measureTime(randomArrayGenerator, randomPivotQuickSort);
        System.out.println("================================");
    }

    private static void measureTime(ArrayGenerator<String> arrayGenerator, QuickSort<String> quickSort) {
        int size = 1000;
        String[] array = arrayGenerator.generate(size);

        long startTime = System.nanoTime();
        quickSort.sort(array);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1_000_000;
        System.out.println("\tTiempo de ejecución: " + duration + " ms");
    }
}
