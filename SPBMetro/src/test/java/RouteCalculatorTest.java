import core.Line;
import core.Station;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RouteCalculatorTest extends TestCase {

  List<Station> viaTransferRoute;
  List<Station> oneTransferRoute;
  List<Station> twoTransferRoute;
  List<Station> fromStationToSameStationRoute;

  StationIndex stationIndex;
  RouteCalculator routeCalculator;

  Station risovaya;
  Station dostoevskaya;
  Station lermontovskaya;
  Station platinovaya;

  @Override
  public void setUp() throws Exception {
    //    Тестовая схема линий метрополитена
    //      [L1]-[Transfer]-[L2]            [L3]
    //      [L1]            [L2]-[Transfer]-[L3]
    //      [L1]            [L2]            [L3]

    stationIndex = new StationIndex();

    Line line1 = new Line(1, "Красная");
    Line line2 = new Line(2, "Синяя");
    Line line3 = new Line(3, "Зелёная");

    risovaya = new Station("Рисовая", line1);
    Station ovsjanaya = new Station("Овсяная", line1);
    Station grechnevaya = new Station("Гречневая", line1);

    dostoevskaya = new Station("Достоевская", line2);
    Station mayakovskaya = new Station("Маяковская", line2);
    lermontovskaya = new Station("Лермонтовская", line2);

    platinovaya = new Station("Платиновая", line3);
    Station zolotaya = new Station("Золотая", line3);
    Station mednaya = new Station("Медная", line3);

    Stream.of(line1, line2, line3).forEach(stationIndex::addLine);

    Stream.of(
            risovaya,
            ovsjanaya,
            grechnevaya,
            dostoevskaya,
            mayakovskaya,
            lermontovskaya,
            platinovaya,
            zolotaya,
            mednaya)
        .peek(station -> station.getLine().addStation(station))
        .forEach(stationIndex::addStation);

    stationIndex.addConnection(Stream.of(risovaya, dostoevskaya).collect(Collectors.toList()));

    stationIndex.addConnection(Stream.of(mayakovskaya, zolotaya).collect(Collectors.toList()));

    stationIndex.getConnectedStations(risovaya);

    stationIndex.getConnectedStations(mayakovskaya);

    routeCalculator = new RouteCalculator(stationIndex);

    //    Маршруты для тестов
    viaTransferRoute =
        Stream.of(dostoevskaya, mayakovskaya, lermontovskaya).collect(Collectors.toList());

    oneTransferRoute =
        Stream.of(dostoevskaya, mayakovskaya, zolotaya, platinovaya).collect(Collectors.toList());

    twoTransferRoute =
        Stream.of(risovaya, dostoevskaya, mayakovskaya, zolotaya, platinovaya)
            .collect(Collectors.toList());
    fromStationToSameStationRoute = Stream.of(risovaya).collect(Collectors.toList());
  }

  public void testCalculateDurationViaTransferRoute() {
    double actual = RouteCalculator.calculateDuration(viaTransferRoute);
    double expected = 5;
    assertEquals(expected, actual);
  }

  public void testCalculateDurationOneTransferRoute() {
    double actual = RouteCalculator.calculateDuration(oneTransferRoute);
    double expected = 8.5;
    assertEquals(expected, actual);
  }

  public void testCalculateDurationTwoTransferRoute() {
    double actual = RouteCalculator.calculateDuration(twoTransferRoute);
    double expected = 12;
    assertEquals(expected, actual);
  }

  public void testCalculateDurationFromStationToSameStationRoute() {
    double actual = RouteCalculator.calculateDuration(fromStationToSameStationRoute);
    double expected = 0;
    assertEquals(expected, actual);
  }

  public void testGetViaTransferRoute() {
    List<Station> actualViaTransfer =
        routeCalculator.getShortestRoute(dostoevskaya, lermontovskaya);
    List<Station> expectedViaTransfer = viaTransferRoute;
    Assert.assertEquals(expectedViaTransfer, actualViaTransfer);
  }

  public void testGetOneTransferRoute() {
    List<Station> actualOneTransfer = routeCalculator.getShortestRoute(dostoevskaya, platinovaya);
    List<Station> expectedOneTransfer = oneTransferRoute;
    Assert.assertEquals(expectedOneTransfer, actualOneTransfer);
  }

  public void testGetTwoTransferRoute() {
    List<Station> actualTwoTransfer = routeCalculator.getShortestRoute(risovaya, platinovaya);
    List<Station> expectedTwoTransfer = twoTransferRoute;
    Assert.assertEquals(expectedTwoTransfer, actualTwoTransfer);
  }

  public void testGetRouteFromStationToSameStation() {
    List<Station> actualRouteFromStationToSameStation =
        routeCalculator.getShortestRoute(risovaya, risovaya);
    List<Station> expectedRouteFromStationToSameStation = fromStationToSameStationRoute;
    Assert.assertEquals(expectedRouteFromStationToSameStation, actualRouteFromStationToSameStation);
  }
}
