package pt.isel.ls.utils;

import pt.isel.ls.model.commands.common.exceptions.ExitException;

/**
 * FunctionalInterface to be used by CommandHandlers that require an exit routine
 */
@FunctionalInterface
public interface ExitRoutine {

    void close() throws ExitException;
}
