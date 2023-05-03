package Podaci;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
@XmlRootElement
public class PopisIsplata {
    private List<Isplate> isplata;
    public List<Isplate> getIsplata() { return isplata; }
    public void setIsplata(List<Isplate> isplata) { this.isplata = isplata; }
}
