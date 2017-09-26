package comparus.de.protocols;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ekaterina on 9/26/17.
 */
public class ProtocolForTask2 {
    public static void  writeProtocolToFileTask2(String protocolName, String taskNumber, String inputFileForMerge1,
                                                 String task1ResultFileName, String errorMessage) throws FileNotFoundException {

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
                writer.write("Input file 1: " + inputFileForMerge1 + "\r\n");
                writer.write("Filename result file: " + task1ResultFileName + "\r\n");

                writer.write("\r\n=============================================================================\r\n");
                writer.write("ERRORS\r\n");
                if(errorMessage != null) {
                    writer.write(errorMessage + "\r\n");
                }

                writer.write("\r\n=============================================================================\r\n");
                writer.write("\r\nAMOUNTS OF DATASETS\r\n\r\n");
                writer.write("Inputfile name 1: " + inputFileForMerge1 + "\r\n");
                writer.write("D : " + ProtocolForTask1.protocol.getResultAmountD() + "\r\n");
            }
        } catch (IOException ex) {
            // report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }
}
