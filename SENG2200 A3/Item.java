/*
Item.java
Author: Scott Walker (c3232582)

Description:
    Simple item class to move through the production line, that only needs to keep track
    of when it entered a queue. Other stats were initially in here, but were
    not being used at any point during the output of the program.
 */
public class Item {

    // When the item entered its current queue
    private double enterQueueTime;

    // -- Constructor --
    //   Role: Create a new Item
    //   Args: None
    // Return: this
    //
    public Item() {
        enterQueueTime = -1.0;
    }

    // -- Public --
    //   Role: Simulates putting the item into a queue
    //   Args: time - current time of the simulation
    // Return: Void
    //
    public void enterQueue(Double time) {
        enterQueueTime = time;
    }

    // -- Public --
    //   Role: Simulates taking the item out of a queue
    //   Args: None
    // Return: Void
    //
    public void leaveQueue() {
        enterQueueTime = -1.0;
    }

    // -- Public --
    //   Role: Getter
    //   Args: None
    // Return: Double - Time the item entered its queue
    //
    public Double getEnterQueueTime() {
        return enterQueueTime;
    }
}
