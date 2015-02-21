import java.awt.Color;

/**
 * A circle has a given radius and a location for its center.  
 * Circles move at a speed that's determined upon instantiation.
 * Direction of motion changes upon wall collision.
 * @author mferraro
 */
public class Circle {
	
	//fields (or instance variables)
	private int radius;
	private int x, y; //center
	private double speed;
	
	/**
	 * angle is stored in radians, which is expected by 
	 * Math.{cos(),sin()}
	 */
	private double angle;
	private Color color;
	private BouncingCircles canvas;
	
	//class variables -- use of 'static' makes these shared across all Circle objects
	private static final int minRadius = 10, maxRadius = 40;
	private static final double minSpeed = 4, maxSpeed = 12;
	private static final double bounceSlowdownFactor = .8;
	private static final Color colorPalette[] = 
		{ Color.BLUE, Color.RED, Color.YELLOW, Color.ORANGE, Color.GREEN }; 
	
	
	public Circle(int x, int y, BouncingCircles canvas) {
		radius = getRandomRadius();
		color = getRandomColor();
		speed = getRandomSpeed();
		angle = getRandomAngle();
		this.x = x;
		this.y = y;
		this.canvas = canvas;
		
		//click location might've been too close to a wall 
		//given this circle's radius, so adjust if needed:
		if ( this.x + radius >= canvas.width ) this.x -= radius;
		else if ( this.x - radius <= 0 ) this.x += radius;
		
		if ( this.y + radius >= canvas.height ) this.y -= radius;
		else if ( this.y - radius <= 0 ) this.y += radius;
	}
	

	private static Color getRandomColor() {
		return colorPalette[ (int)(Math.random() * colorPalette.length ) ];
	}
	
	private static int getRandomRadius() {
		return (int)(Math.random()*(maxRadius - minRadius)) + minRadius;
	}

	private static double getRandomSpeed() {
		return Math.random()*(maxSpeed - minSpeed) + minSpeed;
	}
	
	//return an angle, in radius, between 0 and 2*Math.PI
	private static double getRandomAngle() {
		return Math.random()*2*Math.PI;
	}

	/**
	 * Determine new location for this Circle's center -- modifies fields x and y
	 */
	public void relocate() {
		
		//if distance to top, bottom, left, or right edge too low 
		//(i.e., center to border dist < radius), there's a collision and
		//the angle needs to change to indicate a reflection (bounce)
		
		//either a left- or right-side strike
		if ( x + radius >= canvas.width && ( angle > 1.5*Math.PI || angle < .5*Math.PI ) ) { 
			//right wall strike
			angle = Math.PI - angle;
			speed = bounceSlowdownFactor * speed;
		} else if ( x - radius <= 0 && angle > .5*Math.PI && angle < 1.5*Math.PI ) { 
			//left wall strike
			angle = Math.PI - angle;
			speed = bounceSlowdownFactor * speed;
		}
		
		//either a bottom- or top-side strike
		if ( y + radius >= canvas.height && angle > 0 && angle < 2*Math.PI ) { 
			//bottom wall strike
			angle = 2*Math.PI - angle;
			speed = bounceSlowdownFactor * speed;
		} else if ( y - radius <= 0 && angle > Math.PI ) { 
			//top wall strike
			angle = 2*Math.PI - angle;
			speed = bounceSlowdownFactor * speed;
		}
		
		//in case angle is now >= 2*Math.PI (360 degrees):
		if ( angle >= 2*Math.PI ) {
			angle -= 2*Math.PI;
		}
		
		x += speed * Math.cos(angle);
		y += speed * Math.sin(angle);
		
	}
	
	public void render() {
		//line thickness proportional to radius:
		canvas.strokeWeight( radius / minRadius );
		canvas.stroke(color.getRGB());
		canvas.ellipse(x, y, 2*radius, 2*radius);
	}
	
}
