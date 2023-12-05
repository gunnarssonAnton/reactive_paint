package se.miun.dt176g.angu2106.reactive.drawables;

import java.awt.*;

public class Freehand extends Shape{

    public Freehand(){}
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(this.strokeFloat));
        g2.setColor(this.color);
        int[] pointXArray = this.coords.stream().mapToInt(point-> point.x).toArray();
        int[] pointYArray = this.coords.stream().mapToInt(point-> point.y).toArray();
        g2.drawPolyline(
                pointXArray,
                pointYArray,
                this.coords.size()
        );



    }
}
