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
	public NerdyXbox				driverJoystick			= new NerdyXbox(0);
	public NerdyXbox				operatorJoystick		= new NerdyXbox(1);
	public NerdyOperatorStation		operatorControls		= new NerdyOperatorStation(2);

	public OI() {

		/* ====== DRIVER JOYSTICK ===== */
		
		driverJoystick.bumperRight					.whenPressed(new shifterHighGear());
		driverJoystick.bumperLeft					.whenPressed(new shifterLowGear());
		
	    
	    ////////////////////////////////// 
	    
		/* ====== OPERATOR JOYSTICK ===== */
		
		// operatorJoystick.povUp					.whenPressed(new goToPosition(500));
		// operatorJoystick.povDown					.whenPressed(new goToPosition(300));

		// operatorJoystick.bumperRight				.whenPressed(new hatchBeakClose());
		operatorJoystick.bumperRight				.whenPressed(new hatchBeakClose()); 
		operatorJoystick.bumperRight				.whenReleased(new hatchBeakOpen());
		operatorJoystick.bumperLeft					.whenPressed(new hatchLauncherExtend());
		operatorJoystick.bumperLeft					.whenReleased(new hatchLauncherRetract());

		operatorJoystick.leftStickUp				.whenPressed(new setTargetPosition(Robot.Lift.targetPosition + 10));

		operatorJoystick.start						.whileHeld(new cargoBigBrotherIntake()); // Same as triggerRight - Defensive mode

		operatorJoystick.povLeft					.whenPressed(new hatchBeakOpen());
		operatorJoystick.povRight					.whenPressed(new hatchLauncherRetract());

		operatorJoystick.triggerRight				.whileHeld(new cargoBigBrotherIntake()); // If the position of this command changes, UPDATE THE COMMAND
		operatorJoystick.triggerLeft				.whileHeld(new cargoBigBrotherScore());

		operatorJoystick.greenA						.whileHeld(new setTargetPosition(201));
		operatorJoystick.redB						.whileHeld(new cargoBigBrotherEject());
		operatorJoystick.blueX						.whileHeld(new setTargetPosition(325));
		operatorJoystick.yellowY					.whileHeld(new setTargetPosition(150));

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
