package hr.viduka.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import hr.viduka.model.Vijest;


@Dao
public interface DAO {
    @Query("select * from vijesti")
    LiveData<List<Vijest>> getVijesti();
    @Insert
    void dodajVijest(Vijest vijest);
    @Update
    void promjeniVijest(Vijest vijest);
    @Delete
    void obrisiVijest(Vijest vijest);
}
