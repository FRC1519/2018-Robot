package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
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

	// Note:  300 counts is about 1 inch
	public static final int BOTTOM_SAFETY_LIMIT = 0;
	public static final int PICK_UP_CUBE = 0;
	public static final int REST_NEAR_BOTTOM = 1500;
	public static final int PREPARE_FOR_HANDOFF_HEIGHT = 3500;
	public static final int SWITCH_HEIGHT = 3500;
	
	// Below heights are for Gray's Basement
//	public static final int SCALE_LOW = 7500;
//	public static final int SCALE_MID = 10000; // was 18700;    // normally used by "scale button" on OI
//	public static final int SCALE_HIGH = 13000;   // also used by the autonomous programs
//	public static final int CEILING = 16000; 	  // was 24100 at start of Week 1
//	public static final int TOP_SAFETY_LIMIT = 17000;  // should really be 30000
	
	public static final int SCALE_LOW = 15000;	
	public static final int SCALE_MID = 19700; // was 18700;    // normally used by "scale button" on OI
	public static final int SCALE_HIGH = 23500;   // also used by the autonomous programs
	public static final int CEILING = 28500; 	  // was 24100 at start of Week 1
	public static final int TOP_SAFETY_LIMIT = 30000;  // should really be 30000
	
	boolean m_SafetyOn = true;

    final int POSITION_TOLERANCE = 750;
    
	int m_motorSpeed;
	boolean m_manualMode = true;
	int m_desiredPosition;
	
	MayhemTalonSRX m_elevatorMotor = new MayhemTalonSRX(RobotMap.ELEVATOR_TALON);
	
	public Elevator()
	{
		super();

		m_elevatorMotor.configNominalOutputForward(0.0,  0);
		m_elevatorMotor.configNominalOutputReverse(0.0, 0);
		m_elevatorMotor.configPeakOutputForward(1.0,  0);
		m_elevatorMotor.configPeakOutputReverse(-0.7,  0);    // was -0.5 at UNH

		// TODO: need to tune the PIDF parameters
		m_elevatorMotor.config_kP(0, 1.0, 0);
		m_elevatorMotor.config_kI(0, 0.0, 0);
		m_elevatorMotor.config_kD(0, 100.0, 0);
		m_elevatorMotor.config_kF(0, 0.0, 0);
		
		m_elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		// RJD !@!#@!#12
		
		m_elevatorMotor.setInverted(false); 		
		m_elevatorMotor.setSensorPhase(true); 		
		
		m_elevatorMotor.configClosedloopRamp(0.25, 0);
		m_elevatorMotor.configOpenloopRamp(0.25,  0);
		
		m_elevatorMotor.configForwardSoftLimitThreshold(TOP_SAFETY_LIMIT, 0);
		m_elevatorMotor.configForwardSoftLimitEnable(true,  0);
		m_elevatorMotor.configReverseSoftLimitThreshold(BOTTOM_SAFETY_LIMIT, 0);
		m_elevatorMotor.configReverseSoftLimitEnable(true,  0);
		
		m_elevatorMotor.setSelectedSensorPosition(m_elevatorMotor.getSelectedSensorPosition(0), 0, 0);
	}
	
    public void initDefaultCommand() {}
    
    public void setDesiredPosition(int pos)
    {
    	m_manualMode = false; // set to auto mode
    	m_SafetyOn = true; // turn on the safety checks
    	m_desiredPosition = pos; // get the desired setpoint
    	m_elevatorMotor.configMotionAcceleration(1000,  0);
    }
    
    public int getCurrentPosition()
    {
    	return m_elevatorMotor.getSelectedSensorPosition(0);
    }
    
    public void holdCurrentPosition() {
    	// "hold" current position is the same as a setPosition of the current position
    	setDesiredPosition(m_elevatorMotor.getSelectedSensorPosition(0));
    }
    
    /**
     * If the elevator is within the tolerance, then return true.
     * @return
     */
    public boolean isAtPosition()
    {
    	// if manual mode is enabled, always return true.
    	if( m_manualMode ) return true;
    	
    	int position = m_elevatorMotor.getSelectedSensorPosition(0);
    	int diff = Math.abs(position - m_desiredPosition);
    	
//    	System.out.println("IsElevatorAtPosition Diff " + diff);
    	
    	return (diff < POSITION_TOLERANCE);
    }
    
    /**
     * Set the "zero point" of the elevator by telling the TalonSRX to set
	 * the selectedSensorPosition to zero.
     */
    public void Zero()
    {
    	m_elevatorMotor.setSelectedSensorPosition(0,  0,  0);
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
    	double manualPowerRequested = Robot.oi.getElevatorPower();

//    	System.out.println("Elevator Manual Mode: " + m_manualMode);
//		System.out.println("Elevator Auto: " + m_autoSetpoint);
    	
    	if ( Math.abs(manualPowerRequested) > 0.01 ) {
    		m_manualMode = true;
    		m_elevatorMotor.set(ControlMode.PercentOutput,  manualPowerRequested);
    	} 
    	else {    			// this is position control (not just manual mode)
    		if (m_manualMode) {
    			// we must have just previously been in manual mode, set to "hold position"
    			holdCurrentPosition();
    		}
    		
    		// if our desired position is near the bottom, and our current position is also near the bottom,
    		// give the motor a chance to rest for a while
    		if ((m_desiredPosition < REST_NEAR_BOTTOM) && (m_elevatorMotor.getSelectedSensorPosition(0) < REST_NEAR_BOTTOM)) {
        		m_elevatorMotor.set(ControlMode.PercentOutput,  0.0);
    		} else {
    			// actively hold position
        		m_elevatorMotor.set(ControlMode.Position, m_desiredPosition);	
    		}
    		
    	}
    }
    
    public void updateSmartDashboard()
    {
    	SmartDashboard.putNumber("Elevator Pos", m_elevatorMotor.getPosition());
    	SmartDashboard.putNumber("Elevator V", m_elevatorMotor.getMotorOutputVoltage());
    	SmartDashboard.putNumber("Elevator I", m_elevatorMotor.getOutputCurrent());
    	SmartDashboard.putNumber("Elevator Watts", m_elevatorMotor.getMotorOutputVoltage() * m_elevatorMotor.getOutputCurrent());
    	
    	SmartDashboard.putNumber("Elevator V PDP", Robot.pdp.getCurrent(3));
    }

	@Override
	public double getP() {
		return m_elevatorMotor.getP();
	}

	@Override
	public double getI() {
		return m_elevatorMotor.getI();
	}

	@Override
	public double getD() {
		return m_elevatorMotor.getD();
	}

	@Override
	public double getF() {
		return m_elevatorMotor.getF();
	}

	@Override
	public void setP(double d) {
		m_elevatorMotor.config_kP(0, d, 0);
	}

	@Override
	public void setI(double d) {
		m_elevatorMotor.config_kI(0, d, 0);
	}

	@Override
	public void setD(double d) {
		m_elevatorMotor.config_kD(0, d, 0);
	}
	@Override
	public void setF(double d) {
		m_elevatorMotor.config_kF(0, d, 0);
	}
}

