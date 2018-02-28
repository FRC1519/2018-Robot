package org.mayheminc.robot2018.subsystems;

import java.net.SocketException;
import java.util.List;

import org.mayheminc.util.*;
//import org.mayheminc.ObjectLocation;
import org.mayheminc.util.ObjectLocation.ObjectTypes;

//import org.mayheminc.robot2018.Robot;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The Targeting subsystem gets data from the Network Tables (put there by the targeting application),
 * and returns 
 */
public class Targeting extends Subsystem {
//	NetworkTableInstance table;

//	enum NetworkTableData
//	{
//		FRAME_NUM(0),
//		CUBE_X(1),
//		BLUE_SWITCH_X(2),
//		RED_SWITCH_Y(3),
//		BLUE_SCALE_X(4),
//		RED_SCALE_Y(5);
//		
//	    private final int id; // https://stackoverflow.com/questions/1067352/can-set-enum-start-value-in-java
//		NetworkTableData(int id) { this.id = id; }
//	    public int getValue() { return id; }
//
//	}
	
	private static final Number[] DEFAULT_IMG_RESULTS = {0.0, 1001.0};
	private Number[] latestImgResults = {0.0, 1001.0};
	private int latestFrameNum = 0;
	private int latestCenterX = 1001;
	private double latestImageHeading = 0.0;

	private double m_probability;
	
	ObjectListener listener;
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public Targeting()
	{
//		table = NetworkTableInstance.getDefault();
		
		try {
			listener = new ObjectListener();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listener.start();
	}

	public void initDefaultCommand() { }
	
	public void periodic()
	{
//		latestImgResults = table.getEntry("ImgResults").getNumberArray(DEFAULT_IMG_RESULTS);

		// check to see if these are new results
/*		if ( (int) latestImgResults[0] != latestFrameNum) {
			latestFrameNum = (int) latestImgResults[NetworkTableData.FRAME_NUM.getValue()];
			latestCenterX = (int) latestImgResults[NetworkTableData.CUBE_X.getValue()];
			latestImageHeading = Robot.drive.getHeadingForCapturedImage();
		}*/
		
		// get the list of objects from the listener
		List<ObjectLocation> objects = listener.getObjectList();
		
		if( objects != null )
		{
//			System.out.println("Targeting: Received non-null list");
			// loop through the objects.  This may be an empty list.
			for(ObjectLocation obj : objects)
			{
//				System.out.println("Target: " + obj.toString());

				// if there is a cube...
				if( obj.type == ObjectTypes.OBJ_CUBE)
				{
					m_probability = obj.probability;
					// get its center
					latestCenterX = (int) obj.x;
					latestFrameNum++;
					
					return;
				}
			}
		}
		else
		{
//			System.out.println("Targeting: objects is null");
		}
		
		latestCenterX = 1000; // no cube visible
	}
	
	public void updateSmartDashboard()
	{
		SmartDashboard.putNumber("Target X",  latestCenterX);
		SmartDashboard.putNumber("Target Frame", latestFrameNum);
		SmartDashboard.putNumber("Target Prob", m_probability);
	}
	
	public double getRobotHeading() {return latestImageHeading;}
	public int getCubeCenterOffset() {return latestCenterX;}
	public boolean isCubeVisible() 
	{
		return (getCubeCenterOffset() < 1000);
	}
}

