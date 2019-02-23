package frc.robot.commands.Chassis;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

/**
 * A temporary command used to adjust the top speed of the chassis during a
 * Level 2 Quick Climb (yeet) TODO: Remove this command once a final speed is
 * found
 */
public class setYeetSpeed extends Command {

    double speed;

    /**
     * Sets the top speed adjustment during a yeet
     * 
     * @param speed
     */
    public setYeetSpeed(double speed) {
        this.speed = speed;
        requires(Robot.Chassis);
    }

    protected void initialize() {
        Robot.Chassis.yeetSpeed = speed;
    }

    // Supplys the correct values to the arcadeDrive command to drive the robot
    protected void execute() {

    }

    // This command is not meant to exit
    protected boolean isFinished() {
        return true;
    }
}
