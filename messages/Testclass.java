package messages;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import messages.Message;
import messages.MessageStakeholder;

/**
 *
 * @author X3phiroth
 */
public class Testclass {

    public static void main(String[] args) throws JAXBException {
        File file = new File("src/messages/examples");
        for (File each : file.listFiles()) {
            unMarshalling(each);
            System.out.println("---------------------------------------------");
        }
    }

    private static void unMarshalling(File file) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(Message.class);
        Unmarshaller um = jc.createUnmarshaller();
        Message message = (Message) um.unmarshal(file);
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
}
