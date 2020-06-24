package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.utils.GodType;
import it.polimi.ingsw.view.BoardListener;
import it.polimi.ingsw.view.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GamePanel extends JLayeredPane implements Runnable {
	
	private final Socket socket;
	private final ReadProxyBoard reader;
	private BoardListener listener;
	private ObjectOutputStream outputStream;
	private BoardProxy actualBoard;
	
	private final int firstOffset = 34; // px
	private final int cellLength = 134; // px
	private final int interstitialWidth = 24; //px
	
	/**
	 * Inner class to observe the BoardListener object
	 * saves the boardproxy in a local attribute and calls the repaint()
	 *
	 * @author Elia Ravella, Gianluca Regis
	 */
	class ReadProxyBoard implements Observer<BoardProxy> {
		@Override
		public void update(BoardProxy message) {
			actualBoard = message;
			refreshView();
		}
	}
	
	public GamePanel(Socket socket) {
		this.socket = socket;
		reader = new ReadProxyBoard();
	}
	
	@Override
	public void run() {
		try {
			listener = new BoardListener(new ObjectInputStream(socket.getInputStream()));
			outputStream = new ObjectOutputStream((socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		listener.addObserver(reader);
		new Thread(listener).start();

		this.refreshView();
		StaticFrame.refresh();
	}
	
	/**
	 * Create and add panels with board and power
	 *
	 * @author Gianluca Regis
	 */
	public void setUpUI() {

		this.setSize(StaticFrame.mainFrame.getSize());

		BoardPanel boardPanel = new BoardPanel();
		PowerPanel powerPanel = new PowerPanel();
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 0.9;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		this.add(boardPanel, gbc, 1);
		gbc.gridy = 1;
		gbc.weighty = 0.1;
		this.add(powerPanel, gbc, 1);
		
	}

	/**
	 * reloads the view, loading all new dynamically added components
	 *
	 * @author Elia Ravella
	 */
	private void refreshView(){
		this.invalidate();
		this.validate();
		this.repaint();
	}


}
