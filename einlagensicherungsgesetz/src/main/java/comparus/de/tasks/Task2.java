package comparus.de.tasks;

import com.opencsv.CSVReader;
import comparus.de.domen.CVSClient;
import org.apache.commons.lang3.StringUtils;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


import static comparus.de.CSVReaderMain.*;
import static comparus.de.util.Util.createNumValue;
import static comparus.de.util.Util.decimalToString;

/**
 * Created by ekaterina on 9/26/17.
 */
public class Task2 {
    public static Map<String,CVSClient> readCSVFileByStringSimple(String file) throws IOException {

        Map<String,CVSClient> clients = new LinkedHashMap<>();

        CSVReader reader = new CSVReader(new FileReader(file), '\n', '\'');
        List<String[]> myEntries = reader.readAll();
        if(A == null) {
            A = myEntries.get(0)[0].split("\\*");
        } else {
            AFile2 = myEntries.get(0)[0].split("\\*");
        }

        if(myEntries.size() > 0) {
            for(int k = myEntries.size()-1; k > myEntries.size() - 10; k-- ) {
                if((myEntries.get(k)[0]).startsWith("E")) {
                    E = myEntries.get(k)[0].split("\\*");
                    break;
                }
            }
            int j = 1;
            for (int i = j ;i<myEntries.size(); i++) {
                String currentEllement = myEntries.get(i)[0];
                if(currentEllement.startsWith("B")) {
                    String[] bValue = currentEllement.split("\\*");

                    // UNIQUE KEY - B3 - Last Name, B4 - First Name and B12 - Birthday
                    String key = bValue[2] + bValue[3] + bValue[11];

                    List<String[]> CValues = new LinkedList<>();
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
                    clients.put(key, new CVSClient(bValue, CValues, DValue));
                }
            }
        }
        return clients;
    }

    public static List<String> generateMetadata(String[] E, Map<String, CVSClient> fullFile) throws IOException {
        List<String> metadata = new ArrayList<>(147);
        metadata.add("M");
        metadata.add(A[1]);
        Date date = new Date();
        String currentDate = new SimpleDateFormat("yyMMdd").format(date);
        metadata.add(currentDate);

        for (int i = 4; i < 17; i++) {
            metadata.add(E[i - 2]);
        }

        for (int i = 17; i < 67; i++) {

            BigDecimal sumD = BigDecimal.ZERO;
            for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
                String[] B = entry.getValue().getClientB();
                if (B[13].substring(i-17, i-16).equals("Y")) {
                    sumD =  sumD.add(createNumValue(entry.getValue().getD()[2]));
                }
            }
            metadata.add(decimalToString(sumD));
        }
        for (int i = 67; i < 117; i++) {

            BigDecimal sumC = BigDecimal.ZERO;
            for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
                List<String[]> C = entry.getValue().getClientsC();
                for (String[] el : C) {
                    if (el[20].substring(i-67, i-66).equals("Y")) {
                        sumC = sumC.add( createNumValue(el[19]));
                    }
                }
            }
            metadata.add(decimalToString(sumC));
        }

        BigDecimal sum118 = BigDecimal.ZERO;
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
            List<String[]> C = entry.getValue().getClientsC();
            for (String[] el : C) {
                if (el[21].contains("BE")) {
                    sum118 = sum118.add(createNumValue(el[19]));
                }
            }
        }
        if(!sum118.equals(BigDecimal.ZERO)) {
            metadata.add(decimalToString(sum118));
        } else {
            metadata.add("");
        }

        for (int i = 118; i < 146; i++) {
            metadata.add("");
        }

        BigDecimal sum146 = BigDecimal.ZERO;
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
            List<String[]> C = entry.getValue().getClientsC();
            for (String[] el : C) {
                if (el[21].contains("CY")) {
                    sum146 = sum146.add(createNumValue(el[19]));
                }
            }
        }
        if(!sum146.equals(BigDecimal.ZERO)) {
            metadata.add(decimalToString(sum146));
        } else {
            metadata.add("");
        }

        return metadata;
    }

    public static List<String[]> generateMetadataArray(List<String> metadataList) throws IOException {
        List<String[]> result = new ArrayList<>();
        result.add(metadataList.toArray(new String[]{}));
        return result;
    }

}
