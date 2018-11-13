package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *DOES NOT fully calibrate the navx
 *this command will simply reset the navx
 */
public class ZeroGyro extends InstantCommand {

	private double m_headingOffset = 0.0;
    public ZeroGyro() {
    	this(0.0);
    }
    
    public ZeroGyro(double headingOffset) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	setRunWhenDisabled(true);
    	m_headingOffset = headingOffset;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.zeroHeadingGyro(m_headingOffset);  	
    }
}
