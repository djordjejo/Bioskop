package projekat_bioskop;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Projekcija {

  private static int idCounter = 0;
    private int _id;
    private String naslovFilma;
    LocalDateTime datumVreme;
    private int sala;
    private int dostupnaMesta;

    public Projekcija() {
    }

    public Projekcija(String naslovFilma, LocalDateTime datumVreme,int sala, int dostupnaMesta) {
        this._id = idCounter++;
        this.naslovFilma = naslovFilma;
        this.datumVreme = datumVreme;
        this.sala = sala;
        this.dostupnaMesta = dostupnaMesta;
    }

    // geteri i setteri 
    public String NaslovFilma() {
        return naslovFilma;
    }

    public LocalDateTime getDatumVreme() {
        return datumVreme;
    }

    public int getSala() {
        return sala;
    }

    public int getDostupnaMesta() {
        return dostupnaMesta;
    }

    public void setFilm(String naslov) {
        this.naslovFilma = naslov;
    }

    public void setDatumVreme(LocalDateTime datumVreme) {
        this.datumVreme = datumVreme;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public void setDostupnaMesta(int dostupnaMesta) {
        this.dostupnaMesta = dostupnaMesta;
    }
    
    public static ArrayList<Projekcija> procitajProjekcije(String imeDatotekeProjekcija) throws FileNotFoundException, IOException, ParseException {
       
        ArrayList<Projekcija> projekcije = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        try {
            JSONArray jsonProjekcije = (JSONArray) new JSONParser().parse(new FileReader(imeDatotekeProjekcija));
            for (Object obj : jsonProjekcije) {
                JSONObject jsonObject = (JSONObject) obj;
                
                String naslovFilma = (String) jsonObject.get("Naslov filma");
                LocalDateTime datumVreme = LocalDateTime.parse(jsonObject.get("Vreme projekcije").toString());
                int sala = ((Long) jsonObject.get("Broj sale")).intValue();
                int dostupnaMesta = Integer.parseInt(jsonObject.get("Broj mesta").toString());
                
                projekcije.add(new Projekcija( naslovFilma, datumVreme, sala, dostupnaMesta));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka nije pronađena!\n" + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Greška prilikom parsiranja!\n" + e.getMessage());
        }

        return projekcije;
    }

    public String prikaziDostupneProjekcije() throws IOException, FileNotFoundException, ParseException {
      String imeDatotekeProjekcija = "projekcije.json";
      ArrayList<Projekcija> proj = procitajProjekcije(imeDatotekeProjekcija);
      StringBuilder bilder = new StringBuilder();
      for(Projekcija p : proj){
      bilder.append(p.naslovFilma).append(" ")
      .append(p.dostupnaMesta).append(" ")
      .append(p.sala).append(" ")
      .append(p.dostupnaMesta).append(" ")
       .append(p.datumVreme).append("\n");
        }
        String rezultat = bilder.toString();
        return rezultat;
    }
}
