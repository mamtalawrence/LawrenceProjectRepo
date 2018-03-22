package com.lawrence.ditrp.command;

import com.lawrence.ditrp.Enums.CommandType;

public class WebCallCommand {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand(CommandType commandType) {
        command.execute(commandType);
    }
}
