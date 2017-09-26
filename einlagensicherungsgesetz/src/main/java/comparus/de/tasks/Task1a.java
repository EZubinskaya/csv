package comparus.de.tasks;

import com.opencsv.CSVReader;
import comparus.de.domen.Additional_CRecord_Data_Task1a;
import comparus.de.domen.CVSClient;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**
 * Created by ekaterina on 9/26/17.
 */
public class Task1a {
    public static Map<String,Additional_CRecord_Data_Task1a> readExtraAdditionalDataCTask1a(String additional_cRecord_data_task1a) throws IOException {
        Map<String,Additional_CRecord_Data_Task1a> C = new LinkedHashMap<>();
        CSVReader reader = new CSVReader(new FileReader(additional_cRecord_data_task1a), '\n', '\'');
        List<String[]> myEntries = reader.readAll();
        for(int i = 1; i < myEntries.size(); i++) {
            String[] curEl = myEntries.get(i)[0].split(";", -1);
            String key = curEl[1];
            Additional_CRecord_Data_Task1a value = new Additional_CRecord_Data_Task1a(curEl[0], curEl[1], curEl[3], curEl[4], curEl[5], curEl[6], curEl[7],
                    curEl[8], curEl[9], curEl[10], curEl[11], curEl[12], curEl[13], curEl[14], curEl[15], curEl[16], curEl[17], curEl[18], curEl[19], curEl[20], curEl[21],
                    curEl[22], curEl[23], curEl[24], curEl[25], curEl[26], curEl[27], curEl[28], curEl[29], curEl[30]);
            C.put(key, value);
        }
        return C;
    }

    public static Map<String,CVSClient> generateVersion4AdditionalC(Map<String, CVSClient> fullFile, Map<String, Additional_CRecord_Data_Task1a> C_Additional) {

        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
            String key = entry.getKey();
            CVSClient value = entry.getValue();
            String[] B = value.getClientB();
            String clientB = B[1];
            if(C_Additional.get(clientB) != null) {
                List<String[]> clientC = value.getClientsC();
                clientC.add(C_Additional.get(clientB).toArray());
            }
        }
        return fullFile;
    }

    public static List<String[]> generateListOfDataArray(String[] a, Map<String, CVSClient> fullFile, String[] e) {
        // fullFile.
        List<String[]> fullData = new ArrayList<String[]>();
        fullData.add(a);
        for (CVSClient entry : getSortedListByB1C2A(fullFile)){
            fullData.add(entry.getClientB());
            fullData.addAll(entry.getClientsC());
            fullData.add(entry.getD());
        }
        fullData.add(e);
        return fullData;
    }

    static List<CVSClient> getSortedListByB1C2A (Map<String, CVSClient> data) {
        List<CVSClient> sortedData = new ArrayList<>();
        for (Map.Entry<String, CVSClient> entry : data.entrySet()){
            Collections.sort(entry.getValue().getClientsC(),
                    (new Comparator<String[]>() {
                        @Override
                        public int compare(String[] c1, String[] c2) {
                            return c1[1].compareTo(c2[1]);
                        }
                    }));
            sortedData.add(entry.getValue());
        }
        Collections.sort(sortedData, (new Comparator<CVSClient>() {
            @Override
            public int compare(CVSClient b1, CVSClient b2) {
                return b1.getClientB()[1].compareTo(b2.getClientB()[1]);
            }
        }));
        return  sortedData;
    }
}