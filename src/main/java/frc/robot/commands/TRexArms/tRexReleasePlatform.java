package frc.robot.commands.TRexArms;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class tRexReleasePlatform extends Command {

    public tRexReleasePlatform() {
        requires(Robot.TRexArms);
    }

    @Override
    protected void initialize() {
        Robot.TRexArms.platformRelease();
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