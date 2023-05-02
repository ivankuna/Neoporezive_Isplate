package Podaci;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Osobe {
    public Integer id_osobe;
    public String ime;
    public String prezime;
    public String oib;
    public String iban;
    public String sifra_prebivalista;
    public String oznaka_isplate;

    public Integer getId_osobe() {
        return id_osobe;
    }

    public void setId_osobe(Integer id_osobe) {
        this.id_osobe = id_osobe;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getSifra_prebivalista() {
        return sifra_prebivalista;
    }

    public void setSifra_prebivalista(String sifra_prebivalista) {
        this.sifra_prebivalista = sifra_prebivalista;
    }

    public String getOznaka_isplate() {
        return oznaka_isplate;
    }

    public void setOznaka_isplate(String oznaka_isplate) {
        this.oznaka_isplate = oznaka_isplate;
    }
}
