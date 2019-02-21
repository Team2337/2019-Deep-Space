package frc.robot;

import frc.robot.commands.CargoBigBrother.*;
import frc.robot.commands.HatchBeak.*;
import frc.robot.commands.HatchLauncher.*;
import frc.robot.commands.Lift.*;
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
	public NerdyUltimateXbox		driverJoystick			= new NerdyUltimateXbox(0);
	public NerdyUltimateXbox		operatorJoystick		= new NerdyUltimateXbox(1);
	public NerdyOperatorStation		operatorControls		= new NerdyOperatorStation(2);

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

		operatorJoystick.start						.whileHeld(new cargoBigBrotherScore());
		operatorJoystick.macroSix					.whileHeld(new cargoBigBrotherScore());

		// operatorJoystick.back						.whenPressed(new lowerTheDrawbridge());
		// operatorJoystick.back						.whenReleased(new raiseTheDrawbridge());

		// operatorJoystick.leftStickUp	/*TODO: */	.whenPressed(new setTargetPosition(Robot.Lift.targetPosition + 10));
		// operatorJoystick.leftStickDown				.whenPressed(new setTargetPosition(Robot.Lift.targetPosition - 10));

		operatorJoystick.povUp						.whenPressed(new goToPosition(Robot.Lift.hatchMidScorePosition));
		operatorJoystick.povUp						.whenReleased(new goToPositionStay());
		operatorJoystick.povDown					.whenPressed(new goToPosition(Robot.Lift.hatchLowScorePosition));
		operatorJoystick.povDown					.whenReleased(new goToPositionStay());

		operatorJoystick.greenA						.whenPressed(new goToPosition(Robot.Lift.cargoIntakePosition));
		operatorJoystick.greenA						.whenReleased(new goToPositionStay());

		operatorJoystick.redB						.whenPressed(new goToPosition(Robot.Lift.cargoShipScorePosition));
		operatorJoystick.redB						.whenReleased(new goToPositionStay());

		operatorJoystick.blueX						.whenPressed(new goToPosition(Robot.Lift.climbPosition));
		operatorJoystick.blueX						.whenReleased(new goToPositionStay());

		operatorJoystick.yellowY					.whenPressed(new goToPosition(Robot.Lift.cargoMidScorePosition));
		operatorJoystick.yellowY					.whenReleased(new goToPositionStay());

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
