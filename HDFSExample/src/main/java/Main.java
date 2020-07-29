public class Main {
  private static String rootPath = "hdfs://ecf7a51992de:8020";

  public static void main(String[] args) throws Exception {
    FileAccess fileAccess = new FileAccess(rootPath);

    fileAccess.create("/test/file1.txt");
    fileAccess.create("/dir/file1");
    fileAccess.create("/test/file1.txt");
    fileAccess.create("/test/test/file1.txt");
    fileAccess.create("/test/test/test/file1.txt");
    fileAccess.create("/test/test/test/test/file1.txt");

    fileAccess.list("/").forEach(System.out::println);
  }
}
