package pt.isel.ls.model.commands.common;

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

    ResultType getResultType();
}
