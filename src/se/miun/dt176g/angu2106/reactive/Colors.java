package se.miun.dt176g.angu2106.reactive;

import java.awt.*;

public enum Colors {
    RED(Color.RED),
    BLUE(Color.BLUE),
    YELLOW(Color.YELLOW),
    BLACK(Color.BLACK),
    GREEN(Color.GREEN),
    WHITE(Color.WHITE);

    private final Color color;
    Colors(Color color){
        this.color=color;
    }

    public Color getColor() {
        return color;
    }
}
