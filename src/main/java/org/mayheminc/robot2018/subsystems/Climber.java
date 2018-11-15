package org.mayheminc.robot2018.subsystems;

import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.util.MayhemTalonSRX;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

	MayhemTalonSRX m_motor = new MayhemTalonSRX(RobotMap.CLIMBER_TALON);

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void initDefaultCommand() {}
    
    public void set(double value)
    {
    	m_motor.set(ControlMode.PercentOutput, value);
    	m_motor.setNeutralMode(NeutralMode.Brake);
    }
}

