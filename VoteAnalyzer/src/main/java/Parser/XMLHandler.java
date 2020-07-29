package Parser;

import Connection.DBConnection;
import Core.Voter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

public class XMLHandler extends DefaultHandler {

  private Voter voter;
  private static DateTimeFormatter birthDayFormat =
      DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.getDefault());

  private HashMap<Voter, Integer> voterCounts;
  private Connection connection = DBConnection.getConnection();

  public XMLHandler() {
    voterCounts = new HashMap<>();
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    if (qName.equals("voter") && voter == null) {
      LocalDate birthDay = LocalDate.from(birthDayFormat.parse(attributes.getValue("birthDay")));
      voter = new Voter(attributes.getValue("name"), birthDay);
    } else if (qName.equals("visit") && voter != null) {
      int count = voterCounts.getOrDefault(voter, 0);
      voterCounts.put(voter, count + 1);
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (qName.equals("voter")) {
      voter = null;
    }
  }

  public void printDuplicatedVoters() {
    System.out.println("Duplicated voters");
    for (Voter voter : voterCounts.keySet()) {
      int count = voterCounts.get(voter);
      if (count > 1) {
        System.out.println(voter.toString() + " - " + count);
      }
    }
  }
}
