package pt.isel.ls.utils.html.elements;

import pt.isel.ls.utils.Pair;

public abstract class ElementText extends Element {
    private String text;

    protected ElementText(Pair<String, String> delimiters, String text) {
        super(delimiters);
        this.text = text;
    }

    protected ElementText(String startDelimiter, String text, String endDelimiter) {
        super(startDelimiter, endDelimiter);
        this.text = text;
    }

    @Override
    protected void addContent(StringBuilder builder, int tabAmount) {
        builder.append(text);
    }
}
