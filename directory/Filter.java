package directory;

import java.util.ArrayList;
import messages.Message;

public abstract class Filter {
    protected FpaMessageLoader fpaMessageLoader;
    protected String filterCriteria;
    
    public Filter(FpaMessageLoader fpaMessageLoader, String filterCriteria){
        this.fpaMessageLoader = fpaMessageLoader;
        this.filterCriteria = filterCriteria;
    }
    
    public abstract ArrayList<Message> filterMessages(String path);
    
    public abstract ArrayList<Message> filterMessages(ArrayList<Message> list);
}