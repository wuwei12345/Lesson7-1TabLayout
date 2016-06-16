package org.mobiletrain.android37_materialdesigndemo.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.ChangeTransform;
import android.transition.Transition;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.mobiletrain.android37_materialdesigndemo.R;
import org.mobiletrain.android37_materialdesigndemo.adapter.MyPagerAdapter;
import org.mobiletrain.android37_materialdesigndemo.application.MyApp;
import org.mobiletrain.android37_materialdesigndemo.fragment.DummyFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private Context mContext = this;
    private DrawerLayout drawerLayout_main;
    private NavigationView navigationView_main;
    private ViewPager viewPager_main;
    private TabLayout tabLayout_main;
    private Toolbar toolbar_main;
    private List<Fragment> fragmentsList = new ArrayList<Fragment>();
    private List<Drawable> imageList = new ArrayList<Drawable>();

    //定义用户名和密码输入正确性与否的标记
    private boolean flag_username = true;
    private boolean flag_pwd = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        initView();

        initTabsAndViewPager();
    }

    private void initView() {
        drawerLayout_main = (DrawerLayout) findViewById(R.id.drawerLayout_main);
        //设置Toolbar上的logo与抽屉同步，实现动画切换效果
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout_main, toolbar_main, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout_main.setDrawerListener(toggle);
        toggle.syncState();

        //初始化导航试图
        navigationView_main = (NavigationView) findViewById(R.id.navigationView_main);
        navigationView_main.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        item.setChecked(true);
                        int tabIndex = 0;
                        switch (item.getItemId()) {
                            case R.id.action_android:
                                tabIndex = 0;
                                break;
                            case R.id.action_h5:
                                tabIndex = 1;
                                break;
                            case R.id.action_ios:
                                tabIndex = 2;
                                break;
                            case R.id.action_ui:
                                tabIndex = 3;
                                break;
                        }
                        //滑动ViewPager，实现TAB切换
                        viewPager_main.setCurrentItem(tabIndex);
                        //关闭抽屉
                        drawerLayout_main.closeDrawers();
                        return true;
                    }
                });
    }


    private void initToolbar() {
        toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_main);

        //重新设置logo前方的图标
        //toolbar_main.setNavigationIcon(R.mipmap.ic_menu);
        //toolbar_main.setLogo(R.mipmap.ic_launcher);
        //title默认为APP的Label名称，也可以重新设置：例如：setTitle("材料设计综合案例一");
        //如果不设置子标题，那么title在垂直居中的位置显示。如果设置则上下各一行显示。可以分别设置文字颜色。
        toolbar_main.setSubtitle("育知同创课程介绍");
        toolbar_main.setTitleTextColor(Color.WHITE);
        toolbar_main.setSubtitleTextColor(Color.YELLOW);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void initTabsAndViewPager() {
        tabLayout_main = (TabLayout) findViewById(R.id.tabLayout_main);
        String[] arrTabTitles = getResources().getStringArray(R.array.arrTabTitles);

        viewPager_main = (ViewPager) findViewById(R.id.viewPager_main);

        for (int i = 0; i < arrTabTitles.length; i++) {
            DummyFragment fragment = DummyFragment.getInstance(i + 1);
            fragmentsList.add(fragment);
        }
        PagerAdapter adapter = new MyPagerAdapter(
                getSupportFragmentManager(), fragmentsList, arrTabTitles);
        viewPager_main.setAdapter(adapter);

        tabLayout_main.setupWithViewPager(viewPager_main);
        tabLayout_main.setTabsFromPagerAdapter(adapter);
    }


    public void clickButton(View view) {
        // 启动动画
        MyApp.startAnimation(view, 500, 1);

        switch (view.getId()) {
            case R.id.fab_main:
                Snackbar.make(view, "注册用户可看更多内容！", Snackbar.LENGTH_LONG)
                        .setAction("注册", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                View registerView = getLayoutInflater().inflate(R.layout.dialog_login, null);
                                final TextInputLayout textInputLayout_username = (TextInputLayout) registerView.findViewById(R.id.textInputLayout_username);
                                final TextInputLayout textInputLayout_pwd = (TextInputLayout) registerView.findViewById(R.id.textInputLayout_pwd);
                                final EditText editText_dialog_username = textInputLayout_username.getEditText();
                                final EditText editText_dialog_pwd = textInputLayout_pwd.getEditText();

                                editText_dialog_username.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if (!isChinese(s.toString())) {
                                            textInputLayout_username.setErrorEnabled(true);
                                            textInputLayout_username.setError("用户名必须是中文！");
                                            flag_username = false;
                                        } else {
                                            if (s.length() > 4) {
                                                textInputLayout_username.setErrorEnabled(true);
                                                textInputLayout_username.setError("文字不可以超过4个！");
                                                flag_username = false;
                                            } else {
                                                textInputLayout_username.setErrorEnabled(false);
                                                flag_username = true;
                                            }
                                        }
                                    }
                                });

                                editText_dialog_pwd.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if (!isPwd(s.toString())) {
                                            textInputLayout_pwd.setErrorEnabled(true);
                                            textInputLayout_pwd.setError("密码必须是6位数字、字母！");
                                            flag_pwd = false;
                                        } else {
                                            textInputLayout_pwd.setErrorEnabled(false);
                                            flag_pwd = true;
                                        }
                                    }
                                });

                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setIcon(R.mipmap.ic_class)
                                        .setTitle("用户注册")
                                        .setView(registerView)
                                        .setNegativeButton("取消", null)
                                        .setPositiveButton("注册", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String username = editText_dialog_username.getText() + "";
                                                String pwd = editText_dialog_pwd.getText() + "";
                                                if (username.equals("") || "".equals(pwd)) {
                                                    Toast.makeText(mContext, "信息不可以为空！", Toast.LENGTH_LONG).show();
                                                }
                                                if (flag_username && flag_pwd) {
                                                    Toast.makeText(mContext, username + ":" + pwd, Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(mContext, "信息输入有误，不可以注册！", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                builder.show();
                            }
                        }).show();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout_main.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 页面跳转，实现共享元素场景切换动画(转场动画)
     */
    public void startActivity(View view, int position) {
        // 启动页面跳转
        Intent intent = new Intent();
        intent.setClass(mContext, DetailActivity.class);
        intent.putExtra("position", position);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            View imageView_item_thumbnail = view.findViewById(R.id.imageView_item_icon);
            View textView_item_title = view.findViewById(R.id.textView_item_title);
            View textView_item_teacher = view.findViewById(R.id.textView_item_teacher);
            // 为当前Activity设置共享元素的场景切换动画
            Transition transition = new ChangeTransform();
            transition.setDuration(6000);
            getWindow().setExitTransition(transition);

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    (Activity) mContext,
                    Pair.create(textView_item_title, "text_detail"),
                    Pair.create(textView_item_teacher, "text_teacher"),
                    Pair.create(imageView_item_thumbnail, "banner"));
            Bundle bundle = options.toBundle();
            startActivity(intent, bundle);
        } else {
            startActivity(intent);
        }
    }

    private boolean isChinese(String str) {
        String regexp = "^[\u4e00-\u9fa5]+";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    private boolean isPwd(String str) {
        String regexp = "^[0-9a-zA-Z]{6}$";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    /**
     * 按钮按下事件onKeyDown方法此方法兼容Android 1.0到Android 2.1 。也是常规方法
     * 2.0或更新版的sdk，重写onBackPressed方法即可
     * 因此该方法仅适用于2.0以上版本
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout_main.isDrawerOpen(GravityCompat.START)) {
            drawerLayout_main.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
