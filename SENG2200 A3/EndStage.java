/*
EndStage.java
Author: Scott Walker (c3232582)

Description:
    Implementation of the abstract Stage.java
 */
public class EndStage extends Stage {

    // -- Constructor --
    //   Role: Create a new Stage, set to starving
    //   Args: line - Reference to the production line
    //         name - Name of the stage
    //         mean - Mean time of processing
    //         range - Range of times processing can take
    //         previous - Storage before this stage
    // Return: this
    //
    public EndStage(String name, double mean, double range, Storage previous) {
        super(name, mean, range, previous, null);
        super.starve(0.0);
    }

    // -- Public --
    //   Role: Overrides Stage's implentation to throw an error.
    //   Args: None
    // Return: Void
    //
    @Override
    public void block(double time) {
        throw new RuntimeException("Cannot block an end stage");
    }

    // -- Public --
    //   Role: Overrides Stage's implentation to throw an error.
    //   Args: None
    // Return: Void
    //
    @Override
    public void unblock(double time) {
        throw new RuntimeException("Cannot unblock an end stage");
    }

    // -- Public --
    //   Role: Overrides Stage's implentation to always return false
    //   Args: None
    // Return: boolean
    //
    @Override
    public boolean isBlocked() {
        return false;
    }

    // -- Public --
    //   Role: Implement abstract process method to process an item.
    //   Args: None
    // Return: Void
    //
    public void process() {
        double startTime = getTime();

        // Update times and remove item from storage.
        updateTimes();
        getPreviousStorage().retrieve(startTime, this);
    }
}
