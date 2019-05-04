/*
StartStage.java
Author: Scott Walker (c3232582)

Description:
    Implementation of the abstract Stage.java
 */
public class StartStage extends Stage {

    // -- Constructor --
    //   Role: Create a new Stage
    //   Args: line - Reference to the production line
    //         name - Name of the stage
    //         mean - Mean time of processing
    //         range - Range of times processing can take
    //         next - Storage after this stage
    // Return: this
    //
    public StartStage(String name, double mean, double range, Storage next) {
        super(name, mean, range, null, next);
    }

    // -- Public --
    //   Role: Overrides Stage's implentation to throw an error.
    //   Args: None
    // Return: Void
    //
    @Override
    public void starve(double time) {
        throw new RuntimeException("Cannot starve a start stage");
    }

    // -- Public --
    //   Role: Overrides Stage's implentation to throw an error.
    //   Args: None
    // Return: Void
    //
    @Override
    public void unstarve(double time) {
        throw new RuntimeException("Cannot unstarve a start stage");
    }

    // -- Public --
    //   Role: Overrides Stage's implentation to always return false
    //   Args: None
    // Return: boolean
    //
    @Override
    public boolean isStarved() {
        return false;
    }

    // -- Public --
    //   Role: Implement abstract process method to process an item.
    //   Args: None
    // Return: Void
    //
    public void process() {
        updateTimes();
        getNextStorage().insert(this, new Item());
    }
}
