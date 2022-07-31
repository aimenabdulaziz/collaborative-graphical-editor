import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * A multi-segment Shape, with straight lines connecting "joint" points -- (x1,y1) to (x2,y2) to (x3,y3) ...
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @author CBK, updated Fall 2016
 * @author Aimen Abdulaziz, Dartmouth College, Winter 2022, implemented Polyline
 */
public class Polyline implements Shape {
	// TODO: YOUR CODE HERE
	private Color color;
	private List<Segment> segments = new ArrayList<Segment>();

	/**
	 * Initial 0-length polyline with only one segment
	 */
	public Polyline(int x1, int y1, Color color) {
		Segment s = new Segment(x1, y1, color);
		segments.add(s);
		this.color = color;
	}

	/**
	 * Complete line with two segments
	 */
	public Polyline(int x1, int y1, int x2, int y2, Color color) {
		Segment s = new Segment(x1, y1, x2, y2, color);
		segments.add(s);
		this.color = color;
	}

	/**
	 * Adds segment to the freehand polyline segments list
	 * @param segment segment object
	 */
	public void addSegment(Segment segment){
		segments.add(segment);
	}

	@Override
	public void moveBy(int dx, int dy) {
		for (Segment seg : segments){
			seg.moveBy(dx, dy);
		}
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		for (Segment seg : segments) {
			seg.setColor(color);
		}
	}

	@Override
	public boolean contains(int x, int y) {
		for (Segment seg : segments){
			if (seg.contains(x, y)) {
				return true;
			}
		}
		// return false after checking all the segments
		return false;
	}

	@Override
	public void draw(Graphics g) {
		for (Segment seg : segments) {
			seg.draw(g);
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("polyline ");
		for (Segment seg : segments){
			result.append(seg.toString()).append(" ");
		}
		return result.toString() + color.getRGB();
	}
}