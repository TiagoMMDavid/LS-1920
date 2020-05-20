package pt.isel.ls.view.commandviews.plain;

import pt.isel.ls.view.View;

public abstract class PlainView extends View {

    @Override
    public String getViewFormat() {
        return PlainViewFormat;
    }
}
