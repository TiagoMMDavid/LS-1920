package pt.isel.ls.model.commands.common;

public interface CommandResult {

    boolean hasResults();

    enum ResultType {
        DeleteBookingInRoom,
        GetBookingByRoomAndBookingId,
        GetBookingsByRoomId,
        GetBookingsByUserId,
        GetLabelById,
        GetLabels,
        GetRoomById,
        GetRooms,
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
        PutBookingInRoom
    }

    ResultType getResultType();
}
