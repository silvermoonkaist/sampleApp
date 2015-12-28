package com.sample.user.sampleapp.chat;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.sample.user.sampleapp.R;

/**
 * @author greg
 * @since 6/21/13
 *
 * This class is an example of how to use FirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
public class ChatRoomAdapter extends FirebaseListAdapter<ChatRoom> {

    public ChatRoomAdapter(Query ref, Activity activity, int layout) {
        super(ref, ChatRoom.class, layout, activity);
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param room Chat room information
     */
    @Override
    protected void populateView(View view, ChatRoom room) {
        // Map a Chat object to an entry in our listview
        String name = room.getName();
        TextView titleText = (TextView) view.findViewById(R.id.title);
        titleText.setText(name);
    }
}
