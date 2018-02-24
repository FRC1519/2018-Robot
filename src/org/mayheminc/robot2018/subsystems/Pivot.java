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
	public static final int SPIT_POSITION = 1900;
	public static final int EXCHANGE_POSITION = 400;
	public static final int DOWNWARD_POSITION = 0;//0; //JUST A PLACEHOLDER!
	public static final int PIVOT_TOLERANCE = 20; // PLACEHOLDER!
	
	public static final int DOWN_TOLERANCE = 50;
	public static final double DOWN_MOTOR_POWER = 0.15;
	
	TalonSRX m_pivotmotor = new TalonSRX(RobotMap.PIVOT_TALON);
	int m_position;
	boolean m_manualMode = false;

	public Pivot()
	{
		super();
		
		m_pivotmotor.configNominalOutputForward(0.0,  0);
		m_pivotmotor.configNominalOutputReverse(0.0, 0);
		m_pivotmotor.configPeakOutputForward(0.8,  0);  // was 0.5 before bag day; for going up
		m_pivotmotor.configPeakOutputReverse(-0.6,  0);    // was -0.3 before bag day; for going down
		
		m_pivotmotor.config_kP(0, 3, 0);
		m_pivotmotor.config_kI(0, 0, 0);
		m_pivotmotor.config_kD(0, 0, 0);
		m_pivotmotor.config_kF(0, 0, 0);
		
		m_pivotmotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		m_pivotmotor.setInverted(false);
		m_pivotmotor.setSensorPhase(true);
	}
    protected void initDefaultCommand() {
	}
    
    /**
     * Set the current position of the pivot to be the uprighth position
     */
    public void zeroPivot() {	
    	m_pivotmotor.setSelectedSensorPosition(Pivot.UPRIGHT_POSITION, 0, 1000);
    }
    
    public void LockCurrentPosition()
    {
    	m_pivotmotor.set(ControlMode.Position, m_pivotmotor.getSelectedSensorPosition(0));
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
    	m_manualMode = false;
    	moveTo(UPRIGHT_POSITION );
    }
    
    /**
     * Set the pivot to the down position.
     */
    public void pivotDown() {
    	m_manualMode = false;
    	moveTo(DOWNWARD_POSITION);
    }
    
    public void moveTo(int position)
    {
    	m_pivotmotor.set(ControlMode.Position, position);
    	m_position = position;
    }
    
    public boolean IsPivotInPosition()
    {
    	int diff = Math.abs(m_pivotmotor.getSelectedSensorPosition(0) - m_position);
    	return diff < PIVOT_TOLERANCE;
    }
    
    public void periodic()
    {
    	double power = Robot.oi.pivotArmPower();
    	// if the joystick is commanding a movement, go to manual mode.,
    	if( power > 0.01 ||
    		power < -0.01 )
    	{
    		m_manualMode = true;
    	}
    	
    	if( m_manualMode )
    	{
    		m_pivotmotor.set(ControlMode.PercentOutput, power);
    	}
    	else // auto mode
    	{
    		switch(m_position)
    		{
	    		case DOWNWARD_POSITION:
	    			// if we wanted the DOWN position...
	    			int pos = m_pivotmotor.getSelectedSensorPosition(0);
	    			// and we are within a tolerance of being down...
	    			if( pos < DOWNWARD_POSITION + DOWN_TOLERANCE)
	    			{
	    				// set the motor to press down lightly.
	    				m_pivotmotor.set(ControlMode.PercentOutput,  DOWN_MOTOR_POWER);;
	    			}
	    			break;
    		}
    	}
    }
    public void UpdateSmartDashboard()
    {
    	SmartDashboard.putNumber("Pivot Encoder Pos", m_pivotmotor.getSelectedSensorPosition(0));
    }
}
