package frc.robot.commands.RoboWrangler;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class driveWranglerForward extends Command {

    private double speed;

    public driveWranglerForward(double speed) {
        this.speed = speed;
        requires(Robot.RoboWrangler);
    }

    @Override
    protected void initialize() {
        Robot.RoboWrangler.drive(speed);
    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.RoboWrangler.stop();
    }

    @Override
    protected void interrupted() {
        this.end();
    }

}