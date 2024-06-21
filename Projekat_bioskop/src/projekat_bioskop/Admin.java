
package projekat_bioskop;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Admin extends Osoba {
     Scanner unos = new Scanner(System.in);
     String imeDatotekeFilmova = "filmovi.json";
     String imeDatotekeProjekcija = "projekcije.json";
     String imeDatotekeKorisnika = "korisnici.json";
     Projekcija projekcije = new Projekcija();
     Korisnicki_interfjes interfejs;
     ArrayList<Film> filmovi;
    private static  int id_counter = 0;
    private int id;

    public Admin() {
        filmovi = new ArrayList<>();
    }

    public Admin(String _ime, String _prezime, String username, String _password) {
        super(_ime, _prezime, username, _password);
        id_counter++;
        this.id = id_counter;
    }

    public String getIme() {
        return _ime;
    }

    public String getPrezime() {
        return _prezime;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return _password;
    }

    public void setIme(String _ime) {
        this._ime = _ime;
    }

    public void setPrezime(String _prezime) {
        this._prezime = _prezime;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String _password) {
        this._password = _password;
    }
    public static ArrayList<Admin> procitajAdmine(String imeDatotekeAdministratora) throws FileNotFoundException, IOException, ParseException{
      ArrayList<Admin>administratori  = new ArrayList();   
         try {
            JSONArray jsonAdministratori = (JSONArray) new JSONParser().parse(new FileReader(imeDatotekeAdministratora)); 
            for (Object obj : jsonAdministratori) {
                JSONObject jsonObject = (JSONObject) obj;
                String ime = (String) jsonObject.get("Ime");
                String prezime = (String) jsonObject.get("Prezime");
                String korisnickoIme = (String) jsonObject.get("Korisnicko ime");
                String lozinka = (String) jsonObject.get("Lozinka");
                administratori.add(new Admin(ime, prezime, korisnickoIme, lozinka));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka nije pronađena!\n" + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Greška prilikom parsiranja!\n" + e.getMessage());
        }
        
        return administratori;
    }
     public static void upisiAdmine(ArrayList<Admin> administratori, String imeDatotekeAdministratora){
          JSONArray jsonAdministraotr = new JSONArray();
          PrintWriter pw = null;
          
          
          for(var admin : administratori){
              JSONObject obj = new JSONObject();
              obj.put("Ime", admin._ime);
              obj.put("Prezime", admin._ime);
              obj.put("Korisnicko_ime", admin._ime);
              obj.put("Lozinka", admin._ime);
            //  obj.put("ID", admin._id);
              jsonAdministraotr.add(obj);
          }
          try{
          
          pw = new PrintWriter(imeDatotekeAdministratora);
          pw.println(jsonAdministraotr.toJSONString());
            
          }catch(FileNotFoundException ex){
              System.out.println("Greska prilikom upisa!\n" + ex.getMessage());
          }
          finally{ if(pw != null)pw.close();}
      }
     
    public void DodajFilm(){
            String naslovFilma;
            String imeRezisera;
            String zanrFilma;
            int trajanje;
            JSONArray jsonFilmovi = new JSONArray();
            
            
      do {
        System.out.println("Unesite naslov filma:");
        naslovFilma = unos.nextLine();

        System.out.println("Unesite rezisera filma:");
        imeRezisera = unos.nextLine();

        System.out.println("Unesite zanr filma:");
        zanrFilma = unos.nextLine();

        System.out.println("Unesite trajanje filma u minutima:");
        trajanje = Integer.parseInt(unos.nextLine());

       if (!validirajFilm(naslovFilma) || !validirajFilm(imeRezisera) || !validirajFilm(zanrFilma)) {
           System.out.println("Pogrešan unos. Molimo ponovite unos.");
        }

   } while (!validirajFilm(naslovFilma) || !validirajFilm(imeRezisera) || !validirajFilm(zanrFilma) );
      
      filmovi.add(new Film(naslovFilma,imeRezisera,zanrFilma,trajanje));
       PrintWriter pw = null;
      for(Film f : filmovi){
          JSONObject obj = new JSONObject(); 
          obj.put("ID", f.getID());
          obj.put("Naslov filma", naslovFilma);
          obj.put("Ime rezisera", imeRezisera);
          obj.put("Tip zanra", zanrFilma);
          obj.put("Trajanje filma", trajanje);
            
          jsonFilmovi.add(obj);
      }
         try {
           
            FileWriter fw = new FileWriter(imeDatotekeFilmova, true);
            pw = new PrintWriter(fw);
            pw.write(jsonFilmovi.toJSONString()); 

        } catch (FileNotFoundException ex) {
            System.out.println("Greska prilikom upisa!\n" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Greska prilikom otvaranja datoteke!\n" + ex.getMessage());
        } finally {
            if (pw != null)
                pw.close();
        }
      
    }
        
private boolean validirajFilm(String tekst){
    
    if(tekst.isBlank()){
        return false;
    }
    for(char c : tekst.toCharArray()){
        if(Character.isDigit(c)){
            return false;
        }
    }

 return true;
}

private boolean validirajFilm2(int trajanjeFilma){
 try {
        if (trajanjeFilma <= 0) {
            System.out.println("Neispravan unos za trajanje filma.");
            return false;
        }
    } catch (NumberFormatException e) {
        System.err.println(e.getMessage());
        return false;
    }

    return true;
}
    public void dodajProjekciju() throws IOException{
    Film trazeniFilm = null;
    String datumUnos = "";
      
    LocalDateTime dateTime = LocalDateTime.now();
    
    System.out.println("Unesite naslov filma");
    String naslovFilma = unos.nextLine();
    int sala = 0;
    int brojMesta = 0;
    filmovi = Film.procitajFilmove(imeDatotekeFilmova);
    for (Film f : filmovi) {
     System.out.println(f.getIme_Filma());
 }
    ArrayList<Projekcija> projekcije = new ArrayList();

  
    boolean filmPronadjen = false;
 
    for (Film f : filmovi) {
        if (naslovFilma.trim().equals(f.getIme_Filma())) {
            trazeniFilm = f;
            filmPronadjen = true;
            break;
        }
    }

    if (!filmPronadjen) {
        System.out.println("Nije pronađen film sa tim imenom.");
        return;
    }

    while (true) {
            try {
                System.out.println("Unesite datum (YYYY-MM-DD): ");
               datumUnos = unos.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                break; 
            } catch (DateTimeParseException e) {
                System.err.println("Greška pri parsiranju datuma. Molimo unesite datum u formatu YYYY-MM-DD.");
            }
        }

        
        while (true) {
            try {
                System.out.println("Unesite broj sale:");
                sala = Integer.parseInt(unos.nextLine());
                 System.out.println("Unesite broj slobonih mesta za film:");
                brojMesta = Integer.parseInt(unos.nextLine());
                break; 
            } catch (NumberFormatException e) {
                System.err.println("Greška pri unosu broja sale. Molimo unesite broj sale kao ceo broj.");
            }
        }

    projekcije.add(new Projekcija(trazeniFilm.getIme_Filma(), dateTime, sala, brojMesta));
    JSONArray jsonProjekcije = new JSONArray();
    JSONObject obj = new JSONObject();
    obj.put("Naslov filma", trazeniFilm.getIme_Filma());
    obj.put("Broj sale", sala);
    obj.put("Broj mesta", brojMesta);
    obj.put("Vreme projekcije", dateTime.toString());
    jsonProjekcije.add(obj);

   
    try (FileWriter fw = new FileWriter(imeDatotekeProjekcija, true);
         PrintWriter pw = new PrintWriter(fw)) {
        pw.println(jsonProjekcije.toJSONString());
    } catch (IOException ex) {
        System.out.println("Greška prilikom upisa!\n" + ex.getMessage());
    }

    System.out.println("Projekcija filma " + trazeniFilm.getIme_Filma()+ " je dodata u sali " + sala + " u " + datumUnos);
    
    System.out.println("Pritisnite 'n' za povratak nazad ili 'x' za izlazak iz aplikacije");
            
            char opcija = unos.next().charAt(0);
           
            if (opcija == 'n' || opcija == 'N') {
                
            } else if (opcija == 'x' || opcija == 'X') {
                
                System.out.println("Hvala što koristite naš program. Do viđenja!");
                System.exit(0);
            } else {
               
                System.out.println("Nepoznata opcija. Molimo vas, unesite 'm' za povratak na početni meni ili 'x' za izlazak.");
            } 
    }
    public void upisFilmova(){
         JSONArray jsonFilmovi = new JSONArray();
              PrintWriter pw = null;
              
              for(var f : filmovi){
                 JSONObject obj = new JSONObject();
                 obj.put("Naslov filma", f.getIme_Filma());
                obj.put("Ime rezisera", f.getIme_rezisera());
                obj.put("Tip zanra", f.getZanr());
                obj.put("Trajanje filma", f.getTrajanje());
               
                jsonFilmovi.add(obj);
              }
        try{
               pw = new PrintWriter(imeDatotekeFilmova);
               pw.println(jsonFilmovi.toJSONString());
          
          
         }catch(FileNotFoundException ex){
              System.out.println("Greska prilikom upisa!\n" + ex.getMessage());
          }
          finally{ if(pw != null)pw.close();}
    }
    public void ObrisiFilm() throws IOException{
        String naslovFilma = ""; 
        Film pronFilm = new Film();
        ArrayList<Film> f = Film.procitajFilmove(imeDatotekeFilmova);
        boolean filmPronadjen = false;
        System.out.println("======= Obrisi film =======");
        for(var p : f){
            System.out.println("Naslov filma: " + p.getIme_Filma() +" Ime rezisera: " + p.getIme_rezisera() + " zanr filma: " + p.getZanr() + " trajanje filma: " + p.getTrajanje() );
        }
        System.out.println("Unesite naziv filma koji zelite da obrisete");
        naslovFilma = unos.nextLine();
        
      
        
       for (int i = 0; i < f.size(); i++) {
        if (f.get(i).getIme_Filma().equals(naslovFilma)) {
            f.remove(i--);
            filmPronadjen = true;
            upisFilmova();  
            System.out.println("Film sa naslovom '" + naslovFilma + "' je uspešno obrisan.");
            break;
        }
    }
        

    if (!filmPronadjen) {
        System.out.println("Ne postoji film sa tim naslovom!");
    }
        
        char opcija; 
            System.out.println("Pritisnite 'n' za povratak nazad ili 'x' za izlazak iz aplikacije");
            
            opcija = unos.next().charAt(0);

           
            if (opcija == 'n' || opcija == 'N') {
              interfejs.FormaAdministratora();
               
            } else if (opcija == 'x' || opcija == 'X') {
                
                System.out.println("Hvala što koristite naš program. Do viđenja!");
                System.exit(0);
            } else {
               
                System.out.println("Nepoznata opcija. Molimo vas, unesite 'm' za povratak na početni meni ili 'x' za izlazak.");
            } 
    }
    public void obrisiProjekciju() throws IOException, FileNotFoundException, ParseException{
     String naslovProjekcije = " ";
        int brSale = 0;
        boolean pronadjenaProjekcija = false;
          ArrayList<Projekcija> pro = Projekcija.procitajProjekcije(imeDatotekeProjekcija);
        
        System.out.println("====== Brisanje projekcije ======");
        System.out.println("SVE PROJEKCIJE FILMOVA");
        for(var p :pro){
            System.out.println("Naslov filma: " + p.NaslovFilma() + " " + " sala; " + p.getSala() + " vreme projekcije " + p.getDatumVreme());
        }
        System.out.println("Unesite naslov filma projekcije");
        naslovProjekcije = unos.nextLine();
        System.out.println("Unesite broj sale u kojoj se prikazuje film");
        brSale = unos.nextInt();
      
     
        for (int i = 0; i < pro.size(); i++) {
            if(pro.get(i).NaslovFilma().trim().equals(naslovProjekcije) && pro.get(i).getSala() == brSale){
                pro.remove(i--);
                pronadjenaProjekcija = true;
                break;
            }
        }
           if(!pronadjenaProjekcija){System.out.println("Nije pronadjena projekcija "); return;}
           if (pronadjenaProjekcija) {
            JSONArray jsonProjekcije = new JSONArray();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            for (Projekcija p : pro) {
                JSONObject obj = new JSONObject();
                obj.put("Naslov filma", p.NaslovFilma());
                obj.put("Vreme projekcije", p.getDatumVreme().format(formatter));
                obj.put("Broj sale", p.getSala());
                jsonProjekcije.add(obj);
            }

            try (FileWriter file = new FileWriter(imeDatotekeProjekcija, true)) {
                file.write(jsonProjekcije.toJSONString());
                file.flush();
            }
            System.out.println("Projekcija je uspešno obrisana.");
        
        }
    }
    public  ArrayList<Korisnik> VratiKorisnike() throws FileNotFoundException, IOException, ParseException{
     JSONArray JsonKorisnici = (JSONArray) new JSONParser().parse(new FileReader(imeDatotekeKorisnika));
     ArrayList<Korisnik> korisnici = new ArrayList();
     
     for(Object o : JsonKorisnici){
         JSONObject obj = (JSONObject) o;
         String ime = obj.get("Ime").toString();
         String prezime = obj.get("Prezime").toString();
         String email = obj.get("Email").toString();
         String telefon = obj.get("Telefon").toString();
         String lozinka = obj.get("Lozinka").toString();
         String korisnicko_ime = obj.get("Korisnicko ime").toString();
       
         korisnici.add(new Korisnik(telefon, email,ime, prezime,korisnicko_ime, lozinka));
       
     }
     return korisnici;
    }
    public void PrikaziDostupneKorisnike() throws FileNotFoundException, IOException, ParseException{
     ArrayList<Korisnik> korisnici = VratiKorisnike();
     for(var k : korisnici){
         System.out.println(k._ime + " "+ k._prezime + " " + k.username + " " + k._password + " " + k.getEmail() + " " + k.getBr_mobilnogTel());
        }
        
     }

    
    public void DodajKorisnike() throws IOException, FileNotFoundException, ParseException{
        System.out.println("====== DODAJTE NOVOg KORISNIKA =======");
        System.out.println("Trenutni korisnici:\n");
        PrikaziDostupneKorisnike();
        System.out.println("Unesite ime:");
        System.out.println("Unesite prezime");
        System.out.println("Unesite korisnicko ime");
        System.out.println("Unesite broj telefona");
        System.out.println("Unesite email nalog");
        System.out.println("Unesite lozinku");
        
        
        
    }
    public void ObrisiteKorisnika()throws IOException, FileNotFoundException, ParseException {
        String korisnicko_ime = " ";
        ArrayList<Korisnik> korisnici = VratiKorisnike();
        System.out.println("====== OBRISITE KORISNIKE=======");
        System.out.println("Trenutni korisnici:\n");
        PrikaziDostupneKorisnike();
        System.out.println("Unesite korisnicko ime korisnika kojeg hocete da obrisete");
        korisnicko_ime = unos.nextLine().trim();
           
        boolean korisnikObrisan = false;
        for(int i = 0; i < korisnici.size(); i++){
            if(korisnicko_ime.equals(korisnici.get(i).username)){
             korisnici.remove (i--);
             korisnikObrisan = true;
                break;
            }
        }
        if (korisnikObrisan) {
        System.out.println("Korisnik je uspešno obrisan.");
          PrikaziDostupneKorisnike();
        } else {
        System.out.println("Korisnik sa unetim korisničkim imenom ne postoji.");
        }
        
        
    }
    // razmislicu
    public void azurirajProjekciju() throws IOException, FileNotFoundException, ParseException{
        System.out.println("======== AŽURIRAJTE PROJEKCIJU =======");
        projekcije.prikaziDostupneProjekcije();
    }
}
