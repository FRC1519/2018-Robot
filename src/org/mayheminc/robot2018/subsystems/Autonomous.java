/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.mayheminc.robot2018.autonomousroutines.*;

import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 *
 * @author Team1519
 */
public class Autonomous extends Subsystem {


	private static Command autonomousPrograms[] = {
			
			/* 0 */   new StayStill()  // do nothing	
			, new StartRightCrossBaselineBackwards()
			, new StartRightScaleAndSwitch()
			, new StartRightMultiScale()
			, new StartRightEndScale()
			, new StartCenterSmartSwitch()
			, new StartCenterSmartSwitchPyramid()
			, new StartLeftCrossBaselineBackwards()
			, new StartLeftScaleAndSwitch()
			, new StartLeftMultiScale()
			, new StartLeftEndScale()
			, new MultiSwitchN(Autonomous.StartOn.RIGHT)
			, new TestAuto()
	};

	private static int programNumber = 2; // 1 = cross baseline backwards
	private static int delay = 0;

	public enum StartOn { RIGHT, LEFT };
	
	/**
	 * Convert a right-sided angle to either left or right.
	 * @param startSide RIGHT or LEFT
	 * @param angle 0 - 360 for the right side
	 * @return
	 */
	public static double chooseAngle(StartOn startSide, double angle) {
		
		// if startSide is left, convert angle, otherwise, leave as-is
		if (startSide == StartOn.LEFT ) {
			angle = 360.0 - angle;
		}
		return (angle);
	}
	
	public Autonomous() {
	}

	public void initDefaultCommand() {
	}

	public Command getSelectedProgram(){
		return autonomousPrograms[programNumber];
	}

	public int getDelay() {
		return delay;
	}
	
	public void adjustProgramNumber(int delta){
		programNumber += delta;
		if (programNumber < 0) {
			programNumber = autonomousPrograms.length - 1;
		}
		else if (programNumber >= autonomousPrograms.length) {
			programNumber = 0;
		}
		updateSmartDashboard();
	}

	private static final int MAX_DELAY = 9;

	public void adjustDelay(int delta) {
		delay += delta;
		if (delay < 0) {
			delay = 0;
		} else if (delay > MAX_DELAY) {
			delay = MAX_DELAY;
		}
		updateSmartDashboard();
	}
	
	private static StringBuffer sb = new StringBuffer();

	public static void updateSmartDashboard() {
		sb.setLength(0);
		sb.append(programNumber + " " + autonomousPrograms[programNumber].getName());
		sb.append("         ");
		SmartDashboard.putString("Auto Prog", sb.toString());
		SmartDashboard.putNumber("Auto Delay", delay);
	}

	public String toString(){
		return autonomousPrograms[programNumber].getName();
	}
}
