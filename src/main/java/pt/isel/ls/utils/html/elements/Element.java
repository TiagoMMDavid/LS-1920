package pt.isel.ls.utils.html.elements;

import pt.isel.ls.utils.Pair;

import java.util.LinkedList;

public abstract class Element {

    LinkedList<Element> children = new LinkedList<>();
    Pair<String, String> delimiters;

    protected Element(Pair<String, String> delimiters, Element[] children) {
        this.delimiters = delimiters;
    }

    protected Element(Pair<String, String> delimiters) {
        this.delimiters = delimiters;
    }

    protected Element(String startDelimiter, Element[] children, String endDelimiter) {
        delimiters = new Pair<>(startDelimiter, endDelimiter);
        for (Element child: children) {
            addChild(child);
        }
    }

    protected Element(String startDelimiter, String endDelimiter) {
        delimiters = new Pair<>(startDelimiter, endDelimiter);
    }

    public Element addChild(Element element) {
        children.add(element);
        return this;
    }

    public String getString(int tabAmount) {
        StringBuilder builder = new StringBuilder();
        appendTabs(builder, tabAmount); //Append necessary tabs for correct HTML indentation

        builder.append(delimiters.first);
        addContent(builder, tabAmount);

        builder.append(delimiters.second);
        return builder.toString();
    }

    protected void addContent(StringBuilder builder, int tabAmount) {
        ++tabAmount;
        for (Element child : children) {
            builder.append("\n");
            builder.append(child.getString(tabAmount));
        }
        //Append new line to separate delimiters and add necessary tabs for correct HTML indentation
        builder.append("\n");

        //We decrease tab Amount by one since we want to only add tabs until we get the same tab amount
        //before calling this method, because we want to close the previously opened element
        appendTabs(builder, tabAmount - 1);
    }

    protected void appendTabs(StringBuilder builder, int tabAmount) {
        for (int i = 0; i < tabAmount; i++) {
            builder.append("\t");
        }
    }

    @Override
    public String toString() {
        return getString(0);
    }
}
