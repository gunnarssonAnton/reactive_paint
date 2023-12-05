package se.miun.dt176g.angu2106.reactive.connection;

import io.reactivex.rxjava3.core.Observable;
import se.miun.dt176g.angu2106.reactive.drawables.Shape;

import java.io.*;
import java.net.Socket;

/**
 * <h1>SocketReceiver</h1>
 *
 * @author  Anton Gunnarsson
 * @version 1.0
 * @since   2023-10-29
 */
public interface SocketReceiver {
    /**
     * getter for shape observable
     * @param socket a socket
     * @return a shape observable
     */
    default Observable<Shape> getShapeObservable(Socket socket){
     return Observable.create(emitter -> {
        while (!socket.isClosed() && !emitter.isDisposed()){
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
                Shape shape = (Shape) objInputStream.readObject();
                emitter.onNext(shape);


        }
     });
    }

    /**
     * sends a shape trough socket
     * @param socket a socket
     * @param shape the shaoe
     */
    default void send(Socket socket, Shape shape){
        try {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(shape);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
