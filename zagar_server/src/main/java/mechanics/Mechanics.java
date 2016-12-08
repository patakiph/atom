package mechanics;

import com.sun.jmx.remote.internal.ClientCommunicatorAdmin;
import main.ApplicationContext;
import main.Service;
import matchmaker.MatchMaker;
import messageSystem.Abonent;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.LeaderboardMsg;
import messageSystem.messages.ReplicateMsg;
import model.GameSessionImpl;
import network.ClientConnectionServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import replication.Replicator;
import ticker.Tickable;
import ticker.Ticker;

/**
 * Created by apomosov on 14.05.16.
 */
public class Mechanics extends Service implements Tickable {
  @NotNull
  private final static Logger log = LogManager.getLogger(Mechanics.class);

  public Mechanics() {
    super("mechanics");
  }

  @Override
  public void run() {
    log.info(getAddress() + " started");
    Ticker ticker = new Ticker(this, 1);
    ticker.loop();
  }

  @Override
  public void tick(long elapsedNanos) {
    try {
      Thread.sleep(1500);
    } catch (InterruptedException e) {
      log.error(e);
      Thread.currentThread().interrupt();
      e.printStackTrace();
    }

    log.info("Start replication");
    @NotNull MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
    Message message = new ReplicateMsg(this.getAddress());
    Message msg = new LeaderboardMsg(this.getAddress());
    messageSystem.sendMessage(message);
    messageSystem.sendMessage(msg);
    //execute all messages from queue
    messageSystem.execForService(this);

  }
}
