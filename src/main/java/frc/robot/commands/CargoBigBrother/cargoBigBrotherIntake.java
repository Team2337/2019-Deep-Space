package frc.robot.commands.CargoBigBrother;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command will run the escalator upwards to move cargo towards the scoring
 * mechanism
 * 
 * @author Jack E.
 */
public class cargoBigBrotherIntake extends Command {

    double intakeSpeed = 1;
    double escalatorSpeed = 1;
    double scoreSpeed = 1;

    boolean isScoreMode = Robot.CargoBigBrother.isScoreMode;

    public cargoBigBrotherIntake() {
        requires(Robot.CargoBigBrother);
    }

    // Set the speed of the cargo escalator motors
    @Override
    protected void initialize() {
        // If it is not in score mode
        if (!isScoreMode) {
            // Check the cargo level
            switch (Robot.CargoBigBrother.cargoLevel()) {

            case 0: {
                Robot.CargoIntake.rollIn(1);
                // Does not break, as the next two cases have the same ending
            }
            case 1:
            case 2: {
                Robot.CargoEscalator.rollUp(1);
                Robot.CargoBigBrother.moveToPosition(Robot.Lift.cargoIntakePosition);
                break;
            }
            case 3: {
                // The escalator stops until the lift is in position
                Robot.CargoBigBrother.moveToPosition(Robot.Lift.cargoIntakePosition);
                break;
            }

            // All positions after 3 simply move the lift straight to the scoring position
            case 4:
            case 5:
            case 6:
            case 7: {
                Robot.CargoBigBrother.moveToPosition(Robot.CargoBigBrother.currentScoringPosition);
                break;
            }

            }
        }
    }

    @Override
    protected void execute() {

        if (!Robot.CargoBigBrother.isScoreMode) {
            switch (Robot.CargoBigBrother.cargoLevel()) {
            case 0: {
                // Nothing needs to happen in execute, as the neccisary motors are running as
                // per initialize
                break;
            }
            case 1: {
                // Once the ball has passed the intake, stop the intake
                Robot.CargoIntake.stop();
                // Once the ball has passed the intake sensor, it is between the two escalator
                // sensors, and thus, in position 2
                if (Robot.CargoBigBrother.cargoIntakeSensor.get() == false) {
                    Robot.CargoBigBrother.passedIntakeSensor = true;
                }
                break;
            }
            case 2: {
                // Nothing special needs to happen at this position
                break;
            }
            case 3: {
                if (Robot.Lift.atPosition(10)) {
                    Robot.CargoEscalator.rollUp(1);
                    Robot.CargoScore.rollIn(1);
                } else {
                    Robot.CargoEscalator.stop();
                }
                break;
            }
            case 4: {
                Robot.CargoEscalator.stop();
                Robot.CargoBigBrother.moveToPosition(Robot.CargoBigBrother.currentScoringPosition);
                break;
            }
            case 5:
            case 6: {
                Robot.CargoBigBrother.isScoreMode = Robot.Lift.atPosition(10);
                break;
            }
            case 7: {
                // The lift is not meant to be at this position
                break;
            }
            }
        } else {
            // If the robot is in score mode
            // The trolley motor is told to run outwards in init
            Robot.CargoBigBrother.isScoreMode = Robot.CargoBigBrother.cargoTrolleySensor.get();
            Robot.CargoBigBrother.passedIntakeSensor = Robot.CargoBigBrother.cargoTrolleySensor.get();
        }
    }

    @Override
    protected boolean isFinished() {
        if (!isScoreMode) {
            return Robot.CargoBigBrother.isScoreMode;
        } else {
            return !Robot.CargoBigBrother.isScoreMode;
        }
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