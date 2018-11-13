package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import org.mayheminc.robot2018.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;

/**
 *
 */
public class ElevatorArms extends Subsystem {

	Solenoid m_arm = new Solenoid(RobotMap.ELEVATOR_ARM_SOLENOID);
	TalonSRX m_motor = new TalonSRX(RobotMap.ELEVATOR_ARM_MOTOR);
	
	// default Jaw Position is closed.
	private static final JawPosition DEFAULT_JAW_POSITION = JawPosition.CLOSE;
	
	// initialize variables for solenoid state management
	private JawPosition m_elevatorArmDesiredState = DEFAULT_JAW_POSITION;
	private long m_elevatorArmTimeMotionComplete = System.currentTimeMillis();
	private static final long MOVING_TIME_MILLIS = 200;
	
	public static final double MOTOR_STOP = 0.0;
	public static final double MOTOR_IN_SLOW = 0.2;
	public static final double MOTOR_IN_FAST = 0.5;
	
	public enum JawPosition {
		OPEN(true),
		CLOSE(false);
		
	    private final boolean value; // https://stackoverflow.com/questions/1067352/can-set-enum-start-value-in-java
	    JawPosition(boolean value) { this.value = value; }
	    public boolean getValue() { return value; }
	}
	
	public ElevatorArms()
	{
		setJaw(DEFAULT_JAW_POSITION);
		
		m_motor.configNominalOutputForward(0,  0);
		m_motor.configNominalOutputReverse(0.0,  0);
		m_motor.configPeakOutputForward(1.0,  0);
		m_motor.configPeakOutputReverse(-1.0,  0);
		
		m_motor.setNeutralMode(NeutralMode.Brake);
	}
	
    public void initDefaultCommand() { }
    
    public void setJaw(JawPosition newDesiredJawPosition)
    {
    	// if setting a new position, need to set time motion will be complete
    	if (m_elevatorArmDesiredState != newDesiredJawPosition) {
    		m_elevatorArmTimeMotionComplete = System.currentTimeMillis() + MOVING_TIME_MILLIS;
    		m_elevatorArmDesiredState = newDesiredJawPosition;
    	}
    	
    	// set the jaw position as desired
    	m_arm.set(newDesiredJawPosition.getValue());
    }
    
    // return true if jaw may still be moving from a prior setJaw() call
    public boolean jawStillMoving() {
		return (System.currentTimeMillis() < m_elevatorArmTimeMotionComplete);
    }
    
    
    /**
     * Set the motor to a speed.
     * @param speed
     */
    public void setMotor(double speed)
    {
    	m_motor.set(ControlMode.PercentOutput, speed);  // PRAC has -, COMP has +  (due to right-handed vs. left-handed carriage)
    }
    
    public void updateSmartDashboard()
    {
    	SmartDashboard.putBoolean("T-Rex Arms",  m_arm.get());
    	SmartDashboard.putNumber("T-Rex Motor",  m_motor.getMotorOutputPercent());
    }
}
