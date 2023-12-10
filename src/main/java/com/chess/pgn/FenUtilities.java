package com.chess.pgn;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.piece.Pawn;

public class FenUtilities {
    private FenUtilities() {
        throw new RuntimeException("not instantiable");
    }

    public static Board createGameFromFEN(final String fenString) {
        return null; // TODO - from fen to board
    }

    public static String createFENFromGame(final Board board) {
        return calculateBoardText(board) + " " +
                calculateCurrentPlayerText(board) + " " +
                calculateCastleText(board) + " " +
                calculateEnPassantText(board) + " " +
                "0 1"; //TODO - clock, move number
    }

    private static String calculateBoardText(final Board board) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = board.getTileAtCoordinate(i).toString();
            builder.append(tileText);
        }
        builder.insert(8, "/");
        builder.insert(17, "/");
        builder.insert(26, "/");
        builder.insert(35, "/");
        builder.insert(44, "/");
        builder.insert(53, "/");
        builder.insert(62, "/");

        return builder.toString()
                .replaceAll("--------", "8")
                .replaceAll("-------", "7")
                .replaceAll("------", "6")
                .replaceAll("-----", "5")
                .replaceAll("----", "4")
                .replaceAll("---", "3")
                .replaceAll("--", "2")
                .replaceAll("-", "1");
    }

    private static String calculateCurrentPlayerText(final Board board) {
        return board.getCurrentPlayer().toString().substring(0, 1).toLowerCase();
    }

    private static String calculateCastleText(final Board board) {
        StringBuilder builder = new StringBuilder();
        if (board.getWhitePlayer().isKingSideCastleCapable()) {
            builder.append("K");
        }
        if (board.getWhitePlayer().isQueenSideCastleCapable()) {
            builder.append("Q");
        }
        if (board.getBlackPlayer().isKingSideCastleCapable()) {
            builder.append("k");
        }
        if (board.getBlackPlayer().isQueenSideCastleCapable()) {
            builder.append("q");
        }
        final String result = builder.toString();
        return result.isEmpty() ? "-" : result;
    }

    private static String calculateEnPassantText(final Board board) {
        final Pawn enPassantPawn = board.getEnPassantPawn();
        if (enPassantPawn != null) {
            // the position behind the pawn
            return BoardUtils.getPositionAtCoordinate(enPassantPawn.getPiecePosition() +
                    8 * enPassantPawn.getPieceAlliance().getOppositeDirection());
        }
        return "-";
    }
}
