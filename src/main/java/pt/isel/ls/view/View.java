package pt.isel.ls.view;

import pt.isel.ls.view.commandviews.html.HttpResponseHtmlView;
import pt.isel.ls.view.commandviews.html.NoRouteHtmlView;
import pt.isel.ls.view.commandviews.plain.DeleteBookingInRoomPlainView;
import pt.isel.ls.view.commandviews.plain.GetBookingByRoomAndBookingIdPlainView;
import pt.isel.ls.view.commandviews.plain.GetBookingsByRoomIdPlainView;
import pt.isel.ls.view.commandviews.plain.GetBookingsByUserIdPlainView;
import pt.isel.ls.view.commandviews.plain.GetHomePlainView;
import pt.isel.ls.view.commandviews.plain.GetLabelByIdPlainView;
import pt.isel.ls.view.commandviews.plain.GetLabelsPlainView;
import pt.isel.ls.view.commandviews.plain.GetRoomByIdPlainView;
import pt.isel.ls.view.commandviews.plain.GetRoomsSearchPlainView;
import pt.isel.ls.view.commandviews.plain.GetRoomsPlainView;
import pt.isel.ls.view.commandviews.plain.GetRoomsWithLabelPlainView;
import pt.isel.ls.view.commandviews.plain.GetTimePlainView;
import pt.isel.ls.view.commandviews.plain.GetUserByIdPlainView;
import pt.isel.ls.view.commandviews.plain.GetUsersPlainView;
import pt.isel.ls.view.commandviews.plain.HttpResponsePlainView;
import pt.isel.ls.view.commandviews.plain.ListenPlainView;
import pt.isel.ls.view.commandviews.plain.NoRoutePlainView;
import pt.isel.ls.view.commandviews.plain.OptionPlainView;
import pt.isel.ls.view.commandviews.plain.PostBookingInRoomPlainView;
import pt.isel.ls.view.commandviews.plain.PostLabelPlainView;
import pt.isel.ls.view.commandviews.plain.PostRoomPlainView;
import pt.isel.ls.view.commandviews.plain.PostUserPlainView;
import pt.isel.ls.view.commandviews.plain.PutBookingInRoomPlainView;

import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.view.commandviews.html.DeleteBookingInRoomHtmlView;
import pt.isel.ls.view.commandviews.html.GetBookingByRoomAndBookingIdHtmlView;
import pt.isel.ls.view.commandviews.html.GetBookingsByRoomIdHtmlView;
import pt.isel.ls.view.commandviews.html.GetBookingsByUserIdHtmlView;
import pt.isel.ls.view.commandviews.html.GetHomeHtmlView;
import pt.isel.ls.view.commandviews.html.GetLabelByIdHtmlView;
import pt.isel.ls.view.commandviews.html.GetLabelsHtmlView;
import pt.isel.ls.view.commandviews.html.GetRoomByIdHtmlView;
import pt.isel.ls.view.commandviews.html.GetRoomsSearchHtmlView;
import pt.isel.ls.view.commandviews.html.GetRoomsHtmlView;
import pt.isel.ls.view.commandviews.html.GetRoomsWithLabelHtmlView;
import pt.isel.ls.view.commandviews.html.GetTimeHtmlView;
import pt.isel.ls.view.commandviews.html.GetUserByIdHtmlView;
import pt.isel.ls.view.commandviews.html.GetUsersHtmlView;
import pt.isel.ls.view.commandviews.html.ListenHtmlView;
import pt.isel.ls.view.commandviews.html.OptionHtmlView;
import pt.isel.ls.view.commandviews.html.PostBookingInRoomHtmlView;
import pt.isel.ls.view.commandviews.html.PostLabelHtmlView;
import pt.isel.ls.view.commandviews.html.PostRoomHtmlView;
import pt.isel.ls.view.commandviews.html.PostUserHtmlView;
import pt.isel.ls.view.commandviews.html.PutBookingInRoomHtmlView;

import java.io.IOException;
import java.io.OutputStream;

public abstract class View {

    // TODO: MAKE ROUTER FOR VIEWS | RETURN NULL WHEN NO REPRESENTATION (HTTP 406)
    public static View findView(CommandResult commandResult, String viewFormat) {
        if (viewFormat != null && viewFormat.equals("text/html")) {
            return findHtmlView(commandResult);
        }

        //Since the text/plain view is the default viewFormat we only need to specifically check the other view formats
        return findTextPlainView(commandResult);
    }

    private static View findTextPlainView(CommandResult commandResult) {
        if (!commandResult.hasResults()) {
            return new EmptyView();
        }

        switch (commandResult.getResultType()) {
            case DeleteBookingInRoom:
                return new DeleteBookingInRoomPlainView(commandResult);
            case GetBookingByRoomAndBookingId:
                return new GetBookingByRoomAndBookingIdPlainView(commandResult);
            case GetBookingsByRoomId:
                return new GetBookingsByRoomIdPlainView(commandResult);
            case GetBookingsByUserId:
                return new GetBookingsByUserIdPlainView(commandResult);
            case GetHome:
                return new GetHomePlainView();
            case GetLabelById:
                return new GetLabelByIdPlainView(commandResult);
            case GetLabels:
                return new GetLabelsPlainView(commandResult);
            case GetRoomById:
                return new GetRoomByIdPlainView(commandResult);
            case GetRooms:
                return new GetRoomsPlainView(commandResult);
            case GetRoomsWithLabel:
                return new GetRoomsWithLabelPlainView(commandResult);
            case GetRoomsSearch:
                return new GetRoomsSearchPlainView(commandResult);
            case GetTime:
                return new GetTimePlainView(commandResult);
            case GetUserById:
                return new GetUserByIdPlainView(commandResult);
            case GetUsers:
                return new GetUsersPlainView(commandResult);
            case Listen:
                return new ListenPlainView(commandResult);
            case Option:
                return new OptionPlainView(commandResult);
            case PostBookingInRoom:
                return new PostBookingInRoomPlainView(commandResult);
            case PostLabel:
                return new PostLabelPlainView(commandResult);
            case PostRoom:
                return new PostRoomPlainView(commandResult);
            case PostUser:
                return new PostUserPlainView(commandResult);
            case PutBookingInRoom:
                return new PutBookingInRoomPlainView(commandResult);
            case HttpResponse:
                return new HttpResponsePlainView(commandResult);
            default:
                return new NoRoutePlainView();
        }
    }

    private static View findHtmlView(CommandResult commandResult) {
        if (!commandResult.hasResults()) {
            return new EmptyView();
        }

        switch (commandResult.getResultType()) {
            case DeleteBookingInRoom:
                return new DeleteBookingInRoomHtmlView(commandResult);
            case GetBookingByRoomAndBookingId:
                return new GetBookingByRoomAndBookingIdHtmlView(commandResult);
            case GetBookingsByRoomId:
                return new GetBookingsByRoomIdHtmlView(commandResult);
            case GetBookingsByUserId:
                return new GetBookingsByUserIdHtmlView(commandResult);
            case GetHome:
                return new GetHomeHtmlView();
            case GetLabelById:
                return new GetLabelByIdHtmlView(commandResult);
            case GetLabels:
                return new GetLabelsHtmlView(commandResult);
            case GetRoomById:
                return new GetRoomByIdHtmlView(commandResult);
            case GetRooms:
                return new GetRoomsHtmlView(commandResult);
            case GetRoomsWithLabel:
                return new GetRoomsWithLabelHtmlView(commandResult);
            case GetRoomsSearch:
                return new GetRoomsSearchHtmlView(commandResult);
            case GetTime:
                return new GetTimeHtmlView(commandResult);
            case GetUserById:
                return new GetUserByIdHtmlView(commandResult);
            case GetUsers:
                return new GetUsersHtmlView(commandResult);
            case Listen:
                return new ListenHtmlView(commandResult);
            case Option:
                return new OptionHtmlView(commandResult);
            case PostBookingInRoom:
                return new PostBookingInRoomHtmlView(commandResult);
            case PostLabel:
                return new PostLabelHtmlView(commandResult);
            case PostRoom:
                return new PostRoomHtmlView(commandResult);
            case PostUser:
                return new PostUserHtmlView(commandResult);
            case PutBookingInRoom:
                return new PutBookingInRoomHtmlView(commandResult);
            case HttpResponse:
                return new HttpResponseHtmlView(commandResult);
            default:
                return new NoRouteHtmlView();
        }
    }

    public void render(OutputStream out) throws IOException {
        out.write((getDisplay()).getBytes());
    }

    public String getDisplay() {
        return display() + '\n';
    }

    protected abstract String display();
}
