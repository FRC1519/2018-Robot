package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.util.MayhemTalonSRX;
/**
 *
 */
public class Launcher extends Subsystem{
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    //positive retracts
    
    public static final boolean WINCH_ENGAGED = false; // pulling back
    public static final boolean WINCH_RELEASED = true; // release to launch
    
    // States for the state machine
    public static final int READY = 0;
    public static final int WAIT_FOR_CLAW_TO_OPEN = 1;
    public static final int LAUNCHING = 2;
    public static final int NUDGE_AFTER_FAILED_LAUNCH_PART_1 = 3;
    public static final int NUDGE_AFTER_FAILED_LAUNCH_PART_2 = 4;
    public static final int WAIT_AFTER_LAUNCH = 5;
    public static final int START_CALIBRATING = 6;
    public static final int CALIBRATING = 7;
    public static final int REWINDING = 8;
    public static final int WAIT_AFTER_REWIND = 9;
    
    public static final boolean SET_POSITION_LAUNCH = true;
    public static final boolean SET_POSITION_WIND = !SET_POSITION_LAUNCH;
    
    private static int state = READY;
    private static boolean launch = false;
    
    private Solenoid launchSolenoid = new Solenoid(RobotMap.LAUNCH_SOLENOID);
//    private static DigitalInput LAUNCHER_LIMIT_SWITCH;// = new DigitalInput(RobotMap.LAUNCHER_RESET_LIMIT_SWITCH);
//    private static DigitalInput LAUNCHER_LIMIT_SWITCH2;// = new DigitalInput(RobotMap.LAUNCHER_RESET_LIMIT_SWITCH2);
    private MayhemTalonSRX m_winchMotorTalon = new MayhemTalonSRX(RobotMap.LAUNCHER_WINCH_TALON);
//    private static final AnalogChannel LAUNCHER_SWITCH = new AnalogChannel(RobotMap.LAUNCHER_RESET);
    
    private boolean m_autonomousPermissionToLaunch = false;
    private boolean m_shotJustFired = false;
    
    public Launcher() {
    	
    }    
    
    public void setMotorPower(double d){
        // protect the winch motor from being driven backwards
        if (d < 0.1) {
            d = 0;
        }
        m_winchMotorTalon.set((int) -d);
    }
    
    public boolean isRetracted(){
        return false; // LAUNCHER_LIMIT_SWITCH.get() && LAUNCHER_LIMIT_SWITCH2.get();
    }
    
    public boolean shotJustFired(){
    	return m_shotJustFired;
    }
    
    public double getEncoder(){
        return m_winchMotorTalon.getPosition();
    }
    
    public void autonomousPermissionToLaunch(boolean permission) {
        m_autonomousPermissionToLaunch = permission;
    }
    
    public void treatAutonomousStartAsCalibrated() {
        // use the autonomous starting position as the "calibrated" point
        calibrated = true;
        m_winchMotorTalon.setEncPosition(0);
    }
    
    // calibration design:
    // after being calibrated, the "zero point" is fully retracted
        // when calibrated:  "no slack" = -600
        //                   retracted = 0
            
    private static double lastTransitionTime = 0;

    private static double clawOpenDelay = 0.050; // Was .050 before GSD
    private static double launchTimeout = 0.300;  // was 0.400 on first day of UNH
    private static double nudgeTimeout = 0.300;   // was 0.400 on first day of UNH
    private static double timeToWaitAfterLaunch = 0.500;
    private static int loopsStationary = 0;
    private static double startNudgePosition = 0;
    private static boolean calibrated = false;

    private static final double TENSION_STRING_POWER = 0.13;
    private static final double CALIBRATE_POWER = 0.8; // RJD temp value for short pull-backs. was 0.80;
    private static final double WIND_POWER = 0.8;   // KBS:  was 1.0 in 2014 robot.

    // was -2750 for first practice match at Pine Tree; shots were consistently weak
    // adjusted to -2800 for second practice match at Pine Tree.
    // -2825 was no higher than middle of goal; -2875 decided at CMP practice space on Thu 4/28 
    private static final int NO_SLACK_ENCODER_POSITION = -2800; // was -2800 at NECMP; -2775 at Pine Tree; was -2700 for UNH; 3100 on 3 March; 3000 on 4 March
    private static final int NUDGE_DISTANCE = 25;  // was 100 on first day of UNH
    
	private static final int ARMED_ENCODER_POSITION = 0;
    private static final int ARMED_ENCODER_TOLERANCE = 10; // RJD why do we have this?  Why not adjust NO_SLACK_ENCODER_POSITION?
    
    public void ManualWinchControl() {
    	if( state == READY) {
    		setMotorPower(Robot.oi.getWinchControl());
    	}
    }
    
    public void periodic() {

    	switch (state) {
    	case WAIT_FOR_CLAW_TO_OPEN:
    		if (timeoutTransition(clawOpenDelay)) {
    			// after claw is open, next state will be "LAUNCHING"
    			setSolenoid(SET_POSITION_LAUNCH);
    			startTimeout();
//    			state = LAUNCHING;
    			// temporary change to "nudge every time" with the below:
    			// (to undo, comment out the three lines below, and uncomment first line above)
    			state = NUDGE_AFTER_FAILED_LAUNCH_PART_1;
//        		setMotorPower(CALIBRATE_POWER);
//    			startNudgePosition = m_winchMotorTalon.getPosition();
    		}
    		break;

    	case LAUNCHING:    
    		// if we've calibrated we can look at the encoder position to figure out if we've launched
    		if (calibrated && (m_winchMotorTalon.getPosition() <= NO_SLACK_ENCODER_POSITION)) {
    			// launch has occurred, go to a state to wait a short while before rewinding
    			state = WAIT_AFTER_LAUNCH;
    			startTimeout();
    		}
    		
    		if (timeoutTransition(launchTimeout)) {
    			// we've timed out -- two situations depending upon whether or not we've previously calibrated
    			if (calibrated) {
    				// we have calibrated, so this means we haven't fired; go to a "nudge" state
        			state = NUDGE_AFTER_FAILED_LAUNCH_PART_1;
            		setMotorPower(CALIBRATE_POWER);
        			startNudgePosition = m_winchMotorTalon.getPosition();
        			startTimeout();
    			} else {
    				// we haven't previously calibrated; assume we must have launched
        			state = WAIT_AFTER_LAUNCH;
        			startTimeout();
    			}
    		}
    		break;   
	
    	case NUDGE_AFTER_FAILED_LAUNCH_PART_1:
    		// in this state, we are in the process of nudging the winch cable a bit
    		
    		// if we've calibrated we can look at the encoder position to figure out if we've launched
    		if (calibrated && (m_winchMotorTalon.getPosition() <= NO_SLACK_ENCODER_POSITION)) {
    			// launch has occurred, go to a state to wait a short while before rewinding
    			state = WAIT_AFTER_LAUNCH;
        		setMotorPower(0);
    			startTimeout();
    		}
    		
    		// stop nudging after we turn an extra NUDGE_DISTANCE counts or if we timeout
    		if (timeoutTransition(nudgeTimeout) ||
    				(m_winchMotorTalon.getPosition() > (startNudgePosition + NUDGE_DISTANCE))) {
    			// finished our nudge, turn off the motor to hopefully fire
        		setMotorPower(0);
        		state = NUDGE_AFTER_FAILED_LAUNCH_PART_2;
    			startTimeout();
    		}
    		break;
    	
    	case NUDGE_AFTER_FAILED_LAUNCH_PART_2:
    		// we just stopped nudging; if we don't fire after another timeout, quit trying
    		if (timeoutTransition(nudgeTimeout)) {
    			state = WAIT_AFTER_LAUNCH;
    			startTimeout();
    		}
    		break;
    	    
    	case WAIT_AFTER_LAUNCH:    
    		setMotorPower(0);
    		if (timeoutTransition(timeToWaitAfterLaunch)) {
    			// after firing complete, open the centering hands
    			Robot.claw.setCenteringPistons(Claw.CENTERING_PISTONS_APART);
    			  	
    			// ensure that autonomousPermissionToLaunch is false, since just fired!
    			m_autonomousPermissionToLaunch = false;
    			m_shotJustFired = true;
    			
    			// switch to a new state
    			if (calibrated) {
    				setMotorPower(WIND_POWER);
    				state = REWINDING;
    			} else {
    				state = START_CALIBRATING;
    			}
    			setSolenoid(SET_POSITION_WIND);
    			startTimeout();
    		}
    		break;   

    	case START_CALIBRATING:
    		// this state prepares to "calibrate" by ensuring the winch is engaged and then
    		// turning the motor on to take slack out of the cord
    		setSolenoid(WINCH_ENGAGED);
    		setMotorPower(TENSION_STRING_POWER);
    		if ((m_winchMotorTalon.getSpeed() > 10) || timeoutTransition(2.0))
    		{
    			startTimeout();
    			state = CALIBRATING;
    			loopsStationary = 0;
    		}
    		break;

    	case CALIBRATING:
    		// this state is when the winch is gently rewinding the
    		// cord at a low power and waiting for all of the slack to
    		// be removed from the cord

    		// leave this state when the winch is no longer moving
    		if (m_winchMotorTalon.getSpeed() < 1) 
    		{
    			// this is point where all "slack" is just barely removed from the winch cable
    			// set the encoder value at this "no slack" position
    			if (loopsStationary == 0) 
    			{
    				m_winchMotorTalon.setEncPosition(NO_SLACK_ENCODER_POSITION);
    			}
    			loopsStationary++;
    		} 
    		else // motor is moving, reset the stationary counts.
    		{
    			loopsStationary = 0;
    		}

    		if (loopsStationary > 10) {
    			// Note:  need to wait for 10 loops (200ms) after calling "setEncPosition()" to
    			// ensure that the encoder has been set to the new value.
    			DriverStation.reportError("Loops: " + loopsStationary + "  Enc: " +
    					m_winchMotorTalon.getPosition(), false);
    			startTimeout();
    			setMotorPower(CALIBRATE_POWER);
    			// KBS NOTE: Below line commented out for debugging to force a re-calibration
    			//           after every shot.  Should eventually be commented out once we are
    			//           convinced that calibration is repeatable.
    			// RJD NOTE: from any starting position, fire the launcher and
    			//           this state machine will rewind and re-arm the launcher.
    			calibrated = true;
    			state = REWINDING;
    		}
    		break;

    	case REWINDING:                 
    		// the encoder counts up when winching back.
    		if( (m_winchMotorTalon.getPosition() >= ARMED_ENCODER_POSITION - ARMED_ENCODER_TOLERANCE) ||
    				isRetracted() ||                 
    				timeoutTransition(3)) 
    		{    			
    			setMotorPower(0);
    			startTimeout();
    			launch = false;
    			state = WAIT_AFTER_REWIND;
    			// after rewinding, close the claw again
    			Robot.claw.setClawPosition(Claw.CLAW_CLOSED);
    		}
    		break;

    	case WAIT_AFTER_REWIND:
    		//we are doing this if statement so that you cannot fire twice in a row. 
    		//if we do not have this we will fire twice in a row.(while the button is pressed)
    		if (! Robot.oi.permissionToLaunch()) {
    			Robot.drive.resetLoopsOnTarget();
    			state = READY;	
    		}
    		break; 
    		
    	case READY:
    		launch = false;
    		m_shotJustFired = false;
    		
    		// turn off the harvester if "permission to launch" has been granted
    		// whether acting in teleop or autonomous
    		if (Robot.oi.permissionToLaunch() || m_autonomousPermissionToLaunch) {
    			Robot.claw.setHarvester(Claw.HARVESTER_OFF);
    		}

    		// handle "permission to fire" shooting in auto
    		if (m_autonomousPermissionToLaunch &&  
    				Robot.drive.isAlignedToTarget(Robot.drive.AIM_REQUIRED_LOOPS_ON_TARGET_AUTONOMOUS)) {
    			launch = true;
    		}

    		// handle "permission to fire" shooting in teleop
    		if (Robot.oi.permissionToLaunch() &&  
    				Robot.drive.isAlignedToTarget(Robot.drive.AIM_REQUIRED_LOOPS_ON_TARGET_TELEOP)) {
    			launch = true;
    		}
    		
    		// handle teleop "force launch" shooting
    		if (Robot.oi.forceLaunch()) {
    			launch = true;
    		}

    		if (launch) {
    			if (Robot.claw.isClawOpen()) {
    				clawOpenDelay = 0.050;
    			} else {
    				clawOpenDelay = 0.050;
    			}
    			Robot.claw.setClawPosition(Claw.CLAW_OPEN);
    			startTimeout();
    			state = WAIT_FOR_CLAW_TO_OPEN;
    		}
    		break; 
    	} 
//    	ManualWinchControl();
    }
    
    public void updateSmartDashboard() {
    	SmartDashboard.putNumber("Launcher Enc", getEncoder());
    	SmartDashboard.putNumber("Launcher State", state);
      	SmartDashboard.putNumber("Launcher Winch Power", m_winchMotorTalon.get());
      	SmartDashboard.putNumber("Launcher Winch Speed", m_winchMotorTalon.getSpeed());
          	
    }
    
    private void startTimeout() {
        lastTransitionTime = Timer.getFPGATimestamp();
    }
    
    private boolean timeoutTransition(double seconds) {
        return lastTransitionTime + seconds <= Timer.getFPGATimestamp();
    }
    
    public void setSolenoid(boolean set) {
        launchSolenoid.set(set);
    }
    
    // initDefaultCommand() method is required for all subsystems
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        
    }

}


