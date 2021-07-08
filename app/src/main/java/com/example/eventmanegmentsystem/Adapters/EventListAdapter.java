package com.example.eventmanegmentsystem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanegmentsystem.EventAPI.EventModule;
import com.example.eventmanegmentsystem.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<EventModule> eventList = new ArrayList<>();

    public EventListAdapter(Context context, ArrayList<EventModule> eventList) {
        this.context = context;
        this.eventList = eventList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_view_holder, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventListAdapter.ViewHolder holder, int position) {
        holder.eventCategory.setText(eventList.get(position).getEventCategory());
        holder.eventDescription.setText(eventList.get(position).getEventDescription());
        holder.eventTime.setText(eventList.get(position).getEventTime());
        holder.eventDate.setText(eventList.get(position).getEventDate());
        holder.eventPriceTicket.setText(eventList.get(position).getEventPriceTicket());
        holder.eventID.setText(eventList.get(position).getEventID());
        holder.studentEmail.setText(eventList.get(position).getStudentEmail());
        holder.status.setText(eventList.get(position).getEventStatus());

        Picasso.with(this.context).load(eventList.get(position).getEventImportPicture()).fit().into(holder.eventImage);
        //Glide.with(this.context).load(eventList.get(position).getEventImportPicture()).fitCenter().into(holder.eventImage);

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView eventCategory, eventDescription, eventTime, eventDate, eventPriceTicket, eventID, studentEmail, status ;
        private ImageView eventImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventCategory = itemView.findViewById(R.id.txtViewCategoryLA);
            eventDescription = itemView.findViewById(R.id.txtViewDescriptionLA);
            eventTime = itemView.findViewById(R.id.txtViewTimeLA);
            eventDate = itemView.findViewById(R.id.txtViewDateLA);
            eventPriceTicket = itemView.findViewById(R.id.txtViewPriceLA);
            eventImage = itemView.findViewById(R.id.ImgViewImageLA);
            eventID = itemView.findViewById(R.id.txtViewEventIDLA);
            studentEmail = itemView.findViewById(R.id.txtViewEmailLA);
            status = itemView.findViewById(R.id.txtViewStatusLA);

        }
    }
}
