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
        // requires(Robot.CargoBigBrother);
        // requires(Robot.Lift);
    }

    // TODO: determine actual value for this.
    double tolerance = 10;

    @Override
    protected void initialize() {
        // If the ball is within the trolley and the lift is in position (whatever it is
        // set to using the buttons)
        if (Robot.CargoBigBrother.inFireMode && Robot.Lift.atPosition(10)) {
            // Score the ball and consider it scored once the command ends
            Robot.CargoScore.score(1);
            Robot.CargoBigBrother.inFireMode = false;
        } else {
            // If the lift isn't in position, tell it to go there
            Robot.Lift.setSetpoint(Robot.Lift.targetPosition);
        }
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
        // If the trigger is released, stop the cargo system regardless
        this.end();
    }
}