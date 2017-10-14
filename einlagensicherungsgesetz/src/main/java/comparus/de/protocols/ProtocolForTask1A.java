package comparus.de.protocols;

import comparus.de.domen.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import static comparus.de.CSVReaderMain.protocol;

/**
 * Created by ekaterina on 9/26/17.
 */
public class ProtocolForTask1A {

    public static FileInfo readCSVFileByStringRetCountTask1a(Map<String, CVSClient> fullFile, String fileName) throws IOException {
        FileInfo fileInfo = new FileInfo();
        int b = 0;
        int c = 0;
        int d = 0;
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()){
            CVSClient cvsClient = entry.getValue();
            b += 1;
            c += cvsClient.getClientsC().size();
            d += 1;
        }
        fileInfo.setFileName(fileName);
        fileInfo.setBCount(b);
        fileInfo.setCCount(c);
        fileInfo.setDCount(d);
        return fileInfo;
    }

    public static void  writeProtocolToFileTask1A(String protocolName, String taskNumber, String inputFileForMerge1, String Additional_CRecord_Data_Task1a,
                                                  String task1ResultFileName, FileInfo fileInfo1, FileInfo fileInfo2, Set<Additional_CRecord> mergingCRecords,  Set<Additional_CRecord> notMergingCRecords,
                                                  String errorMessage) throws FileNotFoundException {

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
                writer.write("C : " + fileInfo2.getCCount() + "\r\n");
                writer.write("Resultfile\r\n");
                writer.write("B : " + protocol.getResultAmountB() + "\r\n");
                writer.write("C : " + protocol.getResultAmountC() + "\r\n");
                writer.write("D : " + protocol.getResultAmountD() + "\r\n");

                writer.write("\r\n=============================================================================\r\n");
                writer.write("\r\nMERGING\r\n\r\n");
                writer.write("Amount of merged datasets: " + mergingCRecords.size() + "\r\n");
                writer.write("List of created C-Records:" + "\r\n");
                for (Additional_CRecord d : mergingCRecords) {
                    writer.write(d.toString() + "\r\n");
                }
                if(!notMergingCRecords.isEmpty()) {
                    writer.write("\r\nC-Record could not be merge:" + "\r\n");
                    for (Additional_CRecord d : notMergingCRecords) {
                        writer.write(d.toString() + "\r\n");
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
