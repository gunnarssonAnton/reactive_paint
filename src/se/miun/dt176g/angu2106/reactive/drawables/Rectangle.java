package se.miun.dt176g.angu2106.reactive.drawables;

import java.awt.*;

public class Rectangle extends Shape{

    public Rectangle(){}

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int width = Math.abs(this.endPoint.x - this.startPoint.x);
        int height = Math.abs(this.endPoint.y - this.startPoint.y);
        int startX = Math.min(startPoint.x,endPoint.x);
        g2.setColor(this.color);
        g2.setStroke(new BasicStroke(this.strokeFloat));
        g2.drawRect(
                startX,
                this.startPoint.y,
                width,
                height);
    }
}
