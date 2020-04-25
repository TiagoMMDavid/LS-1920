package pt.isel.ls.model.commands.common;

import java.util.HashMap;
import java.util.LinkedList;

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
            if (value.isEmpty() || name.isEmpty()) {
                throw new IllegalArgumentException("Wrong parameters format");
            }

            value = value.replace('+', ' ');
            addToMap(name, value);
        }
    }

    private void addToMap(String name, String value) {
        LinkedList<String> list = params.get(name);
        if (list == null) {
            list = new LinkedList<>();
            list.add(value);
        } else {
            list.add(value);
        }
        params.put(name, list);
    }

    public Iterable<String> getValues(String key) {
        return params.get(key);
    }

    public String getString(String varName) {
        LinkedList<String> curr = params.get(varName);
        return curr == null ? null : curr.getFirst();
    }

    public Integer getInt(String varName) {
        String res = getString(varName);
        return res == null ? null : Integer.parseInt(res);
    }
}
