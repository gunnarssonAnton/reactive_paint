package se.miun.dt176g.angu2106.reactive.drawables;

import java.awt.*;


public class Line extends Shape{

//    private Point startPoint;
//    private Point endPoint;
    public Line(){}

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(this.strokeFloat));
        g2.setColor(this.color);
        g2.drawLine(
                this.startPoint.x,
                this.startPoint.y,
                this.endPoint.x,
                this.endPoint.y
        );
    }
}
