package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.mayheminc.robot2018.RobotMap;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

/**
 *
 */
public class Winch extends Subsystem {

	public static final double WINCH_SLOW = .5;
	public static final double WINCH_FAST = 1;
	
	TalonSRX m_winch = new TalonSRX(RobotMap.WINCH_TALON);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void runSlow() {
    	m_winch.set(ControlMode.PercentOutput, WINCH_SLOW);
    }
    
    public void runFast() {
    	m_winch.set(ControlMode.PercentOutput, WINCH_FAST);
    }
}