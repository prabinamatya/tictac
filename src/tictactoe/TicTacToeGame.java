package tictactoe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TicTacToeGame {

	public static final char[][] POSITIONS = { { '1', '2', '3' }, { '4', '5', '6' }, { '7', '8', '9' } };

	public static void main(String[] args) {

		TicTacToe ttt = new TicTacToe();
		char[][] createBoard = ttt.createBoard();
		List<Integer> moves = new ArrayList<Integer>();
		int numOfMoves = 0;
		String line = "";
		boolean range = true;
		int numEntered = 0;
		ttt.printBoard(POSITIONS);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		do {
			do {
				try {
					System.out.println("Please enter your value (1 - 9) only.=>");
					line = br.readLine();
					numEntered = (int) Integer.valueOf(line);
					if (numEntered < 1 || numEntered > 9) {
						range = false;
						System.out.println("Invalid position. Please try again! ");
					} else {
						range = true;
						range = userEntersXAsMoveAndUnoccupied(numEntered, createBoard, moves);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} while (range == false);

			numOfMoves++;// count user move
			aiEntersOAsMove(createBoard, moves);
			numOfMoves++; // count ai move

			ttt.printBoard(createBoard);
		} while (!didGameEnd(createBoard, moves) && numOfMoves < 9);
		System.out.println("\n");
		System.out.println("moves = " + moves);
		System.out.println("num of moves = " + numOfMoves);

	}

	private static boolean didGameEnd(char[][] createBoard, List<Integer> moves) {
		return whoWon(createBoard, 'x', moves) || whoWon(createBoard, 'o', moves);
	}

	private static void aiEntersOAsMove(char[][] createBoard, List<Integer> moves) {
		int position;
		int row;
		int col;
		do {
			position = (int) (Math.random() * 9) + 1;
			row = (position - 1) / createBoard.length;
			col = (position - 1) % createBoard.length;

		} while (!unoccupied(createBoard, row, col));
		moves.add(position);
		createBoard[row][col] = 'o';
	}

	private static boolean userEntersXAsMoveAndUnoccupied(int numEntered, char[][] createBoard, List<Integer> moves) {
		int row, col;
		row = (numEntered - 1) / createBoard.length;
		col = (numEntered - 1) % createBoard.length;
		if (unoccupied(createBoard, row, col)) {
			createBoard[row][col] = 'x';
			moves.add(numEntered);
			return true;
		}
		return false;
	}

	private static boolean unoccupied(char[][] createBoard, int row, int col) {
		return (createBoard[row][col] == '_');
	}

	private static boolean whoWon(char[][] board, char playerType, List<Integer> moves) {
		// win horizontal
		for (int i = 0; i < 3; i++) {
			if (board[i][0] == playerType && board[i][1] == playerType && board[i][2] == playerType) {
				printWinner(playerType);
				if (playerType == 'x') {
					saveLosingCombination(moves);
				}
				return true;
			}
		}
		// win vertical
		for (int j = 0; j < 3; j++) {
			if (board[0][j] == playerType && board[1][j] == playerType && board[2][j] == playerType) {
				printWinner(playerType);
				if (playerType == 'x') {
					saveLosingCombination(moves);
				}
				return true;
			}
		}
		// win minor diagonal
		if (board[0][2] == playerType && board[1][1] == playerType && board[2][0] == playerType) {
			printWinner(playerType);
			if (playerType == 'x') {
				saveLosingCombination(moves);
			}
			return true;
		}
		// win major diagonal
		else if (board[0][0] == playerType && board[1][1] == playerType && board[2][2] == playerType) {
			printWinner(playerType);
			if (playerType == 'x') {
				saveLosingCombination(moves);
			}
			return true;
		} else {
			return false;
		}
	}

	private static void saveLosingCombination(List<Integer> moves) {
		try (FileWriter fw = new FileWriter("losingCombination.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println(moves);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printWinner(char playerType) {
		System.out.println("We have " + playerType + " as a winner");
	}

}
