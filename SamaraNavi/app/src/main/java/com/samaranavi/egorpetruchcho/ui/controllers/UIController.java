package com.samaranavi.egorpetruchcho.ui.controllers;

import android.os.Bundle;

public interface UIController {
    void restoreState(Bundle savedInstanceState);

    void saveInstanceState(Bundle outState);
}
