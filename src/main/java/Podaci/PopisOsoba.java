package Podaci;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
@XmlRootElement
public class PopisOsoba {
    private List<Osobe> osoba;
    public List<Osobe> getOsoba() {
        return osoba;
    }
    public void setOsoba(List<Osobe> osoba) {
        this.osoba = osoba;
    }

}
