package org.mayheminc.robot2018.subsystems;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shifter extends Subsystem {
	// Solenoid
	Solenoid m_shifter;

	public Shifter()
	{
		m_shifter = new Solenoid(RobotMap.SHIFTING_SOLENOID);
	}
	
    public void initDefaultCommand() {}
	//**********************************SHIFTER PISTONS***********************************************

	public static final boolean HIGH_GEAR = true;
	public static final boolean LOW_GEAR = !HIGH_GEAR;
	private boolean m_highGear = LOW_GEAR; // flag for current gear setting

	public static final boolean AUTO_SHIFT = true;
	public static final boolean MANUAL_SHIFT = false;
	private boolean m_autoShift = true;      // flag for automatic shifting

	public void setShifter(boolean position){
		m_shifter.set(position);
		m_highGear = position;
	}

	public final void setGear(boolean gear) {
		m_priorShiftTime = Timer.getFPGATimestamp();
		m_highGear = gear;
		if (m_highGear == HIGH_GEAR) {
			setShifter(HIGH_GEAR);  
		} else {
			setShifter(LOW_GEAR);
		}
	}

	public boolean getGear() {
		return m_highGear;
	}

	public void setAutoShift(boolean useAutoShift) {
		m_autoShift = useAutoShift;
	}

	// shift speeds are in inches per second.

	//private static final double SHIFT_RATIO = 2.56;    // Gear spread is 2.56:1 in sonic shifter
	private static final double SHIFT_TO_HIGH = 400.0;  // numbers determined empirically
	private static final double SHIFT_TO_LOW = SHIFT_TO_HIGH / 2.56;   // numbers determined empirically
	private static final double SHIFT_DELAY = .5;
	private static double m_priorShiftTime = Timer.getFPGATimestamp();

	public void updateAutoShift() {
		double currentSpeed = Robot.drive.getLeftSpeed() > Robot.drive.getRightSpeed() ? Robot.drive.getLeftSpeed() : Robot.drive.getRightSpeed();

		if (m_autoShift &&  
				(Robot.oi.forceLowGear() ||  
						Timer.getFPGATimestamp() > m_priorShiftTime + SHIFT_DELAY)) {    

			// general approach:
			//     if currentAverageSpeed is high enough, shift into high gear
			//     if currentAverageSpeed is low, shift into low gear
			// NOTE:  un-needed shifts into high could be avoided by checking
			//        that driver still wants to go faster (i.e., commanding
			//        significant power) when considering a shift into high

			if ((!Robot.oi.forceLowGear() && Math.abs(currentSpeed) > SHIFT_TO_HIGH) &&
					(Math.abs(Robot.oi.driveThrottle()) > 0.9)) {
				setGear(HIGH_GEAR);
			} else if (Robot.oi.forceLowGear() || 
					Math.abs(currentSpeed) < SHIFT_TO_LOW) {
				setGear(LOW_GEAR);
			} else {
				// don't need to shift to high or low; stay in current gear
			}  
		}
	}	

	public void updateSmartDashboard()
	{
		SmartDashboard.putBoolean("Low Gear", !m_highGear);
		SmartDashboard.putBoolean("Auto Shift Mode", m_autoShift);	
	}
}
