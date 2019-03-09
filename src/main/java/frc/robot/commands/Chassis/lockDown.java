package frc.robot.commands.Chassis;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.Robot;
import frc.robot.nerdyfiles.controller.NerdyUltimateXboxDriver;
import frc.robot.subsystems.Chassis;

/**
 * Uses controller joysticks to drive the robot using ArcadeDrive
 */
public class lockDown extends PIDCommand {

    // How fast the robot moves overall
    double moveSpeed;
    double p = 4;
    double i = 0, d = 0;

    /**
     * Uses Arcade Drive to drive either Neo or Talon motor controllers
     * 
     * @param isNeoDrive A boolean representing whether or not the joystick should
     *                   control Neos to drive
     */
    public lockDown() {
        super(4, 0, 0);
        getPIDController().setAbsoluteTolerance(0.1); // acceptable tx offset to end PID
        getPIDController().setContinuous(false); // not continuous like a compass
        getPIDController().setOutputRange(-0.5, 0.5); // output range for 'turn' input to drive command

        requires(Robot.Chassis);
    }

    protected void initialize() {
        Robot.Chassis.resetEncoders();
        Robot.Chassis.resetNeoEncoders();
        Robot.Shifter.shiftLowGear();
    }

    // Supplys the correct values to the arcadeDrive command to drive the robot
    protected void execute() {

    }

    // This command is not meant to exit
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected double returnPIDInput() {
        return Robot.Chassis.getAverageNeoEncoder();
    }

    @Override
    protected void usePIDOutput(double output) {
        getPIDController().setSetpoint(0);
    }
}
