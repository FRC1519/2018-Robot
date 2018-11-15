package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

/**
 *
 */
public class DriveStraightIfCubeHalfIn extends DriveStraight {
public DriveStraightIfCubeHalfIn(double arg_targetSpeed, DistanceUnits units, double arg_distance) {
		super(arg_targetSpeed, units, arg_distance);
		// TODO Auto-generated constructor stub
	}

//private double targetSpeed;
//private enum units {INCHES}
//private double 
//    public DriveStraightIfCubeHalfIn(double arg_targetSpeed, DistanceUnits units, double arg_distance) {
//        // Use requires() here to declare subsystem dependencies
//        // eg. requires(chassis);
//        {
//        	targetSpeed = arg_targetSpeed;
//        }
//    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.cubeDetector.isCubeHalfIn() == true)
    	{
    		m_startPos = Robot.drive.getRightEncoder();
    	}
    }


//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//    }
//
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean isFinished() {
//        return false;
//    }
//
//    // Called once after isFinished returns true
//    protected void end() {
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//    }
}
