package frc.robot.commands.ClimberDeploy;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class climbBigBrother extends Command {
    int i, j;
    boolean finished;

    // CONSTRUCTOR
    public climbBigBrother() {
        requires(Robot.Lift);
        requires(Robot.ClimberDeploy);
        requires(Robot.RoboWrangler);
    }

    protected void initialize() {
        // Set up the timer variables
        i = 0;
        j = 0;

        // This changes to true when the climber has reached its final phase
        finished = false;
    }

    @Override
    protected void execute() {
        // TODO: if (Robot.ClimberDeploy.readyToClimb) { //or does switching case
        // between 5 and 0 work?
        switch (Robot.ClimberDeploy.climberPhase) {
        // Move the trolley into position to release the climber arms - The T-Rex arms
        // must miss the platform, and the RoboWrangler must hit the bumpers
        case 0:
            Robot.Lift.setSetpoint(Robot.Lift.climbDeployPosition);
            if (Robot.Lift.atPosition(10)) {
                Robot.ClimberDeploy.climberPhase = 1;
            }
            break;
        // Release the climber arms
        case 1:
            // Running the RoboWrangler drive motor backwards will help release the climber
            Robot.ClimberDeploy.deployClimber();
            Robot.RoboWrangler.drive(-0.33);
            System.out.print("**************** i value: " + i); // TODO: REMOVE PRINT
            i = i + 1;
            // TODO: Adjust to allow appropriate amount of time for deployment
            // Stop running the RoboWrangler drive motor before the end of this phase,
            // preventing the rope from getting tangled
            if (i > 100) {
                Robot.RoboWrangler.stop();
            } else if (i > 200) {
                Robot.ClimberDeploy.climberPhase = 2;
            }

            break;

        // Lower the trolley (raising the robot)
        case 2:
            Robot.Lift.setSetpoint(Robot.Lift.climbLevel3Position);
            if (Robot.Lift.atPosition(10)) {
                Robot.ClimberDeploy.climberPhase = 3;
            }
            break;

        // Drive forward onto level 3 platform until the line sensor senses the platform
        case 3:
            Robot.RoboWrangler.drive(0.5);
            // TODO: Remove timer once line sensor is installed
            j = j + 1;
            System.out.print("**************** j value: " + j); // TODO: REMOVE PRINT
            if (j > 200) {
                Robot.ClimberDeploy.climberPhase = 4;
            }
            break;

        // Raise the trolley (lifting the RoboWrangler wheels off the lvl. 1 platform)
        case 4:
            Robot.Lift.setSetpoint(Robot.Lift.climbWheelsUpPosition);
            if (Robot.Lift.atPosition(10)) {
                Robot.ClimberDeploy.climberPhase = 5;
            }
            break;

        // We have finished climbing
        case 5:
            // TRYING black switch switch from case 5 to 0 (and back).... instead of boolean
            finished = true;
            break;
        }
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }

    protected void end() {
        Robot.RoboWrangler.stop();
        Robot.Lift.setSetpoint(Robot.Lift.getPosition());
    }

    protected void interrupted() {
        end();
    }
}