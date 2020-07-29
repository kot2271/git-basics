import org.apache.hadoop.conf.Configuration;

public class HdfsConfiguration {
  private static Configuration configuration;

  static {
    configuration = new Configuration();
    configuration.set("dfs.client.use.datanode.hostname", "true");
    configuration.setBoolean("dfs.support.append", true);
    System.setProperty("HADOOP_USER_NAME", "root");
  }

  public static Configuration getConfiguration() {
    return configuration;
  }
}
