package frc.robot.commands.CargoBigBrother;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command will run the escalator upwards to move cargo towards the scoring
 * mechanism
 * 
 * @author Jack E.
 */
public class cargoBigBrotherScore extends Command {

    public cargoBigBrotherScore() {
        requires(Robot.CargoBigBrother);
        requires(Robot.CargoScore);
    }

    // Set the speed of the cargo escalator motors
    @Override
    protected void initialize() {
        Robot.CargoBigBrother.moveToPosition(Robot.CargoBigBrother.currentScoringPosition);
    }

    @Override
    protected void execute() {
        if (Robot.Lift.atPosition(10)) {
            if (Robot.Lift.currentPosition == Robot.Lift.cargoLowScorePosition) {
                Robot.CargoScore.rollIn(1);
            } else if (Robot.Lift.currentPosition == Robot.Lift.cargoMidScorePosition) {
                Robot.CargoScore.rollIn(0.75);
            } else if (Robot.Lift.currentPosition == Robot.Lift.cargoShipScorePosition) {
                Robot.CargoScore.rollIn(0.5);
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        // If the trigger is released mid-travel or the ball has exited
        Robot.CargoBigBrother.stop();
    }

    @Override
    protected void interrupted() {
        // If the trigger is released, stop the cargo system regardless
        this.end();
    }
}