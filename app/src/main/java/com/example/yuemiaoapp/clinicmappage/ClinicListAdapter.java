package com.example.yuemiaoapp.clinicmappage;


import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.example.yuemiaoapp.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * date:2021/4/28
 * author:wsm(admin)
 * funcation:
 */
public class ClinicListAdapter extends RecyclerView.Adapter<ClinicListAdapter.ViewHolder> {
    private Activity mActivity;
    private List<PoiInfo> mList;
    private double lat;
    private double lon;

    public ClinicListAdapter(Activity activity) {
        mActivity = activity;
        mList = new ArrayList<>();
    }

    public void addAll(double lat, double lon, List<PoiInfo> result) {
        this.lat = lat;
        this.lon = lon;
        if (result != null) {
            mList.addAll(result);
            // Sort mList based on the distance
            mList.sort(new Comparator<PoiInfo>() {
                @Override
                public int compare(PoiInfo poi1, PoiInfo poi2) {
                    double distance1 = BaiduMapUtils.getDistanceNumerical(lon, lat, poi1.location.longitude, poi1.location.latitude);
                    double distance2 = BaiduMapUtils.getDistanceNumerical(lon, lat, poi2.location.longitude, poi2.location.latitude);
                    return Double.compare(distance1, distance2);
                }
            });
            notifyDataSetChanged();
        }
    }

    public void clear() {
        mList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.location_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        PoiInfo: name = 五里店; uid = fe29f769d4d79f839d9d682e; address = 江北区; province = 重庆市; city = 重庆市; area = 江北区; street_id = fe29f769d4d79f839d9d682e; phoneNum = ; postCode = null; detail = 1; location = latitude: 29.584075, longitude: 106.562899; hasCaterDetails = false; isPano = false; tag = null; poiDetailInfo = PoiDetailInfo: name = null; location = null; address = null; province = null; city = null; area = null; telephone = null; uid = null; detail = 0; distance = 0; type = life; tag = 商圈; naviLocation = latitude: 29.584301120558, longitude: 106.56405986999; detailUrl = http://api.map.baidu.com/place/detail?uid=fe29f769d4d79f839d9d682e&output=html&source=placeapi_v2; price = 0.0; shopHours = ; overallRating = 4.3; tasteRating = 0.0; serviceRating = 0.0; environmentRating = 0.0; facilityRating = 0.0; hygieneRating = 0.0; technologyRating = 0.0; imageNum = 2; grouponNum = 0; discountNum = 0; commentNum = 9; favoriteNum = 0; checkinNum = 0; direction = null; distance = 0
        holder.linItem.setVisibility(TextUtils.isEmpty(mList.get(position).getAddress()) ? View.GONE : View.VISIBLE);
        if (TextUtils.isEmpty(mList.get(position).getAddress())) {
            return;
        }
        holder.mName.setText(mList.get(position).getName());
        holder.mDistance.setText(BaiduMapUtils.getDistance(lon, lat, mList.get(position).location.longitude, mList.get(position).location.latitude));
        holder.mLocation.setText(mList.get(position).getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItem(mList.get(position), position);
                }
                notifyDataSetChanged();
            }
        });

        if (position == getmPosition()) {
            holder.mSelect.setVisibility(View.VISIBLE);
        } else {
            holder.mSelect.setVisibility(View.GONE);
        }
    }

    private int mPosition;

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mName;
        private final TextView mDistance;
        private final TextView mLocation;
        private final ImageView mSelect;
        private final LinearLayout linItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.txt_name);
            mDistance = itemView.findViewById(R.id.txt_distance);
            mLocation = itemView.findViewById(R.id.txt_location);
            mSelect = itemView.findViewById(R.id.click_select);
            linItem = itemView.findViewById(R.id.lin_item);
        }
    }

    //防止隐藏item出现空白
    public void setVisibility(boolean isVisible, View itemView) {
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        if (isVisible) {
            param.height = RelativeLayout.LayoutParams.WRAP_CONTENT;// 这里注意使用自己布局的根布局类型
            param.width = RelativeLayout.LayoutParams.MATCH_PARENT;// 这里注意使用自己布局的根布局类型
            itemView.setVisibility(View.VISIBLE);
        } else {
            itemView.setVisibility(View.GONE);
            param.height = 0;
            param.width = 0;
        }
        itemView.setLayoutParams(param);
    }

    public interface OnItemClickListener {
        void onItem(PoiInfo poiInfo, int position);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}

