package frc.robot.commands.ClimberDeploy;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.RoboWrangler.driveWranglerBySpeed;

public class CGClimb extends CommandGroup {
    int phase;
    // CONSTRUCTOR
    public CGClimb() {
        this.phase = Robot.ClimberDeploy.climberPhase;
        // TODO: if (Robot.ClimberDeploy.readyToClimb) {
        switch (this.phase) {
        case 0:  //move trolley to position to release climber arms(to miss platform) and legs(still hit bumpers)
           addSequential(new goToPositionClimber(Robot.Lift.climbDeployPosition));
        case 1:  //release climber arms and legs
            addSequential(new deployClimber(4));
        case 2:  //lower trolley/raise robot
            addSequential(new goToPositionClimber(Robot.Lift.climbLevel3Position));
        case 3:  //drive forward, onto level 3 platform, untill line sensor senses platform
            addSequential(new driveWranglerBySpeed(0.3, 2));
        case 4:  //rasie trolley to lift wrangler wheels off of lower platform
            addSequential(new goToPositionClimber(Robot.Lift.climbWheelsUpPosition));
            break;  // if this doesnt stop looping, just delete and use case 5....
        case 5:  // added to prevent command from starting over for some reason....
            addSequential(new neverEndCG());
        }    
        //}
    }
}