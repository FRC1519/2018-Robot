/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.mayheminc.robot2018.autonomousroutines.*;

/**
 *
 * @author Team1519
 */
public class Autonomous extends Subsystem {


	private static Command autonomousPrograms[] = {
			/* 0 */   new StayStill()  // do nothing	
			, new CrossBaseline()
			, new Week0StartLeft()
			, new Week0StartMid()
			, new Week0StartRight()
/*			
			, new Auto2CubeFromRight()
			, new Auto2CubeFromMid()
			, new Auto2CubeFromLeft()
			
			, new Auto1CubeFromLeft()

			, new StartRightLeftSwitchLeftScale()
			, new StartRightLeftSwitchLeftScale()
			, new StartRightRightSwitchLeftScale()
			, new StartRightRightSwitchRightScale()
			
			, new StartMidLeftSwitchLeftScale()
			, new StartMidLeftSwitchLeftScale()
			, new StartMidRightSwitchLeftScale()
			, new StartMidRightSwitchRightScale()
			, new StartMidRightSwitchRightScale()
					
			, new StartLeftLeftSwitchLeftScale()
			, new StartLeftLeftSwitchLeftScale()
			, new StartLeftRightSwitchLeftScale()
			, new StartLeftRightSwitchRightScale()
			, new StartLeftRightSwitchRightScale()
					
			, new StartRightLeftSwitch()
			, new StartRightRightSwitch()
			, new StartLeftLeftSwitch()
			, new StartLeftRightSwitch()
*/			
	};

	private static int programNumber = 1; // 1 = cross baseline
	private static int delay = 0;

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
