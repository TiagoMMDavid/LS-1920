package pt.isel.ls.model.commands.common;

import pt.isel.ls.utils.ExitRoutine;

public interface CommandResult {

    /**
     * @return Whether there's results or not
     */
    boolean hasResults();

    /**
     * Enum containing every type of Result. New Result types must be added here.
     */
    enum ResultType {
        DeleteBookingInRoom,
        GetBookingByRoomAndBookingId,
        GetBookingsByRoomId,
        GetBookingsByUserId,
        GetBookingsCreate,
        GetHome,
        GetLabelById,
        GetLabelsCreate,
        GetLabels,
        GetRoomById,
        GetRoomsCreate,
        GetRooms,
        GetRoomsSearch,
        GetRoomsWithLabel,
        GetTime,
        GetUserById,
        GetUsersCreate,
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

    /**
     * @return the ExitRoutine that must be executed when the App is terminating its execution
     */
    default ExitRoutine getExitRoutine() {
        return null;
    }

    ResultType getResultType();
}
