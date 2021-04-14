package domowy.budzet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "IncomeRecyclerViewAdapt";

    private ArrayList<BalanceItem> listOfIncomes = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recview_balance_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewCategory.setText(listOfIncomes.get(position).getCategory().getName());
        if(listOfIncomes.get(position) instanceof Income){
            holder.textViewAmount.setText(String.format("%.2f", listOfIncomes.get(position).getAmount()) + " zł");
        } else {
            holder.textViewAmount.setText(String.format("-"+ "%.2f", listOfIncomes.get(position).getAmount()) + " zł");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = listOfIncomes.get(position).getLocalDateTime().format(formatter);
        holder.textViewDate.setText(date);
        if(listOfIncomes.get(position) instanceof Income){
            holder.imageView.setImageResource(R.drawable.ic_dollar_sign_green);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_dollar_sign_red);
        }
    }

    @Override
    public int getItemCount() {
        return listOfIncomes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textViewCategory, textViewAmount, textViewDate;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.recview_imageView);
            textViewCategory = itemView.findViewById(R.id.recview_textViewCategory);
            textViewAmount = itemView.findViewById(R.id.recview_textViewAmount);
            textViewDate = itemView.findViewById(R.id.recview_textViewDate);
        }
    }
    public void setListOfItems(ArrayList<BalanceItem> listOfIncomes){
        Collections.sort(listOfIncomes, (a,b)-> b.getLocalDateTime().compareTo(a.getLocalDateTime()));
        this.listOfIncomes = listOfIncomes;
        notifyDataSetChanged();
    }
}
