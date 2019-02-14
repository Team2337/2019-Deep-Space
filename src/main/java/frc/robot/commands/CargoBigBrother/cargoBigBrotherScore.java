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

    double tolerance = 10;

    // Set the speed of the cargo escalator motors
    @Override
    protected void initialize() {
        //Robot.Lift.setSetpoint(Robot.CargoBigBrother.currentScoringPosition);
    }

    @Override
    protected void execute() {
        // Is the lift within tolerance(10) of its given setpoint (whatever it may be)
        if (Robot.Lift.atPosition(tolerance)) {
            if (Robot.Lift.atCargoLowPosition(tolerance)) {
                Robot.CargoScore.rollIn(1);
            } else if (Robot.Lift.atCargoMidPosition(tolerance)) {
                Robot.CargoScore.rollIn(0.75);
            } else if (Robot.Lift.atCargoShipPosition(tolerance)) {
                Robot.CargoScore.rollIn(0.5);
            }
            /*
            if (Robot.Lift.currentPosition == Robot.Lift.cargoLowScorePosition) {
                Robot.CargoScore.rollIn(1);
            } else if (Robot.Lift.currentPosition == Robot.Lift.cargoMidScorePosition) {
                Robot.CargoScore.rollIn(0.75);
            } else if (Robot.Lift.currentPosition == Robot.Lift.cargoShipScorePosition) {
                Robot.CargoScore.rollIn(0.5);
            }
            */
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