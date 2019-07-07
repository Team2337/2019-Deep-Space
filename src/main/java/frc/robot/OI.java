package frc.robot;

import frc.robot.commands.AirCompressor.*;
import frc.robot.commands.Auto.*;
import frc.robot.commands.CargoBigBrother.*;
import frc.robot.commands.HatchBeak.*;
import frc.robot.commands.HatchLauncher.*;
import frc.robot.commands.Lift.*;
import frc.robot.commands.Shifter.*;
import frc.robot.commands.Vision.*;
import frc.robot.commands.Chassis.*;
import frc.robot.commands.ClimberDeploy.*;
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
	public NerdyUltimateXboxDriver		driverJoystick			= new NerdyUltimateXboxDriver(0);
	public NerdyUltimateXboxOperator	operatorJoystick		= new NerdyUltimateXboxOperator(1);
	public NerdyOperatorStation			operatorControls		= new NerdyOperatorStation(2);

	public double[][] valuesPID = pathway.valuesPID;

	/**
	 * Maps out the commands to the buttons on the controllers
	 * 
	 * <h3> Unusable Buttons: </h3>
	 * <ul>
	 * <li>Driver Joystick</li>
	 * 	<ul>
	 * 	<li>Right Trigger</li>
	 * 		<ul>
	 * 		<li>Toggles the max speed of the chassis when "yeeting"</li>
	 * 		</ul>
	 * 	</ul>
	 * </ul>
	 */
	public OI() {

		/* ====== DRIVER JOYSTICK ===== */

		// Assigned to Quick Turn in Chassis.driveByJoystick - DO NOT USE

		driverJoystick.bumperLeft					.whenPressed(new shifterLowGear());
		driverJoystick.bumperLeft					.whenReleased(new shifterHighGear());

		driverJoystick.bumperRight					.whenPressed(new autoEndAuto());

		
		// driverJoystick.triggerRight				.whenPressed(); //Do not assign
		
		driverJoystick.triggerLeft                  .whileHeld(new OmniPIDVisionDriveWithSlow(0.1, 0.06));//PIDVisionDriveWithSlow(0.05, 0, 0));
		driverJoystick.rightStickButton				.whenPressed(new limeLightLEDOn());

		// driverJoystick.redB							.whileHeld(new CGDriveToFarRocketFromLoadRight());
		// driverJoystick.blueX						.whileHeld(new CGDriveToFarRocketFromLoadLeft());
		driverJoystick.yellowY						.whenPressed(new removeNeoOpenLoopRampRate());		

		driverJoystick.back							.whileHeld(new driveByJoystickWithSlowTurn(true));
		// driverJoystick.start						.whenPressed(new CGOmniHatchRightLowNearRocketLowFarRocketLow());
	    ////////////////////////////////// 
	    
		/* ====== OPERATOR JOYSTICK ===== */
		
		operatorJoystick.triggerLeft				.whenPressed(new hatchBeakClose());
		operatorJoystick.triggerLeft				.whenReleased(new hatchBeakOpen());
		operatorJoystick.rightStickButton			.whileHeld(new CGNewScoreHatch()); //CGScoreHatch
		operatorJoystick.rightStickButton			.whenReleased(new CGNewRetractLaunchers()); //CGRetractLaunchers

		operatorJoystick.triggerRight				.whileHeld(new cargoBigBrotherIntake());
		operatorJoystick.bumperRight				.whileHeld(new cargoBigBrotherEject());

		operatorJoystick.back						.whileHeld(new cargoBigBrotherScore());
		operatorJoystick.start						.whileHeld(new cargoBigBrotherIntake());

		//Do not set anything to POV right or left because it cannot run at the same time as other POV buttons
		operatorJoystick.povUp						.whenPressed(new goToPosition(Robot.Lift.hatchMidScorePosition));
		operatorJoystick.povUp						.whenReleased(new stayAtPosition());
		operatorJoystick.bumperLeft					.whenPressed(new hatchLauncherExtend());
		operatorJoystick.bumperLeft					.whenReleased(new hatchLauncherRetract());
		operatorJoystick.povDown					.whenPressed(new goToPosition(Robot.Lift.hatchLowScorePosition));
		operatorJoystick.povDown					.whenReleased(new stayAtPosition());

		operatorJoystick.greenA						.whenPressed(new goToPosition(Robot.Lift.cargoIntakePosition));
		operatorJoystick.greenA						.whenReleased(new stayAtPosition());

		operatorJoystick.redB						.whenPressed(new goToPosition(Robot.Lift.cargoShipScorePosition));
		operatorJoystick.redB						.whenReleased(new stayAtPosition());

		operatorJoystick.yellowY					.whenPressed(new goToPosition(Robot.Lift.cargoMidScorePosition));
		operatorJoystick.yellowY					.whenReleased(new stayAtPosition());

		////////////////////////////////////

		/* ===== DRIVER STATION CONTROLS ===== */
		operatorControls.BlackSwitch				.whenPressed(new readyClimber());
		operatorControls.BlackSwitch				.whenReleased(new unreadyClimber());
		operatorControls.BlackButton				.whenPressed(new climbBigBrother());
		operatorControls.BlackButton				.whenReleased(new stayAtPosition());

		operatorControls.BlueButton					.whenPressed(new deployClimber());
		operatorControls.BlueButton					.whenReleased(new unreadyClimber());
		

		operatorControls.YellowSwitch				.whileHeld(new compressorTurnOff());
		operatorControls.YellowSwitch				.whenReleased(new compressorTurnOn());
		operatorControls.YellowButton				.whileHeld(new liftToClimbTop(Robot.Lift.climbWheelsUpPosition));

		operatorControls.WhiteButton				.whenPressed(new restoreSoftLimits());


		
	
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