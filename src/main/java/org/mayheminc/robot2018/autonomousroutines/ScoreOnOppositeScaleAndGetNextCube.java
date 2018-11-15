package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.subsystems.Autonomous;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreOnOppositeScaleAndGetNextCube extends CommandGroup {

    public ScoreOnOppositeScaleAndGetNextCube(Autonomous.StartOn startSide) {
    	
    	// Used the "shared routine" for scoring on the opposite scale
    	addSequential(new ScoreOnOppositeScale(startSide));
    	 	
    	///////////////////////////////////////////////////////////////////////
    	// just scored the 1st cube on the scale at the point above
    	// so now we need to pick up the closest cube from the fence
    	addSequential(new AfterScoringScaleGetClosestCube(startSide));

       	// we just picked up a cube -- now ready to score the cube where it goes next...
    	
    }
}
