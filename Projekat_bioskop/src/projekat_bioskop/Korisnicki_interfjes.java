package projekat_bioskop;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.parser.ParseException;

public class Korisnicki_interfjes {
    Scanner unos = new Scanner(System.in);
    String imeDatotekeKorisnika = "korisnici.json";
    String imeDatotekeAdministratora = "administratori.json";
    String imeDatotekeFilmova = "filmovi.json";
    String imeDatotekeProjekcija = "projekcije.json";
    static Admin admin = new Admin();
    Korisnik korisnik;
    ArrayList<Korisnik> korisnici;
    ArrayList<Admin> administratori;

    public Korisnicki_interfjes() {
        korisnici  = new ArrayList<Korisnik>();
        administratori = new ArrayList<Admin>();
    }
    
    public void pocetni_meni(){
        
        int rezultatUnosa = 0;
        do{
            System.out.println("----- Bioskom ----- \n");
            System.out.println("Pritisnite 1. da se ulogujete kao korisnik \n");
            System.out.println("Pritisnite 2. da se ulogujete kao administrator \n");
            System.out.println("Ukoliko nemate nalog pritisnite broj 3. \n");
             try {
                String unosKorisnika = unos.nextLine().trim();
                if (unosKorisnika.isEmpty()) {
                    System.out.println("Unesite validan broj.");
                    continue;
                }
                rezultatUnosa = Integer.parseInt(unosKorisnika);
                if (rezultatUnosa == 1) {
                   UlogujSeKaoKorisnik();
                    
                } else if (rezultatUnosa == 2) {
                   UlogujSeKaoAdministrator();
                    
                } else if (rezultatUnosa == 3) {
                   NapraviNalog();
                    
                } else {
                    System.out.println("Pogresno unesen broj. Pokusajte ponovo.");
                }
            } catch (Exception ex) {
                 System.err.println(ex.getMessage());
            }
        } while (rezultatUnosa > 3 || rezultatUnosa < 1);
    }
    
      public  void UlogujSeKaoKorisnik() throws IOException{
        boolean postojiKorisnik = false;
        String korisnickoIme = "", lozinka = "";
        do{
        System.out.println("====== Ulogujte se ======");
        
        System.out.println("Unesite korisničko ime");
            korisnickoIme = unos.nextLine();
        System.out.println("Unesite lozinku");
            lozinka = unos.nextLine();
            
            postojiKorisnik = true;
            if(postojiKorisnik == true){
                
            }
            
        }while(!proveraKorisnika(korisnickoIme, lozinka));
      
    }
      public boolean proveraKorisnika(String userName, String password) throws IOException{
       
       korisnici =  Korisnik.procitajKorisnike(imeDatotekeKorisnika);
       
       for(var korisnik : korisnici){
           if(userName.trim().equals(korisnik.username) && password.trim().equals(korisnik._password)){
               return true;
           }
       }
       return false;
     }
   
    public void UlogujSeKaoAdministrator() throws IOException, FileNotFoundException, ParseException {
    String adminIme = "", lozinka = "";

    do {
        System.out.println("====== Ulogujte se ======");
        
        System.out.println("Unesite korisničko ime");
        adminIme = unos.nextLine();
        System.out.println("Unesite lozinku");
        lozinka = unos.nextLine();
        
        if (proveraAdministratora(adminIme, lozinka)) {
            FormaAdministratora();
        } else {
            System.out.println("Neispravno korisničko ime ili lozinka. Pokušajte ponovo.");
        }
        
    } while (!proveraAdministratora(adminIme, lozinka));
}
    public boolean proveraAdministratora(String userName, String password) throws IOException, FileNotFoundException, ParseException {
   administratori = Admin.procitajAdmine(imeDatotekeAdministratora);

    for (var admin : administratori) {
        if (userName.trim().equals(admin.username) && password.trim().equals(admin._password)) {
            return true;
        }
    }
    return false;
}
     public void NapraviNalog(){
       
        String ime, prezime, username, lozinka, brojTelefona, email;
        
        
        System.out.println("====== Napravite nalog ====== ");
        System.out.println("Unesite vase ime");
        ime = unos.nextLine();
        System.out.println("Unesite vase prezime");
        prezime = unos.nextLine();
        System.out.println("Unesite vase korisnico ime");
        username = unos.nextLine();
        System.out.println("Unesite broj telefona");
        username = unos.nextLine();
        System.out.println("Unesite vasu email adresu  ime");
        username = unos.nextLine();
        System.out.println("Uneste vasu sifru");
        lozinka = unos.nextLine();
        
        if(proveriSifru(lozinka) && daLiSuPrazna(ime, prezime, username, lozinka)){
       // korisnici.add(new Korisnik(ime, prezime, username, lozinka));
        Korisnik.upisiKorisnike(korisnici, imeDatotekeKorisnika);
        char opcija; 
            System.out.println("Pritisnite 'm' za povratak na pocetni meni ili 'x' za izlazak");
            
            opcija = unos.next().charAt(0);

           
            if (opcija == 'm' || opcija == 'M') {
              
                pocetni_meni();
            } else if (opcija == 'x' || opcija == 'X') {
                
                System.out.println("Hvala što koristite naš program. Do viđenja!");
                System.exit(0);
            } else {
               
                System.out.println("Nepoznata opcija. Molimo vas, unesite 'm' za povratak na početni meni ili 'x' za izlazak.");
            } 
        } else {
        System.out.println("Nalog nije uspešno kreiran. Molimo vas, pokušajte ponovo.");
    }
       
    }
    public boolean proveriSifru(String password){
        if(password.isEmpty())return false;
        
        if(password.length()<8) return false;
        for(int i = 0; i< password.length(); i++){
        if( !Character.isLetterOrDigit(password.charAt(i)) ){
            System.out.println("Sifra mora sadrzati bar jedan broj i slovo");
            return false;}
        }
       boolean hasUpperCase = false;

        for (int i = 0; i < password.length(); i++) 
        if (Character.isUpperCase(password.charAt(i))) {
          hasUpperCase = true;
         break; 
    }


        if (!hasUpperCase) {
            System.out.println("Sifra mora imati bar jedno veliko slovo");
            return false;
        }
        
        return true ;
        }
    
    
    
    public boolean proveriImeiPrezime(String ime, String prezime ){
        
        
        
        return true;
    }
    
    
    
    
    
    
    public boolean daLiSuPrazna(String ime, String prezime, String username, String lozinka ){
         if(ime.isEmpty() || prezime.isEmpty() || username.isEmpty() || lozinka.isEmpty()){
             System.out.println("Popunite sva polja");
             return false;
         }
        return true;
    }
     public void FormaAdministratora(){
       
    int izbor = 0; 

    System.out.println("======== Administrator ========");

    do {
        System.out.println("Dodajte novi film (1)");
        System.out.println("Dodajte novu projekciju filma (2)");
        System.out.println("Obrisite film (3)");
        System.out.println("Obrisite projekciju (4)");
        System.out.print("Izaberite opciju: ");
        
        try {
            String unosKorisnika = unos.nextLine().trim();
            if (unosKorisnika.isEmpty()) {
                System.out.println("Unesite validan broj.");
                continue;
            }
            izbor = Integer.parseInt(unosKorisnika);

            if (izbor < 1 || izbor > 4) {
                System.out.println("Unesite broj između 1 i 4.");
                continue;
            }

            switch (izbor) {
                case 1:
                    admin.DodajFilm();
                    break;
                case 2:
                    admin.dodajProjekciju();
                    break;
                case 3:
                    admin.ObrisiFilm();
                    break;
                case 4:
                    admin.obrisiProjekciju();
                    break;
                default:
                    System.out.println("Nepoznata opcija. Pokušajte ponovo.");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Unesite validan broj.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    } while (izbor < 1 || izbor > 4);
}

    
}
