package com.example.eventmanegmentsystem.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanegmentsystem.EventAPI.EventModule;
import com.example.eventmanegmentsystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RequestedEventListAdapter extends RecyclerView.Adapter<RequestedEventListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<EventModule> eventList = new ArrayList<>();

    private FirebaseFirestore firebaseFirestore;
    public RequestedEventListAdapter(Context context, ArrayList<EventModule> eventList) {
        this.context = context;
        this.eventList = eventList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_event_view_holder, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedEventListAdapter.ViewHolder holder, int position) {
        holder.eventCategory.setText(eventList.get(position).getEventCategory());
        holder.eventDescription.setText(eventList.get(position).getEventDescription());
        holder.eventTime.setText(eventList.get(position).getEventTime());
        holder.eventDate.setText(eventList.get(position).getEventDate());
        holder.eventPriceTicket.setText(eventList.get(position).getEventPriceTicket());
        holder.eventID.setText(eventList.get(position).getEventID());
        holder.studentEmail.setText(eventList.get(position).getStudentEmail());
        holder.status.setText(eventList.get(position).getEventStatus());
        String tok= eventList.get(position).getToken();
        System.out.println(" "+ tok);
        Picasso.with(this.context).load(eventList.get(position).getEventImportPicture()).fit().into(holder.eventImage);
        //Glide.with(this.context).load(eventList.get(position).getEventImportPicture()).into(holder.eventImage);

        holder.editRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String status= "Approved";
                //update the event using the event id
                firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference record = firebaseFirestore.collection("EventsData").document(tok);
                record.update("eventStatus", status)
                        .addOnSuccessListener(new OnSuccessListener< Void >() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onSuccess(Void aVoid) {
                                holder.status.setText(status);
                                holder.editRequest.setText("Approved");
                                holder.editRequest.setBackgroundColor(R.color.Green);
                                Toast.makeText(v.getContext(), "Status has been approved", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(), "Something went wrong, check the internet connection",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {

        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView eventCategory, eventDescription, eventTime, eventDate, eventPriceTicket, eventID, studentEmail, status ;
        private ImageView eventImage;
        private Button editRequest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventCategory = itemView.findViewById(R.id.txtViewCategoryLA1);
            eventDescription = itemView.findViewById(R.id.txtViewDescriptionLA1);
            eventTime = itemView.findViewById(R.id.txtViewTimeLA1);
            eventDate = itemView.findViewById(R.id.txtViewDateLA1);
            eventPriceTicket = itemView.findViewById(R.id.txtViewPriceLA1);
            eventImage = itemView.findViewById(R.id.ImgViewImageLA1);
            eventID = itemView.findViewById(R.id.txtViewEventIDLA1);
            studentEmail = itemView.findViewById(R.id.txtViewEmailLA1);
            status = itemView.findViewById(R.id.txtViewStatusLA1);
            editRequest= itemView.findViewById(R.id.EditStatus);
        }
    }
}
