package domowy.budzet;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapterOutcome extends ArrayAdapter {

    private final Activity context;

    private final String[] categoryNameArray;
    private final Double[] balanceValueArray;

    public CustomListAdapterOutcome(Activity context, String[] categoryNameArrayParam, Double[] balanceValueArrayParam){
        super(context, R.layout.single_item_listview_report_outcome, categoryNameArrayParam);
        this.context = context;
        this.categoryNameArray = categoryNameArrayParam;
        this.balanceValueArray = balanceValueArrayParam;
    }
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.single_item_listview_report_outcome, null, true);

        ImageView single_item_dollarSign = rowView.findViewById(R.id.single_item_dollarSign);
        TextView single_item_categoryName = rowView.findViewById(R.id.single_item_categoryName);
        TextView single_item_balanceValue = rowView.findViewById(R.id.single_item_balanceValue);

        single_item_categoryName.setText(categoryNameArray[position]);
        single_item_balanceValue.setText("-" + String.valueOf(balanceValueArray[position]) + " zł");

        return rowView;
    }
}