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

public class GamePanel extends JPanel implements Runnable {
	
	private final Socket socket;
	private final ReadProxyBoard reader;
	private BoardListener listener;
	private ObjectOutputStream outputStream;
	private BoardProxy actualBoard;
	
	private final String PATH = "src/main/java/it/polimi/ingsw/utils/graphics/";
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
	 * override of the JPanel original paint method
	 * allows to do some serious custom painting
	 *
	 * @param g the "Graphics2D" object
	 * @author Elia Ravella
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		BufferedImage img;
		try {
			img = ImageIO.read(new File(PATH + "_board.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		BoardMaker.drawTowers(g, actualBoard, firstOffset, cellLength, interstitialWidth, this);

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
