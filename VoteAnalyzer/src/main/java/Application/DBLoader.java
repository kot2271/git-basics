package Application;

import Connection.DBConnection;
import Parser.XMLHandlerDB;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBLoader {
  public static void main(String[] args)
      throws SQLException, ParserConfigurationException, SAXException, IOException {

    String fileName = "src/main/resources/data-18M.xml";

    long start = System.currentTimeMillis();
    Connection connection = DBConnection.getConnection();

    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser = factory.newSAXParser();
    XMLHandlerDB handler = new XMLHandlerDB();
    parser.parse(new File(fileName), handler);
    handler.executeInsert();

    System.out.println(
        "Загрузка всех данных в БД заняла " + (System.currentTimeMillis() - start) + " ms");

    start = System.currentTimeMillis();
    DBConnection.printVoterCountsForSAXParser();
    System.out.println(
        "Выбор необходимой информации " + (System.currentTimeMillis() - start) + " ms");

    connection.close();
  }
}
