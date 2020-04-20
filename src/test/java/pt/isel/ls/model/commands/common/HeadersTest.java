package pt.isel.ls.model.commands.common;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HeadersTest {

    @Test
    public void correctHeadersTest() {
        //Arrange
        Headers headers = new Headers("accept:text/plain|file-name:rooms.html");
        String viewFormat = headers.getValue("accept");
        String file = headers.getValue("file-name");

        //Act
        boolean isCorrectViewFormat = viewFormat.equals("text/plain");
        boolean isCorrectFilename = file.equals("rooms.html");

        //Assert
        assertTrue(isCorrectViewFormat);
        assertTrue(isCorrectFilename);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongHeadersTestOnEmptyHeaderName() {
        Headers headers = new Headers(":text/plain|file-name:rooms.html");
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongHeadersTestOnEmptyValue() {
        Headers headers = new Headers("accept:|file-name:rooms.html");
    }
}