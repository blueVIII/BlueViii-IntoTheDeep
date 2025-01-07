package org.firstinspires.ftc.teamcode.auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "BlueSpecimenAuto", group = "Autonomous")
public class BlueSpecimenAuto extends LinearOpMode {

    public class Lift {
        private DcMotorEx liftMotor1, liftMotor2;

        public Lift(HardwareMap hardwareMap) {
            liftMotor1 = hardwareMap.get(DcMotorEx.class, "liftMotor1");
            liftMotor2 = hardwareMap.get(DcMotorEx.class, "liftMotor2");
            liftMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            liftMotor1.setDirection(DcMotorSimple.Direction.FORWARD);
            liftMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            liftMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        public class LiftUp implements Action {
            private boolean initialized = false;
            // Example target
            private double targetPos = 3000.0;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                // On first run, start lifting
                if (!initialized) {
                    liftMotor1.setPower(0.8);
                    liftMotor2.setPower(0.8);
                    initialized = true;
                }
                double pos1 = liftMotor1.getCurrentPosition();
                double pos2 = liftMotor2.getCurrentPosition();

                packet.put("liftMotor1Pos", pos1);
                packet.put("liftMotor2Pos", pos2);

                // If either motor below target, keep going
                if (pos1 < targetPos || pos2 < targetPos) {
                    return true;  // Keep re-running
                } else {
                    // Stop both motors
                    liftMotor1.setPower(0);
                    liftMotor2.setPower(0);
                    return false; // Done
                }
            }
        }
        public Action liftUp() {
            return new LiftUp();
        }

        // Example: LiftDown Action
        public class LiftDown implements Action {
            private boolean initialized = false;
            // Example lower limit
            private double lowerLimit = 100.0;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                // On first run, power motors downward
                if (!initialized) {
                    liftMotor1.setPower(-0.6);
                    liftMotor2.setPower(-0.6);
                    initialized = true;
                }
                double pos1 = liftMotor1.getCurrentPosition();
                double pos2 = liftMotor2.getCurrentPosition();

                packet.put("liftMotor1Pos", pos1);
                packet.put("liftMotor2Pos", pos2);

                // If either motor is still above the lower limit, keep going
                if (pos1 > lowerLimit || pos2 > lowerLimit) {
                    return true;
                } else {
                    // Stop both motors
                    liftMotor1.setPower(0);
                    liftMotor2.setPower(0);
                    return false; // Done
                }
            }
        }
        public Action liftDown() {
            return new LiftDown();
        }
    }
    //-------------------------------------------------------------------------
    // CLAW CLASS (manages 4 servos)
    //-------------------------------------------------------------------------
    /*public class Claw {
        private Servo liftClaw;
        private Servo liftClawRotate_Claw;
        private Servo liftClawRotate_Arm;
        private Servo liftClawExtender;

        public Claw(HardwareMap hardwareMap) {
            // Grab your claw-related servos
            liftClaw            = hardwareMap.get(Servo.class, "liftClaw");
            liftClawRotate_Claw = hardwareMap.get(Servo.class, "liftClawRotate_Claw");
            liftClawRotate_Arm  = hardwareMap.get(Servo.class, "liftClawRotate_Arm");
            liftClawExtender    = hardwareMap.get(Servo.class, "liftClawExtender");
        }

        // Example: close the claw
        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                // Adjust the servo position as needed for “close”
                liftClaw.setPosition(0.0);
                return false; // runs once
            }
        }
        public Action closeClaw() {
            return new CloseClaw();
        }

        // Example: open the claw
        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                liftClaw.setPosition(1.0);
                return false;
            }
        }
        public Action openClaw() {
            return new OpenClaw();
        }

        // Example: rotate arm downward
        public class ArmDown implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                liftClawRotate_Arm.setPosition(0.3);
                return false;
            }
        }
        public Action armDown() {
            return new ArmDown();
        }

        // Example: rotate arm upward
        public class ArmUp implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                liftClawRotate_Arm.setPosition(1.0);
                return false;
            }
        }
        public Action armUp() {
            return new ArmUp();
        }

        // Example: rotate the claw servo downward
        public class RotateClawDown implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                liftClawRotate_Claw.setPosition(0.0);
                return false;
            }
        }
        public Action rotateClawDown() {
            return new RotateClawDown();
        }

        // Example: rotate the claw servo upward
        public class RotateClawUp implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                liftClawRotate_Claw.setPosition(1.0);
                return false;
            }
        }
        public Action rotateClawUp() {
            return new RotateClawUp();
        }

        // Example: extend the claw
        public class ExtendClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                liftClawExtender.setPosition(1.0);
                return false;
            }
        }
        public Action extendClaw() {
            return new ExtendClaw();
        }

        // Example: retract the claw
        public class RetractClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                liftClawExtender.setPosition(0.0);
                return false;
            }
        }
        public Action retractClaw() {
            return new RetractClaw();
        }
    } */

    //-------------------------------------------------------------------------
    // MAIN AUTONOMOUS CODE
    //-------------------------------------------------------------------------
    @Override
    public void runOpMode() {
        // 1) INITIALIZE DRIVE (frontLeft, rearLeft, frontRight, rearRight)
        //    and set the robot's starting pose
        double halfWidth = 15.5 / 2;
        double halfLength = 15.375 / 2;
        Pose2d initialPose = new Pose2d(24 - halfWidth, -72 + halfLength, Math.toRadians(90));
        // You will need to implement a MecanumDrive class that
        // uses the hardwareMap to grab frontLeft, frontRight, etc.
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);


        // 2) INITIALIZE LIFT & CLAW
        Lift lift = new Lift(hardwareMap);
        //Claw claw = new Claw(hardwareMap);

        // 4) BUILD YOUR TRAJECTORIES
        //    This is purely an example that you can revise for your route
        TrajectoryActionBuilder drive1 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-12 + halfWidth, -39.5 + halfLength))
                .waitSeconds(1)
                .lineToY(-42.5 + halfLength, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(47 - halfWidth, -45.5 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-22 + halfLength, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(53, -22 + halfLength),null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-52, null, new ProfileAccelConstraint(-80, 80))
                .lineToY(-22 + halfLength, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(63, -22 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-52)
                /*.lineToY(-22 + halfLength)
                .waitSeconds(0.01)
                .strafeTo(new Vector2d(71.5, -22 + halfLength))
                .setTangent(Math.toRadians(90))
                .lineToY(-52) */
                .strafeTo(new Vector2d(64,-42), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(52, -76 + halfLength, Math.toRadians(250)),Math.toRadians(250), null, new ProfileAccelConstraint(-80, 80))
                .waitSeconds(1)
                .strafeTo(new Vector2d(42, -65 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .splineToLinearHeading(new Pose2d(-6, -39.5 + halfLength, Math.toRadians(90)),Math.toRadians(90), null, new ProfileAccelConstraint(-80, 80))
                .waitSeconds(1)
                .strafeTo(new Vector2d(5, -45 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .splineToLinearHeading(new Pose2d(45, -76 + halfLength, Math.toRadians(250)),Math.toRadians(250), null, new ProfileAccelConstraint(-80, 80))
                .waitSeconds(1)
                .strafeTo(new Vector2d(35, -65 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .splineToLinearHeading(new Pose2d(-6, -39.5 + halfLength, Math.toRadians(90)),Math.toRadians(90), null, new ProfileAccelConstraint(-80, 80))
                //.splineToLinearHeading(new Pose2d(-12 + halfWidth, -39.5 + halfLength, Math.toRadians(90)),Math.toRadians(255))
                //.strafeToLinearHeading(new Vector2d(50, -72 + halfLength), Math.toRadians(270))
                //.splineToSplineHeading(new Pose2d(50, -72 + halfLength, Math.toRadians(270)),Math.toRadians(270))
                ;
        TrajectoryActionBuilder stlhtest = drive.actionBuilder(initialPose)
                .splineToSplineHeading(new Pose2d(50, -72 + halfLength, Math.toRadians(270)),270);
        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .lineToYSplineHeading(33, Math.toRadians(0)) // go to y=33, heading=0 deg
                .waitSeconds(2)
                .setTangent(Math.toRadians(90))
                .lineToY(48)
                .setTangent(Math.toRadians(0))
                .lineToX(32)
                .strafeTo(new Vector2d(44.5, 30))
                .turn(Math.toRadians(180))
                .lineToX(47.5)
                .waitSeconds(3);

        TrajectoryActionBuilder tab2 = drive.actionBuilder(initialPose)
                .lineToY(37)
                .setTangent(Math.toRadians(0))
                .lineToX(18)
                .waitSeconds(3)
                .setTangent(Math.toRadians(0))
                .lineToXSplineHeading(46, Math.toRadians(180))
                .waitSeconds(3);

        TrajectoryActionBuilder tab3 = drive.actionBuilder(initialPose)
                .lineToYSplineHeading(33, Math.toRadians(180))
                .waitSeconds(2)
                .strafeTo(new Vector2d(46, 30))
                .waitSeconds(3);

        Action trajectoryActionCloseOut = tab1.endTrajectory().fresh()
                .strafeTo(new Vector2d(48, 12))
                .build();

        // 5) (OPTIONAL) DO ANY ACTIONS ON INIT
        //Actions.runBlocking(claw.closeClaw()); // For instance, keep claw closed

        waitForStart();
        if (isStopRequested()) return;

        Action test = stlhtest.build();
        Action trajectoryAction1 = drive1.build();
        Action trajectoryActionChosen1 = tab1.build();
        Action trajectoryActionChosen2 = tab2.build();
        Action trajectoryActionChosen3 = tab3.build();

        // 8) RUN THE ACTION SEQUENCE
        Actions.runBlocking(
                new SequentialAction(
                        trajectoryAction1,
                        lift.liftUp(),
                        //claw.openClaw(),
                        lift.liftDown(),
                        trajectoryActionCloseOut
                )
        );
    }
}




