package mediamine;

import java.util.List;
import java.util.Scanner;

import mediamine.data.DatasetManager;
import mediamine.m1_strings.StringModuleDemo;
import mediamine.m2_suffix.SuffixModuleDemo;
import mediamine.m3_dp.DPModuleDemo;
import mediamine.m4_flow.FlowModuleDemo;
import mediamine.m5_np.NPModuleDemo;
import mediamine.m6_parallel.ParallelModuleDemo;

/**
 * MediaMine - Intelligent Media Analytics Platform.
 *
 * Main entry point for Modules 1-6 and dataset integration.
 */
public class Main {

    public static void main(String[] args) {

        // Run all modules directly using the "all" argument
        if (args.length > 0 && args[0].equalsIgnoreCase("all")) {
            runAll();
            return;
        }

        Scanner in = new Scanner(System.in);

        while (true) {

            System.out.println();
            System.out.println("================ MediaMine ================");
            System.out.println("1. Module 1 - String Algorithms");
            System.out.println("2. Module 2 - Suffix Structures / Document Similarity");
            System.out.println("3. Module 3 - Advanced Dynamic Programming");
            System.out.println("4. Module 4 - Network Flow");
            System.out.println("5. Module 5 - NP-Completeness & Approximation");
            System.out.println("6. Module 6 - Randomized & Parallel Algorithms");
            System.out.println("7. Dataset Information");
            System.out.println("8. Run ALL Modules");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            if (!in.hasNextLine()) {
                break;
            }

            String choice = in.nextLine().trim();

            switch (choice) {

                case "1":
                    StringModuleDemo.run();
                    break;

                case "2":
                    SuffixModuleDemo.run();
                    break;

                case "3":
                    DPModuleDemo.run();
                    break;

                case "4":
                    FlowModuleDemo.run();
                    break;

                case "5":
                    NPModuleDemo.run();
                    break;

                case "6":
                    ParallelModuleDemo.run();
                    break;

                case "7":
                    showDatasetInformation();
                    break;

                case "8":
                    runAll();
                    break;

                case "0":
                    System.out.println("Exiting MediaMine...");
                    in.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please select 0-8.");
            }
        }

        in.close();
    }

    /**
     * Runs Modules 1-6 sequentially.
     */
    private static void runAll() {

        System.out.println();
        System.out.println("==================================================");
        System.out.println("          MEDIAMINE - FULL SYSTEM RUN");
        System.out.println("==================================================");

        StringModuleDemo.run();
        SuffixModuleDemo.run();
        DPModuleDemo.run();
        FlowModuleDemo.run();
        NPModuleDemo.run();
        ParallelModuleDemo.run();

        System.out.println();
        System.out.println("==================================================");
        System.out.println("        ALL MEDIAMINE MODULES COMPLETED");
        System.out.println("==================================================");
    }

    /**
     * Displays information about the integrated Instacart dataset.
     */
    private static void showDatasetInformation() {

        System.out.println();
        System.out.println("=========== MEDIAMINE DATASET ===========");

        try {

            DatasetManager manager =
                    new DatasetManager("dataset");

            List<String[]> products =
                    manager.loadProducts();

            List<String[]> departments =
                    manager.loadDepartments();

            List<String[]> aisles =
                    manager.loadAisles();

            // CSV files include a header row.
            int productCount =
                    Math.max(0, products.size() - 1);

            int departmentCount =
                    Math.max(0, departments.size() - 1);

            int aisleCount =
                    Math.max(0, aisles.size() - 1);

            System.out.println(
                    "Products loaded    : " + productCount
            );

            System.out.println(
                    "Departments loaded : " + departmentCount
            );

            System.out.println(
                    "Aisles loaded      : " + aisleCount
            );

            System.out.println();

            System.out.println(
                    "Dataset source : Instacart Market Basket Analysis"
            );

            System.out.println(
                    "Dataset role   : User-content interaction analytics"
            );

        } catch (Exception e) {

            System.out.println(
                    "Unable to load dataset: " + e.getMessage()
            );
        }

        System.out.println("==========================================");
    }
}