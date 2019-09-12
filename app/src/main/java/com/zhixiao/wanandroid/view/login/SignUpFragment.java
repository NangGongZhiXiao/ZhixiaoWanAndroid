package com.zhixiao.wanandroid.view.login;

import android.annotation.SuppressLint;
import android.os.Build;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.base.presenter.BasePresenter;
import com.zhixiao.wanandroid.base.view.AbstractBaseFragment;
import com.zhixiao.wanandroid.presenter.login.SignUpContract;
import com.zhixiao.wanandroid.presenter.login.SignUpPresenter;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: SignUpFragment
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/12
 */
public class SignUpFragment extends AbstractBaseFragment<SignUpContract.Presenter>
        implements SignUpContract.View{

    @BindView(R.id.img_go_back)
    public ImageView imgBack;
    @BindView(R.id.et_sign_up_name)
    public EditText etName;
    @BindView(R.id.et_sign_up_password)
    public EditText etPw;
    @BindView(R.id.et_sign_up_repassword)
    public EditText etRP;
    @BindView(R.id.tv_sign_up_confirm)
    public TextView tvConfirm;
    @BindView(R.id.tv_back_login)
    public TextView tvBackLogin;

    @OnClick({R.id.img_go_back, R.id.tv_back_login})
    public void backLogin(){
        pop();
    }

    @Override
    protected SignUpContract.Presenter createPresenter() {
        return new SignUpPresenter();
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public void setNightMode(boolean night) {

    }

    @Override
    public boolean getNightMode() {
        return false;
    }
}
