package comparus.de.protocols;

import comparus.de.domen.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ekaterina on 9/26/17.
 */
public class ProtocolForTask1 {
    public static Protocol protocol = new Protocol();
    //Protocol
    public static FileInfo readCSVFileByStringRetCount(Map<B, CVSClient> fullFile, String fileName) throws IOException {
        FileInfo fileInfo = new FileInfo();
        int b = 0;
        int c = 0;
        int d = 0;
        for (Map.Entry<B, CVSClient> entry : fullFile.entrySet()){
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

    public static void calculateDAmount(Map<String,CVSClient> fullFile) {
        int countD = 0;

        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
            if(entry.getValue().getD() != null) {
                countD ++;
            }
        }
        protocol.setResultAmountD(countD);
    }

    public static void calculateAmount(List<String[]> allData) {
        int countB = 0;
        int countC = 0;
        int countD = 0;

        for(String[] el : allData) {
            if(el[0].equalsIgnoreCase("B")) {
                countB ++;
            } else if (el[0].equalsIgnoreCase("C")) {
                countC ++;
            } else if (el[0].equalsIgnoreCase("D")) {
                countD ++;
            }
        }
        protocol.setResultAmountB(countB);
        protocol.setResultAmountC(countC);
        protocol.setResultAmountD(countD);
    }

    public static List<String>  sameBList(List<KeyFile1ToFile2> keys) {
        List<String> contistTheSameB = new ArrayList<>();
        for (KeyFile1ToFile2 cur : keys) {
            contistTheSameB.add(cur.getKeyFile1());
            contistTheSameB.add(cur.getKeyFile2());
        }
        return contistTheSameB;
    }

    public static void  writeProtocolToFileTask1(String protocolName, String taskNumber, String inputFileForMerge1, String inputFileForMerge2,
                                          String task1ResultFileName, FileInfo fileInfo1, FileInfo fileInfo2, String errorMessage) throws FileNotFoundException {

        Writer writer = null;
        try {
            if(inputFileForMerge1 != null && inputFileForMerge2 != null) {
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(protocolName), "utf-8"));
                writer.write("\r\n=============================================================================\r\n");
                writer.write("GENERAL INFORMATIONS\r\n\r\n");

                writer.write("Program name: einlagensicherungsgesetz\r\n");
                writer.write("Date of run: " + new SimpleDateFormat("dd-MM-YYYY HH-mm").format(new Date()) + "\r\n");
                writer.write("Task: " + taskNumber + "\r\n");
                writer.write("Input file 1: " + inputFileForMerge1 + "\r\n");
                writer.write("Input file 2: " + inputFileForMerge2 + "\r\n");
                writer.write("Filename result file: " + task1ResultFileName + "\r\n");

                writer.write("\r\n=============================================================================\r\n");
                writer.write("ERRORS\r\n");
                if(errorMessage != null) {
                    writer.write(errorMessage + "\r\n");
                }

                writer.write("\r\n=============================================================================\r\n");
                writer.write("\r\nAMOUNTS OF DATASETS\r\n\r\n");
                writer.write("Input file name 1: " + fileInfo1.getFileName() + "\r\n");
                writer.write("B : " + fileInfo1.getBCount() + "\r\n");
                writer.write("C : " + fileInfo1.getCCount()  + "\r\n");
                writer.write("D : " + fileInfo1.getDCount()  + "\r\n");
                writer.write("Input file name: " + fileInfo2.getFileName() + "\r\n");
                writer.write("B : " + fileInfo2.getBCount() + "\r\n");
                writer.write("C : " + fileInfo2.getCCount()  + "\r\n");
                writer.write("D : " + fileInfo2.getDCount()  + "\r\n");
                writer.write("Resultfile\r\n");
                writer.write("B : " + protocol.getResultAmountB() + "\r\n");
                writer.write("C : " + protocol.getResultAmountC() + "\r\n");
                writer.write("D : " + protocol.getResultAmountD() + "\r\n");

                writer.write("\r\n=============================================================================\r\n");
                writer.write("\r\nMERGING\r\n\r\n");
                writer.write("Amount of merged datasets: " + protocol.getResultAmountTotal() + "\r\n");
                writer.write("List of identified double customers:" + "\r\n");
                for(KeyFile1ToFile2 b : protocol.getKeyFile1ToFile2()) {
                    writer.write("B2 in file1 : " + b.getKeyFile1().split("\\*")[1] + "\r\n");
                    writer.write("B2 in file2 : " + b.getKeyFile2().split("\\*")[1] + "\r\n");
                    writer.write("B2 in result file : " + b.getKeyFile1().split("\\*")[1] + "\r\n");
                }

                writer.write("\r\n=============================================================================\r\n");
                writer.write("\r\nRECALCULATING\r\n\r\n");
                writer.write("List of recalculated D-Records in result file:\r\n");
                for(String d : protocol.getRecalculatedDRecords()) {
                    writer.write(d + "\r\n");
                }
            }
        } catch (IOException ex) {
            // report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }
}
