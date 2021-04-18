package org.probuilder.pgquizz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class CounselingActivity extends AppCompatActivity {

    private ChipNavigationBar chipNavigationBar;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counseling);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_Conatiner, new HomeFragment())
                .commit();

        initilzier();



        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id)
            {
                Fragment fragment=null;

                switch (id)
                {
                    case R.id.userHome:
                        fragment=new HomeFragment();
                        break;

                    case R.id.userFavQues:
                        fragment=new FavoriteFragment();
                        break;

                    case R.id.counseling:
                    fragment=new CounselingFragment();
                    break;

//                    case R.id.myScore:
//                        fragment=new MyScoresFragment();
//                        break;

                    case R.id.profile:
                        fragment=new ProfileFragment();
                        break;

                }

                if (fragment!=null)
                {
                    fragmentManager=getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_Conatiner,fragment).commit();
                }else {
                    Log.i("MSG","Error in creating fragment");
                }
            }
        });
    }

    private void initilzier(){
        chipNavigationBar=findViewById(R.id.navBar);
        chipNavigationBar.setItemSelected(R.id.userHome,true);
    }
}