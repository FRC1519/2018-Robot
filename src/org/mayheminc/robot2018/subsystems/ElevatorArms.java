package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;
import org.mayheminc.robot2018.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

/**
 *
 */
public class ElevatorArms extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Solenoid m_arm = new Solenoid(RobotMap.ELEVATOR_ARM_SOLENOID);
	TalonSRX m_motor = new TalonSRX(RobotMap.ELEVATOR_ARM_MOTOR);

	public static boolean OPEN_ARM = true;
	public static boolean CLOSE_ARM = !OPEN_ARM;
	public enum JawPositions{
		OPEN(true),
		CLOSE(false);
		
	    private final boolean id; // https://stackoverflow.com/questions/1067352/can-set-enum-start-value-in-java
	    JawPositions(boolean id) { this.id = id; }
	    public boolean getValue() { return id; }
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setJaw(boolean b)
    {
    	m_arm.set(b);
    }
}
