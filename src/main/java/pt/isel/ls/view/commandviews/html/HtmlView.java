package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.view.View;

/**
 * This class represents an HTML View.
 * Each HTML View must extend this one
 */
public abstract class HtmlView extends View {
    protected static final String HTML_DEFAULT_FONT = "Verdana";

    @Override
    public String getViewFormat() {
        return HtmlViewFormat;
    }
}
