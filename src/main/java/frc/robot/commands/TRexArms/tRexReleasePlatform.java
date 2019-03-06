package frc.robot.commands.TRexArms;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Trigger a pneumatic to release the robot from the platform when lifting
 * another robot
 */
public class tRexReleasePlatform extends Command {

    /**
     * Trigger a pneumatic to release the robot from the platform when lifting
     * another robot
     */
    public tRexReleasePlatform() {
        requires(Robot.TRexArms);
    }

    @Override
    protected void initialize() {
        Robot.TRexArms.releasePlatform();
    }

    @Override
    protected void execute() {

    }

    // This command does not need to run more than once
    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        this.end();
    }

}