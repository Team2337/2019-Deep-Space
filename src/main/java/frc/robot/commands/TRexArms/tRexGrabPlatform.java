package frc.robot.commands.TRexArms;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class tRexGrabPlatform extends Command {

    public tRexGrabPlatform() {
        requires(Robot.TRexArms);
    }

    @Override
    protected void initialize() {
        Robot.TRexArms.platformGrab();
    }

    @Override
    protected void execute() {

    }

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