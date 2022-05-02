package quietboy.android.sampleproject.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import quietboy.android.sampleproject.R;
import quietboy.android.sampleproject.objs.ResultContent;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private ResultContent[] currentItemSet;
    private Activity act;

    public ItemAdapter(Activity ct, ResultContent[] resultContents){
        this.currentItemSet = resultContents;
        this.act = ct;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_movies, viewGroup, false);


        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        ResultContent currentItem = currentItemSet[position];

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        if(currentItem.trackName != null){

            viewHolder.getTrackTitle().setText(currentItem.trackName);
            viewHolder.getTrackGenre().setText(currentItem.kind);
            if(!currentItem.trackPriceIsNull()){
                viewHolder.getTrackPrice().setText(currentItem.trackPrice.toString() + "$");
            }
            else{
                viewHolder.getTrackPrice().setText("0$");
            }
            Picasso.get().load(currentItem.artworkUrl60).into(viewHolder.getImgResource());
        }
        else{
            viewHolder.getTrackTitle().setText(currentItem.collectionCensoredName);
            viewHolder.getTrackGenre().setText(currentItem.kind);
            if(!currentItem.collectionPriceIsNull()){
                viewHolder.getTrackPrice().setText(currentItem.collectionPrice.toString()+"$");
            }
            else{
                viewHolder.getTrackPrice().setText("0$");
            }
            Picasso.get().load(currentItemSet[position].artworkUrl60).into(viewHolder.getImgResource());
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return currentItemSet.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView trackTitle;
        private final TextView trackPrice;
        private final TextView trackGenre;

        private final ImageView imgResource;

        public ViewHolder(View view) {

            super(view);
            // Define click listener for the ViewHolder's View
            trackTitle = (TextView) view.findViewById(R.id.tvTrackTitle);
            trackPrice = (TextView) view.findViewById(R.id.tvTrackPrice);
            trackGenre = (TextView) view.findViewById(R.id.tvTrackGenre);

            imgResource = (ImageView) view.findViewById(R.id.imgImageTrack);

        }

        public TextView getTrackTitle() {
            return trackTitle;
        }

        public TextView getTrackPrice(){
            return trackPrice;
        }

        public TextView getTrackGenre(){
            return trackGenre;
        }

        public ImageView getImgResource(){
            return imgResource;
        }
    }
}
