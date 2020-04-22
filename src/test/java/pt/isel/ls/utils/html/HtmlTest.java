package pt.isel.ls.utils.html;

import org.junit.Test;
import pt.isel.ls.utils.html.elements.Element;

import static pt.isel.ls.utils.html.HtmlDsl.html;
import static pt.isel.ls.utils.html.HtmlDsl.body;
import static pt.isel.ls.utils.html.HtmlDsl.h1;
import static pt.isel.ls.utils.html.HtmlDsl.head;
import static pt.isel.ls.utils.html.HtmlDsl.table;
import static pt.isel.ls.utils.html.HtmlDsl.td;
import static pt.isel.ls.utils.html.HtmlDsl.th;
import static pt.isel.ls.utils.html.HtmlDsl.title;
import static pt.isel.ls.utils.html.HtmlDsl.p;

import static org.junit.Assert.assertEquals;
import static pt.isel.ls.utils.html.HtmlDsl.tr;

public class HtmlTest {

    @Test
    public void createBasicHtmlTest() {
        String expected =
                "<!DOCTYPE html>\n"
                + "<html>\n"
                +   "\t<head>\n"
                +       "\t\t<title>\n"
                +           "\t\t\tThe title\n"
                +       "\t\t</title>\n"
                +   "\t</head>\n"
                +   "\t<body>\n"
                +       "\t\t<h1>\n"
                +           "\t\t\tThis is a section\n"
                +       "\t\t</h1>\n"
                +       "\t\t<p>\n"
                +           "\t\t\tThis is a paragraph\n"
                +       "\t\t</p>\n"
                +   "\t</body>\n"
                + "</html>";

        Element actual =
                html(
                        head(
                                title("The title")
                        ),
                        body(
                                h1("This is a section"),
                                p("This is a paragraph")
                        )
                );

        assertEquals(expected, actual.toString());
    }

    @Test
    public void createHtmlTableTest() {
        String expected =
                "<!DOCTYPE html>\n"
                + "<html>\n"
                + "\t<head>\n"
                + "\t\t<title>\n"
                + "\t\t\tThe title\n"
                + "\t\t</title>\n"
                + "\t</head>\n"
                + "\t<body>\n"
                + "\t\t<h1>\n"
                + "\t\t\tTable:\n"
                + "\t\t</h1>\n"
                + "\t\t<table border=1>\n"
                + "\t\t\t<tr>\n"
                + "\t\t\t\t<th>\n"
                + "\t\t\t\t\tID\n"
                + "\t\t\t\t</th>\n"
                + "\t\t\t\t<th>\n"
                + "\t\t\t\t\tName\n"
                + "\t\t\t\t</th>\n"
                + "\t\t\t</tr>\n"
                + "\t\t\t<tr>\n"
                + "\t\t\t\t<td>\n"
                + "\t\t\t\t\t1\n"
                + "\t\t\t\t</td>\n"
                + "\t\t\t\t<td>\n"
                + "\t\t\t\t\tMeeting Room\n"
                + "\t\t\t\t</td>\n"
                + "\t\t\t</tr>\n"
                + "\t\t\t<tr>\n"
                + "\t\t\t\t<td>\n"
                + "\t\t\t\t\t2\n"
                + "\t\t\t\t</td>\n"
                + "\t\t\t\t<td>\n"
                + "\t\t\t\t\tLunch Room\n"
                + "\t\t\t\t</td>\n"
                + "\t\t\t</tr>\n"
                + "\t\t</table>\n"
                + "\t</body>\n"
                + "</html>";

        Element actual =
                html(
                        head(
                                title("The title")
                        ),
                        body(
                                h1("Table:"),
                                table(
                                        tr(
                                              th("ID"),
                                              th("Name")
                                        ),
                                        tr(
                                              td("1"),
                                              td("Meeting Room")
                                        ),
                                        tr(
                                            td("2"),
                                            td("Lunch Room")
                                        )
                                )
                        )
                );

        assertEquals(expected, actual.toString());
    }
}