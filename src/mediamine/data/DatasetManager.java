package mediamine.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mediamine.model.MediaItem;

public class DatasetManager {

    private final String datasetPath;

    public DatasetManager(String datasetPath) {
        this.datasetPath = datasetPath;
    }

    public List<String[]> loadProducts() {
        return CSVReader.readCSV(
                datasetPath + "/products.csv"
        );
    }

    public List<String[]> loadOrders() {
        return CSVReader.readCSV(
                datasetPath + "/orders.csv"
        );
    }

    public List<String[]> loadAisles() {
        return CSVReader.readCSV(
                datasetPath + "/aisles.csv"
        );
    }

    public List<String[]> loadDepartments() {
        return CSVReader.readCSV(
                datasetPath + "/departments.csv"
        );
    }

    public List<String[]> loadPriorInteractions() {
        return CSVReader.readCSV(
                datasetPath + "/order_products__prior.csv"
        );
    }

    public List<String[]> loadTrainInteractions() {
        return CSVReader.readCSV(
                datasetPath + "/order_products__train.csv"
        );
    }

    /*
     * Converts the Kaggle products dataset into
     * MediaMine MediaItem objects.
     */
    public List<MediaItem> loadMediaItems() {

        List<MediaItem> mediaItems = new ArrayList<>();

        List<String[]> products = loadProducts();
        List<String[]> departments = loadDepartments();

        // department_id -> department_name
        Map<String, String> departmentMap = new HashMap<>();

        for (int i = 1; i < departments.size(); i++) {

            String[] row = departments.get(i);

            if (row.length >= 2) {
                String departmentId = row[0];
                String departmentName = row[1];

                departmentMap.put(
                        departmentId,
                        departmentName
                );
            }
        }

        // Convert products into MediaItem objects
        for (int i = 1; i < products.size(); i++) {

            String[] row = products.get(i);

            if (row.length >= 4) {

                String productId = row[0];
                String productName = row[1];
                String departmentId = row[3];

                String departmentName =
                        departmentMap.getOrDefault(
                                departmentId,
                                "Unknown"
                        );

                MediaItem item = new MediaItem(
                        productId,
                        productName,
                        productName,
                        departmentName,
                        "Instacart Dataset"
                );

                mediaItems.add(item);
            }
        }

        return mediaItems;
    }
}