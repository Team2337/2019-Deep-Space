package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Reads the position of the lift, and sets the setpoint to the current position
 * @author Bryce G.
 */
public class stopAllButChassis extends Command {

    /**
     * Reads the position of the lift, and sets the setpoint to the current position
     */
    public stopAllButChassis() {
        requires(Robot.ClimberDeploy);
        requires(Robot.CargoScore);
        requires(Robot.CargoIntake);
        requires(Robot.CargoEscalator);
        requires(Robot.CargoDrawbridge);
        requires(Robot.CargoBigBrother);
        requires(Robot.HatchLauncher);
        requires(Robot.HatchBeak);
        requires(Robot.Lift);
        requires(Robot.RoboWrangler);
        requires(Robot.TRexArms);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
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
        
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        this.end();
    }
}