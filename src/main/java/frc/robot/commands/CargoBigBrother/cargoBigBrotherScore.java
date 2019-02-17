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

    // Set the speed of the cargo escalator motors
    @Override
    protected void initialize() {
        if (Robot.CargoBigBrother.inFireMode && Robot.Lift.atPosition(10)) {
            Robot.CargoScore.score(1);
            Robot.CargoBigBrother.inFireMode = false;
        } else {
            Robot.Lift.setSetpoint(Robot.Lift.targetPosition);
        }
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
        // If the trigger is released mid-travel or the ball has exited
        Robot.CargoBigBrother.inFireMode = !Robot.CargoBigBrother.inFireMode;
        Robot.CargoBigBrother.stop();
    }

    @Override
    protected void interrupted() {
        // If the trigger is released, stop the cargo system regardless
        this.end();
    }
}