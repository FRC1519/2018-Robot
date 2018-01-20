package org.mayheminc.robot2018.autonomousroutines;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.mayheminc.robot2018.Robot;

//import org.mayheminc.robot2015.subsystems.Drive;
//import org.mayheminc.robot2015.autonomousroutines.*;
//import java.math.*;

/**
 *
 */
public class SelfTest extends CommandGroup {
    
    public  SelfTest() {
    	super("SelfTest");
    	    	
    	addParallel(new SelfTestDrive(SelfTestDrive.k_frontLeft));
    	addParallel(new SelfTestDrive(SelfTestDrive.k_frontRight));
    	addParallel(new SelfTestDrive(SelfTestDrive.k_backLeft));
    	addSequential(new SelfTestDrive(SelfTestDrive.k_backRight));
    	
//    	addParallel(new SelfTestIRSensor(SelfTestIRSensor.k_outerLeft));
//    	addParallel(new SelfTestIRSensor(SelfTestIRSensor.k_innerLeft));
//    	addParallel(new SelfTestIRSensor(SelfTestIRSensor.k_innerRight));
//    	addSequential(new SelfTestIRSensor(SelfTestIRSensor.k_outerRight));
    	
    	addSequential(new SelfTestGyro());
    }
}
