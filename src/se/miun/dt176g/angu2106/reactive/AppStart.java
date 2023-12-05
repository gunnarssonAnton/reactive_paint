package se.miun.dt176g.angu2106.reactive;

import javax.swing.*;


/**
* <h1>AppStart</h1>
*
* @author  Anton Gunnarsson
* @version 1.0
* @since   2023-10-29
*/
public class AppStart {

	public static void main(String[] args) {
		
		// Make sure GUI is created on the event dispatching thread
		SwingUtilities.invokeLater(() ->{
					MainFrameController mainFrameController = new MainFrameController();
					mainFrameController.initMainFrame();
				});

	}
}