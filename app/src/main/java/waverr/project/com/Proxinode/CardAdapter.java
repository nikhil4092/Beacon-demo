package waverr.project.com.Proxinode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nikhil on 8/11/2015.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> implements View.OnClickListener {

    List<Contact> contact;
    Context context;


    public CardAdapter(List<Contact> contact, Context context) {
        this.contact = contact;
        this.context = context;

    }




    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Contact c = contact.get(i);

        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
        viewHolder.name.setTypeface(face);
        viewHolder.name.setText(c.getName());

        Picasso.with(context)
                .load(c.getUrl())
                .resize(300,300)
                .into(viewHolder.imgThumbnail);

        viewHolder.descrip.setTypeface(face);
        viewHolder.descrip.setText(c.getDescription());
        viewHolder.Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+c.getEmail())); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "Innofest 2015 - ProxiNode");
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }

            }


        });


        viewHolder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + c.getPhone()));
                context.startActivity(callIntent);

            }

            ;
        });


        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

// Just two examples of information you can send to pre-fill out data for the
// user.  See android.provider.ContactsContract.Intents.Insert for the complete
// list.
                intent.putExtra(ContactsContract.Intents.Insert.NAME, c.getName());
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, c.getPhone());
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, c.getEmail());

// Send with it a unique request code, so when you get called back, you can
// check to make sure it is from the intent you launched (ideally should be
// some public static final so receiver can check against it)
                int PICK_CONTACT = 100;
                context.startActivity(intent);

            }

            ;
        });
    }



    @Override
    public int getItemCount() {
        return contact.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onClick(View v) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;
        public TextView name;
        public ImageView Email;
        public ImageView phone;
        public TextView descrip;
        public ImageView add;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            name = (TextView)itemView.findViewById(R.id.name);
            Email = (ImageView)itemView.findViewById(R.id.email);
            phone = (ImageView)itemView.findViewById(R.id.call);
            descrip = (TextView)itemView.findViewById(R.id.description);
            add = (ImageView)itemView.findViewById(R.id.contacts);

        }
    }
}
