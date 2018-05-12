package com.fun;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    public static boolean isDebug = false;

    public static final int BOARD_WIDTH = 8;

    public static final int BOARD_HEIGHT = 8;

    public static final int TOTAL_NUMBER_OF_MOVE = BOARD_WIDTH * BOARD_HEIGHT;

    private BoardState boardState = new BoardState();

    private Side startTurnSide = Side.DARK;

    private Side currentTurnSide;

    private boolean isCloned = false;

    private GameBoard() {

    }

    public GameBoard(BoardState boardState, Side startTurnSide) {
        this.boardState = boardState;
        this.currentTurnSide = startTurnSide;
    }

    public GameBoard(Side startTurnSide) {
        this.startTurnSide = startTurnSide;
    }

    public void init() {
        this.boardState = new BoardState();
        this.boardState.init();

        this.currentTurnSide = startTurnSide;
    }

    public Side getCurrentTurnSide() {
        return currentTurnSide;
    }

    public BoardState getBoardState() {
        return boardState;
    }

    public void turnSide() {
        if (Side.DARK == currentTurnSide) {
            currentTurnSide = Side.LIGHT;
        } else {
            currentTurnSide = Side.DARK;
        }
    }

    public Side getNextTurnSide(Side side) {
        return side == Side.DARK ? Side.LIGHT : Side.DARK;
    }


    public boolean isValidMove(Side side, int x, int y) {
        if (boardState.getSide(x, y) != null) {
            return false;
        }

        List<int[]> allPossibleMoves = findAllFlippableMoves(side);
        for (int[] xy : allPossibleMoves) {
            if (xy[0] == x && xy[1] == y) {
                return true;
            }
        }
        return false;
    }

    private boolean isDirectionFlippableFromXY(Direction direction, int x, int y, Side currentTurnSide) {
        int i = x;
        int j = y;

        i += direction.x;
        j += direction.y;
        Side side = boardState.getSide(i, j);
        // check immediate adjacent is empty or self-occupied
        if (side == null || side == currentTurnSide) {
            return false;
        }

        // check along the direction
        i += direction.x;
        j += direction.y;

        while (i >= 0 && i < GameBoard.BOARD_WIDTH && j >= 0 && j < GameBoard.BOARD_HEIGHT) {
            side = boardState.getSide(i, j);
            if (side == null) {
                return false;
            }

            if (side == currentTurnSide) {
                return true;
            }

            i += direction.x;
            j += direction.y;
        }

        return false;
    }

    private int flipDirectionFromXY(Direction direction, int x, int y) {
        int flipCnt = 0;

        int i = x;
        int j = y;

        i += direction.x;
        j += direction.y;

        while (i >= 0 && i < GameBoard.BOARD_WIDTH && j >= 0 && j < GameBoard.BOARD_HEIGHT) {
            if (boardState.getSide(i, j) == currentTurnSide) {
                break;
            }
            boardState.flip(i, j);
            flipCnt++;

            i += direction.x;
            j += direction.y;
        }

        return flipCnt;
    }

    public boolean play(int x, int y, int[] totalFlipCnt) {
        boolean isValidMove = false;

        if (isValidMove(this.currentTurnSide, x, y) && boardState.occupy(this.currentTurnSide, x, y)) {
            // check adjacent
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (i != x || j != y) {
                        // check direction
                        Direction direction = Direction.getDirection(x, y, i, j);
                        if (direction != null && isDirectionFlippableFromXY(direction, x, y, this.currentTurnSide)) {
                            totalFlipCnt[0] += flipDirectionFromXY(direction, x, y);
                        }
                    }
                }
            }

            isValidMove = true;

            turnSide();

            if (findAllFlippableMoves(currentTurnSide).size() == 0) {
                // turnSide
                if (!isCloned)
                    System.out.println("Player '" + currentTurnSide.getId() + "' passes");

                turnSide();
            }
        }

        return isValidMove;
    }

    public List<int[]> findAllFlippableMoves(Side currentTurnSide) {
        List<int[]> allPossibleMoves = new ArrayList();

        final Side nextTurnSide = getNextTurnSide(currentTurnSide);
        for (int i = 0; i < GameBoard.BOARD_HEIGHT; i++) {
            for (int j = 0; j < GameBoard.BOARD_WIDTH; j++) {
                final Side side = boardState.getSide(i, j);
                if (side == nextTurnSide) {
                    for (Direction direction : Direction.values()) {
                        int adjI = i + direction.x;
                        int adjJ = j + direction.y;
                        Side adjacent = boardState.getSide(adjI, adjJ);
                        if (adjacent == null && getBoardState().isValid(adjI, adjJ)) {
                            Direction flipDirection = Direction.getDirection(adjI, adjJ, i, j);
                            if (flipDirection != null && isDirectionFlippableFromXY(flipDirection, adjI, adjJ, currentTurnSide)) {
                                allPossibleMoves.add(new int[]{adjI, adjJ});
                            }
                        }
                    }
                }
            }
        }

        return allPossibleMoves;
    }

    public boolean isEndGame() {
        boolean isFull = boardState.getOccupiedCnt() == TOTAL_NUMBER_OF_MOVE;

        List<int[]> currentSideFlippableMoves = findAllFlippableMoves(currentTurnSide);
        final Side nextTurnSide = getNextTurnSide(this.currentTurnSide);
        List<int[]> nextTurnSideFlippableMoves = findAllFlippableMoves(nextTurnSide);

        if (isDebug) {
            printFlippableMoves(currentTurnSide, currentSideFlippableMoves);
            printFlippableMoves(nextTurnSide, nextTurnSideFlippableMoves);
        }

        return isFull || (currentSideFlippableMoves.size() == 0 && nextTurnSideFlippableMoves.size() == 0);
    }

    public void printBoard() {
        boardState.printState();
        System.out.println();
    }

    public void printFlippableMoves(Side side, List<int[]> flippableMoves) {
        String s = "";
        for (int[] xy : flippableMoves) {
            s += String.valueOf(xy[1] + 1) + (char) (xy[0] + 'a') + ", ";
        }

        System.out.println(side + " flippable moves: " + s);

    }

    public void printScore() {
        int darkSideCnt = boardState.getSideCnt(Side.DARK);
        int lightSideCnt = boardState.getSideCnt(Side.LIGHT);
        if (darkSideCnt > lightSideCnt) {
            if (!isCloned)
                System.out.println("Player '" + Side.DARK.getId() + "' wins ( " + darkSideCnt + " vs " + lightSideCnt + " )");
        } else if (darkSideCnt < lightSideCnt) {
            if (!isCloned)
                System.out.println("Player '" + Side.LIGHT.getId() + "' wins ( " + lightSideCnt + " vs " + darkSideCnt + " )");
        } else {
            if (!isCloned)
                System.out.println("Games draws ( " + lightSideCnt + " vs " + darkSideCnt + " )");
        }
    }

    public GameBoard clone() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.startTurnSide = startTurnSide;
        gameBoard.currentTurnSide = currentTurnSide;
        gameBoard.boardState = this.boardState.clone();
        gameBoard.isCloned = true;
        return gameBoard;
    }


}
