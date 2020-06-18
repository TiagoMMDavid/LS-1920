package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.view.View;

/**
 * This class represents a plain text View.
 * Each textual View must extend this one
 */
public abstract class PlainView extends View {

    @Override
    public String getViewFormat() {
        return PlainViewFormat;
    }
}
