package hr.viduka.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.List;
import hr.viduka.R;
import hr.viduka.model.Vijest;

public class VijestAdapter extends ArrayAdapter<Vijest> {
    private List<Vijest> vijesti;
    private final VijestClickListener vijestClickListener;
    private final int resource;
    private final Context context;
    public VijestAdapter(@NonNull Context context, int resource, VijestClickListener vijestClickListener) {
        super(context, resource);
        this.resource = resource;
        this.context = context;
        this.vijestClickListener = vijestClickListener;
    }
    private static class ViewHolder {
        private TextView naslov;
        private TextView rubrika;
        private TextView datum;
        private ImageView slika;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Vijest vijest;
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                view = inflater.inflate(this.resource, null);
                viewHolder.naslov = view.findViewById(R.id.naslov);
                viewHolder.rubrika = view.findViewById(R.id.rubrika);
                viewHolder.datum = view.findViewById(R.id.datum);
                viewHolder.slika = view.findViewById(R.id.slika);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            vijest = getItem(position);
            if (vijest != null) {
                viewHolder.naslov.setText(vijest.getNaslov());
                viewHolder.rubrika.setText(context.getResources().getStringArray(R.array.rubrike)[vijest.getRubrika()]);
                viewHolder.datum.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                        .format(vijest.getDatumObjave()));
                Picasso.get().load(vijest.getSlika()).fit().centerCrop().into(viewHolder.slika);
            }
        Vijest finalVijest = vijest;
        view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vijestClickListener.onItemClick(finalVijest);
                }
            });
        }
        return view;
    }
    public void setVijesti(List<Vijest> vijesti) {
        this.vijesti = vijesti;
    }
    public void osvjeziVijesti() {
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return vijesti == null ? 0 : vijesti.size();
    }
    @Nullable
    @Override
    public Vijest getItem(int position) {
        return vijesti.get(position);
    }

}
