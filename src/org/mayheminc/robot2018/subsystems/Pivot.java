package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.RobotMap;
//import org.mayheminc.util.MayhemTalonSRX;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;

/**
 * The Pivot lifts the intake to the upright position so the elevator can
 * take the cube.
 */
public class Pivot extends Subsystem {

	public static final int UPRIGHT_POSITION = 2800;//-2800; //JUST PLACEHOLDER!
	public static final int DOWNWARD_POSITION = 0;//0; //JUST A PLACEHOLDER!
	public static final int PIVOT_TOLERANCE = 20; // PLACEHOLDER!
	
	TalonSRX m_pivotmoter = new TalonSRX(RobotMap.PIVOT_TALON);
	int m_position;
	boolean m_manualMode = false;

	public Pivot()
	{
		super();
		
		m_pivotmoter.configNominalOutputForward(0.0,  0);
		m_pivotmoter.configNominalOutputReverse(0.0, 0);
		m_pivotmoter.configPeakOutputForward(0.3,  0);
		m_pivotmoter.configPeakOutputReverse(-0.3,  0);
		
		m_pivotmoter.config_kP(0, 3, 0);
		m_pivotmoter.config_kI(0, 0, 0);
		m_pivotmoter.config_kD(0, 0, 0);
		m_pivotmoter.config_kF(0, 0, 0);
		
		m_pivotmoter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		m_pivotmoter.setInverted(false);
		m_pivotmoter.setSensorPhase(true);
	}
    protected void initDefaultCommand() {
	}
    
    /**
     * Set the current position of the pivot to be zero.
     */
    public void zeroPivot() {	
    	m_pivotmoter.setSelectedSensorPosition(0, 0, 1000);
    }
    public void ManualMode()
    {
    	m_manualMode = true;
    }
    public void PidMode()
    {
    	m_manualMode = false;
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
    	m_position = position;
    }
    
    public boolean IsPivotInPosition()
    {
    	int diff = Math.abs(m_pivotmoter.getSelectedSensorPosition(0) - m_position);
    	return diff < PIVOT_TOLERANCE;
    }
    public void periodic()
    {
    	if( m_manualMode )
    	{
    		double power = Robot.oi.pivotArmPower();
    		m_pivotmoter.set(ControlMode.PercentOutput, power);
    	}
    }
    public void UpdateSmartDashboard()
    {
    	SmartDashboard.putNumber("Pivot Encoder Pos", m_pivotmoter.getSelectedSensorPosition(0));
    }
}
