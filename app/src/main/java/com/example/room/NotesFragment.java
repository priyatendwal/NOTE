package com.example.room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Executor;

import static android.app.Activity.RESULT_OK;;


public class NotesFragment extends Fragment {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private NoteViewModel noteViewModel;
    GoogleSignInClient mGoogleSignInClient;
    Note note;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_notes_frament, container, false);

        FloatingActionButton buttonAddNote = view.findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNoteFragment addNoteFragment = new AddNoteFragment(noteViewModel);
//                Bundle bundle = new Bundle();
//                bundle.putInt("EXTRA_ID", Integer.parseInt("note.getId()"));
//                bundle.putString("EXTRA_TITLE","note.getTitle()");
//                bundle.putString("EXTRA_DESCRIPTION", "note.getDescription()");
//                addNoteFragment.setArguments(bundle);

                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.signin, addNoteFragment);
                fragmentTransaction.commit();

//                Intent intent = new Intent(getActivity(), AddEditNoteActivity.class);
//                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(getActivity(), new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.submitList(notes);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {

//                addNoteFragment.setArguments(getActivity().getIntent().getExtras());
                UpdateFragment fragment = new UpdateFragment(note.getId(), note.getTitle(), note.getDescription(), noteViewModel);
//                Bundle bundle = new Bundle();
//                bundle.putInt("EXTRA_ID",note.getId());
//                bundle.putString("EXTRA_TITLE",note.getTitle());
//                bundle.putString("EXTRA_DESCRIPTION", note.getDescription());
//                addNoteFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().add(
                        android.R.id.content, fragment).commit();
//                FragmentManager fragmentManager;
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.signin, addNoteFragment);
//                fragmentTransaction.commit();

//                Intent intent = new Intent(getActivity(), AddEditNoteActivity.class);
//                intent.(AddEditNoteActivity.EXTRA_ID, note.getId());
//                intent.putExputExtratra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
//                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
//                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
//            String title = data.getStringExtra(AddNoteFragment.EXTRA_TITLE);
//            String description = data.getStringExtra(AddNoteFragment.EXTRA_DESCRIPTION);
//            Note note = new Note(title, description);
//            noteViewModel.insert(note);
//            Toast.makeText(getActivity(), "Note saved", Toast.LENGTH_SHORT).show();
//        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
//            int id = data.getIntExtra(AddNoteFragment.EXTRA_ID, -1);
//            if (id == -1) {
//                Toast.makeText(getActivity(), "Note can't be updated", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            String title = data.getStringExtra(AddNoteFragment.EXTRA_TITLE);
//            String description = data.getStringExtra(AddNoteFragment.EXTRA_DESCRIPTION);
//            note = new Note(title, description);
//            note.setId(id);
//            noteViewModel.update(note);
//            Toast.makeText(getActivity(), "Note updated", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getActivity(), "Note not saved", Toast.LENGTH_SHORT).show();
//        }
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        MenuInflater menuInflater = getActivity().getMenuInflater();
//        menuInflater.inflate(R.menu.main_menu, menu);
//        return true;
//    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(getActivity(), "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            case  R.id.signout:
                signOut();
                Toast.makeText(getActivity(), "Signout", Toast.LENGTH_SHORT).show();
                SharedPreference sharedPreference = new SharedPreference();
                sharedPreference.saveISLogged_IN(getActivity(), false);
                 requireActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
    private void signOut() {
        mGoogleSignInClient.signOut();
//                .addOnCompleteListener((Executor) this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        SharedPreference sharedPreference = new SharedPreference();
//                        sharedPreference.saveISLogged_IN(getActivity(), false);
//
//                    }
//                });
    }
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

}