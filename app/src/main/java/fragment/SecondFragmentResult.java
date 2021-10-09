package fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.database.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import util.FirstLevelExpandedAdapter;
import util.SecondLevelAdapter;
import util.UserValue;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragmentResult#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragmentResult extends Fragment {
    Map<String,Map<String,Map<String,UserValue>>> allValues;
    Map<String,UserValue> valuesForOneMouth;
    Map<String,Map<String,UserValue>> valuesForOneYear;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context context;
    private ExpandableListView listView;

    private  ArrayList<Map<String, String>> groupData;
    private ArrayList<ArrayList<Map<String, String>>> childData;
    private String[] groupFrom;
    private int[] groupTo;
    private String childFrom[];
    private int childTo[];

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private void initValue(){
        String[] groups = new String[] {"2020", "2021", "2022"};
        Map<String, String> m;
        groupFrom = new String[] {"groupName"};
        groupTo = new int[] {R.id.rowFirstText};
        childFrom = new String[] {"childName"};
        childTo= new int[] {R.id.rowSecondText};
        groupData = new ArrayList<Map<String, String>>();
        for (String s:groups){
            m=new TreeMap<String,String>();
            m.put("groupName",s);
            groupData.add(m);
        }

        String[] groupsMouth = new String[] {"maj", "june", "jule"};
        String[] groupsMouth2 = new String[] {"september", "october"};
        String[] groupsMouth3 = new String[] {"december"};

        childData= new ArrayList<ArrayList<Map<String,String>>>();

        ArrayList<Map<String,String>> childDataItem1 = new ArrayList<Map<String, String>>();
        for(String s:groupsMouth){
            m=new TreeMap<>();
            m.put("childName",s);
            childDataItem1.add(m);
        }
        childData.add(childDataItem1);
        ArrayList<Map<String,String>> childDataItem2 = new ArrayList<Map<String, String>>();
        for(String s:groupsMouth2){
            m=new TreeMap<>();
            m.put("childName",s);
            childDataItem2.add(m);
        }
        childData.add(childDataItem2);
        ArrayList<Map<String,String>> childDataItem3 = new ArrayList<Map<String, String>>();
        for(String s:groupsMouth3){
            m=new TreeMap<>();
            m.put("childName",s);
            childDataItem3.add(m);
        }
        childData.add(childDataItem3);


        allValues= new TreeMap<>();
        valuesForOneYear= new TreeMap<>();
        valuesForOneMouth=new TreeMap<>();
        valuesForOneMouth.put("1",new UserValue("1:00"
                ,"3:00"
                ,"0:00"
                ,"ночь"
                ,"12"
                ,"2"
                ,"24"));
        valuesForOneMouth.put("8",new UserValue("11:00"
                ,"13:00"
                ,"0:00"
                ,"ночь"
                ,"12"
                ,"2"
                ,"24"));
        valuesForOneMouth.put("12",new UserValue("21:00"
                ,"23:00"
                ,"0:00"
                ,"ночь"
                ,"12"
                ,"2"
                ,"24"));
        valuesForOneYear.put("maj",valuesForOneMouth);
        valuesForOneMouth.clear();

        valuesForOneMouth.put("4",new UserValue("1:30"
                ,"3:30"
                ,"0:00"
                ,"ночь"
                ,"12"
                ,"2"
                ,"24"));
        valuesForOneMouth.put("13",new UserValue("11:30"
                ,"13:30"
                ,"0:00"
                ,"ночь"
                ,"12"
                ,"2"
                ,"24"));
        valuesForOneMouth.put("23",new UserValue("21:30"
                ,"23:30"
                ,"0:00"
                ,"ночь"
                ,"12"
                ,"2"
                ,"24"));
        valuesForOneMouth.put("30",new UserValue("21:30"
                ,"23:30"
                ,"0:00"
                ,"ночь"
                ,"12"
                ,"2"
                ,"24"));

        valuesForOneYear.put("june",valuesForOneMouth);
        valuesForOneMouth.clear();

        valuesForOneMouth.put("2",new UserValue("1:33"
                ,"3:33"
                ,"0:00"
                ,"ночь"
                ,"12"
                ,"2"
                ,"24"));
        valuesForOneMouth.put("11",new UserValue("11:33"
                ,"13:33"
                ,"0:00"
                ,"ночь"
                ,"12"
                ,"2"
                ,"24"));
        valuesForOneYear.put("jule",valuesForOneMouth);
        valuesForOneMouth.clear();
        allValues.put("2020",valuesForOneYear);
        valuesForOneYear.clear();

        valuesForOneMouth.put("19",new UserValue("1:00"
                ,"3:00"
                ,"0:00"
                ,"ночь"
                ,"12"
                ,"2"
                ,"24"));
        valuesForOneMouth.put("22",new UserValue("11:00"
                ,"13:00"
                ,"0:00"
                ,"ночь"
                ,"12"
                ,"2"
                ,"24"));
        valuesForOneYear.put("september",valuesForOneMouth);
        valuesForOneMouth.clear();

        valuesForOneMouth.put("13",new UserValue("11:00"
                ,"13:00"
                ,"0:00"
                ,"ночь"
                ,"12"
                ,"2"
                ,"24"));
        valuesForOneYear.put("october",valuesForOneMouth);
        valuesForOneMouth.clear();
        allValues.put("2021",valuesForOneYear);
        valuesForOneYear.clear();


        valuesForOneMouth.put("31",new UserValue("1:00"
                ,"3:00"
                ,"0:00"
                ,"ночь"
                ,"12"
                ,"2"
                ,"24"));
        valuesForOneYear.put("december",valuesForOneMouth);
        valuesForOneMouth.clear();
        allValues.put("2022",valuesForOneYear);
        valuesForOneYear.clear();

    }

    public SecondFragmentResult() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragmentResult.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragmentResult newInstance(String param1, String param2) {
        SecondFragmentResult fragment = new SecondFragmentResult();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second_result, container, false);
        initValue();
        listView = view.findViewById(R.id.list_view);
        listView.setAdapter(new FirstLevelExpandedAdapter(context
                ,groupData
        ,R.layout.row_first_year
        ,groupFrom
        ,groupTo
        ,childData
        ,R.layout.row_second_month
        ,childFrom
        ,childTo));
        listView.setGroupIndicator(null);

        return view;
    }



}