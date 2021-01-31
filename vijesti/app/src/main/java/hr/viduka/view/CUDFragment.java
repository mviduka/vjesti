package hr.viduka.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.viduka.R;
import hr.viduka.viewmodel.VijestViewModel;

public class CUDFragment extends Fragment {

    static final int SLIKANJE =1;
    private String putanjaSlike;
    @BindView(R.id.naslov)
    EditText naslov;
    @BindView(R.id.rubrike)
    Spinner rubrike;
    @BindView(R.id.tekst)
    EditText tekst;
    @BindView(R.id.slika)
    ImageView slika;
    @BindView(R.id.spremi)
    Button spremi;
    @BindView(R.id.uslikaj)
    Button uslikaj;
    @BindView(R.id.izmjena)
    Button izmjena;
    @BindView(R.id.brisi)
    Button brisi;
    @BindView(R.id.datum)
    TextView datum;
    VijestViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cud,container, false);
        ButterKnife.bind(this, view);
        model = ((MainActivity) getActivity()).getModel();
        spremi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novaVijest();
            }
        });
        izmjena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                izmjenaVijesti();
            }
        });
        brisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brisiVijest();
            }
        });
        uslikaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uslikaj();
            }
        });
        if (model.getVijest().getId() == 0) {
            definirajNovuVijest();
            return view;
        }
        definirajPromjenaBrisanjeVijesti();
        return view;
    }
    private void definirajPromjenaBrisanjeVijesti() {
        spremi.setVisibility(View.GONE);
        rubrike.setSelection(model.getVijest().getRubrika());
        naslov.setText(model.getVijest().getNaslov());
        tekst.setText(model.getVijest().getTekst());
        datum.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                .format(model.getVijest().getDatumObjave()));
        Picasso.get().load(model.getVijest().getSlika()).into(slika);
    }
    private void definirajNovuVijest() {
        izmjena.setVisibility(View.GONE);
        brisi.setVisibility(View.GONE);
    }
    private void novaVijest() {
        model.getVijest().setNaslov(naslov.getText().toString());
        model.getVijest().setRubrika(rubrike.getSelectedItemPosition());
        model.getVijest().setTekst(tekst.getText().toString());
        model.getVijest().setDatumObjave(new Date());
        model.dodajVijest();
        nazad();
    }
    private void izmjenaVijesti() {
        model.getVijest().setNaslov(naslov.getText().toString());
        model.getVijest().setRubrika(rubrike.getSelectedItemPosition());
        model.getVijest().setTekst(tekst.getText().toString());
        model.getVijest().setDatumObjave(new Date());
        model.promjeniVijest();
        nazad();
    }

    private void brisiVijest() {
        model.getVijest().setNaslov(naslov.getText().toString());
        model.getVijest().setRubrika(rubrike.getSelectedItemPosition());
        model.getVijest().setTekst(tekst.getText().toString());
        model.obrisiVijest();
        nazad();
    }
    @OnClick(R.id.nazad)
    public void nazad() {
        ((MainActivity) getActivity()).read();
    }
    private void uslikaj() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) == null) {
            Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
            return;
        }
            File slika = null;
            try {
                slika = kreirajDatotekuSlike();
            } catch (IOException ex) {
                Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
            return;
            }
            if (slika == null) {
                Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
                return;
            }
            Uri slikaURI = FileProvider.getUriForFile(getActivity(),"hr.viduka.provider", slika);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, slikaURI);
            startActivityForResult(takePictureIntent, SLIKANJE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SLIKANJE && resultCode == Activity.RESULT_OK) {
            model.getVijest().setSlika("file://" + putanjaSlike);
            model.promjeniVijest();
            Picasso.get().load(model.getVijest().getSlika()).into(slika);
        }
    }
    private File kreirajDatotekuSlike() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imeSlike = "vijest_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imeSlike,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        putanjaSlike = image.getAbsolutePath();
        return image;
    }
}