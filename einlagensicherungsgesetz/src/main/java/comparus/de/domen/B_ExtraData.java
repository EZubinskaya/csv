package comparus.de.domen;

import java.math.BigDecimal;

/**
 * Created by ekaterina on 9/13/17.
 */
public class B_ExtraData {
    BigDecimal A2;
    String Satz_ID;
    BigDecimal B16;

    public B_ExtraData() {
    }

    public B_ExtraData(BigDecimal a2, String satz_ID, BigDecimal b16) {
        A2 = a2;
        Satz_ID = satz_ID;
        B16 = b16;
    }

    public BigDecimal getA2() {
        return A2;
    }

    public void setA2(BigDecimal a2) {
        A2 = a2;
    }

    public String getSatz_ID() {
        return Satz_ID;
    }

    public void setSatz_ID(String satz_ID) {
        Satz_ID = satz_ID;
    }

    public BigDecimal getB16() {
        return B16;
    }

    public void setB16(BigDecimal b16) {
        B16 = b16;
    }
}
