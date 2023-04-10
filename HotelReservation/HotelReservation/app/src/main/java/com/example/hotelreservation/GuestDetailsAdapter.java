package com.example.hotelreservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GuestDetailsAdapter  extends RecyclerView.Adapter<GuestDetailsAdapter.ViewHolder>  {

    private LayoutInflater layoutInflater;

    private String numberOfGuests;

    GuestDetailsAdapter(Context context, String numberOfGuests) {
        this.layoutInflater = LayoutInflater.from(context);
        this.numberOfGuests = numberOfGuests;
    }

    @NonNull
    @Override
    public GuestDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.customer_info_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestDetailsAdapter.ViewHolder holder, int position) {
        holder.guestNameLabel.setText("Guest "+(position+1));
    }

    @Override
    public int getItemCount() {
        return Integer.parseInt(this.numberOfGuests);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView guestNameLabel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            guestNameLabel = itemView.findViewById(R.id.guest_name_label);

        }
    }

}
