package org.mayheminc.robot2018;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// Drive CAN Talons
	public static final int FRONT_LEFT_TALON = 1;
	public static final int BACK_LEFT_TALON = 2;
	public static final int FRONT_RIGHT_TALON = 7;
	public static final int BACK_RIGHT_TALON = 8;
	
	//Arm Talon
	public static final int ARM_TALON = 3;
	
	// Claw Talon	
//	public static final int ROLLER_TALON = 4;

	// Claw Talon	
//	public static final int LIFTER_TALON = 5;
	
	// Pivot Talon
	public static final int PIVOT_TALON = 20;
	
	// Intake Talons
	public static final int INTAKE_LEFT_TALON = 21;
	public static final int INTAKE_RIGHT_TALON = 22;
	
	// Elevator Talons
	public static final int ELEVATOR_TALON = 22;

	// Launcher Talon
//	public static final int LAUNCHER_WINCH_TALON = 6;
	
	// digital inputs / output
	public static final int LAUNCHER_RESET_LIMIT_SWITCH = 0;
	public static final int LAUNCHER_RESET_LIMIT_SWITCH2 = 0;
	
    // Analog inputs
//    public static final int HEADING_GYRO = 0;
   
	// Joysticks
	public static final int DRIVER_GAMEPAD = 0;
	public static final int DRIVER_JOYSTICK = 1;
	public static final int OPERATOR_GAMEPAD = 2;
	public static final int OPERATOR_JOYSTICK = 3;
	
	//Solenoids:
	public static final int SHIFTING_SOLENOID = 0;
//	public static final int ARM_BRAKE_SOLENOID = 1;
//	public static final int LAUNCH_SOLENOID = 2;
//	public static final int CENTERING_PISTONS_SOLENOID = 3;
//	public static final int CLAW_SOLENOID = 4;
	
	// PDP Channels = 
	// Visually checked on the Comp Robot 2/23/2016 - Robert Deml
	public static final int DRIVE_FRONT_LEFT_PDP = 15;
	public static final int DRIVE_BACK_LEFT_PDP = 14;
	public static final int DRIVE_FRONT_RIGHT_PDP = 0;
	public static final int DRIVE_BACK_RIGHT_PDP = 1;	
}
