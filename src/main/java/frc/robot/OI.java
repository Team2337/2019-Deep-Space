package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.Chassis.DriveByJoystickAndVision;
import frc.robot.commands.Chassis.PIDVisionDrive;
import frc.robot.commands.HatchBeak.hatchBeakExtend;
import frc.robot.commands.HatchBeak.hatchBeakLaunchersRetract;
import frc.robot.commands.HatchBeak.hatchBeakRetract;
import frc.robot.NerdyFiles.controller.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.GenericHID.RumbleType; 

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

	/*
	 * DriverJoystick
	 */
	public static Joystick				driverJoystick			= new Joystick(0);
	
	JoystickButton			driver_GreenA			= new JoystickButton(driverJoystick, 1);
	JoystickButton			driver_RedB				= new JoystickButton(driverJoystick, 2);
	JoystickButton			driver_BlueX			= new JoystickButton(driverJoystick, 3);
	JoystickButton			driver_YellowY			= new JoystickButton(driverJoystick, 4);
	JoystickButton			driver_BumperLeft		= new JoystickButton(driverJoystick, 5);
	JoystickButton			driver_BumperRight 		= new JoystickButton(driverJoystick, 6);
	JoystickButton			driver_Back				= new JoystickButton(driverJoystick, 7);
	JoystickButton			driver_Start			= new JoystickButton(driverJoystick, 8);
	JoystickButton			driver_LeftStick		= new JoystickButton(driverJoystick, 9);
	JoystickButton			driver_RightStick		= new JoystickButton(driverJoystick, 10);
	JoystickAnalogButton	driver_TriggerLeft		= new JoystickAnalogButton(driverJoystick, 2);
	JoystickAnalogButton	driver_TriggerRight		= new JoystickAnalogButton(driverJoystick, 3);
	JoystickPOVButton		driver_POVUp			= new JoystickPOVButton(driverJoystick, 0);
	JoystickPOVButton		driver_POVUpRight		= new JoystickPOVButton(driverJoystick, 45);
	JoystickPOVButton		driver_POVRight			= new JoystickPOVButton(driverJoystick, 90);
	JoystickPOVButton		driver_POVDownRight		= new JoystickPOVButton(driverJoystick, 135);
	JoystickPOVButton		driver_POVDown			= new JoystickPOVButton(driverJoystick, 180);
	JoystickPOVButton		driver_POVDownLeft		= new JoystickPOVButton(driverJoystick, 225);
	JoystickPOVButton		driver_POVLeft			= new JoystickPOVButton(driverJoystick, 270);
	JoystickPOVButton		driver_POVUpLeft		= new JoystickPOVButton(driverJoystick, 315);
	
	
	/* --- OPERATOR JOYSTICK - FLIGHT STICK --- */

	/*
		AXIS:
		#	Description		Direction   			Positive
		--	---------------	---------------------	--------
		0	Joystick tilt	Right/Left				Right
		1	Joystick tilt	Forward/back			Back
		2	Throttle tilt	Forward/Back			Back
		3	Joystick rotate	Right/Left (Rotation)	Right
		4	Throttle rocker	Right/Left (Rocker)		Right
	 */
	
	/*
	 * OperatorJoystick
	 */

	public static Joystick				operatorJoystick		= new Joystick(1);
	JoystickButton			operator_RightTrigger				= new JoystickButton(operatorJoystick, 1);	//Digital trigger on the back of the joystick
	JoystickButton			operator_StripedButton				= new JoystickButton(operatorJoystick, 2);	//The orange and black striped button on joystick
	JoystickButton			operator_RightKnucleButton			= new JoystickButton(operatorJoystick, 3);	//The button on the top-right of the joytstick
	JoystickButton			operator_L3							= new JoystickButton(operatorJoystick, 4);	//Button on the front right of the joystick
	
	JoystickButton			operator_ThrottleTopThumbButton		= new JoystickButton(operatorJoystick, 5);	//The highest button of the throttle's thumb buttons
	JoystickButton			operator_ThrottleMidThumbButton		= new JoystickButton(operatorJoystick, 6);	//The middle button of the throttle's thumb buttons
	JoystickButton			operator_ThrottleBottomThumbButton	= new JoystickButton(operatorJoystick, 7);	//The lowest button of the throttle's thumb buttons
	
	JoystickButton			operator_PalmButton					= new JoystickButton(operatorJoystick, 8);	//The button on the palmrest of the throttle
	JoystickButton			operator_TopIndexButton 			= new JoystickButton(operatorJoystick, 9);	//The higher button on the back right of the throttle
	JoystickButton			operator_BottomIndexButton			= new JoystickButton(operatorJoystick, 10);	//The lower button on the back right of the throttle
	
	JoystickButton			operator_SE							= new JoystickButton(operatorJoystick, 11); //The "SE" button on the throttle
	JoystickButton			operator_ST							= new JoystickButton(operatorJoystick, 12); //The "ST" button on the throttle
	
	JoystickPOVButton		operator_JoystickPOVUp				= new JoystickPOVButton(operatorJoystick, 0);
	JoystickPOVButton		operator_JoystickPOVUpRight			= new JoystickPOVButton(operatorJoystick, 45);
	JoystickPOVButton		operator_JoystickPOVRight			= new JoystickPOVButton(operatorJoystick, 90);
	JoystickPOVButton		operator_JoystickPOVDownRight		= new JoystickPOVButton(operatorJoystick, 135);
	JoystickPOVButton		operator_JoystickPOVDown			= new JoystickPOVButton(operatorJoystick, 180);
	JoystickPOVButton		operator_JoystickPOVDownLeft		= new JoystickPOVButton(operatorJoystick, 225);
	JoystickPOVButton		operator_JoystickPOVLeft			= new JoystickPOVButton(operatorJoystick, 270);
	JoystickPOVButton		operator_JoystickPOVUpLeft			= new JoystickPOVButton(operatorJoystick, 315);

	/*
	 * OperatorControl
	 */
	public static Joystick				operatorControls		= new Joystick(2);

	JoystickButton			operatorInt_GreenButton				= new JoystickButton(operatorJoystick, 19);
	JoystickButton			operatorInt_RedButton				= new JoystickButton(operatorJoystick, 20);
    JoystickButton 			operatorInt_ClearSwitch				= new JoystickButton(operatorJoystick, 15);
	JoystickButton 			operatorInt_YellowSwitch			= new JoystickButton(operatorJoystick, 18);
	JoystickButton 			operatorInt_BlackSwitch				= new JoystickButton(operatorJoystick, 17);
	JoystickButton 			operatorInt_BlueSwitch				= new JoystickButton(operatorJoystick, 16);
	JoystickButton 			operatorInt_WhiteButton				= new JoystickButton(operatorJoystick, 14);
	JoystickButton 			operatorInt_YellowButton			= new JoystickButton(operatorJoystick, 13);
	JoystickButton 			operatorInt_BlackButton 			= new JoystickButton(operatorJoystick, 11);
	JoystickButton 			operatorInt_BlueButton				= new JoystickButton(operatorJoystick, 12);
    
	public OI() {
		
		/* ====== DRIVER JOYSTICK ===== */
		
		driver_GreenA			.whenPressed(new DriveByJoystickAndVision());
		driver_RedB				.whileHeld(new PIDVisionDrive(.04, 0, 0.02, "turnInPlace"));
		driver_BlueX			.whenPressed(new doNothing()); 
		driver_YellowY			.whileHeld(new PIDVisionDrive(.04, 0, 0.02));
		
		driver_BumperLeft		.whenPressed(new hatchBeakExtend());
		driver_BumperRight		.whenPressed(new hatchBeakRetract());
		
		driver_Back				.whileHeld(new doNothing()); 
		driver_Start			.whenPressed(new hatchBeakLaunchersRetract());
		
		driver_LeftStick		.whenPressed(new doNothing()); 
		driver_RightStick		.whenPressed(new doNothing()); 
		
		driver_TriggerLeft		.whileHeld(new doNothing());
		driver_TriggerRight		.whileHeld(new doNothing());
		
		driver_POVUp			.whenPressed(new doNothing());  
		//driver_POVUpRight		.whenPressed(new _doNothing()); 
	    driver_POVRight			.whenPressed(new doNothing()); 
	   	//driver_POVDownRight	.whenPressed(new _doNothing()); 
	    driver_POVDown			.whenPressed(new doNothing()); 
	   	//driver_POVDownLeft	.whenPressed(new _doNothing()); 
	    driver_POVLeft			.whenPressed(new doNothing()); 
	   	//driver_POVUpLeft		.whenPressed(new _doNothing()); 
		
		   
		/* --- OPERATOR JOYSTICK - FLIGHT STICK --- */
		
		operator_RightTrigger			       .whenPressed(new doNothing());
		operator_StripedButton			       .whenPressed(new doNothing());
		operator_RightKnucleButton		       .whenPressed(new doNothing());
		operator_RightKnucleButton		       .whenPressed(new doNothing());
		operator_L3						       .whenPressed(new doNothing());
		operator_L3						       .whenPressed(new doNothing());
											
		operator_ThrottleTopThumbButton		   .whenPressed(new doNothing());
		operator_ThrottleMidThumbButton		   .whenPressed(new doNothing());
		operator_ThrottleBottomThumbButton	   .whenPressed(new doNothing());
											
		operator_PalmButton				       .whenPressed(new doNothing());
		operator_TopIndexButton				   .whenPressed(new doNothing());
		operator_BottomIndexButton		       .whenPressed(new doNothing());

		operator_SE						 	   .whenPressed(new doNothing()); 
		operator_ST						  	   .whenPressed(new doNothing());  

		operator_JoystickPOVUp			       .whenPressed(new doNothing());
		operator_JoystickPOVUpRight		       .whenPressed(new doNothing());
		operator_JoystickPOVUpRight			   .whenPressed(new doNothing());
		operator_JoystickPOVUpLeft		       .whenPressed(new doNothing());
		operator_JoystickPOVUpLeft			   .whenPressed(new doNothing());

		operator_JoystickPOVDownRight	       .whenPressed(new doNothing());
		operator_JoystickPOVDown		       .whenPressed(new doNothing());
		operator_JoystickPOVDownLeft	       .whenPressed(new doNothing());

		operator_JoystickPOVRight		       .whenPressed(new doNothing());
		operator_JoystickPOVLeft		       .whenPressed(new doNothing());


		/* ===== DRIVER STATION CONTROLS ===== */

		operatorInt_GreenButton	.whenPressed(new doNothing());
		operatorInt_RedButton	.whenPressed(new doNothing());

		operatorInt_ClearSwitch	.whenPressed(new doNothing());
		operatorInt_BlueSwitch	.whenPressed(new doNothing());
		operatorInt_BlackSwitch	.whenPressed(new doNothing());
		operatorInt_YellowSwitch.whenPressed(new doNothing());

		operatorInt_BlackButton	.whenPressed(new doNothing());
		operatorInt_BlueButton	.whenPressed(new doNothing());
		operatorInt_YellowButton.whenPressed(new doNothing());
		operatorInt_WhiteButton	.whenPressed(new doNothing());
		
		
		/* ===== DRIVER STATION CONTROLS ===== */
		
		operatorInt_GreenButton	.whenPressed(new doNothing());
		operatorInt_RedButton	.whenPressed(new doNothing());
		
		operatorInt_ClearSwitch	.whenPressed(new doNothing());
		operatorInt_BlueSwitch	.whenPressed(new doNothing());
		operatorInt_BlackSwitch	.whenPressed(new doNothing());
		operatorInt_YellowSwitch.whenPressed(new doNothing());
		
		operatorInt_BlackButton	.whenPressed(new doNothing());
		operatorInt_BlueButton	.whenPressed(new doNothing());
		operatorInt_YellowButton.whenPressed(new doNothing());
		operatorInt_WhiteButton	.whenPressed(new doNothing());
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
	
	/** 
	   * <p style="color:blue;"><strong>Function enables/disables controller vibration.</strong></p> 
	   * <p style="color:blue;"><i>Call with Robot.OI.rumble(OnOff)</i></p> 
	   * @author SomeNerd 
	   * @param joy Joystick to vibrate (EX: Robot.OI.driverJoystick)
	   * @param intensity Intensity of the vibration from 0 to 1 (EX: 0.75)
	   */ 
	
	public void rumble(Joystick joy, double intensity) {
		joy.setRumble(RumbleType.kLeftRumble, intensity);
		joy.setRumble(RumbleType.kRightRumble, intensity);
	}
}
