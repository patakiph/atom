package replication.leaderboardtest;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import network.ClientConnections;
import network.packets.PacketLeaderBoard;
import network.packets.PacketReplicate;
import org.eclipse.jetty.websocket.api.Session;
import replication.Replicator;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Ольга on 29.11.2016.
 */
public class LeaderboardImpl implements Leaderboard{
    @Override
    public void makeLeaderboard() {
        LeaderboardJsonReader reader = new LeaderboardJsonReader();
        String [] leaderboard = reader.read();
        for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
            for (Map.Entry<Player, Session> connection : ApplicationContext.instance().get(ClientConnections.class).getConnections()) {
                if (gameSession.getPlayers().contains(connection.getKey())) {
                    try {
                        new PacketLeaderBoard(leaderboard).write(connection.getValue());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
