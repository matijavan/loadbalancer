package LoadBalancer.Model;


import java.io.Serializable;

public class Task implements Serializable {
    private int ID;
    private int length; //in milliseconds

    public Task(int ID, int length) {
        this.ID = ID;
        this.length = length;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
