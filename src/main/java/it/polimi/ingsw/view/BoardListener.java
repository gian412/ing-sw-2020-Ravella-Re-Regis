package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.utils.GameState;
import it.polimi.ingsw.view.cli.CliComposer;

import java.io.ObjectInputStream;

/**
 * this class reads all the information coming from the server via the stream passed in the constructor.
 * this class was made Observable to make possible to other components to act as Observer of it and ease the
 * communication with the server
 *
 * @author Elia Ravella, Marco Re, Gianluca Regis
 */
public class BoardListener extends Observable<BoardProxy> implements Runnable {
    ObjectInputStream inputStream;

    public BoardListener(ObjectInputStream stream){
        inputStream = stream;
    }

    @Override
    public void run() {
        BoardProxy message;
        while(true){
            try {
                 message = (BoardProxy)inputStream.readObject();
            } catch (Exception e) {
                BoardProxy disconnected = new BoardProxy();
                disconnected.setStatus(GameState.TERMINATOR);
                disconnected.setWinner("Server down");
                notify(disconnected);
                return;
            }
            notify(message);
            if(message.getStatus().equals(GameState.TERMINATOR))
                return;
        }
    }
}
