package hr.viduka.model;

import lombok.Getter;
import lombok.Setter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity(tableName = "vijesti")
public class Vijest implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String naslov;
    private String tekst;
    private String slika;
    private int rubrika;
    @TypeConverters(DateConverter.class)
    private Date datumObjave;
}
