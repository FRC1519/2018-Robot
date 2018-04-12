package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimberSet extends Command {

	double m_value;
	public ClimberSet(double value) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.climber);
		m_value = value;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.climber.set(m_value);
	}
	
    protected void execute() {
		Robot.climber.set(m_value);
	}
    
	protected void end() {Robot.climber.set(0.0);}

	protected void interrupted() {Robot.climber.set(0.0);}

	protected boolean isFinished() {return false;	}

}
