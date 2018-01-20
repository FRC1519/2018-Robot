package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.commands.CloseClaw;
import org.mayheminc.robot2018.commands.SetArmPosition;
import org.mayheminc.robot2018.commands.SetCenteringPistons;
import org.mayheminc.robot2018.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TraverseDefense extends CommandGroup {
    
    public  TraverseDefense() {
    	addSequential(new CloseClaw());
    	addSequential(new SetCenteringPistons(Robot.claw.CENTERING_PISTONS_TOGETHER));
    	addSequential(new SetArmPosition(Robot.arm.UPRIGHT_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));
    	
    	addSequential(new ApproachOuterWorks());  
    	
    	addSequential(new RunSelectedDefense());
    	addSequential(new Message("Completed Selected Defense.\n"));
    }
}
