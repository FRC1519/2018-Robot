package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;


   
/**
 *
 */
public class DriveStraight extends Command {
	double m_targetPower;
	double m_desiredDisplacement;
	public int m_startPos = 0;
	double m_timeout;
	Timer timer = new Timer();
	
	private static double DEFAULT_TIMEOUT = 100.0;
	 
	public static enum DistanceUnits { ENCODER_TICKS, INCHES }
	/**
	 * 
	 * @param arg_targetPower +/- motor power [-1.0, +1.0]
	 * @param arg_distance Distance in encoder counts
	 */
    public DriveStraight(double arg_targetSpeed, DistanceUnits units, double arg_distance) {
    	this(arg_targetSpeed, units, arg_distance, DEFAULT_TIMEOUT);
    }
    public DriveStraight(double arg_targetSpeed, DistanceUnits units, double arg_distance, double timeLimit) {
    	if (units == DistanceUnits.INCHES) {
    		arg_distance = arg_distance / Drive.DISTANCE_PER_PULSE;
    	}
    	m_timeout = timeLimit;
    	m_targetPower = arg_targetSpeed;
    	m_desiredDisplacement = Math.abs(arg_distance);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_startPos = Robot.drive.getRightEncoder();
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.speedRacerDrive(m_targetPower, 0, false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	int displacement = Robot.drive.getRightEncoder() - m_startPos;
    	displacement = Math.abs(displacement);
        return (displacement >= m_desiredDisplacement) || (timer.get() > m_timeout);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.stop();    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drive.stop();
    	
    }
}
