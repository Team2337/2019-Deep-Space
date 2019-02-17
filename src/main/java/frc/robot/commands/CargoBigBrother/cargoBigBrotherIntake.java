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
    double scorerIntakeSpeed = 0.2; // speed of the lift intake, may be slower than scoreSpeed so we are sure to
                                    // stop before ejecting the cargo.
    double scoreSpeed = 0.5;

    double liftTolerance = 10; // how close the lift needs to be to set point to allow loading of cargo

    public cargoBigBrotherIntake() {
        requires(Robot.CargoBigBrother);
        requires(Robot.CargoIntake);
        requires(Robot.CargoEscalator);
        requires(Robot.CargoScore);
        requires(Robot.Lift);
    }

    // Set the speed of the cargo escalator motors and adjust lift accordingly.
    @Override
    protected void initialize() {
        Robot.CargoBigBrother.inDeadzone = false; // assume we don't have a cargo the deadzone (area of excalator that
                                                  // sensors don't cover)

        // Check the cargo level and start the command accordingly.
        switch (Robot.CargoBigBrother.cargoLevel()) {

        case 0: {
            if (Robot.oi.operatorJoystick.triggerRight.get()) {
                Robot.CargoDrawbridge.lowerTheDrawbridge();
            }
            Robot.CargoIntake.rollIn(intakeSpeed);
            // Does not break, as the next cases have the same ending
        }
        case 1:
        case 2:
        case 3: {
            Robot.CargoEscalator.rollUp(escalatorSpeed);
            Robot.Lift.setSetpoint(Robot.Lift.cargoIntakePosition);
            break;
        }
        case 4: {
            break;
        }

        }
    }

    @Override
    protected void execute() {

        switch (Robot.CargoBigBrother.cargoLevel()) {

        case 0: {
            // Nothing needs to happen in execute, as the necessary motors are running
            // as per initialize
            // TODO: Copy intake, excalator, drawbridge and lift command to read better,
            // then delete contents of init case 0.
            break;
        }
        case 1: {
            Robot.CargoDrawbridge.raiseTheDrawbridge();
            // Once the ball has passed the intake, stop the intake. Escalator is still
            // running.
            Robot.CargoIntake.stop();
            // Once the ball has passed the intake sensor, it is between the two escalator
            // sensors, and thus, in position 2.
            if (Robot.CargoBigBrother.cargoIntakeSensor.get() == false) {
                Robot.CargoBigBrother.inDeadzone = true;
            }
            break;
        }
        case 2: {
            // Nothing special needs to happen at this position
            if (!Robot.CargoBigBrother.cargoEscalatorSensor.get()) {
                Robot.CargoBigBrother.inDeadzone = false;
            }
            break;
        }
        case 3: {
            if (Robot.Lift.atCargoIntakePosition(liftTolerance)) {
                Robot.CargoEscalator.rollUp(escalatorSpeed);
                // Go slower on lift intake to start, so that it doesn't shoot past the sensor
                Robot.CargoScore.rollIn(scorerIntakeSpeed);
            } else {
                Robot.CargoEscalator.stop();
            }
            break;
        }
        case 4: {
            Robot.CargoEscalator.stop();
            Robot.CargoScore.stop();
            Robot.Lift.setSetpoint(Robot.Lift.cargoIntakePosition);
            Robot.CargoBigBrother.inFireMode = true;
            break;
        }

        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.CargoDrawbridge.raiseTheDrawbridge();
        Robot.CargoEscalator.stop();
        Robot.CargoIntake.stop();
        if (!Robot.oi.operatorJoystick.bumperLeft.get()) {
            Robot.CargoScore.stop(); // TODO: add stop score to case zero incase we lose lift sensor.?
        }
    }

    @Override
    protected void interrupted() {
        // If the trigger is released, stop the cargo system
        this.end();
    }
}