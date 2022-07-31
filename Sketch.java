import java.awt.*;
import java.util.*;

/**
 * Sketch class
 * @author Aimen Abdulaziz, Dartmouth College, Winter 2022
 */

public class Sketch {
    private TreeMap<Integer, Shape> idMap;          // id -> shape
    private int shapeCount = 0;                     // num of shapes added

    /**
     * Constructor -- initializes an empty map
     */
    Sketch() {
        idMap = new TreeMap<>();
    }

    /**
     * Adds a shape to the sketch
     * @param shape the shape to be added
     */
    public synchronized void add(Integer id, Shape shape, int count) {
        idMap.put(id, shape); // add id,shape to the map
        shapeCount = count;
    }

    /**
     * Removes a shape from the sketch
     * @param id the ID of the shape
     */
    public synchronized void remove(Integer id) {
        idMap.remove(id);
    }

    /**
     * Draws all the shapes in the sketch
     * @param g graphics
     */
    public synchronized void draw(Graphics g) {
        // iterate through the map from the lowest to the highest key
        for (Integer shapeID : idMap.navigableKeySet()){
            idMap.get(shapeID).draw(g);
        }
    }

    /**
     * Checks if the map is empty
     * @return boolean
     */
    public synchronized boolean isEmpty() {
        return idMap.size() == 0;
    }

    /**
     * Getter method
     * @param id shape id
     * @return shape object
     */
    public synchronized Shape getShape(int id) {
        return idMap.get(id);
    }

    /**
     * Getter method for the shapes map
     * @return map
     */
    public synchronized TreeMap<Integer, Shape> getMap() {
        return idMap;
    }

    /**
     * Getter method for the number of shapes that has been added so far
     * Count does not decrement when a shape is removed
     * @return map
     */
    public synchronized int getShapeCount() {
        return shapeCount;
    }

    /**
     * Returns the id of the top shape if there are more than one bodies on top of each other
     * @param x x coordinate of the point
     * @param y y coordinate of the point
     * @return the id of the shape at that specific point
     */
    public synchronized int topMostShapeAt(int x, int y) {
        // iterate through the map from the highest to the lowest key
        for (Integer id : idMap.descendingKeySet()){
            if (idMap.get(id).contains(x, y)) {
                return id;
            }
        }
        // return -1 if there is no shape at the place where the mouse is pressed (x, y)
        return -1;
    }
}