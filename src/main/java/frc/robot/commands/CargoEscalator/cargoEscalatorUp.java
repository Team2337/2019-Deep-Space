package frc.robot.commands.CargoEscalator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command will run the escalator upwards to move cargo towards the
 * scoring mechanism
 * 
 * @author Jack E.
 */
public class cargoEscalatorUp extends Command {

    private double speed;

    // CONSTRUCTOR
    public cargoEscalatorUp(double speed) {
        requires(Robot.CargoEscalator);
        this.speed = speed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.CargoEscalator.rollUp(this.speed);
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
        Robot.CargoEscalator.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
    }
}