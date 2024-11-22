package websocket.messages;

import model.GameData;

public class LoadGameMessage extends ServerMessage{
    public GameData game;
    private final String playerType;

    public LoadGameMessage(ServerMessageType type, GameData game, String playerType) {
        super(type);
        this.game = game;
        this.playerType = playerType;
    }

    public GameData getGame(){
        return game;
    }

    public String getPlayerType(){
        return playerType;
    }
}
