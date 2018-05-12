package com.fun;

public class Othello {

    public static void main(String[] args) throws Exception {
        GameBoard gameBoard = new GameBoard(Side.DARK);
        gameBoard.isDebug = false;
        gameBoard.init();
        gameBoard.printBoard();

        BotPlayer botPlayer = new BotPlayer(Side.LIGHT);

        final int[] totalFlipCnt = new int[1];
        byte[] bytes = new byte[128];

        while (!gameBoard.isEndGame()) {
            if (gameBoard.getCurrentTurnSide() == Side.DARK) {
                System.out.print("Player '" + gameBoard.getCurrentTurnSide().getId() + "' move: ");
                int len = System.in.read(bytes);
                final String commandLn = new String(bytes, 0, len);
                if ("".equals(commandLn.trim()) && commandLn.trim().length() == 2) {
                    continue;
                }

                final int y;
                try {
                    y = Integer.parseInt(String.valueOf(commandLn.charAt(0))) - 1;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please try again.");
                    continue;
                }

                final int x = commandLn.charAt(1) - 'a';

                if (gameBoard.play(x, y, totalFlipCnt)) {
                    gameBoard.printBoard();
                } else {
                    System.out.println("Invalid move. Please try again.");
                    System.out.println();
                }
            } else {
                int[] xy = botPlayer.findOptimal(gameBoard, 16);
                if (xy != null) {
                    System.out.println("Player '" + gameBoard.getCurrentTurnSide().getId() + "' move: " + (xy[1] + 1) + (char) (xy[0] + 'a'));
                    gameBoard.play(xy[0], xy[1], totalFlipCnt);
                    gameBoard.printBoard();
                }
            }
        }

        gameBoard.printScore();
    }

}
