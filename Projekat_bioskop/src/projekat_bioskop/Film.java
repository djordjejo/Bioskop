package projekat_bioskop;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Film {

    public Film() {
    }

    public Film(String ime_Filma, String ime_rezisera, String zanr, int trajanje) {
        id_counter++;
        this.id = id_counter;
        this.ime_Filma = ime_Filma;
        this.ime_rezisera = ime_rezisera;
        this.zanr = zanr;
        this.trajanje = trajanje;
    }

    public String getIme_Filma() {
        return ime_Filma;
    }

    public String getIme_rezisera() {
        return ime_rezisera;
    }

    public String getZanr() {
        return zanr;
    }

    public int getTrajanje() {
        return trajanje;
    }
     public int getID() {
        return id;
    }

    public void setIme_Filma(String ime_Filma) {
        this.ime_Filma = ime_Filma;
    }

    public void setIme_rezisera(String ime_rezisera) {
        this.ime_rezisera = ime_rezisera;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }
    private static int id_counter = 0;
    private int id;
    private String ime_Filma;
    private String ime_rezisera;
    private String zanr;
    private int trajanje;
    
     public static ArrayList<Film> procitajFilmove(String imeDatotekeFilmova) throws IOException{
         ArrayList<Film>filmovi = new ArrayList();
         try {
            JSONArray jsonFilmovi = (JSONArray) new JSONParser().parse(new FileReader(imeDatotekeFilmova)); 
            for (Object obj : jsonFilmovi) {
                JSONObject jsonObject = (JSONObject) obj;
                String nazivFilma = (String) jsonObject.get("Naslov filma");
                String imeRezisera = (String) jsonObject.get("Ime rezisera");
                String zanr = (String) jsonObject.get("Tip zanra");
                int trajanje = Integer.parseInt(jsonObject.get("Trajanje filma").toString());
                filmovi.add(new Film(nazivFilma, imeRezisera, zanr, trajanje));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka nije pronađena!\n" + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Greška prilikom parsiranja!\n" + e.getMessage());
        }
         return filmovi;
    }
}

