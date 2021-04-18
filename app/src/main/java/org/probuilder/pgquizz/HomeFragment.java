package org.probuilder.pgquizz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private ImageSlider slider;

    private RecyclerView recyclerView;
    private List<CategoryModel> list;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar toolbar=view.findViewById(R.id.homeToolBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Career Navigator");


        recyclerView=view.findViewById(R.id.rv_home);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        list=new ArrayList<>();

        list.add(new CategoryModel("Science","https://3.imimg.com/data3/VF/OU/MY-8278748/science-intermediate-book-500x500.png",0));
        list.add(new CategoryModel("Maths","https://static.kopykitab.com/image/cache/data/faculty-notes/502293-300x380.jpg",0));
        list.add(new CategoryModel("NDA","https://images-na.ssl-images-amazon.com/images/I/51BQJe1tPAL.jpg",0));
        list.add(new CategoryModel("Commerce","https://3.imimg.com/data3/BQ/TY/MY-189285/commerce-books-500x500.jpeg",0));
        list.add(new CategoryModel("Arts","https://images-na.ssl-images-amazon.com/images/I/711ORZl5CHL.jpg",0));
        list.add(new CategoryModel("English","https://i1.wp.com/boitoi.in/wp-content/uploads/2020/01/Spoken-English.png",0));


        final BookAdapter adapter=new BookAdapter(list);
        recyclerView.setAdapter(adapter);

        slider=view.findViewById(R.id.slider);
        List<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.sciencebanner));
        slideModels.add(new SlideModel(R.drawable.mathsbanner));
        slideModels.add(new SlideModel(R.drawable.commercebanner));
        slideModels.add(new SlideModel(R.drawable.artbanner));

        slider.setImageList(slideModels,true);

        return view;
    }
}