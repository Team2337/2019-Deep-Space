package frc.robot.commands.CargoIntake;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command will run the outake
 * 
 * @author Zayd A
 */
public class cargoClose extends Command {
    public double speed = 1;

    // CONSTRUCTOR
    public cargoClose(double speed) {
        requires(Robot.CargoIntake);
        this.speed = speed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.CargoIntake.outake(this.speed);

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true, it set the outtake to 0% speed
    @Override
    protected void end() {
        Robot.CargoIntake.outake(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
    }

}