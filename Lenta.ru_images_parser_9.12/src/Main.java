import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
  private static Pattern HTTP = Pattern.compile("^(http)");
  private static String myUserAgent =
      "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36";
  private static String downloadFolder = "data/";
  private static int download = 0;

  public static void main(String[] args) throws IOException {

    Document document = parseFile("https://lenta.ru/");

    if (document != null) {
      downloadImages(document);
    }
  }

  private static void downloadImages(Document document) {
    Elements elements = document.select("img");

    System.out.println("Загрузка изображений с сайта " + document.title());
    elements.forEach(
        element -> {
          String link = element.attr("src");
          System.out.printf("%d) %s\t", ++download, link);

          URL url = getURL(link);
          if (url == null) {
            System.out.printf("Файл не найден по указанному адресу %s\n", link);
            return;
          }

          String fileName = getFileName(url);
          try (ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
              FileOutputStream fileOutputStream =
                  new FileOutputStream(downloadFolder + fileName); ) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            System.out.printf("\tЗагружен файл: %s\n", fileName);

          } catch (Exception e) {
            System.out.printf("Возникла ошибка при загрузке %s\n", fileName);
          }
        });

    System.out.println("Всего файлов " + elements.size() + ". Загружено " + download);
  }

  private static String getFileName(URL url) {
    String fileName;
    String src = url.getPath();
    String[] split = src.split("\\/");
    fileName = split[split.length - 1];
    return fileName.trim();
  }

  private static URL getURL(String link) {
    URL url;
    String fullLink;
    Matcher matcher = HTTP.matcher(link);
    if (!matcher.find()) {
      fullLink = "https://" + link.replaceFirst("^(\\/\\/|\\/)", "");
    } else {
      fullLink = link;
    }
    try {
      url = new URL(fullLink);
    } catch (MalformedURLException e) {
      System.out.println("Некорректный формат ссылки " + fullLink);
      url = null;
    }
    return url;
  }

  private static Document parseFile(String path) {
    Document document = null;
    try {
      document =
          Jsoup.connect(path)
              .userAgent(myUserAgent)
              .referrer("https://google.com")
              .maxBodySize(0)
              .get();
    } catch (IOException e) {
      System.out.printf("Возникла ошибка\n при подключении к сайту '%s'", path);
    }
    return document;
  }
}
