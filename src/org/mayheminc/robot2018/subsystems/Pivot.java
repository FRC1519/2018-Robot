package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.util.MayhemTalonSRX;
//import org.mayheminc.util.MayhemTalonSRX;
import org.mayheminc.util.PidTunerObject;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;

/**
 * The Pivot lifts the intake to the upright position so the elevator can
 * take the cube.
 */
public class Pivot extends Subsystem implements PidTunerObject {

	public static final int UPRIGHT_POSITION = 2700;   // PRAC 3/12/2018 // 2800 had been used at comp bot & WPI
	public static final int SPIT_POSITION = 2100; //Was 1900 02-02-2018
	public static final int FLIP_CUBE_POSITION = 700;
	public static final int EXCHANGE_POSITION = 700;//was 500 02-02-2018
	public static final int DOWNWARD_POSITION = 0;
	public static final int CAMERA_THRESHOLD = UPRIGHT_POSITION/2;
	
	public static final int PIVOT_TOLERANCE = 150; // 75 seemed like not quite enough at Week 1
	
	private static final int DOWN_TOLERANCE = 10;
	private static final int UP_TOLERANCE = 10;
	
	private static final double ZERO_MAX_CURRENT = 1.0; // Amps
	private static final double ZERO_MOTOR_POWER = 0.20; // was 0.25 at Week 1; KBS trying to lower this a bit
	private static final double HOLD_DOWN_MOTOR_POWER = -0.10;  // was -0.15 at Week 1; KBS trying to lower this a bit
	private static final double HOLD_UP_MOTOR_POWER = 0.10;

	private static final int ZEROING_LOOPS_TO_WAIT_TO_MOVE = 10;  // at start of zeroing, loops to wait for arm to move
	private static final int ZEROING_LOOPS_TO_WAIT_FOR_SAME = 5;  // at end of zeroing, loops to wait with same value
	
	MayhemTalonSRX m_pivotMotor = new MayhemTalonSRX(RobotMap.PIVOT_TALON);
	int m_desiredPosition = UPRIGHT_POSITION;  // default is the "starting position"
	boolean m_manualMode = false;              // default to automatic mode
	boolean m_zero = false;                    // flag for if zeroing has been performed; initialized to false
	int m_zeroWaitToMove;
	int m_zeroWaitForSame;
	int m_priorPositionWhileZeroing;
	int m_activeCamera;
	
	public Pivot()
	{
		super();
		
		m_pivotMotor.configNominalOutputForward(0.0,  0);
		m_pivotMotor.configNominalOutputReverse(0.0, 0);
		m_pivotMotor.configPeakOutputForward(1.0,  0);      // was 0.8 at Week 1; was 0.5 before bag day; for going up
		m_pivotMotor.configPeakOutputReverse(-0.8,  0);     // was -0.6 at Week 1; was -0.3 before bag day; for going down
		
		m_pivotMotor.configClosedloopRamp(0.10, 0);
		m_pivotMotor.configOpenloopRamp(0.10,  0);
		
		m_pivotMotor.config_kP(0, 2.0, 0);                    // was 3.0 at week 1
		m_pivotMotor.config_kI(0, 0.0, 0);
		m_pivotMotor.config_kD(0, 120.0, 0);
		m_pivotMotor.config_kF(0, 0.0, 0);
		
		m_pivotMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		m_pivotMotor.setInverted(false);
		m_pivotMotor.setSensorPhase(true);
		
    	m_pivotMotor.enableControl();	
	}
    protected void initDefaultCommand() { }
    
    
    /**
     * Commence zeroing of the pivot.  Let periodic run the zero routine.  Set the zero flag.  Set the counters.  
     */
    public void commenceZeroingPivot() {
    	// set flag indicating that zeroing is in process
    	m_zero = true;
    	
    	// initialize counters used in the zeroing procedure
    	m_zeroWaitToMove = ZEROING_LOOPS_TO_WAIT_TO_MOVE;
    	m_zeroWaitForSame = ZEROING_LOOPS_TO_WAIT_FOR_SAME;
    	
    	// set the motor power to the power to be used for zeroing
    	m_pivotMotor.set(ControlMode.PercentOutput, ZERO_MOTOR_POWER);
    }
    
    
    /**
     * Where ever the pivot is, hold that position.
     */
    public void holdCurrentPosition()
    {
    	// "hold" current position is the same as a "moveTo" the current position
    	setDesiredPosition(m_pivotMotor.getSelectedSensorPosition(0));
    }
    
    
    /**
     * Set a new desired position for the arm.
     */
    public void setDesiredPosition(int position)
    {
    	// Simply set the mode to be automatic (not manual) and set the desiredPosition;
    	// the "periodic" control loop takes care of appropriately commanding the pivot motor
    	m_manualMode = false;
    	m_desiredPosition = position;
    }
    
    /**
     * Get the current position of the arm pivot.
     */
    public int getCurrentPosition()
    {
    	return m_pivotMotor.getSelectedSensorPosition(0);
    }
    
    /**
     * Returns true if the arm pivot is "close enough" to the desired position.
     */
    public boolean isAtPosition()
    {
    	int diff = Math.abs(m_pivotMotor.getSelectedSensorPosition(0) - m_desiredPosition);
    	return diff < PIVOT_TOLERANCE;
    }
    
    /**
     * Perform the zeroing procedure while in "periodic"
     */
    public void periodicZero() {
    	// While in the process of zeroing, this function will be called exactly once during each periodic loop
		
    	// we only get here if zeroing is in progress.
    	
		// First, check to see if we have waited long enough to start moving
		if ( m_zeroWaitToMove > 0 )
		{
			// if we haven't waited long enough for the arm to move, keep waiting
				m_zeroWaitToMove--;
		} else {  
			// the arm has started moving in the zeroing procedure, let's see where it is
			int currentPosition = m_pivotMotor.getSelectedSensorPosition(0);

			// see if the encoder hasn't moved since the prior loop...
			if ( currentPosition == m_priorPositionWhileZeroing )
			{
				// count down the same encoder counts.
				m_zeroWaitForSame--;
				if ( m_zeroWaitForSame == 0 )
				{
					// zero is done; we are in the upright position
					m_zero = false;
					m_pivotMotor.setSelectedSensorPosition(UPRIGHT_POSITION, 0, 0);
					m_desiredPosition = UPRIGHT_POSITION;
					m_manualMode = false;
				}
			} else {  
				// the arm is still moving, reset the "wait for same" counter
				m_zeroWaitForSame = 5;
				// reset the encoder count we are looking for.
				m_priorPositionWhileZeroing = currentPosition;
			}
		}
    }
    
    
    /**
     * Perform the "periodic" update of the arm pivot.
     */
    public void periodic()
    {
    	// if we are zeroing the pivot...
    	if ( m_zero )
    	{
    		periodicZero();
    	} 
    	else // not zeroing
    	{
    		// find out if the joystick is commanding a movement.  If so, go to manual mode
        	double manualPowerRequested = Robot.oi.pivotArmPower();
        	if ( Math.abs(manualPowerRequested) > 0.01 )
        	{
        		m_manualMode = true;
        	} else {  // joystick not commanding movement
        		if (m_manualMode) { // we must have just previously been in manual mode, set to "hold position"
        			holdCurrentPosition();
        		}
        	}
        	
    		// command the arm appropriately depending upon mode
    		if ( m_manualMode )
    		{
    			m_pivotMotor.set(ControlMode.PercentOutput, manualPowerRequested);
    		}
    		else // positioning mode
    		{
    			// determine the current position of the arm
    			int currentPosition = m_pivotMotor.getSelectedSensorPosition(0);
    			
    			// handle position control with two special cases and the third "normal" case:
    			// 1 - if we want a position that is at or below the DOWNWARD_POSITION and 
    			//     we are currently close enough to the DOWNWARD_POSITION, press down lightly
    			// 2 - if we want a position that is at or above the UPRIGHT_POSITION and
    			//     we are currently close enough to the UPRIGHT_POSITION, press up lightly
    			// 3 - otherwise just go to the desired position
    			
    			
    			// 1 - if we want a position that is at or below the DOWNWARD_POSITION and 
    			//     we are currently close enough to the DOWNWARD_POSITION, press down lightly
    			if ( ( m_desiredPosition <= DOWNWARD_POSITION ) &&
    					( currentPosition < (DOWNWARD_POSITION + DOWN_TOLERANCE)) ) 
    			{
    				
    				m_pivotMotor.set(ControlMode.PercentOutput,  HOLD_DOWN_MOTOR_POWER);;
    			} else if ( ( m_desiredPosition >= UPRIGHT_POSITION ) &&
    					    ( currentPosition > (UPRIGHT_POSITION - UP_TOLERANCE)) ) 
    			// 2 - if we want a position that is at or above the UPRIGHT_POSITION and
    			//     we are currently close enough to the UPRIGHT_POSITION, press up lightly
    			{
    				m_pivotMotor.set(ControlMode.PercentOutput,  HOLD_UP_MOTOR_POWER);;
    			} else {
        			// 3 - otherwise just go to the desired position
					m_pivotMotor.set(ControlMode.Position, m_desiredPosition);	
    			}
    		}  // end if positioning mode
     	}  // end if zeroing
    	
    	setActiveCamera();
    }
    
    private void setActiveCamera()
    {
    	if ( m_manualMode )
    	{
    		if( getCurrentPosition() < CAMERA_THRESHOLD)
    		{
    			m_activeCamera = 0;
    		}
    		else
    		{
    			m_activeCamera = 1;
    		}
    	}
    	else
    	{
    		if( m_desiredPosition < CAMERA_THRESHOLD )
    		{
    			m_activeCamera = 0;
    		}
    		else
    		{
    			m_activeCamera = 1;
    		}
    	}
    	SmartDashboard.putNumber("Active Camera",  m_activeCamera);
    }
    
    public void UpdateSmartDashboard()
    {
    	SmartDashboard.putNumber("Pivot Encoder Pos", m_pivotMotor.getSelectedSensorPosition(0));
    	SmartDashboard.putBoolean("Pivot Manual Mode", m_manualMode);
    }
    
    
    ////////////////////////////////////////////////////////
    // PidTunerObject
    ////////////////////////////////////////////////////////
	@Override
	public double getP() { return m_pivotMotor.getP();}
	@Override
	public double getI() { return m_pivotMotor.getI();}
	@Override
	public double getD() { return m_pivotMotor.getD();}
	@Override
	public double getF() { return m_pivotMotor.getF();}
	@Override
	public void setP(double d) {m_pivotMotor.config_kP(0,  d,  0); }
	@Override
	public void setI(double d) {m_pivotMotor.config_kI(0,  d,  0); }
	@Override
	public void setD(double d) {m_pivotMotor.config_kD(0,  d,  0); }
	@Override
	public void setF(double d) {m_pivotMotor.config_kF(0,  d,  0); }
}
