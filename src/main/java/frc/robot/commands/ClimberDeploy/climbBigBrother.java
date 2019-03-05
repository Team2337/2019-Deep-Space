package frc.robot.commands.ClimberDeploy;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class climbBigBrother extends Command {
    int i,j;
    boolean finished;

    // CONSTRUCTOR
    public climbBigBrother() {
        requires(Robot.Lift);
        requires(Robot.ClimberDeploy);
        requires(Robot.RoboWrangler);
    }

    protected void initialize() {
        i=0;j=0;
        finished=false;
    }

    @Override
    protected void execute() {
        // TODO: if (Robot.ClimberDeploy.readyToClimb) {  //or does switching case between 5 and 0 work?
        switch (Robot.ClimberDeploy.climberPhase) {
        case 0:  //move trolley to position to release climber arms(to miss platform) but legs(still hit bumpers)
           Robot.Lift.setSetpoint(Robot.Lift.climbDeployPosition);
           if (Robot.Lift.atPosition(10)) { Robot.ClimberDeploy.climberPhase = 1;}
           break;
        case 1:  //release climber arms and legs
            Robot.ClimberDeploy.deployClimber();
            i=i+1;  //TODO: adjust to allow appropaite amount of time for deployment
            System.out.print(i+"my iiiiiiiiiiiiiiiiiiiiiiiiii"); //TODO: REMOVE PRINT
            if (i>200){Robot.ClimberDeploy.climberPhase = 2;}
            break;
        case 2:  //lower trolley/raise our robot          
            Robot.Lift.setSetpoint(Robot.Lift.climbLevel3Position);
            if (Robot.Lift.atPosition(10)) { Robot.ClimberDeploy.climberPhase = 3;}
            break;
        case 3:  //drive forward, onto level 3 platform, until the line sensor senses platform
            Robot.RoboWrangler.drive(0.5);
            j=j+1; // TODO: remove once line sensor is installed
            System.out.print(j+"my jjjjjjjjjjjjjjjjjjjjjjjjjjj");  //TODO: REMOVE PRINT
            //if (Robot.Chassis.linesensor) { 
            if (j>200){
                Robot.ClimberDeploy.climberPhase = 4;
            }
            break;
        case 4:  //raise trolley to lift wrangler wheels off of lower platform
            //TODO:  comment out until wrangler drive set!!!!!?????
            Robot.Lift.setSetpoint(Robot.Lift.climbWheelsUpPosition);
            if (Robot.Lift.atPosition(10)) { Robot.ClimberDeploy.climberPhase = 5;}
            break;
        case 5:  // done
        //TRYING black switch switch from case 5 to 0 (and back).... instead of boolean
            finished = true;
            break;
        }    
        //}
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }
    protected void end() {
        Robot.RoboWrangler.stop();
        Robot.Lift.setSetpoint(Robot.Lift.getPosition());
        //Robot.ClimberDeploy.undeployClimber();
    }

    protected void interrupted() {
        end();
    }
}