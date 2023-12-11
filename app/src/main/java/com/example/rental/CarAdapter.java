package com.example.rental;

import android.content.Context;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

// ...
public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    private List<Car> cars;
    private Context context;
    private OnItemClickListener listener;

    public CarAdapter(List<Car> cars, Context context) {
        this.cars = cars;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Car car = cars.get(position);
        holder.brandModelTextView.setText(car.getBrand() + " " + car.getModel());
        holder.yearTextView.setText(String.valueOf(car.getYear())); // Mengubah int ke String
        Picasso.get().load(car.getImageUrl()).into(holder.carImageView);
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemDoubleClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView brandModelTextView;
        public TextView yearTextView;
        public ImageView carImageView;

        private GestureDetector gestureDetector;

        public ViewHolder(View itemView) {
            super(itemView);
            brandModelTextView = itemView.findViewById(R.id.brandModelTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            carImageView = itemView.findViewById(R.id.carImageView);

            // Setup GestureDetector untuk mendeteksi double-click
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemDoubleClick(position);
                        }
                    }
                    return true;
                }
            });

            // Tambahkan listener onTouch untuk mendeteksi sentuhan
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    gestureDetector.onTouchEvent(event);
                    return true;
                }
            });
        }
    }
}
