package COMP5216.shoppinglist;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    private List<Item> items;
    private Context context;

    public ItemAdapter(List<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Item getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder {
        TextView name;
        TextView count;
        TextView time;
        CheckBox tick;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            view = View.inflate(context, R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById(R.id.itemName);
            viewHolder.count = view.findViewById(R.id.itemCount);
            viewHolder.time = view.findViewById(R.id.itemTime);
            viewHolder.tick = view.findViewById(R.id.itemTick);
            view.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) view.getTag();

        final Item item = items.get(i);
        viewHolder.name.setText(item.getName());
        viewHolder.count.setText(String.valueOf(item.getCount()));
        viewHolder.time.setText(item.getTimeGap());
        viewHolder.tick.setChecked(item.isTicked());
        viewHolder.tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("checkbox", "Clicked " + viewHolder.tick.isChecked());
                if (viewHolder.tick.isChecked()) {
                    viewHolder.tick.setChecked(true);
                    item.setTicked(true);
                }
                else {
                    viewHolder.tick.setChecked(false);
                    item.setTicked(false);
                }
            }
        });
        return view;
    }
}
