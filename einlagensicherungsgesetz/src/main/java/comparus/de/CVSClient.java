package comparus.de;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekaterina on 8/31/17.
 */
public class CVSClient {
    List<String[]> clientsB;
    String[] D;

    public CVSClient() {
    }

    public CVSClient(List<String[]> clientsB, String[] d) {
        this.clientsB = clientsB;
        D = d;
    }

    public List<String[]> getClientsB() {
        return clientsB;
    }

    public void setClientsB(List<String[]> clientsB) {
        this.clientsB = clientsB;
    }

    public String[] getD() {
        return D;
    }

    public void setD(String[] d) {
        D = d;
    }

    public List<String> listStringClientsB() {
        List<String> clientBString = new ArrayList<String>();
        for (String[] el : this.clientsB) {
            clientBString.add(String.join("*", el));
        }
        return clientBString;
    }
}
