package com.example.netvideoplayerdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SurfaceView sv;

    private ProgressBar pb;

    private SeekBar sb;

    private SharedPreferences sp;

    private static final String TAG = "MainActivity";

    private MediaPlayer mediaPlayer;

    private Timer timer;

    private TimerTask task;

    private ImageButton play;

    private Bitmap pause_image;

    private Bitmap play_image;

    private ImageButton reset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    /**
     * 初始化事件操作
     * */
    private void initEvent() {
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /*进度改变*/
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            /*开始滑动*/
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            /*停止滑动*/
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mediaPlayer != null && mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }
        });

        /**
         * 通过SV的holder设置回调
         * */
        sv.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "surfaceCreated: 被创建");
                try {
                    MP4 mp4 = (MP4) getIntent().getSerializableExtra("mp4");
                    if (mp4 == null){
                        mp4 = new MP4();
                        mp4.setPath("http://192.168.0.102:8080/webServer/2.mp4");
                    }
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(mp4.getPath());
                    /*设置播放容器*/
                    mediaPlayer.setDisplay(sv.getHolder());
                    /*异步准备，开启子线程*/
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt("position",0);
                            editor.commit();
                        }
                    });
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(final MediaPlayer mediaPlayer) {
                            pb.setVisibility(View.INVISIBLE);
                            mediaPlayer.start();
                            int position = sp.getInt("position",0);
                            mediaPlayer.seekTo(position);
                            timer = new Timer();
                            task = new TimerTask() {
                                @Override
                                public void run() {
                                    int max = mediaPlayer.getDuration();
                                    sb.setMax(max);
                                    int currentProgress = mediaPlayer.getCurrentPosition();
                                    sb.setProgress(currentProgress);
                                }
                            };
                            timer.schedule(task,0,500);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "surfaceDestroyed: 被销毁了");
                if(mediaPlayer != null && mediaPlayer.isPlaying()){
                    int position = mediaPlayer.getCurrentPosition();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("position",position);
                    editor.commit();
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    timer.cancel();
                    task.cancel();
                    timer = null;
                    task = null;
                }

            }
        });

    }

    /**
     * 初始化数据操作
     * */
    private void initData() {
        sp = getSharedPreferences("config",MODE_PRIVATE);
        play.setOnClickListener(this);
        reset.setOnClickListener(this);
        pause_image = BitmapFactory.decodeResource(getResources(),R.drawable.pause);
        play_image = BitmapFactory.decodeResource(getResources(),R.drawable.play);
    }

    /**
     * 初始化视图操作
     * */
    private void initView() {
        setContentView(R.layout.activity_main);
        sv = (SurfaceView) findViewById(R.id.sv);
        pb = (ProgressBar) findViewById(R.id.pb);
        sb = (SeekBar) findViewById(R.id.sb);
        play = (ImageButton) findViewById(R.id.player_button);
        reset = (ImageButton) findViewById(R.id.reset_button);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.select_video:
                Intent select_intent = new Intent(this,VideoActivity.class);
                startActivity(select_intent);
                break;
            case R.id.finish:
                finish();
                break;
            case R.id.web_view:
                Intent web_intent = new Intent(this,WebActivity.class);
                startActivity(web_intent);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                sb.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(3000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sb.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                }).start();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.player_button:
                if(mediaPlayer.isPlaying() && mediaPlayer != null){
                    play.setImageBitmap(play_image);
                    mediaPlayer.pause();
                }else {
                    play.setImageBitmap(pause_image);
                    mediaPlayer.start();
                }
                break;
            case R.id.reset_button:
                if(mediaPlayer!=null){
                    mediaPlayer.seekTo(0);
                }
            default:
                break;
        }
    }
}
