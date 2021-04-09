package com.example.room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class UpdateFragment extends Fragment {
    public int EXTRA_ID;
    public String EXTRA_TITLE;
      public String EXTRA_DESCRIPTION;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private NoteViewModel noteViewModel;

    public UpdateFragment(int EXTRA_ID, String EXTRA_TITLE, String EXTRA_DESCRIPTION, NoteViewModel model) {
        this.EXTRA_ID = EXTRA_ID;
        this.EXTRA_TITLE = EXTRA_TITLE;
        this.EXTRA_DESCRIPTION = EXTRA_DESCRIPTION;
        this.noteViewModel = model;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_note, container, false);

        editTextTitle = view.findViewById(R.id.edit_text_title);
        editTextDescription = view.findViewById(R.id.edit_text_description);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
        getActivity().setTitle("Edit Note");
            editTextTitle.setText(EXTRA_TITLE);
            editTextDescription.setText(EXTRA_DESCRIPTION);
        return view;
    }
    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Note note = new Note(title, description);

        note.setId(EXTRA_ID);
        noteViewModel.update(note);

        NotesFragment notesFragment = new NotesFragment();
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.signin, notesFragment);
        fragmentTransaction.commit();
        Toast.makeText(getActivity(), "Reached", Toast.LENGTH_SHORT).show();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
