package comparus.de;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ekaterina on 8/31/17.
 */
public class CSVReaderMain1 {
    public static void main(String[] args) throws IOException {

        String file1 = "/Users/ekaterina/Desktop/einlagensicherungsgesetz/src/main/resources/Pseudodatei aufbereitet B11.csv";
        String file2 = "/Users/ekaterina/Desktop/einlagensicherungsgesetz/src/main/resources/example.csv";
        Map<String,CVSClient> readFile1 = readCSVFileByString(file1);
        Map<String,CVSClient> readFile2 = readCSVFileByString(file2);

        Map<String,CVSClient> fullFile = generateMapOfData(readFile1, readFile2);
        System.out.println(fullFile);
    }

    private static Map<String,CVSClient> readCSVFileByString(String file) throws IOException {

        Map<String,CVSClient> clients = new HashMap<String, CVSClient>();
        CSVReader reader = new CSVReader(new FileReader(file), '\t', '\'');
        List<String[]> myEntries = reader.readAll();

        int j = 0;
        for (int i = j ;i<myEntries.size(); i++) {
            String currentEllement = myEntries.get(i)[0];
            if(currentEllement.startsWith("B")) {
                String key = currentEllement;
                List<String[]> CValues = new ArrayList<String[]>();
                String[] DValue = null;
                j = i+1;
                while (myEntries.get(j)[0].startsWith("C")) {
                    CValues.add(myEntries.get(j)[0].split("\\*"));
                    j++;
                }
                if(myEntries.get(j)[0].startsWith("D")) {
                    DValue = myEntries.get(j)[0].split("\\*");
                    j++;
                }
                clients.put(key, new CVSClient(CValues, DValue));
            }
        }
        return clients;
    }

    private static Map<String,CVSClient> generateMapOfData(Map<String, CVSClient> readFile1, Map<String, CVSClient> readFile2) {
        Map<String,CVSClient> resultMap = new HashMap<String, CVSClient>();
        for (Map.Entry<String, CVSClient> entry : readFile1.entrySet()){
            if (readFile2.get(entry.getKey()) != null) {
                List<String[]> clients = new ArrayList<String[]>();
                clients.addAll(entry.getValue().getClientsB());
                clients.addAll(readFile2.get(entry.getKey()).getClientsB());
                resultMap.put(entry.getKey(), new CVSClient(clients, entry.getValue().getD()));
                readFile2.remove(entry.getKey());
            } else {
                resultMap.put(entry.getKey(), entry.getValue());
            }
        }
        resultMap.putAll(readFile2);
        return resultMap;
    }
}
