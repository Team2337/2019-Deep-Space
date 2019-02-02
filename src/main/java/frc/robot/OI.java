package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.Lift.*;
import frc.robot.nerdyfiles.controller.*;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	/*
	 * Controllers
	 */
	public NerdyXbox			driverJoystick			= new NerdyXbox(0);
	public NerdyFlightStick		operatorJoystick		= new NerdyFlightStick(1);
	public NerdyOperatorStation	operatorControls		= new NerdyOperatorStation(2);

    
	public OI() {
		
		/* ====== DRIVER JOYSTICK ===== */
		
		driverJoystick.greenA			.whenPressed(new goToPosition(550));
		driverJoystick.redB				.whenPressed(new goToPosition(65));
		driverJoystick.povUp			.whenPressed(new doNothing());
	    
	    ////////////////////////////////// 
	    
		/* ====== OPERATOR JOYSTICK ===== */
		
		operatorJoystick.StripedButton	.whenPressed(new doNothing());
	
		////////////////////////////////////
		
		
		/* ===== DRIVER STATION CONTROLS ===== */
		
		operatorControls.GreenButton	.whenPressed(new doNothing());
		operatorControls.YellowSwitch	.whileHeld(new liftWithJoystickOverride());
	
		///////////////////////////////////////// 
	}

	public Joystick getDriverJoystick() {
		return driverJoystick;
	}
	
	public Joystick getOperatorJoystick() {
		return operatorJoystick;
	}
	
	public Joystick getOperatorControls() {
		return operatorControls;
	}
}
