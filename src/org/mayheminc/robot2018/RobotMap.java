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
	
	// Pivot Talon
	public static final int PIVOT_TALON = 20;
	
	// Intake Talons
	public static final int INTAKE_LEFT_TALON = 21;
	public static final int INTAKE_RIGHT_TALON = 22;
	
	// Elevator Talons
	public static final int ELEVATOR_TALON = 23;
	public static final int ELEVATOR_ARM_MOTOR = 24;

	// Joysticks
	public static final int DRIVER_GAMEPAD = 0;
	public static final int DRIVER_JOYSTICK = 1;
	public static final int OPERATOR_GAMEPAD = 2;
	public static final int OPERATOR_JOYSTICK = 3;
	
	//Solenoids:
	public static final int SHIFTING_SOLENOID = 0;
	public static final int ELEVATOR_ARM_SOLENOID = 1;
	
	// PDP Channels = 
	// Visually checked on the Comp Robot 2/23/2016 - Robert Deml
	public static final int DRIVE_FRONT_LEFT_PDP = 15;
	public static final int DRIVE_BACK_LEFT_PDP = 14;
	public static final int DRIVE_FRONT_RIGHT_PDP = 0;
	public static final int DRIVE_BACK_RIGHT_PDP = 1;	
}
