package frc.robot.commands.CargoBigBrother;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command will (on the first press of the button) move the lift to the
 * given position, then (on the second press) launch the ball at full speed
 * 
 * @author Jack E.
 */
public class cargoBigBrotherScore extends Command {

    public cargoBigBrotherScore() {
        requires(Robot.CargoScore);
        requires(Robot.CargoBigBrother);
    }

    @Override
    protected void initialize() {
        Robot.CargoScore.rollForwards(1);
    }

    @Override
    protected void execute() {
        // Everything happens in initalize for this command
    }

    // This command is not meant to end until the trigger is released
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        // Stop the cargo system
        Robot.CargoBigBrother.stop();
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}