package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurretMoveToDegree extends Command {

        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	int m_position;
    	//static double MAX_TIMEOUT = 5.0; // maximum number of seconds to run this command.
        public TurretMoveToDegree(double d) {
            super();
            // Use requires() here to declare subsystem dependencies
            // eg. requires(chassis);
            if (d > 180) {
            	d = d-360;
            }
            m_position = (int) (d * Turret.ENCODER_COUNTS_PER_DEGREE);
        }

     // Called once when the command executes
        protected void initialize() 
        {
        	// if we told it to go to the rear, figure out which rear to go to.
        	if( m_position <= Turret.LEFT_REAR ||
        		m_position >= Turret.RIGHT_REAR )
        	{
        		if( Robot.turret.getCurrentPosition() > 0 )
        			m_position = Turret.RIGHT_REAR;
        		else
        			m_position = Turret.LEFT_REAR;
        	}
        	System.out.println("TurretMoveTo: setPosition" + m_position);
        	Robot.turret.setDesiredPosition(m_position);
        }
        
        protected boolean isFinished() {return Robot.turret.isAtPosition();}
        
}
