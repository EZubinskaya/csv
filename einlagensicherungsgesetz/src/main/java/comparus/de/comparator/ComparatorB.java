package comparus.de.comparator;

import comparus.de.domen.CVSClient;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Created by ekaterina on 9/21/17.
 */

public class ComparatorB  implements Comparator<CVSClient> {

    @Override
    public int compare(CVSClient o1, CVSClient o2) {

        BigDecimal r = createNumValue(o2.getClientB()[1]).subtract(createNumValue(o1.getClientB()[1]));
        return r.intValue();
    }

    private static BigDecimal createNumValue(String value) {
        BigDecimal result = BigDecimal.ZERO;
        String sanitized = value.replace(",", ".");
        if (NumberUtils.isCreatable(sanitized))     {
            result = NumberUtils.createBigDecimal(sanitized);
        }
        return result;
    }
}
