package comparus.de.tasks;

import comparus.de.domen.Additional_CRecord;
import comparus.de.domen.CVSClient;
import comparus.de.domen.C_ExtraData;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static comparus.de.tasks.Task1a.readExtraAdditionalDataCTask1a;

/**
 * Created by ekaterina on 10/12/17.
 */
public class Task4A {

    public static List<String> additional_cRecord_Withought_C_Extra_Data = new ArrayList<>();


    public static Map<String, Additional_CRecord> sortDataForAddingNewCRecord (Map<String, Additional_CRecord> additional_cRecord , Map<String, C_ExtraData> C_ExtraData ) {
        for(Iterator<Map.Entry<String, Additional_CRecord>> it = additional_cRecord.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Additional_CRecord> entry_c = it.next();
            String C2B = entry_c.getValue().getC2B();
            if(C2B.split("-").length > 0) {
                C2B = C2B.split("-")[0];
            }
            if(C_ExtraData.get(C2B) == null) {
                additional_cRecord_Withought_C_Extra_Data.add(StringUtils.join(entry_c.getValue().toArray(), "*"));
                it.remove();
            }
        }

        return additional_cRecord;
    }
}
