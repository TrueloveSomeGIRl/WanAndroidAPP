package cxw.com.commonapp.StatePage;

import com.kingja.loadsir.callback.Callback;

import cxw.com.commonapp.R;

public class NetWorkErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.no_network_state_page;
    }
}
