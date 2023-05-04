package Podaci;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Servis.DatabaseUtils.getRowCount;
import static Servis.DatabaseUtils.readFromTable;

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

    public void setId_osobe(Integer id_osobe) {
        this.id_osobe = id_osobe;
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
}
