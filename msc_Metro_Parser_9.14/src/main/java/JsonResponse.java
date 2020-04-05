import java.util.*;

public class JsonResponse {
    private LinkedHashMap<String, ArrayList<String>> stations = new LinkedHashMap<>();
    private LinkedHashMap<String, String> lines = new LinkedHashMap<>();

    public LinkedHashMap<String, ArrayList<String>> getStations() {
        return stations;
    }

    public List<String> getLines() {
        return (List<String>) lines;
    }


    public void setLines(LinkedHashMap<String, String> lines) {
        this.lines = lines;
    }

    public void setStations(LinkedHashMap<String, ArrayList<String>> stations) {
        this.stations = stations;
    }
}
