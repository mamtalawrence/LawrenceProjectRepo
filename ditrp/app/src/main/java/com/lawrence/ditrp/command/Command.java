package com.lawrence.ditrp.command;

import com.lawrence.ditrp.Enums.CommandType;

/**
 * Created by mamta.lawrence on 11/6/2017.
 */

public interface Command {

    /**
     * To execute the command
     *
     * @param commandType command type
     */
    public void execute(CommandType commandType);
}
