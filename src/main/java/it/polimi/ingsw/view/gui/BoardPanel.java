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
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BoardPanel extends JPanel{
	
	private final Socket socket;
	private final ReadProxyBoard reader;
	private BoardListener listener;
	private ObjectOutputStream outputStream;

	private BoardProxy actualBoard;

	private OptionPanel optionPanel;
	private DirectionsPanel directionsPanel;

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

			// Initialize and add a "end turn" button
			JButton endTurnButton = new JButton("End Turn");
			endTurnButton.addActionListener(e -> {
				PlayerCommand toSend = new PlayerCommand(
					StaticFrame.getPlayerName(),
					new Command(new Pair(0, 0), CommandType.CHANGE_TURN),
					0
				);

				try {
					outputStream.reset();
					outputStream.writeObject(toSend);
					outputStream.flush();
				}catch (IOException x){
					JOptionPane.showMessageDialog(null, "Problem with sending your command to the server! Try again");
				}

				optionPanel.setVisible(false);

			});
			this.add(endTurnButton);


			// Initialize and add move button
			JButton moveButton = new JButton("Move");
			moveButton.addActionListener(e -> {
				directionsPanel.setCmd(CommandType.MOVE);
				optionPanel.setVisible(false);
				directionsPanel.setVisible(true);
			});
			this.add(moveButton);

			// Initialize and add build button
			JButton buildButton = new JButton("Build");
			buildButton.addActionListener(e -> {
				directionsPanel.setCmd(CommandType.BUILD);
				optionPanel.setVisible(false);
				directionsPanel.setVisible(true);
			});
			this.add(buildButton);

			if (canForce) {
				// If player's God have the power to force...
				// ... initialize and add force button
				JButton forceButton = new JButton("Force");
				forceButton.addActionListener(e -> {
					directionsPanel.setCmd(CommandType.FORCE);
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
		private int workerIndex;
		private Pair workerCell;
		private CommandType cmd;

		/**
		 * builds the panel that shows the possible directions for the selected move
		 * all the buttons have a dedicated actionListener that "puts together" the command
		 * and pushes it through the outputstream
		 */
		public DirectionsPanel() {
			super();
			workerCell = new Pair(0, 0);

			JButton btnNorth = new JButton("NORTH"),
					btnEast = new JButton("EAST"),
					btnSouth = new JButton("SOUTH"),
					btnWest = new JButton("WEST"),
					btnNorthEast = new JButton("NORTH-EAST"),
					btnSouthEast = new JButton("SOUTH-EAST"),
					btnSouthWest = new JButton("SOUTH-WEST"),
					btnNorthWest = new JButton("NORTH-WEST");

			btnNorth.addActionListener(e -> {
				Pair destination = new Pair(workerCell.x, workerCell.y - 1);
				PlayerCommand toSend = new PlayerCommand(
					StaticFrame.getPlayerName(),
					new Command(destination, cmd),
					workerIndex
				);

				try {
					outputStream.reset();
					outputStream.writeObject(toSend);
					outputStream.flush();
				}catch (IOException x){
					JOptionPane.showMessageDialog(null, "Problem with sending your command to the server! Try again");
				}

				directionsPanel.setVisible(false);
			});

			btnEast.addActionListener(e -> {
				Pair destination = new Pair(workerCell.x + 1, workerCell.y);
				PlayerCommand toSend = new PlayerCommand(
						StaticFrame.getPlayerName(),
						new Command(destination, cmd),
						workerIndex
				);

				try {
					outputStream.reset();
					outputStream.writeObject(toSend);
					outputStream.flush();
				}catch (IOException x){
					JOptionPane.showMessageDialog(null, "Problem with sending your command to the server! Try again");
				}

				directionsPanel.setVisible(false);
			});

			btnSouth.addActionListener(e -> {
				Pair destination = new Pair(workerCell.x, workerCell.y + 1);
				PlayerCommand toSend = new PlayerCommand(
						StaticFrame.getPlayerName(),
						new Command(destination, cmd),
						workerIndex
				);

				try {
					outputStream.reset();
					outputStream.writeObject(toSend);
					outputStream.flush();
				}catch (IOException x){
					JOptionPane.showMessageDialog(null, "Problem with sending your command to the server! Try again");
				}

				directionsPanel.setVisible(false);
			});

			btnWest.addActionListener(e -> {
				Pair destination = new Pair(workerCell.x - 1, workerCell.y);
				PlayerCommand toSend = new PlayerCommand(
						StaticFrame.getPlayerName(),
						new Command(destination, cmd),
						workerIndex
				);

				try {
					outputStream.reset();
					outputStream.writeObject(toSend);
					outputStream.flush();
				}catch (IOException x){
					JOptionPane.showMessageDialog(null, "Problem with sending your command to the server! Try again");
				}

				directionsPanel.setVisible(false);
			});

			btnNorthEast.addActionListener(e -> {
				Pair destination = new Pair(workerCell.x + 1, workerCell.y - 1);
				PlayerCommand toSend = new PlayerCommand(
						StaticFrame.getPlayerName(),
						new Command(destination, cmd),
						workerIndex
				);

				try {
					outputStream.reset();
					outputStream.writeObject(toSend);
					outputStream.flush();
				}catch (IOException x){
					JOptionPane.showMessageDialog(null, "Problem with sending your command to the server! Try again");
				}

				directionsPanel.setVisible(false);
			});

			btnSouthEast.addActionListener(e -> {
				Pair destination = new Pair(workerCell.x + 1, workerCell.y + 1);
				PlayerCommand toSend = new PlayerCommand(
						StaticFrame.getPlayerName(),
						new Command(destination, cmd),
						workerIndex
				);

				try {
					outputStream.reset();
					outputStream.writeObject(toSend);
					outputStream.flush();
				}catch (IOException x){
					JOptionPane.showMessageDialog(null, "Problem with sending your command to the server! Try again");
				}

				directionsPanel.setVisible(false);
			});

			btnSouthWest.addActionListener(e -> {
				Pair destination = new Pair(workerCell.x - 1, workerCell.y + 1);
				PlayerCommand toSend = new PlayerCommand(
						StaticFrame.getPlayerName(),
						new Command(destination, cmd),
						workerIndex
				);

				try {
					outputStream.reset();
					outputStream.writeObject(toSend);
					outputStream.flush();
				}catch (IOException x){
					JOptionPane.showMessageDialog(null, "Problem with sending your command to the server! Try again");
				}

				directionsPanel.setVisible(false);
			});

			btnNorthWest.addActionListener(e -> {
				Pair destination = new Pair(workerCell.x - 1, workerCell.y - 1);
				PlayerCommand toSend = new PlayerCommand(
						StaticFrame.getPlayerName(),
						new Command(destination, cmd),
						workerIndex
				);

				try {
					outputStream.reset();
					outputStream.writeObject(toSend);
					outputStream.flush();
				}catch (IOException x){
					JOptionPane.showMessageDialog(null, "Problem with sending your command to the server! Try again");
				}

				directionsPanel.setVisible(false);
			});

			this.add(btnNorth);
			this.add(btnEast);
			this.add(btnSouth);
			this.add(btnWest);
			this.add(btnNorthEast);
			this.add(btnSouthEast);
			this.add(btnSouthWest);
			this.add(btnNorthWest);
		}

		public void setWorkerIndex(int workerIndex) {
			this.workerIndex = workerIndex;
		}

		public void setWorkerCell(Pair workerCell) {
			this.workerCell = workerCell;
		}

		public void setCmd(CommandType cmd) {
			this.cmd = cmd;
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
			if(message.getIllegalMoveString().equals("")) {
				switch (actualBoard.getStatus()) {
					case ADDING_WORKER:
						if (message.getTurnPlayer().equals(StaticFrame.getPlayerName()) && !message.getWorkers().containsKey(StaticFrame.getPlayerName() + "0")) {
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
					case TERMINATOR:
						if (message.getWinner().equals("Unexpected Game Over")) {
							JOptionPane.showMessageDialog(
									StaticFrame.mainFrame,
									"Game over because another client closed the game",
									"Client disconnected",
									JOptionPane.ERROR_MESSAGE
							);
						} else if (message.getWinner().equals(StaticFrame.getPlayerName())){
							JOptionPane.showMessageDialog(
									StaticFrame.mainFrame,
									"Congrats! You win!",
									"You win",
									JOptionPane.INFORMATION_MESSAGE
							);
						} else if (message.getWinner().equals("Server down")) {
							JOptionPane.showMessageDialog(
									StaticFrame.mainFrame,
									"Game over because the server went down",
									"Server down",
									JOptionPane.ERROR_MESSAGE
							);
						} else {
							JOptionPane.showMessageDialog(
									StaticFrame.mainFrame,
									"You lose! " + "The winner is " + message.getWinner() + "!",
									"You lose",
									JOptionPane.INFORMATION_MESSAGE
							);
						}
						showLogin();
				}
			}else{
				JOptionPane.showMessageDialog(StaticFrame.mainFrame, message.getIllegalMoveString());
				refreshView();
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
		// the first board is not received directly from the model
		// it's passed from the previous panel, so "it's already there"
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

						} else {
							// TODO: remove after test in order to simulate inactivity
							JOptionPane.showMessageDialog( StaticFrame.mainFrame, "it is not your turn!");
						}
						break;
					case PLAYING:
						cell = BoardMaker.map(e.getX(), e.getY());

						if (StaticFrame.getPlayerName().equals(actualBoard.getTurnPlayer())) {
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

	/**
	 * given a cell to check, verify if there's a worker on it and sets the optionPanel accordingly
	 * @param cell the cell to be checked
	 * @return true/false
	 * @author Elia Ravella, Gianluca Regis
	 */
	private boolean checkWorkerPresence(Pair cell) {
		if(actualBoard.getWorkers().get(StaticFrame.getPlayerName() + "0").equals(cell)){
			directionsPanel.setWorkerIndex(0);
			directionsPanel.setWorkerCell(cell);
			return true;
		}
		else if(actualBoard.getWorkers().get(StaticFrame.getPlayerName() + "1").equals(cell)){
			directionsPanel.setWorkerIndex(1);
			directionsPanel.setWorkerCell(cell);
			return true;
		}
		else return  false;
	}

	private void showLogin() {

		listener.removeObserver(reader);

		//load next panel
		LoginPanel loginPanel = new LoginPanel();

		try {

			outputStream.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		StaticFrame.removePanel(this);
		StaticFrame.addPanel(loginPanel);
		StaticFrame.refresh();

	}

}
