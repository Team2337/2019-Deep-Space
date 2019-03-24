package frc.robot.commands.ClimberDeploy;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Lift;

public class climbBigBrother extends Command {
    // TODO: Change i and j to a variable name that means something
    // i is a timer, and so is j
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

        if (Robot.ClimberDeploy.climberPhase == 1) {
            Robot.ClimberDeploy.climberPhase = 2;
        }

        if (Robot.ClimberDeploy.climberPhase == 3) {
            Robot.ClimberDeploy.climberPhase = 4;
        }

        // This changes to true when the climber has reached its final phase
        finished = false;
    }

    @Override
    protected void execute() {

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
            // move servo to release T-rex arms and legs.
            // Operator needs to hit button again to continue to next case.
            Robot.ClimberDeploy.deployClimber();
            break;

        // Lower the trolley (raising the robot)
        case 2:
            Lift.setSoftLimits(Lift.forwardLiftSoftLimit, 91);
            Robot.Lift.setSetpoint(Robot.Lift.climbLevel3Position);
            if (Robot.Lift.atPosition(10)) {
                Robot.ClimberDeploy.climberPhase = 3;
                Robot.ClimberDeploy.undeployClimber(); // Reset the servo
            }
            break;

        // Drive forward onto level 3 platform until the line sensor senses the platform
        case 3:
            Robot.RoboWrangler.drive(0.75);

            if (!Robot.ClimberDeploy.climberLineSensor.get()) {
                System.out.println("******************************CLIMBER LINE SENSOR WAS DETECTED");
                Robot.RoboWrangler.stop();
                Robot.ClimberDeploy.climberPhase = 4;
            }

            // TODO: Remove timer once line sensor is installed
            j = j + 1;
            if (j > 100) {
                System.out.println("******************************J has finished");
                Robot.RoboWrangler.stop();
                // Robot.ClimberDeploy.climberPhase = 5;
            }
            break;

        // Raise the trolley (lifting the RoboWrangler wheels off the lvl. 1 platform)
        case 4:
            Robot.Lift.setSetpoint(Robot.Lift.climbWheelsUpPosition);
            if (Robot.Lift.atPosition(10)) {
                // Lift.setSoftLimits(455, 91);
                Robot.ClimberDeploy.climberPhase = 5;
            }
            break;

        // We have finished climbing
        case 5:
            // TRYING black switch to switch from case 5 to 0 (and back).... instead of
            // boolean
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
        Robot.ClimberDeploy.undeployClimber();
        Robot.Lift.setSetpoint(Robot.Lift.getPosition());
    }

    protected void interrupted() {
        end();
    }
}
