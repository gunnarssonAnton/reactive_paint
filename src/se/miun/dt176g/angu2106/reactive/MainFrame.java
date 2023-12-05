package se.miun.dt176g.angu2106.reactive;

import javax.swing.*;
import java.awt.*;

/**
 * <h1>MainFrame</h1> 
 * JFrame to contain the rest
 *
 * @author 	--Anton Gunnarsson--
 * @version 1.0
 * @since 	2023-10-19
 */

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private String header;
	private DrawingPanel drawingPanel;

	public MainFrame() {

		// default window-size.
		this.setSize(1200, 900);
		// application closes when the "x" in the upper-right corner is clicked.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.header = "Reactive Paint";
		this.setTitle(header);

		// Changes layout from default to BorderLayout
		this.setLayout(new BorderLayout());
	}

	public void initDrawingPanel(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		this.drawingPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
		this.getContentPane().add(drawingPanel, BorderLayout.CENTER);
	}
}
