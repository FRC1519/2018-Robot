package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.subsystems.Autonomous;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MultiScaleO extends CommandGroup {

	public MultiScaleO(Autonomous.StartOn startSide) {
		
    	// Used the "shared routine" for scoring on the near scale
    	// this routine ends up with the cube scored and ready to drive away...
    	addSequential(new ScoreOnOppositeScaleAndGetNextCube(startSide));
    	    	
    	// we just picked up a cube -- now ready to score the cube onto the scale again...
    	addSequential(new ScorePickedUpCubeFromFenceOntoOppositeScale(startSide));
    	
    	addSequential(new DisengageFromScale());
    	
	}
}
