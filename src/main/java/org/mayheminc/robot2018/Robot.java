
package org.mayheminc.robot2018;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.*;

import org.mayheminc.robot2018.commands.RunAutonomous;
import org.mayheminc.robot2018.subsystems.*;
import org.mayheminc.util.Utils;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot /* IterativeRobot */ { //FRCWaitsForIterativeRobot
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
	private static final double LOOP_TIME = 0.020;    // The duration of our periodic timed loop, in seconds.  0.020 gives 50 loops/sec

	public static final double BROWNOUT_VOLTAGE_LOWER_THRESHOLD = 10.0;
	public static final double BROWNOUT_VOLTAGE_UPPER_THRESHOLD = 11.0;
	public static final double BROWNOUT_REST_PERIOD = 3;
	public static boolean brownoutMode = false;

	public static final boolean UPDATE_AUTO_SETUP_FIELDS = true;
	public static final boolean DONT_UPDATE_AUTO_SETUP_FIELDS = false;

	public Robot() {
		super(LOOP_TIME);    // construct a TimedRobot with a timeout of "LOOP_TIME"
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

		// // Turn off LiveWindow reporting to avoid appearance of "ERROR 1 CTRE CAN Receive Timeout" messages on concole.
		// // This fix is documented in section 4.7 of the CTRE PDP User's Guide:
		// // http://www.ctr-electronics.com/downloads/pdf/PDP%20User's%20Guide.pdf
		
		// // The CAN Receive Timeout occurs when the requested data has not been received within the timeout.
		// // This is usually caused when the PDP is not connected to the CAN bus.
		// // However, with the 2018 version of WPILib, having a PDP object in robot code can result in a
		// // CTRE CAN Timeout error being reported in the console/DriverStation.
		// // This seems to be a result of automatic LiveWindow behavior with the PDP and can be fixed by
		// // disabling LiveWindow telemetry.  LiveWindow can either be disabled for the PDP object…
		// LiveWindow.disableTelemetry(pdp);
		// // … or by disabling all LiveWindow telemetry:
		LiveWindow.disableAllTelemetry();
		

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
		
		// // print the blackbox.
		// blackbox.print();
		
		// // reset the blackbox.
		// blackbox.reset();
	}
	
	private double dpTime0 = 0.0;
	private double dpTime1 = 0.0;
	private double dpTime2 = 0.0;
	private double dpTime3 = 0.0;
	private double dpTime4 = 0.0;
	private double dpTime5 = 0.0;
	private int dpWaitLoops = 0;
	private int dpLoops = 0;
	
	private double dpElapsed1 = 0.0;
	private double dpElapsed2 = 0.0;
	private double dpElapsed3 = 0.0;
	private double dpElapsed4 = 0.0;
	private double dpElapsed5 = 0.0;
	
	public void disabledPeriodic() {
		
		dpWaitLoops++;
		if (dpWaitLoops > (10.0 * 1.0/LOOP_TIME)) {
			dpLoops++;
			dpTime0 = Timer.getFPGATimestamp();
		
			// update sensors that need periodic update
			cubeDetector.periodic();
			Scheduler.getInstance().run();


			cubeDetector.updateSmartDashboard();	
			
			dpTime1 = Timer.getFPGATimestamp();
			dpElapsed1 = dpElapsed1 + dpTime1 - dpTime0;
			
			drive.updateSmartDashboard();

			dpTime2 = Timer.getFPGATimestamp();
			dpElapsed2 = dpElapsed2 + dpTime2 - dpTime1;
			
			// update Smart Dashboard, including fields for setting up autonomous operation
			// updateSmartDashboard(UPDATE_AUTO_SETUP_FIELDS);

			elevator.updateSmartDashboard();
			elevatorArms.updateSmartDashboard();

			pivot.UpdateSmartDashboard();

			dpTime3 = Timer.getFPGATimestamp();
			dpElapsed3 = dpElapsed3 + dpTime3 - dpTime2;
			
			intake.updateSmartDashboard();
			turret.updateSmartDashboard();

			// PrintPeriodicPeriod();
			dpTime4 = Timer.getFPGATimestamp();
			dpElapsed4 = dpElapsed4 + dpTime4 - dpTime3;
		
			if (OI.pidTuner != null) {
				OI.pidTuner.updateSmartDashboard();
			}

			Autonomous.updateSmartDashboard();

			Robot.drive.updateHistory();
			
			if ((dpLoops % 40) == 0) {
				System.out.println(
				"dp1: " + dpElapsed1/dpLoops + 
				"   dp2: " + dpElapsed2/dpLoops +
				"   dp3: " + dpElapsed3/dpLoops +
				"   dp4: " + dpElapsed4/dpLoops
				);
			}
		}
		
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

	static double periodicTimer = 0;
	static int periodicLoops = 0;
	static String periodicOutputString = "periodic: ";
	void PrintPeriodicPeriod()
	{
		double timer = Timer.getFPGATimestamp();
		double elapsed = timer-periodicTimer;

		// add a timing figure to the printout, but only if we're too slow
		if ( elapsed <= (LOOP_TIME + 0.002) ) {
			periodicOutputString = periodicOutputString + "* ";
		} else {
			periodicOutputString = periodicOutputString +  Utils.threeDecimalPlaces(elapsed) + " ";
		}
		// DriverStation.reportWarning("periodic: " + (timer-periodicTimer), false);
		if ((periodicLoops % 50) == 1) {
			// System.out.println("periodic: " + Utils.threeDecimalPlaces(timer-periodicTimer));
			periodicOutputString = Utils.threeDecimalPlaces(timer) + " " + periodicOutputString;
			System.out.println(periodicOutputString);
			periodicOutputString = "periodic: ";
		}
		periodicLoops++;
		periodicTimer = timer;		
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

	private double SMART_DASHBOARD_UPDATE_INTERVAL = 0.250;   // was 0.250;
	// private long nextSmartDashboardUpdate = System.currentTimeMillis();
	private double nextSmartDashboardUpdate = Timer.getFPGATimestamp();

	public void updateSmartDashboard(boolean updateAutoFields) {
		// double currentTime = Timer.getFPGATimestamp();

			// if (currentTime > nextSmartDashboardUpdate) {

				// System.out.println("updateSmartDashboard: current: " + currentTime + " next: " + nextSmartDashboardUpdate);

				this.updateSmartDashboard();

				cubeDetector.updateSmartDashboard();
				drive.updateSmartDashboard();
				elevator.updateSmartDashboard();
				elevatorArms.updateSmartDashboard();
				intake.updateSmartDashboard();
				pivot.UpdateSmartDashboard();
				turret.updateSmartDashboard();

				if (OI.pidTuner != null) {
					OI.pidTuner.updateSmartDashboard();
				}

				if (updateAutoFields) {
					Autonomous.updateSmartDashboard();
				}

				// nextSmartDashboardUpdate += SMART_DASHBOARD_UPDATE_INTERVAL;
			// }
	}

	void updateSmartDashboard() {
		// display free memory for the JVM
		double freeMemoryInKB = runtime.freeMemory() / 1024;
		SmartDashboard.putNumber("Free Memory", freeMemoryInKB);

		// SmartDashboard.putNumber("Battery Voltage", pdp.getVoltage());
		//			SmartDashboard.putBoolean("FRC Comm Checked In", oi.IsCheckedInWithFieldManagement());

		SmartDashboard.putString("Game Data",  gameData.toString());

	}


	// public static boolean getBrownoutLowerThreshold() {
	// 	return (pdp.getVoltage() < BROWNOUT_VOLTAGE_LOWER_THRESHOLD);
	// }

	// public static boolean getBrownoutUpperThreshold() {
	// 	return (pdp.getVoltage() > BROWNOUT_VOLTAGE_UPPER_THRESHOLD);
	// }
}
