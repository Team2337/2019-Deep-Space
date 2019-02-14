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

    public cargoBigBrotherIntake() {
        requires(Robot.CargoBigBrother);
        requires(Robot.CargoIntake);
        requires(Robot.CargoEscalator);
        requires(Robot.CargoScore);
    }

    // Set the speed of the cargo escalator motors
    @Override
    protected void initialize() {
        Robot.CargoBigBrother.inDeadzone = false;
        Robot.CargoBigBrother.moveToPosition(Robot.Lift.cargoIntakePosition);

        // Check the cargo level
        switch (Robot.CargoBigBrother.cargoLevel()) {

        case 0: {
            // May want to put the drawbridge down *****************
            Robot.CargoIntake.rollIn(intakeSpeed);
            // Does not break, as the next two cases have the same ending
        }
        case 1:
        case 2:
        case 3: {
            Robot.CargoEscalator.rollUp(intakeSpeed);
            Robot.CargoBigBrother.moveToPosition(Robot.Lift.cargoIntakePosition);
            break;
        }

        case 4:
        case 5:
        case 6:
        case 7: {
            Robot.CargoBigBrother.moveToPosition(Robot.CargoBigBrother.currentScoringPosition);
            break;
        }

        }
    }

    @Override
    protected void execute() {

        switch (Robot.CargoBigBrother.cargoLevel()) {

        case 0: {
            // Nothing needs to happen in execute, as the neccisary motors are running as
            // per initialize
            break;
        }
        case 1: {
            // Probably want to raise the pneumatics
            // Once the ball has passed the intake, stop the intake
            Robot.CargoIntake.stop();
            // Once the ball has passed the intake sensor, it is between the two escalator
            // sensors, and thus, in position 2
            if (Robot.CargoBigBrother.cargoIntakeSensor.get() == false) {
                Robot.CargoBigBrother.inDeadzone = true;
            }
            break;
        }
        case 2: {
            // Nothing special needs to happen at this position
            if (Robot.CargoBigBrother.cargoEscalatorSensor.get()) {
                Robot.CargoBigBrother.inDeadzone = false;
            }
            break;
        }
        case 3: {
            if (Robot.Lift.atCargoIntakePosition(10)) {
                Robot.CargoEscalator.rollUp(escalatorSpeed);
                // Go slower to start, so that it doesn't shoot past the sensor
                Robot.CargoScore.rollIn(scoreSpeed / 2);
            } else {
                Robot.CargoEscalator.stop();
            }
            break;
        }
        case 4: {
            Robot.CargoEscalator.stop();
            Robot.CargoScore.stop();
            Robot.CargoBigBrother.moveToPosition(Robot.CargoBigBrother.currentScoringPosition);
            break;
        }

        }
    }

    @Override
    protected boolean isFinished() {
        // If the lift is traveling to the set scoring position (whether it's actually
        // there or not) end the command
        return false;
    }

    @Override
    protected void end() {
        // Bring up the drawbridge
        Robot.CargoEscalator.stop();
        Robot.CargoIntake.stop();
        if (!Robot.oi.operatorJoystick.bumperLeft.get()) {
            Robot.CargoScore.stop();
        }
    }

    @Override
    protected void interrupted() {
        // If the trigger is released, stop the cargo system regardless
        this.end();
    }
}