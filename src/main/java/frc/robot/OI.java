package frc.robot;

import frc.robot.commands.CargoBigBrother.*;
import frc.robot.commands.HatchBeak.*;
import frc.robot.commands.HatchLauncher.*;
import frc.robot.commands.Lift.*;
import frc.robot.commands.Shifter.*;
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

	public OI() {

		/* ====== DRIVER JOYSTICK ===== */

		// Assigned to Quick Turn in Chassis.driveByJoystick - DO NOT USE
		// driverJoystick.bumperRight				.whenPressed(); 

		driverJoystick.bumperLeft					.whenPressed(new shifterLowGear());
		driverJoystick.bumperLeft					.whenReleased(new shifterHighGear());

		// Assigned to Adjust Yeet Speed in Chassis.driveByJoystick - DO NOT USE
		// driverJoystick.triggerRight				.whenPressed(); // Level2SuperCoolRampJump Do not assign
		driverJoystick.triggerLeft					.whenPressed(new PIDVisionDrive(1.0, 0.1, 0.1, "false"));

	    ////////////////////////////////// 
	    
		/* ====== OPERATOR JOYSTICK ===== */
		
		operatorJoystick.triggerLeft				.whenPressed(new hatchBeakClose());
		operatorJoystick.triggerLeft				.whenReleased(new hatchBeakOpen());
		operatorJoystick.bumperLeft					.whenPressed(new hatchLauncherExtend());
		operatorJoystick.bumperLeft					.whenReleased(new hatchLauncherRetract());

		operatorJoystick.triggerRight				.whileHeld(new cargoBigBrotherIntake());
		operatorJoystick.bumperRight				.whileHeld(new cargoBigBrotherEject());

		operatorJoystick.start						.whileHeld(new cargoBigBrotherScore());

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

		////////////////////////////////////

		/* ===== DRIVER STATION CONTROLS ===== */
		operatorControls.BlackSwitch				.whenPressed(new readyClimber());
		operatorControls.BlackSwitch				.whenReleased(new unreadyClimber());
		operatorControls.BlackButton				.whenPressed(new climbBigBrother());
		operatorControls.BlackButton				.whenReleased(new stayAtPosition());

		
	
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
