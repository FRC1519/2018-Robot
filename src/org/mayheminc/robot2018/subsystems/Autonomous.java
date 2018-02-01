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

//	private static int slot = 1; // start with slot 1 selected
	
//	private static String [] slotNames = {
//			"ERROR : 0",
//			"Left-Most: 1",
//			"Left-Center: 2",
//			"Center: 3",
//			"Right-Center: 4",
//			"Right-Most: 5",
//			"Left-Center to Center: 6"
//	};
	
//	private static Command autonomousDefenses[] = {          
//			/* 0 */ 
////			/* 1 */  new AutoDefenseMoat()
////			/* 2 */ , new AutoDefensePortcullis()
////			/* 3 */ , new AutoDefenseRamparts()
////			/* 4 */ , new AutoDefenseRockWall()
////			/* 5 */ , new AutoDefenseRoughTerrain()
////			/* ? */ , new AutoDefenseChevalDeFrise()
////			/* ? */ , new AutoDefenseDrawbridge()
////			/* ? */ , new AutoDefenseSallyPort()
//	};

	private static Command autonomousPrograms[] = {
			/* 0 */   new StayStill()  // do nothing	
			/* 1a*/ , new auto1()
			, new Auto2CubeFromRight()
			, new Auto2CubeFromMid()
			, new Auto2CubeFromLeft()

			, new StartRightLeftSwitchLeftScale()
			, new StartRightLeftSwitchLeftScale()
			, new StartRightRightSwitchLeftScale()
			, new StartRightRightSwitchRightScale()
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
					
	};

	// set number of default program to run
	private static int programNumber = 1;
//	private static int defenseNumber = 0;
	private static int delay = 0;

	public Autonomous() {
	}

	public void initDefaultCommand() {
	}

//	public Command getSelectedDefense() {
//		return autonomousDefenses[defenseNumber];
//	}

	public Command getSelectedProgram(){
		return autonomousPrograms[programNumber];
	}

//	public int getSelectedSlot(){
//		return slot;
//	}

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

//	public void adjustDefenseNumber(int delta) {
//		defenseNumber += delta;
//		if (defenseNumber < 0) {
//			defenseNumber = autonomousDefenses.length - 1;
//		}
//		else if (defenseNumber >= autonomousDefenses.length) {
//			defenseNumber = 0;
//		}
//		updateSmartDashboard();
//	}

	private static final int MAX_DELAY = 9;

	public void adjustDelay(int delta) {
		delay += delta;
		if (delay < 0) {
			delay = 0;
		} else if (delay > MAX_DELAY) {
			delay = MAX_DELAY;
		}
	}
	
//	private static final int MAX_SLOT = 6;
//	public void adjustSlot(int delta) {
//		slot += delta;
//		if (slot < 1) {
//			slot = MAX_SLOT;
//		} else if (slot > MAX_SLOT) {
//			slot = 1;
//		}
//	}

	private static StringBuffer sb = new StringBuffer();

	public static void updateSmartDashboard() {
		sb.setLength(0);
		sb.append(programNumber + " " + autonomousPrograms[programNumber].getName());
		sb.append("         ");
		SmartDashboard.putString("Auto Prog", sb.toString());
//		SmartDashboard.putString("Auto Slot", slotNames[slot]);
		
//		sb.setLength(0);
//		sb.append(defenseNumber + " " + autonomousDefenses[defenseNumber].getName());
//		sb.append("         ");
//		SmartDashboard.putString("Auto Defense", sb.toString());

		SmartDashboard.putNumber("Auto Delay", delay);
	}

	public String toString(){
		return autonomousPrograms[programNumber].getName();
	}
}
