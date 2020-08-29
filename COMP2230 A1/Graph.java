/* 
 * File:   		   Graph.java
 * Course: 		   COMP2230
 * Assignment: 	   #1
 *
 * Description:
 *	The graph function is in charge of holding all the data
 *	for output when asked for the EmergencyStations
 *	and to print out the matrix of Weighted edges
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Graph {
    private List<Hotspot> hotspots;
    private List<Edge> edges;
    private double[][] weightedEdges;

    // Constructor
    public Graph(List<Hotspot> hotspots) {
        setHotspots(hotspots);
    }

    // Print out the matrix of weighted Edges
    public void print() {
        for (int i = 0; i < getHotspots().size(); i++) {                 // Loop through the x coords
            for (int j = 0; j < getHotspots().size(); j++) {             // Loop through the y coords
                System.out.printf("%.2f  ", getWeightedEdges()[i][j]);   // Display the weighted Edge value at (x, y)
            }
            System.out.println();
        }
    }

    // Return the Emergency Stations - Input number of stations
    public StationList getEmergencyStations(int stationCount) {
        int addedEdges = 0;
        DisjointSet sets = new DisjointSet(getHotspots().size());
        Iterator<Edge> it = getEdges().iterator();

        // Use Kruskal's Algorithm to go through every edge up until
        //  we get to (n-k) edges, where k is the number of stations we want.
        // Standard Kruskal's algorithm goes to (n-1) which has 1 station.
        while (addedEdges < getHotspots().size() - stationCount) {
            // Get the start and end hotspots (id1, id2) of the edge.
            Edge e = it.next();
            int id1 = e.getH1().getId() - 1;
            int id2 = e.getH2().getId() - 1;

            // If the hotspots aren't in the same station, add the edge linking them and make a union.
            if (sets.findSet(id1) != sets.findSet(id2)) {
                sets.union(id1, id2);
                addedEdges++;
            }
        }

        // Create stations list
        StationList stations = new StationList();
        Station[] stationsArray = new Station[getHotspots().size()];
        
        // Loop through each Hotspot
        for (Hotspot h : getHotspots()) {
            int root = sets.findSet(h.getId() - 1);  // Get the root of the current hotspot set
            
            if (stationsArray[root] == null) {       // If the element of the Station array is null
                stationsArray[root] = new Station(); // Set that element as a new Station
                stations.add(stationsArray[root]);   // And add it to the Stations list for later use
            }
            stationsArray[root].addHotspot(h);       // Then add the hotspot to its respective station
        } 

        // Return the StationList
        return stations;
    }

    // Calculate the Weighted edges
    public void calculateWeightedEdges() {
        setEdges(new ArrayList<Edge>());
        setWeightedEdges(new double[getHotspots().size()][getHotspots().size()]);

        // Calculate the weighted Graph of edges in an n*n matrix.
        // For each Hotspot (Row-wise)
        for (int i = 0; i < getHotspots().size(); i++) {
            // Set the Diagonal to 0
            getWeightedEdges()[i][i] = 0;

            // For every other Hotspot larger than i (Column-wise)
            for (int j = i + 1; j < getHotspots().size(); j++) {
                // Get the two hotspots
                Hotspot h1 = getHotspots().get(i);
                Hotspot h2 = getHotspots().get(j);

                getWeightedEdges()[i][j] = h1.distanceTo(h2);        // Set the grid (i, j) to the distance between h1 and h2
                getWeightedEdges()[j][i] = getWeightedEdges()[i][j]; // Then mirror the graph from (i, j) to (j, i)

                // Add the edge between h1 and h2 to the edges list
                getEdges().add(new Edge(h1, h2));
            }
        }

        // Sort the edges List based of weights
        Collections.sort(getEdges());
    }

    // -- Getters and Setters --
    public List<Hotspot> getHotspots() {
        return hotspots;
    }

    public void setHotspots(List<Hotspot> hotspots) {
        this.hotspots = hotspots;
        calculateWeightedEdges();
    }

    public List<Edge> getEdges() {
        return edges;
    }

    private void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public double[][] getWeightedEdges() {
        return weightedEdges;
    }

    private void setWeightedEdges(double[][] weightedEdges) {
        this.weightedEdges = weightedEdges;
    }
}
