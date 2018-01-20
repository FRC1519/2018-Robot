package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.util.Utils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SelfTestGyro extends Command {
	public static final double TIME_MIN = 0.00;
	public static final double TIME_MAX = 2.00;
	
	public static final int CHANGES_MIN = 25;
	public static final int CHANGES_MAX = 450;
	
	public static final double TOTAL_DRIFT_MIN = 0.0;
	public static final double TOTAL_DRIFT_MAX = 0.085;
	
	public static final int NUMBER_OF_LOOPS = 50;
	public static final int NUMBER_OF_LOOPS_BEFORE_CHECKS = 0;
	
	//private double m_voltage = 0.00;
	private int m_count = 0;
	private boolean m_pass = true;
	private double m_timeRequired = 0;
	private Timer m_timer = new Timer();
	
	private double startValue = 0.00;
	private double lastValue = 0.00;
	private int numberOfChanges = 0;
	private double totalDrift = 0.00;
	
	
    public SelfTestGyro() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putBoolean("SelfTest Gyro Pass", false);
    	DriverStation.reportError("Starting to test Gyro", false);
    	
    	// initialize needed settings
		m_count = 0;
		m_pass = true;
		m_timer.reset();
    	m_timer.start();
    	
    	startValue = Robot.drive.getHeading();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	m_count++;
    	
    	
    	if(Robot.drive.getHeading() != lastValue){
    		numberOfChanges++;
    	}
    	
    	
    	
    	
    	lastValue = Robot.drive.getHeading();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	 return (m_count >= NUMBER_OF_LOOPS);
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	m_timer.stop();
    	m_timeRequired = m_timer.get(); 
    	totalDrift = Math.abs(Robot.drive.getHeading() - startValue);
    	
		
		if (m_timeRequired < (TIME_MIN * NUMBER_OF_LOOPS)) {
    		m_pass = false;
    		DriverStation.reportError("SelfTest Gyro Error: Time required to complete " 
    									+ NUMBER_OF_LOOPS +  " loops: " 
    									+ m_timeRequired + " is below threshold " 
    									+ (TIME_MIN * NUMBER_OF_LOOPS) + "\n" , false);
    	}
    	
		if (m_timeRequired > (TIME_MAX * NUMBER_OF_LOOPS)) {
    		m_pass = false;
    		DriverStation.reportError("SelfTest Gyro Error: Time required to complete " 
    									+ NUMBER_OF_LOOPS +  " loops: " 
										+ m_timeRequired + " is above threshold " 
    									+ (TIME_MAX * NUMBER_OF_LOOPS) + "\n" , false);
    	}
		
		if (numberOfChanges < CHANGES_MIN) {
    		m_pass = false;
    		DriverStation.reportError("SelfTest Gyro Error: " 
    									+ numberOfChanges +  " changes is below threshold " 
    									+ CHANGES_MIN + "\n" , false);
    	}
    	
		if (numberOfChanges > CHANGES_MAX) {
    		m_pass = false;
    		DriverStation.reportError("SelfTest Gyro Error: " 
    									+ numberOfChanges +  " changes is above threshold " 
    									+ CHANGES_MAX + "\n" , false);
    	}
		if (totalDrift < TOTAL_DRIFT_MIN) {
    		m_pass = false;
    		DriverStation.reportError("SelfTest Gyro Error: " 
    									+ Utils.twoDecimalPlaces(totalDrift) +  " total Drift is below threshold " 
    									+ TOTAL_DRIFT_MIN + "\n" , false);
    	}
		if (totalDrift > TOTAL_DRIFT_MAX) {
    		m_pass = false;
    		DriverStation.reportError("SelfTest Gyro Error: " 
    									+ Utils.twoDecimalPlaces(totalDrift) +  " total Drift is above threshold " 
    									+ TOTAL_DRIFT_MAX + "\n" , false);
    	}
		
		
    	
		SmartDashboard.putNumber("SelfTest Gyro Number Of Changes", numberOfChanges);
		SmartDashboard.putNumber("SelfTest Gyro Total Drift", totalDrift);
		SmartDashboard.putNumber("SelfTest Gyro Time Required", m_timeRequired);
		SmartDashboard.putBoolean("SelfTest Gyro Pass", m_pass);
		DriverStation.reportError("Finished testing Gyro", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	m_pass = false;
		SmartDashboard.putBoolean("SelfTest Gyro Pass", m_pass);
		DriverStation.reportError("SelfTest Gyro Error: Self Test Interrupted", false);
    }
}
