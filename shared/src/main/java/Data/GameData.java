package Data;

import chess.ChessGame;

public class GameData {
    record gameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game){}
}
