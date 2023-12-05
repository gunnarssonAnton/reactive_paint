package se.miun.dt176g.angu2106.reactive;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import se.miun.dt176g.angu2106.reactive.drawables.Clear;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <h1>Menu</h1> 
 *
 * @author  Anton Gunnarsson
 * @version 1.0
 * @since   2023-10-29
 */
public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	private Observable<EnumShapes> drawMenuObservable;
	private Observable<String> fileMenuObservable;
	private Observable<String> sizeMenuObservable;
	private Observable<Color> colorMenuObservable;
	private Observable<Clear> clearBtnObservable;
	private final JMenu fileMenu = new JMenu("File");
	private final JMenu drawMenu = new JMenu("Draw");
	private final JMenu sizeMenu = new JMenu("Size");
	private final JMenu colorMenu = new JMenu("Color");
	private final JButton clearBtn = new JButton("Clear");


	public MenuBar() {
		this.initMenuBar();
	}

	/**
	 * initialize  the menuBar
	 */
	private void initMenuBar() {
		this.setBackground(Color.white);
		this.clearBtn.setBackground(this.getBackground());
		this.clearBtn.setBorder(BorderFactory.createEmptyBorder());
		this.clearBtn.setFocusable(false);

		this.addMenusToBar(this,this.fileMenu);
		this.addMenusToBar(this,this.drawMenu);
		this.addMenusToBar(this,this.sizeMenu);
		this.addMenusToBar(this,this.colorMenu);
		this.add(this.clearBtn);

		this.setSizeMenuObservable();

		this.setDrawMenuObservable();

		this.setFileMenuObservable();

		this.setColorMenuObservable();

		this.setClearBtnObservable();

	}

	/**
	 * setter for the clearBtnObservable
	 */
	private void setClearBtnObservable() {
		this.clearBtnObservable = Observable.create(emitter ->
				clearBtn.addActionListener(e -> emitter.onNext(new Clear())));
	}

	/**
	 * setter for the colorMenuObservable
	 */
	private void setColorMenuObservable() {
		JMenuItem redMenu = new JMenuItem(Colors.RED.name());
		JMenuItem blueMenu = new JMenuItem(Colors.BLUE.name());
		JMenuItem yellowMenu = new JMenuItem(Colors.YELLOW.name());
		JMenuItem blackMenu = new JMenuItem(Colors.BLACK.name());
		JMenuItem whiteMenu = new JMenuItem(Colors.WHITE.name());
		JMenuItem greenMenu = new JMenuItem(Colors.GREEN.name());

		this.colorMenuObservable = Observable.create(emitter -> {
			redMenu.addActionListener(e ->
					emitter.onNext(Colors.RED.getColor()));
			this.colorMenu.add(redMenu);

			blueMenu.addActionListener(e ->
					emitter.onNext(Colors.BLUE.getColor()));
			this.colorMenu.add(blueMenu);

			yellowMenu.addActionListener(e ->
					emitter.onNext(Colors.YELLOW.getColor()));
			this.colorMenu.add(yellowMenu);

			blackMenu.addActionListener(e ->
					emitter.onNext(Colors.BLACK.getColor()));
			this.colorMenu.add(blackMenu);

			whiteMenu.addActionListener(e ->
					emitter.onNext(Colors.WHITE.getColor()));
			this.colorMenu.add(whiteMenu);

			greenMenu.addActionListener(e ->
					emitter.onNext(Colors.GREEN.getColor()));
			this.colorMenu.add(greenMenu);

		});
	}

	/**
	 * setter for the drawMenuObservable
	 */
	private void setDrawMenuObservable(){
		JMenuItem fhMenuItem = new JMenuItem("Freehand");
		JMenuItem recMenuItem = new JMenuItem("New Rectangle");
		JMenuItem ovalMenuItem = new JMenuItem("New Oval");
		JMenuItem lineMenuItem = new JMenuItem("New Line");
		this.drawMenuObservable = Observable.create(emitter -> {

			fhMenuItem.addActionListener(e ->
					emitter.onNext(EnumShapes.FREEHAND));
			this.drawMenu.add(fhMenuItem);

			recMenuItem.addActionListener(e ->
					emitter.onNext(EnumShapes.RECTANGLE));
			this.drawMenu.add(recMenuItem);

			ovalMenuItem.addActionListener(e ->
					emitter.onNext(EnumShapes.OVAL));
			this.drawMenu.add(ovalMenuItem);

			lineMenuItem.addActionListener(e ->
					emitter.onNext(EnumShapes.LINE));
			this.drawMenu.add(lineMenuItem);

		});
	}

	/**
	 * setter for the sizeMenuObservable
	 */
	private void setSizeMenuObservable(){
		this.sizeMenuObservable = Observable.create(emitter -> {
			Observable.range(1,10)
					.subscribe(resp->{
						JMenuItem item = new JMenuItem(resp.toString());
						item.addActionListener(e ->
								emitter.onNext(resp.toString()));
						this.sizeMenu
								.add(item);
					}).dispose();

		});

	}

	/**
	 * setter for the fileMenuObservable
	 */
	private void setFileMenuObservable(){
		JMenuItem serverHostItem = new JMenuItem("Server Host");
		JMenuItem clientItem = new JMenuItem("Client Connect");
		this.fileMenuObservable = Observable.create(emitter -> {

			serverHostItem.addActionListener(e ->
					emitter.onNext("SERVER"));

			clientItem.addActionListener(e ->
					emitter.onNext("CLIENT"));

			this.fileMenu.add(serverHostItem);
			this.fileMenu.add(clientItem);

		});
	}

	/**
	 * add a menu to a menuBar
	 * @param menuBar a menu bar
	 * @param menu a menu
	 */
	private void addMenusToBar(JMenuBar menuBar, JMenu menu){
		Observable.just(menu)
				.subscribe(menuBar::add)
				.dispose();
	}


	public Observable<EnumShapes> getDrawMenuObservable() {
		return this.drawMenuObservable;
	}
	public Observable<String> getFileMenuObservable() {
		return this.fileMenuObservable;
	}
	public Observable<String> getSizeMenuObservable(){return this.sizeMenuObservable;}
	public Observable<Color> getColorMenuObservable() {
		return this.colorMenuObservable;
	}

	public Observable<Clear> getClearBtnObservable() {
		return this.clearBtnObservable;
	}
}
