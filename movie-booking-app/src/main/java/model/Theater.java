/**
 * 
 */
package model;

/**
 * 
 */
public class Theater {
    private int id;
    private String type;
    private int seatCount;

    public Theater(int id, String type, int seatCount) {
        this.id = id;
        this.type = type;
        this.seatCount = seatCount;
    }

    public int getId() { return id; }
    public String getType() { return type; }
    public int getSeatCount() { return seatCount; }
}
