package com.example.travellers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travellers.R;
import com.example.travellers.models.MyCartModel;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

      Context context;
      List<MyCartModel> cartModelList;

    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context=context;
        this.cartModelList=cartModelList;
    }


    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {

        holder.date.setText(cartModelList.get(position).getCurrentDate());
        holder.time.setText(cartModelList.get(position).getCurrentTime());
        holder.name.setText(cartModelList.get(position).getProductName());
        holder.price.setText("Rs "+cartModelList.get(position).getProductsPrice());
        holder.totalPrice.setText("Rs "+String.valueOf(cartModelList.get(position).getTotalPrice()));
        holder.totalQuantity.setText(cartModelList.get(position).getTotalQuantity());

        //total amount pass to cart activity


    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,price,date,time,totalQuantity,totalPrice;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.product_name);
            price=itemView.findViewById(R.id.product_price);
            date=itemView.findViewById(R.id.current_date);
            time=itemView.findViewById(R.id.current_time);
            totalQuantity=itemView.findViewById(R.id.total_quantity);
            totalPrice=itemView.findViewById(R.id.total_price);

        }
    }
}
