package directory;

import java.util.ArrayList;
import messages.Message;


public class IsSubjectFilter extends Filter{

    public IsSubjectFilter(FpaMessageLoader fpaMessageLoader, String filterCriteria) {
        super(fpaMessageLoader, filterCriteria);
    }
    
    @Override
    public ArrayList<Message> filterMessages(String path){
        ArrayList<Message> liste = new ArrayList<Message>();
        for(Message object: fpaMessageLoader.getMessages(path)){
            if(object.getSubject().equals(filterCriteria)){
                liste.add(object);
            }
        }
        return liste;
    }

    @Override
    public ArrayList<Message> filterMessages(ArrayList<Message> list) {
        ArrayList<Message> liste = new ArrayList<Message>();
        for(Message object: list){
            if(object.getSubject().equals(filterCriteria)){
                liste.add(object);
            }
        }
        return liste;
    }
}
