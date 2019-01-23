package frc.robot.commands.Auto;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

public class Pathway {
   public static double pathweaverConversion;  //17.5 //35.166666666 - Our conversion from our trials, 33.33333 
   public static Trajectory.Config config;
   public static Trajectory trajectory;

   private static double timeStep = 0.02;

   private static Waypoint[] autoReverseToShipFromLvl1 = new Waypoint[] {
       // Waypoint @ x=0, y=0,   exit angle=0 radians
       new Waypoint(0, 0, 0),
       new Waypoint(5, 0, 0)
        //new Waypoint(convert(195), 0, 0) //256 inches out
        //180 = 237
    };

  /**
   * Converts the waypoints to generate the path into values readable by the code
   * @param points - array of waypoints
   */
  public static Trajectory autoReverseToShipFromLvl1() {
    //old = 1.7, 2.0, 60.0
    //new = 4.267, 5.0, 150.6
    //2,1.75,10
    // FIRST S CURVE -- config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 2, 1.9, 10.0);
    // 1.7
    config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 2, 1.9, 10.0);
    trajectory = Pathfinder.generate(autoReverseToShipFromLvl1, config);
    return trajectory;
  }


  private static Waypoint[] curveFromToHatchRight = new Waypoint[] {
    new Waypoint(0, 0, 0),
    new Waypoint(5.95, 2.35, 0)
 };
/**
* Converts the waypoints to generate the path into values readable by the code
* @param points - array of waypoints
*/
public static Trajectory curveFromToHatchRight() {
 config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 2.5, 1.9, 10.0);  //2, 1.7
 trajectory = Pathfinder.generate(curveFromToHatchRight, config);
 return trajectory;
}


    private static Waypoint[] testSCurve = new Waypoint[] {
        new Waypoint(0, 0, 0),            
        new Waypoint(3, -1, 0)
    };

    /**
   * Converts the waypoints to generate the path into values readable by the code
   * @param points - array of waypoints
   */
  public static Trajectory testSCurve() {
    //old = 1.7, 2.0, 60.0
    //new = 4.267, 5.0, 150.6
    //2,1.75,10
    config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 2, 1.9, 10.0);
    trajectory = Pathfinder.generate(testSCurve, config);
    return trajectory;
  }
}