import com.skillbox.airport.Aircraft;
import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.util.Comparator;
import java.util.List;


public class Main {
  private static final int DEPARTURE_HOURS = 2 * 60 * 60 * 1000;
  private static long rightNow;
  private static Airport airport;
  private static List<Terminal>terminals;

  public static void main(String[] args) {

    airport = Airport.getInstance();
    terminals = airport.getTerminals();

    terminals.forEach(
        terminal -> {
          List<Flight> flights = terminal.getFlights();
          flights.stream()
              .filter(Main::departureInTheNext2Hours)
              .filter(flight -> flight.getType().equals(Flight.Type.DEPARTURE))
              .forEach(i -> System.out.println(i + " - " + i.getAircraft()));
        });
    // Рейсы по порядку времени вылета
    System.out.println("___________________________________");

    terminals.stream()
        .flatMap(terminal -> terminal.getFlights().stream())
        .filter(Main::departureInTheNext2Hours)
        .filter(flight -> flight.getType().equals(Flight.Type.DEPARTURE))
            .sorted(Comparator.comparing(Flight::getDate))
        .forEach(i -> System.out.println(i + " - " + i.getAircraft()));

  }

  private static boolean departureInTheNext2Hours(Flight flight) {
    rightNow = System.currentTimeMillis();
    return flight.getDate().getTime() - rightNow <= DEPARTURE_HOURS
        && flight.getDate().getTime() - rightNow > 0;
  }
}
