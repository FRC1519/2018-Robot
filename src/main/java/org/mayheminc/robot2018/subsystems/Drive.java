/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mayheminc.robot2018.subsystems;

import com.kauailabs.navx.frc.*;
import org.mayheminc.util.History;

import edu.wpi.first.wpilibj.*;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.ControlMode;

//import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
//import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import java.util.Arrays;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.util.MB1340Ultrasonic;
import org.mayheminc.util.MayhemTalonSRX;
import org.mayheminc.util.Utils;

public class Drive extends Subsystem {
	
	History headingHistory = new History();
	
	// Brake modes
	public static final boolean BRAKE_MODE = true;
	public static final boolean COAST_MODE = false;

	// PID loop variables
	private PIDController m_HeadingPid;
	private PIDHeadingError m_HeadingError;
	private PIDHeadingCorrection m_HeadingCorrection;
	private boolean m_HeadingPidPreventWindup = false;

	// Talons
	private final MayhemTalonSRX leftFrontTalon = new MayhemTalonSRX(RobotMap.FRONT_LEFT_TALON);
	private final MayhemTalonSRX leftRearTalon = new MayhemTalonSRX(RobotMap.BACK_LEFT_TALON);
	private final MayhemTalonSRX rightFrontTalon = new MayhemTalonSRX(RobotMap.FRONT_RIGHT_TALON);
	private final MayhemTalonSRX rightRearTalon = new MayhemTalonSRX(RobotMap.BACK_RIGHT_TALON);

	// Sensors
	private final MB1340Ultrasonic ultrasonic = new MB1340Ultrasonic(0);
	private AHRS Navx;

//	// Solenoid
//	Solenoid m_shifter;

	// Driving mode
	private boolean m_speedRacerDriveMode = true; // set by default
	// NavX parameters
	private double m_desiredHeading = 0.0;
	private boolean m_useHeadingCorrection = true;
	private static final double kToleranceDegreesPIDControl = 0.2;

	// Drive parameters
	// Todo: check gear ratio and final wheel size
	// RJD : the numbers have not been checked for 2018.
	public static final double DISTANCE_PER_PULSE = 3.14 * 6.26 * 36 / 42 / (250*4); // pi * diameter * (gear ratio) / (counts per rev) 
	private boolean m_closedLoopMode = false;
	private double m_maxWheelSpeed = 130;
	private double m_voltageRampRate = 48.0;

	int m_iterationsSinceRotationCommanded = 0;
	int m_iterationsSinceMovementCommanded = 0;
	
	/***********************************INITIALIZATION**********************************************************/

	public Drive() {
		try {
			/* Communicate w/navX MXP via the MXP SPI Bus.                                     */
			/* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
			/* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
			Navx = new AHRS(SPI.Port.kMXP);
			Navx.reset();
			System.out.println("NAVX LOADED.");
		} catch (RuntimeException ex ) {
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
			System.out.println("Error oading navx.");
		}

		// create a PID Controller that reads the heading error and outputs the heading correction.
		m_HeadingError = new PIDHeadingError();
		m_HeadingCorrection = new PIDHeadingCorrection();
//		m_HeadingPid = new PIDController(0.015, 0.001, 0.05, m_HeadingError, m_HeadingCorrection);  // values from Week Zero tournament
//RJD		m_HeadingPid = new PIDController(0.015, 0.001, 0.04, m_HeadingError, m_HeadingCorrection);  // vales from NECMP and Pine Tree
//		m_HeadingPid = new PIDController(0.02, 0.0015, 0.05, m_HeadingError, m_HeadingCorrection);

		m_HeadingPid = new PIDController(0.015, 0.000, 0.04, m_HeadingError, m_HeadingCorrection);  // vales from NECMP and Pine Tree
		m_HeadingPid.setInputRange(-180.0f, 180.0f);
		m_HeadingPid.setContinuous(true);             // treats the input range as "continous" with wrap-around
		m_HeadingPid.setOutputRange(-.50, .50); // set the maximum power to correct twist
		m_HeadingPid.setAbsoluteTolerance(kToleranceDegreesPIDControl);

		// confirm all four drive talons are in coast mode
		leftFrontTalon.setNeutralMode(NeutralMode.Coast);
		leftRearTalon.setNeutralMode(NeutralMode.Coast);
		rightFrontTalon.setNeutralMode(NeutralMode.Coast);
		rightRearTalon.setNeutralMode(NeutralMode.Coast);
		
		// set rear talons to follow their respective front talons
		leftRearTalon.changeControlMode(ControlMode.Follower);
		leftRearTalon.set(leftFrontTalon.getDeviceID());

		rightRearTalon.changeControlMode(ControlMode.Follower);
		rightRearTalon.set(rightFrontTalon.getDeviceID());
	}

	public void init() {
		// reset the NavX
		zeroHeadingGyro(0.0);

		// talon closed loop config	
		configureCanTalon(leftFrontTalon);
		configureCanTalon(rightFrontTalon);
	}

	public void zeroHeadingGyro(double headingOffset) {
		Navx.zeroYaw();
		setHeadingOffset(headingOffset);

		DriverStation.reportError("heading immediately after zero = " + getHeading() + "\n", false);

		m_desiredHeading = 0.0;

		SmartDashboard.putString("Trace", "Zero Heading Gyro");

		// restart the PID controller loop
		m_HeadingPid.reset();
		m_HeadingPid.enable();
	}

	public void initDefaultCommand() {
		//      setDefaultCommand(new SpeedRacerDrive());
	}

	private void configureCanTalon(MayhemTalonSRX talon)
	{
		double wheelP = 1.5;
		double wheelI = 0.0;
		double wheelD = 0.0;
		double wheelF = 1.0;

		//		wheelP = RobotPreferences.getWheelP();
		//		wheelI = RobotPreferences.getWheelI();
		//		wheelD = RobotPreferences.getWheelD();
		//		wheelF = RobotPreferences.getWheelF();
		
		talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		
		// Note:  comment out below line so that encoder units are forced to be in counts (x4)
        //  talon.configEncoderCodesPerRev(360);

		talon.reverseSensor(false);
		talon.configNominalOutputVoltage(+0.0f, -0.0f);
		talon.configPeakOutputVoltage(+12.0, -12.0);

		if (m_closedLoopMode) {
			talon.changeControlMode(ControlMode.Velocity);
			m_maxWheelSpeed = 270;
		} else {
			talon.changeControlMode(ControlMode.PercentOutput);
			m_maxWheelSpeed = 1.0;
		}

		talon.setPID(wheelP, wheelI, wheelD, wheelF, 0, m_voltageRampRate, 0);
		
		
//		talon.enableControl();

		DriverStation.reportError("setWheelPIDF: " + wheelP + " " + wheelI
				+ " " + wheelD + " " + wheelF + "\n", false);
	}

	/**
	 * Set the "Brake Mode" behavior on the drive talons when "in neutral"
	 * @param brakeMode - true for "brake in neutral" and false for "coast in neutral"
	 */
	public void setBrakeMode(boolean brakeMode) {
//		leftFrontTalon.enableBrakeMode(brakeMode);
//		leftRearTalon.enableBrakeMode(brakeMode);
//		rightFrontTalon.enableBrakeMode(brakeMode);
//		rightRearTalon.enableBrakeMode(brakeMode);
		
		NeutralMode mode = (brakeMode)?NeutralMode.Brake : NeutralMode.Coast;
		
		leftFrontTalon.setNeutralMode(mode);
		leftRearTalon.setNeutralMode(mode);
		rightFrontTalon.setNeutralMode(mode);
		rightRearTalon.setNeutralMode(mode);
	}

	//***********************************CLOSED-LOOP MODE**********************************************************

	public void toggleClosedLoopMode() {
		if (!m_closedLoopMode) {
			setClosedLoopMode();
		} else {
			setOpenLoopMode();
		}
	}

	public void setClosedLoopMode() {
		m_closedLoopMode = true;
		// reconfigure the "master" drive talons now that we're in closed loop mode
		configureCanTalon(leftFrontTalon);
		configureCanTalon(rightFrontTalon);
	}

	public void setOpenLoopMode() {
		m_closedLoopMode = false;
		// reconfigure the "master" drive talons now that we're in closed loop mode
		configureCanTalon(leftFrontTalon);
		configureCanTalon(rightFrontTalon);
	}

	//***********************************ENCODER- GETTERS**********************************************************

	public double distanceTravelledMeters() {
		//double y = Navx.getDisplacementY();
		double x = Navx.getDisplacementX();

		//Pythagorean theorem
		//return Math.sqrt(y * y + x * x);

		// assume linear motion only
		return x;
	}
	public int getRightEncoder(){
//		return (int) rightFrontTalon.getPosition();
		return rightFrontTalon.getSelectedSensorPosition(0);
	}

	public int getLeftEncoder(){
//		return (int) leftFrontTalon.getPosition();
		return leftFrontTalon.getSelectedSensorPosition(0);
	}

	// speed is in inches per second
	public double getRightSpeed(){
//		return rightFrontTalon.getSpeed();
		return rightFrontTalon.getSelectedSensorVelocity(0);
	}

	public double getLeftSpeed() {
//		return leftFrontTalon.getSpeed();
		return leftFrontTalon.getSelectedSensorVelocity(0);
	}

	//***********************************GYRO**********************************************************

	public double calculateHeadingError(double Target) {
		double currentHeading = getHeading();
		double error = Target - currentHeading;
		error = error % 360.0;
		if (error > 180.0) {
			error -= 360.0;
		}
		return error;
	}

	public boolean getHeadingCorrectionMode(){
		return m_useHeadingCorrection;
	}
	public void setHeadingCorrectionMode(boolean useHeadingCorrection){
		m_useHeadingCorrection = useHeadingCorrection;
	}   

	static private final double STATIONARY = 0.1;
	static private double m_prevLeftDistance = 0.0;
	static private double m_prevRightDistance = 0.0;

	public boolean isStationary() {		
        double leftDistance = getLeftEncoder();
        double rightDistance = getRightEncoder();

		double leftDelta = Math.abs(leftDistance - m_prevLeftDistance);
		double rightDelta = Math.abs(rightDistance - m_prevRightDistance);

		m_prevLeftDistance = leftDistance;
		m_prevRightDistance = rightDistance;

		return leftDelta < STATIONARY && rightDelta < STATIONARY;
	}

	public void displayGyroInfo() {
		SmartDashboard.putNumber("Gyro Heading", Utils.twoDecimalPlaces(getHeading()));
		SmartDashboard.putNumber("Gyro Roll", Utils.twoDecimalPlaces(Navx.getRoll()));
		SmartDashboard.putNumber("Gyro Pitch", Utils.twoDecimalPlaces(Navx.getPitch()));
		SmartDashboard.putNumber("Loop Counter", LoopCounter++);
		SmartDashboard.putNumber("Navx distance" ,Utils.twoDecimalPlaces(this.distanceTravelledMeters()*100));
		SmartDashboard.putNumber("Navx distance Y" ,Utils.twoDecimalPlaces(Navx.getDisplacementY()*100));
		SmartDashboard.putNumber("Navx distance X" ,Utils.twoDecimalPlaces(Navx.getDisplacementX()*100));
	}

	private double m_headingOffset = 0.0;
	public void setHeadingOffset(double arg_offset){
		m_headingOffset = arg_offset;
	}
	
	public double getHeading() {
		return Navx.getYaw() + m_headingOffset;
	}
	public double getTilt(){
		return Navx.getPitch();
	}

	public double getDesiredHeading(){
		return m_desiredHeading;
	}

	//**********************************SETTING POWER TO MOTORS**********************************************************

	private void simpleDrive(double leftPower, double rightPower) {
		// Note that due to the way that the Y axis on the default joysticks
		// give a "-1" when pressed forward, and a "+1" when pulled backward,
		// it is a historical vestige that this function will result in the
		// robot going forward when the power arguments are negative
		if (rightPower >  1.0) { rightPower =  1.0;}
		else if (rightPower < -1.0) { rightPower = -1.0;}
		if (leftPower  >  1.0) { leftPower  =  1.0;}
		else if (leftPower  < -1.0) { leftPower  = -1.0;}
		setMotorPower(leftPower, rightPower);
	}

	// for a "positive means forward" alternative to simpleDrive, use
	// the below "positiveSimpleDrive" method instead
	public void positiveSimpleDrive(double leftPower, double rightPower) {
		simpleDrive(-leftPower, -rightPower);
	}

	public void resetNavXDisplacement(){
		Navx.resetDisplacement();
	}

	public void stop() {
		setMotorPower(0, 0);
	}

	public void recalibrateHeadingGyro() {
		//        headingGyro.free();
		//        headingGyro = new AnalogGyro(RobotMap.HEADING_GYRO);
		//Navx.free();
		//Navx = new AHRS(SPI.Port.kMXP); 
	}

	private int LoopCounter = 0;

	private void setMotorPower(double leftPower, double rightPower) {
//		rightFrontTalon.set(-rightPower * m_maxWheelSpeed);
//		leftFrontTalon.set(leftPower * m_maxWheelSpeed);
		rightFrontTalon.set(ControlMode.PercentOutput, -rightPower * m_maxWheelSpeed);
		leftFrontTalon.set(ControlMode.PercentOutput, leftPower * m_maxWheelSpeed);
	}

	public void set(double rightPower, double leftPower){
		if (rightPower > 1) { rightPower = 1; }
		if (rightPower < -1) { rightPower = -1; }

		if (leftPower > 1) { leftPower = 1; }
		if (leftPower < -1) { leftPower = -1; }

//		rightFrontTalon.set(rightPower);
//		leftFrontTalon.set(leftPower);
		rightFrontTalon.set(ControlMode.PercentOutput, -rightPower * m_maxWheelSpeed);
		leftFrontTalon.set(ControlMode.PercentOutput, leftPower * m_maxWheelSpeed);
	}
	
	PowerDistributionPanel pdp = new PowerDistributionPanel();

	/**
	 * updateSdbPdp
	 * Update the Smart Dashboard with the Power Distribution Panel currents.
	 */
	public void updateSdbPdp()
	{
		double lf;
		double rf;
		double lb;
		double rb;
		double fudgeFactor = 0.0;

		lf = pdp.getCurrent(RobotMap.DRIVE_FRONT_LEFT_PDP) - fudgeFactor;
		rf = pdp.getCurrent(RobotMap.DRIVE_FRONT_RIGHT_PDP)- fudgeFactor;
		lb = pdp.getCurrent(RobotMap.DRIVE_BACK_LEFT_PDP)- fudgeFactor;
		rb = pdp.getCurrent(RobotMap.DRIVE_BACK_RIGHT_PDP)- fudgeFactor;

		SmartDashboard.putNumber("Left Front I", lf);
		SmartDashboard.putNumber("Right Front I", rf);
		SmartDashboard.putNumber("Left Back I", lb);
		SmartDashboard.putNumber("Right Back I", rb);
	}

	/* This method allows one to drive in "Tank Drive Mode". Tank drive mode
	 * uses the left side of the joystick to control the left side of the robot,
	 * whereas the right side of the joystick controls the right side of the robot. 
	 */
	public void tankDrive(double leftSideThrottle, double rightSideThrottle) {
		if (leftSideThrottle >= 0.0) {
			leftSideThrottle = (leftSideThrottle * leftSideThrottle); // squaring inputs increases fine control
		} else {
			leftSideThrottle = -(leftSideThrottle * leftSideThrottle); // preserves the sign while squaring negative values
		}

		if (rightSideThrottle >= 0.0) {
			rightSideThrottle = (rightSideThrottle * rightSideThrottle);
		} else {
			rightSideThrottle = -(rightSideThrottle * rightSideThrottle);
		}

		positiveSimpleDrive(leftSideThrottle, rightSideThrottle);
	}

	public void speedRacerDrive(double throttle, double rawSteeringX, boolean quickTurn) {
		double leftPower, rightPower;
		double rotation = 0;
		double adjustedSteeringX = rawSteeringX * throttle;		
		final double QUICK_TURN_GAIN = 0.75;   // culver drive used 1.5
		final int LOOPS_GYRO_DELAY = 10;

		int throttleSign;
		if (throttle >= 0.0) {
			throttleSign = 1;
		} else {
			throttleSign = -1;
		}		

		if (Robot.oi.autoTarget()) {
			// shift into low gear if needed
			if (Robot.shifter.getGear() == Shifter.HIGH_GEAR) {
				Robot.shifter.setGear(Shifter.LOW_GEAR);
			}
			// we are in autoTarget mode
			if (Robot.oi.forceLowGear()) {
				// driver holding down low gear button; auto-align forwards
				autoTarget(+0.15);
			} else {
				autoTarget(-0.15);
			}
		} else {
			//we are NOT in autoTarget mode
			if (rawSteeringX == 0.0) { 
				// no turn being commanded, drive straight or stay still
				m_iterationsSinceRotationCommanded++;
				if (throttle == 0.0) {
					// no motion commanded, stay still
					m_iterationsSinceMovementCommanded++;
					rotation = 0.0;
					m_desiredHeading = getHeading();  // whenever motionless, set desired heading to current heading
					// 	reset the PID controller loop now that we have a new desired heading
					m_HeadingPid.reset();
					m_HeadingPid.enable();  // need to re-enable the PID controller after a reset()
				} else {
					// driving straight
					if ((m_iterationsSinceRotationCommanded == LOOPS_GYRO_DELAY) || 
						(m_iterationsSinceMovementCommanded >=LOOPS_GYRO_DELAY)) 
					{
//						DriverStation.reportError("drive 1",  false);
						// exactly five iterations with no commanded turn, 
						// get current heading as desired heading
						m_desiredHeading = getHeading();
						// 	reset the PID controller loop now that we have a new desired heading
						m_HeadingPid.reset();
						m_HeadingPid.enable();  // need to re-enable the PID controller after a reset()
						rotation = 0.0;
					} else if (m_iterationsSinceRotationCommanded < LOOPS_GYRO_DELAY) {
//						DriverStation.reportError("drive 2",  false);
						// just start driving straight without special heading maintenance
						rotation = 0.0;
					} else if (m_iterationsSinceRotationCommanded > LOOPS_GYRO_DELAY) {
//						DriverStation.reportError("drive 3",  false);
						// after more then five iterations since commanded turn, maintain the target heading 
						rotation = maintainHeading();
					} 
					m_iterationsSinceMovementCommanded = 0;
				}
			} else { 
				// commanding a turn, reset iterationsSinceRotationCommanded
				m_iterationsSinceRotationCommanded = 0;   
				m_iterationsSinceMovementCommanded = 0;   
				
				if (quickTurn) {
					// want a high-rate turn (also allows "spin" behavior)
					rotation = rawSteeringX * throttleSign * QUICK_TURN_GAIN;
				} else {
					// want a standard rate turn (scaled by the throttle)
					rotation = adjustedSteeringX;     // set the turn to the throttle-adjusted steering input
				}
			}

			// power to each wheel is a combination of the throttle and rotation
			leftPower = throttle + rotation;
			rightPower = throttle - rotation;
			positiveSimpleDrive(leftPower, rightPower);
		}
	}
	
	public int stage = 0;
	public final double DELAY = 4;
	public double timerStart = Timer.getFPGATimestamp();

	public boolean selfTest(){
		return false;
	}

	/**
	 * The headings are from -180 to 180.  The CurrentHeading is the current robot orientation.
	 * The TargetHeading is where we want the robot to face.
	 * 
	 * e.g.  Current = 0, Target = 10, error = 10
	 *       Current = 180, Target = -170, error = 10 (we need to turn 10 deg Clockwise to get to -170)
	 *       Current = -90, Target = 180, error = -90 (we need to turn 90 deg Counter-Clockwise to get to 180)
	 *       Current = 10, target = -10, error = -20 (we need to turn Counterclockwise -20 deg)
	 * @param CurrentHeading
	 * @param TargetHeading
	 * @return
	 */
	private double maintainHeading() {
		double headingError = calculateHeadingError(m_desiredHeading);
		m_HeadingError.m_Error = headingError;
		double headingCorrection = 0.0;

		m_HeadingError.m_Error = m_desiredHeading - getHeading();

		if (m_useHeadingCorrection) {
			headingCorrection = -m_HeadingCorrection.HeadingCorrection;
		} else {
			headingCorrection = 0.0;
		}
		
		if( Math.abs(m_HeadingError.m_Error) > 10.0 )
		{
			if (!m_HeadingPidPreventWindup) {
				m_HeadingPid.setI(0.0);		
				m_HeadingPid.reset(); // clear the wind-up.
				m_HeadingPid.enable(); // the reset turns it off.  Turn it on.
				m_HeadingPidPreventWindup = true;
			}
		}
		else
		{
			m_HeadingPidPreventWindup = false;
			m_HeadingPid.setI(0.001);
		}

		return headingCorrection;
	}

	/**
	 * Drive forward and use the compass to correct any twisting
	 * @param targetPower
	 */
	public void driveStraightForward(double targetPower){ 
		double leftPower = 0.0;
		double rightPower = 0.0;
		double headingCorrection = maintainHeading();

		rightPower = targetPower - headingCorrection;
		leftPower = targetPower + headingCorrection;

		SmartDashboard.putNumber("LeftPower :", leftPower);
		SmartDashboard.putNumber("RightPower :", rightPower);
		setMotorPower(leftPower, rightPower);
	}

	public void rotate(double RotateX)
	{
		m_desiredHeading += RotateX;
		if(m_desiredHeading > 180){
			m_desiredHeading -= 360;
		}
		if(m_desiredHeading < -180){
			m_desiredHeading += 360;
		}
		m_iterationsSinceRotationCommanded = 50; // hack so speedracerdrive maintains heading
		m_iterationsSinceMovementCommanded = 0;
	}

	public void rotateToHeading(double desiredHeading)
	{
		m_desiredHeading = desiredHeading;
	}
	
	//**********************************************DISPLAY****************************************************

	public void updateSmartDashboard(){
		displayGyroInfo();

		// ***** KBS:  Uncommenting below, as it takes a LONG time to get PDP values
		// updateSdbPdp();

//		SmartDashboard.putNumber("ultrasonicDistance", (ultrasonic.getDistance() * 0.393701));

		SmartDashboard.putNumber("Current Heading",  getHeading());

//		SmartDashboard.putNumber("yaw",  Navx.getYaw());
//		SmartDashboard.putNumber("pitch",  Navx.getPitch());
//		SmartDashboard.putNumber("angle",  Navx.getAngle());
		
		SmartDashboard.putNumber("Left Front Encoder Counts", leftFrontTalon.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Right Front Encoder Counts", rightFrontTalon.getSelectedSensorPosition(0));
//		SmartDashboard.putNumber("Left Rear Encoder Counts", leftRearTalon.getSelectedSensorPosition(0));
//		SmartDashboard.putNumber("Right Rear Encoder Counts", rightRearTalon.getSelectedSensorPosition(0));

		// Note:  getSpeed() returns ticks per 0.1 seconds
		SmartDashboard.putNumber("Left Encoder Speed", leftFrontTalon.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Right Encoder Speed", -rightFrontTalon.getSelectedSensorVelocity(0));

		// To convert ticks per 0.1 seconds into feet per second
		// a - multiply be 10 (tenths of second per second)
		// b - divide by 12 (1 foot per 12 inches)
		// c - multiply by distance (in inches) per pulse
		SmartDashboard.putNumber("Left Speed (fps)", leftFrontTalon.getSelectedSensorVelocity(0) * 10 / 12 * DISTANCE_PER_PULSE);
		SmartDashboard.putNumber("Right Speed (fps)", -rightFrontTalon.getSelectedSensorVelocity(0) * 10 / 12 * DISTANCE_PER_PULSE);

//		SmartDashboard.putNumber("Left Talon Setpoint", leftFrontTalon.getSetpoint());
//		SmartDashboard.putNumber("Right Talon Setpoint", -rightFrontTalon.getSetpoint());

		SmartDashboard.putNumber("Left Talon Error", leftFrontTalon.getError());
		SmartDashboard.putNumber("Right Talon Error", -rightFrontTalon.getError());

		SmartDashboard.putNumber("Left Talon Output Voltage", -leftFrontTalon.getOutputVoltage());
		SmartDashboard.putNumber("Right Talon Output Voltage", rightFrontTalon.getOutputVoltage());

		SmartDashboard.putBoolean("Closed Loop Mode", m_closedLoopMode);
//		SmartDashboard.putBoolean("Speed Racer Drive Mode", m_speedRacerDriveMode);
//		SmartDashboard.putBoolean("Auto Shift Mode", m_autoShift);

		SmartDashboard.putBoolean("Heading Correction Mode", m_useHeadingCorrection);
		SmartDashboard.putNumber("Heading Desired", m_desiredHeading);
		SmartDashboard.putNumber("Heading Error", m_HeadingError.m_Error);
		SmartDashboard.putNumber("Heading Correction", -m_HeadingCorrection.HeadingCorrection);
		
		SmartDashboard.putBoolean("On Target", isAlignedToTarget(AIM_REQUIRED_LOOPS_ON_TARGET_TELEOP));

		SmartDashboard.putNumber("Joystick Drive Throttle", Robot.oi.driveThrottle());
		SmartDashboard.putNumber("Joystick SteeringX", Robot.oi.steeringX());

//		SmartDashboard.putBoolean("Low Gear", !m_highGear);

		// determine currentAverageSpeed and display it
		double currentSpeed = getLeftSpeed() > getRightSpeed() ? getLeftSpeed() : getRightSpeed();
		//double currentAverageSpeed = (getLeftSpeed() + getRightSpeed()) / 2;

		// display current speed to driver
		SmartDashboard.putNumber("Current Speed",  currentSpeed);


//		SmartDashboard.putNumber("Tilt", getTilt());
	}

	//**********************************ULTRASONICS***********************************************
	public double getUltrasonicDistance() {
		return ultrasonic.getDistanceInInches();
	}

//	//**********************************SHIFTER PISTONS***********************************************
//
//	public static final boolean HIGH_GEAR = true;
//	public static final boolean LOW_GEAR = !HIGH_GEAR;
//	private boolean m_highGear = LOW_GEAR; // flag for current gear setting
//
//	public static final boolean AUTO_SHIFT = true;
//	public static final boolean MANUAL_SHIFT = false;
//	private boolean m_autoShift = true;      // flag for automatic shifting
//
//	public void setShifter(boolean position){
//		m_shifter.set(position);
//		m_highGear = position;
//	}
//
//	//private static final double LEFT_SHIFT_HIGH = 0.0;
//	//private static final double LEFT_SHIFT_LOW = 1.0;
//	//private static final double RIGHT_SHIFT_HIGH = 0.0;
//	//private static final double RIGHT_SHIFT_LOW = 1.0;
//
//	public final void setGear(boolean gear) {
//		m_priorShiftTime = Timer.getFPGATimestamp();
//		m_highGear = gear;
//		if (m_highGear == HIGH_GEAR) {
//			//            leftShiftServo.set(LEFT_SHIFT_HIGH);
//			//            rightShiftServo.set(RIGHT_SHIFT_HIGH);
//			setShifter(HIGH_GEAR);  
//			//DriverStation.reportError("High Gear", false);
//
//		} else {
//			//            leftShiftServo.set(LEFT_SHIFT_LOW);
//			//            rightShiftServo.set(RIGHT_SHIFT_LOW);
//			setShifter(LOW_GEAR);   // NOTE:  Solenoid set to false gives low gear
//			//DriverStation.reportError("Low Gear", false);
//		}
//	}
//
//	public boolean getGear() {
//		return m_highGear;
//	}
//
//	public void setAutoShift(boolean useAutoShift) {
//		m_autoShift = useAutoShift;
//	}
//
//	// shift speeds are in inches per second.
//
//	//TODO: SeanM thinks that these constants are not great for 2016 Robot.  Should be re-evaluated at large practice space.
//	//private static final double SHIFT_RATIO = 2.56;    // Gear spread is 2.56:1 in sonic shifter
//	private static final double SHIFT_TO_HIGH = 450.0;  // numbers determined empirically
//	private static final double SHIFT_TO_LOW = SHIFT_TO_HIGH / 2.56;   // numbers determined empirically
//	private static final double SHIFT_DELAY = .5;
//	private static double m_priorShiftTime = Timer.getFPGATimestamp();
//
//	public void updateAutoShift() {
//
//		// determine currentAverageSpeed and display it
//		double currentSpeed = getLeftSpeed() > getRightSpeed() ? getLeftSpeed() : getRightSpeed();
//		//double currentAverageSpeed = (getLeftSpeed() + getRightSpeed()) / 2;
//
//		// display current speed to driver
//		SmartDashboard.putNumber("Current Speed",  currentSpeed);
//
//		if (m_autoShift &&  
//				(Robot.oi.forceLowGear() ||  
//						Timer.getFPGATimestamp() > m_priorShiftTime + SHIFT_DELAY)) {    
//
//			// general approach:
//			//     if currentAverageSpeed is high enough, shift into high gear
//			//     if currentAverageSpeed is low, shift into low gear
//			// NOTE:  un-needed shifts into high could be avoided by checking
//			//        that driver still wants to go faster (i.e., commanding
//			//        significant power) when considering a shift into high
//
//			if ((!Robot.oi.forceLowGear() && Math.abs(currentSpeed) > SHIFT_TO_HIGH) &&
//					(Math.abs(Robot.oi.driveThrottle()) > 0.9)) {
//				setGear(HIGH_GEAR);
//			} else if (Robot.oi.forceLowGear() || 
//					Math.abs(currentSpeed) < SHIFT_TO_LOW) {
//				setGear(LOW_GEAR);
//			} else {
//				// don't need to shift to high or low; stay in current gear
//			}  
//		}
//
//		// display debugging information for auto-shift data collection
//		//        System.out.println("t:" + Utils.twoDecimalPlaces(Timer.getFPGATimestamp()) +
//		//                   "  g:" + ((currentGear == HIGH_GEAR) ? "H" : "L") +
//		//                   "  as:" + Utils.twoDecimalPlaces(currentAverageSpeed) + 
//		//                   "  thr:" + Utils.twoDecimalPlaces(CommandBase.oi.driveThrottle()) +
//		//                   "  v:" + Utils.twoDecimalPlaces(DriverStation.getInstance().getBatteryVoltage()));
//	}
//	
	//********************************AUTO TARGET*********************************
	
	public void autoTarget(double argPower) {
//		final double DEGREES_PER_PIXEL = (56.5 / 320.0); // Axis 206 - 56.5 degree FOV, 320 pixels
//		double correction = Robot.targeting.getCubeCenterOffset() * DEGREES_PER_PIXEL;
//		double headingForImage = Robot.targeting.getRobotHeading();
//		double targetHeading = (headingForImage + correction);
//		
//		SmartDashboard.putNumber("Auto Aim: Target Heading", targetHeading);
//		SmartDashboard.putNumber("Auto Aim: Image Header", headingForImage);
//		SmartDashboard.putNumber("Auto Aim: Target Correction", correction);
		
		// if we can see a target, update the PID Controller with the desired heading
//		if (Robot.targeting.isCubeVisible()) {
//			m_desiredHeading = targetHeading;
//		}
		
		// ask the PIDController for the adjustment needed to go to the desired heading 
//		double adjustment = maintainHeading();
//		double leftPower = argPower + adjustment;
//		double rightPower = argPower - adjustment;
//		
//		if (argPower > 0) {
//			// constrain leftPower and rightPower to be positive
//			if (leftPower <= 0) { leftPower = 0; }
//			if (rightPower <= 0) { rightPower = 0; }
//		}
//		if (argPower < 0) {
//			// constrain leftPower and rightPower to be negative
//			if (leftPower >= 0) { leftPower = 0; }
//			if (rightPower >= 0) { rightPower = 0; }			
//		}
//		positiveSimpleDrive(leftPower, rightPower);
//		SmartDashboard.putNumber("Auto Aim: Drive Adjustment", adjustment);
	}
	
	public static final int AIM_REQUIRED_LOOPS_ON_TARGET_TELEOP = 9;       // was 6 on first day of UNH; 9 on second day
	public static final int AIM_REQUIRED_LOOPS_ON_TARGET_AUTONOMOUS = 18;   // was 6 on first day of UNH; 9 on second day
	
	public void resetLoopsOnTarget(){
	}
	public boolean isAlignedToTarget(int requiredLoopsOnTarget) {
//		int diff = Math.abs(Robot.targeting.getCubeCenterOffset());
//		if (diff <= AIM_PIXEL_TOLERANCE ) {
//			m_loopsOnTarget++;
//		} else {
//			if (m_loopsOnTarget >=1) {
//				DriverStation.reportError("Aim lost after " + m_loopsOnTarget + " loops. \n", false);
//			}
//			m_loopsOnTarget = 0;
//		}
//		
//		// require multiple loops of being on target
//		return (m_loopsOnTarget >= requiredLoopsOnTarget);
		
		return false;
	}
	
	final double CAMERA_LAG = 0.2;
	
	public void updateHistory(){
		double now = Timer.getFPGATimestamp();
		headingHistory.add(now, getHeading());
	}
	
	public double getHeadingForCapturedImage(){
		double now = Timer.getFPGATimestamp();
		double indexTime = now - CAMERA_LAG;
		return headingHistory.getAzForTime(indexTime);
	}
	
//	double [] initialWheelDistance = new double[4];
//	double [] calcWheelDistance = new double[4];
	double initialWheelDistance;
	/**
	 * Start a distance travel
	 */
	public void saveInitialWheelDistance(){
//		initialWheelDistance[0] = rightFrontTalon.getSelectedSensorPosition(0);
//		initialWheelDistance[1] = rightRearTalon.getSelectedSensorPosition(0);
//		initialWheelDistance[2] = leftFrontTalon.getSelectedSensorPosition(0);
//		initialWheelDistance[3] = leftRearTalon.getSelectedSensorPosition(0);
		initialWheelDistance = (leftFrontTalon.getSelectedSensorPosition(0) + -rightFrontTalon.getSelectedSensorPosition(0))/2;

	}
	/**
	 * Calculate the distance travelled.  Return the second shortest distance.
	 * If a wheel is floating, it will have a larger value - ignore it.
	 * If a wheel is stuck, it will have a small value
	 * @return
	 */
	public double getWheelDistance(){
//		calcWheelDistance[0] = Math.abs(rightFrontTalon.getSelectedSensorPosition(0) - initialWheelDistance[0]);
//		calcWheelDistance[1] = Math.abs(rightRearTalon.getSelectedSensorPosition(0) - initialWheelDistance[1]);
//		calcWheelDistance[2] = Math.abs(leftFrontTalon.getSelectedSensorPosition(0) - initialWheelDistance[2]);
//		calcWheelDistance[3] = Math.abs(leftRearTalon.getSelectedSensorPosition(0) - initialWheelDistance[3]);
//		Arrays.sort(calcWheelDistance);
//		return  calcWheelDistance[1];
		double dist = (leftFrontTalon.getSelectedSensorPosition(0) + -rightFrontTalon.getSelectedSensorPosition(0))/2;
		return dist - initialWheelDistance;
	}
	
	//NOTE the difference between rotateToHeading(...) and goToHeading(...)
	public void setDesiredHeading(double desiredHeading) {
		m_desiredHeading = desiredHeading;
		m_iterationsSinceRotationCommanded = 50;  // RJD: I think this should be the constnat.

		// reset the heading control loop for the new heading
		m_HeadingPid.reset();
		m_HeadingPid.enable();
	}
	
	//**********************************MISC.***********************************************
	public void toggleSpeedRacerDrive() {
		m_speedRacerDriveMode = !m_speedRacerDriveMode;
	}

	public boolean isSpeedRacerDrive() {
		return m_speedRacerDriveMode;
	}
	
	private double m_unwindStartPosition;
	public void setUnwindStartPosition(double arg_startPos){
		m_unwindStartPosition = arg_startPos;
	}
	public double getUnwindStartPosition(){
		return m_unwindStartPosition;
	}
}
