package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.autonomousroutines.DriveStraight.DistanceUnits;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoTargetRewind extends Command {

	private double m_destination;
	private double m_distance;
	DriveStraight unwindMotion;
	private double m_targetSpeed;
	
	private boolean m_hasStarted;
	
    public AutoTargetRewind(double arg_targetSpeed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_targetSpeed = arg_targetSpeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_destination = Robot.drive.getUnwindStartPosition();
    	m_distance = Robot.drive.getRightEncoder() - m_destination;
    	unwindMotion = new DriveStraight(m_targetSpeed, DistanceUnits.ENCODER_TICKS, m_distance);
    	unwindMotion.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(unwindMotion.isRunning()){
    		m_hasStarted = true;
    	}
    	
        return (m_hasStarted && !unwindMotion.isRunning());
    }

    // Called once after isFinished returns true
    protected void end() {
    	unwindMotion.cancel();
    	Robot.drive.stop();
    }
    

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	unwindMotion.cancel();
    	Robot.drive.stop();
    }
}
