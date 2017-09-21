package comparus.de;

/**
 * Created by ekaterina on 9/21/17.
 */
public class B {
    private String BKey;
    private String B1;

    public B() {
    }

    public B(String BKey, String b1) {
        this.BKey = BKey;
        B1 = b1;
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
}
