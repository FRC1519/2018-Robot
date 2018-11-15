package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AIGatherCube extends Command {
	
	float DRIVE_FWD_SPEED = 0.25f;
	private float cubex;
	private float cubey;
	private float cubew;
	private float cubeh;
	private float cubep;
		
    public AIGatherCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    protected void initialize() { }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	cubex = Robot.targeting.locationCubeX();
    	cubey = Robot.targeting.locationCubeY();
    	cubew = Robot.targeting.locationCubeW();
    	cubeh = Robot.targeting.locationCubeH();
    	cubep = Robot.targeting.locationCubeP();
    	
    	SmartDashboard.putNumber("cubex", cubex);
    	SmartDashboard.putNumber("cubey", cubey);
    	SmartDashboard.putNumber("cubew", cubew);
    	SmartDashboard.putNumber("cubeh", cubeh);
    	SmartDashboard.putNumber("cubep", cubep);
    	
    	double RobotDriveX = (cubex-0.5);//*2; // convert from the YOLO 0 to 1.0 to -.5 to +.5
    	float AutoDriveFWDSpeed;//(float) (Math.abs(1-cubew)/3 + 0.2);//cubew;
    	// if the cube is far away, then go fast.
//    	if (cubew <= 0.3 && (cubex >= 0.8 || cubex <= 0.2))
//    	{
//    		AutoDriveFWDSpeed = 0.5f;
//    	}
//    	// if the cube is getting close, go a bit slower
//    	else if (cubew <= 0.5 && (cubex >= 0.8 || cubex <= 0.2))
//    	{
//    		AutoDriveFWDSpeed = 0.4f;
//    	}else if (cubew <= 0.9 && (cubex >= 0.8 || cubex <= 0.2))
//    		AutoDriveFWDSpeed = 0.2f;
//    	// if the cube is close, keep crawling
//    	else //if (cubew <= 0.72)
//    	{
//    		AutoDriveFWDSpeed = 0.1f;
//    	}
    	AutoDriveFWDSpeed = 0.2f;

    	//If the cube is half in backup
    	if( Robot.cubeDetector.isCubeHalfIn())
    	{
    		Robot.drive.speedRacerDrive(-0.2, 0, false);
    		
    	}
    	//if the cube is smashed against the intake.
//    	else if (cubex >= 0.7 && cubew >= 0.7 && cubey >= 0.2 && cubey <= 0.4) {
//    		Robot.drive.speedRacerDrive(-0.1, 0.5, false );	
//    	}
//    	else if (cubex <= 0.3 && cubew >= 0.7 && cubey >= 0.2 && cubey <= 0.4) {
//    		Robot.drive.speedRacerDrive(-0.1, -0.5, false);	
//    	}
    	//If we see a cube then drive to get it
    	else if (cubex != 1000) {
    		Robot.drive.speedRacerDrive(AutoDriveFWDSpeed, RobotDriveX, false);
    		SmartDashboard.putString("Cube???", "I see a cube!!!");
    		SmartDashboard.putNumber("RobotDriveX", RobotDriveX);
    	//	System.out.println("RobotDriveX" + RobotDriveX);
    	}
    	// if we can't see it, crawl!
    	else
    	{
    		SmartDashboard.putString("Cube???", "I do not see a cube");
    		Robot.drive.speedRacerDrive(0.2, 0, false);
    		
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.cubeDetector.isCubeSquare();
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
