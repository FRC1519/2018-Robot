package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.util.MayhemTalonSRX;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;

/**
 * The Pivot lifts the intake to the upright position so the elevator can
 * take the cube.
 */
public class Pivot extends Subsystem {

	public static final int UPRIGHT_POSITION = 2000; //JUST PLACEHOLDER!
	public static final int DOWNWARD_POSITION = 0; //JUST A PLACEHOLDER!
	
	MayhemTalonSRX m_pivotmoter = new MayhemTalonSRX(RobotMap.PIVOT_TALON);

	public Pivot()
	{
		super();
		
		m_pivotmoter.config_kP(0, 1, 0);
		m_pivotmoter.config_kI(0, 0, 0);
		m_pivotmoter.config_kD(0, 0, 0);
		m_pivotmoter.config_kF(0, 0, 0);
		
		m_pivotmoter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
	}
    protected void initDefaultCommand() {
	}
    
    /**
     * Set the current position of the pivot to be zero.
     */
    public void zeroPivot() {	
    	m_pivotmoter.setPosition(0);	
    }
    
    /**
     * Set the pivot to the up position.
     */
    public void pivotUp() {
    	moveTo(UPRIGHT_POSITION );
    }
    
    /**
     * Set the pivot to the down position.
     */
    public void pivotDown() {
    	moveTo(DOWNWARD_POSITION);
    }
    
    void moveTo(int position)
    {
    	m_pivotmoter.set(ControlMode.Position, position);
    }
}
