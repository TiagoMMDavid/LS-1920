package pt.isel.ls.utils.html;

import pt.isel.ls.utils.html.elements.Anchor;
import pt.isel.ls.utils.html.elements.Body;
import pt.isel.ls.utils.html.elements.Break;
import pt.isel.ls.utils.html.elements.Element;
import pt.isel.ls.utils.html.elements.H1;
import pt.isel.ls.utils.html.elements.H2;
import pt.isel.ls.utils.html.elements.Head;
import pt.isel.ls.utils.html.elements.Html;
import pt.isel.ls.utils.html.elements.ListElement;
import pt.isel.ls.utils.html.elements.Paragraph;
import pt.isel.ls.utils.html.elements.Table;
import pt.isel.ls.utils.html.elements.TableData;
import pt.isel.ls.utils.html.elements.TableDataElement;
import pt.isel.ls.utils.html.elements.TableHeader;
import pt.isel.ls.utils.html.elements.TableRow;
import pt.isel.ls.utils.html.elements.Title;
import pt.isel.ls.utils.html.elements.UnorderedList;
import pt.isel.ls.utils.html.elements.List;

public class HtmlDsl {
    public static Element html(Element... elements) {
        return new Html(elements);
    }

    public static Element head(Element... elements) {
        return new Head(elements);
    }

    public static Element title(String title) {
        return new Title(title);
    }

    public static Element body(Element... elements) {
        return new Body(elements);
    }

    public static Element h1(String text) {
        return new H1(text);
    }

    public static Element h2(String text) {
        return new H2(text);
    }

    public static Element p(String text) {
        return new Paragraph(text);
    }

    public static Element table(Element... elements) {
        return table(1, elements);
    }

    public static Element table(int borderSize, Element... elements) {
        return new Table(borderSize, elements);
    }

    public static Element tr(Element... elements) {
        return new TableRow(elements);
    }

    public static Element th(String header) {
        return new TableHeader(header);
    }

    public static Element td(String data) {
        return new TableData(data);
    }

    public static Element td(int data) {
        return new TableData(Integer.toString(data));
    }

    public static Element td(Element... elements) {
        return new TableDataElement(elements);
    }

    public static Element ul(Element... elements) {
        return new UnorderedList(elements);
    }

    public static Element li(String text) {
        return new List(text);
    }

    public static Element li(Element... elements) {
        return new ListElement(elements);
    }

    public static Element br() {
        return new Break();
    }

    public static Element a(String href, String text) {
        return new Anchor(href, text);
    }

}
