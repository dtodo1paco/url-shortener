package org.dtodo1paco.samples.urlshortener.test;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.springframework.data.mongodb.core.MongoTemplate;

public abstract class AbstractMongoDBTest {

  /**
   * please store Starter or RuntimeConfig in a static final field
   * if you want to use artifact store caching (or else disable caching)
   */
  private static final MongodStarter starter = MongodStarter.getDefaultInstance();

  private static final String CONNECTION_STRING = "mongodb://%s:%d";
  private MongodExecutable _mongodExe;
  private MongodProcess _mongod;
  private MongoTemplate mongoTemplate;

  private MongoClient _mongo;
  private int port;

  @Before
  public void setUp() throws Exception {
    String ip = "localhost";
    int port = 27017;

    IMongodConfig mongodConfig = new MongodConfigBuilder()
      .version(Version.Main.PRODUCTION)
      .net(new Net(ip, port, Network.localhostIsIPv6()))
      .build();

    MongodStarter starter = MongodStarter.getDefaultInstance();
    _mongodExe = starter.prepare(mongodConfig);
    _mongodExe.start();
    mongoTemplate = new MongoTemplate(MongoClients
            .create(String.format(CONNECTION_STRING, ip, port)),
            "test");
    initDb();
  }

  public abstract void initDb();

  public int port() {
    return port;
  }

  @AfterAll
  protected void tearDown() throws Exception {
    System.out.println("\n\n ----------- MONGO STOPS --------------");
    _mongod.stop();
    _mongodExe.stop();
  }

  public Mongo getMongo() {
    return _mongo;
  }

}
