package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorRaiseIfTurretNotCentered extends Command {

    public ElevatorRaiseIfTurretNotCentered() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// If the turret is off-center, need to raise elevator before centering turret
    	if( Math.abs(Robot.turret.getPosition()) > Turret.POSITION_TOLERANCE )
    	{
    		Robot.elevator.setElevatorPosition(Elevator.PREPARE_FOR_HANDOFF_HEIGHT);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.elevator.IsElevatorAtPosition();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
