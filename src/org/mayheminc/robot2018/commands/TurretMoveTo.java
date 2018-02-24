package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class TurretMoveTo extends InstantCommand {

	int m_position;
    public TurretMoveTo(int position) {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_position = position;
    }

    // Called once when the command executes
    protected void initialize() 
    {
    	// if we told it to go to the rear, figure out which rear to go to.
    	if( m_position == Turret.LEFT_REAR ||
    		m_position == Turret.RIGHT_REAR )
    	{
    		if( Robot.turret.getPosition() > 0 )
    			m_position = Turret.RIGHT_REAR;
    		else
    			m_position = Turret.LEFT_REAR;
    	}
    	
    	Robot.turret.setPosition(m_position);
    }
}
