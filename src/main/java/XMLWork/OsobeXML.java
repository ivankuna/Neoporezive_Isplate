package XMLWork;

import Podaci.Osobe;
import Podaci.PopisOsoba;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Servis.DatabaseUtils.getRowCount;
import static Servis.DatabaseUtils.readFromTable;

public class OsobeXML {
    public static void createXMLOsobe() throws JAXBException, SQLException {
        List<Osobe> osobeList = new ArrayList<>();
        int rowCount = getRowCount("osobe");
        File file = new File("xml/osobe.xml");

        if(file.exists()) {
            file.delete();
            System.out.println("File deleted.");
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

    public static void main(String[] args) throws SQLException, JAXBException {
        createXMLOsobe();
    }
}
