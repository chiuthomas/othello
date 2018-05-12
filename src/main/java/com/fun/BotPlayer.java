package com.fun;

import java.util.List;

public class BotPlayer {

    private Side side;

    public BotPlayer(Side side) {
        this.side = side;
    }

    public int[] findOptimal(GameBoard gameBoard, int predictSteps) {
        List<int[]> allPossibleMoves = gameBoard.findAllFlippableMoves(side);

        int[] optimalXy = null;

        int maxSideCnt = 0;
        int moveIdx = 0;
        for (int[] xy : allPossibleMoves) {
            GameBoard myGameBoard = gameBoard.clone();
            int[] flipCnt = new int[1];
            myGameBoard.play(xy[0], xy[1], flipCnt);

            if (predictSteps == 0) {
                int sideCnt = myGameBoard.getBoardState().getSideCnt(side);
                if (sideCnt > maxSideCnt) {
                    maxSideCnt = sideCnt;
                    optimalXy = allPossibleMoves.get(moveIdx);
                }
            }

            for (int i = 0; i < predictSteps; i++) {
                maxSideCnt = 0;
                List<int[]> nextPossibleMoves = gameBoard.findAllFlippableMoves(side);
                for (int[] nextXy : nextPossibleMoves) {
                    myGameBoard = gameBoard.clone();
                    flipCnt = new int[1];
                    myGameBoard.play(nextXy[0], nextXy[1], flipCnt);

                    if (i == predictSteps - 1) {
                        int sideCnt = myGameBoard.getBoardState().getSideCnt(side);
                        if (sideCnt > maxSideCnt) {
                            maxSideCnt = sideCnt;
                            optimalXy = allPossibleMoves.get(moveIdx);
                        }
                    }
                }
            }

            moveIdx++;
        }

        return optimalXy;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BotPlayer{");
        sb.append("side=").append(side);
        sb.append('}');
        return sb.toString();
    }
}
