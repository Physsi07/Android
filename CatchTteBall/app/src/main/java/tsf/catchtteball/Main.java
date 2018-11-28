package tsf.catchtteball;

import android.content.Intent;
import android.graphics.Point;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends AppCompatActivity
{
    //Declaring the variables of my images and textboxes
    private TextView scoreLabel;
    private TextView starLabel;
    private ImageView num1;
    private ImageView num66;
    private ImageView num202;
    private ImageView plane;

    //Size
    private int frameHeight;
    private int frameWidth; //***
    private int planeSize;
    private int screenWidth;
    private int screenHeight;


    //Position
    private int planeY;
    private int PlaneX; //***
    private int num1X;
    private int num1Y;
    private int num66X;
    private int num66Y;
    private int num202X;
    private int num202Y;


    //score
    private int score = 0;


    //Initialize Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private soundPlayer sound;

    //Status Check
    private boolean action_flg = false;
    private boolean start_flg = false;

    //Function That appears before playing(Menu)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sound = new soundPlayer(this);

        //Calling the images and textviews we gonna use
        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        starLabel = (TextView) findViewById(R.id.startLabel);
        num1 = (ImageView) findViewById(R.id.Num1);
        num66 = (ImageView) findViewById(R.id.Num66);
        num202 = (ImageView) findViewById(R.id.Num202);
        plane = (ImageView) findViewById(R.id.plane);

        // Get screen size.
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        //Move to out of the screen
        num1.setX(1000);
        num1.setY(500);
        num66.setX(-80);
        num66.setY(-80);
        num202.setX(-80);
        num202.setY(-80);


        scoreLabel.setText("Score : 0");

    }

    //Function to change the position of the plane
    public void changePos()
    {
        hitCheck();


        //num1
        num1X -= 20;
        if (num1X < 0)
        {
            //Starting position of the num1
            //which is 20 away from the screen
            num1X = screenWidth + 20;
            num1Y = (int) Math.floor(Math.random() * (frameHeight - num1.getHeight()));

        }
        num1.setX(num1X);
        num1.setY(num1Y);


        //num202
        num202X -= 16;
        if (num202X < 0)
        {
            //Starting position of the num1
            //which is 10 away from the screen
            num202X = screenWidth + 10;
            num202Y = (int) Math.floor(Math.random() * (frameHeight - num202.getHeight()));

        }
        num202.setX(num202X);
        num202.setY(num202Y);

        //num66
        num66X -= 20;
        if (num66X < 0)
        {
            //Starting position of the num1
            //which is 5000 away from the screen
            num66X = screenWidth + 5000;
            num66Y = (int) Math.floor(Math.random() * (frameHeight - num66.getHeight()));

        }
        num66.setX(num66X);
        num66.setY(num66Y);


        //Move plane
        if (action_flg == true)
        {
            //Touching
            planeY -= 20;

        }
        else
        {
            //Releasing
            planeY += 20;
        }

        //Check plane position
        //This doesnt let the plane go beyong
        //the top of the screen.[TopScreenPosition = (0, 0)]
        if(planeY < 0) planeY = 0;

        //It doesnt let the plane go beyond the bottom
        //of the screen.[BottonScreenPosition = (0, framelayout)]
        if(planeY > frameHeight - planeSize) planeY = frameHeight - planeSize;


        plane.setY(planeY);


        scoreLabel.setText("Score : " + score );

    }

    public void hitCheck()
    {
      //If the center of the ball is in the plane, is a hit

        //num1
        int num1CenterX = num1X + num1.getWidth() / 2;
        int num1CenterY = num1Y + num1.getHeight() / 2;

        //0 <= num1CenterX <= planeWidth
        //planeY <= num1CenterY <= planeY + planeHeight

        if(0 <= num1CenterX && num1CenterX <= planeSize &&
                planeY <= num1CenterY && num1CenterY <= planeY + planeSize)
        {

            score += 10;
            num1X = -10;
            sound.playHitSound();

        }

        //num66
        int num66CenterX = num66X + num66.getWidth() / 2;
        int num66CenterY = num66Y + num66.getHeight() / 2;

        if(0 <= num66CenterX && num66CenterX <= planeSize &&
                planeY <= num66CenterY && num66CenterY <= planeY + planeSize)
        {

            score += 30;
            num66X = -10;
            sound.playHitSound();


        }

        //num202
        int num202CenterX = num202X + num202.getWidth() / 2;
        int num202CenterY = num202Y + num202.getHeight() / 2;

        if(0 <= num202CenterX && num202CenterX <= planeSize &&
                planeY <= num202CenterY && num202CenterY <= planeY + planeSize)
        {

            //Stop timer
            timer.cancel();
            timer = null;
            sound.playOverSound();

            //show result
            Intent intent = new Intent(getApplicationContext(), result.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);

        }
    }

    //Function that makes "plane" move up when the screen is touch
    public boolean onTouchEvent(MotionEvent me)
    {
        if (start_flg == false)
        {
         start_flg = true;

            //Why get frame height and plane height here?
            //Becase the UI has not been set on the screen on OnCreate()!!

            FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
            frameHeight = frame.getHeight();

            planeY = (int)plane.getY();

            //The plane size is a square.(height and width are the same).
            planeSize = plane.getHeight();


         starLabel.setVisibility(View.GONE);

         timer.schedule(new TimerTask()
         {
             @Override
             public void run()
             {
               handler.post(new Runnable()
               {
                 @Override
                  public void run()
                 {
                     changePos();
                 }
               });
             }
         }, 0, 20); //Its going to call changePos() every 20 milisecond
        }
        else
        {
            if (me.getAction() == MotionEvent.ACTION_DOWN)
            {
                action_flg = true;
            }
            else if (me.getAction() == MotionEvent.ACTION_UP)
            {
                action_flg = false;
            }

        }

        return  true;

    }

    //Disable return Button
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_DOWN)
        {
            switch (event.getKeyCode())
            {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
