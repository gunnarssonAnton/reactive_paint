package se.miun.dt176g.angu2106.reactive.drawables;

import java.awt.*;

/**
 * <h1>ConcreteShape</h1> Creates a Circle-object.
 * Concrete class which extends Shape.
 * In other words, this class represents ONE type of shape
 * i.e. a circle, rectangle, n-sided regular polygon (if that's your thing)
 *
 * @author 	--Anton Gunnarsson--
 * @version 1.0
 * @since 	2023-10-29
 */

public class ConcreteShape extends Shape {

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g; // Type-cast the parameter to Graphics2D.
		   
		// Draw using g2.
		// eg g2.fillOval(int x, int y, int width, int height)
	}

}
