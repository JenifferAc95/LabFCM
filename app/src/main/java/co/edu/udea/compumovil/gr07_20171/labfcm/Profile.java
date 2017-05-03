package co.edu.udea.compumovil.gr07_20171.labfcm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    private NavDrawe nwParent;
    private View view;

    ImageView profileImage;
    TextView profileName;
    TextView profileEmail;


    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_profile,container,false);
        nwParent = (NavDrawe) getActivity();

        profileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
        profileName = (TextView) view.findViewById(R.id.tvProfileName);
        profileEmail = (TextView) view.findViewById(R.id.tvProfileEmail);

        profileName.setText(nwParent.getUsername());
        profileEmail.setText(nwParent.getEmail());
        Glide.with(this).load(nwParent.getPhotoUrl()).into(profileImage);


        return view;
    }

}
