package org.mayheminc.robot2018.subsystems;

import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.util.MayhemTalonSRX;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The intake sucks in cubes and holds them.
 */
public class Intake extends Subsystem {
	
	public static final int LEFT_MOTER_SPEED = 200; //JUST A PLACE HOLDER!
	public static final int RIGHT_MOTER_SPEED = 200; //JUST A PLACE HOLDER!
	public static final int STOP_MOTOR_SPEED = 0;
	
	MayhemTalonSRX m_intakeMoterRight = new MayhemTalonSRX(RobotMap.INTAKE_RIGHT_TALON);
	MayhemTalonSRX m_intakeMoterLeft = new MayhemTalonSRX(RobotMap.INTAKE_LEFT_TALON);

    public void initDefaultCommand() {
    }
    
    public void takeInCube() {
    	m_intakeMoterRight.set(ControlMode.Velocity, -RIGHT_MOTER_SPEED);
    	m_intakeMoterLeft.set(ControlMode.Velocity, -LEFT_MOTER_SPEED);
    }
    
    public void stop() {
    	m_intakeMoterRight.set(ControlMode.Velocity, STOP_MOTOR_SPEED);
    	m_intakeMoterLeft.set(ControlMode.Velocity, STOP_MOTOR_SPEED);
    }
    
    public void spitOutCube() {
    	m_intakeMoterRight.set(ControlMode.Velocity, RIGHT_MOTER_SPEED);
    	m_intakeMoterLeft.set(ControlMode.Velocity, LEFT_MOTER_SPEED);
    }
}

