package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class IntakeOut extends Command {

    public IntakeOut() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.intake.spitOutCube();
    }
    protected boolean isFinished() {return false;}
    protected void end()
    {
    	Robot.intake.stop();
    }
    
    protected void interrupt()
    {
    	Robot.intake.stop();
    }

}
