package Models;
import java.util.*;
public class HttpRequestModel {
    public String Host;
    public String RequestType;
    public String Connection;
    public String Path;

    // public Socket? Client { get; set; }

    public List<Map.Entry<String, String>> headers = new ArrayList<>();
}
