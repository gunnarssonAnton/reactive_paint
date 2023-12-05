package se.miun.dt176g.angu2106.reactive;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import se.miun.dt176g.angu2106.reactive.connection.SocketReceiver;
import se.miun.dt176g.angu2106.reactive.drawables.Clear;
import se.miun.dt176g.angu2106.reactive.drawables.Shape;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <h1>MainFrameController</h1>
 *
 * @author  Anton Gunnarsson
 * @version 1.0
 * @since   2023-10-29
 */
public class MainFrameController implements SocketReceiver {
    private final int serverPort = 6666;
    private MainFrame mainFrame;
    private MenuBar menuBar;
    private final DrawingPanel drawingPanel = new DrawingPanel();
    public MainFrameController(){
        this.mainFrame = new MainFrame();
        this.mainFrame.initDrawingPanel(this.drawingPanel);
        this.menuBar = new MenuBar();


        this.menuBar.getColorMenuObservable()
                    .subscribe(this.drawingPanel::setColor);

        this.menuBar.getSizeMenuObservable()
                    .subscribe(this.drawingPanel::setSize);

        this.menuBar
                .getDrawMenuObservable()
                .subscribe(this.drawingPanel::setCurrentShape);

        this.menuBar.getClearBtnObservable()
                .subscribe(this.drawingPanel::clearPanel);

        this.menuBar
                .getFileMenuObservable()
                .subscribe(file -> {
                    if (file.equals("SERVER")){
                        this.serverConnection();
                    }
                    if (file.equals("CLIENT")){
                        this.clientConnection();
                    }
                });


        this.mainFrame.setJMenuBar(menuBar);
    }

    /**
     * ServerConnection
     * act as a server to the application
     */
    private void serverConnection(){

        List<Socket> sockets = new ArrayList<>();
        List<Shape> oldShapes= new CopyOnWriteArrayList<>();
        mainFrame.setSize(200,100);
        mainFrame.setTitle("Server Host");
        Observable<Socket> obsSocket = Observable.create(emitter -> {

            try (ServerSocket serverSocket = new ServerSocket(this.serverPort)) {

                while (!serverSocket.isClosed()
                        && !emitter.isDisposed()) {
                    // Accept new client connections.
                    System.out.println("Waiting for client");
                    Socket socket = serverSocket.accept();
                    sockets.add(socket);
                    emitter.onNext(socket);

                    Observable.fromIterable(oldShapes)
                            .subscribe(s ->
                                    this.send(socket,s));

                }
            } catch (IOException e) {
                emitter.onError(e);
            }
        });
        obsSocket
                .subscribeOn(Schedulers.io())
                .subscribe(socket->{
                    this.getShapeObservable(socket)
                            .subscribeOn(Schedulers.io())
                            .subscribe(shape -> {
                                System.out.println("Listining on client");

                                sockets.stream()
                                        .forEach(clients-> {
                                            oldShapes.add(shape);
                                            this.send(clients,shape);
                                        });
                            },throwable -> {
                                sockets.remove(socket);
                            },()->{
                                System.out.println("Logged OFF");
                            });
                },throwable -> {

                },()->{

                });

    }

    /**
     * ClientConnection
     * handels the communication between the server and a client
     * @throws IOException an Exception
     */
    private void clientConnection() throws IOException {
        Socket clientSocket = new Socket("localhost",this.serverPort);
        mainFrame.setTitle("Client");
        if (clientSocket.isConnected()){

            System.out.println("CLENT CONNECTED");

            this.drawingPanel.redraw();

        }
        this.getShapeObservable(clientSocket)
                .subscribeOn(Schedulers.io())
                .subscribe(shape -> {

                    if (shape instanceof Clear){
                        this.drawingPanel.getDrawing().clearShapes();
                        this.drawingPanel.redraw();
                    }
                    else {
                        this.drawingPanel.getDrawing().addShape(shape);
                        this.drawingPanel.redraw();
                    }
                },Throwable::printStackTrace);

        this.drawingPanel
                .getShapePublishSubject()
                .subscribeOn(Schedulers.io())
                .subscribe(s ->{
                    this.send(clientSocket,s);
                });
    }

    /**
     * make the mainframe visible
     */
    public void initMainFrame(){
        this.mainFrame.setVisible(true);
    }
}
