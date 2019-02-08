package frc.robot.commands.Auto;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

/**
 * @author Bryce G.
 */
public class Pathway {
   public static double metersConversion = 0.0254;  //17.5 //35.166666666 - Our conversion from our trials, 33.33333 
   public static Trajectory.Config config;
   public static Trajectory trajectory;

   private static double timeStep = 0.1;

    private double inchesToMeters(double inches) {
        return inches * metersConversion;
    }

   /**
     * Double 2D array
     * The PID values for the drive commands. Call each value by using an int value, to grab the row or column 
     * First dimension: row
     * Second dimension: column
     */
    public static double valuesPID[][] = new double[][] {
        {1.5, 0, 0.15, 0}, //autoReverseToShipFromLvl1
        {1.2, 0, 0, 0}, //curveFromToHatchRightT
        {2, 0, 0, 0}, //fromRightLoadJTurnToCargoShipT
        {2, 0, 0, 0}, //jTurnToCargoShipRightT
        {1.5, 0, 0, 0}, //TEST
    };

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
    config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 2, 1.5, 10.0);
    trajectory = Pathfinder.generate(autoReverseToShipFromLvl1, config);
    return trajectory;
  }


  private static Waypoint[] curveFromToHatchRight = new Waypoint[] {
    new Waypoint(0, 0, 0),
    new Waypoint(3.5, 2, 0.42), //3.5, 2.35
    new Waypoint(5.5, 2.35, 0) //5.95
 };
/**
* Converts the waypoints to generate the path into values readable by the code
* @param points - array of waypoints
*/
public static Trajectory curveFromToHatchRight() {
 config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 1.5, 0.35, 0.1);  //2, 1.7, 10
 trajectory = Pathfinder.generate(curveFromToHatchRight, config);
 return trajectory;
}


    private static Waypoint[] fromRightLoadJTurnToCargoShip = new Waypoint[] {
        new Waypoint(0, 0, 0),
        new Waypoint(2, -.8, -0.58), //33 degrees
        // new Waypoint(4, -1.2, 0.1),
        new Waypoint(5.8, -0.6, 1.2)
    };
    /**
    * Converts the waypoints to generate the path into values readable by the code
    * @param points - array of waypoints
    */
    public static Trajectory fromRightLoadJTurnToCargoShip() {
    config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 2.5, 1.9, 10.0);  //2, 1.7
    trajectory = Pathfinder.generate(fromRightLoadJTurnToCargoShip, config);
    return trajectory;
    }


    private static Waypoint[] JTurnToCargoShipRight = new Waypoint[] {
        new Waypoint(0, 0, 0), 
        new Waypoint(2, 0, -0.58)
    };
    /**
    * Converts the waypoints to generate the path into values readable by the code
    * @param points - array of waypoints
    */
    public static Trajectory jTurnToCargoShipRight() {
    config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 2.5, 1.9, 10.0);  //2, 1.7
    trajectory = Pathfinder.generate(JTurnToCargoShipRight, config);
    return trajectory;
    }


    private static Waypoint[] testSCurve = new Waypoint[] {
        new Waypoint(0, 0, 0),            
        new Waypoint(3, 1, 0) //POSITIVE IS LEFT
    };

    /**
   * Converts the waypoints to generate the path into values readable by the code
   * @param points - array of waypoints
   */
  public static Trajectory testSCurve() {
    //old = 1.7, 2.0, 60.0
    //new = 4.267, 5.0, 150.6
    //2,1.75,10
    config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 0.75, 0.35, 10.0);
    trajectory = Pathfinder.generate(testSCurve, config);
    return trajectory;
  }


  private static Waypoint[] driveForward = new Waypoint[] {
    // Waypoint @ x=0, y=0,   exit angle=0 radians
    new Waypoint(0, 0, 0),
    new Waypoint(50, 0, 0)
    //NEGATIVE is RIGHT
 };

/**
* Converts the waypoints to generate the path into values readable by the code
* @param points - array of waypoints
*/
public static Trajectory driveForward() {
 config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 0.5, 0.25, 2.5);
 trajectory = Pathfinder.generate(driveForward, config);
 return trajectory;
}
}