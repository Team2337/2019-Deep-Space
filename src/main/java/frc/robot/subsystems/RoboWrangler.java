package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.commands.RoboWrangler.*;

/**
 * Controls the motors on the end of the RoboWrangler
 */
public class RoboWrangler extends Subsystem {

    private TalonSRX wranglerDriveMotor;
    private TalonSRX wranglerLassoMotor;

    public RoboWrangler() {
        this.wranglerDriveMotor = new TalonSRX(Robot.Constants.wranglerDriveID);

        wranglerLassoMotor = new TalonSRX(Robot.Constants.wranglerLassoID);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new driveWranglerByJoystick());
    }

    /**
     * Run the wrangler drive motor at a specified speed
     * 
     * @param speed A decimal value from -1 to 1 to set the wrangler drive motor
     *              speed to
     */
    public void drive(double speed) {
        wranglerDriveMotor.set(ControlMode.PercentOutput, speed);
    }

    /**
     * Run the wrangler lasso motor at a specified speed
     * 
     * @param speed A decimal value from -1 to 1 to set the wrangler lasso motor
     *              speed to
     */
    public void lasso(double speed) {
        wranglerLassoMotor.set(ControlMode.PercentOutput, -speed);
    }

    /**
     * Stops the RoboWrangler's drive and lasso motors
     */
    public void stop() {
        wranglerLassoMotor.set(ControlMode.PercentOutput, 0);
        wranglerDriveMotor.set(ControlMode.PercentOutput, 0);
    }

    /**
     * The speed being sent to the drive motor
     * 
     * @return The speed being sent to the drive motor
     */
    public double driveMotorStatus() {
        return wranglerDriveMotor.getMotorOutputPercent();
    }

    /**
     * The speed being sent to the wrangler lasso motor
     * 
     * @return The speed being sent to the wrangler lasso motor
     */
    public double lassoMotorStatus() {
        return wranglerLassoMotor.getMotorOutputPercent();
    }
}