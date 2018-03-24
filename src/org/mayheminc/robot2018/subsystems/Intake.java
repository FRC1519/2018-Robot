package org.mayheminc.robot2018.subsystems;

import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.robot2018.commands.IntakeOpenJaw;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The intake sucks in cubes and holds them.
 */
public class Intake extends Subsystem {
	
	public static final double INTAKE_SPEED = 0.75; // changed from 0.5 for handoff
	public static final double OUTTAKE_SPEED = -1.0;   // spit is at full speed...  
	public static final double STOP_SPEED = 0;
	
	public static final boolean ESCAPE_DEATH_GRIP_RIGHT = true;
	public static final boolean ESCAPE_DEATH_GRIP_LEFT = !ESCAPE_DEATH_GRIP_RIGHT;
	
	TalonSRX m_intakeMotorRight = new TalonSRX(RobotMap.INTAKE_RIGHT_TALON);
	TalonSRX m_intakeMotorLeft = new TalonSRX(RobotMap.INTAKE_LEFT_TALON);
	Solenoid m_solenoid = new Solenoid(RobotMap.INTAKE_FINGERS_SOLENOID);

	double m_setSpeed;
	
	boolean m_reverseLeftSide = false;
	boolean m_reverseRightSide = false;
	
	public Intake()
	{
		configTalon(m_intakeMotorRight);
		configTalon(m_intakeMotorLeft);
	}
	
	void configTalon(TalonSRX talon)
	{
		talon.configNominalOutputForward(0,  0);
		talon.configNominalOutputReverse(0.0,  0);
		talon.configPeakOutputForward(1.0,  0);
		talon.configPeakOutputReverse(-1.0,  0);

		talon.configContinuousCurrentLimit(1, 0);
		talon.configPeakCurrentLimit(2,  0);
		talon.configPeakCurrentDuration(500,  0);
	}
	
    public void initDefaultCommand() {
    }
    
    public void takeInCube() {
    	setMotors(INTAKE_SPEED);
    }
    
    void setMotors(double speed)
    {
    	m_setSpeed = speed;
    }
    
    public void stop() {
    	setMotors(STOP_SPEED);
    }
    
    public void spitOutCube() {
    	setMotors(OUTTAKE_SPEED);
    }
    
    public void openJaws()
    {
    	m_solenoid.set(true);
    }
    
    public void closeJaws()
    {
    	m_solenoid.set(false);
    }
    
    public void reverseLeft(boolean b)
    {
    	m_reverseLeftSide = b;
    }    
    public void reverseRight(boolean b)
    {
    	m_reverseRightSide = b;
    }
    
    public void periodic()
    {
    	double reverseLeft = (m_reverseLeftSide) ? -1.0 : 1.0;
    	double reverseRight = (m_reverseRightSide) ? -1.0 : 1.0;
    	
    	// left motor turns more slowly than right for suck in, but same speed for spit out
    	if (m_setSpeed > 0.0) {
    		m_intakeMotorLeft.set(ControlMode.PercentOutput, -m_setSpeed * reverseLeft * .75);
    	} else {
    		m_intakeMotorLeft.set(ControlMode.PercentOutput, -m_setSpeed * reverseLeft);
    	}
    	m_intakeMotorRight.set(ControlMode.PercentOutput, m_setSpeed * reverseRight);

    }
    public void updateSmartDashboard()
    {
    	SmartDashboard.putNumber("Intake Left Current",  m_intakeMotorLeft.getOutputCurrent());
    	SmartDashboard.putNumber("Intake Right Current",  m_intakeMotorRight.getOutputCurrent());
    }
}

