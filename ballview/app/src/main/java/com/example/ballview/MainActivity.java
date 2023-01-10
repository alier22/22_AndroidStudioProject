package com.example.ballview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(getApplicationContext()));
        //setContentView(R.layout.activity_main);
    }
}

class Ball{

    int x,y,xInc = 1,yInc = 1; //xInc와 yInc는 한번에 움직이는 거리이다.
    int diameter; // 공 반지름
    int WIDTH,HEIGHT;
    int color;
    //static int width = 1080, height = 1920;//움직이는 공간의 크기

    public Ball(int d, int width, int height){
        this.diameter = d;
        this.WIDTH = width;
        this.HEIGHT = height;

        //볼의 위치를 랜덤하게 설정
        x = (int) (Math.random() * (WIDTH - d) +3 );
        y = (int) (Math.random() * (HEIGHT - d) +3 );

        //한번에 움직이는 거리도 랜덤하게 설정
        xInc = (int) (Math.random() * 30 + 1 );
        yInc = (int) (Math.random() * 30 + 1 );
    }


    //공 그리기
    public void paint(Canvas g){
        Paint paint = new Paint();

        //벽에 부딪치면 반사하는 동작
        if (x < diameter || x > (WIDTH - diameter))
            xInc = - xInc;
        if (y < diameter || y > (HEIGHT - diameter))
            yInc = - yInc;


        //볼의 좌표를 갱신
        x += xInc;
        y += yInc;

        //볼을 화면에 그린다.
        paint.setColor(Color.RED);//공색
        g.drawCircle(x,y,diameter,paint);



    }

}

class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    public Ball basket[] = new Ball[10]; //공 10개 만들어줌
    MyThread thread; //스레드

    public MySurfaceView(Context context) {//생성자 함수 만듬
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        for(int i = 0; i<10; i++){
            basket[i] = new Ball(20, width, height);
        }
        thread = new MyThread(holder);

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
    class MyThread extends Thread {

        private boolean mRun = false;
        private SurfaceHolder mSurfaceHolder;

        public  MyThread(SurfaceHolder surfaceHolder) {
            this.mSurfaceHolder =surfaceHolder;
        }

        @Override
        public void run() {
            super.run();
            while (mRun){
                Canvas c= null;
                try{
                    c = mSurfaceHolder.lockCanvas(null); //캔버스 얻음
                    c.drawColor(Color.BLACK);//캔버스 배경색
                    synchronized (mSurfaceHolder){
                        for(Ball b : basket )//basket의 모든 원소를 그린다.
                            b.paint(c);//캔버스에 볼을 넣기
                    }
                }
                finally {
                    if(c != null){
                        //캔버스의 lock을 해제한다.
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        void setRunning (boolean b){
            mRun = b;
        }

    }
}
