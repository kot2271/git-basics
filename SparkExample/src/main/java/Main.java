import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    SparkSession spark = SparkSession
            .builder()
            .appName("JavaSparkPi")
            .config("spark.master", "local")
            .getOrCreate();

      JavaSparkContext javaSparkContext = new JavaSparkContext(spark.sparkContext());

      int slices = (args.length == 1) ? Integer.parseInt(args[0]) : 2;
      int n = 100_000 * slices;
      List<Integer> lines = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      lines.add(i);
    }

      JavaRDD<Integer> dataSet = javaSparkContext.parallelize(lines, slices);

    int count = dataSet.map(Integer -> {
        double x = Math.random() * 2 - 1;
        double y = Math.random() * 2 - 1;
        return (x * x + y * y <= 1) ? 1 : 0;
            }).reduce(Integer :: sum);

    System.out.println("Pi is roughly " + 4.0 * count / n);

    spark.stop();
  }
}
