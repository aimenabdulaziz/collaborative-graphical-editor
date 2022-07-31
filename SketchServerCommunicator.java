import java.io.*;
import java.net.Socket;
import java.util.TreeMap;

/**
 * Handles communication between the server and one client, for SketchServer
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate SketchServerCommunicator
 * @author Aimen Abdulaziz, Dartmouth College, Winter 2022
 */
public class SketchServerCommunicator extends Thread {
	private Socket sock;					// to talk with client
	private BufferedReader in;				// from client
	private PrintWriter out;				// to client
	private SketchServer server;			// handling communication for

	public SketchServerCommunicator(Socket sock, SketchServer server) {
		this.sock = sock;
		this.server = server;
	}

	/**
	 * Sends a message to the client
	 * @param msg
	 */
	public void send(String msg) {
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the client
	 */
	public void run() {
		try {
			System.out.println("someone connected");

			// Communication channel
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);

			// Tell the new client the current state of the world
			// TODO: YOUR CODE HERE
			TreeMap<Integer, Shape> idMap = server.getSketch().getMap();
			int shapeCount = server.getSketch().getShapeCount();
			for (Integer id : idMap.navigableKeySet()){
				// tell the new clients to add the shapes with the same ID as the current client
				send("add " + id + " " + shapeCount + " " + idMap.get(id).toString());
			}

			// Keep getting and handling messages from the client
			// TODO: YOUR CODE HERE
			String line;
			while ((line = in.readLine()) != null) {
				// print out what has been read
				System.out.println(line);

				// make a new MessageHandler object that will call the appropriate method to handle the requests
				MessageHandler handler = new MessageHandler(server.getSketch());
				handler.decode(line);
				server.broadcast(line); // tell other clients
			}

			// Clean up -- note that also remove self from server's list, so it doesn't broadcast here
			server.removeCommunicator(this);
			out.close();
			in.close();
			sock.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}