package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.util.MayhemTalonSRX;
//import org.mayheminc.util.MayhemTalonSRX;
import org.mayheminc.util.PidTunerObject;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;

/**
 * The Pivot lifts the intake to the upright position so the elevator can
 * take the cube.
 */
public class Pivot extends Subsystem implements PidTunerObject {

	public static final int UPRIGHT_POSITION = 2800; //JUST PLACEHOLDER!
	public static final int SPIT_POSITION = 1900;
	public static final int EXCHANGE_POSITION = 400;
	public static final int DOWNWARD_POSITION = 0;
	public static final int PIVOT_TOLERANCE = 20; // PLACEHOLDER!
	
	public static final int DOWN_TOLERANCE = 50;
	public static final double DOWN_MOTOR_POWER = 0.15;
	public static final double ZERO_MAX_CURRENT = 0.2;
	
	MayhemTalonSRX m_pivotmotor = new MayhemTalonSRX(RobotMap.PIVOT_TALON);
	int m_position;
	boolean m_manualMode = false;
	boolean m_zero;
	
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
//    	m_pivotmotor.setSelectedSensorPosition(Pivot.UPRIGHT_POSITION, 0, 1000);
    	m_pivotmotor.set(ControlMode.PercentOutput, 0.1);
    	m_zero = true;
    	
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
    	
    	// if we are zeroing the pivot...
    	if( m_zero )
    	{
    		// if the current goes up...
    		if( m_pivotmotor.getOutputCurrent() > ZERO_MAX_CURRENT)
    		{
    			// we found the hard stop.  Set the encoder position.
    			m_pivotmotor.setSelectedSensorPosition(UPRIGHT_POSITION, 0, 0);
    			// stop zeroing.
    			m_zero = false;
    			// stop the motor
    			m_pivotmotor.set(ControlMode.Position, UPRIGHT_POSITION);
    		}
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
    
    ////////////////////////////////////////////////////////
    // PidTunerObject
    ////////////////////////////////////////////////////////
	@Override
	public double getP() { return m_pivotmotor.getP();}
	@Override
	public double getI() { return m_pivotmotor.getI();}
	@Override
	public double getD() { return m_pivotmotor.getD();}
	@Override
	public double getF() { return m_pivotmotor.getF();}
	@Override
	public void setP(double d) {m_pivotmotor.config_kP(0,  d,  0); }
	@Override
	public void setI(double d) {m_pivotmotor.config_kI(0,  d,  0); }
	@Override
	public void setD(double d) {m_pivotmotor.config_kD(0,  d,  0); }
	@Override
	public void setF(double d) {m_pivotmotor.config_kF(0,  d,  0); }
}
