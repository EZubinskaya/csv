package comparus.de;

import comparus.de.domen.*;
import comparus.de.tasks.Task1a;
import comparus.de.tasks.Task3;
import comparus.de.tasks.Task4A;

import java.io.*;
import java.util.*;


import static comparus.de.protocols.ProtocolForTask1A.readCSVFileByStringRetCountTask1a;
import static comparus.de.protocols.ProtocolForTask1A.writeProtocolToFileTask1A;
import static comparus.de.protocols.ProtocolForTask3.writeProtocolToFileTask3;
import static comparus.de.protocols.ProtocolForTask4A.writeProtocolToFileTask4A;
import static comparus.de.tasks.Task1a.*;
import static comparus.de.tasks.Task4.calculateDversion5;
import static comparus.de.tasks.Task4A.*;
import static comparus.de.tasks.Task5.generateMetadataTask5;
import static comparus.de.util.ReadWriteData.writeCSV;
import static comparus.de.tasks.Task1.*;
import static comparus.de.protocols.ProtocolForTask1.*;

import static comparus.de.tasks.Task2.*;
import static comparus.de.protocols.ProtocolForTask2.*;
import static comparus.de.tasks.Task3.*;

/**
 * Created by ekaterina on 8/31/17.
 */
public class CSVReaderMain {

    public static Protocol protocol = new Protocol();
    public static String[] A = null;
    public static String[] AFile2 = null;

    public static String[] E = null;

    public static void main(String[] args) throws IOException {

        String inputFileForMerge1 = System.getProperty("file1");
        String inputFileForMerge2 = System.getProperty("file2");
//        String inputFileAfterMerge = System.getProperty("EinreicherdateiGesamt41");
        String A_Additional_5 = System.getProperty("A_Additional5");
        String inputFileForTask3 = System.getProperty("Einreicherdatei41");
        String inputFileForTask5 = System.getProperty("EinreicherdateiGesamt50");

//        String Additional_CRecord_Data_Task1a = System.getProperty("Additional_CRecord_Data_Task1a");
        String B_Additional_5 = System.getProperty("B_Additional5");
//        String C_Additional_5 = System.getProperty("C_Additional5");
//        String C_Additional_5_2 = System.getProperty("C_Additional5_2");
//        String taskNumber = System.getProperty("taskNumber");

        String task1ResultFileName = "EinreicherdateiB11_Gesamt_4.1.csv";
        String task1aResultFileName = "EinreicherdateiB11_Gesamt_4.1_add_additional_C.csv";
        String task2ResultFileName = "Meldedatei_Gesamt_4.1.csv";
        String task4ResultFileName = "EinreicherdateiB11_Gesamt_5.1_merge.csv";
        String task4AResultFileName = "EinreicherdateiB11_Gesamt_5.1_merge_additional_C_Records.csv";
        String task5ResultFileName = "Meldedatei_Gesamt_3.0.csv";

        // TEST DATA
//        String inputFileForMerge1 =  "src/main/resources/task4/1.csv";
//        String inputFileForMerge2 = "src/main/resources/task4/2.csv";
////        String inputFileForMerge1 =  "src/main/resources/bug1/1.csv";
////        String inputFileForMerge2 = "src/main/resources/bug1/2.csv";
////        String inputFileAfterMerge =  "src/main/resources/task1a/input.csv";
////        String Additional_CRecord_Data_Task1a = "src/main/resources/task1a/Additional_CRecord_Data_Task1a.csv";
////        String inputFileAfterMerge =  "src/main/resources/task3Test/0000000121170930.csv";
//        String inputFileForTask3 =  "src/main/resources/ED_Test_GEMKD/0000000883171012_GEMKD_reduce.csv";
////        String Additional_CRecord_Data_Task1a = "src/main/resources/task1aP/bank11_meldung_einlagensicherung_20170831_neu.csv";
//
//        String A_Additional_5 = "src/main/resources/extraData/A_Additional_5.0.csv";
//        String B_Additional_5 = "src/main/resources/ED_Test_GEMKD/B_Zusatz5.0_883_1210.csv";
//        String C_Additional_5 = "src/main/resources/ED_Test_GEMKD/C_Zusatz5.0_883_1210.csv";
//        String taskNumber = "Task4";
//        String inputFileForTask5 = "src/main/resources/ED_Test_GEMKD/res.csv";
//
        String taskNumber = "Task4A";
        String inputFileAfterMerge =  "src/main/resources/task4ANew/task4.csv";
//        String Additional_CRecord_Data_Task1a = "src/main/resources/task4A/Additional_CRecord_Data_Task1a_new.csv";
//        String C_Additional_5 = "src/main/resources/task4A/C_Additional_5.0.csv";
        String Additional_CRecord_Data_Task1a = "src/main/resources/task4ANew/Additional_CRecord_Data_Task1a_new.csv";
        String C_Additional_5 = "src/main/resources/task4ANew/C_Zusatz5.0_121_1210.csv";
        String C_Additional_5_2 = "src/main/resources/task4ANew/C_Zusatz5.0_883_1210.csv";

        if(taskNumber.equalsIgnoreCase("Task1") && inputFileForMerge1!= null & inputFileForMerge2 != null) {
            Map<B, CVSClient> readFile1 = null;
            Map<B, CVSClient> readFile2 = null;
            FileInfo fileInfo1 = null;
            FileInfo fileInfo2 = null;
            List<String[]> allData = null;
            String errorMessage = null;
            try {
                readFile1 = readCSVFileByString(inputFileForMerge1, true);
                readFile2 = readCSVFileByString(inputFileForMerge2, true);

                fileInfo1 = readCSVFileByStringRetCount(readFile1, inputFileForMerge1);
                fileInfo2 = readCSVFileByStringRetCount(readFile2, inputFileForMerge2);

                Map<String, CVSClient> fullFile = generateMapOfData(readFile1, readFile2);
                reCalculateD(fullFile, A, true);
                String[] E = reCalculateE(fullFile, A);
                allData = generateListOfDataArray(A, fullFile, E);
                writeCSV(allData, task1ResultFileName);
            } catch (Exception ex) {
                errorMessage = ex.getStackTrace().toString();
                System.out.println(" exception " + ex.getStackTrace());
                throw ex;
            }

            //Protocol
            calculateAmount(allData);
            protocol.setResultAmountTotal(allData.size());
            protocol.setResultAmountTotal(protocol.getKeyFile1ToFile2().size());
            writeProtocolToFileTask1("Protocol Task1.txt", taskNumber, inputFileForMerge1, inputFileForMerge2,
                    task1ResultFileName, fileInfo1, fileInfo2, errorMessage);
        } else if(taskNumber.equalsIgnoreCase("Task2") && inputFileAfterMerge != null) {
            String errorMessage = null;
            Map<String,CVSClient> fullFile = null;
            List<String[]> allMetadata = null;
            try {
                fullFile = readCSVFileByStringSimple(inputFileAfterMerge);
                List<String> metadata = generateMetadata(E, fullFile);
                allMetadata = generateMetadataArray(metadata);
                writeCSV(allMetadata, task2ResultFileName);
            } catch (Exception ex) {
                errorMessage = ex.getStackTrace().toString();
                throw ex;
            }

            //Protocol
            calculateDAmount(fullFile);
            writeProtocolToFileTask2("Protocol Task2.txt", taskNumber, inputFileAfterMerge,
                    task2ResultFileName, errorMessage);

        }else if(taskNumber.equalsIgnoreCase("Task1a") && inputFileAfterMerge != null && Additional_CRecord_Data_Task1a != null) {
            Map<String, CVSClient> readFile1 = null;
            FileInfo fileInfo1 = null;
            FileInfo fileInfo2 = null;
            List<String[]> allData = null;
            String errorMessage = null;
            Set<Additional_CRecord> mergingCRecords = new HashSet<>();
            try {
                readFile1 = readCSVFileByStringSimple(inputFileAfterMerge);
                fileInfo1 = readCSVFileByStringRetCountTask1a(readFile1, inputFileAfterMerge);

                Map<String, Additional_CRecord> additional_cRecord = readExtraAdditionalDataCTask1a(Additional_CRecord_Data_Task1a);

                fileInfo2 = new FileInfo();
                fileInfo2.setCCount(additional_cRecord.size());
                mergingCRecords.addAll(additional_cRecord.values());
                Map<String,CVSClient> fullFileVersion4_1_Additional_C = generateVersion4AdditionalC(readFile1, additional_cRecord);
                List<String> B = new ArrayList<>();
                for(Additional_CRecord a : mergingCRecords) {
                    B.add(a.getC2A());
                }
                reCalculateD_task1A(fullFileVersion4_1_Additional_C, A, B);
                String[] E = reCalculateE(fullFileVersion4_1_Additional_C, A);
                allData = generateListOfDataArrayNotSorted(A, fullFileVersion4_1_Additional_C, E);
                writeCSV(allData, task1aResultFileName);

            } catch (Exception ex) {
                errorMessage = ex.getStackTrace().toString();
                System.out.println(" exception " + ex.getStackTrace());
                throw ex;
            }

            //Protocol
            calculateAmount(allData);
            protocol.setResultAmountTotal(allData.size());
            writeProtocolToFileTask1A("Protocol Task1a.txt", taskNumber, inputFileAfterMerge, Additional_CRecord_Data_Task1a,
                    task1aResultFileName, fileInfo1, fileInfo2, Task1a.C_Additional_Import, Task1a.C_Additional_Not_Import, errorMessage);

        }else if(taskNumber.equalsIgnoreCase("Task3") && inputFileForTask3 != null
                && B_Additional_5 != null && C_Additional_5 != null) {
            Map<B, CVSClient> readFile1 = null;
            FileInfo fileInfo1 = null;
            Map<String, String> A_ExtraData = new HashMap<>();
            Map<String, B_ExtraData> B_ExtraData = new HashMap<>();
            Map<String, C_ExtraData> C_ExtraData = new HashMap<>();
            String errorMessage = null;
            String[] AVersion5_1 = A;
            try {
                readFile1 = readCSVFileByString(inputFileForTask3, true);
                fileInfo1 = readCSVFileByStringRetCount(readFile1, inputFileForTask3);

                Map<String,CVSClient> fullFile = readCSVFileByStringSimple(inputFileForTask3);
                if(A_Additional_5 != null) {
                    A_ExtraData = readExtraDataA(A_Additional_5);
                    AVersion5_1 = geeratedAVersion5_1 (A,  A_ExtraData);
                }
                if(AVersion5_1 == null) {
                    AVersion5_1 = A;
                }

                B_ExtraData = readExtraDataB(B_Additional_5);
                C_ExtraData = readExtraDataC(C_Additional_5);
                Map<String,CVSClient> fullFileVersion5_1 = generateVersion5(fullFile, B_ExtraData, C_ExtraData);
                Task3.reCalculateDVersion5_1(fullFileVersion5_1, AVersion5_1);
                String[] EVersion5_1 = reCalculateEVersion5_1(fullFileVersion5_1);
                List<String[]> allDataVersion5_1 = generateListOfDataArray(AVersion5_1, fullFileVersion5_1, EVersion5_1);
                String task3ResultFileName = "DEinreicherdatei50-" + A2 + ".csv";

                writeCSV(allDataVersion5_1, task3ResultFileName);
                calculateAmount(allDataVersion5_1);
                protocol.setResultAmountTotal(allDataVersion5_1.size());
            } catch (Exception ex) {
                errorMessage = ex.getStackTrace().toString();
                System.out.println(" exception " + ex.getStackTrace());
                throw ex;
            }
            //Protocol
            writeProtocolToFileTask3("Protocol Task3 - " + A2 + ".txt", taskNumber, inputFileForTask3, fileInfo1, A_Additional_5, B_Additional_5, C_Additional_5,
            task1aResultFileName, 1, B_ExtraData.size(), C_ExtraData.size(), errorMessage, B_Record_Not_Updated, C_Record_Not_Updated, D_HW);

        } else if (taskNumber.equalsIgnoreCase("Task4")  && inputFileForMerge1!= null & inputFileForMerge2 != null) {
            Map<B, CVSClient> readFile1 = null;
            Map<B, CVSClient> readFile2 = null;
            FileInfo fileInfo1 = null;
            FileInfo fileInfo2 = null;
            List<String[]> allData = null;
            String errorMessage = null;
            try {
                readFile1 = readCSVFileByString(inputFileForMerge1, false);
                readFile2 = readCSVFileByString(inputFileForMerge2, false);

                fileInfo1 = readCSVFileByStringRetCount(readFile1, inputFileForMerge1);
                fileInfo2 = readCSVFileByStringRetCount(readFile2, inputFileForMerge2);

                Map<String, CVSClient> fullFile = generateMapOfData(readFile1, readFile2);


                calculateDversion5(fullFile, A, true);
                String[] EVersion5_1 = reCalculateEVersion5_1(fullFile);
                allData = generateListOfDataArray(A, fullFile, EVersion5_1);
                writeCSV(allData, task4ResultFileName);

            } catch (Exception ex) {
                errorMessage = ex.getStackTrace().toString();
                System.out.println(" exception " + ex.getStackTrace());
                throw ex;
            }
            //Protocol
            calculateAmount(allData);
            protocol.setResultAmountTotal(allData.size());
            protocol.setResultAmountTotal(protocol.getKeyFile1ToFile2().size());
            writeProtocolToFileTask1("Protocol Task4.txt", taskNumber, inputFileForMerge1, inputFileForMerge2,
                    task4ResultFileName, fileInfo1, fileInfo2, errorMessage);
        } else if(taskNumber.equalsIgnoreCase("Task4A") && inputFileAfterMerge != null && Additional_CRecord_Data_Task1a != null && C_Additional_5 != null && C_Additional_5_2 != null) {
            Map<String,CVSClient> fullFile = null;
            Map<String, Additional_A_C_Record> additional_cRecord = null;
            Map<String, C_ExtraData> C_ExtraData_1 = null;
            Map<String, C_ExtraData> C_ExtraData_2 = null;
            Map<String,CVSClient> fullFileVersion5_1 = null;
            String errorMessage = null;
            FileInfo fileInfo1 = null;

            try {
                fullFile = readCSVFileByStringSimple(inputFileAfterMerge);
                additional_cRecord = readExtraAdditionalDataCTask4a(Additional_CRecord_Data_Task1a);
                C_ExtraData_1 = readExtraDataC(C_Additional_5);
                C_ExtraData_2 = readExtraDataC(C_Additional_5_2);

                Map<B, CVSClient> readFile1 = readCSVFileByString(inputFileAfterMerge, false);
                fileInfo1 = readCSVFileByStringRetCount(readFile1, inputFileAfterMerge);

                sortDataForAddingNewCRecord(additional_cRecord, C_ExtraData_1, C_ExtraData_2, fullFile);
                generateVersion5_Task4A(fullFile, full_C_Record);
                fullFileVersion5_1 = Task4A.generateVersion5_Task4A(fullFile, full_C_Record);
                Task4A.reCalculateDVersion5_1(fullFileVersion5_1, A);
                String[] EVersion5_1 = reCalculateEVersion5_1(fullFileVersion5_1);
                List<String[]> allDataVersion5_1 = generateListOfDataArray(A, fullFileVersion5_1, EVersion5_1);
                writeCSV(allDataVersion5_1, task4AResultFileName);

            } catch (Exception ex) {
                errorMessage = ex.getStackTrace().toString();
                System.out.println(" exception " + ex.getStackTrace());
                throw ex;
            }

            //Protocol
            writeProtocolToFileTask4A("Protocol Task4a.txt", taskNumber, inputFileAfterMerge, Additional_CRecord_Data_Task1a, C_Additional_5, C_Additional_5_2,
                    task4AResultFileName, fileInfo1,
                    additional_cRecord_Withought_C_Extra_Data, mergingCRecords,
                    errorMessage, additional_cRecord.size(), C_ExtraData_1.size(), C_ExtraData_2.size());

        } else if(taskNumber.equalsIgnoreCase("Task5") && inputFileForTask5 != null) {
            String errorMessage = null;
            Map<String,CVSClient> fullFile = null;
            List<String[]> allMetadata = null;
            try {
                fullFile = readCSVFileByStringSimple(inputFileForTask5);
                List<String> metadata = generateMetadataTask5(E, fullFile);
                allMetadata = generateMetadataArray(metadata);
                writeCSV(allMetadata, task5ResultFileName);
            } catch (Exception ex) {
                errorMessage = ex.getStackTrace().toString();
                throw ex;
            }

            //Protocol
            calculateDAmount(fullFile);
            writeProtocolToFileTask2("Protocol Task5.txt", taskNumber, inputFileForTask5,
                    task2ResultFileName, errorMessage);

        } else {

        }
    }

}