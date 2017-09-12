package comparus.de;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekaterina on 8/31/17.
 */
public class CVSClient {
    private String[] clientB;
    private List<String[]> clientsC;
    private String[] D;

    public CVSClient() {
    }

    public CVSClient(List<String[]> clientsC, String[] d) {
        this.clientsC = clientsC;
        D = d;
    }

    public CVSClient(String[] clientB, List<String[]> clientsC, String[] d) {
        this.clientB = clientB;
        this.clientsC = clientsC;
        D = d;
    }

    public String[] getClientB() {
        return clientB;
    }

    public void setClientB(String[] clientB) {
        this.clientB = clientB;
    }

    public List<String[]> getClientsC() {
        return clientsC;
    }

    public void setClientsC(List<String[]> clientsC) {
        this.clientsC = clientsC;
    }

    public String[] getD() {
        return D;
    }

    public void setD(String[] d) {
        D = d;
    }

    public List<String> listStringClientsC() {
        List<String> clientCString = new ArrayList<String>();
        for (String[] el : this.clientsC) {
            clientCString.add(String.join("*", el));
        }
        return clientCString;
    }
}