package org.mayheminc.robot2018.subsystems;

import org.mayheminc.robot2018.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The intake sucks in cubes and holds them.
 */
public class Intake extends Subsystem {
	
	public static final double INTAKE_SPEED = 0.5; //JUST A PLACE HOLDER!
	public static final double OUTTAKE_SPEED = -1.0; //JUST A PLACE HOLDER!
	public static final double STOP_SPEED = 0;
	
	TalonSRX m_intakeMoterRight = new TalonSRX(RobotMap.INTAKE_RIGHT_TALON);
	TalonSRX m_intakeMoterLeft = new TalonSRX(RobotMap.INTAKE_LEFT_TALON);
	Solenoid m_solenoid = new Solenoid(RobotMap.INTAKE_FINGERS_SOLENOID);

	double m_setSpeed;
	
	boolean m_reverseLeftSide;
	
//	public Intake()
//	{
//		m_intakeMoterLeft.configPeakOutputForward(1.0,  0);
//		m_intakeMoterLeft.configPeakOutputReverse(-1.0,  0);
//		
//	}
    public void initDefaultCommand() {
    }
    
    public void takeInCube() {
    	setMotors(INTAKE_SPEED);
    }
    
    void setMotors(double speed)
    {
    	m_setSpeed = speed;
//    	m_intakeMoterRight.set(ControlMode.PercentOutput, speed);
//    	m_intakeMoterLeft.set(ControlMode.PercentOutput, -speed);
    }
    
    public void stop() {
    	setMotors(STOP_SPEED);
    }
    
    public void spitOutCube() {
    	setMotors(OUTTAKE_SPEED);
    }
    
    public void OpenJaws()
    {
    	m_solenoid.set(false);
    }
    public void CloseJaws()
    {
    	m_solenoid.set(true);
   
    }
    
    public void Reverse(boolean b)
    {
    	m_reverseLeftSide = b;
    }
    
    public void periodic()
    {
    	double reverse = (m_reverseLeftSide) ? -1.0 : 1.0;
    	
    	m_intakeMoterLeft.set(ControlMode.PercentOutput, -m_setSpeed * reverse);
    	m_intakeMoterRight.set(ControlMode.PercentOutput, m_setSpeed);

    }
}

