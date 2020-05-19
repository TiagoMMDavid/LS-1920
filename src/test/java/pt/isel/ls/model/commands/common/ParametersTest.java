package pt.isel.ls.model.commands.common;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ParametersTest {

    @Test
    public void correctParametersTest() {
        //Arrange
        Parameters params = new Parameters("name=LS3&location=Building+F+floor+-1&label=monitors&label=windows");
        Iterator<String> name = params.getValues("name").iterator();
        Iterator<String> location = params.getValues("location").iterator();
        Iterator<String> label = params.getValues("label").iterator();


        //Act
        boolean isCorrectName = name.next().equals("LS3");
        boolean isCorrectLocation = location.next().equals("Building F floor -1");
        boolean isCorrectLabel = label.next().equals("monitors") && label.next().equals("windows");

        //Assert
        assertTrue(isCorrectName);
        assertTrue(isCorrectLocation);
        assertTrue(isCorrectLabel);
    }

    @Test
    public void parametersOnEmptyValueTest() {
        Parameters params = new Parameters("name=&label=windows");
        assertNull(params.getString("name"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongParametersOnEmptyParamNameTest() {
        Parameters params = new Parameters("=value&name=LS3&label=windows");
    }
}