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
	public static final int FRONT_RIGHT_TALON = 3;
	public static final int BACK_RIGHT_TALON = 4;
	
	// Pivot Talon
	public static final int PIVOT_TALON = 12;
	
	// Intake Talons
	public static final int INTAKE_LEFT_TALON = 7;
	public static final int INTAKE_RIGHT_TALON = 11;
	
//	public static final int UNUSED_TALON = 8
	public static final int CLIMBER_TALON = 10;
	
	// Elevator Talons
	public static final int ELEVATOR_TALON = 9;
	public static final int ELEVATOR_ARM_MOTOR = 6;
	public static final int TURRET_TALON = 5;

	// Joysticks
	public static final int DRIVER_GAMEPAD = 0;
	public static final int DRIVER_JOYSTICK = 1;
	public static final int OPERATOR_GAMEPAD = 2;
	public static final int OPERATOR_JOYSTICK = 3;
	
	public static final int WINCH_TALON = 25;
	
	//Solenoids:
	public static final int INTAKE_FINGERS_SOLENOID = 0;
	public static final int ELEVATOR_ARM_SOLENOID = 1;
	public static final int SHIFTING_SOLENOID = 4;
	
	// Analog Inputs
	public static final int LEFT_IR = 1;
	public static final int CENTER_IR = 2;
	public static final int RIGHT_IR = 3;
	
	// PDP Channels = 
	public static final int DRIVE_FRONT_LEFT_PDP = 15;
	public static final int DRIVE_BACK_LEFT_PDP = 14;
	public static final int DRIVE_FRONT_RIGHT_PDP = 0;
	public static final int DRIVE_BACK_RIGHT_PDP = 1;	
}
