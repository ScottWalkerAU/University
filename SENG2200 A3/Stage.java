/*
Stage.java
Author: Scott Walker (c3232582)

Description:
    Abstract class that processes items by taking them out of their previous storage and putting
    them into the storage following it.
 */
import java.util.Random;

public abstract class Stage {

    private String name;

    private double mean; // M
    private double range; // N

    private double time; // Current time of the simulation this stage is at.
    private double productionTime; // How long it will take to process the next item.

    private Storage previous; // Storage before this stage
    private Storage next; // Storage after this stage

    private boolean blocked; // Whether or not the stage is blocked
    private boolean starved; // Similarly, starved.

    private double timeBlocked; // When the stage became blocked
    private double totalTimeBlocked; // How long the stage has been blocked for in total
    private double timeStarved; // Similarly, starved
    private double totalTimeStarved; // Similarly, starved
    private double totalProductionTime; // How much time the stage has spent in actual production

    // -- Constructor --
    //   Role: Create a new Stage
    //   Args: line - Reference to the production line
    //         name - Name of the stage
    //         mean - Mean time of processing
    //         range - Range of times processing can take
    //         previous - Storage before this stage
    //         next - Storage after this stage
    // Return: this
    //
    public Stage(String name, double mean, double range, Storage previous, Storage next) {
        this.name = name;
        this.mean = mean;
        this.range = range;
        this.previous = previous;
        this.next = next;

        // Set data to initial values.
        productionTime = -1.0;
        blocked = false;
        starved = false;
        timeBlocked = -1.0;
        totalTimeBlocked = 0.0;
        timeStarved = -1.0;
        totalTimeStarved = 0.0;
        totalProductionTime = 0.0;
    }

    // -- Public Abstract --
    //   Role: Process an item in the stage
    //   Args: None
    // Return: Void
    //
    public abstract void process();

    // -- Public --
    //   Role: Block the stage
    //   Args: time - current time of the simulation
    // Return: void
    //
    public void block(double time) {
        if (time > getTime())
            setTime(time);

        blocked = true;
        timeBlocked = getTime();
    }

    // -- Public --
    //   Role: Starve the stage
    //   Args: time - current time of the simulation
    // Return: void
    //
    public void starve(double time) {
        if (time > getTime())
            setTime(time);

        starved = true;
        timeStarved = getTime();
    }

    // -- Public --
    //   Role: Unblock the stage
    //   Args: time - current time of the simulation
    // Return: void
    //
    public void unblock(double time) {
        if (!blocked)
            return;

        blocked = false;
        if (time >= getTime()) {
            setTime(time);
            totalTimeBlocked += getTime() - timeBlocked;
        }
    }

    // -- Public --
    //   Role: Unstarve the stage
    //   Args: time - current time of the simulation
    // Return: void
    //
    public void unstarve(double time) {
        if (!starved)
            return;

        starved = false;
        if (time >= getTime()) {
            setTime(time);
            totalTimeStarved += getTime() - timeStarved;
        }
    }

    // -- Protected --
    //   Role: Set stages time to the time of completion, and reset the production variable.
    //   Args: None
    // Return: void
    //
    protected void updateTimes() {
        // Update the time stats and reset production time to a negative value forcing it to be recalculated.
        totalProductionTime += getProductionTime();
        setTime(getCompletionTime());
        productionTime = -1.0;
    }

    // -- Private --
    //   Role: How long production will take for the next item. Calculated once per item.
    //   Args: None
    // Return: double - Production time.
    //
    private double getProductionTime() {
        // Calculate the production time once per production, else sorting stages by completion time is meaningless.
        if (productionTime <= 0.0) {
            Random r = new Random();
            double d = r.nextDouble();
            productionTime = mean + range * (d - 0.5);
        }

        return productionTime;
    }

    // -- Public --
    //   Role: Getter
    //   Args: None
    // Return: boolean - Is the stage blocked.
    //
    public boolean isBlocked() {
        return blocked;
    }

    // -- Public --
    //   Role: Getter
    //   Args: None
    // Return: boolean - Is the stage starved.
    //
    public boolean isStarved() {
        return starved;
    }

    // -- Public --
    //   Role: Getter
    //   Args: None
    // Return: boolean - Can the stage process an item
    //
    public boolean canProcess() {
        return !(isStarved() || isBlocked());
    }

    // -- Public --
    //   Role: Getter
    //   Args: None
    // Return: String - Name of the stage
    //
    public String getName() {
        return name;
    }

    // -- Public --
    //   Role: Getter
    //   Args: None
    // Return: double - When the next item will be completed
    //
    public double getCompletionTime() {
        return getTime() + getProductionTime();
    }

    // -- Public --
    //   Role: Getter
    //   Args: None
    // Return: double - current time of the simulation
    //
    public double getTime() {
        return time;
    }

    // -- Public --
    //   Role: Setter
    //   Args: time - New simulation time
    // Return: void
    //
    private void setTime(double time) {
        this.time = time;
    }

    // -- Public --
    //   Role: Getter
    //   Args: None
    // Return: Storage - storage before this stage
    //
    public Storage getPreviousStorage() {
        return previous;
    }

    // -- Public --
    //   Role: Getter
    //   Args: None
    // Return: Storage - storage after this stage
    //
    public Storage getNextStorage() {
        return next;
    }

    // -- Public --
    //   Role: Getter
    //   Args: None
    // Return: double - how long the stage has been blocked
    //
    public double getTotalTimeBlocked() {
        return totalTimeBlocked;
    }

    // -- Public --
    //   Role: Getter
    //   Args: None
    // Return: double - how long the stage has been starved
    //
    public double getTotalTimeStarved() {
        return totalTimeStarved;
    }

    // -- Public --
    //   Role: Getter
    //   Args: None
    // Return: double - how long the stage has been in production
    //
    public double getTotalProductionTime() {
        return totalProductionTime;
    }

    // -- Public --
    //   Role: Getter
    //   Args: None
    // Return: String - Printable version of this stage
    //
    @Override
    public String toString() {
        double starved = getTotalTimeStarved()/getTime()*100;
        double blocked = getTotalTimeBlocked()/getTime()*100;
        double prod = getTotalProductionTime()/getTime()*100;
        return String.format("%s\t| Time producing: %4.2f%% | Time blocked: %4.2f%% | Time starved: %4.2f%%",
                getName(), prod, blocked, starved);
    }
}
