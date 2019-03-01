package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

/**
 * Controls the intake mechanism for cargo
 */
public class RoboWrangler extends Subsystem {

    private TalonSRX wranglerDriveMotor;
    private TalonSRX wranglerLassoMotor;

    public RoboWrangler() {
        this.wranglerDriveMotor = Robot.Chassis.rightFrontMotor;

        wranglerLassoMotor = new TalonSRX(Robot.Constants.wranglerLassoID);
    }

    public void initDefaultCommand() {

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
     * Stop the cargo intake motor
     */
    public void stop() {
        wranglerDriveMotor.set(ControlMode.PercentOutput, 0);
        wranglerLassoMotor.set(ControlMode.PercentOutput, 0);
    }
}