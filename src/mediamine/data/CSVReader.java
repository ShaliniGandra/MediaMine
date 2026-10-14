package mediamine.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static List<String[]> readCSV(String filePath) {

        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = br.readLine()) != null) {

                // Basic CSV handling
                List<String> row = new ArrayList<>();
                StringBuilder value = new StringBuilder();
                boolean insideQuotes = false;

                for (int i = 0; i < line.length(); i++) {

                    char c = line.charAt(i);

                    if (c == '"') {
                        insideQuotes = !insideQuotes;
                    } 
                    else if (c == ',' && !insideQuotes) {
                        row.add(value.toString());
                        value.setLength(0);
                    } 
                    else {
                        value.append(c);
                    }
                }

                row.add(value.toString());

                data.add(row.toArray(new String[0]));
            }

        } catch (IOException e) {

            System.out.println("Error reading CSV:");
            System.out.println(filePath);
            e.printStackTrace();
        }

        return data;
    }
}