package frc.robot.commands.ClimberDeploy;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.RoboWrangler.driveWranglerBySpeed;

/**
 * 1. Move the trolley to the position where it can deploy the climber
 */
public class CGClimb extends CommandGroup {
    // CONSTRUCTOR
    public CGClimb() {
        if (Robot.ClimberDeploy.readyToClimb) {
            switch (Robot.ClimberDeploy.climberPhase) {
            case 0:
                addSequential(new goToPositionClimber(Robot.Lift.climbDeployPosition));
            case 1:
                addSequential(new deployClimber(4));
            case 2:
                addSequential(new goToPositionClimber(Robot.Lift.climbLevel3Position));
            case 3:
                addSequential(new driveWranglerBySpeed(1, 1));
            case 4:
                addSequential(new goToPositionClimber(Robot.Lift.climbWheelsUpPosition));
            }
        } else {
            
        }
    }
}