package pt.isel.ls.model.commands.common;

import java.util.HashMap;
import java.util.LinkedList;

public class Headers {
    private HashMap<String, LinkedList<String>> headers;

    public Headers(String headers) {
        this.headers = new HashMap<>();
        processHeaders(headers);
    }

    /**
     * Process the given headers String
     * @param headers the String containing the headers and their value
     */
    private void processHeaders(String headers) {
        String[] splitParams = headers.split("\\|");
        for (String param: splitParams) {
            int indexOfEqual = param.indexOf(':');
            if (indexOfEqual < 0) {
                throw new IllegalArgumentException("Wrong header format");
            }

            String name = param.substring(0, indexOfEqual);
            String value = param.substring(indexOfEqual + 1);
            if (value.isEmpty() || name.isEmpty()) {
                throw new IllegalArgumentException("Wrong header format");
            }

            // Split values by preference, since the most preferred ones appear first
            String[] preferences = value.split(";");
            for (String pref: preferences) {
                for (String val: pref.split(",")) {
                    // Ignore q's and v's
                    if (!val.contains("=")) {
                        addToMap(name, val);
                    }
                }
            }
        }
    }

    /**
     * Adds a Header to the Map
     * @param name the Header's name
     * @param value the Header's value
     */
    private void addToMap(String name, String value) {
        LinkedList<String> list = headers.get(name);
        if (list == null) {
            list = new LinkedList<>();
        }
        list.add(value);
        headers.put(name, list);
    }

    /**
     * Gets the first value from a Header
     * @param header the Header's name
     * @return the first value of the Header
     */
    public String getFirst(String header) {
        LinkedList<String> values = headers.get(header);
        return values != null ? values.get(0) : null;
    }

    /**
     * Gets every value from a given Header
     * @param header the Header's name
     * @return the Header's values
     */
    public Iterable<String> getValues(String header) {
        return headers.get(header);
    }
}
