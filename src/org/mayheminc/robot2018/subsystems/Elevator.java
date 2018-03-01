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
	public static final int REST_NEAR_BOTTOM = 1000;
	public static final int HANDOFF_HEIGHT = 3000;
	public static final int SWITCH_HEIGHT = 3000;
	public static final int SCALE_LOW = 15000;
	public static final int SCALE_MID = 18700;
	public static final int SCALE_HIGH = 20000;
	public static final int CEILING = 24100; // high scale is 24100
	
	boolean m_SafetyOn = true;

    final int POSITION_TOLERANCE = 750;
    
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
		m_motor.configPeakOutputReverse(-0.6,  0);  // full speed of -1.0 going down was too fast

		// TODO: need to tune the PIDF parameters
		m_motor.config_kP(0, 0.3, 0);
		m_motor.config_kI(0, 0.0, 0);
		m_motor.config_kD(0, 0.01, 0);
		m_motor.config_kF(0, 0.0, 0);
		
		m_motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		// RJD !@!#@!#12
		
		m_motor.setInverted(true); // PRAC has this true
		m_motor.setSensorPhase(false); // PRAC has this true
		
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
    
    
    public void changeSetpointToCurrentPosition() {
    	m_autoSetpoint = m_motor.getSelectedSensorPosition(0);
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
     * Set the "zero point" of the elevator by telling the TalonSRX to set
	 * the selectedSensorPosition to zero.
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

//    	System.out.println("Elevator Manual Mode: " + m_manualMode);
//		System.out.println("Elevator Auto: " + m_autoSetpoint);
    	
    	if ( power > 0.01 || power < -0.01 ) {
    		m_manualMode = true;
    		m_motor.set(ControlMode.PercentOutput,  power);
    	} else {    			// this is position control (not just manual mode)
    		if (m_manualMode) { // we must have just previously been in manual mode, set to "hold position"
    			m_manualMode = false;
    			m_autoSetpoint = m_motor.getSelectedSensorPosition(0);
    		}
    		
    		// if our desired position is near the bottom, and our current position is also near the bottom,
    		// give the motor a chance to rest for a while
    		if ((m_autoSetpoint < REST_NEAR_BOTTOM) && (m_motor.getSelectedSensorPosition(0) < REST_NEAR_BOTTOM)) {
        		m_motor.set(ControlMode.PercentOutput,  0.0);
    		} else {
    			// actively hold position
        		m_motor.set(ControlMode.Position, m_autoSetpoint);	
    		}
    		
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

