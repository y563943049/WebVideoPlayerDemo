package com.example.netvideoplayerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {

    private List<MP4> mMP4List = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initVideo();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        MP4Adapter adapter = new MP4Adapter(mMP4List);
        recyclerView.setAdapter(adapter);
    }

    private void initVideo() {
        MP4 cartoon = new MP4();
        cartoon.setName("森林大冒险");
        cartoon.setKind("动画片");
        cartoon.setPath("http://192.168.0.102:8080/webServer/2.mp4");
        mMP4List.add(cartoon);
        MP4 yule = new MP4();
        yule.setName("JJ林俊杰与李荣浩");
        yule.setKind("娱乐八卦");
        yule.setPath("http://192.168.0.102:8080/webServer/yule.mp4");
        mMP4List.add(yule);
        MP4 mv = new MP4();
        mv.setName("性感韩国女团热舞");
        mv.setKind("MV");
        mv.setPath("http://192.168.0.102:8080/webServer/mv.mp4");
        mMP4List.add(mv);
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
}
