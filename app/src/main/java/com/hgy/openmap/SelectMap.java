package com.hgy.openmap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectMap extends AppCompatActivity implements OnClickListener {

    private Button btn_select_baidu, btn_select_gaode, btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_map_layout);

        //设置dialog的宽度为当前手机屏幕的宽度
        WindowManager wm = getWindow().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = outMetrics.widthPixels;
        getWindow().setAttributes(p);

        btn_select_baidu = (Button) this.findViewById(R.id.btn_select_baidu);
        btn_select_gaode = (Button) this.findViewById(R.id.btn_select_gaode);
        btn_cancel = (Button) this.findViewById(R.id.btn_cancel);

//        检查是否有高德地图或者百度地图
        if (isAppAvilible(getApplicationContext(), "com.autonavi.minimap")) {
            btn_select_gaode.setVisibility(View.VISIBLE);
        } else{
            btn_select_gaode.setVisibility(View.GONE);
        }

        if (isAppAvilible(getApplicationContext(), "com.baidu.BaiduMap")) {
            btn_select_baidu.setVisibility(View.VISIBLE);
        } else{
            btn_select_baidu.setVisibility(View.GONE);
        }

        //添加按钮监听
        btn_cancel.setOnClickListener(this);
        btn_select_baidu.setOnClickListener(this);
        btn_select_gaode.setOnClickListener(this);
    }

    //实现 onTouchEvent 触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    public void onClick(View v) {
        Intent i1 = null;
        switch (v.getId()) {
            case R.id.btn_select_baidu:
                i1 = new Intent();
                i1.setData(Uri.parse("baidumap://map/geocoder?location=26.727104,106.63375&src=andr.baidu.openAPIdemo"));
                startActivity(i1);
                break;
            case R.id.btn_select_gaode:
                i1 = new Intent();
                i1.setData(Uri.parse("androidamap://viewReGeo?sourceApplication=iwatersgisapp&lat=26.478672&lon=106.70232&dev=0"));
                startActivity(i1);
                break;
            case R.id.btn_cancel:
                break;
            default:
                break;
        }
        finish();
    }

    // 验证各种导航地图是否安装
    public boolean isAppAvilible(Context context, String packageName) {

        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();

        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);

        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();

        //从 pinfo 中将包名字逐一取出，压入 pName list 中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);
    }

}
