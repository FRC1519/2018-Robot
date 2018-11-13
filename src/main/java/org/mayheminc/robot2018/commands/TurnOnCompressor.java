package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class TurnOnCompressor extends InstantCommand {

    public TurnOnCompressor() {
    	super();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.compressor.start();
    }
}
