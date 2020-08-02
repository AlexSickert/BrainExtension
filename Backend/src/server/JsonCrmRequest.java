package server;

import java.util.ArrayList;

public class JsonCrmRequest {

    public String m = "";  // module
    public String session = "";
    public String action = "";  // insert or update

    public String fieldName = "";  // session

    public String fieldValue = "";
    public String rowId = "";

    public String filter_1_field = "";
    public String filter_1_value = "";
    public String filter_1_logic = "";

    public String filter_2_field = "";
    public String filter_2_value = "";
    public String filter_2_logic = "";

    public String filter_3_field = "";
    public String filter_3_value = "";
    public String filter_3_logic = "";

    public String filter_4_field = "";
    public String filter_4_value = "";
    public String filter_4_logic = "";


    public String filter_5_field = "";
    public String filter_5_value = "";
    public String filter_5_logic = "";

    public String newData = "";

    public ArrayList result = new ArrayList();

    public boolean hasError = false;
    public String errorDescription = "";

}
