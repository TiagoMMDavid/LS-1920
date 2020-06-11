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

    public void addParam(String name, String value) {
        LinkedList<String> list = params.get(name);
        if (list == null) {
            list = new LinkedList<>();
        }
        list.add(value);
        params.put(name, list);
    }

    public Iterable<String> getValues(String key) {
        return params.get(key);
    }

    public List<String> getValuesAsList(String key) {
        return params.get(key);
    }

    public String getString(String varName) {
        LinkedList<String> curr = params.get(varName);
        return curr == null ? null : curr.getFirst();
    }

    public Integer getInt(String varName) throws NumberFormatException {
        String res = getString(varName);
        return res == null ? null : Integer.parseInt(res);
    }
}
