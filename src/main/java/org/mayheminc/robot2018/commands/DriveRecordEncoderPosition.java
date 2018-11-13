package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveRecordEncoderPosition extends Command {

    public DriveRecordEncoderPosition() {  	
    }
    protected void initialize() {
    	Robot.drive.setUnwindStartPosition(Robot.drive.getRightEncoder());
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }
    protected void end() {
    }
    protected void interrupted() {
    }
}
