package pt.isel.ls.utils.html.elements;

import pt.isel.ls.utils.Pair;

public abstract class ElementText extends Element {

    /**
     * This is a simple class used to represent an HTML element that has no children.
     * The only content it has is a textual representation in the form of a String
     */
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
