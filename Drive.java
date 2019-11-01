/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;


/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *w
 * Use Android Studios to Copy this Class, and Paste it into your team's code  a new name.
 * Remove or comment out the @Disabled line to add this op-mode to the Driver Station folder with OpMode list
 **/
@TeleOp(name="Tank Drive", group="Iterative Opmode") //
    //@Disabled
    public class Drive extends OpMode
    {
        // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor ForwardLeftDrive = null;
    private DcMotor ForwardRightDrive = null;
    private DcMotor BackwardsRightDrive = null;
    private DcMotor BackwardsLeftDrive = null;
    Servo servo0;
    Servo servo1;
    Servo servo2;
    private DcMotor Pulley = null;
    //private DcMotor Lift = null;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        ForwardLeftDrive = hardwareMap.get(DcMotor.class, "forwards_left_drive");
        ForwardRightDrive = hardwareMap.get(DcMotor.class, "forwards_right_drive");
        BackwardsLeftDrive = hardwareMap.get(DcMotor.class, "backwards_left_drive");
        BackwardsRightDrive = hardwareMap.get(DcMotor.class, "backwards_right_drive");
        servo0 = hardwareMap.servo.get("servo0"); //capball
        servo1 = hardwareMap.servo.get("servo1"); //capball
        servo2 = hardwareMap.servo.get("servo2");
        Pulley = hardwareMap.get(DcMotor.class, "Pulley_Motor");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        ForwardLeftDrive.setDirection(DcMotor.Direction.FORWARD); //motor 1
        ForwardRightDrive.setDirection(DcMotor.Direction.REVERSE); //motor 2
        BackwardsLeftDrive.setDirection(DcMotor.Direction.FORWARD); //motor 3
        BackwardsRightDrive.setDirection(DcMotor.Direction.REVERSE); //motor 4
        //Lift.setDirection(DcMotor.Direction.FORWARD);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        telemetry.addData("ERROR:", "Robot has failed inspection. Please consult Bartholaius for more information.");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry
        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;
        double upwardspulleyPower;
        double downwardspulleyPower;

        // Choose to drive using either Tank Mode, or POV Mode
        // Comment out the method that's not used.  The default below is POV.

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        //double drive = -gamepad1.left_stick_y;
        //double turn  =  gamepad1.right_stick_x;
        //frontRightPower = Range.clip(drive - turn, -1.0, 1.0) ;
        //frontLeftPower = Range.clip(drive + turn, -1.0, 1.0);
        //backLeftPower = Range.clip(drive + turn, -1.0, 1.0) ;
        //backRightPower = Range.clip(drive - turn, -1.0, 1.0) ;

        // Tank Mode uses one stick to control each wheel.
        // - This requires no math, but it is hard to drive forward slowly and keep straight.
        frontLeftPower = gamepad1.left_stick_y;
        backLeftPower = -gamepad1.left_stick_y;
        frontRightPower = -gamepad1.right_stick_y;
        backRightPower = -gamepad1.right_stick_y;
        upwardspulleyPower = gamepad2.left_stick_y;

        //opens arms
        if (gamepad2.a) {
            servo0.setPosition(1);
            servo1.setPosition(0);
        }
        //closes arms
        if (gamepad2.b) {
            servo0.setPosition(0);
            servo1.setPosition(1);
        }
        //raises color arm
        if (gamepad2.dpad_up) {
            servo2.setPosition(1);
        }
        //lowers color arm
        if (gamepad2.dpad_down) {
            servo2.setPosition(0);
        }
        if(gamepad2.left_bumper)
        //liftPower = -gamepad1.right_trigger;

        // Send calculated power to wheels
        ForwardLeftDrive.setPower(frontLeftPower);
        ForwardRightDrive.setPower(frontRightPower);
        BackwardsLeftDrive.setPower(backLeftPower);
        BackwardsRightDrive.setPower(backRightPower);
        Pulley.setPower(upwardspulleyPower);
        //Lift.setPower(liftPower);

        // Show the elapsed game time and wheel power.
        //telemetry.addData("Status", "Run Time: " + runtime.toString());
        //telemetry.addData("Motors", backLeft (%.2f), backRight (%.2f)", backLeftPower, backRightPower);
        //telemetry.addData("Motors", "frontleft (%.2f), frontright (%.2f), backLeft (%.2f), backRight (%.2f)", frontLeftPower, frontRightPower, backLeftPower, backRightPower);
    }



    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
