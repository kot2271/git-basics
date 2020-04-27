import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main
{
    private static int newWidth = 300;

    public static void main(String[] args) throws InterruptedException, IOException {
        String srcFolder = "/Users/Data/src";
        String dstFolder = "/Users/Data/dst";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        Queue<Path> queue = new ConcurrentLinkedQueue<>();

        int countOfCores = Runtime.getRuntime().availableProcessors();
        System.out.println("Потоков в системе: " + countOfCores);

        ExecutorService service = Executors.newFixedThreadPool(countOfCores);
        for (int i = 0; i < files.length; i++) {
            service.submit(new ImageResizer(queue,files[i], newWidth, dstFolder, start));
        }
        service.shutdown();
        service.awaitTermination(20, SECONDS);

        System.out.println("Finished after start: " + (System.currentTimeMillis() - start)/1000.0 + " seconds");
    }
}
