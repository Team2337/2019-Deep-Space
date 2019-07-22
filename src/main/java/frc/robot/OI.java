package frc.robot;

import frc.robot.commands.stopAllButChassis;
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
	 * <h3>Button Map</h3>
	 * <ul>
	 * <li>Driver Joystick</li>
	 * 	<ul>
	 * 	<li>Right Trigger</li>
	 * 		<ul>
	 * 		<li>Toggles the max speed of the chassis when "yeeting"</li>
	 * 		</ul>
	 * 	</ul>
	 * </ul>
	 * <p><strong style="font-size: larger">NOTE:</strong> More than one POV button on the controllers <strong>cannot</strong> be used at once</p>
	 */
	public OI() {

		/***************************/
		/* ----------------------- */
		/* --- DRIVER JOYSTICK --- */
		/* ----------------------- */
		/***************************/

		driverJoystick.bumperLeft					.whenPressed(new shifterLowGear());
		driverJoystick.bumperLeft					.whenReleased(new shifterHighGear());
		
		driverJoystick.bumperRight					.whenPressed(new autoEndAuto());
		
		
		driverJoystick.triggerRight					.whenPressed(new setYeetSpeed(1.0)); 
		driverJoystick.triggerRight					.whenReleased(new setYeetSpeed(0.85));
		driverJoystick.triggerRight					.whenInactive(new setYeetSpeed(0.85)); //TODO: test to make sure this actually works :)
		
		driverJoystick.triggerLeft                  .whileHeld(new OmniPIDVisionDriveWithSlow(0.05, 0));//PIDVisionDriveWithSlow(0.05, 0, 0)); //OmniPIDVisionDriveWithSlow(0.1, 0.06)
		driverJoystick.leftStickButton				.whenPressed(new limeLightLEDOn());
		
		driverJoystick.rightStickButton				.whileHeld(new driveByJoystickCurvQuickTurn(true));
		// driverJoystick.rightStickButton				.whenPressed(new shifterLowGear());
		// driverJoystick.rightStickButton				.whenReleased(new shifterHighGear());

		driverJoystick.yellowY						.whenPressed(new removeNeoOpenLoopRampRate());		

		driverJoystick.back							.whileHeld(new driveByJoystickWithSlowTurn(true));
	    
		/*****************************/
		/* ------------------------- */
		/* --- OPERATOR JOYSTICK --- */
		/* ------------------------- */
		/*****************************/
		
		/* --- Hatch Mechanism Buttons --- */
		operatorJoystick.triggerLeft				.whenPressed(new hatchBeakClose(0));		//runs periodically to controll controller vibration
		operatorJoystick.triggerLeft				.whenReleased(new hatchBeakOpen());		//runs periodically to controll controller vibration
		
		operatorJoystick.bumperLeft					.whenPressed(new hatchLauncherExtend());
		operatorJoystick.bumperLeft					.whenReleased(new hatchLauncherRetract());
		
		// Macros //
		operatorJoystick.rightStickButton			.whileHeld(new CGNewScoreHatch()); 
		operatorJoystick.rightStickButton			.whenReleased(new CGNewRetractLaunchers()); 
		
		operatorJoystick.leftStickButton			.whileHeld(new hatchBeakWithUltraSonic(0));
		operatorJoystick.leftStickButton			.whenReleased(new hatchBeakOpen());		//un-beak mode

		/* --- Cargo System Buttons --- */
		operatorJoystick.triggerRight				.whileHeld(new cargoBigBrotherIntake(false));
		operatorJoystick.bumperRight				.whileHeld(new cargoBigBrotherEject());

		operatorJoystick.back						.whileHeld(new cargoBigBrotherScore());
		operatorJoystick.start						.whileHeld(new cargoBigBrotherIntake(true));	//toggles defensive intake

		/* --- Lift Position Buttons --- */
		operatorJoystick.povUp						.whenPressed(new goToPosition(Robot.Lift.hatchMidScorePosition));
		operatorJoystick.povUp						.whenReleased(new stayAtPosition());
		operatorJoystick.povDown					.whenPressed(new goToPosition(Robot.Lift.hatchLowScorePosition));
		operatorJoystick.povDown					.whenReleased(new stayAtPosition());

		operatorJoystick.greenA						.whenPressed(new goToPosition(Robot.Lift.cargoIntakePosition));
		operatorJoystick.greenA						.whenReleased(new stayAtPosition());

		operatorJoystick.redB						.whenPressed(new goToPosition(Robot.Lift.cargoShipScorePosition));
		operatorJoystick.redB						.whenReleased(new stayAtPosition());

		operatorJoystick.yellowY					.whenPressed(new goToPosition(Robot.Lift.cargoMidScorePosition));
		operatorJoystick.yellowY					.whenReleased(new stayAtPosition());

		/***********************************/
		/* ------------------------------- */
		/* --- DRIVER STATION CONTROLS --- */
		/* ------------------------------- */
		/***********************************/

		/* --- Black Buttons --- */
		operatorControls.BlackSwitch				.whenPressed(new readyClimber());
		operatorControls.BlackSwitch				.whenReleased(new unreadyClimber());

		operatorControls.BlackButton				.whenPressed(new climbBigBrother());
		operatorControls.BlackButton				.whenReleased(new stayAtPosition());

		/* --- Blue Buttons --- */
		operatorControls.BlueButton					.whenPressed(new deployClimber());
		operatorControls.BlueButton					.whenReleased(new unreadyClimber());
		
		operatorControls.BlueSwitch					.whileHeld(new stopLift());
		operatorControls.BlueSwitch					.whenReleased(new resetLift());

		/* --- Yellow Buttons --- */
		operatorControls.YellowSwitch				.whileHeld(new compressorTurnOff());
		operatorControls.YellowSwitch				.whenReleased(new compressorTurnOn());

		operatorControls.YellowButton				.whileActive(new autoButtonDelay(true));		//whileHeld(new liftToClimbTop(Robot.Lift.climbWheelsUpPosition));
		operatorControls.YellowButton				.whenInactive(new autoButtonDelay(false));

		/* --- White/Clear Buttons --- */
		operatorControls.WhiteButton				.whileActive(new autoButtonDelay(true));
		operatorControls.WhiteButton				.whenInactive(new autoButtonDelay(false));
		
		operatorControls.ClearSwitch				.whileHeld(new stopAllButChassis());

	}

	/**
	 * Returns the Driver Joystick object
	 * @return - Joysick Object = Driver Joystick
	 */
	public Joystick getDriverJoystick() {
		return driverJoystick;
	}

	/**
	 * Returns the Operator Joystick object
	 * @return - Joysick Object = Operator Joystick
	 */
	public Joystick getOperatorJoystick() {
		return operatorJoystick;
	}

	/**
	 * Returns the Operator Controlls object
	 * @return - Joysick Object = Operator Controlls
	 */
	public Joystick getOperatorControls() {
		return operatorControls;
	}
}