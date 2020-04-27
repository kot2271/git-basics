import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class ImageResizer extends Thread {
    private Queue<Path> queue = new ConcurrentLinkedQueue<>();

    private File file;
    private int newWidth;
    private String dstFolder;
    private long start;

    public ImageResizer(Queue<Path> queue,File file, int newWidth, String dstFolder, long start) throws IOException {
        this.queue = queue;
        this.file = file;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
        this.start = start;

        queue.addAll(Files.walk(Paths.get(String.valueOf(file))).collect(Collectors.toList()));
    }

    @Override
    public void run()  {
    for (;;) {
      Path flow = queue.poll();
      if (flow == null) break;

      System.out.println(Thread.currentThread().getName() + " started ");

      try {
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
          return;
        }

        int newHeight =
            (int) Math.round(image.getHeight() / (image.getWidth() / (double) newWidth));

        BufferedImage newImage;

        if (image.getWidth() / newWidth > 2) {
          newImage = resizeImg(image, 2 * newWidth, 2 * newHeight, ScalingMode.BILINEAR);
          newImage = resizeImg(newImage, newWidth, newHeight, ScalingMode.BICUBIC);
        } else {
          newImage = resizeImg(image, newWidth, newHeight, ScalingMode.PROGRESSIVE_BILINEAR);
        }

        File newFile =
            new File(
                dstFolder + File.separator + Thread.currentThread().getName() + file.getName());
        ImageIO.write(newImage, "jpg", newFile);

      } catch (IOException e) {
        e.printStackTrace();
      }
        }
    }

    private BufferedImage resizeImg(BufferedImage image, int width, int height, ScalingMode mode) {
        try {
            return Thumbnails.of(image).size(width, height).scalingMode(mode).asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
       System.out.println("Не изменён размер изображения");
        }
        return image;
    }
}
