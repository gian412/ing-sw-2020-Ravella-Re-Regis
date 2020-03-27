package it.polimi.ingsw.view;

import it.polimi.ingsw.model.BoardProxy;


public class RemoteView implements Observer<BoardProxy> {

    @Override
    public void update(BoardProxy message) {
        System.out.println(message.toString());
    }
}
