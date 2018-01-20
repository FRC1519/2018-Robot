package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import org.mayheminc.robot2015.subsystems.Drive;

/**
 *
 */
public class SelfTestDrive extends Command {

	
//	ALL OF THESE NUMBERS *NEED* TO BE RE-EVALUATED
	
	public static final int NUMBER_OF_LOOPS = 50;
	public static final int NUMBER_OF_LOOPS_BEFORE_CHECKS = 25;
	
	public static final int VELOCITY_MIN = 0;
	public static final int VELOCITY_MAX = 25000;

	public static final double CURRENT_MIN  = 0;
	public static final double CURRENT_MAX = 100;

	public static final int ENCODER_MIN = 0;
	public static final int ENCODER_MAX = 10000;
	
	public static final double TIME_MIN = 0.5 / NUMBER_OF_LOOPS;
	public static final double TIME_MAX = 2.0 / NUMBER_OF_LOOPS;
	
	public static final String errorReportStrings[] = { "Front Left", "Front Right", "Back Left", "Back Right"};
	
	public static final int k_frontLeft = 0;
	public static final int k_frontRight = 1;
	public static final int k_backLeft = 2;
	public static final int k_backRight = 3;

	
	private static int startPosition = 0;
	private static int SPEED = 600;
	
	private int wheelToTest = 0;

	int m_count = 0;

	boolean m_pass = true;

	double m_timeRequired = 0;
	Timer m_timer = new Timer();
	
	int m_encoderCount = 0;
	double m_velocity = 0;
	double m_current = 0;
    public SelfTestDrive(int argument) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	wheelToTest = argument;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// set display of SelfTest status to false
    			SmartDashboard.putBoolean("SelfTest Drive " + errorReportStrings[wheelToTest] + " Pass", false);
    	    	
    	    	// initialize needed settings
    			m_count = 0;
    			m_pass = true;
    			m_timer.reset();
    	    	m_timer.start();
    	    	
//    	    	record the current position of the wheel
//    	    	startPosition = Robot.drive.getWheelPosition(wheelToTest);
    	    	
    	    	// start the test by turning the motor on
//    	    	Robot.drive.setSelfTestWheelSpeed(wheelToTest, SPEED);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	m_count++;
    	if (m_count > NUMBER_OF_LOOPS_BEFORE_CHECKS) {
//			m_velocity = Robot.drive.getWheelSpeed(wheelToTest);
//			m_current = Robot.drive.getWheelCurrent(wheelToTest);

			if (m_velocity < VELOCITY_MIN) {
				m_pass = false;
				DriverStation.reportError("SelfTest Drive " + errorReportStrings[wheelToTest] +  
						" Error: Velocity of " + m_velocity +
						" below threshold of " + VELOCITY_MIN + "\n", false);
			}
			
			if (m_velocity > VELOCITY_MAX) {
				m_pass = false;
				DriverStation.reportError("SelfTest Drive " + errorReportStrings[wheelToTest] +
						" Error: Velocity of " + m_velocity + 
						" above threshold of " + VELOCITY_MAX + "\n", false);
			}	
			
			if (m_current < CURRENT_MIN) {
				m_pass = false;
				DriverStation.reportError("SelfTest Drive " + errorReportStrings[wheelToTest] +
						" Error: Current of " + m_current + 
						" below threshold of " + CURRENT_MIN + "\n", false);
			}
			
			if (m_current > CURRENT_MAX) {
				m_pass = false;
				DriverStation.reportError("SelfTest Drive " + errorReportStrings[wheelToTest] + 
						" Error: Current of " + m_current + 
						" above threshold of " + CURRENT_MAX + "\n", false);
			}
			SmartDashboard.putNumber("Drive " + errorReportStrings[wheelToTest] +
					" SelfTest Velocity", m_velocity);
			SmartDashboard.putNumber("Drive " + errorReportStrings[wheelToTest] +
					" SelfTest Current", m_current);
		}
	}

    

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (m_count >= NUMBER_OF_LOOPS);
    }

    // Called once after isFinished returns true
    protected void end() {
//    	Robot.drive.stopWheel(wheelToTest);
		m_timer.stop();
    	m_timeRequired = m_timer.get();   	
//    	m_encoderCount = Robot.drive.getWheelPosition(wheelToTest) - startPosition;

		if (m_encoderCount < ENCODER_MIN) {
			m_pass = false;
			DriverStation.reportError("SelfTest Drive " + errorReportStrings[wheelToTest] +
					" Error: Distance traveled by the Drive " + errorReportStrings[wheelToTest] + " Wheel: "
					+ m_encoderCount + " is beneath the threshold of " 
					+ ENCODER_MIN + "\n", false);    		
		}
		
		if (m_encoderCount > ENCODER_MAX) {
			m_pass = false;
			DriverStation.reportError("SelfTest Drive " + errorReportStrings[wheelToTest] + 
					" Error: Distance traveled by the Drive " + errorReportStrings[wheelToTest] + " Wheel: "
										+ m_encoderCount + " is above the threshold of " 
										+ ENCODER_MAX + "\n", false);
		}
		
		if (m_timeRequired < (TIME_MIN * NUMBER_OF_LOOPS)) {
    		m_pass = false;
    		DriverStation.reportError("SelfTest Drive "+ errorReportStrings[wheelToTest]
    									+ "Error: Time required to complete " 
    									+ NUMBER_OF_LOOPS +  " loops: " 
    									+ m_timeRequired + " is below threshold " 
    									+ (TIME_MIN * NUMBER_OF_LOOPS) + "\n" , false);
    	}
    	
		if (m_timeRequired > (TIME_MAX * NUMBER_OF_LOOPS)) {
    		m_pass = false;
    		DriverStation.reportError("SelfTest Drive " + errorReportStrings[wheelToTest] 
    									+ " Error: Time required to complete " 
    									+ NUMBER_OF_LOOPS +  " loops: " 
										+ m_timeRequired + " is above threshold " 
    									+ (TIME_MAX * NUMBER_OF_LOOPS) + "\n" , false);
    	}
    	
		SmartDashboard.putNumber("SelfTest Drive " + errorReportStrings[wheelToTest] 
				+ " Encoder", m_encoderCount);
		SmartDashboard.putNumber("SelfTest Drive " + errorReportStrings[wheelToTest] +
				" Time Required", m_timeRequired);
		SmartDashboard.putBoolean("SelfTest Drive " + errorReportStrings[wheelToTest] + " Pass", m_pass);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	m_pass = false;
		SmartDashboard.putBoolean("SelfTest Drive " +  errorReportStrings[wheelToTest] + " Pass", m_pass);
		DriverStation.reportError("SelfTest Drive " + errorReportStrings[wheelToTest] 
				+ " Error: Self Test Interrupted", false);
//		Robot.drive.stopWheel(wheelToTest);
    }
}
