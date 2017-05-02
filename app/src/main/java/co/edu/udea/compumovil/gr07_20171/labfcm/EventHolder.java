package co.edu.udea.compumovil.gr07_20171.labfcm;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Santiago on 29/04/2017.
 */

public class EventHolder extends RecyclerView.ViewHolder {
    private View mView;

    public EventHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setName(String name) {
        TextView field = (TextView) mView.findViewById(R.id.namelv);
        field.setText(name);
    }

    public void setDes(String des) {
        TextView field = (TextView) mView.findViewById(R.id.desclv);
        field.setText(des);
    }

    public void setDate(String date) {
        TextView field = (TextView) mView.findViewById(R.id.datelv);
        field.setText(date);
    }

    public void setMail(String mail) {
        TextView field = (TextView) mView.findViewById(R.id.userMaillv);
        field.setText(mail);
    }

    public View getmView() {
        return mView;
    }
}
