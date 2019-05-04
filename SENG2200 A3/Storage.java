/*
Storage.java
Author: Scott Walker (c3232582)

Description:
    This class holds a queue of items to simulate a storage between 2 or more stages.
    It holds two lists of storages on either side of it, and attributes for calculation outputs.
    This class (un)starves and (un)blocks stages with respect to the size of the queue it contains.
 */
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Storage {

    private String name;
    private int maxSize;
    private Queue<Item> queue;

    // Lists of stages either side of the storage
    private List<Stage> nextStages;
    private List<Stage> previousStages;

    // Attributes for how long an item waits in this queue on average.
    private int itemsProcessed;
    private double averageWaitTime;

    // Attributes for how many items are in this queue on average at any time.
    private double timeLastWeightedAddition;
    private double weightedItemAverage;

    // -- Constructor --
    //   Role: Create a new ProductionLine
    //   Args: String name - Name of the queue
    //         Int maxSize - Maximum size of the queue inside this storage
    // Return: this
    //
    public Storage(String name, Integer maxSize) {
        this.name = name;
        this.maxSize = maxSize;

        // Create empty lists and set initial data to zero.
        queue = new LinkedList<Item>();
        nextStages = new LinkedList<Stage>();
        previousStages = new LinkedList<Stage>();

        itemsProcessed = 0;
        averageWaitTime = 0.0;
    }

    // -- Public --
    //   Role: Inserts an item into the queue.
    //   Args: Stage stage - Stage that inserted the item.
    //         Item item - Item to be inserted.
    // Return: void
    //
    public void insert(Stage stage, Item item) {
        // Already full. Should never be called.
        if (isFull())
            throw new RuntimeException("Trying to insert into a full storage");

        double completionTime = stage.getTime();

        // Update starved stages before inserting the item.
        for (Stage next : nextStages) {
            if (next.isStarved()) {
                next.unstarve(completionTime); // Unstarve at previous stages completion time.
            }
        }

        // -- Item processing --
        extendWeightedAverage(completionTime);
        queue.add(item);
        item.enterQueue(completionTime);
        // -- Finish item processing --

        // Block stages if the queue is now full.
        for (Stage prev : previousStages) {
            if (isFull()) {
                prev.block(prev.getTime()); // Block at the inserting stages' finish, to block parallel lines.
            }
        }

    }

    // -- Public --
    //   Role: Takes an item out of the queue
    //   Args: double startTime - The time the item was retrieved.
    //         Stage stage - Stage that retrieved the item.
    // Return: Item - The item retrieved from the head of the queue.
    //
    public Item retrieve(double startTime, Stage stage) {
        // Already empty. Should never be called.
        if (isEmpty())
            throw new RuntimeException("Trying to retrieve from an empty storage");

        // Update blocked stages before removing the item.
        for (Stage prev : previousStages) {
            if (prev.isBlocked()) {
                prev.unblock(startTime);
            }
        }

        // -- Item processing --
        Item item = queue.peek();
        updateAverageItemWaitTime(startTime, item);
        removeFromWeightedAverage(stage.getTime(), stage.getTime() - startTime);
        queue.poll();
        item.leaveQueue();
        // -- Finish item processing --

        // Starve stages if the queue is now full.
        for (Stage next : nextStages) {
            if (isEmpty()) {
                next.starve(next.getTime());
            } else {
                // Set the start production times for stages directly following this queue to
                // the enter queue time of the next item.
                double nextItemTime = peek().getEnterQueueTime();
                if (next.getTime() < nextItemTime) {
                    next.starve(next.getTime());
                    next.unstarve(nextItemTime);
                }
            }
        }

        return item;
    }

    // -- Private internal methods -- //

    // -- Private --
    //   Role: Updates the average wait time for an item in the queue
    //   Args: double time - Current time of the simulation.
    //         Item item - Item that was just removed.
    // Return: void
    //
    private void updateAverageItemWaitTime(double time, Item item) {
        // Expand and reduce the average value in order to add a new value into it.
        double itemWaitTime = time - item.getEnterQueueTime();
        averageWaitTime = (itemsProcessed * averageWaitTime + itemWaitTime) / ++itemsProcessed;
    }

    // -- Private --
    //   Role: Removes a time from the weighted average
    //   Args: double time - Current time of the simulation.
    //         double duration - How long to remove one item's worth of time.
    // Return: void
    //
    private void removeFromWeightedAverage(double time, double duration) {
        extendWeightedAverage(time);
        weightedItemAverage -= duration;
    }

    // -- Private --
    //   Role: Adds weighted time onto the current value, and extends when it was last added to.
    //   Args: double time - Current time of the simulation.
    // Return: void
    //
    private void extendWeightedAverage(double time) {
        // Only if current time is greater than when it was last extended.
        if (time > timeLastWeightedAddition) {
            weightedItemAverage += queue.size() * (time - timeLastWeightedAddition);
            timeLastWeightedAddition = time;
        }
    }

    // -- Getters, setters, and other information queries -- //

    // -- Public --
    //   Role: Getter
    // Return: int - Size of the queue
    //
    public int size() {
        return queue.size();
    }

    // -- Public --
    //   Role: Getter
    // Return: boolean - If the queue is full or not
    //
    public boolean isFull() {
        return size() == maxSize;
    }

    // -- Public --
    //   Role: Getter
    // Return: boolean - If the queue is empty or not
    //
    public boolean isEmpty() {
        return size() == 0;
    }

    // -- Public --
    //   Role: Getter
    // Return: Item - Current item at the head of the queue. Does not remove. Returns null if no item.
    //
    public Item peek() {
        return queue.peek();
    }

    // -- Public --
    //   Role: Adds stage after this storage
    //   Args: Stage stage - Stage to be added
    // Return: void
    //
    public void addNextStage(Stage stage) {
        nextStages.add(stage);
    }

    // -- Public --
    //   Role: Adds stage before this storage
    //   Args: Stage stage - Stage to be added
    // Return: void
    //
    public void addPreviousStage(Stage stage) {
        previousStages.add(stage);
    }

    // -- Public --
    //   Role: Getter
    // Return: String - Name of this storage
    //
    public String getName() {
        return name;
    }

    // -- Public --
    //   Role: Getter
    // Return: double - The average wait time of an item in this storage
    //
    public double getAverageWaitTime() {
        return averageWaitTime;
    }

    // -- Public --
    //   Role: Getter
    // Return: double - The average amount of items in the queue at any one time
    //
    public double getAverageItemsInQueue() {
        return weightedItemAverage / timeLastWeightedAddition;
    }

    // -- Public --
    //   Role: Getter
    // Return: String - The printed information about this storage.
    //
    @Override
    public String toString() {
        return String.format("%s | Average item wait time: %4.2f TU | Average items in queue: %4.2f",
                getName(), getAverageWaitTime(), getAverageItemsInQueue());
    }
}
