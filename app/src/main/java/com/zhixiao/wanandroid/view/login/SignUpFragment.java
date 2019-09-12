package com.zhixiao.wanandroid.view.login;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.Editable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
import butterknife.OnTextChanged;

/**
 * @ClassName: SignUpFragment
 * @Description:
 * @Author: zhixiao
 * @CreateDate: 2019/9/12
 */
public class SignUpFragment extends AbstractBaseFragment<SignUpContract.Presenter>
        implements SignUpContract.View{

    @BindView(R.id.img_go_back)
    ImageView imgBack;
    @BindView(R.id.et_sign_up_name)
    EditText etName;
    @BindView(R.id.et_sign_up_password)
    EditText etPw;
    @BindView(R.id.et_sign_up_repassword)
    EditText etRP;
    @BindView(R.id.tv_sign_up_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_back_login)
    TextView tvBackLogin;

    @OnClick({R.id.img_go_back, R.id.tv_back_login})
    void backLogin(){
        pop();
    }

    @OnClick({R.id.tv_sign_up_confirm})
    void signUp(){
        if(etName.getText().toString().isEmpty()){
            Toast.makeText(getContext(), R.string.input_account_name, Toast.LENGTH_SHORT).show();
        }else if(etPw.getText().toString().isEmpty() || etRP.getText().toString().isEmpty()){
            Toast.makeText(getContext(), R.string.input_password, Toast.LENGTH_SHORT).show();
        }else if(!etPw.getText().toString().equals(etRP.getText().toString())){
            Toast.makeText(getContext(), R.string.inconsistent_passwords, Toast.LENGTH_SHORT).show();
        }else{
            presenter.signUp(etName.getText().toString(),
                    etPw.getText().toString(), etRP.getText().toString());
        }
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

    @Override
    public void signUpSuccessful() {
        Toast.makeText(getContext(), R.string.sign_up_successful, Toast.LENGTH_SHORT).show();
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void signUpFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
