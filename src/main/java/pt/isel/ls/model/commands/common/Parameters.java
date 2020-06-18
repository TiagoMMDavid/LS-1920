package pt.isel.ls.model.commands.common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Parameters {
    private HashMap<String, LinkedList<String>> params;

    public Parameters(String params) {
        this.params = new HashMap<>();
        processParams(params);
    }

    /**
     * Process the given parameters String
     * @param params the String containing the parameters and their value
     */
    private void processParams(String params) {
        String[] splitParams = params.split("&");
        for (String param: splitParams) {
            int indexOfEqual = param.indexOf('=');
            if (indexOfEqual < 0) {
                throw new IllegalArgumentException("Wrong parameters format");
            }

            String name = param.substring(0, indexOfEqual);
            String value = param.substring(indexOfEqual + 1);

            if (name.isEmpty()) {
                throw new IllegalArgumentException("Wrong parameters format");
            }
            // Parameters without a value are valid, but are treated as non existent
            if (value.isEmpty()) {
                continue;
            }

            value = value.replace('+', ' ');
            addParam(name, value);
        }
    }

    /**
     * Adds a Parameter to the map. In case the parameter already exists, then add a new value to it.
     * @param name the Parameter's name
     * @param value the Parameter's value
     */
    public void addParam(String name, String value) {
        LinkedList<String> list = params.get(name);
        if (list == null) {
            list = new LinkedList<>();
        }
        list.add(value);
        params.put(name, list);
    }

    /**
     * Gets every value of a given Parameter
     * @param paramName the Parameter's name
     * @return the Parameter's values
     */
    public Iterable<String> getValues(String paramName) {
        return params.get(paramName);
    }

    /**
     * Gets every value of a given Parameter as a List
     * @param paramName the Parameter's name
     * @return the Parameter's values
     */
    public List<String> getValuesAsList(String paramName) {
        return params.get(paramName);
    }

    /**
     * Gets the first value of a given Parameter as a String
     * @param paramName the Parameter's name
     * @return the first value of the Parameter
     */
    public String getString(String paramName) {
        LinkedList<String> curr = params.get(paramName);
        return curr == null ? null : curr.getFirst();
    }

    /**
     * Gets the first value of a given Parameter as an Integer
     * @param paramName the Parameter's name
     * @return the first value of the Parameter
     */
    public Integer getInt(String paramName) throws NumberFormatException {
        String res = getString(paramName);
        return res == null ? null : Integer.parseInt(res);
    }
}
