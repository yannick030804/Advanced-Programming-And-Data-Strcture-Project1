import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static boolean isValidOption(String option) {
        try {
            int numOption = Integer.parseInt(option);
            return numOption >= 1 && numOption <= 4;
        } catch (NumberFormatException e) {
            System.out.println("\nInvalid option\n");
            return false;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<>();
        String option, line;
        int flag = 0, n = 0, r = 0, l = 0, count = 0;
        Scanner scanner = new Scanner(System.in);

        Scanner taskF = new Scanner(new File("files/sigma.paed"));

        if (taskF.hasNextLine()) {
            n = Integer.parseInt(taskF.nextLine().trim());
        }

        for (int i = 0; i < n; i++) {
            line = taskF.nextLine();
            Task task = new Task(line);
            tasks.add(task);
        }
        taskF.close();

        do {
            System.out.println("Choose the format:");
            System.out.println("\t1. ascending");
            System.out.println("\t2. descending");

            System.out.print("Enter an option: ");
            option = scanner.nextLine();
        } while (!isValidOption(option));

        switch (option) {
            case "1":
                flag = 1;
                break;
            case "2":
                flag = 2;
                break;
        }

        do {
            System.out.println("\nNow choose if you want to use MergeSort or QuickSort:");
            System.out.println("\t1. MergeSort");
            System.out.println("\t2. QuickSort");
            System.out.println("\t3. Insertion Sort");
            System.out.println("\t4. Selection Sort");

            System.out.print("Enter an option: ");
            option = scanner.nextLine();
        } while (!isValidOption(option));

        switch (option) {
            case "1":
                System.out.println();
                r = tasks.size() - 1;

                long startTimeMergeSort = System.nanoTime();
                mergeSort(tasks, 0, r, flag);
                long endTimeMergeSort = System.nanoTime();

                //System.out.println("MergeSort ejecutado en: " + (endTimeMergeSort - startTimeMergeSort) + " nanosegundos");
                break;

            case "2":
                System.out.println();
                r = tasks.size() - 1;

                long startTimeQuickSort = System.nanoTime();
                quickSort(tasks, 0, r, flag);
                long endTimeQuickSort = System.nanoTime();

                //System.out.println("QuickSort ejecutado en: " + (endTimeQuickSort - startTimeQuickSort) + " nanosegundos");
                break;

            case "3":
                System.out.println();

                long startTimeInsertionSort = System.nanoTime();
                insertionSort(tasks, flag);
                long endTimeInsertionSort = System.nanoTime();

                //System.out.println("InsertionSort ejecutado en: " + (endTimeInsertionSort - startTimeInsertionSort) + " nanosegundos");
                break;

            case "4":
                System.out.println();

                long startTimeSelectionSort = System.nanoTime();
                selectionSort(tasks, flag);
                long endTimeSelectionSort = System.nanoTime();

                //System.out.println("SelectionSort ejecutado en: " + (endTimeSelectionSort - startTimeSelectionSort) + " nanosegundos");
                break;
        }


        scanner.close();

        for(Task task : tasks){
            System.out.println(task);
        }

    }

    //MergeSort
    private static void mergeSort(ArrayList<Task> tasks, int left, int right, int flag) {
        if (left >= right) {
            return;
        }

        int mid = (left + right) / 2;

        mergeSort(tasks, left, mid, flag);
        mergeSort(tasks, mid + 1, right, flag);
        merge(tasks, left, mid, right, flag);
    }

    //Lo primero que verificamos es el nombre de la tarea (por orden alfabético), lo segundo es la dificultad de la tarea y por último el tiempo que se necesita para realizar la tarea
    private static void merge(ArrayList<Task> tasks, int left, int mid, int right, int flag) {
        ArrayList<Task> aux = new ArrayList<>(tasks);

        int leftIndex = left;
        int rightIndex = mid + 1;
        int cursor = left;

        while (leftIndex <= mid && rightIndex <= right) {
            if (flag == 1) {
                if (tasks.get(leftIndex).getName().compareToIgnoreCase(tasks.get(rightIndex).getName()) < 0 ||
                        (tasks.get(leftIndex).getName().compareToIgnoreCase(tasks.get(rightIndex).getName()) == 0 && tasks.get(leftIndex).getDifficulty() < tasks.get(rightIndex).getDifficulty()) ||
                        (tasks.get(leftIndex).getName().compareToIgnoreCase(tasks.get(rightIndex).getName()) == 0 && tasks.get(leftIndex).getDifficulty() == tasks.get(rightIndex).getDifficulty() && tasks.get(leftIndex).getTime() < tasks.get(rightIndex).getTime())) {
                    aux.set(cursor, tasks.get(leftIndex));
                    leftIndex++;
                } else {
                    aux.set(cursor, tasks.get(rightIndex));
                    rightIndex++;
                }
            } else if (flag == 2) {
                if (tasks.get(leftIndex).getName().compareToIgnoreCase(tasks.get(rightIndex).getName()) > 0 ||
                        (tasks.get(leftIndex).getName().compareToIgnoreCase(tasks.get(rightIndex).getName()) == 0 && tasks.get(leftIndex).getDifficulty() > tasks.get(rightIndex).getDifficulty()) ||
                        (tasks.get(leftIndex).getName().compareToIgnoreCase(tasks.get(rightIndex).getName()) == 0 && tasks.get(leftIndex).getDifficulty() == tasks.get(rightIndex).getDifficulty() && tasks.get(leftIndex).getTime() > tasks.get(rightIndex).getTime())) {
                    aux.set(cursor, tasks.get(leftIndex));
                    leftIndex++;
                } else {
                    aux.set(cursor, tasks.get(rightIndex));
                    rightIndex++;
                }
            }
            cursor++;
        }

        while (leftIndex <= mid) {
            aux.set(cursor, tasks.get(leftIndex));
            leftIndex++;
            cursor++;
        }

        while (rightIndex <= right) {
            aux.set(cursor, tasks.get(rightIndex));
            rightIndex++;
            cursor++;
        }

        for (int j = left; j <= right; j++) {
            tasks.set(j, aux.get(j));
        }
    }

    //QuickSort
    private static void quickSort(ArrayList<Task> tasks, int left, int right, int flag) {
        while (left < right) {
            int p = partition(tasks, left, right, flag);

            // Procesar la partición más pequeña recursivamente
            if (p - left < right - p) {
                quickSort(tasks, left, p, flag); // Llamada recursiva para la izquierda
                left = p + 1; // Iterativo para la derecha
            } else {
                quickSort(tasks, p + 1, right, flag); // Llamada recursiva para la derecha
                right = p; // Iterativo para la izquierda
            }
        }
    }

    //Lo primero que verificamos es el nombre de la tarea (por orden alfabético), lo segundo es la dificultad de la tarea y por último el tiempo que se necesita para realizar la tarea
    private static int partition(ArrayList<Task> tasks, int left, int right, int flag) {
        Task pivot = tasks.get(left + (right - left) / 2);
        int leftIndex = left;
        int rightIndex = right;

        while (leftIndex <= rightIndex) {
            if (flag == 1) {
                while (tasks.get(leftIndex).getName().compareToIgnoreCase(pivot.getName()) < 0 ||
                        (tasks.get(leftIndex).getName().compareToIgnoreCase(pivot.getName()) == 0 && (tasks.get(leftIndex).getDifficulty() > pivot.getDifficulty() ||
                                (tasks.get(leftIndex).getName().compareToIgnoreCase(pivot.getName()) == 0 && (tasks.get(leftIndex).getDifficulty() == pivot.getDifficulty() && tasks.get(leftIndex).getTime() < pivot.getTime()))))) {
                    leftIndex++;
                }

                while (tasks.get(rightIndex).getName().compareToIgnoreCase(pivot.getName()) > 0 ||
                        (tasks.get(rightIndex).getName().compareToIgnoreCase(pivot.getName()) == 0 && (tasks.get(rightIndex).getDifficulty() > pivot.getDifficulty() ||
                                (tasks.get(rightIndex).getName().compareToIgnoreCase(pivot.getName()) == 0 && (tasks.get(rightIndex).getDifficulty() == pivot.getDifficulty() && tasks.get(rightIndex).getTime() > pivot.getTime()))))) {
                    rightIndex--;
                }
            } else if (flag == 2) {
                while (tasks.get(leftIndex).getName().compareToIgnoreCase(pivot.getName()) > 0 ||
                        (tasks.get(leftIndex).getName().compareToIgnoreCase(pivot.getName()) == 0 && (tasks.get(leftIndex).getDifficulty() < pivot.getDifficulty() ||
                                (tasks.get(leftIndex).getName().compareToIgnoreCase(pivot.getName()) == 0 && (tasks.get(leftIndex).getDifficulty() == pivot.getDifficulty() && tasks.get(leftIndex).getTime() > pivot.getTime()))))) {
                    leftIndex++;
                }
                while (tasks.get(rightIndex).getName().compareToIgnoreCase(pivot.getName()) < 0 ||
                        (tasks.get(rightIndex).getName().compareToIgnoreCase(pivot.getName()) == 0 && (tasks.get(rightIndex).getDifficulty() < pivot.getDifficulty() ||
                                (tasks.get(rightIndex).getName().compareToIgnoreCase(pivot.getName()) == 0 && (tasks.get(rightIndex).getDifficulty() == pivot.getDifficulty() && tasks.get(rightIndex).getTime() < pivot.getTime()))))) {
                    rightIndex--;
                }
            }

            if (leftIndex < rightIndex) {
                Task aux = tasks.get(leftIndex);
                tasks.set(leftIndex, tasks.get(rightIndex));
                tasks.set(rightIndex, aux);

                leftIndex++;
                rightIndex--;
            } else if (leftIndex == rightIndex) {
                leftIndex++;
            }
        }
        return rightIndex;
    }

    //Insertion Sort, ordenamos por nombre
    private static void insertionSort(ArrayList<Task> tasks, int flag) {
        int n = tasks.size();
        int j = 0;
        Task aux;

        if (flag == 1) {
            for (int i = 1; i < n; i++) {
                aux = tasks.get(i);
                j = i - 1;

                while (j >= 0 && tasks.get(j).getName().compareTo(aux.getName()) > 0) {
                    tasks.set(j + 1, tasks.get(j));
                    j = j - 1;
                }
                tasks.set(j + 1, aux);
            }
        }
        if (flag == 2) {
            for (int i = 1; i < n; i++) {
                aux = tasks.get(i);
                j = i - 1;

                while (j >= 0 && tasks.get(j).getName().compareTo(aux.getName()) < 0) {
                    tasks.set(j + 1, tasks.get(j));
                    j = j - 1;
                }
            }
        }

    }

    //Selection Sort, ordenamos por nombre
    private static void selectionSort(ArrayList<Task> tasks, int flag) {
        int n = tasks.size();
        int minIn = 0;

        if (flag == 1) {
            for (int i = 0; i < n; i++) {
                minIn = i;
                for (int j = i + 1; j < n; j++) {
                    if (tasks.get(j).getName().compareTo(tasks.get(minIn).getName()) < 0) {
                        minIn = j;
                    }
                }

                Task aux = tasks.get(i);
                tasks.set(i, tasks.get(minIn));
                tasks.set(minIn, aux);
            }
        }
        if (flag == 2) {
            for (int i = 0; i < n; i++) {
                minIn = i;
                for (int j = i + 1; j < n; j++) {
                    if (tasks.get(j).getName().compareTo(tasks.get(minIn).getName()) > 0) {
                        minIn = j;
                    }
                }
                Task aux = tasks.get(i);
                tasks.set(i, tasks.get(minIn));
                tasks.set(minIn, aux);
            }
        }
    }
}