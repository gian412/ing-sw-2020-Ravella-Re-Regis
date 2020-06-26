package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.PlayerCommand;
import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.utils.CommandType;
import it.polimi.ingsw.utils.GameState;
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

public class GamePanel extends JPanel{
	
	private final Socket socket;
	private final ReadProxyBoard reader;
	private BoardListener listener;
	private ObjectOutputStream outputStream;
	private BoardProxy actualBoard;

	private final String PATH = "src/main/java/it/polimi/ingsw/utils/graphics/";
	private final int firstOffset = 11; // px
	private final int cellLength = 137; // px
	private final int interstitialWidth = 22; //px
	private int workersAdded;

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
				JOptionPane.showConfirmDialog(parentComponent, "Add your workers!");
			refreshView();
		}
	}

	public GamePanel(Socket socket, BoardProxy firstBoard, BoardListener listener, ObjectOutputStream outputStream) {
		workersAdded = 0;
		this.listener = listener;
		this.actualBoard = firstBoard;
		this.socket = socket;
		reader = new ReadProxyBoard(this);
		this.outputStream = outputStream;

		listener.addObserver(reader);
		//new Thread(listener).start();
		//setUpUI();
		appendMouseClickMapper();
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
		BufferedImage boardImg;
		//BufferedImage powerImage;
		try {
			boardImg = ImageIO.read(new File(PATH + "_board.png"));
			//powerImage = ImageIO.read(new File(PATH + "_power.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		g.drawImage(boardImg, 0, 0, boardImg.getWidth(), boardImg.getHeight(), this);
		//g.drawImage(powerImage, 405, 815, powerImage.getWidth(), powerImage.getHeight(), this);
		this.setSize(boardImg.getWidth(), boardImg.getHeight());

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
			JOptionPane.showConfirmDialog(this, "Add your workers!");

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Pair cell = BoardMaker.map(e.getX(), e.getY());
				int workerIndex = (workersAdded == 0) ? 0 : 1;

				// TODO remove this dialog
				JOptionPane.showInputDialog("adding worker number " + workerIndex + " at " + cell.x + " " + cell.y);

				PlayerCommand toSend = new PlayerCommand(
						StaticFrame.getPlayerName(),
						new Command(
								cell,
								CommandType.ADD_WORKER
						),
						workerIndex
				);
				workersAdded++;

				try {
					outputStream.writeObject(toSend);
					outputStream.flush();
				} catch(IOException x){
					x.printStackTrace();
				}

				checkChangeTurn();
			}
		});
	}

	/**
	 * used in the "adding workers" phase, this method calls the change of turn when the current player
	 * has added his quote of workers to the board. (this triggers a board update)
	 *
	 * @author Elia Ravella
	 */
	private void checkChangeTurn() {
		if(workersAdded == 2){
			PlayerCommand changeTurn = new PlayerCommand(
					StaticFrame.getPlayerName(),
					new Command(
							new Pair(0, 0),
							CommandType.CHANGE_TURN
					),
					0
			);

			try {
				outputStream.writeObject(changeTurn);
				outputStream.flush();
			} catch(IOException x){
				x.printStackTrace();
			}
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

	private void setUpUI() {
		Image image;
		JLabel powerLabel;
		try {
			image = ImageIO.read(new File(PATH + "_power.png"))
					.getScaledInstance(300, 201, Image.SCALE_DEFAULT);
			powerLabel = new JLabel(new ImageIcon(image));

			this.setLayout(new GridBagLayout());
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.PAGE_END;
			this.add(powerLabel, gridBagConstraints);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
