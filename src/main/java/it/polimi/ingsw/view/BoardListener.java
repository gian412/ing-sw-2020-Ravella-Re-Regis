package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.utils.GameState;
import it.polimi.ingsw.view.cli.CliComposer;

import java.io.ObjectInputStream;

public class BoardListener extends Observable<BoardProxy> implements Runnable {
    ObjectInputStream inputStream;

    public BoardListener(ObjectInputStream stream){
        inputStream = stream;
    }

    @Override
    public void run() {
        while(true){
            try {
                BoardProxy message = (BoardProxy)inputStream.readObject();
                notify(message);
            } catch (Exception e) {
                BoardProxy disconnected = new BoardProxy();
                disconnected.setStatus(GameState.TERMINATOR);
                disconnected.setWinner("Server down");
                notify(disconnected);
            }
        }
    }
}
