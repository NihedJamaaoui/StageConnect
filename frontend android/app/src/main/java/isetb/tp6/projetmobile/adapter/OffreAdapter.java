package isetb.tp6.projetmobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import isetb.tp6.projetmobile.R;
import isetb.tp6.projetmobile.model.Offre;

public class OffreAdapter extends RecyclerView.Adapter<OffreViewHolder> {
    private List<Offre> list;
    private OnItemClickListener onItemClickListener;

    public OffreAdapter(List<Offre> initialList) {
        this.list = initialList;
    }

    public OffreAdapter(List<Offre> initialList, OnItemClickListener onItemClick) {
        this.list = initialList;
        this.onItemClickListener = onItemClick;
    }

    public void setData(List<Offre> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OffreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offre, parent, false);
        return new OffreViewHolder(view, list);
    }

    @Override
    public void onBindViewHolder(@NonNull OffreViewHolder holder, int position) {
        Offre offre = list.get(position);
        holder.bind(offre);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(offre);
                }
            }
        });

        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (onItemClickListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClickListener.onUpdateClick(adapterPosition);
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (onItemClickListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClickListener.onDeleteClick(adapterPosition);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void deleteItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public Offre getItem(int position) {
        return list.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(Offre offre);

        // Updated: Provide position as an argument
        default void onUpdateClick(int position) {
            // Default implementation or leave it empty
        }

        // Updated: Provide position as an argument
        default void onDeleteClick(int position) {
            // Default implementation or leave it empty
        }
        default void onCreateClick() {
            // Default implementation or leave it empty
        }
    }

}