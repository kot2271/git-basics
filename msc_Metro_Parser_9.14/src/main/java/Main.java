import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Main {
  private static final int STATION_NUMBER = 0;
  private static final int STATION_NAME = 1;
  private static final int CONNECTIONS_NUMBER = 2;
  private static final int CONNECTIONS_LINE_NAME = 3;

  public static void main(String[] args) throws IOException {

    Document document =
        Jsoup.connect("https://ru.wikipedia.org/wiki/Список_станций_Московского_метрополитена")
            .maxBodySize(0)
            .get();
    Element table = document.getElementsByTag("table").get(3);
    Elements rows = table.getElementsByTag("tr");

    LinkedHashMap<String, String> lines = getLines(rows);
    LinkedHashMap<String, ArrayList<String>> stations = getStations(rows);

    ArrayList<LinkedHashMap<String, String>> linesToFormat = new ArrayList<>();
    for (Map.Entry<String, String> entry : lines.entrySet()) {
      LinkedHashMap<String, String> entryToAdd = new LinkedHashMap<>();
      entryToAdd.put("name", entry.getValue());
      entryToAdd.put("number", entry.getKey());
      linesToFormat.add(entryToAdd);
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.append("lines", linesToFormat);

    jsonObject.append("stations", stations);

    Path path = Paths.get("src/main/resources/metro.json");
    if (!path.toFile().exists()) {
      Files.write(path, jsonObject.toString(5).getBytes(), StandardOpenOption.CREATE_NEW);
    } else {
      Files.write(path, jsonObject.toString(5).getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(path.toString())) {
      org.json.simple.JSONObject json = (org.json.simple.JSONObject) parser.parse(reader);
      JSONArray stationsArray = (JSONArray) json.get("stations");
      org.json.simple.JSONObject simple = (org.json.simple.JSONObject) stationsArray.get(0);
      simple.forEach(
          (k, v) ->
              System.out.println(
                  " На " + k + " Линии " + " : " + ((JSONArray) v).size() + " Станции(ий) "));
    } catch (Exception e) {
      e.printStackTrace();
    }

    JsonResponse jsonResponse = new JsonResponse();
    jsonResponse.setStations(stations);
    jsonResponse.setLines(lines);

    String json =
        new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(jsonResponse);
    System.out.println(json);
  }

  private static LinkedHashMap<String, ArrayList<String>> getStations(Elements rows) {
    LinkedHashMap<String, ArrayList<String>> metro = new LinkedHashMap<>();
    ArrayList<String> thisStation;

    for (Element row : rows) {
      if (row.equals(rows.get(STATION_NUMBER))) {
        continue;
      }
      Element firstTd = row.child(STATION_NUMBER);
      Element secondTd = row.child(STATION_NAME);
      String name = secondTd.select("a[href]").get(0).text();
      Elements spans = firstTd.getElementsByTag("span");
      Set<String> linesNums = new LinkedHashSet<>();
      if (spans.size() > 3) {
        String num1 = spans.get(0).text();
        String num2 = spans.get(2).text();
        linesNums.add(num1);
        linesNums.add(num2);
      } else {
        String num = spans.get(0).text();
        linesNums.add(num);
      }
      for (String line : linesNums) {
        if (!metro.containsKey(line)) {
          ArrayList<String> stationsList = new ArrayList<>();
          stationsList.add(name);
          metro.put(line, stationsList);
        } else {
          thisStation = metro.get(line);
          thisStation.add(name);
          metro.put(line, thisStation);
        }
      }
    }
    return metro;
  }

  private static LinkedHashMap<String, String> getLines(Elements rows) {
    LinkedHashMap<String, String> linesWithNum = new LinkedHashMap<>();
    for (Element row : rows) {
      if (row.equals(rows.get(0))) {
        continue;
      }
      Element firstTd = row.child(0);
      Elements spans = firstTd.getElementsByTag("span");
      if (spans.size() > 3) {
        String num1 = spans.get(STATION_NUMBER).text();
        String name1 = spans.get(STATION_NAME).attr("title");
        String num2 = spans.get(CONNECTIONS_NUMBER).text();
        String name2 = spans.get(CONNECTIONS_LINE_NAME).attr("title");
        linesWithNum.put(num1, name1);
        linesWithNum.put(num2, name2);
      } else {
        String num = spans.get(STATION_NUMBER).text();
        String name = spans.get(STATION_NAME).attr("title");
        linesWithNum.put(num, name);
      }
    }
    return linesWithNum;
  }
}
