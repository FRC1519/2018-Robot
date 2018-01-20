package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Drive;

/**
 *
 */
public class ArcingTurn extends Command {
	 double m_distance;
	 double m_throttle;
	 int m_startingEncPos;
	 double m_steeringAmount;
	 
	 
	/**
	 * drive the robot forward a number of encoder counts
	 * TurnAmount should be positive for right arcing turn, negative for left arcing turn
	 */
    public ArcingTurn(double distance, double motorPower, double turnAmount ) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_distance = distance / Drive.DISTANCE_PER_PULSE;
    	
    	m_throttle = motorPower;
    	m_steeringAmount = turnAmount;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    			
    	if (m_steeringAmount >= 0) {
    		//we're arcing to the right
    		m_startingEncPos = Robot.drive.getRightEncoder();
    	} else {
    		//we're arcing to the left
    		m_startingEncPos = Robot.drive.getLeftEncoder();
    	}
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.speedRacerDrive(m_throttle, m_steeringAmount, false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double currentPos;
    	if (m_steeringAmount >=0) {
    		// we're arcing to the right
    		currentPos = Robot.drive.getRightEncoder();
    	} else {
    		// we're arcing to the left
    		currentPos = Robot.drive.getLeftEncoder();
    	}
    	double distance_travelled = Math.abs(currentPos - m_startingEncPos);
    	
		DriverStation.reportError(
				"DriveForward = " + distance_travelled + "   " + m_distance + "\n", false);
    	
        return (distance_travelled >= m_distance);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.speedRacerDrive(0, 0, false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drive.speedRacerDrive(0, 0, false);
   }
}
