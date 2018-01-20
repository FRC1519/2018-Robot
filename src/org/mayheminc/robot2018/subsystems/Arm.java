package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.CANTalon;
//import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import org.mayheminc.util.MayhemTalonSRX;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.robot2018.RobotPreferences;

public class Arm extends Subsystem{
	
	public static final int FIRE_POSITION_COUNT = 595;
	public static final int BATTER_FIRE_POSITION_COUNT = 360;
	
	// trying -300 at start of CMP
	// -100 previously desired, but makes a bit tippy towards back ; was 0 for most of UNH
    public static final int UPRIGHT_POSITION_COUNT = -250;       
    public static final int LOW_POSITION_COUNT = -1000;        // was -850 for "CDF"
    public static final int PORTCULLIS_POSITION_COUNT = -1160;
    public static final int ZERO_POSITION_COUNT = -1320;
    public static final int FLOOR_POSITION_COUNT = ZERO_POSITION_COUNT - 70;
    
	public static final boolean REQUIRE_ARM_SUBSYSTEM = true;
	public static final boolean DONT_REQUIRE_ARM_SUBSYSTEM = false;
	
    public static final boolean ARM_BRAKE_ENGAGED = true;
    public static final boolean ARM_BRAKE_DISENGAGED = false;
    
    private static final int POSITION_TOLERANCE = 100;
    private static final int LAUNCH_TOLERANCE = 15;
    
    private static final int PID_THRESHOLD_COUNT	 = -340;
    
    private static final double P_UP = 2.5;   // initial tuning was 2.0
    private static final double I_UP = 0.0;   // initial tuning was 0.002
    private static final double D_UP = 50.0;  // was 360 at week 0 tournament
    
    private static final double P_DOWN = 2.5; // was 1.5 on 3/1/2016; KBS increased to help get to floor
    private static final double I_DOWN = 0.0;
    private static final double D_DOWN = 200.0; 
    
    private static final int DOWN_PID_SLOT = 0;    
    private static final int UP_PID_SLOT = 1;
    
    private static boolean usingUpSlot;
	
    private static final MayhemTalonSRX ARM_TALON = new MayhemTalonSRX(RobotMap.ARM_TALON);    
    private static Solenoid ARM_BRAKE;
   
    private enum ArmModes { AUTO_ACTIVE, AUTO_RELAXED, MANUAL };
    private ArmModes m_armMode = ArmModes.AUTO_RELAXED;
    
    public Arm() {
        ARM_BRAKE = new Solenoid(RobotMap.ARM_BRAKE_SOLENOID);
        
        if (m_armMode == ArmModes.AUTO_ACTIVE) {
        	ARM_TALON.changeControlMode(ControlMode.Position);
        } else {
        	ARM_TALON.changeControlMode(ControlMode.PercentOutput);
        }
        
        ARM_TALON.setProfile(DOWN_PID_SLOT);
        ARM_TALON.setPID(P_DOWN, I_DOWN, D_DOWN);
        ARM_TALON.setVoltageRampRate(96.0);
        ARM_TALON.configNominalOutputVoltage(+0.0f, -0.0f);
        ARM_TALON.configPeakOutputVoltage(+5f, -5f);
        
        ARM_TALON.setProfile(UP_PID_SLOT);
        ARM_TALON.setPID(P_UP, I_UP, D_UP);        
        ARM_TALON.setVoltageRampRate(96.0);
        ARM_TALON.configNominalOutputVoltage(+0.0f, -0.0f);
        ARM_TALON.configPeakOutputVoltage(+6f, -5f);
        
        // assuming the arm is starting in the upright position, start using the down PID slot
        setPIDSlot(DOWN_PID_SLOT);
                
        ARM_TALON.enableControl();
        
    }
    
    public void initDefaultCommand() {
    }
    
    public void setArmPID() {
    	double wheelP = RobotPreferences.getWheelP();
    	double wheelI = RobotPreferences.getWheelI();
    	double wheelD = RobotPreferences.getWheelD();
    	
    	ARM_TALON.setPID(wheelP, wheelI, wheelD);
    	
    	DriverStation.reportError("setWheelPIDF: " + wheelP + " " + wheelI + " " + wheelD + "\n", false);
    }
    
    public void zeroEncoder() {
    	ARM_TALON.setPosition(ZERO_POSITION_COUNT);
    }
    
    //************************** ARM AUTOMATIC (POSITIION) MODE*********************************
    
    public void setPIDSlot(int slot) {
    	ARM_TALON.setProfile(slot);
    	if (slot == UP_PID_SLOT) {
    		usingUpSlot = true;
    	}
    	if (slot == DOWN_PID_SLOT) {
    		usingUpSlot = false;
    	}
    }
    
    public double getEncoder() {
        return ARM_TALON.getPosition();
    }
    public double getSpeed() {
    	return ARM_TALON.getSpeed();
    }
    public double getVoltage() {
    	return ARM_TALON.getOutputVoltage();
    }
    
    /*
     * Tell the arm to physically move to an encoder count position.  This will turn off manual mode if it is enabled.
     */
    public void setPosition(double position) {    	
    	if (m_armMode != ArmModes.AUTO_ACTIVE) {
    		setAutoActiveMode();
    	}    	
        ARM_TALON.set((int) position);
    }
    
    public boolean isAtPosition(int positionToCheck) {
    	if (positionToCheck == FLOOR_POSITION_COUNT) {
    		positionToCheck = ZERO_POSITION_COUNT;
    	}
    	if (positionToCheck == FIRE_POSITION_COUNT){
    		return (getEncoder() >= (FIRE_POSITION_COUNT - POSITION_TOLERANCE));
    	}
    	int diff = Math.abs(positionToCheck - (int) getEncoder());
    	return (diff <= POSITION_TOLERANCE);
    }
    
    //************************************ MANUAL MODE *************************************
    
    public void moveArmManual (double power) {
    	if (m_armMode != ArmModes.MANUAL) {
    		setManualMode();
    	}    	
        setArmBrake(ARM_BRAKE_DISENGAGED);
    	ARM_TALON.set((int) power);
    }
    
    public void stopArm() {
    	moveArmManual(0.0);
    }
    
    private boolean isAutoMode() {
    	return (m_armMode != ArmModes.MANUAL);
    } 

    private boolean isAutoActiveMode() {
    	return (m_armMode == ArmModes.AUTO_ACTIVE);
    } 
    
    public void setManualMode() {    	
    	m_armMode = ArmModes.MANUAL;
        ARM_TALON.changeControlMode(ControlMode.PercentOutput);
        ARM_TALON.enableControl();
    }
    
    public void setAutoRelaxedMode() {
    	m_armMode = ArmModes.AUTO_RELAXED;
        ARM_TALON.changeControlMode(ControlMode.PercentOutput);
        ARM_TALON.enableControl();
    }
    
    private void setAutoActiveMode() {
    	m_armMode = ArmModes.AUTO_ACTIVE;
    	ARM_TALON.changeControlMode(ControlMode.Position);
        ARM_TALON.enableControl();    	
    }
    
    public void periodic() {
    	updatePIDSlot();
    	
    	if (m_armMode == ArmModes.MANUAL) {   
    		// if not automatic, move arm manually (also releases arm brake)
    		moveArmManual(Robot.oi.getArmManualControl());
    	} else {
    		// consider the automatic modes...
    		if ((ARM_TALON.getSetpoint() == FIRE_POSITION_COUNT) &&
    			(ARM_TALON.getPosition() >= FIRE_POSITION_COUNT)) {
    			setAutoRelaxedMode();
    		}
    	}
    }
    
    public void updatePIDSlot() {
    	// down PID should be used in the vast majority of circumstances.  The only situation in which up PID should be used
    	// would be one in which the arm is 1) below the PID_THRESHOLD_COUNT and 2) the arm is trying to move up.
    	
    	boolean armIsLow = false;
    	boolean armMovingUp = false;
    	
    	if (getEncoder() <= PID_THRESHOLD_COUNT) {
    		armIsLow = true;
    	}
    	
    	//if error is positive, then we're moving up
    	if (ARM_TALON.getError() >= 0) {
    		armMovingUp = true;
    	}
    	
    	if (ARM_TALON.getSetpoint() == BATTER_FIRE_POSITION_COUNT) {
    		// use more aggressive PID slot
    		setPIDSlot(UP_PID_SLOT);
    	} else if (armIsLow && armMovingUp) {
    		setPIDSlot(UP_PID_SLOT);
    	} else {
    		setPIDSlot(DOWN_PID_SLOT);    		
    	}    	
    }
    
    public void updateSmartDashboard() {
    	SmartDashboard.putBoolean("Arm Auto Mode", isAutoMode());
    	SmartDashboard.putBoolean("Arm Active Mode", isAutoActiveMode());
    	SmartDashboard.putNumber("Arm Encoder", getEncoder());
    	SmartDashboard.putNumber("Arm Speed", getSpeed());
    	
    	SmartDashboard.putNumber("Arm Voltage", getVoltage());
		SmartDashboard.putNumber("Arm Setpoint", ARM_TALON.getSetpoint());
		SmartDashboard.putNumber("Arm Error", ARM_TALON.getError()); 
		
		SmartDashboard.putBoolean("Arm using DOWN_PID_SLOT", !usingUpSlot);
		
		SmartDashboard.putNumber("Arm OI joystick value", Robot.oi.getArmManualControl());  
		
		SmartDashboard.putBoolean("Arm Section", true); //not to display useful information; this box serves as a light-blue 'container' to hold other arm-related indicators
    }
    
    public boolean getArmBrake() {
    	return(ARM_BRAKE.get());
    }
    
    public void setArmBrake(boolean position) {
    	ARM_BRAKE.set(position);
    }
    
    public boolean isInLaunchPosition() {
    	int diff = Math.abs(FIRE_POSITION_COUNT - (int) getEncoder());
    	return (diff <= LAUNCH_TOLERANCE);
    }
}
