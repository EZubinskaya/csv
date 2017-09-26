package comparus.de.domen;

/**
 * Created by ekaterina on 9/21/17.
 */
public class B {
    private String BKey;
    private String B1;
    private String B1RealInFile;

    public B() {
    }

    public B(String BKey, String B1, String B1RealInFile) {
        this.BKey = BKey;
        this.B1 = B1;
        this.B1RealInFile = B1RealInFile;
    }

    public String getBKey() {
        return BKey;
    }

    public void setBKey(String BKey) {
        this.BKey = BKey;
    }

    public String getB1() {
        return B1;
    }

    public void setB1(String b1) {
        B1 = b1;
    }

    public String getB1RealInFile() {
        return B1RealInFile;
    }

    public void setB1RealInFile(String b1RealInFile) {
        B1RealInFile = b1RealInFile;
    }
}
