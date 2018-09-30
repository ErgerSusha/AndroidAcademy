package android_academy.erger.task6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import android_academy.erger.task6.entities.Note;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    @NonNull
    public static List<Note> notes;
    @NonNull
    private Context context;
    private final LayoutInflater inflater;

    RecyclerAdapter(@NonNull List<Note> currentNotes,
                    @NonNull Context context) {
        notes = currentNotes;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.note_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.bodyTextView.setText(note.getBody());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView titleTextView;
        final TextView bodyTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            bodyTextView = itemView.findViewById(R.id.body_text_view);
        }
    }

    public void updateList(List<Note> updatedList) {
        notes = updatedList;
        notifyDataSetChanged();
    }
}
