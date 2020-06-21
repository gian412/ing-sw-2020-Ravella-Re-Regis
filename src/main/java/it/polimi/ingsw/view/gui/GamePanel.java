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
	
	private static final String PATH = "src/main/java/it/polimi/ingsw/utils/graphics/";
	
	/**
	 * Inner class to observe the BoardListener object
	 * and display the elements that arrive form the socket
	 *
	 * @author Elia Ravella, Gianluca Regis
	 */
	class ReadProxyBoard implements Observer<BoardProxy> {
		
		GamePanel displayPanel;
		
		public ReadProxyBoard(GamePanel displayPanel) {
			this.displayPanel = displayPanel;
		}
		
		@Override
		public void update(BoardProxy message) {
		
		}
	}
	
	public GamePanel(Socket socket) {
		this.socket = socket;
		this.setUpUI(StaticFrame.getGod()); // Set up the UI for the current player based on the God
		reader = new ReadProxyBoard(this);
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
	}
	
	/**
	 * Set up the game basic UI
	 * @param god is the god of the player
	 * @author Elia Ravella, Gianluca Regis
	 */
	private void setUpUI(GodType god) {
		
		this.refreshView();
		this.setLayout(new GridBagLayout());
		try {
			BufferedImage image = ImageIO.read(new File(PATH + "_board.png"));
			super.paintComponent(this.getGraphics());
			this.getGraphics().drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        }catch(Exception e){
            e.printStackTrace();
        }
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
