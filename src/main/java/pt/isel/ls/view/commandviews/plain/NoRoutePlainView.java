package pt.isel.ls.view.commandviews.plain;

public class NoRoutePlainView extends PlainView {

    public NoRoutePlainView() {
        this.foundRoot = false;
    }

    @Override
    protected String display() {
        return "No textual representation available!";
    }
}
