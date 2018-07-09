package com.alan.myfitnesspaldemo.mvc.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alan.myfitnesspaldemo.R;
import com.alan.myfitnesspaldemo.api.ApiService;
import com.alan.myfitnesspaldemo.api.model.Doc;
import com.alan.myfitnesspaldemo.api.model.Multimedium;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private List<Doc> docList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClickClick(String url);
    }

    public ArticleAdapter(List<Doc> docList) {
        this.docList = docList;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_article,
                parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Doc article = docList.get(position);
        holder.title.setText(article.getHeadline().getMain());
        ArrayList<Multimedium> multimedia = (ArrayList<Multimedium>) article.getMultimedia();
        String thumbUrl = "";
        for (Multimedium m : multimedia) {
            if (m.getType().equals("image") && m.getSubtype().equals("thumbnail")) {
                thumbUrl = ApiService.API_IMAGE_BASE_URL + m.getUrl();
                break;
            }
        }
        if (!thumbUrl.isEmpty()) {
            Picasso.get().load(thumbUrl).into(holder.articleThumb);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClickClick(article.getWebUrl());
                    Log.e("weburl", article.getWebUrl());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return docList.size();
    }

    public void clearAll() {
        docList.clear();
        notifyItemRangeRemoved(0, getItemCount());
    }

    public void appendArticles(List<Doc> articles) {
        int oldSize = articles.size();
        docList.addAll(articles);
        notifyItemRangeInserted(oldSize, articles.size() - oldSize);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView articleThumb;
        TextView title;

        ViewHolder(View view) {
            super(view);
            articleThumb = view.findViewById(R.id.article_thumb);
            title = view.findViewById(R.id.article_title);
        }
    }
}
