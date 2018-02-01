package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import org.mayheminc.robot2018.RobotMap;

/**
 *
 */
public class Elevator extends Subsystem {

	boolean m_SafetyOn = true;

	int m_motorSpeed;
	TalonSRX m_motor = new TalonSRX(RobotMap.ELEVATOR_TALON);
	
    public void initDefaultCommand() {
    }
    
    /**
     * Set the elevator speed to positive (up) or negative (down) or stop (zero).
     * @param x
     */
    public void SetSpeed(int x)
    {
    	m_motorSpeed = x;
    }
    
    /**
     * Set the current position of the elevator to the zero point.
     */
    public void Zero()
    {
    	m_motor.setSelectedSensorPosition(0,  0,  0);
    }
    
    /**
     * Turn the safety bounds off.  Do this if we skip the belt and need to position the elvevator.
     */
    public void TurnSafetyOff()
    {
    	m_SafetyOn = false;
    }
    
    final int ELEVATOR_FLOOR = 0;
    final int ELEVATOR_CEILING = 1000;
    
    /**
     * Periodically, check to see if the elevator is within the bounds stop if out of bounds.
     */
    public void Periodic()
    {
    	if( m_SafetyOn )
    	{
    		// check the encoder limits.
    		int position = m_motor.getSelectedSensorPosition(0);
    		
    		if( position < ELEVATOR_FLOOR ||
    			position > ELEVATOR_CEILING)
    		{
    			m_motorSpeed = 0;
    		}
    	}
    	
    	m_motor.set(ControlMode.Velocity,  m_motorSpeed);
    }
}

