package frc.robot.commands.CargoIntake;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command will run the intake outwards to prevent the possesion of
 * additional cargo, or to eject extra cargo from the robot
 * 
 * @author Zayd A. Jack E.
 */
public class cargoIntakeOut extends Command {

    private double speed;

    // CONSTRUCTOR
    public cargoIntakeOut(double speed) {
        requires(Robot.CargoIntake);
        this.speed = speed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.CargoIntake.rollOut(this.speed);
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

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.CargoIntake.stop();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
    }
}