package directory;

import control.TableController;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import messages.Message;

public class FpaMessageLoader {
    
    public FpaMessageLoader(){};

    public ArrayList<Message> getMessages(String path) {

        ArrayList<Message> liste = new ArrayList<>();
        File file = new File(path);
        for (File each : file.listFiles()) {
            if (each != null && each.isFile()) {
                liste.add(readMessageFile(each));
            }
        }
        return liste;
    }

    private Message readMessageFile(File file) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Message.class);
            Unmarshaller um = jc.createUnmarshaller();
            return (Message) um.unmarshal(file);
        } catch (JAXBException ex) {
            Logger.getLogger(TableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
