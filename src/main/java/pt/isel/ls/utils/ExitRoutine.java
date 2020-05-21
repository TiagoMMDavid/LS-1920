package pt.isel.ls.utils;

import pt.isel.ls.model.commands.common.exceptions.ExitException;

@FunctionalInterface
public interface ExitRoutine {

    void close() throws ExitException;
}
