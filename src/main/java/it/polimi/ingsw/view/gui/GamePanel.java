package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.utils.GameState;
import it.polimi.ingsw.utils.GodType;
import it.polimi.ingsw.view.BoardListener;
import it.polimi.ingsw.view.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	private final int firstOffset = 11; // px
	private final int cellLength = 137; // px
	private final int interstitialWidth = 22; //px

	/**
	 * Inner class to observe the BoardListener object
	 * saves the boardproxy in a local attribute and calls the repaint()
	 *
	 * @author Elia Ravella, Gianluca Regis
	 */
	class ReadProxyBoard implements Observer<BoardProxy> {
		private final Component parentComponent;

		public ReadProxyBoard(Component parentComponent){
			this.parentComponent = parentComponent;
		}

		@Override
		public void update(BoardProxy message) {
			actualBoard = message;
			if(message.getStatus().equals(GameState.ADDING_WORKER) && message.getTurnPlayer().equals(StaticFrame.getPlayerName()))
				JOptionPane.showConfirmDialog(parentComponent, "Add your fucking workers!");
			refreshView();
		}
	}

	public GamePanel(Socket socket, BoardProxy firstBoard) {
		this.actualBoard = firstBoard;
		this.socket = socket;
		reader = new ReadProxyBoard(this);
		appendMouseClickMapper();
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
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage img;
		try {
			img = ImageIO.read(new File(PATH + "_board.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), this);
		this.setSize(img.getWidth(), img.getHeight());

		if (actualBoard != null) {
			BoardMaker.drawElements(g, actualBoard, firstOffset, cellLength, interstitialWidth, this);
		}

	}

	/**
	 * this method adds a dedicated mouselistener to this panel: this mouselistener maps
	 * the click on the image to a click on the actualboard's grid of cells
	 */
	private void appendMouseClickMapper() {
		// for the first player:
		if(actualBoard.getStatus().equals(GameState.ADDING_WORKER) && actualBoard.getTurnPlayer().equals(StaticFrame.getPlayerName()))
			JOptionPane.showConfirmDialog(this, "Add your fucking workers!");



		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pair cell = BoardMaker.map(e.getX(), e.getY());
				JOptionPane.showMessageDialog(e.getComponent(), "Cell: " + cell.x + " " + cell.y);
				//TODO implement the PlayerCommand send procedure
			}
		});
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
