package se.miun.dt176g.angu2106.reactive.drawables;


import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Shape</h1> Abstract class which derived classes builds on.
 * <p>
 * This class consists of the attributes common to all geometric shapes.
 * Specific shapes are based on this class.
 * 
 * @author 	--YOUR NAME HERE--
 * @version 1.0
 * @since 	2022-09-08
 */

public abstract class Shape implements Drawable, Serializable {

    protected List<Point> coords = new ArrayList<>();
    protected Point startPoint = new Point(0,0);
    protected Point endPoint = new Point(0,0);
    protected Float strokeFloat = 0F;
    protected Color color = Color.BLACK;
    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public void setStrokeFloat(float stroke){
        this.strokeFloat=stroke;
    }

    public void addCoords(Point point){
        this.coords.add(point);
    }

    public void setColor(Color color){
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {}
}
