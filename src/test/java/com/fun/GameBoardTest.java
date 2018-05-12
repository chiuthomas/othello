package com.fun;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class GameBoardTest {

    @Before
    public void init() {

    }


    @Test
    public void testInvalidMove() throws Exception {
        BoardState actualBoardState = new BoardState();
        actualBoardState.occupy(Side.DARK, 3, 2);
        actualBoardState.occupy(Side.LIGHT, 3, 3);
        actualBoardState.occupy(Side.LIGHT, 3, 4);

        // no flip
        GameBoard gameBoard = new GameBoard(actualBoardState, Side.DARK);
        final int[] flipCnt = new int[1];
        boolean isValid = gameBoard.play(3, 1, flipCnt);
        Assert.assertFalse(isValid);

        // already occupied
        isValid = gameBoard.play(3, 2, flipCnt);
        Assert.assertFalse(isValid);

        isValid = gameBoard.play(3, 3, flipCnt);
        Assert.assertFalse(isValid);

        isValid = gameBoard.play(3, 4, flipCnt);
        Assert.assertFalse(isValid);

        Assert.assertEquals(actualBoardState.getSideCnt(Side.DARK), 1);
        Assert.assertEquals(actualBoardState.getSideCnt(Side.LIGHT), 2);
    }

    @Test
    public void testFlipNorth() throws Exception {
        BoardState actualBoardState = new BoardState();
        actualBoardState.occupy(Side.DARK, 3, 2);
        actualBoardState.occupy(Side.LIGHT, 3, 3);
        actualBoardState.occupy(Side.LIGHT, 3, 4);

        GameBoard gameBoard = new GameBoard(actualBoardState, Side.DARK);
        final int[] flipCnt = new int[1];
        boolean isValid = gameBoard.play(3, 5, flipCnt);

        Assert.assertTrue(isValid);
        Assert.assertEquals(2, flipCnt[0]);

        BoardState expectedBoardState = new BoardState();
        expectedBoardState.occupy(Side.DARK, 3, 2);
        expectedBoardState.occupy(Side.DARK, 3, 3);
        expectedBoardState.occupy(Side.DARK, 3, 4);
        expectedBoardState.occupy(Side.DARK, 3, 5);

        Assert.assertEquals(expectedBoardState, actualBoardState);

        Assert.assertEquals(actualBoardState.getSideCnt(Side.DARK), 4);
        Assert.assertEquals(actualBoardState.getSideCnt(Side.LIGHT), 0);
    }

    @Test
    public void testFlipNE() throws Exception {
        BoardState actualBoardState = new BoardState();
        actualBoardState.occupy(Side.DARK, 4, 6);
        actualBoardState.occupy(Side.LIGHT, 3, 5);
        actualBoardState.occupy(Side.LIGHT, 2, 4);

        GameBoard gameBoard = new GameBoard(actualBoardState, Side.DARK);
        final int[] flipCnt = new int[1];
        boolean isValid = gameBoard.play(1, 3, flipCnt);

        Assert.assertTrue(isValid);
        Assert.assertEquals(2, flipCnt[0]);

        BoardState expectedBoardState = new BoardState();
        expectedBoardState.occupy(Side.DARK, 4, 6);
        expectedBoardState.occupy(Side.DARK, 3, 5);
        expectedBoardState.occupy(Side.DARK, 2, 4);
        expectedBoardState.occupy(Side.DARK, 1, 3);

        Assert.assertEquals(expectedBoardState, actualBoardState);

        Assert.assertEquals(actualBoardState.getSideCnt(Side.DARK), 4);
        Assert.assertEquals(actualBoardState.getSideCnt(Side.LIGHT), 0);
    }

    @Test
    public void testFlipEast() throws Exception {
        BoardState actualBoardState = new BoardState();
        actualBoardState.occupy(Side.DARK, 4, 6);
        actualBoardState.occupy(Side.LIGHT, 3, 6);
        actualBoardState.occupy(Side.LIGHT, 2, 6);

        GameBoard gameBoard = new GameBoard(actualBoardState, Side.DARK);
        final int[] flipCnt = new int[1];
        boolean isValid = gameBoard.play(1, 6, flipCnt);

        Assert.assertTrue(isValid);
        Assert.assertEquals(2, flipCnt[0]);

        BoardState expectedBoardState = new BoardState();
        expectedBoardState.occupy(Side.DARK, 4, 6);
        expectedBoardState.occupy(Side.DARK, 3, 6);
        expectedBoardState.occupy(Side.DARK, 2, 6);
        expectedBoardState.occupy(Side.DARK, 1, 6);

        Assert.assertEquals(expectedBoardState, actualBoardState);

        Assert.assertEquals(actualBoardState.getSideCnt(Side.DARK), 4);
        Assert.assertEquals(actualBoardState.getSideCnt(Side.LIGHT), 0);
    }

    @Test
    public void testFlipSE() throws Exception {
        BoardState actualBoardState = new BoardState();
        actualBoardState.occupy(Side.LIGHT, 4, 4);
        actualBoardState.occupy(Side.LIGHT, 5, 5);
        actualBoardState.occupy(Side.DARK, 6, 6);

        GameBoard gameBoard = new GameBoard(actualBoardState, Side.DARK);
        final int[] flipCnt = new int[1];
        boolean isValid = gameBoard.play(3, 3, flipCnt);

        Assert.assertTrue(isValid);
        Assert.assertEquals(2, flipCnt[0]);

        BoardState expectedBoardState = new BoardState();
        expectedBoardState.occupy(Side.DARK, 3, 3);
        expectedBoardState.occupy(Side.DARK, 4, 4);
        expectedBoardState.occupy(Side.DARK, 5, 5);
        expectedBoardState.occupy(Side.DARK, 6, 6);

        Assert.assertEquals(expectedBoardState, actualBoardState);

        Assert.assertEquals(actualBoardState.getSideCnt(Side.DARK), 4);
        Assert.assertEquals(actualBoardState.getSideCnt(Side.LIGHT), 0);
    }

    @Test
    public void testFlipSouth() throws Exception {
        BoardState actualBoardState = new BoardState();
        actualBoardState.occupy(Side.LIGHT, 3, 3);
        actualBoardState.occupy(Side.LIGHT, 3, 4);
        actualBoardState.occupy(Side.DARK, 3, 5);

        GameBoard gameBoard = new GameBoard(actualBoardState, Side.DARK);
        final int[] flipCnt = new int[1];
        boolean isValid = gameBoard.play(3, 2, flipCnt);

        Assert.assertTrue(isValid);
        Assert.assertEquals(2, flipCnt[0]);

        BoardState expectedBoardState = new BoardState();
        expectedBoardState.occupy(Side.DARK, 3, 2);
        expectedBoardState.occupy(Side.DARK, 3, 3);
        expectedBoardState.occupy(Side.DARK, 3, 4);
        expectedBoardState.occupy(Side.DARK, 3, 5);

        Assert.assertEquals(expectedBoardState, actualBoardState);

        Assert.assertEquals(actualBoardState.getSideCnt(Side.DARK), 4);
        Assert.assertEquals(actualBoardState.getSideCnt(Side.LIGHT), 0);
    }

    @Test
    public void testFlipSW() throws Exception {
        BoardState actualBoardState = new BoardState();
        actualBoardState.occupy(Side.LIGHT, 3, 3);
        actualBoardState.occupy(Side.LIGHT, 2, 4);
        actualBoardState.occupy(Side.DARK, 1, 5);

        GameBoard gameBoard = new GameBoard(actualBoardState, Side.DARK);
        final int[] flipCnt = new int[1];
        boolean isValid = gameBoard.play(4, 2, flipCnt);

        Assert.assertTrue(isValid);
        Assert.assertEquals(2, flipCnt[0]);

        BoardState expectedBoardState = new BoardState();
        expectedBoardState.occupy(Side.DARK, 4, 2);
        expectedBoardState.occupy(Side.DARK, 3, 3);
        expectedBoardState.occupy(Side.DARK, 2, 4);
        expectedBoardState.occupy(Side.DARK, 1, 5);

        Assert.assertEquals(expectedBoardState, actualBoardState);

        Assert.assertEquals(actualBoardState.getSideCnt(Side.DARK), 4);
        Assert.assertEquals(actualBoardState.getSideCnt(Side.LIGHT), 0);
    }

    @Test
    public void testFlipWest() throws Exception {
        BoardState actualBoardState = new BoardState();
        actualBoardState.occupy(Side.LIGHT, 3, 3);
        actualBoardState.occupy(Side.LIGHT, 2, 3);
        actualBoardState.occupy(Side.DARK, 1, 3);

        GameBoard gameBoard = new GameBoard(actualBoardState, Side.DARK);
        final int[] flipCnt = new int[1];
        boolean isValid = gameBoard.play(4, 3, flipCnt);

        Assert.assertTrue(isValid);
        Assert.assertEquals(2, flipCnt[0]);

        BoardState expectedBoardState = new BoardState();
        expectedBoardState.occupy(Side.DARK, 4, 3);
        expectedBoardState.occupy(Side.DARK, 3, 3);
        expectedBoardState.occupy(Side.DARK, 2, 3);
        expectedBoardState.occupy(Side.DARK, 1, 3);

        Assert.assertEquals(expectedBoardState, actualBoardState);

        Assert.assertEquals(actualBoardState.getSideCnt(Side.DARK), 4);
        Assert.assertEquals(actualBoardState.getSideCnt(Side.LIGHT), 0);
    }

    @Test
    public void testFlipNW() throws Exception {
        BoardState actualBoardState = new BoardState();
        actualBoardState.occupy(Side.LIGHT, 3, 3);
        actualBoardState.occupy(Side.LIGHT, 2, 2);
        actualBoardState.occupy(Side.DARK, 1, 1);

        GameBoard gameBoard = new GameBoard(actualBoardState, Side.DARK);
        final int[] flipCnt = new int[1];
        boolean isValid = gameBoard.play(4, 4, flipCnt);

        Assert.assertTrue(isValid);
        Assert.assertEquals(2, flipCnt[0]);

        BoardState expectedBoardState = new BoardState();
        expectedBoardState.occupy(Side.DARK, 1, 1);
        expectedBoardState.occupy(Side.DARK, 2, 2);
        expectedBoardState.occupy(Side.DARK, 3, 3);
        expectedBoardState.occupy(Side.DARK, 4, 4);

        Assert.assertEquals(expectedBoardState, actualBoardState);

        Assert.assertEquals(actualBoardState.getSideCnt(Side.DARK), 4);
        Assert.assertEquals(actualBoardState.getSideCnt(Side.LIGHT), 0);
    }

    @Test
    public void testTurnPlay() throws Exception {
        BoardState actualBoardState = new BoardState();
        actualBoardState.occupy(Side.DARK, 3, 2);
        actualBoardState.occupy(Side.LIGHT, 3, 3);
        actualBoardState.occupy(Side.LIGHT, 3, 4);

        // no flip
        GameBoard gameBoard = new GameBoard(actualBoardState, Side.DARK);
        final int[] flipCnt = new int[1];
        boolean isValid = gameBoard.play(3, 1, flipCnt);
        Assert.assertFalse(isValid);

        // already occupied
        isValid = gameBoard.play(3, 2, flipCnt);
        Assert.assertFalse(isValid);

        isValid = gameBoard.play(3, 3, flipCnt);
        Assert.assertFalse(isValid);

        isValid = gameBoard.play(3, 4, flipCnt);
        Assert.assertFalse(isValid);
    }

    @Test
    public void testFindAllFlippableMoves() throws Exception {
        BoardState actualBoardState = new BoardState();
        actualBoardState.occupy(Side.DARK, 3, 0);

        actualBoardState.occupy(Side.DARK, 3, 2);
        actualBoardState.occupy(Side.LIGHT, 3, 3);
        actualBoardState.occupy(Side.LIGHT, 3, 4);

        GameBoard gameBoard = new GameBoard(actualBoardState, Side.DARK);

        List<int[]> flippableMoves = gameBoard.findAllFlippableMoves(Side.DARK);
        List<int[]> expectFlippableMoves = new ArrayList<>();
        expectFlippableMoves.add(new int[]{3, 5});

        Assert.assertEquals(expectFlippableMoves.size(), flippableMoves.size());

        Assert.assertArrayEquals(expectFlippableMoves.get(0), flippableMoves.get(0));
    }

    @Test
    public void testEndGame() throws Exception {
        BoardState actualBoardState = new BoardState();
        for (int i = 0; i < GameBoard.BOARD_HEIGHT; i++) {
            for (int j = 0; j < GameBoard.BOARD_WIDTH - 1; j++) {
                actualBoardState.occupy(Side.DARK, i, j);
            }
        }

        for (int j = 0; j < GameBoard.BOARD_HEIGHT; j++) {
            actualBoardState.occupy(Side.LIGHT, j, GameBoard.BOARD_WIDTH - 1);
        }

        actualBoardState.lift(0, 6);
        actualBoardState.lift(0, 7);

        GameBoard gameBoard = new GameBoard(actualBoardState, Side.DARK);

        Assert.assertTrue(gameBoard.isEndGame());
    }

}
