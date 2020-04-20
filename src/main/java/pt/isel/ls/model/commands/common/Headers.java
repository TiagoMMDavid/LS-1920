package pt.isel.ls.model.commands.common;

import java.util.HashMap;

public class Headers {
    private HashMap<String, String> headers;

    public Headers(String headers) {
        this.headers = new HashMap<>();
        processHeaders(headers);
    }

    private void processHeaders(String params) {
        String[] splitParams = params.split("\\|");
        for (String param: splitParams) {
            int indexOfEqual = param.indexOf(':');
            if (indexOfEqual < 0) {
                throw new IllegalArgumentException();
            }

            String name = param.substring(0, indexOfEqual);
            String value = param.substring(indexOfEqual + 1);
            if (value.isEmpty() || name.isEmpty()) {
                throw new IllegalArgumentException();
            }

            addToMap(name, value);
        }
    }

    private void addToMap(String name, String value) {
        headers.put(name, value);
    }

    public String getValue(String header) {
        return headers.get(header);
    }
}
