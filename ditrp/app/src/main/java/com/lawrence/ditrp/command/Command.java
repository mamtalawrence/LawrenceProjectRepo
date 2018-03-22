package com.lawrence.ditrp.command;

import com.lawrence.ditrp.Enums.CommandType;

public interface Command {

    /**
     * To execute the command
     *
     * @param commandType command type
     */
    void execute(CommandType commandType);
}
