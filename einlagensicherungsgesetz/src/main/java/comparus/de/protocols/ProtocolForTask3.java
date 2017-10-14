package comparus.de.protocols;

import comparus.de.domen.*;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static comparus.de.CSVReaderMain.protocol;
<<<<<<< HEAD
import static comparus.de.tasks.Task3.clients_not_have_additional_data;
=======
>>>>>>> bfa13cfe54f9c3b3277cd0dee4f8f849b6123c59

/**
 * Created by ekaterina on 9/26/17.
 */
public class ProtocolForTask3 {
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

    public static void  writeProtocolToFileTask3(String protocolName, String taskNumber, String inputFileForMerge1, FileInfo fileInfo, String additioanlFileA,
                        String additioanlFileB, String additioanlFileC, String task1ResultFileName, int AAdditioanlCount, int BAdditioanlCount, int CAdditioanlCount, String errorMessage,
                        Map<String, String[]> notUpdatedBRecords, Map<String, String[]> notUpdatedCRecords, Map<String, HW> D_HW
    ) throws FileNotFoundException {

        Writer writer = null;
        try {
            if(inputFileForMerge1 != null) {
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(protocolName), "utf-8"));
                writer.write("\r\n=============================================================================\r\n");
                writer.write("GENERAL INFORMATIONS\r\n\r\n");

                writer.write("Program name: einlagensicherungsgesetz\r\n");
                writer.write("Date of run: " + new SimpleDateFormat("dd-MM-YYYY HH-mm").format(new Date()) + "\r\n");
                writer.write("Task: " + taskNumber + "\r\n");
                writer.write("Input file: " + inputFileForMerge1 + "\r\n");
                writer.write("Additional file A: " + additioanlFileA + "\r\n");
                writer.write("Additional file B: " + additioanlFileB + "\r\n");
                writer.write("Additional file C: " + additioanlFileC + "\r\n");

                writer.write("\r\n=============================================================================\r\n");
                writer.write("ERRORS\r\n");
                if(errorMessage != null) {
                    writer.write(errorMessage + "\r\n");
                }

                writer.write("\r\n=============================================================================\r\n");
                writer.write("\r\nAMOUNTS OF DATASETS\r\n\r\n");
                writer.write("Input file name: " + fileInfo.getFileName() + "\r\n");
                writer.write("B : " + fileInfo.getBCount() + "\r\n");
                writer.write("C : " + fileInfo.getCCount()  + "\r\n");
                writer.write("D : " + fileInfo.getDCount()  + "\r\n");
                writer.write("Additional file A: " + additioanlFileA + "\r\n");
                writer.write("A : " + AAdditioanlCount + "\r\n");
                writer.write("Additional file B: " + additioanlFileB + "\r\n");
                writer.write("B : " + BAdditioanlCount + "\r\n");
                writer.write("Additional file C: " + additioanlFileC + "\r\n");
                writer.write("C : " + CAdditioanlCount + "\r\n");
                writer.write("Resultfile\r\n");
                writer.write("B : " + protocol.getResultAmountB() + "\r\n");
                writer.write("C : " + protocol.getResultAmountC() + "\r\n");
                writer.write("D : " + protocol.getResultAmountD() + "\r\n");
<<<<<<< HEAD
//
//                writer.write("\r\n=============================================================================\r\n");
//                writer.write("\r\nMERGING\r\n\r\n");
//                writer.write("Amount of merged datasets: " + protocol.getResultAmountTotal() + "\r\n");
//                writer.write("List of identified double customers:" + "\r\n");
//                for(KeyFile1ToFile2ToFile3 b : protocol.getKeyFile1ToFile2ToFile3_B_Record()) {
//                    writer.write("B2 in original file : " + b.getKeyFile1().split("\\*")[1] + "\r\n");
//                    writer.write("B2 in additional B file : " + b.getKeyFile2().split(";")[2] + "\r\n");
//                    writer.write("B2 in result file : " + b.getKeyFile3().split("\\*")[1] + "\r\n");
//                }
//                for(KeyFile1ToFile2ToFile3 c : protocol.getKeyFile1ToFile2ToFile3_C_Record()) {
//                    writer.write("C2B in original file : " + c.getKeyFile1().split("\\*")[2] + "\r\n");
//                    writer.write("C2B  in additional C file : " + c.getKeyFile2().split(";")[2] + "\r\n");
//                    writer.write("C2B in result file : " + c.getKeyFile3().split("\\*")[2] + "\r\n");
//                }
=======

                writer.write("\r\n=============================================================================\r\n");
                writer.write("\r\nMERGING\r\n\r\n");
                writer.write("Amount of merged datasets: " + protocol.getResultAmountTotal() + "\r\n");
                writer.write("List of identified double customers:" + "\r\n");
                for(KeyFile1ToFile2ToFile3 b : protocol.getKeyFile1ToFile2ToFile3_B_Record()) {
                    writer.write("B2 in original file : " + b.getKeyFile1().split("\\*")[1] + "\r\n");
                    writer.write("B2 in additional B file : " + b.getKeyFile2().split(";")[2] + "\r\n");
                    writer.write("B2 in result file : " + b.getKeyFile3().split("\\*")[1] + "\r\n");
                }
                for(KeyFile1ToFile2ToFile3 c : protocol.getKeyFile1ToFile2ToFile3_C_Record()) {
                    writer.write("C in original file : " + c.getKeyFile1().split("\\*")[2] + "\r\n");
                    writer.write("C  in additional C file : " + c.getKeyFile2().split(";")[2] + "\r\n");
                    writer.write("C in result file : " + c.getKeyFile3().split("\\*")[2] + "\r\n");
                }
>>>>>>> bfa13cfe54f9c3b3277cd0dee4f8f849b6123c59

                writer.write("\r\n=============================================================================\r\n");
                writer.write("\r\nRECALCULATING\r\n\r\n");
                writer.write("List of recalculated D-Records in result file:\r\n");
<<<<<<< HEAD
                //  for(String d : protocol.getRecalculatedDRecords()) {
                for(Map.Entry<String, HW> hw : D_HW.entrySet()) {
=======
              //  for(String d : protocol.getRecalculatedDRecords()) {
              for(Map.Entry<String, HW> hw : D_HW.entrySet()) {
>>>>>>> bfa13cfe54f9c3b3277cd0dee4f8f849b6123c59
                    writer.write("D : " + hw.getKey() + "\r\n");
                    writer.write("HW1 : " + hw.getValue().getHW1() + "\r\n");
                    writer.write("HW2 : " + hw.getValue().getHW2() + "\r\n");
                    writer.write("HW3 : " + hw.getValue().getHW3() + "\r\n");
                    writer.write("HW4 : " + hw.getValue().getHW4() + "\r\n");
                    writer.write("HW5 : " + hw.getValue().getHW5() + "\r\n");
                }

                if(!notUpdatedBRecords.isEmpty() || !notUpdatedCRecords.isEmpty()) {
                    writer.write("\r\n=============================================================================\r\n");
                    writer.write("\r\nNot updated records\r\n\r\n");
                    if(!notUpdatedBRecords.isEmpty()) {
                        writer.write("List of B records:\r\n");
                        for (Map.Entry<String, String[]> entry : notUpdatedBRecords.entrySet()) {
                            writer.write(StringUtils.join(entry.getValue(), ';') + "\r\n");
                        }
                    }
                    if(!notUpdatedCRecords.isEmpty()) {
                        writer.write("List of c records:\r\n");
                        for (Map.Entry<String, String[]> entry : notUpdatedCRecords.entrySet()) {
                            writer.write(StringUtils.join(entry.getValue(), ';') + "\r\n");
                        }
                    }
                }
<<<<<<< HEAD

                if(clients_not_have_additional_data.size() > 0) {
                    writer.write("\r\n=============================================================================\r\n");
                    writer.write("\r\nClients that have not additional data \r\n\r\n");
                    for (Map.Entry<String, CVSClient> entry : clients_not_have_additional_data.entrySet()) {
                        writer.write("B : " + StringUtils.join(entry.getValue().getClientB(), ';') + "\r\n");
                        for(String[] c : entry.getValue().getClientsC()) {
                            writer.write("C : " + StringUtils.join(c, ';') + "\r\n");
                        }
                        writer.write("D : " + StringUtils.join(entry.getValue().getD(), ';') + "\r\n");
                    }
                }
=======
>>>>>>> bfa13cfe54f9c3b3277cd0dee4f8f849b6123c59
            }
        } catch (IOException ex) {
            // report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }
}
