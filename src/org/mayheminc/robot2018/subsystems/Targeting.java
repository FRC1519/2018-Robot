package org.mayheminc.robot2018.subsystems;

import org.mayheminc.robot2018.Robot;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The Targeting subsystem gets data from the Network Tables (put there by the targeting application),
 * and returns 
 */
public class Targeting extends Subsystem {
	NetworkTableInstance table;

	enum NetworkTableData
	{
		FRAME_NUM(0),
		CUBE_X(1),
		BLUE_SWITCH_X(2),
		RED_SWITCH_Y(3),
		BLUE_SCALE_X(4),
		RED_SCALE_Y(5);
		
	    private final int id; // https://stackoverflow.com/questions/1067352/can-set-enum-start-value-in-java
		NetworkTableData(int id) { this.id = id; }
	    public int getValue() { return id; }

	}
	
	private static final Number[] DEFAULT_IMG_RESULTS = {0.0, 1001.0};
	private Number[] latestImgResults = {0.0, 1001.0};
	private int latestFrameNum = 0;
	private int latestCenterX = 1001;
	private double latestImageHeading = 0.0;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public Targeting()
	{
		table = NetworkTableInstance.getDefault();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
	public void periodic()
	{
		latestImgResults = table.getEntry("ImgResults").getNumberArray(DEFAULT_IMG_RESULTS);

		// check to see if these are new results
		if ( (int) latestImgResults[0] != latestFrameNum) {
			latestFrameNum = (int) latestImgResults[NetworkTableData.FRAME_NUM.getValue()];
			latestCenterX = (int) latestImgResults[NetworkTableData.CUBE_X.getValue()];
			latestImageHeading = Robot.drive.getHeadingForCapturedImage();
		}
	}
	
	public double getRobotHeading() {return latestImageHeading;}
	public int getCubeCenterOffset() {return latestCenterX;}
	public boolean isCubeVisible() 
	{
		return (getCubeCenterOffset() < 1000);
	}
}

