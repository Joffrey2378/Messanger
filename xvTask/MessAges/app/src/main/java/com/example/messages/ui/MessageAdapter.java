package com.example.messages.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.messages.MessageModel;
import com.example.messages.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MessageAdapter extends FirestoreRecyclerAdapter<MessageModel, MessageAdapter.MessageHolder> {

    MessageAdapter(@NonNull FirestoreRecyclerOptions<MessageModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageHolder holder, int position, @NonNull MessageModel model) {
        holder.name.setText(model.getName());
        holder.message.setText(model.getMessage());
        holder.location.setText(model.getLocation());
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_message,
                viewGroup,
                false);
        return new MessageHolder(view);
    }

    String getLocationFieldFromFirebaseObject(int position) {
        return getSnapshots().getSnapshot(position).get("location").toString();
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView message;
        TextView location;

        MessageHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_text_view);
            message = itemView.findViewById(R.id.message_text_view);
            location = itemView.findViewById(R.id.location_text_view);
        }
    }
}
