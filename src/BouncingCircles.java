import processing.core.*;
import java.util.ArrayList;

public class BouncingCircles extends PApplet {

	//satisfy Java (otherwise there's a warning)
	private static final long serialVersionUID = 1L;

	private ArrayList<Circle> circles;
	private boolean circlesArrLocked = false;
	
	/**
	 * method runs once at the start of this app, sets
	 * initial values
	 */
	public void setup() {
		size(600, 600);
		background(255);
		circles = new ArrayList<Circle>();
	}

	/**
	 * method is called each time a mouse click occurs, regardless
	 * of which button (left, middle, right)
	 */
	public void mousePressed() {
		//setting to true prevents draw() from using the circles ArrayList<Circle>,
		//which could generate ConcurrentModificationExceptions
		circlesArrLocked = true;  
		
		//make new circle centered at mouse click location
		circles.add(new Circle(mouseX, mouseY, this));
		
		//safe for draw() to look at circles now that we're done modifying the ArrayList:
		circlesArrLocked = false;
	}
	
	public void draw() {
		
		if ( circlesArrLocked ) { 
			return;
		}
		
		background(255);
		
		for(Circle c : circles) {
			c.render();  //draw this circle
			c.relocate();  //determine next location for circle
			
		}
	
	}

	public static void main(String[] args) {
		PApplet.main(new String[] { "--present", "BouncingCircles" });
	}

}
