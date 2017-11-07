package com.lawrence.ditrp.command;

/**
 * Created by mamta.lawrence on 11/6/2017.
 */

public class WebCallCommand {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand() {
        command.execute();
    }
}
