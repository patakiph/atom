package replication;

import main.ApplicationContext;
import matchmaker.MatchMaker;

import model.GameSession;
import model.Player;
import network.ClientConnections;
import network.packets.PacketLeaderBoard;
import network.packets.PacketReplicate;
import org.eclipse.jetty.websocket.api.Session;
import protocol.model.Cell;
import protocol.model.Food;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Ольга on 01.12.2016.
 */
public class ReplicatorJson implements Replicator{

    @Override
    public void replicate() {
        ReplicationJsonParser parser = new ReplicationJsonParser();
        Food[] food = parser.getFoodFromJson();
        Cell[] cells = parser.getCellsFromJson();
        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
            for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
                if (gameSession.getPlayers().contains(connection.getKey())) {
                    try {
                        new PacketReplicate(cells,food).write(connection.getValue());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
