package org.mayheminc.robot2018;

import java.util.Hashtable;

import org.mayheminc.util.AndJoystickButton;
import org.mayheminc.util.DisabledOnlyJoystickButton;
import org.mayheminc.util.JoystickAxisButton;
import org.mayheminc.util.JoystickPOVButton;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class GamepadF310 extends Joystick{
	//	Axis Definitions for the F310 gamepad
	//	Axis 0 - Left X Axis (-1.0 left to +1.0 right)
	//	Axis 1 - Left Y Axis (-1.0 forward to +1.0 backward)
	//	Axis 2 - Left Trigger (0.0 unpressed to +1.0 fully pressed)
	//	Axis 3 - Right Trigger (0.0 unpressed to +1.0 fully pressed)
	//	Axis 4 - Right X Axis (-1.0 left to +1.0 right)
	//	Axis 5 - Right Y axis (-1.0 forward to +1.0 backward)
	//  Empirical testing on 23 Jan 2015 shows that +/-0.07 is a reasonable threshold for "centered"
	//   (in other words, intentional non-zero values will have magnitude >= 0.07;
	//    values with a magnitude of < 0.07 should probably be treated as zero.)
	
	// These are public so a caller can use GamepadF310.getRawAxis() to get the analog stick values.
	public static final int LEFT_X_AXIS = 0;
	public static final int LEFT_Y_AXIS = 1;
	private static final int LEFT_TRIGGER = 2;
	private static final int RIGHT_TRIGGER = 3;
	public static final int RIGHT_X_AXIS = 4;
	public static final int RIGHT_Y_AXIS = 5;

	private static final int A_BUTTON = 1;
	private static final int B_BUTTON = 2;
	private static final int X_BUTTON = 3;
	private static final int Y_BUTTON = 4;
	private static final int LEFT_TOP_BUTTON = 5;
	private static final int RIGHT_TOP_BUTTON = 6;
	private static final int BACK_BUTTON = 7;
	private static final int START_BUTTON = 8;
	private static final int LEFT_STICK_BUTTON = 9;
	private static final int RIGHT_STICK_BUTTON = 10;
	
	public enum F310Button
	{
		A_BUTTON,
		B_BUTTON,
		X_BUTTON,
		Y_BUTTON,
		
		D_PAD_UP,
		D_PAD_RIGHT,
		D_PAD_DOWN,
		D_PAD_LEFT,
		
		LEFT_BUTTON,
		RIGHT_BUTTON,
		BACK_BUTTON,
		START_BUTTON,
		LEFT_STICK_BUTTON,
		RIGHT_STICK_BUTTON,
		
		LEFT_TOP_BUTTON,
		LEFT_BOTTOM_BUTTON,
		RIGHT_TOP_BUTTON,
		RIGHT_BOTTOM_BUTTON,
		
		DISABLED_A_BUTTON,
		
		A_BUTTON_AND_B_BUTTON,
	};
	
	public Hashtable<F310Button, Button> Button = new Hashtable<F310Button, Button>();
	public GamepadF310(int Joystick){
		super(Joystick);
		
		Button.put(F310Button.A_BUTTON, new JoystickButton(this, A_BUTTON));
		Button.put(F310Button.B_BUTTON, new JoystickButton(this, B_BUTTON));
		Button.put(F310Button.X_BUTTON, new JoystickButton(this, X_BUTTON));
		Button.put(F310Button.Y_BUTTON, new JoystickButton(this, Y_BUTTON));

		Button.put(F310Button.D_PAD_UP, new JoystickPOVButton(this, 0));
		Button.put(F310Button.D_PAD_RIGHT, new JoystickPOVButton(this, 90));
		Button.put(F310Button.D_PAD_DOWN, new JoystickPOVButton(this, 180));
		Button.put(F310Button.D_PAD_LEFT, new JoystickPOVButton(this, 270));

		Button.put(F310Button.LEFT_STICK_BUTTON, new JoystickButton(this, LEFT_STICK_BUTTON));
		Button.put(F310Button.RIGHT_STICK_BUTTON, new JoystickButton(this, RIGHT_STICK_BUTTON));

		Button.put(F310Button.BACK_BUTTON, new JoystickButton(this, BACK_BUTTON));
		Button.put(F310Button.START_BUTTON, new JoystickButton(this, START_BUTTON));
		
		Button.put(F310Button.LEFT_TOP_BUTTON, new JoystickButton(this, LEFT_TOP_BUTTON));
		Button.put(F310Button.LEFT_BOTTOM_BUTTON, new JoystickAxisButton(this, LEFT_TRIGGER, JoystickAxisButton.POSITIVE_ONLY));
		Button.put(F310Button.RIGHT_TOP_BUTTON, new JoystickButton(this, RIGHT_TOP_BUTTON));		
		Button.put(F310Button.RIGHT_BOTTOM_BUTTON, new JoystickAxisButton(this, RIGHT_TRIGGER, JoystickAxisButton.POSITIVE_ONLY));

		Button.put(F310Button.DISABLED_A_BUTTON, new DisabledOnlyJoystickButton(this, A_BUTTON));
		
		Button.put(F310Button.A_BUTTON_AND_B_BUTTON, new AndJoystickButton(this, A_BUTTON, this, B_BUTTON));
	}
	
	// Usage
	// GAMEPAD_F310 DriverGamepad = new GAMEPAD_F310(JOYSTICK_1);
	// DriverGamepad.Button.get(F310Button.A_BUTTON).whenPressed(new Command());
}
