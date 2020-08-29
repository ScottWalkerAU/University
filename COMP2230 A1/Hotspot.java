/* 
 * File:   		   Hotspot.java
 * Course: 		   COMP2230
 * Assignment: 	   #1
 *
 * Description:
 *	Hotspot is a simple Getter and Setter class 
 *	that also has a Distance method to calculate the distance
 *	between two hotspots
*/

public class Hotspot {

    private int id; // Hotspot ID number
    private int x; // X-coordinate
    private int y; // Y-coordinate

    // Constructor - Set the ID and (x, y) coords
    public Hotspot(int id, int x, int y) {
        setId(id);
        setX(x);
        setY(y);
    }

    // Distance between two Hotspots
    public double distanceTo(Hotspot h) {
        // Pythagoras Theorem
        double xSquare = Math.pow(getX() - h.getX(), 2);
        double ySquare = Math.pow(getY() - h.getY(), 2);
        return Math.sqrt(xSquare + ySquare);
    }

    // -- Getters and Setters --
    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    private void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    private void setY(int y) {
        this.y = y;
    }
}
