/*
ProductionLine.java
Author: Scott Walker (c3232582)

Description:
    Main container class for the production line. Stores stages, storages, and the current
    simulation time. Also takes user input for M, N, and qMAX values.
 */

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ProductionLine {

    // Max simulation time of 10,000,000 time units
    private static final Double MAX_TIME = 10000000.0;

    private List<Stage> stages;
    private List<Storage> storages;

    private Double simulationTime;

    // -- Constructor --
    //   Role: Create a new ProductionLine
    //   Args: None
    // Return: this
    //
    public ProductionLine() {
        stages = new LinkedList<Stage>();
        storages = new LinkedList<Storage>();

        simulationTime = 0.0;
    }

    // -- Public --
    //   Role: Method which starts the production line.
    //   Args: None
    // Return: Void
    //
    public void run() {

        // Input three values
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt(); // Mean
        int n = scanner.nextInt(); // Range
        int qMax = scanner.nextInt(); // Max storage size

        // Create the five storages
        storages.add(new Storage("Q01", qMax));
        storages.add(new Storage("Q12", qMax));
        storages.add(new Storage("Q23", qMax));
        storages.add(new Storage("Q34", qMax));
        storages.add(new Storage("Q45", qMax));

        // Create the stages
        stages.add(new StartStage(   "S0",  m,   n,   storages.get(0)));
        stages.add(new InternalStage("S1",  m,   n,   storages.get(0), storages.get(1)));
        stages.add(new InternalStage("S2a", m*2, n*2, storages.get(1), storages.get(2)));
        stages.add(new InternalStage("S2b", m*2, n*2, storages.get(1), storages.get(2)));
        stages.add(new InternalStage("S3",  m,   n,   storages.get(2), storages.get(3)));
        stages.add(new InternalStage("S4a", m*2, n*2, storages.get(3), storages.get(4)));
        stages.add(new InternalStage("S4b", m*2, n*2, storages.get(3), storages.get(4)));
        stages.add(new EndStage(     "S5",  m,   n,   storages.get(4)));

        // Add links in each storage to their next and previous stages.
        addStorageReferences();

        // Run the simulation
        while (simulationTime < MAX_TIME) {
            processNextStage();
        }

        // Print the statistics
        System.out.println("Statistics for each stage:");
        for (Stage stage : stages)
            System.out.println(stage);

        System.out.println("\nStatistics for each storage:");
        for (Storage storage : storages)
            System.out.println(storage);

    }

    // -- Private --
    //   Role: Add references in each storage to their next/previous stages.
    //   Args: None
    // Return: Void
    //
    private void addStorageReferences() {
        for (Stage stage : stages) {
            if (!(stage instanceof StartStage))
                stage.getPreviousStorage().addNextStage(stage);

            if (!(stage instanceof EndStage))
                stage.getNextStorage().addPreviousStage(stage);
        }
    }

    // -- Private --
    //   Role: Gets the next stage available to process an item, and processes it.
    //   Args: None
    // Return: Void
    //
    private void processNextStage() {
        Stage nearestStage = getNearestStage();
        simulationTime = nearestStage.getCompletionTime();
        nearestStage.process();
    }

    // -- Private --
    //   Role: Gets the nearest stage available to process an item.
    //   Args: None
    // Return: Stage
    //
    private Stage getNearestStage() {
        Stage nearestStage = null;
        for (Stage stage : stages) {
            // If the stage can't process, move onto the next one.
            if (!stage.canProcess())
                continue;

            // If the stage finishes before the current nearest stage, make it the nearest stage.
            if (nearestStage == null || stage.getCompletionTime() < nearestStage.getCompletionTime()) {
                nearestStage = stage;
            }
        }

        return nearestStage;
    }

}
