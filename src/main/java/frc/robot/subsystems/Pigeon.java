package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Gyro class for CTRE Pigeon Gyro
 * @category CHASSIS
 * @author Team2337 - EngiNERDs
 * Must be initiated after RobotMap
 */

public class Pigeon extends Subsystem {

	public static PigeonIMU pidgey;
	public static PigeonIMU.FusionStatus gyrofusionStatus;
	public static PigeonIMU.GeneralStatus gyroGenStatus;
	public double[] ypr_deg;
	public double[] xyz_dps;
	private int timeoutMs = 20;
	
	public Pigeon() {
		// Assign to the Talon the Pigeon is connected into.

		//pidgey = new PigeonIMU(RobotMap.intake_left);
		pidgey = new PigeonIMU(0);
		//pidgey.enterCalibrationMode(CalibrationMode.BootTareGyroAccel, 10);
		gyrofusionStatus = new PigeonIMU.FusionStatus();
		gyroGenStatus = new PigeonIMU.GeneralStatus();
		ypr_deg = new double[3];
		xyz_dps = new double[3];
	
	}
	
	@Override
	protected void initDefaultCommand() {

	}
	

	/**
	 * Periodically request information from the device
	 */
	public void periodic() {
		
		pidgey.getFusedHeading(gyrofusionStatus);
		pidgey.getGeneralStatus(gyroGenStatus);
		pidgey.getYawPitchRoll(ypr_deg);
		pidgey.getRawGyro(xyz_dps);
		
		//SmartDashboard.putNumber("FusedHeading", pidgey.getFusedHeading());
		//SmartDashboard.putNumber("AbsoluteCompass", getThing());
		SmartDashboard.putNumber("yaw", getYaw());
		//SmartDashboard.putNumber("Pitch", getPitch());
		//SmartDashboard.putNumber("Roll", getRoll());

		
	}
	/**
	 * 
	 * @return yaw
	 */
	public double getYaw() {
		double yaw = 0;
		yaw = ypr_deg[0];
		return yaw;
	}
	/**
	 * 
	 * @return pitch
	 */
	public double getPitch() {
		double pitch = 0;
		pitch = ypr_deg[1];
		return pitch;
	}
	/**
	 * 
	 * @return roll
	 */
	public double getRoll() {
		double roll = 0;
		roll = ypr_deg[2];
		return roll;
	}
	/**
	 * 
	 * @return returns the absolute compass heading 
	 */
	public double getAbsoluteCompassHeading() {
		return pidgey.getAbsoluteCompassHeading();
	}
	
	public void resetPidgey() {
		pidgey.setYaw(0, timeoutMs);
	}

	public double getAngularRate() {
		double angularRate;
		angularRate = xyz_dps[2];
		return angularRate;
	}
	
	public void manualSetYaw(double yaw) {
		yaw *= 64;
		pidgey.setYaw(yaw, timeoutMs);

	}
}