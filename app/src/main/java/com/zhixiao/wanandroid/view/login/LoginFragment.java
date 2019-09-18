package com.zhixiao.wanandroid.view.login;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.Group;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zhixiao.wanandroid.R;
import com.zhixiao.wanandroid.base.view.AbstractBaseFragment;
import com.zhixiao.wanandroid.base.view.MVPBaseFragment;
import com.zhixiao.wanandroid.presenter.login.LoginContract;
import com.zhixiao.wanandroid.presenter.login.LoginPresenter;
import com.zhixiao.wanandroid.utils.NamePictureCreator;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: LoginFragment
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/9
 */
public class LoginFragment extends MVPBaseFragment<LoginContract.Presenter>
        implements LoginContract.View {

    private String name;

    @BindView(R.id.img_login_account)
    public ImageView imgAccount;
    @BindView(R.id.img_third_qq)
    public ImageView imgQQ;
    @BindView(R.id.img_third_wechat)
    public ImageView imgWechat;
    @BindView(R.id.tv_account_name)
    public TextView tvName;
    @BindView(R.id.tv_login)
    public TextView tvLogin;
    @BindView(R.id.tv_sign_up)
    public TextView tvSignUp;
    @BindView(R.id.et_login_input)
    public EditText etInput;
    @BindView(R.id.fab_previous)
    public FloatingActionButton fabPrevious;
    @BindView(R.id.gp_account_status)
    public Group gpAccount;
    @BindView(R.id.gp_password_status)
    public Group gpPassword;
    @BindView(R.id.tv_forget)
    public TextView tvForget;
    @BindView(R.id.et_password_input)
    public EditText etPassword;

    @OnClick({R.id.img_third_qq, R.id.img_third_wechat, R.id.tv_login,
            R.id.tv_sign_up, R.id.fab_previous, R.id.tv_forget})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.img_third_qq:
                Toast.makeText(getActivity(), "qq", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_third_wechat:
                Toast.makeText(getActivity(), "wechat", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_login:
                if(name == null){
                    name = etInput.getText().toString();
                    if("".equals(name)){
                        name = null;
                        Toast.makeText(getActivity(), R.string.you_have_to_input_account_name,
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showInputPassword();
                }else{
                    String pw = etPassword.getText().toString();
                    if("".equals(pw)){
                        Toast.makeText(getActivity(), R.string.you_have_to_input_password,
                                Toast.LENGTH_SHORT).show();
                    }
                    presenter.login(name, pw);
                }
                break;
            case R.id.tv_sign_up:
                start(new SignUpFragment());
                break;
            case R.id.fab_previous:
                showInputAccount();
                break;
            case R.id.tv_forget:
                Toast.makeText(getActivity(), "forget", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @SuppressLint("RestrictedApi")
    private void showInputAccount() {
        name = null;
        tvLogin.setText(R.string.next);
        gpAccount.setVisibility(View.VISIBLE);
        gpPassword.setVisibility(View.GONE);
        fabPrevious.setVisibility(View.GONE);
        tvSignUp.setVisibility(View.VISIBLE);
        imgAccount.setImageResource(R.drawable.img_account_circle_default);
        tvName.setText(R.string.account_name);
    }

    @SuppressLint("RestrictedApi")
    private void showInputPassword() {
        tvLogin.setText(R.string.login);
        gpAccount.setVisibility(View.GONE);
        gpPassword.setVisibility(View.VISIBLE);
        fabPrevious.setVisibility(View.VISIBLE);
        tvSignUp.setVisibility(View.GONE);
        imgAccount.setImageBitmap(NamePictureCreator.generateNameDrawable(name));
        tvName.setText(name);
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initView() {
        tvLogin.setText(R.string.next);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void setNightMode(boolean night) {

    }

    @Override
    public boolean getNightMode() {
        return false;
    }

    @Override
    public boolean onBackPressedSupport() {
        if(name != null){
            showInputAccount();
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void loginSucceed() {
        getActivity().finish();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(getActivity(), R.string.login_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void networkError() {
        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
    }
}
