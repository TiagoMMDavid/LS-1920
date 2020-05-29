package pt.isel.ls.model.commands.common;

import pt.isel.ls.utils.ExitRoutine;

public interface CommandResult {

    boolean hasResults();

    enum ResultType {
        DeleteBookingInRoom,
        GetBookingByRoomAndBookingId,
        GetBookingsByRoomId,
        GetBookingsByUserId,
        GetHome,
        GetLabelById,
        GetLabels,
        GetRoomById,
        GetRoomsCreate,
        GetRooms,
        GetRoomsSearch,
        GetRoomsWithLabel,
        GetTime,
        GetUserById,
        GetUsers,
        Listen,
        Option,
        PostBookingInRoom,
        PostLabel,
        PostRoom,
        PostUser,
        PutBookingInRoom,
        HttpResponse
    }

    default ExitRoutine getExitRoutine() {
        return null;
    }

    ResultType getResultType();
}
