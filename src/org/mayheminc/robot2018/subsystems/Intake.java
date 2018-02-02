package org.mayheminc.robot2018.subsystems;

import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.util.MayhemTalonSRX;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The intake sucks in cubes and holds them.
 */
public class Intake extends Subsystem {
	
	public static final double INTAKE_SPEED = 0.5; //JUST A PLACE HOLDER!
	public static final double OUTTAKE_SPEED = -0.5; //JUST A PLACE HOLDER!
	public static final double STOP_SPEED = 0;
	
	MayhemTalonSRX m_intakeMoterRight = new MayhemTalonSRX(RobotMap.INTAKE_RIGHT_TALON);
	MayhemTalonSRX m_intakeMoterLeft = new MayhemTalonSRX(RobotMap.INTAKE_LEFT_TALON);

    public void initDefaultCommand() {
    }
    
    public void takeInCube() {
    	setMotors(INTAKE_SPEED);
    }
    
    void setMotors(double speed)
    {
    	m_intakeMoterRight.set(ControlMode.PercentOutput, speed);
    	m_intakeMoterLeft.set(ControlMode.PercentOutput, speed);
    }
    
    public void stop() {
    	setMotors(STOP_SPEED);
    }
    
    public void spitOutCube() {
    	setMotors(OUTTAKE_SPEED);
    }
}

