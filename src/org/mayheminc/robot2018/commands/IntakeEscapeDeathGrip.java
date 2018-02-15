package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeEscapeDeathGrip extends Command {
	 static final double REVERSE_TIME_SEC = 0.4; 
	
	Timer m_Timer = new Timer();
	
    public IntakeEscapeDeathGrip() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// when this command starts, start a timer, reverse the intake motors, and take in the cube.
    	m_Timer = new Timer();
    	Robot.intake.Reverse(true);
    	Robot.intake.takeInCube();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// after some time we are done escaping the death grip
        return m_Timer.hasPeriodPassed(REVERSE_TIME_SEC);
    }

    // Called once after isFinished returns true
    protected void end() {
    	// unreverse the intake and stop the motors.
    	Robot.intake.Reverse(false);
    	Robot.intake.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intake.Reverse(false);
    	Robot.intake.stop();
    }
}
