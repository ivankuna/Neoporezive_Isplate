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

    public static void createXMLOsobe() throws JAXBException, SQLException {
        List<Osobe> osobeList = new ArrayList<>();
        int rowCount = getRowCount("osobe");
        File file = new File("xml/osobe.xml");

        if(file.exists()) {
            file.delete();
            System.out.println("File deleted.");
        } else {
            // Do nothing
        }

        for (int i = 0; i < rowCount; i++) {
            String[] osobe = readFromTable("osobe", "id_osobe", i + 1);
            Osobe osoba = new Osobe();
            assert osobe != null;
            osoba.setId_osobe(Integer.parseInt(osobe[0]));
            osoba.setIme(osobe[1]);
            osoba.setPrezime(osobe[2]);
            osoba.setOib(osobe[3]);
            osoba.setIban(osobe[4]);
            osobeList.add(osoba);
        }
        // Create a wrapper class that contains the list of Osobe instances
        PopisOsoba popisOsoba = new PopisOsoba();
        popisOsoba.setOsoba(osobeList);

        // Marshal the wrapper class to XML
        JAXBContext jaxbContext = JAXBContext.newInstance(PopisOsoba.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(popisOsoba, new File("xml/osobe.xml"));
    }

    public static void main(String[] args) {
        try {
            createXMLOsobe();
        } catch (JAXBException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
