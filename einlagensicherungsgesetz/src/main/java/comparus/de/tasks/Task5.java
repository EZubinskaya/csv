package comparus.de.tasks;

import comparus.de.domen.CVSClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static comparus.de.CSVReaderMain.A;
import static comparus.de.util.Util.createNumValue;
import static comparus.de.util.Util.decimalToString;

/**
 * Created by ekaterina on 10/19/17.
 */
public class Task5 {

    public static List<String> generateMetadataTask5(String[] E, Map<String, CVSClient> fullFile) throws IOException {
        List<String> metadata = new ArrayList<>(149);
        metadata.add("M");
        metadata.add(A[1]);
        Date date = new Date();
        String currentDate = new SimpleDateFormat("yyMMdd").format(date);
        metadata.add(currentDate);

        for (int i = 4; i < 19; i++) {
            metadata.add(E[i - 2]);
        }

        for (int i = 19; i < 69; i++) {

            BigDecimal sumD = BigDecimal.ZERO;
            for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
                String[] B = entry.getValue().getClientB();
                if (B[13].substring(i-19, i-18).equals("Y")) {
                    sumD =  sumD.add(createNumValue(entry.getValue().getD()[2]));
                }
            }
            metadata.add(decimalToString(sumD));
        }
        for (int i = 69; i < 119; i++) {

            BigDecimal sumC = BigDecimal.ZERO;
            for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
                List<String[]> C = entry.getValue().getClientsC();
                for (String[] el : C) {
                    if (el[20].substring(i-69, i-68).equals("Y")) {
                        sumC = sumC.add( createNumValue(el[19]));
                    }
                }
            }
            metadata.add(decimalToString(sumC));
        }

        BigDecimal sum120 = BigDecimal.ZERO;
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
            List<String[]> C = entry.getValue().getClientsC();
            for (String[] el : C) {
                if (el[21].contains("BE")) {
                    sum120 = sum120.add(createNumValue(el[19]));
                }
            }
        }
        if(!sum120.equals(BigDecimal.ZERO)) {
            metadata.add(decimalToString(sum120));
        } else {
            metadata.add("");
        }

        for (int i = 120; i < 148; i++) {
            metadata.add("");
        }

        BigDecimal sum148 = BigDecimal.ZERO;
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
            List<String[]> C = entry.getValue().getClientsC();
            for (String[] el : C) {
                if (el[21].contains("CY")) {
                    sum148 = sum148.add(createNumValue(el[19]));
                }
            }
        }
        if(!sum148.equals(BigDecimal.ZERO)) {
            metadata.add(decimalToString(sum148));
        } else {
            metadata.add("");
        }

        return metadata;
    }


}
