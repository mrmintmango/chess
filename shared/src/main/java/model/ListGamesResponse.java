package model;

import chess.ChessGame;

import java.util.ArrayList;

public record ListGamesResponse(ArrayList<ChessGame> listGames) {
}
//{ "games": [{"gameID": 1234, "whiteUsername":"", "blackUsername":"", "gameName:""}