package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will create another command at runtime and execute it.
 * The command must be able to be created with no parameters.
 * 
 * Example:
 *  BUTTON.whenPressed(new DeferredCommand(AnotherCommand.class));
 *  -notice the ".class" after the command that is passed in.
 */
public class DeferredCommand extends Command {
	Class<? extends Command> m_CommandType;
	boolean m_CommandStarted;
	
    public DeferredCommand(Class<? extends Command> Cmd) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_CommandType = Cmd;
    	m_CommandStarted = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Command c = null;
    	try {
    		c = m_CommandType.newInstance();

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if( c != null)
    	{
    		c.start();
    	}
		m_CommandStarted = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// do nothing
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_CommandStarted;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
