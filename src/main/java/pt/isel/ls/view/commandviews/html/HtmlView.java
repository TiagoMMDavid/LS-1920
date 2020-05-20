package pt.isel.ls.view.commandviews.html;

import pt.isel.ls.view.View;

public abstract class HtmlView extends View {

    @Override
    public String getViewFormat() {
        return HtmlViewFormat;
    }
}
