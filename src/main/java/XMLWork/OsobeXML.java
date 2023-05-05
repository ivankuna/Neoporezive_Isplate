package XMLWork;

import Podaci.Osobe;
import Podaci.PopisOsoba;
import Servis.DatabaseUtils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static Servis.DatabaseConnection.getConnection;
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

        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM osobe";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {

            Osobe osoba = new Osobe();

            osoba.setId_osobe(Integer.parseInt(rs.getString("id_osobe")));
            osoba.setIme(rs.getString("ime"));
            osoba.setPrezime(rs.getString("prezime"));
            osoba.setOib(rs.getString("oib"));
            osoba.setIban(rs.getString("iban"));
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
