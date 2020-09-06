package edu.umsl;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    public String name; //should be named A-G
    public void setName(int vertNum)
    {
        if (vertNum==0)
        {
            name="A";
        }
        else if (vertNum==1)
        {
            name="B";

        }
        else if (vertNum==2)
        {
            name="C";

        }
        else if (vertNum==3)
        {
            name="D";

        }
        else if (vertNum==4)
        {
            name="E";

        }
        else if (vertNum==5)
        {
            name="F";

        }
        else
        {
            name="G";

        }


    }
    public int dollars;
    public void setDollars(int mydollars)
    {
        dollars=mydollars;
    }
    public void give(Vertex vert)
    {
        int subtract=vert.connectedWith;
        String letters="ABCDEFG";
        vert.dollars=(vert.dollars)-subtract;
        for(int k=0; k<vert.sharedEdges.size();k++)
        {
            String find=vert.sharedEdges.get(k);
            char found=find.charAt(0);
            int finding=(letters.indexOf(found))-1;

        }
    }

    public List<String> sharedEdges = new ArrayList<String>();
    public int connectedWith;
    public void setConnectedWith(int connections)
    {
        connectedWith=connections;
    }

}
