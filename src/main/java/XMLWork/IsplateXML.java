package XMLWork;

import Podaci.Isplate;
import Podaci.PopisIsplata;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static Servis.DatabaseUtils.getRowCount;
import static Servis.DatabaseUtils.readFromTable;

public class IsplateXML {
    public static void createXMLIsplate() throws JAXBException, SQLException, ParseException {
        List<Isplate> isplateList = new ArrayList<>();
        int rowCount = getRowCount("isplate");
        File file = new File("xml/isplate.xml");

        if(file.exists()) {
            file.delete();
            System.out.println("File deleted.");
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

    public static void main(String[] args) throws SQLException, JAXBException, ParseException {
        createXMLIsplate();
    }
}
