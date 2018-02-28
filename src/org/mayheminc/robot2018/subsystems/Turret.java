package org.mayheminc.robot2018.subsystems;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.util.MayhemTalonSRX;
import org.mayheminc.util.PidTunerObject;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Turret extends Subsystem implements PidTunerObject {

	public static final int FRONT_POSITION = 0;
	public static final int LEFT_POSITION = -1000;
	public static final int RIGHT_POSITION = 1000;
	public static final int LEFT_REAR = -2000;
	public static final int RIGHT_REAR = 2000;

	MayhemTalonSRX m_motor = new MayhemTalonSRX(RobotMap.TURRET_TALON);
	boolean m_manualmode = false;
	int m_autoSetpoint;
	
    public void initDefaultCommand() { }
    
    public Turret()
    {
    	super();
    	
    	// initialize the PID controller
    	m_motor.config_kP(0,  0.4,  0);
    	m_motor.config_kI(0,  0.0,  0);
    	m_motor.config_kD(0,  0.0,  0);
    	m_motor.config_kF(0,  0.0, 0);
    	
    	m_motor.selectProfileSlot(0,  0);
    	
    	m_motor.setNeutralMode(NeutralMode.Coast);
    	m_motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    	
		m_motor.setInverted(false);
		m_motor.setSensorPhase(false);
		
		m_motor.configClosedloopRamp(0.25, 0);
		m_motor.configOpenloopRamp(0.25,  0);
		

		m_motor.setSelectedSensorPosition(m_motor.getSelectedSensorPosition(0), 0, 0);
		m_motor.configMotionAcceleration(1000,  0);
    	m_motor.enableControl();
    }
    
    public boolean isAtPosition()
    {
    	return ( Math.abs(getPosition() - m_autoSetpoint) < 250);
    }
    
    public int getPosition()
    {
    	return m_motor.getSelectedSensorPosition(0);
    }
    
    public void setPosition(int position)
    {
    	System.out.println("Turret: setPosition" + position);
    	m_autoSetpoint = position;
    	m_manualmode = false;
    }
    
    public void zeroEncoder()
    {
    	m_motor.setEncPosition(FRONT_POSITION);
    }
    
    public void periodic()
    {
    	double power = Robot.oi.getTurretPower();
    	
    	// if the joystick is being commanded...
    	if(Math.abs(power) > 0.0)
    	{
    		System.out.println("Turret: periodic: Power: " + power);
    		m_manualmode = true;
    	}
    	
    	if( m_manualmode )
    	{
    		m_motor.set(ControlMode.PercentOutput, power);
    	}
		else // PID mode is set in setPosition()
    	{
			m_motor.set(ControlMode.Position, m_autoSetpoint);
    	}
    }
    
    public void updateSmartDashboard()
    {
    	SmartDashboard.putNumber("Turret Pos", m_motor.getPosition());
    	SmartDashboard.putNumber("Turret Power", m_motor.getMotorOutputPercent());
    	SmartDashboard.putBoolean("Turret Manual Mode",  m_manualmode);
    }
    
    /////////////////////////////////////////////////////////
    // Implement PidTunerObject
    /////////////////////////////////////////////////////////
    
	public double getP()
	{
		return m_motor.getP();
	}
	public double getI()
	{
		return m_motor.getI();
	}
	public double getD()
	{
		return m_motor.getD();
	}
	public double getF()
	{
		return m_motor.getF();
	}
	public void setP(double d)
	{
		m_motor.config_kP(0,  d,  0);
	}
	public void setI(double d)
	{
		m_motor.config_kI(0,  d,  0);
	}
	public void setD(double d)
	{
		m_motor.config_kD(0,  d,  0);
	}
	public void setF(double d)
	{
		m_motor.config_kF(0,  d,  0);
	}
}

