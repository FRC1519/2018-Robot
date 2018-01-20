package org.mayheminc.robot2018.autonomousroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.autonomousroutines.DriveStraight.DistanceUnits;
import org.mayheminc.robot2018.commands.*;
import org.mayheminc.robot2018.commands.Rotate.DesiredHeadingForm;
import org.mayheminc.robot2018.subsystems.Arm;

/**
 *
 */
public class LineUpForShotCommandGroup extends CommandGroup {
	
	private double[] firstForwardDistances = {
			/*0- THERE IS NO SLOT ZERO, LEAVE BLANK*/ 0.0
			/*1*/, 0.0
			/*2*/, 96.0	// was 72.0 at start of Pine Tree; at UNH was 40.0
			/*3*/, 2.0
			/*4*/, 2.0
			/*5*/, 2.0
			/*6*/, 2.0
	};
	
	private int[] firstTurnHeadings = { // in degrees, absolute value of heading
			/*0- THERE IS NO SLOT ZERO, LEAVE BLANK*/ 0
			/*1*/, 52 // far left, LOW BAR
			/*2*/, 41 // left center
			/*3*/, 16 // middle
			/*4*/, -7 // right center
			/*5*/, -80 // used to be -31 for direct shot
			/*6*/, 85 // used to be -31 for direct shot
			};
	
	private double[] secondForwardDistances = {
			/*0- THERE IS NO SLOT ZERO, LEAVE BLANK*/ 0.0
			/*1*/, 0.0
			/*2*/, 0.0
			/*3*/, 0.0
			/*4*/, 0.0
			/*5*/, 48.0
			/*6*/, 54.0
			};
	
	private int[] secondTurnHeadings = { // in degrees, absolute value of heading
			/*0- THERE IS NO SLOT ZERO, LEAVE BLANK*/ 0
			/*1*/, 52 // far left, LOW BAR
			/*2*/, 41 // left center
			/*3*/, 16 // middle
			/*4*/, -7 // right center
			/*5*/, -7 // -7 for in front of slot 4; used to be -31 for direct shot
			/*6*/, 16 // 16 for in front of slot 3
			};
	
    public  LineUpForShotCommandGroup(int slot, boolean robotFacingForward) {
    	
    	if (robotFacingForward) {
    		addSequential(new DriveStraight(0.75, DistanceUnits.INCHES, firstForwardDistances[slot]));
    	} else {
    		addSequential(new DriveStraight(-0.75, DistanceUnits.INCHES, firstForwardDistances[slot]));
    	}
    	addSequential(new Rotate(firstTurnHeadings[slot], DesiredHeadingForm.ABSOLUTE));

    	if ((slot == 5) || (slot == 6)) {
    		// for slot 5 or 6, use the extra commands to turn and drive to a center slot
        	addSequential(new DriveStraight(0.75, DistanceUnits.INCHES, secondForwardDistances[slot]));
        	addSequential(new Rotate(secondTurnHeadings[slot], DesiredHeadingForm.ABSOLUTE));
    	}
    	
    	// make sure the arm is in the fire position before shooting!	
    	addSequential(new SetArmPosition(Robot.arm.FIRE_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));
    	if (slot == 2) {
    		// align while going backwards in slot #2
    		addSequential(new AlignToTargetAndShoot(-0.12));
    	} else {
    		// align while going forwards in the other slots
    		addSequential(new AlignToTargetAndShootWithRewind(0.12, 0.5));
    	}   
		addSequential(new TurnOnCompressor());
    }
    
}
