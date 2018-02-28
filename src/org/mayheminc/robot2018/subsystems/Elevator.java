package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.util.MayhemTalonSRX;
import org.mayheminc.util.PidTunerObject;

/**
 *
 */
public class Elevator extends Subsystem implements PidTunerObject {

	public static final int PICK_UP_CUBE = 0;
	public static final int SWITCH_LOW = 500;
	public static final int SWITCH_HIGH = 2000;
	public static final int SCALE_LOW = 4000;
	public static final int SCALE_MID = 18700;
	public static final int SCALE_HIGH = 6000;
	public static final int CEILING = 24100; // hihg scale is 24100
	
	boolean m_SafetyOn = true;

//    final int ELEVATOR_FLOOR = 0;
//    final int ELEVATOR_CEILING = 1000;
    final int POSITION_TOLERANCE = 500;
    
	int m_motorSpeed;
	boolean m_manualMode = true;
	int m_autoSetpoint;
	
	MayhemTalonSRX m_motor = new MayhemTalonSRX(RobotMap.ELEVATOR_TALON);
	
	public Elevator()
	{
//		super();

		m_motor.configNominalOutputForward(0.0,  0);
		m_motor.configNominalOutputReverse(0.0, 0);
		m_motor.configPeakOutputForward(1.0,  0);
		m_motor.configPeakOutputReverse(-1.0,  0);

		// TODO: need to tune the PIDF parameters
		m_motor.config_kP(0, 0.3, 0);
		m_motor.config_kI(0, 0.0, 0);
		m_motor.config_kD(0, 0.01, 0);
		m_motor.config_kF(0, 0.0, 0);
		
		m_motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		// RJD !@!#@!#12
		
		m_motor.setInverted(true);
		m_motor.setSensorPhase(true);
		
		m_motor.configClosedloopRamp(0.25, 0);
		m_motor.configOpenloopRamp(0.25,  0);
		
		m_motor.setSelectedSensorPosition(m_motor.getSelectedSensorPosition(0), 0, 0);
	}
	
    public void initDefaultCommand() {}
    
    public void setElevatorPosition(int pos)
    {
//    	System.out.println("Elevator Auto Pos: " + pos);
    	
    	m_manualMode = false; // set to auto mode
    	m_SafetyOn = true; // turn on the safety checks
    	m_autoSetpoint = pos; // get the desired setpoint
    	m_motor.set(ControlMode.Position, m_autoSetpoint); // tell the motor to get to the setpoint
    	m_motor.configMotionAcceleration(1000,  0);
    }
    
    /**
     * If the elevator is within the tolerance, then return true.
     * @return
     */
    public boolean IsElevatorAtPosition()
    {
//    	System.out.println("IsElevatorAtPosition Manual " + m_manualMode);
    	// if manual mode is enabled, always return true.
    	if( m_manualMode ) return true;
    	
    	int position = m_motor.getSelectedSensorPosition(0);
    	int diff = Math.abs(position - m_autoSetpoint);
    	
//    	System.out.println("IsElevatorAtPosition Diff " + diff);
    	
    	return (diff < POSITION_TOLERANCE);
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
     * The elevator has set positions defined at the top.
     * In auto-mode, buttons set the desired position and the periodic function does nothing.
     * In manual mode, the motor is set to velocity mode.  There is a safety check (that can
     * be turned off) to limit the elevator to the top and bottom.
     */
    public void periodic()
    {
    	double power = Robot.oi.getElevatorPower();

    	if( power > 0.01 ||
        	power < -0.01 )
    	{
    		m_manualMode = true;
    	}
    	else
    	{
	    	if( m_manualMode)
	    	{
	//    		m_manualMode = false;
	//    		m_autoSetpoint = m_motor.getSelectedSensorPosition(0);
	    	}
    	}
    	
//    	System.out.println("Elevator Manual Mode: " + m_manualMode);
//    	System.out.println("Elevator Safety Mode: " + m_SafetyOn);
    	
    	// if this is manual mode...
    	if( m_manualMode )
    	{
    		// if the safety is on...
    		if( m_SafetyOn )
        	{
        		// check the encoder limits.
        		int position = m_motor.getSelectedSensorPosition(0);

        		// if the position is above the ceiling or below the floor, turn off the motor.
        		if( position < PICK_UP_CUBE ||
        			position > CEILING )
        		{
        			m_motorSpeed = 0;
        		}
        	}
    		
    		m_motor.set(ControlMode.PercentOutput,  power);
    	}
    	else // this is auto mode
    	{
//    		System.out.println("Elevator Auto: " + m_autoSetpoint);
        	
    		// the motors are set to the position in the auto calls.
    		m_motor.set(ControlMode.Position, m_autoSetpoint);
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
	public double getF() {
		return m_motor.getF();
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
	@Override
	public void setF(double d) {
		m_motor.config_kF(0, d, 0);
	}
}

