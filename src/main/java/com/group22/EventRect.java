package com.group22;

import java.awt.*;

/**
 * EventRect extends the Rectangle class to include functionality for in-game events.
 * It provides default positions and a flag to indicate if an event has already occurred.
 */
public class EventRect extends Rectangle {
    int eventRectDefaultX, eventRectDefaultY;
    boolean eventDone = false;

}
