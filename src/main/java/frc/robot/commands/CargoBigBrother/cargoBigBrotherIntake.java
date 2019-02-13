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
    }

    // Set the speed of the cargo escalator motors
    @Override
    protected void initialize() {

        switch (Robot.CargoBigBrother.cargoLevel()) {
        // If a ball is not detected within the robot
        case 0: {
            // Set the lift to move to the intake position
            Robot.Lift.setSetpoint(Robot.CargoBigBrother.intakeCargoPosition);
            // If the ball is just between the escalator sensors
            if (Robot.CargoBigBrother.passedIntakeSensor) {
                // If there really is no ball within the robot

            } else {
                Robot.CargoIntake.rollIn(1);
                Robot.CargoEscalator.rollUp(1);
            }
        }
        case 1: {
            Robot.Lift.setSetpoint(Robot.CargoBigBrother.intakeCargoPosition);
            Robot.CargoEscalator.rollUp(1);
        }
        case 2: {
            Robot.Lift.setSetpoint(Robot.CargoBigBrother.intakeCargoPosition);
        }
        case 3: {
            //Figure out a way to use both the low and mid position
            Robot.Lift.setSetpoint(Robot.CargoBigBrother.midCargoPosition);
        }
        default: {
            System.out.println("ERROR");
        }
        }
    }

    @Override
    protected void execute() {
        /*
        if (!startWithBall) {
            if (Robot.CargoBigBrother.cargoIntakeSensor.get()) {
                Robot.CargoIntake.stop();
            }
            if (Robot.CargoBigBrother.cargoEscalatorSensor.get()) {
                if (!Robot.Lift.atPosition(10)) {
                    Robot.CargoEscalator.stop();
                } else {
                    Robot.CargoScore.rollIn(1);
                    Robot.CargoEscalator.rollUp(1);
                    // isFinished
                }
            }
        }
        */
        switch(Robot.CargoBigBrother.cargoLevel()){
            case 0: {
                if (Robot.CargoBigBrother.passedIntakeSensor) {
                    // If there really is no ball within the robot

                } else {

                }
            }
            case 1: {

            }
            case 2: {

            }
            case 3: {

            }
        }
    }

    @Override
    protected boolean isFinished() {
        if (startWithBall) {
            // If the cargo has successfully reached the trolley
            return Robot.CargoBigBrother.cargoTrolleySensor.get() && !Robot.CargoBigBrother.cargoEscalatorSensor.get();
        } else {
            // If the cargo has successfully exited the trolley (scoring or otherwise)
            return !Robot.CargoBigBrother.cargoTrolleySensor.get();
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