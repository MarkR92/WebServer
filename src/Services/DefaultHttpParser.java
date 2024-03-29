package Services;

import Models.HttpRequestModel;

public class DefaultHttpParser implements IHttpRequestParser{

    private String extractValue(String[] lines, String key) {
        for (String line : lines) {
            if (line.startsWith(key + ":")) {
                return line.substring(key.length() + 1).trim();
            }
        }
        return null;
    }

    @Override
    public HttpRequestModel ParseHttpRequest(String input) {

        var model = new HttpRequestModel();
        //Splitting all logged data into array lines
        String[] lines = input.split("\\r?\\n");

        //Extracting logged data and placing them into HTTPRequestModel
        model.Host = extractValue(lines, "Host");

        String[] lineOneParts = lines[0].split(" "); //splitting 1st line into parts EG. "GET[0], /path[1], HTTP1.1[2]"
        model.RequestType = lineOneParts[0]; //Request type GET PUT POST DELETE
        model.Path = lineOneParts[1];// /path/to/file
        model.Connection = extractValue(lines, "Connection");

        for (String line : lines) {
            String[] parts = line.split(":", 2);
            if (parts.length == 2) {
                model.addHeader(parts[0].trim(), parts[1].trim());
            } else if (parts.length > 2) {
                String key = parts[0].trim();
                String value = String.join(":", parts[1].trim(), parts[2].trim());
                model.addHeader(key, value);
            }
        }



        return model;
    }
    
}
