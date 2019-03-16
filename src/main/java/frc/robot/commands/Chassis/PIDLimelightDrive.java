package frc.robot.commands.Chassis;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

/**
 * Finds the vision target, and drives lines up towards it, taking over the driver's movement
 * @author Bryce G.
 */
public class PIDLimelightDrive extends PIDCommand {

	double turnSpeed, yaw;
	double[] limelight3D;
	double[] limelightValues = new double[6];
	double turnP = 0.03;
	double ticksPerInch = 686;
	double angleAway, initialAngle, targetAngle, distanceAway, tx;

	boolean angleNotSet = true;
	boolean noDistance = true;

	/**
	 * Finds the vision target using the limelight
	 * The target angle and distance are only set once
	 */
	public PIDLimelightDrive() {
		super(0.00005, 0, 0); //multiplies ticks by value to get percentage
		requires(Robot.Chassis);
	}

	protected void initialize() {
		//Resets all the values
		Robot.Vision.setLEDMode(3);
		Robot.Chassis.resetEncoders();
		Robot.Pigeon.resetPidgey();
		this.setSetpoint(40000); //test value
		initialAngle = 0;
		targetAngle = 0;
		angleAway = 0;
		distanceAway = 0;
		angleNotSet = true;
		noDistance = true;
	}

	/* --- PID Methods --- */
	@Override
	protected double returnPIDInput() {
		return Robot.Chassis.getAverageEncoderPosition();
	}

	@Override
	protected void usePIDOutput(double output) {
		limelight3D = NetworkTableInstance.getDefault().getTable("limelight").getEntry("camtran").getDoubleArray(limelightValues);
		//TODO: Add in drive by joystick if the target is not found
		System.out.println("Output: " + output);
		//Limits the foward speed
		if(output > 0.5) {
			output = 0.5;
		}

		//Keeps the robot from going reverse
		if(output < 0) {
			output = 0;
		}

		//Sets the distance setpoint once the target has been found, and does not set again
		if(distanceAway == 0 && noDistance) {
			Robot.Chassis.resetEncoders();
			distanceAway = limelight3D[2];
		} else if(noDistance) {
			// this.setSetpoint(Math.abs(distanceAway * ticksPerInch) - 10000);
			noDistance = false;
		}

		//gets the yaw from the gyro
		yaw = Robot.Pigeon.getYaw();

		//Changes the P value based on how close the angle is to 0
		if (Math.abs(Robot.Pigeon.getYaw()) < 8) {
			turnP = 0.08;
		  } else {
			turnP = 0.02;
		  }

		//Sets the anlge setpoint once the angle is above zero, does not set again
		if(initialAngle == 0 && angleNotSet) {
			initialAngle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
		} else if (angleNotSet) {
			targetAngle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
			angleNotSet = false;
		}
		//Calculates turn speed based on the error from the current yaw to the taget angle
		turnSpeed = (turnP * ((yaw - targetAngle) / Math.abs(targetAngle)));

		System.out.println("Output: " + output + " *** " + "TurnSpeed: " + turnSpeed);
		//uses the values to drive
		Chassis.neoArcade(output, turnSpeed, false);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		//Turns the limelight off
		Robot.Vision.setLEDMode(1);
	}

	protected void interupted() {
		this.end();
	}

}