package br.com.terras.hero.base;

import android.content.Context;
import android.databinding.BaseObservable;

/**
 * Woop
 * Created by Gustavo Terras on 11/05/2018.
 * Copyright © 2017 Woop. All rights reserved.
 */

public abstract class BaseViewHolder extends BaseObservable {

    private Context context;

    public BaseViewHolder(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
