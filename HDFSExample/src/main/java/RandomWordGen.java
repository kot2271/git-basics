import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class RandomWordGen {

  private static String rootPath = "hdfs://ecf7a51992de:8020";
  private static String symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public static void main(String[] args) throws Exception {
    FileSystem hdfs = FileSystem.get(new URI(rootPath), HdfsConfiguration.getConfiguration());
    Path file = new Path("hdfs://ecf7a51992de:8020/test/file.txt");

    if (hdfs.exists(file)) {
      hdfs.delete(file, true);
    }

    OutputStream outputStream = hdfs.create(file);
    BufferedWriter bufferedWriter =
        new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

    for (int i = 0; i < 10_000_000; i++) {
      bufferedWriter.write(getRandomWord() + " ");
    }

    bufferedWriter.flush();
    bufferedWriter.close();
    hdfs.close();
  }

  private static String getRandomWord() {
    StringBuilder builder = new StringBuilder();
    int length = 2 + (int) Math.round(10 * Math.random());
    int symbolsCount = symbols.length();
    for (int i = 0; i < length; i++) {
      builder.append(symbols.charAt((int) (symbolsCount * Math.random())));
    }
    return builder.toString();
  }
}
