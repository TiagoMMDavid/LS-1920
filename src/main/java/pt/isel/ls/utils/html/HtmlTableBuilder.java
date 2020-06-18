package pt.isel.ls.utils.html;

import pt.isel.ls.utils.Pair;
import pt.isel.ls.utils.html.elements.Element;

import java.util.LinkedList;
import java.util.function.Function;

import static pt.isel.ls.utils.html.HtmlDsl.table;
import static pt.isel.ls.utils.html.HtmlDsl.td;
import static pt.isel.ls.utils.html.HtmlDsl.th;
import static pt.isel.ls.utils.html.HtmlDsl.tr;

public class HtmlTableBuilder<T> {

    private Iterable<T> rowData;
    private LinkedList<Pair<String, Function<T, Object>>> columns = new LinkedList<>();

    public HtmlTableBuilder(Iterable<T> rowData) {
        this.rowData = rowData;
    }

    public HtmlTableBuilder<T> withColumn(String name, Function<T, Object> dataFunction) {
        columns.add(new Pair<>(name, dataFunction));
        return this;
    }

    /**
     * Builds a Table Element.
     * Each column contains a Pair of a String and a Function<T,Object>.
     * The String is used to represent the column name while the Function is used to get each row's content
     */
    public Element build() {
        Element table = table();
        Element tableHeaders = tr();

        columns.forEach(column -> tableHeaders.addChild(th(column.first)));
        table.addChild(tableHeaders);

        for (T row : rowData) {
            Element tableRowData = tr();
            columns.forEach(column -> tableRowData.addChild(td(column.second.apply(row).toString())));
            table.addChild(tableRowData);
        }

        return table;
    }
}
