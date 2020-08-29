/* 
 * File:   		   Station.java
 * Course: 		   COMP2230
 * Assignment: 	   #1
 *
 * Description:
 *	Station holds a list of Hotspots and only calculates the center of the hotspots 
 *	along with the Intra Cluster Distance
*/

import java.util.ArrayList;
import java.util.List;

public class Station {

    List<Hotspot> hotspots;

    // Constructor
    public Station() {
        setHotspots(new ArrayList<Hotspot>());
    }

    // Add hotspot to the List
    public void addHotspot(Hotspot h) {
        getHotspots().add(h);
    }

    // Return the Center X value of all the Hotspots
    public double getCentreX() {
        // For every Hotspot, add all their X values up
        double x = 0.0;
        for (Hotspot h : getHotspots())
            x += h.getX();

        // Then divide by how many Hotspots to find the middle
        return x / getHotspots().size();
    }

    // Return the Center Y value of all the Hotspots
    public double getCentreY() {
        // For every Hotspot, add all their Y values up
        double y = 0.0;
        for (Hotspot h : getHotspots())
            y += h.getY();

        // Then divide by how many Hotspots to find the middle
        return y / getHotspots().size();
    }

    // Return the Intra Cluster Distance - It's the largest distance between any 2 hotspots that are in the same cluster
    public double getIntraCD() {
        double maxDist = 0.0;

        // Compare every hotspot with each other
        for (int i = 0; i < getHotspots().size(); i++) {
            Hotspot h1 = getHotspots().get(i);
            for (int j = i + 1; j < getHotspots().size(); j++) {
                Hotspot h2 = getHotspots().get(j);
                // If the distance between these hotspots is larger than the previous, update maxDist.
                maxDist = Math.max(maxDist, h1.distanceTo(h2));
            }
        }

        return maxDist;
    }

    // Return the local List of Hotspots
    public List<Hotspot> getHotspots() {
        return hotspots;
    }

    // Set the local List of Hotspots
    private void setHotspots(List<Hotspot> hotspots) {
        this.hotspots = hotspots;
    }
}
