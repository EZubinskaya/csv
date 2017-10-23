package comparus.de.protocols;

import comparus.de.domen.Additional_CRecord;
import comparus.de.domen.CVSClient;
import comparus.de.domen.FileInfo;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static comparus.de.CSVReaderMain.protocol;

/**
 * Created by ekaterina on 9/26/17.
 */
public class ProtocolForTask4A {

    public static void  writeProtocolToFileTask4A(String protocolName, String taskNumber, String inputFileForMerge1, String Additional_CRecord_Data_Task1a, String C_Additional_5, String C_Additional_5_2,
                                                  String task1ResultFileName, FileInfo fileInfo1,
                                                  List<String> additional_cRecord_Withought_C_Extra_Data,
                                                  List<String> mergingCRecords,
                                                  String errorMessage,
                                                  int Additional_CRecord_Data_Task1aCount, int C_Additional_5Count_1, int C_Additional_5Count_2) throws FileNotFoundException {

        Writer writer = null;
        try {
            if (inputFileForMerge1 != null && Additional_CRecord_Data_Task1a != null) {
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(protocolName), "utf-8"));
                writer.write("\r\n=============================================================================\r\n");
                writer.write("GENERAL INFORMATIONS\r\n\r\n");

                writer.write("Program name: einlagensicherungsgesetz\r\n");
                writer.write("Date of run: " + new SimpleDateFormat("dd-MM-YYYY HH-mm").format(new Date()) + "\r\n");
                writer.write("Task: " + taskNumber + "\r\n");
                writer.write("Input file 1: " + inputFileForMerge1 + "\r\n");
                writer.write("Input file 2: " + Additional_CRecord_Data_Task1a + "\r\n");
                writer.write("Input file 3: " + C_Additional_5 + "\r\n");

                writer.write("Filename result file: " + task1ResultFileName + "\r\n");

                writer.write("\r\n=============================================================================\r\n");
                writer.write("ERRORS\r\n");
                if (errorMessage != null) {
                    writer.write(errorMessage + "\r\n");
                }

                writer.write("\r\n=============================================================================\r\n");
                writer.write("\r\nAMOUNTS OF DATASETS\r\n\r\n");
                writer.write("Input file name 1: " + inputFileForMerge1 + "\r\n");
                writer.write("B : " + fileInfo1.getBCount() + "\r\n");
                writer.write("C : " + fileInfo1.getCCount() + "\r\n");
                writer.write("D : " + fileInfo1.getDCount() + "\r\n");
                writer.write("Input file name: " + Additional_CRecord_Data_Task1a + "\r\n");
                writer.write("C : " + Additional_CRecord_Data_Task1aCount + "\r\n");
                writer.write("Input file name: " + C_Additional_5 + "\r\n");
                writer.write("C : " + C_Additional_5Count_1 + "\r\n");
                writer.write("Input file name: " + C_Additional_5_2 + "\r\n");
                writer.write("C : " + C_Additional_5Count_2 + "\r\n");
                writer.write("Resultfile\r\n");
                writer.write("B : " + protocol.getResultAmountB() + "\r\n");
                writer.write("C : " + protocol.getResultAmountC() + "\r\n");
                writer.write("D : " + protocol.getResultAmountD() + "\r\n");

                writer.write("\r\n=============================================================================\r\n");
                writer.write("\r\nMERGING\r\n\r\n");
                writer.write("Amount of merged datasets: " + mergingCRecords.size() + "\r\n");
                writer.write("List of created C-Records:" + "\r\n");
                for (String  d : mergingCRecords) {
                    writer.write(d + "\r\n");
                }
                if(!additional_cRecord_Withought_C_Extra_Data.isEmpty()) {
                    writer.write("\r\nC-Record could not be merge (extra data absent for additional record):" + "\r\n");
                    for (String d : additional_cRecord_Withought_C_Extra_Data) {
                        writer.write(d + "\r\n");
                    }
                }

                writer.write("\r\n=============================================================================\r\n");
                writer.write("\r\nRECALCULATING\r\n\r\n");
                writer.write("List of recalculated D-Records in result file:\r\n");
                for (String d : protocol.getRecalculatedDRecords()) {
                    writer.write(d + "\r\n");
                }
            }
        } catch (IOException ex) {
            // report
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {/*ignore*/}
        }
    }
}
