package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

/**
 * Controls the intake mechanism for cargo
 */
public class RoboWrangler extends Subsystem {

    // The motor to run the cargo intake
    private TalonSRX RoboWranglerMotor;

    public RoboWrangler() {
        // Configurations for the cargo intake motor
        this.RoboWranglerMotor = Robot.Chassis.rightFrontMotor;
    }

    public void initDefaultCommand() {

    }

    /**
     * Run the cargo intake motor at a specified speed
     * 
     * @param speed A decimal value from -1 to 1 to set the cargo intake motor speed
     *              to
     */
    public void drive(double speed) {
        RoboWranglerMotor.set(ControlMode.PercentOutput, speed);
    }

    /**
     * Stop the cargo intake motor
     */
    public void stop() {
        RoboWranglerMotor.set(ControlMode.PercentOutput, 0);
    }
}