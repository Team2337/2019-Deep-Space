package frc.robot;

import frc.robot.commands.Auto.autoLineSensorDrive;
import frc.robot.commands.Auto.pathway;
import frc.robot.commands.Auto.CommandGroups.CGTwoHatchAutoRight;
import frc.robot.commands.Auto.setpaths.*;
import frc.robot.commands.AutoHatchKicker.hatchKickerExtend;
import frc.robot.commands.AutoHatchKicker.hatchKickerRetract;
import frc.robot.commands.CargoDrawbridge.*;
import frc.robot.commands.CargoEscalator.*;
import frc.robot.commands.CargoIntake.*;
import frc.robot.commands.CargoScore.*;
import frc.robot.commands.Shifter.*;
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
	public NerdyXbox				driverJoystick			= new NerdyXbox(0);
	public NerdyXbox				operatorJoystick		= new NerdyXbox(1);
	public NerdyOperatorStation		operatorControls		= new NerdyOperatorStation(2);

	double valuesPID[][] = pathway.valuesPID;

	public OI() {

		/* ====== DRIVER JOYSTICK ===== */
		driverJoystick.greenA						.whenPressed(new autoSetPathReverse(Robot.driveForwardT, valuesPID[0]));
		driverJoystick.redB							.whenPressed(new autoSetPath(Robot.curveFromToHatchRightT, valuesPID[1]));
		driverJoystick.blueX						.whenPressed(new autoSetPathReverseFile(Robot.NerdyPath.readFile("backUpDriveDefault"), valuesPID[0], 0.5));
		driverJoystick.yellowY						.whenPressed(new CGTwoHatchAutoRight());
	
		driverJoystick.bumperRight					.whenPressed(new shifterHighGear());
		driverJoystick.bumperLeft					.whenPressed(new shifterLowGear());
		// driverJoystick.povLeft                      .whenPressed(new hatchKickerExtend());
		// driverJoystick.povRight                     .whenPressed(new hatchKickerRetract());
		// driverJoystick.povDown						.whenPressed(new autoLineSensorDrive());
	    
	    ////////////////////////////////// 
	    
		/* ====== OPERATOR JOYSTICK ===== */
		
		// operatorJoystick.povUp					.whenPressed(new goToPosition(500));
		// operatorJoystick.povDown					.whenPressed(new goToPosition(300));

		// operatorJoystick.bumperRight				.whenPressed(new hatchBeakClose());
		// operatorJoystick.bumperRight				.whenReleased(new hatchBeakOpen());
		// operatorJoystick.bumperLeft					.whenPressed(new hatchLauncherExtend());
		// operatorJoystick.bumperLeft					.whenReleased(new hatchLauncherRetract());

		operatorJoystick.start						.whenPressed(new raiseTheDrawbridge());
		operatorJoystick.back						.whenPressed(new lowerTheDrawbridge());

		operatorJoystick.bumperRight				.whileHeld(new cargoIntakeIn(1));
		operatorJoystick.triggerRight				.whileHeld(new cargoIntakeOut(1));
		operatorJoystick.triggerLeft				.whileHeld(new cargoEscalatorUp(1));
		operatorJoystick.bumperLeft					.whileHeld(new cargoEscalatorDown(1));
		operatorJoystick.blueX						.whileHeld(new cargoScoreIn(1));
		operatorJoystick.yellowY					.whileHeld(new cargoScoreOut(1));
		////////////////////////////////////

		/* ===== DRIVER STATION CONTROLS ===== */
		
		// operatorControls.YellowSwitch	.whileHeld(new liftWithJoystickOverride());
	
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
