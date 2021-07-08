package com.example.eventmanegmentsystem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanegmentsystem.CustomerAPI.CustomerModule;
import com.example.eventmanegmentsystem.R;

import java.util.ArrayList;



public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CustomerModule> studentList = new ArrayList<>();

    public CustomerListAdapter(Context context, ArrayList<CustomerModule> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_view_holder, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.studentTp.setText(studentList.get(position).getStudent_Tp());
        holder.studentName.setText(studentList.get(position).getStudent_Name());
        holder.studentEmail.setText(studentList.get(position).getStudent_Email());
        holder.studentPHoneNo.setText(studentList.get(position).getStudent_PhoneNumber());
        holder.studentMajorField.setText(studentList.get(position).getStudent_DegreeField());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView  studentTp, studentName, studentEmail, studentPHoneNo, studentMajorField ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentTp = itemView.findViewById(R.id.txtViewStudentTpLA);
            studentName = itemView.findViewById(R.id.txtViewStudentNameLA);
            studentEmail = itemView.findViewById(R.id.textStudentEmailLA);
            studentPHoneNo = itemView.findViewById(R.id.txtPhoneNumberLA);
            studentMajorField = itemView.findViewById(R.id.txtViewStudentMajorFieldLA);
        }
    }
}
