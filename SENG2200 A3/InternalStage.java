/*
InternalStage.java
Author: Scott Walker (c3232582)

Description:
    Implementation of the abstract Stage.java
 */
public class InternalStage extends Stage {

    // -- Constructor --
    //   Role: Create a new Stage, set to starving
    //   Args: line - Reference to the production line
    //         name - Name of the stage
    //         mean - Mean time of processing
    //         range - Range of times processing can take
    //         previous - Storage before this stage
    //         next - Storage after this stage
    // Return: this
    //
    public InternalStage(String name, double mean, double range, Storage previous, Storage next) {
        super(name, mean, range, previous, next);
        super.starve(0.0);
    }

    // -- Public --
    //   Role: Implement abstract process method to process an item.
    //   Args: None
    // Return: Void
    //
    public void process() {
        double startTime = getTime();

        updateTimes();
        Item item = getPreviousStorage().retrieve(startTime, this);
        getNextStorage().insert(this, item);
    }
}
