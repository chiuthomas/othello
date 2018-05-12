package com.fun;

import java.util.Arrays;

public class BoardState {

    private Boolean[][] grid = new Boolean[GameBoard.BOARD_HEIGHT][GameBoard.BOARD_WIDTH];

    private int occupiedCnt = 0;

    private int darkSideCnt = 0;

    private int lightSideCnt = 0;

    public void init() {
        occupiedCnt = 0;
        for (int i = 0; i < GameBoard.BOARD_HEIGHT; i++) {
            for (int j = 0; j < GameBoard.BOARD_WIDTH; j++) {
                grid[i][j] = null;
            }
        }

        grid[3][3] = getSideValue(Side.LIGHT);
        grid[3][4] = getSideValue(Side.DARK);
        grid[4][4] = getSideValue(Side.LIGHT);
        grid[4][3] = getSideValue(Side.DARK);
    }

    public boolean isValid(int x, int y) {
        return (x >= 0 && x < GameBoard.BOARD_WIDTH && y >= 0 && y < GameBoard.BOARD_HEIGHT);
    }

    public Side getSide(int x, int y) {
        if (!isValid(x, y)) {
            return null;
        }

        if (grid[y][x] == null) {
            return null;
        } else {
            if (grid[y][x] == getSideValue(Side.DARK)) {
                return Side.DARK;
            } else {
                return Side.LIGHT;
            }
        }
    }

    public int getSideCnt(Side side) {
        if (side == Side.DARK) {
            return darkSideCnt;
        } else {
            return lightSideCnt;
        }
    }

    public boolean occupy(Side side, int x, int y) {
        if (!isValid(x, y)) {
            return false;
        }

        if (grid[y][x] == null) {
            grid[y][x] = getSideValue(side);

            occupiedCnt++;

            if (side == Side.DARK) {
                darkSideCnt++;
            } else {
                lightSideCnt++;
            }

            return true;
        }

        return false;
    }

    public void lift(int x, int y) {
        grid[y][x] = null;
    }

    public boolean flip(int x, int y) {
        if (!isValid(x, y)) {
            return false;
        }

        if (grid[y][x] != null) {
            grid[y][x] = !grid[y][x];

            if (getSide(x, y) == Side.DARK) {
                darkSideCnt++;
                lightSideCnt--;
            } else {
                lightSideCnt++;
                darkSideCnt--;
            }

            return true;
        }

        return false;
    }

    private static boolean getSideValue(Side side) {
        return side.ordinal() == 0 ? false : true;
    }

    public int getOccupiedCnt() {
        return occupiedCnt;
    }

    public String formatState() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < GameBoard.BOARD_HEIGHT; i++) {
            sb.append((i + 1) + "\t");
            for (int j = 0; j < GameBoard.BOARD_WIDTH; j++) {
                Side side = getSide(j, i);
                sb.append(side != null ? side.getId() : "-");
            }
            sb.append("\n");
        }

        int ascii = (byte) 'a';
        sb.append("\t");
        for (int i = ascii; i < ascii + GameBoard.BOARD_WIDTH; i++) {
            sb.append((char) i);
        }

        sb.append("\n");
        return sb.toString();
    }

    public void printState() {
        System.out.print(formatState());
    }

    public BoardState clone() {
        BoardState boardState = new BoardState();

        for (int i = 0; i < GameBoard.BOARD_HEIGHT; i++) {
            for (int j = 0; j < GameBoard.BOARD_WIDTH; j++) {
                boardState.grid[i][j] = this.grid[i][j];
            }
        }

        boardState.occupiedCnt = this.occupiedCnt;
        boardState.darkSideCnt = this.darkSideCnt;
        boardState.lightSideCnt = this.lightSideCnt;

        return boardState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardState that = (BoardState) o;

        if (occupiedCnt != that.occupiedCnt) return false;
        return Arrays.deepEquals(grid, that.grid);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(grid);
    }

    @Override
    public String toString() {
        return formatState();
    }
}
