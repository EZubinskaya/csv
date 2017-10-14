package comparus.de.util;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by ekaterina on 9/26/17.
 */
public class ReadWriteData {
    public static void writeCSV(List<String[]> all, String outputFile ) throws IOException {
        File file = new File(outputFile);
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        CSVWriter csvWriter = new CSVWriter(writer, '*', CSVWriter.NO_QUOTE_CHARACTER, '\'', "\n");
        csvWriter.writeAll(all);
        csvWriter.close();
    }
}
