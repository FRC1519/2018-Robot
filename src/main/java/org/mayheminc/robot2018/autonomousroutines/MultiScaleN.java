package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.subsystems.Autonomous;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *  
 */
public class MultiScaleN extends CommandGroup {

    public MultiScaleN(Autonomous.StartOn startSide) {
    	
    	// Used the "shared routine" for scoring on the near scale
    	// this routine ends up with the cube scored and ready to drive away...
    	addSequential(new ScoreOnNearScaleAndGetNextCube(startSide));
    	
    	// Score the just-picked up cube onto the scale
    	addSequential(new ScorePickedUpCubeFromFenceOntoNearScale(startSide));
    	
    	// Either disengage from the scale, or get a third cube
    	//addSequential(new DisengageFromScale());
    	addSequential(new AfterScoringScaleGetThirdCube(startSide));

    }
}
