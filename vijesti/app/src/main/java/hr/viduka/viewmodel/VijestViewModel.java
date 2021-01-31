package hr.viduka.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import hr.viduka.data.Baza;
import hr.viduka.data.DAO;
import hr.viduka.model.Vijest;

public class VijestViewModel extends AndroidViewModel {
    DAO dao;
    private Vijest vijest;

    public VijestViewModel(Application application) {
        super(application);
        dao = Baza.getBaza(application.getApplicationContext()).DAO();
    }
    public LiveData<List<Vijest>> getVijesti() {
        return dao.getVijesti();
    }
    public void setVijest(Vijest vijest) {
        this.vijest = vijest;
    }
    public Vijest getVijest() {
        return vijest;
    }
    public void dodajVijest() {
        dao.dodajVijest(vijest);
    }
    public void promjeniVijest() {
        dao.promjeniVijest(vijest);
    }
    public void obrisiVijest() {
        dao.obrisiVijest(vijest);
    }
}
