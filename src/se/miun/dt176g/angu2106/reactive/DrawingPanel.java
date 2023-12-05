package se.miun.dt176g.angu2106.reactive;

import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import se.miun.dt176g.angu2106.reactive.drawables.*;
import se.miun.dt176g.angu2106.reactive.drawables.Rectangle;
import se.miun.dt176g.angu2106.reactive.drawables.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * <h1>DrawingPanel</h1> Creates a Canvas-object for displaying all graphics
 * already drawn.
 *
 * @author 	--Anton Gunnarsson--
 * @version 1.0
 * @since 	2023-10-29
 */

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel{

	private Drawing drawing;
	private EnumShapes currentEnumShape;

	private Shape currentShape;
	private final PublishSubject<Point> mouseDraggedSubject = PublishSubject.create();
	private final PublishSubject<Point> mousePressedSubject = PublishSubject.create();
	private final PublishSubject<Point> mouseReleasedSubject = PublishSubject.create();

	private PublishSubject<Shape> shapePublishSubject = PublishSubject.create();

	public DrawingPanel() {
		drawing = new Drawing();
		this.setBackground(Colors.WHITE.getColor());
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				mouseDraggedSubject.onNext(new Point(e.getX(), e.getY()));
			}
		});

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mousePressedSubject.onNext(new Point(e.getX(), e.getY()));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				mouseReleasedSubject.onNext(new Point(e.getX(), e.getY()));
			}
		});

		this.initSubjectObservables();
	}

	public PublishSubject<Shape> getShapePublishSubject(){
		return this.shapePublishSubject;
	}

	public void redraw() {
		repaint();
	}


	public void setCurrentShape(EnumShapes enumShape) {
		this.currentEnumShape = enumShape;

		switch (enumShape){
			case LINE: {
				this.currentShape = new Line();
				break;
			}
			case FREEHAND:{
				this.currentShape = new Freehand();
				break;
			}
			case RECTANGLE: {
				this.currentShape = new Rectangle();
				break;
			}
			case OVAL:{
				this.currentShape = new Oval();
				break;
			}
		}
		this.drawing.addShape(this.currentShape);
	}

	public Drawing getDrawing() {
		return this.drawing;
	}

	public void setSize(String size){
		float stroke = Float.parseFloat(size);
		this.currentShape.setStrokeFloat(stroke);
	}

	public void setColor(Color color){
		this.currentShape.setColor(color);
	}

	public void clearPanel(Clear clear){
		this.shapePublishSubject.onNext(clear);
		this.redraw();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.drawing.draw(g);
	}


	/**
	 * initialize observables to mouseDraggedSubject, mousePressedSubject and mouseReleasedSubject
	 */
	private void initSubjectObservables(){

			mouseDraggedSubject
					.subscribeOn(Schedulers.io())
					.subscribe(resp ->{

						if (this.currentEnumShape == EnumShapes.FREEHAND){
							this.currentShape.setStartPoint(resp);
							this.currentShape.addCoords(resp);
							this.redraw();

						}
						else {
							this.currentShape.setEndPoint(resp);
							this.redraw();
						}
					});

			mousePressedSubject
					.subscribeOn(Schedulers.io())
					.subscribe(resp->{
						if (this.currentShape == null){
							JOptionPane.showMessageDialog(this,
									"Pick a shape","ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
						else {
							this.currentShape.setStartPoint(resp);
							this.currentShape.addCoords(resp);
						}

					});

			mouseReleasedSubject
					.subscribeOn(Schedulers.io())
					.subscribe(resp->{
						if (this.currentShape == null){
							JOptionPane.showMessageDialog(this,
									"Pick a new shape","WARNING",
									JOptionPane.WARNING_MESSAGE);
						}
						else {
							this.currentShape.setEndPoint(resp);
							this.currentShape.addCoords(resp);
							this.redraw();

							this.shapePublishSubject.onNext(this.currentShape);
							this.currentShape = null;
						}

					});



	}

}
