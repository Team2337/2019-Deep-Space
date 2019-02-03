package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.CargoEscalator.*;
import frc.robot.commands.CargoIntake.*;
import frc.robot.commands.CargoScore.*;
import frc.robot.commands.HatchBeak.*;
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
		
		
	    
	    ////////////////////////////////// 
	    
		/* ====== OPERATOR JOYSTICK ===== */
		
	 	operatorJoystick.RightTrigger				.whileHeld(new CGAcquireHatch());
		operatorJoystick.StripedButton				.whileHeld(new CGFireHatch());
		operatorJoystick.ThrottleTopThumbButton		.whenPressed(new goToPosition(550));
		operatorJoystick.ThrottleMidThumbButton		.whenPressed(new goToPosition(65));
		operatorJoystick.povUp						.whileHeld(new cargoIntakeIn(0.5));
		operatorJoystick.povRight					.whileHeld(new cargoEscalatorUp(0.5));
		operatorJoystick.povDown					.whileHeld(new cargoScoreOut(0.5));
		////////////////////////////////////
		
		
		/* ===== DRIVER STATION CONTROLS ===== */
		
		operatorControls.GreenButton	.whenPressed(new doNothing());
	
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
