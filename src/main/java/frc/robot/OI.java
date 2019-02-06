package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.Auto.Pathway;
import frc.robot.commands.Auto.setpaths.autoSetPath;
import frc.robot.commands.Auto.setpaths.autoSetPathReverse;
import frc.robot.commands.Auto.setpaths.autoSetPathWithHold;
import frc.robot.commands.Auto.setpaths.autoWriteTrajectoryFile;
import frc.robot.commands.Auto.CommandGroups.CGJTurnFromLoadToCargoShipRight;
import frc.robot.commands.Auto.CommandGroups.CGPostProfileVision;
import frc.robot.commands.Auto.CommandGroups.CGTwoHatchAutoRight;
import frc.robot.commands.Vision.limeLightLEDOff;
import frc.robot.commands.Vision.limeLightLEDOn;
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
		double[][] valuesPID = Pathway.valuesPID;
		/* ====== DRIVER JOYSTICK ===== */
		
		driverJoystick.greenA			.whenPressed(new autoSetPath(Robot.curveFromToHatchRightT, valuesPID[1])); 
		driverJoystick.redB				.whenPressed(new autoSetPathReverse(Robot.initTrajectory, valuesPID[0])); 
		driverJoystick.blueX			.whenPressed(new autoSetPathWithHold(Robot.curveFromToHatchRightT, valuesPID[1])); 
		driverJoystick.yellowY			.whenPressed(new CGPostProfileVision());
		
		driverJoystick.bumperLeft		.whenPressed(new autoSetPath(Robot.NerdyPath.readFile("locations"), valuesPID[0]));
		driverJoystick.bumperRight		.whenPressed(new autoSetPath(Robot.driveForwardT, valuesPID[0]));
		
		driverJoystick.back				.whenPressed(new autoSetPathReverse(Robot.driveForwardFile, valuesPID[0])); 
		driverJoystick.start			.whileHeld(new autoWriteTrajectoryFile(Robot.driveForwardT, "test"));
		
		driverJoystick.leftStickButton		.whenPressed(new doNothing()); 
		driverJoystick.rightStickButton		.whenPressed(new doNothing()); 
		
		driverJoystick.triggerLeft		.whileHeld(new doNothing());
		driverJoystick.triggerRight		.whileHeld(new doNothing());
		
		driverJoystick.povUp			.whenPressed(new CGJTurnFromLoadToCargoShipRight());  
		//driver_POVUpRight		.whenPressed(new _doNothing()); 
	    driverJoystick.povRight			.whenPressed(new doNothing()); 
	   	//driver_POVDownRight	.whenPressed(new _doNothing()); 
		   driverJoystick.povDown			.whenPressed(new limeLightLEDOn()); 
	   	//driver_POVDownLeft	.whenPressed(new _doNothing()); 
		   driverJoystick.povLeft			.whenPressed(new doNothing()); 
	   	//driver_POVUpLeft		.whenPressed(new _doNothing()); 
	    
	    //////////////////////////////////
	    
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
