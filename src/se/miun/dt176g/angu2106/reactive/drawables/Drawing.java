package se.miun.dt176g.angu2106.reactive.drawables;


import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * <h1>Drawing</h1> 
 * Let this class store an arbitrary number of AbstractShape-objects in
 * some kind of container. 
 *
 * @author 	Anton Gunnarsson
 * @version 1.0
 * @since 	2023-10-29
 */


public class Drawing implements Drawable {



	// private SomeContainer shapes;
	private final List<Shape> shapes = new CopyOnWriteArrayList<>();

	/**
	 * <h1>addShape</h1> add a shape to the "SomeContainer shapes"
	 * 
	 * @param s a {@link Shape} object.
	 */
	public void addShape(Shape s) {
		this.shapes.add(s);
	}

	/**
	 * <h1>clearShape</h1> clears the shapes container
	 *
	 */
	public void clearShapes(){
		this.shapes.clear();
	}

	@Override
	public void draw(Graphics g) {
		// iterate over all shapes and draw them using the draw-method found in
		// each concrete subclass.
		Observable.fromIterable(shapes)
				.subscribe(
						shape -> shape.draw(g),
						Throwable::printStackTrace);
	}

}
