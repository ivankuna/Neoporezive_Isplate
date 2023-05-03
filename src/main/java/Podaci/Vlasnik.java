package Podaci;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;
import java.io.IOException;

import static Servis.FileUtils.readTxt;

@XmlRootElement
public class Vlasnik {
    private String oib;
    private String iban;
    private String naziv;
    private String grad;
    private String ulica;
    private String broj;
    private String mail;
    private String oznaka;

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

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getBroj() {
        return broj;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }
    public static void createXMLVlasnik() throws IOException, JAXBException {
        String[] vlasnikInfo = readTxt("dat/vlasnik.txt", 8);
        Vlasnik vlasnik = new Vlasnik();
        vlasnik.setOib(vlasnikInfo[0]);
        vlasnik.setIban(vlasnikInfo[1]);
        vlasnik.setNaziv(vlasnikInfo[2]);
        vlasnik.setGrad(vlasnikInfo[3]);
        vlasnik.setUlica(vlasnikInfo[4]);
        vlasnik.setBroj(vlasnikInfo[5]);
        vlasnik.setMail(vlasnikInfo[6]);
        vlasnik.setOznaka(vlasnikInfo[7]);

        JAXBContext jaxbContext = JAXBContext.newInstance(Vlasnik.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(vlasnik, new File("xml/vlasnik.xml"));
    }
    public static void main(String[] args) {
        try {
            createXMLVlasnik();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
