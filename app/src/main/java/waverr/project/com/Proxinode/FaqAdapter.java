package waverr.project.com.Proxinode;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nikhil on 8/18/2015.
 */
public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> implements View.OnClickListener {

    List<Faqcred> faq;
    Context context;


    public FaqAdapter(List<Faqcred> faq, Context context) {
        this.faq = faq;
        this.context = context;

    }




    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Faqcred c = faq.get(i);

        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
        viewHolder.q.setTypeface(face, Typeface.BOLD);
        viewHolder.q.setText(c.getQ());
        viewHolder.a.setTypeface(face);
        viewHolder.a.setText(c.getA());
        viewHolder.at.setTypeface(face);
        viewHolder.qt.setTypeface(face);


    }



    @Override
    public int getItemCount() {
        return faq.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.faq_card, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onClick(View v) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView q,qt;
        public TextView a,at;



        public ViewHolder(View itemView) {
            super(itemView);

            q = (TextView)itemView.findViewById(R.id.question);
            qt = (TextView)itemView.findViewById(R.id.q);
            a = (TextView)itemView.findViewById(R.id.answer);
            at = (TextView)itemView.findViewById(R.id.a);

        }
    }
}