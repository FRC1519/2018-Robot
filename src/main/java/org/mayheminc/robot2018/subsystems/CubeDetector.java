package org.mayheminc.robot2018.subsystems;

import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.util.RangeFinder_GP2D120;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CubeDetector extends Subsystem {

	//IR Sensors
	private static final int kLeftIR = 0;
	private static final int kCenterIR = 1;
	private static final int kRightIR = 2;
	
	RangeFinder_GP2D120 m_left = new RangeFinder_GP2D120(RobotMap.LEFT_IR, kLeftIR);
	RangeFinder_GP2D120 m_center = new RangeFinder_GP2D120(RobotMap.CENTER_IR, kCenterIR);
	RangeFinder_GP2D120 m_right = new RangeFinder_GP2D120(RobotMap.RIGHT_IR, kRightIR);

    public void initDefaultCommand() { }
    
    public double getLeft()
    {
    	return m_left.getFilteredVoltage();
    }
    public double getCenter()
    {
    	return m_center.getFilteredVoltage();
    }
    public double getRight()
    {
    	return m_right.getFilteredVoltage();
    }
    
    public void periodic()
    {
    	m_left.periodic();
    	m_center.periodic();
    	m_right.periodic();
    }
    public void updateSmartDashboard()
    {
    	SmartDashboard.putNumber("IR L", getLeft());
    	SmartDashboard.putNumber("IR C", getCenter());
    	SmartDashboard.putNumber("IR R", getRight());
    	
    	SmartDashboard.putBoolean("Cube Death",  isDeathGrip());
    	SmartDashboard.putBoolean("Cube In",  isCubeSquare());
    }
    
    static final double OUTSIDE_CORNER_V = 1.0;
    static final double CENTER_CORNER_V = 1.3;
    static final double IR_TOLERANCE = 0.3;
    static final double CUBE_FULLY_IN = 1.3;
    
    public boolean isDeathGrip()
    {
    	double left = getLeft();
    	double center = getCenter();
    	double right = getRight();
    	
    	// if the left, center, and right are within the tolerance of the death grip...
//    	if( Math.abs(center - CENTER_CORNER_V) < IR_TOLERANCE &&
//    		Math.abs(left -OUTSIDE_CORNER_V ) < IR_TOLERANCE &&
//    		Math.abs(right - OUTSIDE_CORNER_V) < IR_TOLERANCE)
//    	{
//    		return true;
//    	}
    	
    	if( center > CENTER_CORNER_V &&
    			left < OUTSIDE_CORNER_V &&
    			right < OUTSIDE_CORNER_V )
    	{
    		return true;
    	}

    	return false;
    }
    
    public boolean isCubeSquare()
    {
    	double left = getLeft();
    	double center = getCenter();
    	double right = getRight();
    	
    	if( left > CUBE_FULLY_IN &&
    			//center > CUBE_FULLY_IN &&
    			right > CUBE_FULLY_IN)
    	{
    		return true;
    	}
    	return false;
    }
    
    public boolean isCubeHalfIn()
    {
    	if (getLeft() >= 0.7 || getRight() >= 0.7)
    	{
    		return true;
    	}
    	return false;
    }

}

