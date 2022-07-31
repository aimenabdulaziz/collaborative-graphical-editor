# Collaborative Graphical Editor

The purpose of this project is to build a collaborative graphical editor — akin to Google Docs. It has the ability of having multiple simultaneous editors of the same document. In both cases, multiple clients connect to a server, and whatever editing any of them does, the others see. So I can add an ellipse, my partner on another computer can recolor and move it, etc.
 
Each client editor has a thread for talking to the sketch server, along with a main thread for user interaction. However, a client doesn't just do the drawing actions on its own. Instead, it requests the server for permission to do an action, and the server then tells all the clients (including the requester) to do it. So rather than actually recoloring a shape, the client tells the server "I'd like to color this shape that color", and the server then tells all the clients to do just that. That way, the server has one unified, consistent global view of the sketch, and keeps all the clients informed.
 
## Code Organization
This project has the following files:
- EchoServer: an alternative server useful for development / debugging
- Editor: client -- handling GUI-based drawing interaction
- EditorCommunicator: for messages to/from the server
- Ellipse: class for drawing an ellipse
- MessageHandler: handles actions sent to the server and then back to the client and the other clients.
- Polyline: class for drawing a freehand sketch (multi-joint segment)
- Rectangle: class for drawing a rectangle
- Segment: class for drawing a line segment
- Shape: interface for a graphical shape (with color), with implementations Ellipse, Polyline, Rectangle, and Segment
- Sketch: synchronized class that holds the current shape list
- SketchServer: central keeper of the master sketch; synchronizing the various Editors
- SketchServerCommunicator: for messages to/from a single editor (one for each such client)
 
## Usage
You have two options two run the program:
1. start `EchoServer.java` and run `Editor.java`. This option will allow you to connect only one client.
2. start `SketchServer.java` and connect multiple clients by running `Editor.java`.  Other people can connect to your server if you give them your IP address. To do so, they should go to `Editor.java` and change serverIP to your IP address. Alternatively, you can also run multiple editors on your machine.
 
IDEs might differ in how you can enable multiple instances of the same program. The following steps work on IntelliJ (as of Aug 2022):
- click on `Run` on the top of your screen -> `Edit configurations…`
- select `Editor` in the left pane
- click on `Modify options` drop down in the right pane
- check `Allow multiple instances`
- click on `Apply` at the bottom.
 
This project was done as an assignment for Dartmouth’s Computer Science course. If you are a professor teaching this course and would like me to make the repository private, please reach out to me [here](mailto:aimen.a.abdulaziz.25@dartmouth.edu). Thanks!

