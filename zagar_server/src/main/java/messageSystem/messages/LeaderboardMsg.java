package messageSystem.messages;

import main.ApplicationContext;
import matchmaker.MatchMaker;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import network.ClientConnectionServer;
import replication.Replicator;

/**
 * Created by Ольга on 01.12.2016.
 */
public class LeaderboardMsg extends Message{

    public LeaderboardMsg(Address from) {
        super(from, ApplicationContext.instance().get(MessageSystem.class).getService(ClientConnectionServer.class).getAddress());
    }

    @Override
    public void exec(Abonent abonent) {
        ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions().forEach((gs)->gs.getLeaderboard().makeLeaderboard());
    }
}

