package com.mlm.hypemeter.cardstream;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Martin on 2/29/2016.
 */

public class StreamRetentionFragment extends Fragment {

    CardStreamState mState;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    public void storeCardStream(CardStreamState state) {
        mState = state;
    }

    public CardStreamState getCardStream() {
        return mState;
    }
}
