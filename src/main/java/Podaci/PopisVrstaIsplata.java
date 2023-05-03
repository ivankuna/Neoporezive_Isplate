package Podaci;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
@XmlRootElement
public class PopisVrstaIsplata {
    private List<VrstaIsplate> vrstaIsplate;
    public List<VrstaIsplate> getVrstaIsplate() {
        return vrstaIsplate;
    }
    public void setVrstaIsplate(List<VrstaIsplate> vrstaIsplate) {
        this.vrstaIsplate = vrstaIsplate;
    }
}
