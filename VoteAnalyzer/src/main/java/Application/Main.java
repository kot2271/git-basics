package Application;

import Core.Voter;
import Core.WorkTime;
import Parser.XMLHandler;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Main {

  private static DateTimeFormatter birthDayFormat =
      DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.getDefault());
  private static SimpleDateFormat visitDateFormat =
      new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault());

  private static HashMap<Integer, WorkTime> voteStationWorkTimes = new HashMap<>();
  private static HashMap<Voter, Integer> voterCounts = new HashMap<>();

  public static void main(String[] args) throws Exception {

    String fileName = "src/main/resources/data-18M.xml";

    System.out.println("SAX Parser: ");

    long usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser = factory.newSAXParser();
    XMLHandler handler = new XMLHandler();

    parser.parse(new File(fileName), handler);
    handler.printDuplicatedVoters();
    usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;

    System.out.println("SAX парсер забирает память: " + usage);

    System.out.println("DOM parser: ");

    usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    parseFile(fileName);

    System.out.println("Duplicated voters: ");

    for (Voter voter : voterCounts.keySet()) {
      Integer count = voterCounts.get(voter);
      if (count > 1) {
        System.out.println("\t" + voter + " - " + count);
      }
    }

    usage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usage;

    System.out.println("DOM парсер забирает память: " + usage);
  }

  private static void parseFile(String fileName) throws Exception {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.parse(new File(fileName));

    findEqualVoters(doc);
    fixWorkTimes(doc);
  }

  private static void findEqualVoters(Document doc) throws Exception {
    NodeList voters = doc.getElementsByTagName("voter");
    int votersCount = voters.getLength();
    for (int i = 0; i < votersCount; i++) {
      Node node = voters.item(i);
      NamedNodeMap attributes = node.getAttributes();

      String name = attributes.getNamedItem("name").getNodeValue();
      LocalDate birthDay =
          LocalDate.from(birthDayFormat.parse(attributes.getNamedItem("birthDay").getNodeValue()));

      Voter voter = new Voter(name, birthDay);
      Integer count = voterCounts.get(voter);
      voterCounts.put(voter, count == null ? 1 : count + 1);
    }
  }

  private static void fixWorkTimes(Document doc) throws Exception {
    NodeList visits = doc.getElementsByTagName("visit");
    int visitCount = visits.getLength();
    for (int i = 0; i < visitCount; i++) {
      Node node = visits.item(i);
      NamedNodeMap attributes = node.getAttributes();

      Integer station = Integer.parseInt(attributes.getNamedItem("station").getNodeValue());
      Date time = visitDateFormat.parse(attributes.getNamedItem("time").getNodeValue());
      WorkTime workTime = voteStationWorkTimes.get(station);
      if (workTime == null) {
        workTime = new WorkTime();
        voteStationWorkTimes.put(station, workTime);
      }
      workTime.addVisitTime(time.getTime());
    }
  }
}
