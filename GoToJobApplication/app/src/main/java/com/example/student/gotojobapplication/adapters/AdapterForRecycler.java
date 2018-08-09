package com.example.student.gotojobapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.student.gotojobapplication.utils.DataProvider;
import com.example.student.gotojobapplication.R;
import com.example.student.gotojobapplication.activities.InfoActivity;
import com.example.student.gotojobapplication.models.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterForRecycler extends RecyclerView.Adapter<AdapterForRecycler.MyViewHolder> implements Filterable {

    private final List<Result> listOfModels = DataProvider.list;
    private List<Result> searchList = DataProvider.list;
    private Context context;

    public AdapterForRecycler(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_for_recycler, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(searchList.get(position).getPicture().getMedium()).into(holder.circleImageView);
        holder.textName.setText(searchList.get(position).getName().getFirst());
        holder.textDesc.setText(searchList.get(position).getNat());
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                final String searchText = String.valueOf(charSequence);
                if (searchText.isEmpty()) {
                    searchList = listOfModels;
                } else {
                    List<Result> newList = new ArrayList<>();
                    for (int i = 0; i < listOfModels.size(); i++) {
                        if (listOfModels.get(i).getName().getFirst().contains(searchText)) {
                            newList.add(listOfModels.get(i));
                        }
                    }
                    searchList = newList;
                }
                final FilterResults filterResults = new FilterResults();
                filterResults.values = searchList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                searchList = (List<Result>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView textName;
        TextView textDesc;
        ImageButton callButton;
        ImageButton emailButton;

        MyViewHolder(View itemView) {
            super(itemView);
            findViews(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, InfoActivity.class);
                    intent.putExtra(DataProvider.POSITION_KEY, getAdapterPosition());
                    context.startActivity(intent);
                }
            });
            callFunction();
            emailFunction();
        }

        private void findViews(View view) {
            circleImageView = view.findViewById(R.id.user_image);
            textName = view.findViewById(R.id.user_name);
            textDesc = view.findViewById(R.id.description);
            callButton = view.findViewById(R.id.call_button);
            emailButton = view.findViewById(R.id.email_button);
        }

        private void callFunction() {
            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String uri = "tel:" + searchList.get(getAdapterPosition()).getPhone();
                    final Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse(uri));
                    context.startActivity(callIntent);
                }
            });
        }

        private void emailFunction() {
            emailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:" + searchList.get(getAdapterPosition()).getEmail()));
                    context.startActivity(Intent.createChooser(emailIntent, "Send Mail"));
                }
            });
        }
    }
}
