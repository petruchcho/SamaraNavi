package com.samaranavi.egorpetruchcho.ui;

import android.support.annotation.DrawableRes;

import com.samaranavi.egorpetruchcho.samaranavi.R;

public enum LocationState {
    DISABLED(R.drawable.icon_enable_location),
    SHOW_MY_LOCATION(R.drawable.icon_my_location),
    FOLLOW_ME(R.drawable.icon_navigation);

    final
    @DrawableRes
    int icon;

    LocationState(int icon) {
        this.icon = icon;
    }

    public LocationState getNextState() {
        switch (this) {
            case DISABLED:
                return SHOW_MY_LOCATION;
            case SHOW_MY_LOCATION:
                return FOLLOW_ME;
            default:
                return DISABLED;
        }
    }
}
