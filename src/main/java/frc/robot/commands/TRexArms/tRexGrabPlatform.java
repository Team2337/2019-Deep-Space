package frc.robot.commands.TRexArms;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Trigger a pneumatic to secure the robot to the platform when lifting another
 * robot
 */
public class tRexGrabPlatform extends Command {

    /**
     * Trigger a pneumatic to secure the robot to the platform when lifting another
     * robot
     */
    public tRexGrabPlatform() {
        requires(Robot.TRexArms);
    }

    @Override
    protected void initialize() {
        Robot.TRexArms.grabPlatform();
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