package pt.isel.ls;

import org.junit.Test;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.model.paths.PathTemplate;

import static org.junit.Assert.assertTrue;

public class PathTest {


    @Test
    public void correctPathTest(){
        //Arrange
        PathTemplate roomsTemplate = new PathTemplate("/rooms/{rid}");
        PathTemplate bookingsTemplate = new PathTemplate("/bookings/{bid}");
        Path roomsPath = new Path("/rooms/123");
        Path bookingsPath = new Path("/bookings/abc");

        //Act
        boolean isRoomsTemplate = roomsTemplate.isTemplateOf(roomsPath);
        boolean isBookingsTemplate = bookingsTemplate.isTemplateOf(bookingsPath);

        //Assert
        assertTrue(isRoomsTemplate);
        assertTrue(isBookingsTemplate);
    }

    @Test
    public void wrongPathTest(){

        //Arrange
        PathTemplate template = new PathTemplate("/rooms/{rid}");
        Path path1 = new Path("/rooms/");
        Path path2 = new Path("/booking/abc");
        Path path3 = new Path("/foo");

        //Act
        boolean isTemplate1 = template.isTemplateOf(path1);
        boolean isTemplate2 = template.isTemplateOf(path2);
        boolean isTemplate3 = template.isTemplateOf(path3);

        //Assert
        assertTrue(!isTemplate1);
        assertTrue(!isTemplate2);
        assertTrue(!isTemplate3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidPathTemplateFormatTest(){
        PathTemplate template = new PathTemplate("///");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyPathTemplateFormatTest(){
        PathTemplate template = new PathTemplate("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyPathTest(){
        Path path = new Path("");
    }
}
