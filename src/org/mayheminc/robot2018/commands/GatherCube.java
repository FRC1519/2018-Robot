package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.mayheminc.robot2018.commands.IntakeEscapeDeathGripLeft;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GatherCube extends Command {
	
	float DRIVE_FWD_SPEED = 0.25f;
	private float cubex;
	private float cubey;
	private float cubew;
	private float cubeh;
	private float cubep;
	private float AutoDriveFWDSpeed;
	
    public GatherCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	while (true) {
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
    	
    	double RobotDriveX = (cubex-0.5);//*2;
    	float AutoDriveFWDSpeed;//(float) (Math.abs(1-cubew)/3 + 0.2);//cubew;
    	if (cubew <= 0.3)
    	{
    		AutoDriveFWDSpeed = 0.7f;
    	}
    	else if (cubew <= 0.66)
    	{
    		AutoDriveFWDSpeed = 0.4f;
    	}
    	else //if (cubew <= 0.72)
    	{
    		AutoDriveFWDSpeed = 0.2f;
    	}
    	//double robotdepthw = ((1-cubew)-2);
//    	if (robotdepthw < 0.2 ) {
//    		robotdepthw = 0.2;
//    	}
//    	org.mayheminc.robot2018.commands.AllRollersOut();
    	
    	if (cubex != 1000) {
    		Robot.drive.speedRacerDrive(AutoDriveFWDSpeed, RobotDriveX, false);
    		SmartDashboard.putString("Cube???", "I see a cube!!!");
    		SmartDashboard.putNumber("RobotDriveX", RobotDriveX);
    	}
    	else //if (cubex == 1000) 
    	{
    		SmartDashboard.putString("Cube???", "I do not see a cube");
    		Robot.drive.speedRacerDrive(0, 0, false);
    		
    	}
    	
    	if (Robot.cubeDetector.isDeathGrip() == true) {
//    		IntakeEscapeDeathGripLeft();
    	}
//    	else if (Robot.cubeDetector.isCubeSquare() == true) {
//    		break;
//    	}
//    	}
//    	isFinished();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.cubeDetector.isCubeSquare() ||
        		Robot.cubeDetector.isDeathGrip();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.speedRacerDrive(0, 0, false);
    	Robot.intake.stop();
    }
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drive.speedRacerDrive(0, 0, false);
    	Robot.intake.stop();
    }
}
