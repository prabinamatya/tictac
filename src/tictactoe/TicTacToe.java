package tictactoe;

import java.util.Random;

public class TicTacToe {
	

	private static final int size = 3;
	char board[][] = new char[size][size];

	public char[][] createBoard() {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				board[i][j] = '_';
			}
		}
		return board;
	}
	


	
	public void printBoard(char[][] board) {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				System.out.print(" " + board[i][j]);
			}
			System.out.println();
		}
	}

}
