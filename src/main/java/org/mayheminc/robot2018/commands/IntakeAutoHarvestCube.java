package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Suck in the cubes.
 * If the cube is in death grip, reverse the intake directions to escape.
 * Stop when the cube is square.
 */
public class IntakeAutoHarvestCube extends TimedCommand {

	// by default, take up to 15 seconds to harvest a cube
    public IntakeAutoHarvestCube() {
    	super(15.0);
    }
    public IntakeAutoHarvestCube(double timeout) {
    	super(timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.intake.takeInCube();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.cubeDetector.isDeathGrip())
    	{
    		Robot.intake.reverseLeft(true);
    	}
    	else
    	{
    		Robot.intake.reverseLeft(false);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return super.isFinished() || Robot.cubeDetector.isCubeSquare();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intake.stop();
    }
}
