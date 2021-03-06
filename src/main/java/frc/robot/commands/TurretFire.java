/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import static frc.robot.RobotContainer.shooter;
import static frc.robot.RobotContainer.loader;
import static frc.robot.RobotContainer.indexer;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** Vision Tracking is found in the Turret subsystem, however firing Power Cells is handled here */
public class TurretFire extends CommandBase {

  private double delay;
  private double initTime;
  /**
   * Creates a new HandleTurret.
   */
  public TurretFire(double delay) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter, loader, indexer);
    this.delay = delay;
    this.initTime = 0;
  }

  /** Initially spool up. 
  * This will only be executed when an input is recieved from RobotContainer.configButtonBindings()
  */
  @Override
  public void initialize() {
    indexer.stopIndexer();
    loader.stopLoader();
    shooter.startSpoolSequence();
    initTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (shooter.getShooterSpeed() > 3000) {
      //shooter.spoolUp();
    }
    if (shooter.readyToFire(initTime, delay)){
      loader.runLoader();
      indexer.runIndexer();
    }
  }

  /** Stop when trigger in RobotContainer is released */
  @Override
  public void end(boolean interrupted) {
    shooter.spoolDown();
  }

  // Not used, as the command is cancelled by the user
  @Override
  public boolean isFinished() {
    return false;
  }
}
