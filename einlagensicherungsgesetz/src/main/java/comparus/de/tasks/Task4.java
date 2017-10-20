package comparus.de.tasks;

import comparus.de.domen.CVSClient;
import comparus.de.domen.HW;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static comparus.de.CSVReaderMain.protocol;
import static comparus.de.protocols.ProtocolForTask1.sameBList;
import static comparus.de.tasks.Task1.*;
import static comparus.de.tasks.Task1.calculateD14;
import static comparus.de.tasks.Task3.*;
import static comparus.de.util.Util.createNumValue;

/**
 * Created by ekaterina on 9/26/17.
 */
public class Task4 {
    public static void calculateDversion5(Map<String, CVSClient> fullFile, String[] A, boolean writeToProtocol) {
        List<String> sameBList = new ArrayList<>();
        if(writeToProtocol) {
            sameBList = sameBList(protocol.getKeyFile1ToFile2());
        }
        List<String>  recalculatedDRecords = new ArrayList<>();
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()){
            HW hw = new HW();
            String[] clientB = entry.getValue().getClientB();
            CVSClient cvsClient = entry.getValue();
            List<String[]> CList = cvsClient.getClientsC();
            String[] D = cvsClient.getD();

            String[] tempD = new String[18];

            if(writeToProtocol && sameBList.contains(StringUtils.join(clientB, "*"))) {
                D[2] = calculateD3(CList);

                D[3] = calculateD4Version5_1(clientB, CList, createNumValue(D[2]));
                tempD[3] = D[3];

                D[3] = calculateD4(clientB, CList, createNumValue(D[2]));
                D[4] = calculateD5D7(createNumValue(D[2]), createNumValue(D[3]));
                D[5] = calculateD6(createNumValue(A[3]), createNumValue(D[4]), CList);
                D[6] = calculateD5D7(createNumValue(D[4]), createNumValue(D[5]));
                D[7] = "0,00";
                D[8] = "0,00";

                System.arraycopy(D, 0, tempD, 0, D.length);

                D[9] = calculateD10Version5_1(CList);
                tempD[9] = D[9];

                D[10] = calculateD11Version5_1(CList);
                tempD[10] = D[10];

                BigDecimal D14A = calculateD14AVersion5_1(CList);
                tempD[15] = String.valueOf(D14A);

                BigDecimal D12A = calculateD12AVersion5_1(A[5], tempD, CList);
                tempD[11] = String.valueOf(D12A);

                BigDecimal D12B = BigDecimalcalculateD12B(tempD, A, CList, hw);
                tempD[12] = String.valueOf(D12B);

                BigDecimal D12C = BigDecimalcalculateD12C(tempD, A, CList, hw);
                tempD[13] = String.valueOf(D12C);

                BigDecimal D13 = calculateD13Version5_1(D12A, D12B, D12C);
                tempD[14] = String.valueOf(D13);

                BigDecimal D14B = BigDecimalcalculateD14B(tempD);
                tempD[16] = String.valueOf(D14B);
                recalculatedDRecords.add(StringUtils.join(D, "*"));
            }
        }
        if(writeToProtocol) {
            protocol.setRecalculatedDRecords(recalculatedDRecords);
        }
    }

}
