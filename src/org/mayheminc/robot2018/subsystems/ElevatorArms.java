package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import org.mayheminc.robot2018.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

/**
 *
 */
public class ElevatorArms extends Subsystem {

	Solenoid m_arm = new Solenoid(RobotMap.ELEVATOR_ARM_SOLENOID);
	TalonSRX m_motor = new TalonSRX(RobotMap.ELEVATOR_ARM_MOTOR);

	public static boolean OPEN_ARM = true;
	public static boolean CLOSE_ARM = !OPEN_ARM;
	
	public static final double MOTOR_STOP = 0.0;
	public static final double MOTOR_OUT_SLOW = 0.2;
	public static final double MOTOR_OUT_FAST = 1.0;
	
	public enum JawPosition{
		OPEN(true),
		CLOSE(false);
		
	    private final boolean id; // https://stackoverflow.com/questions/1067352/can-set-enum-start-value-in-java
	    JawPosition(boolean id) { this.id = id; }
	    public boolean getValue() { return id; }
	}
	
    public void initDefaultCommand() { }
    
    public void setJaw(JawPosition b)
    {
    	m_arm.set(b.getValue());
    }
    
    /**
     * Set the motor to a speed.
     * @param d
     */
    public void setMotor(double d)
    {
    	m_motor.set(ControlMode.PercentOutput, d);
    }
    
    public void updateSmartDashboard()
    {
    	SmartDashboard.putBoolean("T-Rex Arms",  m_arm.get());
    	SmartDashboard.putNumber("T-Rex Motor",  m_motor.getMotorOutputPercent());
    }
}
