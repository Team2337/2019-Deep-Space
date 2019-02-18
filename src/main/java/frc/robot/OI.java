package frc.robot;

import frc.robot.commands.AutoHatchKicker.*;
import frc.robot.commands.CargoBigBrother.*;
import frc.robot.commands.CargoDrawbridge.*;
import frc.robot.commands.ClimberPneumatics.platformGrab;
import frc.robot.commands.HatchBeak.*;
import frc.robot.commands.HatchLauncher.*;
import frc.robot.commands.Lift.*;
import frc.robot.commands.Shifter.*;
import frc.robot.nerdyfiles.motorTest;
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
	public NerdyUltimateXbox		driverJoystick			= new NerdyUltimateXbox(0);
	public NerdyUltimateXbox		operatorJoystick		= new NerdyUltimateXbox(1);
	public NerdyOperatorStation		operatorControls		= new NerdyOperatorStation(2);
	public NerdyUltimateXbox		programmingJoystick		= new NerdyUltimateXbox(3);

	public OI() {

		/* ====== DRIVER JOYSTICK ===== */
		
		driverJoystick.bumperRight					.whenPressed(new shifterHighGear());
		driverJoystick.bumperLeft					.whenPressed(new shifterLowGear());

	    
	    ////////////////////////////////// 
	    
		/* ====== OPERATOR JOYSTICK ===== */
		
		operatorJoystick.triggerLeft				.whenPressed(new hatchBeakClose());
		operatorJoystick.triggerLeft				.whenReleased(new hatchBeakOpen());
		operatorJoystick.bumperLeft					.whenPressed(new hatchLauncherExtend());
		operatorJoystick.bumperLeft					.whenReleased(new hatchLauncherRetract());

		operatorJoystick.triggerRight				.whileHeld(new cargoBigBrotherIntake());
		operatorJoystick.bumperRight				.whileHeld(new cargoBigBrotherEject());

		operatorJoystick.macroFour					.whileHeld(new cargoBigBrotherScore());
		operatorJoystick.macroSix					.whileHeld(new cargoBigBrotherScore());

		operatorJoystick.macroTwo					.whenPressed(new lowerTheDrawbridge());
		operatorJoystick.povRight					.whenPressed(new raiseTheDrawbridge());

		operatorJoystick.povUp						.whenPressed(new setTargetPosition(Robot.Lift.hatchLowScorePosition));
		operatorJoystick.povDown					.whenPressed(new setTargetPosition(Robot.Lift.hatchMidScorePosition));

		operatorJoystick.greenA						.whenPressed(new setTargetPosition(Robot.Lift.cargoMidScorePosition));
		operatorJoystick.redB						.whenPressed(new setTargetPosition(Robot.Lift.cargoShipScorePosition));
		operatorJoystick.blueX						.whenPressed(new setTargetPosition(Robot.Lift.climbPosition));
		operatorJoystick.yellowY					.whenPressed(new setTargetPosition(Robot.Lift.cargoLowScorePosition));

		////////////////////////////////////

		/* ===== DRIVER STATION CONTROLS ===== */
		
		// operatorControls.YellowSwitch	.whileHeld(new liftWithJoystickOverride());
	
		///////////////////////////////////////// 

		////////////////////////////////////

		/* ===== PROGRAMMING JOYSTICK CONTROL ===== */
		
		// programmingJoystick.greenA					.whileHeld(new motorTest(0.5));
		// programmingJoystick.redB					.whileHeld(new motorTest(-0.5));
	
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
