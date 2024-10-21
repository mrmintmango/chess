package model;

import chess.ChessGame;

import java.util.ArrayList;

public record ListGamesResponse(String game, ArrayList<GameData> listGames) {
}
//{ "games": [{"gameID": 1234, "whiteUsername":"", "blackUsername":"", "gameName:""}