package Podaci;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

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

    public Integer getId_isplate() {
        return id_isplate;
    }

    public void setId_isplate(Integer id_isplate) {
        this.id_isplate = id_isplate;
    }

    public Integer getId_osobe() {
        return id_osobe;
    }

    public void setId_osobe(Integer id_osobe) {
        this.id_osobe = id_osobe;
    }

    public Integer getId_vrsta_isplate() {
        return id_vrsta_isplate;
    }

    public void setId_vrsta_isplate(Integer id_vrsta_isplate) {
        this.id_vrsta_isplate = id_vrsta_isplate;
    }

    public Date getDatum_isplate() {
        return datum_isplate;
    }

    public void setDatum_isplate(Date datum_isplate) {
        this.datum_isplate = datum_isplate;
    }

    public Date getRazdoblje_od() {
        return razdoblje_od;
    }

    public void setRazdoblje_od(Date razdoblje_od) {
        this.razdoblje_od = razdoblje_od;
    }

    public Date getRazdoblje_do() {
        return razdoblje_do;
    }

    public void setRazdoblje_do(Date razdoblje_do) {
        this.razdoblje_do = razdoblje_do;
    }

    public BigDecimal getIznos_isplate() {
        return iznos_isplate;
    }

    public void setIznos_isplate(BigDecimal iznos_isplate) {
        this.iznos_isplate = iznos_isplate;
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

    public String getNaziv_vrste_isplate() {
        return naziv_vrste_isplate;
    }

    public void setNaziv_vrste_isplate(String naziv_vrste_isplate) {
        this.naziv_vrste_isplate = naziv_vrste_isplate;
    }

    public String getSifra_mjesta_rada() {
        return sifra_mjesta_rada;
    }

    public void setSifra_mjesta_rada(String sifra_mjesta_rada) {
        this.sifra_mjesta_rada = sifra_mjesta_rada;
    }

    public String getOznaka_osiguranika() {
        return oznaka_osiguranika;
    }

    public void setOznaka_osiguranika(String oznaka_osiguranika) {
        this.oznaka_osiguranika = oznaka_osiguranika;
    }

    public String getOznaka_primitka() {
        return oznaka_primitka;
    }

    public void setOznaka_primitka(String oznaka_primitka) {
        this.oznaka_primitka = oznaka_primitka;
    }

    public String getOznaka_neoporezivog_primitka() {
        return oznaka_neoporezivog_primitka;
    }

    public void setOznaka_neoporezivog_primitka(String oznaka_neoporezivog_primitka) {
        this.oznaka_neoporezivog_primitka = oznaka_neoporezivog_primitka;
    }
    public static void createXMLIsplate() throws JAXBException, SQLException, ParseException {
        List<Isplate> isplateList = new ArrayList<>();
        int rowCount = getRowCount("isplate");
        File file = new File("xml/isplate.xml");

        if(file.exists()) {
            file.delete();
            System.out.println("File deleted.");
        } else {
            // Do nothing
        }

        for (int i = 0; i < rowCount; i++) {
            String[] isplate = readFromTable("isplate", "id_isplate", i + 1);
            Isplate isplata = new Isplate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            assert isplata != null;
            isplata.setId_isplate(Integer.parseInt(isplate[0]));
            isplata.setId_osobe(Integer.parseInt(isplate[1]));
            isplata.setId_vrsta_isplate(Integer.parseInt(isplate[2]));
            isplata.setDatum_isplate(dateFormat.parse(isplate[3]));
            isplata.setRazdoblje_od(dateFormat.parse(isplate[4]));
            isplata.setRazdoblje_do(dateFormat.parse(isplate[5]));
            isplata.setIznos_isplate(BigDecimal.valueOf(Double.parseDouble(isplate[6])));
            isplata.setIme(isplate[7]);
            isplata.setPrezime(isplate[8]);
            isplata.setOib(isplate[9]);
            isplata.setIban(isplate[10]);
            isplata.setSifra_prebivalista(isplate[11]);
            isplata.setOznaka_isplate(isplate[12]);
            isplata.setNaziv_vrste_isplate(isplate[13]);
            isplata.setSifra_mjesta_rada(isplate[14]);
            isplata.setOznaka_osiguranika(isplate[15]);
            isplata.setOznaka_primitka(isplate[16]);
            isplata.setOznaka_neoporezivog_primitka(isplate[17]);
            isplateList.add(isplata);
        }
        // Create a wrapper class that contains the list of Osobe instances
        PopisIsplata popisIsplata = new PopisIsplata();
        popisIsplata.setIsplata(isplateList);

        // Marshal the wrapper class to XML
        JAXBContext jaxbContext = JAXBContext.newInstance(PopisIsplata.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(popisIsplata, new File("xml/isplate.xml"));
    }

    public static void main(String[] args) {
        try {
            createXMLIsplate();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
