package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * The DeferredCommand creates an object at runtime.
 * This command only does that if the auto time remaining is at least N.nn seconds.
 * @author User
 *
 */
public class RunIfAutoTimeRemaining extends DeferredCommand{
	double m_timeRemaining;
	
	public RunIfAutoTimeRemaining(double timeRemaining, Class<? extends Command> Cmd)
	{
		super(Cmd);
		m_timeRemaining = timeRemaining;
	}
	
	protected void initialize() 
	{
		// if the time remaining is more than the limit, call the DeferredCommand to start the command.
		if( Robot.autonomousTimeRemaining() >= m_timeRemaining )
		{
			super.initialize();
		}
		else // there is not enough time, just mark the command as started, so the DeferredCommand will end.
		{
			this.m_CommandStarted = true;
		}
	}
}
