package main;

import accountserver.AccountServer;
import matchmaker.MatchMaker;
import matchmaker.MatchMakerImpl;
import messageSystem.MessageSystem;
import model.GameSessionImpl;
import network.ClientConnectionServer;
import mechanics.Mechanics;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import replication.FullStateReplicator;
import replication.Replicator;
import replication.ReplicatorJson;
import ticker.Ticker;
import utils.IDGenerator;
import utils.SequentialIDGenerator;
import java.io.*;
import java.util.Properties;

import java.util.concurrent.ExecutionException;

/**
 * Created by apomosov on 14.05.16.
 */
public class MasterServer {
  @NotNull
  private final static Logger log = LogManager.getLogger(MasterServer.class);

  private void start() throws ExecutionException, InterruptedException {
    log.info("MasterServer started");
      MasterServerConfiguration config = new MasterServerConfiguration();
try{
    //TODO RK3 configure server parameters
    ApplicationContext.instance().put(Class.forName(config.MATCH_MAKER), new MatchMakerImpl());
    ApplicationContext.instance().put(ClientConnections.class, new ClientConnections());
//    ApplicationContext.instance().put(Class.forName(config.REPLICATOR), new FullStateReplicator());
    ApplicationContext.instance().put(Class.forName(config.REPLICATOR), new ReplicatorJson());
    ApplicationContext.instance().put(IDGenerator.class, new SequentialIDGenerator());
}catch (ClassNotFoundException e){
  e.printStackTrace();
}
    MessageSystem messageSystem = new MessageSystem();
    ApplicationContext.instance().put(MessageSystem.class, messageSystem);

    Mechanics mechanics = new Mechanics();
try {
  messageSystem.registerService(Class.forName(config.SERVICES[0]), mechanics);
  messageSystem.registerService(Class.forName(config.SERVICES[1]), new AccountServer(Integer.parseInt(config.ACCOUNT_SERVER_PORT)));
  messageSystem.registerService(Class.forName(config.SERVICES[2]), new ClientConnectionServer(Integer.parseInt(config.CLENT_CONNECTION_PORT)));
  messageSystem.getServices().forEach(Service::start);
}catch (ClassNotFoundException e){
  e.printStackTrace();
}
    for (Service service : messageSystem.getServices()) {
      service.join();
    }
  }

  public static void main(@NotNull String[] args) throws ExecutionException, InterruptedException {
    MasterServer server = new MasterServer();
    server.start();
  }
}
