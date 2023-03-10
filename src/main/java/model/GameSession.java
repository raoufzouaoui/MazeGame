package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

/**
 * Represents a single game session. A game session comprises of 1, 2 or 4 players.
 * @author raouf zouaoui
 */
public class GameSession {
	
	public static int MAX_PLAYERS = 2;
	private Board board;
	private static List<HumanPlayer> players;

	private Player winner;
	
	/**
	 * A {@link Stack} was chosen to store all the moves as an undo function can be 
	 * implemented in the practise mode. 
	 */
	private Deque<Move> moves;
	
	public GameSession(Board board) {
		this.board = board;
		this.players = new ArrayList<HumanPlayer>();
		this.moves = new ArrayDeque<Move>();

	}
	
	/**
	 * Gets the {@link Board} used in this session.
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}	
	
	/**
	 * Adds a player to the session.
	 * @param player the player to add
	 * @throws IllegalStateException when more players than the limit are added
	 * @throws IllegalArgumentException if the player is null
	 */
	public void addPlayer(HumanPlayer player) {
		if(players.size() > MAX_PLAYERS) {
			throw new IllegalStateException("There can only be a maximum of " + MAX_PLAYERS + " players");
		}
		if(player == null) {
			throw new IllegalArgumentException("The player cannot be null");
		}
		players.add(player);
	}
	
	/**
	 * Gets all the players in this session.
	 * @return the players in this session
	 */
	public static List<HumanPlayer> getPlayers() {
		return players;
	}
	
	/**
	 * Gets a player with the specified id.
	 * @param id the player to get
	 * @return the player
	 */
	public Player getPlayer(int id) {
		return players.get(id);
	}
	
	/**
	 * Adds a {@link Move} to the move list.
	 * @param move
	 * @return true if the move was successful
	 */
	public boolean addMove(Move move) {
		moves.push(move);
		return true;
	}
	
	/**
	 * Gets a history of moves that have taken place.
	 * @return the history of moves
	 */
	public Deque<Move> getMoves() {
		return moves;
	}
	


	/**
	 * Checks whether a move is valid.
	 * @param current the current {@link Tile} the {@link Player} is at.
	 * @param movingTo the {@link Tile} the {@link Player} wants to move to.
	 * @throws IllegalArgumentException if the tile is null 
	 * @return whether the move is valid
	 */
	public boolean isValidMove(Tile current, Tile movingTo) {
		if(current == null | movingTo == null) {
			throw new IllegalArgumentException("The tile cannot be null.");
		}
		int currentX = current.getX();
		int currentY = current.getY();
		int nextX = movingTo.getX();
		int nextY = movingTo.getY();
    	if(nextX >= board.getWidth() || nextY >= board.getHeight() || nextX < 0 || nextY < 0) { //Check if the new position is off the board
    		return false;
    	}		
    	if(board.getTile(nextX, nextY).containsPawn()) {
    		return false;
    	} 
    	if(nextX == currentX) {
    		if(nextY == currentY - 1) { //going upwards
    			if(board.containsWall(currentX, currentY, true)) {
    				return false;
    			}
    			return true;
    		}
    		if(nextY == currentY + 1) { //going downwards
    			if(board.containsWall(currentX, currentY+1, true)) {
    				return false;
    			}
    			return true;    			
    		}
    	}
    	if(nextY == currentY) {
    		if(nextX == currentX - 1) { //going left
    			if(board.containsWall(currentX-1, currentY, false)) {
    				return false;
    			}  		
    			return true;
    		}
    		if(nextX == currentX + 1) { //going right
    			if(board.containsWall(currentX, currentY, false)) {
    				return false;
    			}
    			return true;
    		}
    	}
    	return false;
	}	
	
	/**
	 * Sets the winner for this {@link GameSession}.
	 * @param player the winner
	 */
	public void setWinner(Player player) {
		winner = player;
	}
	
	/**
	 * Gets the {@link Player} who won the match.
	 * @return the winner
	 */
	public Player getWinner() {
		return winner;
	}	

}
