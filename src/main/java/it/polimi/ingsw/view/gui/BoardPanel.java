package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.PlayerCommand;
import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.utils.CommandType;
import it.polimi.ingsw.utils.GameState;
import it.polimi.ingsw.utils.GodMoves;
import it.polimi.ingsw.utils.GodType;
import it.polimi.ingsw.view.BoardListener;
import it.polimi.ingsw.view.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class BoardPanel extends JPanel{
	
	private final Socket socket;
	private final ReadProxyBoard reader;
	private BoardListener listener;
	private ObjectOutputStream outputStream;

	private BoardProxy actualBoard;
	private ArrayList<Pair> workersAdded;
	private ArrayList<CommandType> turnMoves;

	private OptionPanel optionPanel;
	private DirectionsPanel directionsPanel;

	private final String PATH = "src/main/java/it/polimi/ingsw/utils/graphics/";
	private final int firstOffset = 19; // px
	private final int cellLength = 137; // px
	private final int interstitialWidth = 22; //px

	/**
	 * Inner class that represents the pop-up panel with the available actions
	 *
	 * @author Elia Ravella, Gianluca Regis
	 */
	class OptionPanel extends JPanel {

		private boolean isPlaying;

		public OptionPanel(boolean canForce, boolean canBuildDome){
			super();

			isPlaying = false;
			// Initialize and add a "end turn" button
			JButton endTurnButton = new JButton("End Turn");
			endTurnButton.addActionListener(e -> {
				remoteChangeTurn();
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

			if (canBuildDome) {
				JButton buildDomeButton = new JButton("Build dome");
				buildDomeButton.addActionListener(e -> {
					directionsPanel.setCmd(CommandType.BUILD_DOME);
					optionPanel.setVisible(false);
					directionsPanel.setVisible(true);
				});
				this.add(buildDomeButton);
			}
		}

		public boolean isPlaying() {
			return isPlaying;
		}

		public void setPlaying(boolean playing) {
			isPlaying = playing;
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
		private static final int IMAGE_BASE_WIDTH = 150;
		private static final int IMAGE_BASE_HEIGHT = 50;

		/**
		 * builds the panel that shows the possible directions for the selected move
		 * all the buttons have a dedicated actionListener that "puts together" the command
		 * and pushes it through the outputstream
		 */
		public DirectionsPanel(GodType particularGod) {
			super();
			workerCell = new Pair(0, 0);

			// Initialize components
			JButton btnNorthWest = new JButton("NORTH-WEST"),
					btnNorth = new JButton("NORTH"),
					btnNorthEast = new JButton("NORTH-EAST"),
					btnWest = new JButton("WEST"),
					btnPower,
					btnEast = new JButton("EAST"),
					btnSouthWest = new JButton("SOUTH-WEST"),
					btnSouth = new JButton("SOUTH"),
					btnSouthEast = new JButton("SOUTH-EAST");

			// Add action listeners
			btnNorthWest.addActionListener(e -> {
				Pair destination = new Pair(workerCell.x - 1, workerCell.y - 1);
				sendCommand(destination, cmd, workerIndex);

				registerMove(StaticFrame.getGod(), cmd, destination);
				directionsPanel.setVisible(false);
				optionPanel.setVisible(true);
			});

			btnNorth.addActionListener(e -> {
				Pair destination = new Pair(workerCell.x, workerCell.y - 1);
				sendCommand(destination, cmd, workerIndex);

				registerMove(StaticFrame.getGod(), cmd, destination);
				directionsPanel.setVisible(false);
				optionPanel.setVisible(true);
			});
			btnNorthEast.addActionListener(e -> {
				Pair destination = new Pair(workerCell.x + 1, workerCell.y - 1);
				sendCommand(destination, cmd, workerIndex);

				registerMove(StaticFrame.getGod(), cmd, destination);
				directionsPanel.setVisible(false);
				optionPanel.setVisible(true);
			});
			btnWest.addActionListener(e -> {
				Pair destination = new Pair(workerCell.x - 1, workerCell.y);
				sendCommand(destination, cmd, workerIndex);

				registerMove(StaticFrame.getGod(), cmd, destination);
				directionsPanel.setVisible(false);
				optionPanel.setVisible(true);
			});
			btnEast.addActionListener(e -> {
				Pair destination = new Pair(workerCell.x + 1, workerCell.y);
				sendCommand(destination, cmd, workerIndex);

				registerMove(StaticFrame.getGod(), cmd, destination);
				directionsPanel.setVisible(false);
				optionPanel.setVisible(true);
			});
			btnSouthWest.addActionListener(e -> {
				Pair destination = new Pair(workerCell.x - 1, workerCell.y + 1);
				sendCommand(destination, cmd, workerIndex);

				registerMove(StaticFrame.getGod(), cmd, destination);
				directionsPanel.setVisible(false);
				optionPanel.setVisible(true);
			});
			btnSouth.addActionListener(e -> {
				Pair destination = new Pair(workerCell.x, workerCell.y + 1);
				sendCommand(destination, cmd, workerIndex);

				registerMove(StaticFrame.getGod(), cmd, destination);
				directionsPanel.setVisible(false);
				optionPanel.setVisible(true);
			});
			btnSouthEast.addActionListener(e -> {
				Pair destination = new Pair(workerCell.x + 1, workerCell.y + 1);
				sendCommand(destination, cmd, workerIndex);

				registerMove(StaticFrame.getGod(), cmd, destination);
				directionsPanel.setVisible(false);
				optionPanel.setVisible(true);
			});

			if (particularGod == GodType.ZEUS) {
				btnPower = new JButton("UNDER");
				btnPower.addActionListener(e -> {
					Pair destination = new Pair(workerCell.x, workerCell.y);
					sendCommand(destination, cmd, workerIndex);

					registerMove(StaticFrame.getGod(), cmd, destination);
					directionsPanel.setVisible(false);
					optionPanel.setVisible(true);
				});
			} else {
				// Add power to the central button
				Image image;
				try {
					image = (GetImages.getPowerImage(StaticFrame.getGod().toString())).getScaledInstance(IMAGE_BASE_WIDTH, IMAGE_BASE_HEIGHT, Image.SCALE_DEFAULT);
					btnPower = new JButton(new ImageIcon(image));
				}catch(Exception e){
					btnPower = new JButton();
				}
			}

			// Add components to the panel
			this.add(btnNorthWest);
			this.add(btnNorth);
			this.add(btnNorthEast);
			this.add(btnWest);
			this.add(btnPower);
			this.add(btnEast);
			this.add(btnSouthWest);
			this.add(btnSouth);
			this.add(btnSouthEast);

			this.setLayout( new GridLayout(3, 3));
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

		public int getWorkerIndex(){
			return workerIndex;
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
			if(message.getIllegalMoveString().equals("")) {
				switch (message.getStatus()) {
					case ADDING_WORKER:
						if (message.getTurnPlayer().equals(StaticFrame.getPlayerName()) && !message.getWorkers().containsKey(StaticFrame.getPlayerName() + "0")) {
							JOptionPane.showMessageDialog(StaticFrame.mainFrame, "Add your workers!");
						}
						actualBoard = message;
						refreshView();
						break;
					case PLAYING:
						if (message.getTurnPlayer().equals(StaticFrame.getPlayerName())) {
							if(message.getTurnPlayer().equals(actualBoard.getTurnPlayer())){
								if(GodMoves.isTurnEnded(StaticFrame.getGod(), turnMoves.toArray())){
									JOptionPane.showMessageDialog(StaticFrame.mainFrame, "Your turn is ended!");
									remoteChangeTurn();
								} else {
									JOptionPane.showMessageDialog(StaticFrame.mainFrame, "Go on pal!");
									directionsPanel.setWorkerCell(message.getWorkers().get(StaticFrame.getPlayerName() + directionsPanel.getWorkerIndex()));
								}
							} else {
								JOptionPane.showMessageDialog(StaticFrame.mainFrame, "select the worker for this turn");
							}
						}
						actualBoard = message;
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
						} else if (message.getWinner().equals("Server down")) {
							JOptionPane.showMessageDialog(
									StaticFrame.mainFrame,
									"Game over because the server went down",
									"Server down",
									JOptionPane.ERROR_MESSAGE
							);
						} else if (message.getWinner().equals(StaticFrame.getPlayerName())){
							JOptionPane.showMessageDialog(
									StaticFrame.mainFrame,
									"Congrats! You win!",
									"You win",
									JOptionPane.INFORMATION_MESSAGE
							);
						}  else {
							JOptionPane.showMessageDialog(
									StaticFrame.mainFrame,
									"You lose! " + "The winner is " + message.getWinner() + "!",
									"You lose",
									JOptionPane.INFORMATION_MESSAGE
							);
						}
						actualBoard = message;
						showLogin();
				}
			} else {
				if(message.getTurnPlayer().equals(StaticFrame.getPlayerName())) {
					JOptionPane.showMessageDialog(StaticFrame.mainFrame, message.getIllegalMoveString());
					if(turnMoves.size() > 0) turnMoves.remove(turnMoves.size() - 1);
				}
				actualBoard = message;
				refreshView();
			}
		}
    }

	public BoardPanel(Socket socket, BoardProxy firstBoard, BoardListener listener, ObjectOutputStream outputStream) {
		workersAdded = new ArrayList<>();
		this.listener = listener;
		this.actualBoard = firstBoard;
		this.socket = socket;
		reader = new ReadProxyBoard(this);
		this.outputStream = outputStream;

		// Loads gods possible move, turn moves dataset
		GodMoves.PossibleMoveInit();
		turnMoves = new ArrayList<>();

		// Initialize option panel and add it to the board panel
		optionPanel = new OptionPanel(StaticFrame.godCanForce(), StaticFrame.godCanBuildDome());
		optionPanel.setVisible(false);
		this.add(optionPanel);

		// Initialize directions panel and add it to the board panel
		directionsPanel = new DirectionsPanel(StaticFrame.getGod());
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
		Image boardImg;
		try {
			boardImg = GetImages.getBoard();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		g.drawImage(boardImg, 0, 0, boardImg.getWidth(null), boardImg.getHeight(null), this);
		this.setSize(boardImg.getWidth(null), boardImg.getHeight(null));

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
							if(!actualBoard.getWorkers().containsValue(cell) && !workersAdded.contains(cell)){
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
								workersAdded.add(cell);

								try {
									outputStream.reset();
									outputStream.writeObject(toSend);
									outputStream.flush();
								} catch (IOException x) {
									x.printStackTrace();
								}
							} else {
								JOptionPane.showMessageDialog(StaticFrame.mainFrame, "Worker already present here!");
							}

							/**
							 *  procedure to verify if the player already added his 2 workers
							 */
							if (workersAdded.size() == 2) {
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
							JOptionPane.showMessageDialog( StaticFrame.mainFrame, "it is not your turn!");
						}
						break;
					case PLAYING:
						cell = BoardMaker.map(e.getX(), e.getY());

						if (StaticFrame.getPlayerName().equals(actualBoard.getTurnPlayer())) {
							// Check if there is a worker in the selected cell, and if is'nt already selected another one
							if (checkWorkerPresence(cell) && !optionPanel.isPlaying()) {
								optionPanel.setVisible(true);
								optionPanel.setPlaying(true);
							}
						} else {
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
		if (actualBoard.getWorkers().get(StaticFrame.getPlayerName() + "0") != null && actualBoard.getWorkers().get(StaticFrame.getPlayerName() + "0").equals(cell)) {
			directionsPanel.setWorkerIndex(0);
			directionsPanel.setWorkerCell(cell);
			return true;
		}
		if (actualBoard.getWorkers().get(StaticFrame.getPlayerName() + "1") != null && actualBoard.getWorkers().get(StaticFrame.getPlayerName() + "1").equals(cell)) {
			directionsPanel.setWorkerIndex(1);
			directionsPanel.setWorkerCell(cell);
			return true;
		} else return false;
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

	private void remoteChangeTurn(){
		optionPanel.setPlaying(false);
		optionPanel.setVisible(false);

		turnMoves.clear();

		PlayerCommand endTurn = new PlayerCommand(
				StaticFrame.getPlayerName(),
				new Command(new Pair(0, 0), CommandType.CHANGE_TURN),
				0
		);

		try {
			outputStream.reset();
			outputStream.writeObject(endTurn);
			outputStream.flush();
		}catch (IOException x){
			JOptionPane.showMessageDialog(null, "Unknown problem with the network communications!");
		}
	}

	private void sendCommand(Pair cell, CommandType command, int workerIndex){
		PlayerCommand toSend = new PlayerCommand(
				StaticFrame.getPlayerName(),
				new Command(cell, command),
				workerIndex
		);

		try {
			outputStream.reset();
			outputStream.writeObject(toSend);
			outputStream.flush();
		}catch (IOException x){
			JOptionPane.showMessageDialog(null, "Problem with sending your command to the server! Try again");
		}

	}


	private void registerMove(GodType god, CommandType cmd, Pair destination) {
		if(god.getCapitalizedName().equals("Triton")){
			if(!(cmd.equals(CommandType.MOVE) || (destination.x == 4 || destination.y == 4 || destination.x == 0 || destination.y == 0)))
				turnMoves.add(cmd);
		} else {
			turnMoves.add(cmd);
		}
	}

}
