/* 
 * File:   		   Main.java
 * Course: 		   COMP2230
 * Assignment: 	   #1
 *
 * Description:
 *	Main is the entry point for the program.
 *	It reads the input file and all the Hotspots inside that file.
 *  Asks the user for how many Emergency Stations they want
 *  Then creates a graph of all the hotspots and displays all the statistics
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner;
        List<Hotspot> hotspots = new ArrayList<Hotspot>();

        // Read the Input file
        try {
            scanner = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find input.txt, please try again");
            return;
        }

        // Read in each Hotspot and create a new Hotspot Object
        while (scanner.hasNext()) {
            int id = scanner.nextInt();
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            hotspots.add(new Hotspot(id, x, y));
        }

        // Create a new Graph Object and pass through the List of Hotspots
        Graph graph = new Graph(hotspots);
        // Start the main program
        kruskalsClustering(graph);
    }

    private static void kruskalsClustering(Graph graph) {
        // Greet the User and prompt for the number of Clusters
        Scanner scanner = new Scanner(System.in);
        System.out.print("Hello and welcome to Kruskal's Clusting!\n\n");
        System.out.printf("There are %d hotspots.\n\n", graph.getHotspots().size());
        System.out.print("The weighted graph of hotspots:\n\n");
        // Display the current weighted graph from the given hotspots
        graph.print();
        System.out.println();

        // The main loop of the program asking for how many Emergency Stations they would like
        int stations;
        while (true) {
            System.out.println("\nHow many emergency stations would you like?");
            System.out.printf("(Enter a number between 1 and %d to place the emergency stations.\n", graph.getHotspots().size());
            System.out.println("Enter -1 to automatically select the number of emergency stations.");
            System.out.println("Enter 0 to exit.)\n");

            // Read the input from the User on how many Emergency Stations they would like
            try {
                stations = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.print("\nEntry not valid.\n");
                continue;
            }

            // If User Input equals '0' then exit the program
            if (stations == 0) {
                break;

            }
            // If User Input equals '-1' then the program will automatically decide the optimal number of Emergency Stations
            else if (stations == -1) {

                if (graph.getHotspots().size() < 3) {
                    System.out.println("There are not enough hotspots to perform this function.");
                    continue;
                }

                // Values of k: 2 <= k <= n-1, maximise the ratio.
                // Start at k=2, and loop for k=3 to n-1
                StationList list = graph.getEmergencyStations(2);
                StationList best = list;
                double bestRatio = list.getCDRatio();

                // Looping from k = 3 to n-1 to find the bestRatio possible
                for (int i = 3; i < graph.getHotspots().size() - 1; i++) {
                    list = graph.getEmergencyStations(i);   // Get the graph of 'i' EmergencyStations
                    double ratio = list.getCDRatio();       // Find the ratio of that graph
                    if (ratio > bestRatio) {                // If it is better than the default k = 2 OR current bestRatio
                        best = list;                        // Update the best StationList to the current one
                        bestRatio = ratio;                  // And remember the current bestRatio value
                    }
                }

                // Print out the best StationList along with the best ratio value
                best.print();
                System.out.printf("\nInterCD/IntraCD = %.2f\n", bestRatio);

            }
            // If the User inputed a desired number of stations - calculate that graph of 'n' stations
            else if (stations >= 1 && stations <= graph.getHotspots().size()) {
                StationList list = graph.getEmergencyStations(stations);    // Get the StationList of 'n' Emergency Stations
                list.print();   // Print out the list of Hotspots

                Double interCD = list.getInterCD(); // Calculate the inter cluster distance
                if (interCD == null)    // If the inter cluster distance is null, print that the graph is Not Applicable
                    System.out.print("Inter-clustering distance: Not Applicable.\n");
                else    // Otherwise output the inter cluster distance
                    System.out.printf("Inter-clustering distance: %.2f\n", interCD);


            }
            // Numerical input is invalid
            else {
                System.out.print("\nEntry not valid.\n");
            }

        } // End of while loop

        System.out.print("\nThank you for using Kruskal's Clustering. Bye.");
    }
}
