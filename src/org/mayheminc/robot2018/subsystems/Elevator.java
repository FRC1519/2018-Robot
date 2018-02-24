package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.util.MayhemTalonSRX;
import org.mayheminc.util.PidTunerObject;

/**
 *
 */
public class Elevator extends Subsystem implements PidTunerObject {

	public static final int PICK_UP_CUBE = 0;
	public static final int SWITCH_LOW = 100;
	public static final int SWITCH_HIGH = 200;
	public static final int SCALE_LOW = 300;
	public static final int SCALE_MID = 400;
	public static final int SCLAE_HIGH = 500;
	public static final int CEILING = 600;
	
	boolean m_SafetyOn = true;

    final int ELEVATOR_FLOOR = 0;
    final int ELEVATOR_CEILING = 1000;
    final int POSITION_TOLERANCE = 20;
    
	int m_motorSpeed;
	boolean m_manualMode;
	int m_autoSetpoint;
	
	MayhemTalonSRX m_motor = new MayhemTalonSRX(RobotMap.ELEVATOR_TALON);
	
	public Elevator()
	{
		super();
		
		// TODO: need to tune the PIDF parameters
		m_motor.config_kP(0, 1, 0);
		m_motor.config_kI(0, 0, 0);
		m_motor.config_kD(0, 0, 0);
		m_motor.config_kF(0, 0, 0);
	}
	
    public void initDefaultCommand() {}
    
    public void setElevatorPosition(int pos)
    {
    	m_manualMode = false; // set to auto mode
    	m_SafetyOn = true; // turn on the safety checks
    	m_autoSetpoint = pos; // get the desired setpoint
    	m_motor.set(ControlMode.Position, m_autoSetpoint); // tell the motor to get to the setpoint
    }
    
    /**
     * If the elevator is within the tolerance, then return true.
     * @return
     */
    public boolean IsElevatorAtPosition()
    {
    	// if manual mode is enabled, always return true.
    	if( m_manualMode ) return true;
    	
    	int position = m_motor.getSelectedSensorPosition(0);
    	int diff = Math.abs(position - m_autoSetpoint);
    	return (diff < POSITION_TOLERANCE);
    }
    
    /**
     * Set the elevator speed to positive (up) or negative (down) or stop (zero).
     * @param x
     */
    public void SetSpeed(int x)
    {
    	m_manualMode = true;
    	m_motorSpeed = x;
    }
    
    /**
     * Set the current position of the elevator to the zero point.
     */
    public void Zero()
    {
    	m_motor.setSelectedSensorPosition(0,  0,  0);
    }
    
    /**
     * Turn the safety bounds off.  Do this if we skip the belt and need to position the elvevator.
     */
    public void TurnSafetyOff()
    {
    	m_SafetyOn = false;
    }
    
    /**
     * The elevator has set positions defined in an enum at the top.
     * In auto-mode, buttons set the desired position and the periodic function does nothing.
     * In manual mode, the motor is set to velocity mode.  There is a safety check (that can
     * be turned off) to limit the elevator to the top and bottom.
     */
    public void Periodic()
    {
    	// if this is manual mode...
    	if( m_manualMode )
    	{
    		// if the safety is on...
    		if( m_SafetyOn )
        	{
        		// check the encoder limits.
        		int position = m_motor.getSelectedSensorPosition(0);
        		
        		// if the position is above the celing or below the floor, turn off the motor.
        		if( position < ELEVATOR_FLOOR ||
        			position > ELEVATOR_CEILING)
        		{
        			m_motorSpeed = 0;
        		}
        	}
    		
    		m_motor.set(ControlMode.Velocity,  m_motorSpeed);
    	}
    	else // this is auto mode
    	{
    		// the motors are set to the position in the auto calls.
    	}
    }
    
    public void updateSmartDashboard()
    {
    	SmartDashboard.putNumber("Elevator Pos", m_motor.getPosition());
    }

	@Override
	public double getP() {
		return m_motor.getP();
	}

	@Override
	public double getI() {
		return m_motor.getI();
	}

	@Override
	public double getD() {
		return m_motor.getD();
	}

	@Override
	public void setP(double d) {
		m_motor.config_kP(0, d, 0);
	}

	@Override
	public void setI(double d) {
		m_motor.config_kI(0, d, 0);
	}

	@Override
	public void setD(double d) {
		m_motor.config_kD(0, d, 0);
	}
}

