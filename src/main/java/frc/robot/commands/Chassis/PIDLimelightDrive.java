package frc.robot.commands.Chassis;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

public class PIDLimelightDrive extends PIDCommand {

	double turnSpeed, yaw;
	double[] limelight3D;
	double[] limelightValues = new double[6];
	double turnP = 0.03;
	double ticksPerInch = 686;
	double angleAway, initialAngle, targetAngle, distanceAway, tx;

	boolean angleNotSet = true;
	boolean noDistance = true;

	public PIDLimelightDrive() {
		super(0.00005, 0, 0); //multiplies ticks by value to get percentage
		requires(Robot.Chassis);
	}

	protected void initialize() {
		Robot.Vision.setLEDMode(3);
		Robot.Chassis.resetEncoders();
		Robot.Pigeon.resetPidgey();
		this.setSetpoint(40000);
		initialAngle = 0;
		targetAngle = 0;
		angleAway = 0;
		distanceAway = 0;
		angleNotSet = true;
		noDistance = true;
	}

	@Override
	protected double returnPIDInput() {
		return Robot.Chassis.getAverageEncoderPosition();
	}

	@Override
	protected void usePIDOutput(double output) {
		limelight3D = NetworkTableInstance.getDefault().getTable("limelight").getEntry("camtran").getDoubleArray(limelightValues);

		// System.out.println("Output: " + output);
		if(output > 0.5) {
			output = 0.5;
		}
		if(distanceAway == 0 && noDistance) {
			Robot.Chassis.resetEncoders();
			distanceAway = limelight3D[2];
		} else if(noDistance) {
			// this.setSetpoint(Math.abs(distanceAway * ticksPerInch) - 10000);
			noDistance = false;
		}

		yaw = Robot.Pigeon.getYaw();

		if (Math.abs(Robot.Pigeon.getYaw()) < 8) {
			turnP = 0.08;
		  } else {
			turnP = 0.02;
		  }

		if(initialAngle == 0 && angleNotSet) {
			initialAngle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
		} else if (angleNotSet) {
			targetAngle = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
			angleNotSet = false;
		}
		turnSpeed = (turnP * ((yaw - targetAngle) / Math.abs(targetAngle)));

		// System.out.println("Output: " + output + " *** " + "TurnSpeed: " + turnSpeed);
		Chassis.neoArcade(output, turnSpeed, false);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.Vision.setLEDMode(1);
		angleNotSet = true;
	}

	protected void interupted() {
		this.end();
	}

}