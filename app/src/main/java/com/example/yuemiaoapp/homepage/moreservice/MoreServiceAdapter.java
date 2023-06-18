package com.example.yuemiaoapp.homepage.moreservice;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yuemiaoapp.common.NewsArticleContentActivity;
import com.example.yuemiaoapp.R;
import com.example.yuemiaoapp.entity.Card;

import org.litepal.LitePal;

import java.util.List;

public class MoreServiceAdapter extends RecyclerView.Adapter<MoreServiceAdapter.ViewHolder> {
    List<Card> cardList = LitePal.findAll(Card.class);
    View cardView;
    Activity activity;
    public MoreServiceAdapter(Activity activity){
        this.activity = activity;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cardImage;
        TextView cardText;
        public ViewHolder(View view){
            super(view);
            cardImage = (ImageView) view.findViewById(R.id.card_image);
            cardText = (TextView) view.findViewById(R.id.card_name);
        }
    }

    @Override
    public MoreServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //将卡片的布局样式加载进来
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_item,viewGroup,false);
        cardView = view;
        return new ViewHolder(view);
    }
    //将数据与每个单元绑定上
    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int position) {
        Card card = cardList.get(position);
        int index = Integer.parseInt(card.image);
        viewHolder.cardImage.setImageResource(R.color.white);
        //viewHolder.cardImage.setAlpha(0.5f);
        viewHolder.cardText.setText(card.name);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsArticleContentActivity.actionStart(activity,card.name,card.url);
            }
        });
    }
    //获取数据列表的长度给viewPager，告诉recyclerVIew有多少个子项
    @Override
    public int getItemCount() {
        return cardList.size();
    }


}
