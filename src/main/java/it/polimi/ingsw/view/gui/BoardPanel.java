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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BoardPanel extends JPanel{
	
	private final Socket socket;
	private final ReadProxyBoard reader;
	private OptionPanel optionPanel;
	private DirectionsPanel directionsPanel;
	private BoardListener listener;
	private ObjectOutputStream outputStream;
	private BoardProxy actualBoard;

	private final String PATH = "src/main/java/it/polimi/ingsw/utils/graphics/";
	private final int firstOffset = 11; // px
	private final int cellLength = 137; // px
	private final int interstitialWidth = 22; //px
	private int workersAdded;

	/**
	 * Inner class that represents the pop-up panel with the available actions
	 *
	 * @author Elia Ravella, Gianluca Regis
	 */
	class OptionPanel extends JPanel {
		public OptionPanel(boolean canForce){
			super();

			// Initialize and add move button
			JButton moveButton = new JButton("Move");
			moveButton.addActionListener(e -> {
				optionPanel.setVisible(false);
				directionsPanel.setVisible(true);
			});
			this.add(moveButton);

			// Initialize and add build panel
			JButton buildButton = new JButton("Build");
			buildButton.addActionListener(e -> {
				optionPanel.setVisible(false);
				directionsPanel.setVisible(true);
			});
			this.add(buildButton);

			if (canForce) { // If player's God have the power to force...
				// ... initialize and add force panel
				JButton forceButton = new JButton("Force");
				forceButton.addActionListener(e -> {
					optionPanel.setVisible(false);
					directionsPanel.setVisible(true);
				});
				this.add(forceButton);
			}
		}
	}

	/**
	 * Inner class that represents the pop-up panel with the available directions for the chosen action
	 *
	 * @author Elia Ravella, Gianluca Regis
	 */
	class DirectionsPanel extends JPanel {
		public DirectionsPanel() {
			super();
		}
	}

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
			switch (actualBoard.getStatus()) {
				case ADDING_WORKER:
					if (message.getTurnPlayer().equals(StaticFrame.getPlayerName())) {
						JOptionPane.showMessageDialog(parentComponent, "Add your workers!");
					}
					refreshView();
					break;
				case PLAYING:
					if (message.getTurnPlayer().equals(StaticFrame.getPlayerName())) {
						JOptionPane.showMessageDialog(parentComponent, "Select the worker that you want to play with in this turn");
					}
					refreshView();
					break;
			}
		}
	}

	public BoardPanel(Socket socket, BoardProxy firstBoard, BoardListener listener, ObjectOutputStream outputStream) {
		workersAdded = 0;
		this.listener = listener;
		this.actualBoard = firstBoard;
		this.socket = socket;
		reader = new ReadProxyBoard(this);
		this.outputStream = outputStream;

		// Initialize option panel and add it to the board panel
		optionPanel = new OptionPanel(StaticFrame.godCanForce());
		optionPanel.setVisible(false);
		this.add(optionPanel);

		// Initialize directions panel and add it to the board panel
		directionsPanel = new DirectionsPanel();
		directionsPanel.setVisible(false);
		this.add(directionsPanel);

		listener.addObserver(reader);
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
		try {
			boardImg = ImageIO.read(new File(PATH + "_board.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		g.drawImage(boardImg, 0, 0, boardImg.getWidth(), boardImg.getHeight(), this);
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
			JOptionPane.showMessageDialog(this, "Add your workers!");

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Pair cell;

				switch (actualBoard.getStatus()) {
					case ADDING_WORKER:
						cell = BoardMaker.map(e.getX(), e.getY());

						if (StaticFrame.getPlayerName().equals(actualBoard.getTurnPlayer())) {
							// TODO remove this dialog
							JOptionPane.showMessageDialog(StaticFrame.mainFrame, "adding worker at " + cell.x + " " + cell.y);

							PlayerCommand toSend = new PlayerCommand(
									StaticFrame.getPlayerName(),
									new Command(
											cell,
											CommandType.ADD_WORKER
									),
									0
							);
							workersAdded++;

							try {
								outputStream.reset();
								outputStream.writeObject(toSend);
								outputStream.flush();
							} catch (IOException x) {
								x.printStackTrace();
							}

							/**
							 *  procedure to verify if the player already added his 2 workers
							 */
							if (workersAdded == 2) {
								PlayerCommand changeTurn = new PlayerCommand(
										StaticFrame.getPlayerName(),
										new Command(
												new Pair(0, 0),
												CommandType.CHANGE_TURN
										),
										0
								);

								try {
									outputStream.reset();
									outputStream.writeObject(changeTurn);
									outputStream.flush();
								} catch (IOException x) {
									x.printStackTrace();
								}
							}

						} else { // TODO: remove after test in order to simulate inactivity
							JOptionPane.showMessageDialog( StaticFrame.mainFrame, "it is not your turn!");
						}
						break;
					case PLAYING:
						cell = BoardMaker.map(e.getX(), e.getY());

						if (StaticFrame.getPlayerName().equals(actualBoard.getTurnPlayer())) {
							// TODO remove this dialog
							JOptionPane.showMessageDialog(StaticFrame.mainFrame, "searching worker at " + cell.x + " " + cell.y);

							// Check if there is a worker in the selected cell
							if (checkWorkerPresence(cell)) {
								optionPanel.setVisible(true);
							}
						} else { // TODO: remove after test in order to simulate inactivity
							JOptionPane.showMessageDialog( StaticFrame.mainFrame, "it is not your turn!");
						}

						break;
				}
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

	private boolean checkWorkerPresence(Pair cell) {
		// TODO: implement
		BoardProxy board = actualBoard;
		return true;
	}

}
