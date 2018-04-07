package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class TurretZero extends InstantCommand {

	int m_position;
	
    public TurretZero() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        setRunWhenDisabled(true);
        m_position = Turret.FRONT_POSITION;
    }
    public TurretZero(int position) {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_position = position;
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.turret.zeroEncoder(m_position);
    }

}
