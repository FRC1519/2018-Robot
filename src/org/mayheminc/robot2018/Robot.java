
package org.mayheminc.robot2018;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.*;

import org.mayheminc.robot2018.commands.RunAutonomous;
import org.mayheminc.robot2018.subsystems.*;
import org.mayheminc.util.PidTuner;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot { //FRCWaitsForIterativeRobot
	static NetworkTable table;

	public static final boolean DEBUG = true;
	public static final boolean PRACTICE_BOT = false;

	// create commands to be invoked for autonomous and teleop
	private Command autonomousCommand;
	private Runtime runtime = Runtime.getRuntime();

	// create the robot subsystems
	public static final Compressor compressor = new Compressor();
	public static final Drive drive = new Drive();
	public static final PowerDistributionPanel pdp = new PowerDistributionPanel();

	public static GameData gameData = new GameData();
	public static Intake intake = new Intake();
	public static Pivot pivot = new Pivot();
	public static Elevator elevator = new Elevator();
	public static ElevatorArms elevatorArms = new ElevatorArms();
	public static Targeting targeting = new Targeting();
	public static CubeDetector cubeDetector = new CubeDetector();
	public static Turret turret = new Turret();
	public static BlackBox blackbox = new BlackBox();
	public static Shifter shifter = new Shifter();
	public static Climber climber = new Climber();

	// allocate the "virtual" subsystems; wait to construct these until
	// robotInit()
	public static Autonomous autonomous;
	public static OI oi;

	// autonomous start time monitoring
	private static long autonomousStartTime;
	private static boolean printAutoElapsedTime = false;

	public static final double BROWNOUT_VOLTAGE_LOWER_THRESHOLD = 10.0;
	public static final double BROWNOUT_VOLTAGE_UPPER_THRESHOLD = 11.0;
	public static final double BROWNOUT_REST_PERIOD = 3;
	public static boolean brownoutMode = false;

	public static final boolean UPDATE_AUTO_SETUP_FIELDS = true;
	public static final boolean DONT_UPDATE_AUTO_SETUP_FIELDS = false;

	public Robot() {

	}

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		table = NetworkTableInstance.getDefault().getTable("datatable");

		System.out.println("robotInit");
		SmartDashboard.putString("Auto Prog", "Initializing...");

		System.out.println("About to DriverStation.reportError for 'Initializing...'");
		DriverStation.reportError("Initializing... ", false);
		System.out.println("Done with DriverStation.reportError for 'Initializing...'");

		DriverStation.reportError("About to construct Autonomous Subsystem... \n", false);
		autonomous = new Autonomous();
		DriverStation.reportError("Done constructing Autonomous Subsystem.\n", false);

		DriverStation.reportError("About to construct OI... \n", false);
		oi = new OI();
		DriverStation.reportError("Done constructing OI.\n", false);

		// initialize the subsystems that need it
		drive.init();

		// instantiate the command used for the autonomous period
		autonomousCommand = new RunAutonomous();
		DriverStation.reportWarning("Constructed auto command.\n", false);
		SmartDashboard.putString("Auto Prog", "Done.");
		Autonomous.updateSmartDashboard();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {
		
		// for safety reasons, change the elevator and cturret setpoints to the current position
		turret.setDesiredPosition(turret.getCurrentPosition());
		elevator.holdCurrentPosition();
		
		if (printAutoElapsedTime) {
			double autonomousTimeElapsed = (double) (System.currentTimeMillis() - autonomousStartTime) / 1000.0;
			DriverStation.reportError("Autonomous Time Elapsed: " + autonomousTimeElapsed + "\n", false);
			printAutoElapsedTime = false;
		}	   
		
		// print the blackbox.
		blackbox.print();
		
		// reset the blackbox.
		blackbox.reset();
	}

	public void disabledPeriodic() {
		// update sensors that need periodic update
//		targeting.periodic();
		cubeDetector.periodic();
		pivot.UpdateSmartDashboard();

		Scheduler.getInstance().run();

		// update Smart Dashboard, including fields for setting up autonomous operation
		updateSmartDashboard(UPDATE_AUTO_SETUP_FIELDS);
		//		System.out.println("disale P");
		Robot.drive.updateHistory();

		PrintPeriodicPeriod();
	}

	public void autonomousInit() {
		
		//force low gear
		shifter.setShifter(Shifter.LOW_GEAR);

		// turn off the compressor
		// KBS:  Not sure we really want to do this -- we did this in 2016 to ensure the compressor
		//       didn't affect the operation of the autonomous programs.  Not sure we really want this.
		//       At the least, we can take advantage of the last few seconds in autonomous by turning
		//       on the compressor at the end of our autonomous programs instead of waiting for the
		//       teleopInit to be called at the start of teleop.
		compressor.stop();

		
		// "Zero" the robot subsystems which have position encoders in this section.
		// Overall strategy for zeroing subsystems is as follows:
		// Every time autonomous starts:
		//    "Zero" the heading gyro using the drive subsystem
		//    "Zero" the elevator, presuming it is down due to gravity
		//    "Zero" the turret, presuming it is pointing straight forward
		//    "Zero" the arm/pivot, which will initiate finding zero by using hard stop

		// zero the drive base gyro at current position
		drive.zeroHeadingGyro(0.0);		
		
		// zero the elevator at current position
		// this presumes the elevator is down due to gravity
		elevator.Zero();

		// zero the turret at current position
		turret.zeroEncoder();

		// set the current arm/pivot position to the upright position
		pivot.commenceZeroingPivot();   // Note:  this will initiate a sequence to move the arm/pivot to vertical

		
		// where ever the pivot is, lock it there.
		// KBS:  think we don't want to do this before zeroing the pivot, which will
        //       require some time in the future.  Commenting out til RJD and KBS discuss
		// pivot.LockCurrentPosition();

		
		
		// schedule the autonomous command (example)
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
		autonomousStartTime = System.currentTimeMillis();
		printAutoElapsedTime = true;

		DriverStation.reportError(
				"AutonomousTimeRemaining from autonomousInit = " + Robot.autonomousTimeRemaining() + "\n", false);
	}

	public static double autonomousTimeRemaining() {
		double autonomousTimeElapsed = (double) (System.currentTimeMillis() - autonomousStartTime) / 1000.0;
		return (15.0 - autonomousTimeElapsed);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		// update sensors that need periodic update
		// KBS:  Why isn't this list the same as the list in teleop?  Should a function be called to
		//       share this code?
//		targeting.periodic();
		cubeDetector.periodic();

		Scheduler.getInstance().run();

		updateSmartDashboard(DONT_UPDATE_AUTO_SETUP_FIELDS);
		Robot.drive.updateHistory();
	}

	public void teleopInit() {
	
		// before doing anything else in teleop, kill any existing commands
		Scheduler.getInstance().removeAll();
		
		// Safety measures:
				// KBS: When commencing teleop, we want to make sure "position-controlled" subsystems don't
				//      try to move immediately upon enabling the robot.  
		// Where ever the pivot, turret, and elevator are, hold them there.
		pivot.holdCurrentPosition();
		turret.holdCurrentPosition();
		elevator.holdCurrentPosition();
		
		// turn on the compressor
		compressor.start();

		// NOTE:  BELOW SHOULD BE OBE WITH above Scheduler.getInstance().removeAll();
//		// This makes sure that the autonomous stops running when
//		// teleop starts running. If you want the autonomous to
//		// continue until interrupted by another command, remove
//		// this line or comment it out.
//		if (autonomousCommand != null) {
//			autonomousCommand.cancel();
//		}

		DriverStation.reportError("Entering Teleop.\n", false);
		
		shifter.setGear(Shifter.LOW_GEAR);
	}

	/**
	 * This function is called periodically during operator control
	 */
	boolean teleopOnce = false;

	static double teleopTimer = 0;
	void PrintPeriodicPeriod()
	{
		double timer = Timer.getFPGATimestamp();

		//DriverStation.reportWarning("teleop: " + (timer-teleopTimer), false);
		//System.out.println("periodic: " + (timer-teleopTimer));
		teleopTimer = timer;		
	}

	public void teleopPeriodic() {
		PrintPeriodicPeriod();

		if (!teleopOnce) {
			DriverStation.reportError("Teleop Periodic is running!", false);
		}
		teleopOnce = true;

		// update sensors that need periodic update
		cubeDetector.periodic();
		elevator.periodic();
		elevatorArms.periodic();
		intake.periodic();
		pivot.periodic();
//		targeting.periodic();
		turret.periodic();

		Scheduler.getInstance().run();

		if (!oi.autoInTeleop()) {
			if (drive.isSpeedRacerDrive()) {
				drive.speedRacerDrive(oi.driveThrottle(), oi.steeringX(), oi.quickTurn());
			} else {
				drive.tankDrive(oi.tankDriveLeft(), oi.tankDriveRight());
			}
		} else {
		}

		updateSmartDashboard(DONT_UPDATE_AUTO_SETUP_FIELDS);

		Robot.shifter.updateAutoShift();
		Robot.drive.updateHistory();
	}

	public static boolean getBrownoutMode() {
		return brownoutMode;
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
	}

	private long SMART_DASHBOARD_UPDATE_INTERVAL = 250;
	private long nextSmartDashboardUpdate = System.currentTimeMillis();

	public void updateSmartDashboard(boolean updateAutoFields) {
		try {
			if (System.currentTimeMillis() > nextSmartDashboardUpdate) {

				this.updateSmartDashboard();

				cubeDetector.updateSmartDashboard();
				drive.updateSmartDashboard();
				elevator.updateSmartDashboard();
				elevatorArms.updateSmartDashboard();
				intake.updateSmartDashboard();
				pivot.UpdateSmartDashboard();
//				targeting.updateSmartDashboard();
				turret.updateSmartDashboard();

				OI.pidTuner.updateSmartDashboard();

				if (updateAutoFields) {
					Autonomous.updateSmartDashboard();
				}

				nextSmartDashboardUpdate += SMART_DASHBOARD_UPDATE_INTERVAL;
			}
		} catch (Exception e) {
			return;
		}
	}

	void updateSmartDashboard() {
		// display free memory for the JVM
		double freeMemoryInKB = runtime.freeMemory() / 1024;
		SmartDashboard.putNumber("Free Memory", freeMemoryInKB);

		SmartDashboard.putNumber("Battery Voltage", pdp.getVoltage());
		//			SmartDashboard.putBoolean("FRC Comm Checked In", oi.IsCheckedInWithFieldManagement());

		SmartDashboard.putString("Game Data",  gameData.toString());

	}


	public static boolean getBrownoutLowerThreshold() {
		return (pdp.getVoltage() < BROWNOUT_VOLTAGE_LOWER_THRESHOLD);
	}

	public static boolean getBrownoutUpperThreshold() {
		return (pdp.getVoltage() > BROWNOUT_VOLTAGE_UPPER_THRESHOLD);

	}
}
