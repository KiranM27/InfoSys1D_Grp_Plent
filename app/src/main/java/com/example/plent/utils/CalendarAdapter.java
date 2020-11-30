package com.example.plent.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plent.R;
import com.example.plent.models.ActivityType;
import com.example.plent.models.Event;
import com.example.plent.myActivities.CalendarActivity;
import com.example.plent.myActivities.EventActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.example.plent.utils.Constants.CALENDAR_CARD_CONTEXT;
import static com.example.plent.utils.Constants.CALENDAR_CARD_CONTEXT;
import static com.example.plent.utils.Constants.PREVIOUS_ACTIVITY;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> {

    List<Event> calendarEvents;
    List<Event> calendarEventsAll;
    private Activity CalendarActivity;

    // TODO Comment below for the best parctice method for onCLick
//    private OnCalendarListener mOnCalendarListener;
    // TODO Comment below for the best parctice method for onCLick
//    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView eventTitle, time, location;
        public ImageView indicator;
        public CardView calendarCard;
        public Button calToEventButton;

        // TODO Comment below for the best parctice method for onCLick
//         public OnCalendarListener onCalendarListener;

        // TODO Comment below for the best parctice method for onCLick
//        public MyViewHolder(View view, OnCalendarListener onCalendarListener){

        public MyViewHolder(View view) {
            super(view);
            CALENDAR_CARD_CONTEXT = view.getContext();
            eventTitle = view.findViewById(R.id.calendar_title);
            time = view.findViewById(R.id.calendar_time);
            location = view.findViewById(R.id.calendar_location);
            indicator = view.findViewById(R.id.indicator);
            calendarCard = view.findViewById(R.id.calendar_card);
            calToEventButton = view.findViewById(R.id.calToEventButton);
            // TODO Comment below for the best parctice method for onCLick
//            this.onCalendarListener = onCalendarListener;

            // TODO Comment below for the best parctice method for onCLick
//            view.setOnClickListener(this);
        }

        // TODO Comment below for the best parctice method for onCLick
//        @Override
//        public void onClick(View v) {
//            onCalendarListener.onCalendarClick(getAdapterPosition());
//        }
    }

    // TODO Comment below for the best parctice method for onCLick
//    public CalendarAdapter(List<Event> calendarEvents, OnCalendarListener onCalendarListener) {
    public CalendarAdapter(List<Event> calendarEvents) {
        this.calendarEvents = calendarEvents;
        this.calendarEventsAll = new ArrayList<>(calendarEvents);
        // TODO Comment below for the best parctice method for onCLick
//        this.mOnCalendarListener = onCalendarListener;
    }

    @NonNull
    @Override
    public CalendarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View calendarView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_card, parent, false);
        // TODO Comment below for the best parctice method for onCLick
//        return new CalendarAdapter.MyViewHolder(calendarView, mOnCalendarListener);
        return new CalendarAdapter.MyViewHolder(calendarView);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.MyViewHolder holder, int position) {
        final Event calendarEvent = calendarEvents.get(position);
        holder.eventTitle.setText(calendarEvent.getTitle());
        holder.time.setText(DateTimeUtils.formatTime24H(calendarEvent.getStartTime()) + " - " + DateTimeUtils.formatTime24H(calendarEvent.getEndTime()));
        holder.location.setText(calendarEvent.getLocation());


//            public void onClick(View V){
//                Intent intent = new Intent(CALENDAR_CARD_CONTEXT, EventActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                intent.putExtra(PREVIOUS_ACTIVITY, CAL_EVENT_CLICK);
//                CALENDAR_CARD_CONTEXT.startActivity(intent);
//            }
//        });

        if (calendarEvent.getType() == ActivityType.FIFTH_ROW) {
            holder.indicator.setBackgroundColor(Color.parseColor("#EAD620"));
            holder.calendarCard.setBackgroundColor(Color.parseColor("#FFFCE3"));
        } else if (calendarEvent.getType() == ActivityType.STUDENT_LIFE) {
            holder.indicator.setBackgroundColor(Color.parseColor("#81D2AC"));
            holder.calendarCard.setBackgroundColor(Color.parseColor("#EDFFF7"));
        } else if (calendarEvent.getType() == ActivityType.INDUSTRY_TALK) {
            holder.indicator.setBackgroundColor(Color.parseColor("#81C3D2"));
            holder.calendarCard.setBackgroundColor(Color.parseColor("#EAFBFF"));
        }

//        TODO: Not the most efficient way - can be removed if the other way works
        final String CAL_EVENT_CLICK = calendarEvents.get(position).getId();

        holder.calToEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Calendar Adapter", "Button Pressed");
                Intent intent = new Intent(CALENDAR_CARD_CONTEXT, EventActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                Log.d("Calendar Adapter", "onClick " + CAL_EVENT_CLICK);
                intent.putExtra(PREVIOUS_ACTIVITY, CAL_EVENT_CLICK);
                CALENDAR_CARD_CONTEXT.startActivity(intent);
            }
        });

//        holder.calendarCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View V){
//                Intent intent = new Intent(CALENDAR_CARD_CONTEXT, EventActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                intent.putExtra(PREVIOUS_ACTIVITY, CAL_EVENT_CLICK);
//                CALENDAR_CARD_CONTEXT.startActivity(intent);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return calendarEvents.size();
    }

    // it edits the calendarEvent array which is what will be displayed in the recycleviewer.
    // calendarEventsAll what we use to remember all the user events.
    public void filterEvents(Calendar date) {
        calendarEvents.clear();
        LocalDate calendarDate = LocalDate.of(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, date.get(Calendar.DATE));

        for (Event e : calendarEventsAll) {
            if (e.getDate().isEqual(calendarDate)) {
                calendarEvents.add(e);
            }
        }
        notifyDataSetChanged();
    }


    // TODO Comment below for the best parctice method for onCLick
//    public interface OnCalendarListener {
//        void onCalendarClick(int position);
//    }

}
