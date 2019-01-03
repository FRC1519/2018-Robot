
package org.mayheminc.robot2018;

import org.mayheminc.util.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.mayheminc.robot2018.commands.*;
import org.mayheminc.robot2018.subsystems.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());

	// driver pad and stick
	public static final Joystick DRIVER_PAD = new Joystick(RobotMap.DRIVER_GAMEPAD);
	public static final Button DRIVER_PAD_BUTTON_EIGHT = new JoystickButton(DRIVER_PAD, 8);
	public static final Joystick DRIVER_STICK = new Joystick(RobotMap.DRIVER_JOYSTICK);

	// driver stick buttons
	private static final Button DRIVER_STICK_BUTTON_ONE_DISABLED = new DisabledOnlyJoystickButton(DRIVER_STICK, 1);
	private static final Button DRIVER_STICK_BUTTON_ONE_ENABLED = new EnabledOnlyJoystickButton(DRIVER_STICK, 1);
	private static final Button DRIVER_STICK_BUTTON_TWO = new DisabledOnlyJoystickButton(DRIVER_STICK, 2);
	private static final Button DRIVER_STICK_BUTTON_THREE = new DisabledOnlyJoystickButton(DRIVER_STICK, 3);
	private static final Button DRIVER_STICK_BUTTON_FOUR = new DisabledOnlyJoystickButton(DRIVER_STICK, 4);
	private static final Button DRIVER_STICK_BUTTON_FIVE = new DisabledOnlyJoystickButton(DRIVER_STICK, 5);
	private static final Button DRIVER_STICK_BUTTON_SIX = new DisabledOnlyJoystickButton(DRIVER_STICK, 6);
	private static final Button DRIVER_STICK_BUTTON_SEVEN = new DisabledOnlyJoystickButton(DRIVER_STICK, 7);
	private static final Button DRIVER_STICK_BUTTON_EIGHT = new DisabledOnlyJoystickButton(DRIVER_STICK, 8);
	private static final Button DRIVER_STICK_BUTTON_NINE = new JoystickButton(DRIVER_STICK, 9);
	private static final Button DRIVER_STICK_BUTTON_TEN = new DisabledOnlyJoystickButton(DRIVER_STICK, 10);
	private static final Button DRIVER_STICK_BUTTON_ELEVEN = new DisabledOnlyJoystickButton(DRIVER_STICK, 11);

	// operator pad and stick
	public static final Joystick OPERATOR_PAD = new Joystick(RobotMap.OPERATOR_GAMEPAD);
	private static final Button OPERATOR_PAD_BUTTON_ONE = new JoystickButton(OPERATOR_PAD, 1);
	private static final Button OPERATOR_PAD_BUTTON_TWO = new JoystickButton(OPERATOR_PAD, 2);
	private static final Button OPERATOR_PAD_BUTTON_THREE = new JoystickButton(OPERATOR_PAD, 3);
	private static final Button OPERATOR_PAD_BUTTON_FOUR = new JoystickButton(OPERATOR_PAD, 4);
	private static final Button OPERATOR_PAD_BUTTON_FIVE = new JoystickButton(OPERATOR_PAD, 5);
	private static final Button OPERATOR_PAD_BUTTON_SIX = new JoystickButton(OPERATOR_PAD, 6);
	private static final Button OPERATOR_PAD_BUTTON_SEVEN = new JoystickButton(OPERATOR_PAD, 7);
	private static final Button OPERATOR_PAD_BUTTON_EIGHT = new JoystickButton(OPERATOR_PAD, 8);
	private static final Button OPERATOR_PAD_BUTTON_NINE = new JoystickButton(OPERATOR_PAD, 9);
	private static final Button OPERATOR_PAD_BUTTON_TEN = new JoystickButton(OPERATOR_PAD, 10);
	@SuppressWarnings("unused")
	private static final Button OPERATOR_PAD_BUTTON_ELEVEN = new JoystickButton(OPERATOR_PAD, 11);
	private static final Button OPERATOR_PAD_BUTTON_TWELVE = new JoystickButton(OPERATOR_PAD, 12);
	@SuppressWarnings("unused")
	private static final Button FORCE_FIRE_BUTTON = new AndJoystickButton(OPERATOR_PAD, 5, OPERATOR_PAD, 7);

	public static final int OPERATOR_PAD_LEFT_X_AXIS = 0;
	public static final int OPERATOR_PAD_LEFT_Y_AXIS = 1;
	public static final int OPERATOR_PAD_RIGHT_X_AXIS = 2;
	public static final int OPERATOR_PAD_RIGHT_Y_AXIS = 3;

	// Operator Control Buttons
	@SuppressWarnings("unused")
	private static final JoystickAxisButton OPERATOR_PAD_LEFT_Y_AXIS_UP = new JoystickAxisButton(OPERATOR_PAD,
			OPERATOR_PAD_LEFT_Y_AXIS, JoystickAxisButton.NEGATIVE_ONLY);
	@SuppressWarnings("unused")
	private static final JoystickAxisButton OPERATOR_PAD_LEFT_Y_AXIS_DOWN = new JoystickAxisButton(OPERATOR_PAD,
			OPERATOR_PAD_LEFT_Y_AXIS, JoystickAxisButton.POSITIVE_ONLY);
	@SuppressWarnings("unused")
	private static final JoystickAxisButton OPERATOR_PAD_RIGHT_Y_AXIS_UP = new JoystickAxisButton(OPERATOR_PAD,
			OPERATOR_PAD_RIGHT_Y_AXIS, JoystickAxisButton.NEGATIVE_ONLY);
	@SuppressWarnings("unused")
	private static final JoystickAxisButton OPERATOR_PAD_RIGHT_Y_AXIS_DOWN = new JoystickAxisButton(OPERATOR_PAD,
			OPERATOR_PAD_RIGHT_Y_AXIS, JoystickAxisButton.POSITIVE_ONLY);

	// Axis Definitions for the F310 gamepad
	// Axis 0 - Left X Axis (-1.0 left to +1.0 right)
	// Axis 1 - Left Y Axis (-1.0 forward to +1.0 backward)
	// Axis 2 - Left Trigger (0.0 unpressed to +1.0 fully pressed)
	// Axis 3 - Right Trigger (0.0 unpressed to +1.0 fully pressed)
	// Axis 4 - Right X Axis (-1.0 left to +1.0 right)
	// Axis 5 - Right Y axis (-1.0 forward to +1.0 backward)
	// Empirical testing on 23 Jan 2015 shows that +/-0.07 is a reasonable threshold
	// for "centered"
	// (in other words, intentional non-zero values will have magnitude >= 0.07;
	// values with a magnitude of < 0.07 should probably be treated as zero.)
	public static final int GAMEPAD_F310_LEFT_X_AXIS = 0;
	public static final int GAMEPAD_F310_LEFT_Y_AXIS = 1;
	public static final int GAMEPAD_F310_LEFT_TRIGGER = 2;
	public static final int GAMEPAD_F310_RIGHT_TRIGGER = 3;
	public static final int GAMEPAD_F310_RIGHT_X_AXIS = 4;
	public static final int GAMEPAD_F310_RIGHT_Y_AXIS = 5;

	public static final int GAMEPAD_F310_A_BUTTON = 1;
	public static final int GAMEPAD_F310_B_BUTTON = 2;
	public static final int GAMEPAD_F310_X_BUTTON = 3;
	public static final int GAMEPAD_F310_Y_BUTTON = 4;
	public static final int GAMEPAD_F310_LEFT_BUTTON = 5;
	public static final int GAMEPAD_F310_RIGHT_BUTTON = 6;
	public static final int GAMEPAD_F310_BACK_BUTTON = 7;
	public static final int GAMEPAD_F310_START_BUTTON = 8;
	public static final int GAMEPAD_F310_LEFT_STICK_BUTTON = 9;
	public static final int GAMEPAD_F310_RIGHT_STICK_BUTTON = 10;

	// Driver Control Modes
	@SuppressWarnings("unused")
	private static final Button TOGGLE_CLOSED_LOOP_MODE_BUTTON = new DisabledOnlyJoystickButton(DRIVER_PAD, 7);
	@SuppressWarnings("unused")
	private static final Button TOGGLE_FOD_BUTTON = new DisabledOnlyJoystickButton(DRIVER_PAD, 8);

	private static final Button DRIVER_PAD_LEFT_UPPER_TRIGGER_BUTTON = new EnabledOnlyJoystickButton(DRIVER_PAD,
			GAMEPAD_F310_LEFT_BUTTON);
	private static final JoystickAxisButton DRIVER_PAD_LEFT_LOWER_TRIGGER_BUTTON = new JoystickAxisButton(DRIVER_PAD,
			GAMEPAD_F310_LEFT_TRIGGER, JoystickAxisButton.POSITIVE_ONLY);
	private static final JoystickAxisButton DRIVER_PAD_RIGHT_LOWER_TRIGGER_BUTTON = new JoystickAxisButton(DRIVER_PAD,
			GAMEPAD_F310_RIGHT_TRIGGER, JoystickAxisButton.POSITIVE_ONLY);

	@SuppressWarnings("unused")
	private static final Button DRIVER_PAD_START_BUTTON = new JoystickButton(DRIVER_PAD, GAMEPAD_F310_START_BUTTON);
	private static final Button DRIVER_PAD_GREEN_BUTTON = new JoystickButton(DRIVER_PAD, 1); // Green "A" button
	private static final Button DRIVER_PAD_RED_BUTTON = new JoystickButton(DRIVER_PAD, 2); // RED 'B" button
	private static final Button DRIVER_PAD_BLUE_BUTTON = new JoystickButton(DRIVER_PAD, 3); // BLUE 'X' button
	private static final Button DRIVER_PAD_YELLOW_BUTTON = new JoystickButton(DRIVER_PAD, 4); // YELLOW 'Y' button
	@SuppressWarnings("unused")
	private static final Button DRIVER_PAD_BUTTON_FIVE = new JoystickButton(DRIVER_PAD, 5); // Left Top Trigger
	@SuppressWarnings("unused")
	private static final Button DRIVER_PAD_BUTTON_SIX = new JoystickButton(DRIVER_PAD, 6); // Right Top Trigger
	@SuppressWarnings("unused")
	private static final Button DRIVER_PAD_LEFT_STICK_BUTTON = new JoystickButton(DRIVER_PAD,
			GAMEPAD_F310_LEFT_STICK_BUTTON); // Left Stick Trigger
	@SuppressWarnings("unused")
	private static final Button DRIVER_PAD_RIGHT_STICK_BUTTON = new JoystickButton(DRIVER_PAD,
			GAMEPAD_F310_RIGHT_STICK_BUTTON); // Right Stick Trigger

	@SuppressWarnings("unused")
	private static final JoystickPOVButton DRIVER_PAD_D_PAD_UP = new JoystickPOVButton(DRIVER_PAD, 0);
	@SuppressWarnings("unused")
	private static final JoystickPOVButton DRIVER_PAD_D_PAD_RIGHT = new JoystickPOVButton(DRIVER_PAD, 90);
	@SuppressWarnings("unused")
	private static final JoystickPOVButton DRIVER_PAD_D_PAD_DOWN = new JoystickPOVButton(DRIVER_PAD, 180);
	@SuppressWarnings("unused")
	private static final JoystickPOVButton DRIVER_PAD_D_PAD_LEFT = new JoystickPOVButton(DRIVER_PAD, 270);

	private static final JoystickPOVButton OPERATOR_PAD_D_PAD_LEFT = new JoystickPOVButton(OPERATOR_PAD, 270);
	private static final JoystickPOVButton OPERATOR_PAD_D_PAD_RIGHT = new JoystickPOVButton(OPERATOR_PAD, 90);
	private static final JoystickPOVButton OPERATOR_PAD_D_PAD_UP = new JoystickPOVButton(OPERATOR_PAD, 0);
	private static final JoystickPOVButton OPERATOR_PAD_D_PAD_DOWN = new JoystickPOVButton(OPERATOR_PAD, 180);

	private static boolean USE_PID_TUNER = false;
	public static PidTuner pidTuner = null;

	public OI() {

		DriverStation.reportError("OI constructor.\n", false);

		// ****************************** PID_TUNER
		// **************************************/

		if (USE_PID_TUNER) {
			pidTuner = new PidTuner(DRIVER_STICK_BUTTON_SIX, DRIVER_STICK_BUTTON_SEVEN, DRIVER_STICK_BUTTON_ELEVEN,
					DRIVER_STICK_BUTTON_TEN, Robot.elevator);
		}

		// *******************************DRIVER
		// PAD**************************************

		DRIVER_PAD_RED_BUTTON.whileHeld(new FlipACube(45));
		DRIVER_PAD_BLUE_BUTTON.whileHeld(new FlipACube(-45));
		DRIVER_PAD_YELLOW_BUTTON.whileHeld(new ClimberSet(1.0)); // Climb up
		DRIVER_PAD_GREEN_BUTTON.whileHeld(new ClimberSet(-0.2)); // Descend down

		DRIVER_PAD_LEFT_UPPER_TRIGGER_BUTTON.whileHeld(new AllRollersIn());
		DRIVER_PAD_LEFT_LOWER_TRIGGER_BUTTON.whileHeld(new AllRollersOut());

		// ******************************* DRIVER STICK
		// ****************************************************************************

		DRIVER_STICK_BUTTON_ONE_DISABLED.whenPressed(new TurretZero());
		DRIVER_STICK_BUTTON_ONE_ENABLED.whenPressed(new ElevatorZero());

		// adjust auto parameters
		DRIVER_STICK_BUTTON_THREE.whenPressed(new SelectAutonomousProgram(1));
		DRIVER_STICK_BUTTON_TWO.whenPressed(new SelectAutonomousProgram(-1));
		DRIVER_STICK_BUTTON_FOUR.whenPressed(new SelectAutonomousDelay(-1));
		DRIVER_STICK_BUTTON_FIVE.whenPressed(new SelectAutonomousDelay(1));

		// NOTE: buttons SIX, SEVEN, TEN, ELEVEN are reserved for PidTuner

		// zero elements that require zeroing
		DRIVER_STICK_BUTTON_EIGHT.whenPressed(new DriveZeroGyro());
		DRIVER_STICK_BUTTON_NINE.whenPressed(new PivotZeroEncoder());

		// *************************OPERATOR PAD*******************************

		OPERATOR_PAD_BUTTON_ONE.whileHeld(new TurretMoveTo(Turret.RIGHT_REAR));
		OPERATOR_PAD_BUTTON_TWO.whenPressed(new PivotMove(Pivot.DOWNWARD_POSITION)); // PivotToFloor());
		OPERATOR_PAD_BUTTON_THREE.whenPressed(new PivotMove(Pivot.SPIT_POSITION));

		// OPERATOR_PAD_BUTTON_FOUR does two different commands simultaneously!
		OPERATOR_PAD_BUTTON_FOUR.whenPressed(new TurretMoveTo(Turret.FRONT_POSITION)); // Center turret
		OPERATOR_PAD_BUTTON_FOUR.whenPressed(new PivotMove(Pivot.UPRIGHT_POSITION)); // PivotToUpright());

		// BUTTONS FIVE AND SEVEN ARE For Operating pneumatics
		OPERATOR_PAD_BUTTON_FIVE.whenPressed(new AllJawsClose());
		OPERATOR_PAD_BUTTON_SEVEN.whenPressed(new AllJawsOpen());

		// Button Six and Eight currently control rollers of intake or elevator
		OPERATOR_PAD_BUTTON_SIX.whileHeld(new IntakeInAndLiftTheCube(false));
		OPERATOR_PAD_BUTTON_EIGHT.whileHeld(new AllRollersOut());

		OPERATOR_PAD_D_PAD_UP.whenPressed(new ElevatorSetPosition(Elevator.CEILING));
		OPERATOR_PAD_D_PAD_DOWN.whenPressed(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
		OPERATOR_PAD_D_PAD_RIGHT.whenPressed(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
		OPERATOR_PAD_D_PAD_LEFT.whenPressed(new ElevatorSetPosition(Elevator.SCALE_MID));

		OPERATOR_PAD_BUTTON_NINE.whenPressed(new SafePosition());
		OPERATOR_PAD_BUTTON_TEN.whenPressed(new HandoffCubeToElevator(Elevator.SWITCH_HEIGHT));

		// Uncomment any of the "blackbox" commands in order to debug the OI buttons
		// Robot.blackbox.addButton("DRIVER_PAD_BLUE_BUTTON", DRIVER_PAD_BLUE_BUTTON);
		// Robot.blackbox.addButton("DRIVER_PAD_GREEN_BUTTON", DRIVER_PAD_GREEN_BUTTON);
		// Robot.blackbox.addButton("DRIVER_PAD_RED_BUTTON", DRIVER_PAD_RED_BUTTON);
		// Robot.blackbox.addButton("DRIVER_PAD_YELLOW_BUTTON",
		// DRIVER_PAD_YELLOW_BUTTON);
		// Robot.blackbox.addButton("DRIVER_PAD_LEFT_UPPER_TRIGGER_BUTTON",
		// DRIVER_PAD_LEFT_UPPER_TRIGGER_BUTTON);
		// Robot.blackbox.addButton("DRIVER_PAD_LEFT_LOWER_TRIGGER_BUTTON",
		// DRIVER_PAD_LEFT_LOWER_TRIGGER_BUTTON);

		// Robot.blackbox.addButton("OPERATOR_PAD_BUTTON_ONE", OPERATOR_PAD_BUTTON_ONE);
		// Robot.blackbox.addButton("OPERATOR_PAD_BUTTON_TWO", OPERATOR_PAD_BUTTON_TWO);
		// Robot.blackbox.addButton("OPERATOR_PAD_BUTTON_THREE",
		// OPERATOR_PAD_BUTTON_THREE);
		// Robot.blackbox.addButton("OPERATOR_PAD_BUTTON_FOUR",
		// OPERATOR_PAD_BUTTON_FOUR);
		// Robot.blackbox.addButton("OPERATOR_PAD_BUTTON_FIVE",
		// OPERATOR_PAD_BUTTON_FIVE);
		// Robot.blackbox.addButton("OPERATOR_PAD_BUTTON_SIX", OPERATOR_PAD_BUTTON_SIX);
		// Robot.blackbox.addButton("OPERATOR_PAD_BUTTON_SEVEN",
		// OPERATOR_PAD_BUTTON_SEVEN);
		// Robot.blackbox.addButton("OPERATOR_PAD_BUTTON_EIGHT",
		// OPERATOR_PAD_BUTTON_EIGHT);
		// Robot.blackbox.addButton("OPERATOR_PAD_BUTTON_NINE",
		// OPERATOR_PAD_BUTTON_NINE);
		// Robot.blackbox.addButton("OPERATOR_PAD_BUTTON_TEN", OPERATOR_PAD_BUTTON_TEN);
		// Robot.blackbox.addButton("OPERATOR_PAD_BUTTON_ELEVEN",
		// OPERATOR_PAD_BUTTON_ELEVEN);
		// Robot.blackbox.addButton("OPERATOR_PAD_BUTTON_TWELVE",
		// OPERATOR_PAD_BUTTON_TWELVE);
		// Robot.blackbox.addButton("OPERATOR_PAD_D_PAD_LEFT", OPERATOR_PAD_D_PAD_LEFT);
		// Robot.blackbox.addButton("OPERATOR_PAD_D_PAD_RIGHT",
		// OPERATOR_PAD_D_PAD_RIGHT);
		// Robot.blackbox.addButton("OPERATOR_PAD_D_PAD_UP", OPERATOR_PAD_D_PAD_UP);
		// Robot.blackbox.addButton("OPERATOR_PAD_D_PAD_DOWN", OPERATOR_PAD_D_PAD_DOWN);

	}

	public boolean quickTurn() {
		return (DRIVER_PAD.getRawButton(OI.GAMEPAD_F310_RIGHT_BUTTON));
	}

	public double driveThrottle() {
		// the driveThrottle is the "Y" axis of the Driver Gamepad.
		// However, the joysticks give -1.0 on the Y axis when pushed forward
		// This method reverses that, so that positive numbers are forward
		double throttleVal = -DRIVER_PAD.getY();

		if (Math.abs(throttleVal) < 0.05) {
			throttleVal = 0.0;
		}

		// if the slow button is pressed, cut the throttle value in third.
		if (DRIVER_PAD_RIGHT_LOWER_TRIGGER_BUTTON.get()) {
			throttleVal = throttleVal / 3.0;
		}

		return (throttleVal);
	}

	// TODO: KBS: Note that there is a piece of code within pivotArmPower which is
	// shared with other joystick features.
	// I'm inclined to put that code in a shared function for
	// "linearizeStickWithDeadZone(double percentage)"
	// but also want to do some joystick testing to determine an appropriate size
	// for the deadzone. 20%
	// seems a bit too large. The linearization could also be improved. A
	// side-effect of the current algorithm
	// is that the "maximum" power available from the joystick is 100% -
	// DEAD_ZONE_PERCENT. While probably okay
	// for the specific cases here, this might not be the right thing to do in
	// general. However, in here would
	// be a good place to enforce "operator-controlled" restrictions on maximum
	// power to be applied for manual
	// control of the robot mechanisms.
	public double pivotArmPower() {
		// if the joystick button is held in, calculate the power.
		if (OPERATOR_PAD_BUTTON_TWELVE.get()) {
			// NOTE: Joystick has "up" be negative and "down" be positive. Reverse this by
			// multiplying by -1.
			double value = (OPERATOR_PAD.getRawAxis(OPERATOR_PAD_RIGHT_Y_AXIS)) * -1;
			// if the power is less than 20%, make it 0
			if (-0.2 < value && value < 0.2) {
				value = 0.0;
			} else if (value > 0.2) {
				// if it is above 20%, subtract the 20% to keep the linearness.
				value = value - 0.2;
			} else // (this means value < -0.2)
			{
				// if it is above 20%, subtract the 20% to keep the linearness.
				value = value + 0.2;
			}
			return value;
		} else // joystick button is not held in, return 0 power.
		{
			return 0.0;
		}
	}

	public double tankDriveLeft() {
		double tankDriveLeftAxis = -DRIVER_PAD.getRawAxis(OI.GAMEPAD_F310_LEFT_Y_AXIS);

		if (Math.abs(tankDriveLeftAxis) < 0.05) {
			tankDriveLeftAxis = 0.0;
		}
		return tankDriveLeftAxis;
	}

	public double tankDriveRight() {
		double tankDriveRightAxis = -DRIVER_PAD.getRawAxis(OI.GAMEPAD_F310_RIGHT_Y_AXIS);

		if (Math.abs(tankDriveRightAxis) < 0.05) {
			tankDriveRightAxis = 0.0;
		}
		return tankDriveRightAxis;
	}

	public double steeringX() {
		// SteeringX is the "X" axis of the right stick on the Driver Gamepad.
		double value = DRIVER_PAD.getRawAxis(OI.GAMEPAD_F310_RIGHT_X_AXIS);
		if (Math.abs(value) < 0.05) {
			value = 0.0;
		}

		if (DRIVER_PAD_RIGHT_LOWER_TRIGGER_BUTTON.get()) {
			value = value / 2.0;
		}
		return value;
	}

	public double steeringY() {
		// However, the joysticks give -1.0 on that axis when pushed forward
		// This method reverses that, so that positive numbers are forward
		return (-DRIVER_PAD.getRawAxis(OI.GAMEPAD_F310_RIGHT_Y_AXIS));
	}

	public boolean forceLowGear() {
		return DRIVER_PAD_LEFT_LOWER_TRIGGER_BUTTON.get();
	}

	// returns true if any of the autoInTeleop buttons are held
	public boolean autoInTeleop() {
		return ( // DRIVER_PAD_GREEN_BUTTON.get() // added for ribfest
		DRIVER_PAD_BLUE_BUTTON.get() || // removed for ribfest
				DRIVER_PAD_RED_BUTTON.get() // removed for ribfest
		);
	}

	public double getTurretManualPower() {

		// Positive turret power should give clockwise rotation.
		// KBS: Note that right axis should be positive when clockwise; I don't think we
		// really want this
		// reversed for the turret. Looks like a copy/paste error from pivotArmPower.
		// For ease of
		// thinking about the turret operation, I think we want clockwise to be
		// "forward" and ascending
		// sensor values.

		if (OPERATOR_PAD_BUTTON_TWELVE.get()) {

			double value = (OPERATOR_PAD.getRawAxis(OPERATOR_PAD_RIGHT_X_AXIS));
			// if the power is less than 20%, make it 0
			if (value < 0.2 && value > -0.2) {
				value = 0.0;
			} else if (value > 0.2) {
				// if it is above 20%, subtract the 20% to keep the linearness.
				value = value - 0.2;
			} else {
				// if it is below -20%, add the 20% to keep the linearness.
				value = value + 0.2;
			}
			return value;
		} else // if the joysitck button is not held in, return 0.0
		{
			return 0.0;
		}
	}

	public double getElevatorPower() {
		// NOTE: Joystick has "up" be negative and "down" be positive. Reverse this by
		// multiplying by -1.
		double value = (OPERATOR_PAD.getRawAxis(OPERATOR_PAD_LEFT_Y_AXIS) * -1);
		// if the power is less than 20%, make it 0
		if (value < 0.2 && value > -0.2) {
			value = 0.0;
		} else if (value > 0.2) {
			// if it is above 20%, subtract the 20% to keep the linearness.
			value = value - 0.2;
		} else {
			// if it is below -20%, add the 20% to keep the linearness.
			value = value + 0.2;
		}
		return value;
	}

	public boolean getTurretFieldOrientedIsCommanded() {
		double x = OPERATOR_PAD.getRawAxis(OPERATOR_PAD_RIGHT_X_AXIS);
		double y = OPERATOR_PAD.getRawAxis(OPERATOR_PAD_RIGHT_Y_AXIS) * -1; // up is positive

		double mag = Math.sqrt(x * x + y * y);

		return mag > 0.5;
	}

	public double getTurretFieldOrientedDirection() {
		double x = OPERATOR_PAD.getRawAxis(OPERATOR_PAD_RIGHT_X_AXIS);
		double y = OPERATOR_PAD.getRawAxis(OPERATOR_PAD_RIGHT_Y_AXIS) * -1; // up is positive

		double radians = Math.atan2(x, y);
		double degrees = radians * 180 / Math.PI;
		return degrees;
	}
}
