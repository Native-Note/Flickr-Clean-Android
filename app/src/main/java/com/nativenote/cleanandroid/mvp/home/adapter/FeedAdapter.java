package com.nativenote.cleanandroid.mvp.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nativenote.cleanandroid.R;
import com.nativenote.cleanandroid.models.Item;
import com.nativenote.cleanandroid.mvp.home.HomeActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private final List<Item> feedItems = new ArrayList<>(0);
    private final Context context;
    private final Picasso picasso;

    @Inject
    public FeedAdapter(HomeActivity context, Picasso picasso) {
        this.context = context;
        this.picasso = picasso;
    }

    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Item item = feedItems.get(position);

        holder.title.setVisibility(TextUtils.isEmpty(item.getTitle()) ? GONE : VISIBLE);
        holder.title.setText(TextUtils.isEmpty(item.getTitle()) ? "N/A" : item.getTitle());

        String author;
        String[] auth = item.getAuthor().split("\\(");
        if (auth.length > 1)
            author = auth[1].replace(")", "");
        else
            author = auth[0];

        holder.author.setText(author.replace("\"", ""));

        DateFormat formatFrom = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Format formatTo = new SimpleDateFormat("dd MMM, yy HH:mm:ss");
        try {
            holder.date.setText(context.getResources()
                    .getString(R.string.published, formatTo.format(formatFrom.parse(item.getPublished()))));
        } catch (ParseException e) {
            holder.date.setText(context.getResources()
                    .getString(R.string.published, item.getPublished()));
        }

        picasso.load(item.getMedia().getM().replace("_m", "_c"))
                .placeholder(R.drawable.placeholder)
                .into(holder.flickrImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.progress.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.flickr_image)
        ImageView flickrImage;

        @BindView(R.id.txt_title)
        TextView title;

        @BindView(R.id.txt_author)
        TextView author;

        @BindView(R.id.txt_date)
        TextView date;

        @BindView(R.id.progress)
        ProgressBar progress;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void swapData(Collection<Item> items) {
        feedItems.clear();
        if (items != null) {
            feedItems.addAll(items);
        }
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onClick(Item Item);
    }
}
