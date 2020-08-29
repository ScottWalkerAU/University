/*
 * File:   		   StationList.java
 * Course: 		   COMP2230
 * Assignment: 	   #1
 *
 * Description:
 *	Class for holding stations, with some built-in methods for calculating distances.
*/

import java.util.ArrayList;
import java.util.List;

public class StationList {

    private List<Station> stations;

    // Constructor
    public StationList() {
        setStations(new ArrayList<Station>());
    }

    // Add station to the Stations List
    public void add(Station s) {
        getStations().add(s);
    }

    // Inter cluster distance - It's the smallest distance between any 2 hotspots that aren't in the same cluster
    public Double getInterCD() {
        Double interCD = null;
        // For each Station (s1)
        for (int i = 0; i < getStations().size() - 1; i++) {
            Station s1 = getStations().get(i);

            // Check every other station (s2) with greater ID
            for (int j = i + 1; j < getStations().size(); j++) {
                Station s2 = getStations().get(j);

                for (Hotspot h1 : s1.getHotspots()) {           // For every Hotspot inside of s1
                    for (Hotspot h2 : s2.getHotspots()) {       // For every Hotspot inside of s2
                        double dist = h1.distanceTo(h2);        // Calculate the Distance between h1 and h2
                        if (interCD == null || dist < interCD)  // If the distance < inter Cluster Distance
                            interCD = dist;                     // Update the variable
                    }
                }
            }
        }
        return interCD;    // Return the Inter Cluster Distance
    }

    // Return the Intra Cluster Distance - It's the largest distance between any 2 hotspots that are in the same cluster
    public Double getIntraCD() {
        Double intraCD = null;                      // Set the Intra Cluster Distance to null
        for (Station s : getStations()) {           // For each station
            double intra = s.getIntraCD();          // Get the Intra Cluster Distance
            if (intraCD == null || intra > intraCD) // If it is larger than the current distance
                intraCD = intra;                    // Update the variable
        }
        return intraCD;    // Return the Intra Cluster Distance
    }

    public Double getCDRatio() {
        return getInterCD() / getIntraCD();
    }

    // Print the Stations out with Their ID, Coords and Hotspots
    public void print() {
        System.out.println();
        for (int i = 0; i < getStations().size(); i++) {     // For each Station
            Station s = getStations().get(i);
            String hotspotString = "";                  // String of Hotspots for output
            for (Hotspot h : s.getHotspots()) {         // For each Hotspot
                if (hotspotString.isEmpty())            // Add the hotspot ID to the String of Hotspots
                    hotspotString += h.getId();
                else
                    hotspotString += "," + h.getId();
            }

            // Display the Statistics
            System.out.printf("Station %d:\n", i + 1);
            System.out.printf("Coordinates: (%.2f,%.2f)\n", s.getCentreX(), s.getCentreY());
            System.out.printf("Hotspots: {%s}:\n\n", hotspotString);
        }
    }

    // -- Getters and Setters --
    public List<Station> getStations() {
        return stations;
    }

    private void setStations(List<Station> stations) {
        this.stations = stations;
    }
}
