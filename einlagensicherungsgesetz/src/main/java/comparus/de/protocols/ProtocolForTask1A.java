//package comparus.de.protocols;
//
//import comparus.de.domen.KeyFile1ToFile2;
//
//import java.io.*;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * Created by ekaterina on 9/26/17.
// */
//public class ProtocolForTask1A {
//    public static void  writeProtocolToFileTask1A(String protocolName, String taskNumber, String inputFileForMerge1,
//                                                 String task1ResultFileName, String errorMessage) throws FileNotFoundException {
//
//        Writer writer = null;
//        try {
//            if (taskNumber.equalsIgnoreCase("Task1") && inputFileForMerge1 != null && inputFileForMerge2 != null) {
//                writer = new BufferedWriter(new OutputStreamWriter(
//                        new FileOutputStream(protocolName), "utf-8"));
//                writer.write("\r\n=============================================================================\r\n");
//                writer.write("GENERAL INFORMATIONS\r\n\r\n");
//
//                writer.write("Program name: einlagensicherungsgesetz\r\n");
//                writer.write("Date of run: " + new SimpleDateFormat("dd-MM-YYYY HH-mm").format(new Date()) + "\r\n");
//                writer.write("Task: " + taskNumber + "\r\n");
//                writer.write("Input file 1: " + inputFileForMerge1 + "\r\n");
//                writer.write("Input file 2: " + inputFileForMerge2 + "\r\n");
//                writer.write("Filename result file: " + task1ResultFileName + "\r\n");
//
//                writer.write("\r\n=============================================================================\r\n");
//                writer.write("ERRORS\r\n");
//                if (errorMessage != null) {
//                    writer.write(errorMessage + "\r\n");
//                }
//
//                writer.write("\r\n=============================================================================\r\n");
//                writer.write("\r\nAMOUNTS OF DATASETS\r\n\r\n");
//                writer.write("Input file name 1: " + fileInfo1.getFileName() + "\r\n");
//                writer.write("B : " + fileInfo1.getBCount() + "\r\n");
//                writer.write("C : " + fileInfo1.getCCount() + "\r\n");
//                writer.write("D : " + fileInfo1.getDCount() + "\r\n");
//                writer.write("Input file name: " + fileInfo2.getFileName() + "\r\n");
//                writer.write("B : " + fileInfo2.getBCount() + "\r\n");
//                writer.write("C : " + fileInfo2.getCCount() + "\r\n");
//                writer.write("D : " + fileInfo2.getDCount() + "\r\n");
//                writer.write("Resultfile\r\n");
//                writer.write("B : " + protocol.getResultAmountB() + "\r\n");
//                writer.write("C : " + protocol.getResultAmountC() + "\r\n");
//                writer.write("D : " + protocol.getResultAmountD() + "\r\n");
//
//                writer.write("\r\n=============================================================================\r\n");
//                writer.write("\r\nMERGING\r\n\r\n");
//                writer.write("Amount of merged datasets: " + protocol.getResultAmountTotal() + "\r\n");
//                writer.write("List of identified double customers:" + "\r\n");
//                for (KeyFile1ToFile2 b : protocol.getKeyFile1ToFile2()) {
//                    writer.write("B2 in file1 : " + b.getKeyFile1().split("\\*")[1] + "\r\n");
//                    writer.write("B2 in file2 : " + b.getKeyFile2().split("\\*")[1] + "\r\n");
//                    writer.write("B2 in result file : " + b.getKeyFile1().split("\\*")[1] + "\r\n");
//                }
//
//                writer.write("\r\n=============================================================================\r\n");
//                writer.write("\r\nRECALCULATING\r\n\r\n");
//                writer.write("List of recalculated D-Records in result file:\r\n");
//                for (String d : protocol.getRecalculatedDRecords()) {
//                    writer.write(d + "\r\n");
//                }
//            }
//        } catch (IOException ex) {
//            // report
//        } finally {
//            try {
//                writer.close();
//            } catch (Exception ex) {/*ignore*/}
//        }
//    }
//}
