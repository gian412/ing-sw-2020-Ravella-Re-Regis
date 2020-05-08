package it.polimi.ingsw.view;

import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.view.cli.CliComposer;

import java.io.ObjectInputStream;

public class BoardListener implements Runnable {
    ObjectInputStream inputStream;
    CliComposer output;

    public BoardListener(ObjectInputStream stream){
        inputStream = stream;
        output = new CliComposer();
    }

    @Override
    public void run() {
        while(true){
            try {
                BoardProxy message = (BoardProxy)inputStream.readObject();
                output.boardMaker(message);
            } catch (Exception e) {
                break;
            }
        }
    }
}
