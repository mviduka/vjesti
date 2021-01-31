package hr.viduka.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.viduka.R;
import hr.viduka.adapter.VijestAdapter;
import hr.viduka.adapter.VijestClickListener;
import hr.viduka.model.Vijest;
import hr.viduka.viewmodel.VijestViewModel;

public class ReadFragment extends Fragment {
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lista)
    ListView listView;
    private VijestViewModel model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read,
                container, false);
        ButterKnife.bind(this,view);
        model = ((MainActivity)getActivity()).getModel();
        definirajListu();
        definirajSwipe();
        osvjeziVijesti();
        return view;
    }
    @OnClick(R.id.fab)
    public void novaVijest(){
        model.setVijest(new Vijest());
        ((MainActivity)getActivity()).cud();
    }
    private void definirajSwipe() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                osvjeziVijesti();
            }
        });
    }
    private void osvjeziVijesti(){
        model.getVijesti().observe(this, new Observer<List<Vijest>>() {
            @Override
            public void onChanged(@Nullable List<Vijest> vijesti) {
                 swipeRefreshLayout.setRefreshing(false);
                ((VijestAdapter)listView.getAdapter()).setVijesti(vijesti);
                ((VijestAdapter) listView.getAdapter()).osvjeziVijesti();
            }
        });
    }
    private void definirajListu() {
        listView.setAdapter( new VijestAdapter(getActivity(), R.layout.red_liste, new VijestClickListener() {
            @Override
            public void onItemClick(Vijest vijest) {
                model.setVijest(vijest);
                ((MainActivity)getActivity()).cud();
            }
        }));
    }

}