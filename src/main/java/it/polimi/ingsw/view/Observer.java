package it.polimi.ingsw.view;

public interface Observer<T> {

    public void update(T message);

    public void update(Observable obs, T message);

}
