package cxw.com.commonapp.StatePage;

import com.kingja.loadsir.callback.Callback;

import cxw.com.commonapp.R;

public class LoadingCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.loading_state_page;
    }
}
