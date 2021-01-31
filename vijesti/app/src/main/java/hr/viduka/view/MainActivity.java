package hr.viduka.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import hr.viduka.R;
import hr.viduka.viewmodel.VijestViewModel;

public class MainActivity extends AppCompatActivity {
    private VijestViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = ViewModelProviders.of(this).get(VijestViewModel.class);
        read();
    }
    public void read(){
        setFragment( new ReadFragment());
    }
    public void cud(){
        setFragment(new CUDFragment());
    }
    private void setFragment(Fragment fragment){
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }
    public VijestViewModel getModel() {
        return model;
    }
}
