package pt.isel.ls.view;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.view.commandviews.DeleteBookingInRoomView;
import pt.isel.ls.view.commandviews.GetBookingByRoomAndBookingIdView;
import pt.isel.ls.view.commandviews.GetBookingsByRoomIdView;
import pt.isel.ls.view.commandviews.GetBookingsByUserIdView;
import pt.isel.ls.view.commandviews.GetHomeView;
import pt.isel.ls.view.commandviews.GetLabelByIdView;
import pt.isel.ls.view.commandviews.GetLabelsView;
import pt.isel.ls.view.commandviews.GetRoomByIdView;
import pt.isel.ls.view.commandviews.GetRoomsView;
import pt.isel.ls.view.commandviews.GetRoomsWithLabelView;
import pt.isel.ls.view.commandviews.GetTimeView;
import pt.isel.ls.view.commandviews.GetUserByIdView;
import pt.isel.ls.view.commandviews.GetUsersView;
import pt.isel.ls.view.commandviews.ListenView;
import pt.isel.ls.view.commandviews.OptionView;
import pt.isel.ls.view.commandviews.PostBookingInRoomView;
import pt.isel.ls.view.commandviews.PostLabelView;
import pt.isel.ls.view.commandviews.PostRoomView;
import pt.isel.ls.view.commandviews.PostUserView;
import pt.isel.ls.view.commandviews.PutBookingInRoomView;

import java.io.IOException;
import java.io.OutputStream;

public abstract class View {
    public static View getInstance(CommandResult commandResult) {
        if (commandResult == null) {
            // Result can only be null when the Servlet receives a GET request for a non existent command.
            // This never happens in the App
            return new NoRouteView();
        }

        if (!commandResult.hasResults()) {
            return new EmptyView();
        }

        switch (commandResult.getResultType()) {
            case DeleteBookingInRoom:
                return new DeleteBookingInRoomView(commandResult);
            case GetBookingByRoomAndBookingId:
                return new GetBookingByRoomAndBookingIdView(commandResult);
            case GetBookingsByRoomId:
                return new GetBookingsByRoomIdView(commandResult);
            case GetBookingsByUserId:
                return new GetBookingsByUserIdView(commandResult);
            case GetHome:
                return new GetHomeView();
            case GetLabelById:
                return new GetLabelByIdView(commandResult);
            case GetLabels:
                return new GetLabelsView(commandResult);
            case GetRoomById:
                return new GetRoomByIdView(commandResult);
            case GetRooms:
                return new GetRoomsView(commandResult);
            case GetRoomsWithLabel:
                return new GetRoomsWithLabelView(commandResult);
            case GetTime:
                return new GetTimeView(commandResult);
            case GetUserById:
                return new GetUserByIdView(commandResult);
            case GetUsers:
                return new GetUsersView(commandResult);
            case Listen:
                return new ListenView(commandResult);
            case Option:
                return new OptionView(commandResult);
            case PostBookingInRoom:
                return new PostBookingInRoomView(commandResult);
            case PostLabel:
                return new PostLabelView(commandResult);
            case PostRoom:
                return new PostRoomView(commandResult);
            case PostUser:
                return new PostUserView(commandResult);
            case PutBookingInRoom:
                return new PutBookingInRoomView(commandResult);
            default:
                return null;
        }
    }

    public void display(OutputStream out, String viewFormat) throws IOException {
        out.write((getDisplay(viewFormat)).getBytes());
    }

    public String getDisplay(String viewFormat) {
        String text = "";
        if (viewFormat == null || viewFormat.equals("text/plain")) {
            text = displayText();
        } else if (viewFormat.equals("text/html")) {
            text = displayHtml();
        }
        return text + '\n';
    }


    public abstract String displayText();


    public abstract String displayHtml();
}
