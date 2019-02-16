package frc.robot.commands.CargoDrawbridge;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * The command raises the cargo drawbridge for the intake
 */
public class raiseTheDrawbridge extends Command {

    /**
     * Raises the cargo drawbridge for the intake
     */
    public raiseTheDrawbridge() {
        requires(Robot.CargoDrawbridge);
    }

    // Extend the drawbridge pneumatic
    @Override
    protected void initialize() {
        Robot.CargoDrawbridge.raiseTheDrawbridge();
    }

    // The solenoid only needs to be set once, so nothing happens in execute()
    @Override
    protected void execute() {

    }

    // This command is not meant to run after it has extended the solenoid
    @Override
    protected boolean isFinished() {
        return true;
    }

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