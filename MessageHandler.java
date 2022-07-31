import java.awt.*;

/**
 * Contain common request handling methods shared by EditorCommunicator and SketchServerCommunicator
 * @author Aimen Abdulaziz, Dartmouth College, Winter 2022
 */
public class MessageHandler {
    Sketch sketch;                     // current drawing canvas
    Shape curr = null;				   // current shape (if any) being modified

    /**
     * Constructor for single client communication
     * @param sketch drawing canvas
     */
    public MessageHandler(Sketch sketch) {
        this.sketch = sketch;
    }

    /**
     * Sends editor requests to the server
     * @param request string request received
     */
    public void decode(String request){
        String[] msg = request.split(" ");
        switch (msg[0]) {
            case "add" -> handleAddShape(msg);
            case "recolor" -> handleRecolor(msg);
            case "delete" -> handleDelete(msg);
            case "move" -> handleMove(msg);
            default -> {
                System.out.println(request + " is invalid");
                System.out.println("Invalid command");
            }
        }
    }

    /**
     * Handles all requests to draw a new shape in the sketch
     * @param msg request received from the editor class
     */
    public void handleAddShape (String[] msg){
        int id = Integer.parseInt(msg[1]);
        int shapeCount = Integer.parseInt(msg[2]);
        if (msg[3].equals("ellipse") || msg[3].equals("rectangle") || msg[3].equals("segment")){
            // parse data from the received request
            int x1 = Integer.parseInt(msg[4]);
            int y1 = Integer.parseInt(msg[5]);
            int x2 = Integer.parseInt(msg[6]);
            int y2 = Integer.parseInt(msg[7]);
            int colorRGB = Integer.parseInt(msg[8]);

            // add the shapes accordingly
            if (msg[3].equals("ellipse")) {
                curr = new Ellipse(x1, y1, x2, y2, new Color(colorRGB));
                sketch.add(id, curr, shapeCount); // add to the map
            }
            else if (msg[3].equals("rectangle")) {
                curr = new Rectangle(x1, y1, x2, y2, new Color(colorRGB));
                sketch.add(id, curr, shapeCount);
            }
            else {
                curr = new Segment(x1, y1, x2, y2, new Color(colorRGB));
                sketch.add(id, curr, shapeCount);
            }
        }

        // parse data and add polyline
        else if (msg[3].equals("polyline")){
            Polyline polyline = new Polyline(Integer.parseInt(msg[5]), Integer.parseInt(msg[6]),
                    Integer.parseInt(msg[7]), Integer.parseInt(msg[8]), new Color(Integer.parseInt(msg[8])));
            for (int i = 10; i < msg.length; i+=6) {
                if (msg[i].equals("segment")) {
                    polyline.addSegment(new Segment(Integer.parseInt(msg[i+1]), Integer.parseInt(msg[i+2]), Integer.parseInt(msg[i+3]), Integer.parseInt(msg[i+4]), new Color(Integer.parseInt(msg[i+5]))));
                }
            }
            curr = polyline;
            sketch.add(id, curr, shapeCount);
        }
    }

    /**
     * Handles recolor request received from one of the client
     * @param msg request received from the editor class
     */
    public void handleRecolor(String[] msg){
        // parse data from the received request
        int shapeID = Integer.parseInt(msg[1]);
        int colorRGB = Integer.parseInt(msg[2]);
        curr = sketch.getShape(shapeID);

        // recolor
        if (curr != null) {
            curr.setColor(new Color(colorRGB));
        }
    }

    /**
     * Handles delete request received from one of the client
     * @param msg request received from the editor class
     */
    public void handleDelete(String[] msg){
        // parse datum from the received request
        int shapeID = Integer.parseInt(msg[1]);

        // get the current shape
        sketch.remove(shapeID);
    }

    /**
     * Handles move request received from one of the client
     * @param msg request received from the editor class
     */
    public void handleMove(String[] msg){
        // parse data from the received request
        int shapeID = Integer.parseInt(msg[1]);
        int dx = Integer.parseInt(msg[2]);
        int dy = Integer.parseInt(msg[3]);

        // get the current shape
        curr = sketch.getShape(shapeID);

        // move the shape
        if (curr != null) {
            curr.moveBy(dx, dy);
        }
    }
}
