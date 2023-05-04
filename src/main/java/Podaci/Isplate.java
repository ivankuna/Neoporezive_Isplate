package Podaci;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static Servis.DatabaseUtils.getRowCount;
import static Servis.DatabaseUtils.readFromTable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Isplate {
    public Integer id_isplate;
    public Integer id_osobe;
    public Integer id_vrsta_isplate;
    public Date datum_isplate;
    public Date razdoblje_od;
    public Date razdoblje_do;
    public BigDecimal iznos_isplate;
    public String ime;
    public String prezime;

    public String oib;

    public String iban;
    public String sifra_prebivalista;
    public String oznaka_isplate;
    public String naziv_vrste_isplate;
    public String sifra_mjesta_rada;
    public String oznaka_osiguranika;
    public String oznaka_primitka;
    public String oznaka_neoporezivog_primitka;

    public void setId_isplate(Integer id_isplate) {
        this.id_isplate = id_isplate;
    }

    public void setId_osobe(Integer id_osobe) {
        this.id_osobe = id_osobe;
    }

    public void setId_vrsta_isplate(Integer id_vrsta_isplate) {
        this.id_vrsta_isplate = id_vrsta_isplate;
    }

    public void setDatum_isplate(Date datum_isplate) {
        this.datum_isplate = datum_isplate;
    }

    public void setRazdoblje_od(Date razdoblje_od) {
        this.razdoblje_od = razdoblje_od;
    }

    public void setRazdoblje_do(Date razdoblje_do) {
        this.razdoblje_do = razdoblje_do;
    }

    public void setIznos_isplate(BigDecimal iznos_isplate) {
        this.iznos_isplate = iznos_isplate;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setSifra_prebivalista(String sifra_prebivalista) {
        this.sifra_prebivalista = sifra_prebivalista;
    }

    public void setOznaka_isplate(String oznaka_isplate) {
        this.oznaka_isplate = oznaka_isplate;
    }

    public void setNaziv_vrste_isplate(String naziv_vrste_isplate) {
        this.naziv_vrste_isplate = naziv_vrste_isplate;
    }

    public void setSifra_mjesta_rada(String sifra_mjesta_rada) {
        this.sifra_mjesta_rada = sifra_mjesta_rada;
    }

    public void setOznaka_osiguranika(String oznaka_osiguranika) {
        this.oznaka_osiguranika = oznaka_osiguranika;
    }

    public void setOznaka_primitka(String oznaka_primitka) {
        this.oznaka_primitka = oznaka_primitka;
    }

    public void setOznaka_neoporezivog_primitka(String oznaka_neoporezivog_primitka) {
        this.oznaka_neoporezivog_primitka = oznaka_neoporezivog_primitka;
    }
}
