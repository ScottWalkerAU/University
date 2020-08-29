/* 
 * File:   		   Edge.java
 * Course: 		   COMP2230
 * Assignment: 	   #1
 *
 * Description:
 *	The Edge class is used to get the Weight (Distance) between two Hotspots
 *	and has a compare function between 2 edges
*/

public class Edge implements Comparable<Edge> {

    private Hotspot h1;
    private Hotspot h2;

    public Edge(Hotspot h1, Hotspot h2) {
        // Make h1 the lesser value hotspot
        if (h1.getId() < h2.getId()) {
            setH1(h1);
            setH2(h2);
        } else {
            setH1(h2);
            setH2(h1);
        }
    }

    // Return the Weight of the edge (Distance between Hotspots)
    public double getWeight() {
        return getH1().distanceTo(getH2());
    }

    // Compare Weights of edges
    // Cannot remember why we're masking the actual value of `test`. Probably no real reason
    public int compareTo(Edge e) {
        double test = getWeight() - e.getWeight();

        // If they're equal, return 0
        if (test == 0)
            return 0;
        
        // If current Weight > e Weight return 1, else return -1
        return (test < 0 ? -1 : 1);
    }

    // -- Getters and Setters --
    public Hotspot getH2() {
        return h2;
    }

    private void setH2(Hotspot h2) {
        this.h2 = h2;
    }

    public Hotspot getH1() {
        return h1;
    }

    private void setH1(Hotspot h1) {
        this.h1 = h1;
    }
}
