package messages;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Just a class for testing purposes in JAXB..
 *
 * @author X3phiroth
 */
public class Testclass {

    private static Message message;

    public static void main(String[] args) throws JAXBException {
        File file = new File("src/messages/examples");
        for (File each : file.listFiles()) {
            unMarshalling(each);
            marshalling(message);
            System.out.println("---------------------------------------------");
        }
    }

    private static void unMarshalling(File file) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Message.class);
        Unmarshaller um = jc.createUnmarshaller();
        message = (Message) um.unmarshal(file);
        toConsole(message);
    }

    private static void toConsole(Message message) {
        System.out.println("ID: " + message.getId());
        System.out.println("Read Status: " + message.getReadStatus());
        System.out.println("Received at: " + message.getReceivedAt());
        for (MessageStakeholder person : message.getRecipients()) {
            System.out.println("Person:\n  Mail: " + person.getMailAddress()
                    + "\n  Name: " + person.getName());
        }
        System.out.println("Sender:\n  Mail: " + message.getSender().getMailAddress()
                + "\n  Name: " + message.getSender().getName());
        System.out.println("Subject: " + message.getSubject());
        System.out.println("Text: " + message.getText());
    }

    private static void marshalling(Message message) {
        try {
            File file = new File("src/messages/examples/" + "test" /*message.getId()*/ + ".xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Message.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(message, file);
//            jaxbMarshaller.marshal(message, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
