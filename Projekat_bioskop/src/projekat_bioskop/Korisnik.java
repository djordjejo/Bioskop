
package projekat_bioskop;

import java.util.ArrayList;

public class Korisnik extends Osoba{
    
    private static int id_counter = 0;
    private int id;
    private String br_mobilnogTel;
    private String email;

    public void setBr_mobilnogTel(String br_mobilnogTel) {
        this.br_mobilnogTel = br_mobilnogTel;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getBr_mobilnogTel() {
        return br_mobilnogTel;
    }

    public String getEmail() {
        return email;
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

    public Korisnik(String br_mobilnogTel, String email, String _ime, String _prezime, String username, String _password) {
        super(_ime, _prezime, username, _password);
        this.br_mobilnogTel = br_mobilnogTel;
        this.email = email;
    }

    public Korisnik(String br_mobilnogTel, String email) {
        this.br_mobilnogTel = br_mobilnogTel;
        this.email = email;
    }
    public static ArrayList<Korisnik> procitajKorisnike(String imeDatotekeKorisnika){
        ArrayList<Korisnik> korisnici = new ArrayList();
        return korisnici;
    }
    public  static void upisiKorisnike(ArrayList<Korisnik> korisnici , String imeDatotekeKorisnika){
        
        
    }
  
    
}
