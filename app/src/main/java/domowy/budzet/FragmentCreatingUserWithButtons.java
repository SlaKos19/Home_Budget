package domowy.budzet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentCreatingUserWithButtons extends Fragment {
    private static final String ARG_TEXT = "argText";
    private static final String ARG_NUMBER = "argNumber";

    private FragmentListener listener;

    private String text;
    private int questionIndex;

    private TextView textViewQuestion;
    private Button btnOK, btnNO;

    public interface FragmentListener{
        void onInputButtonsSent(Boolean input, int questionIndex);
    }

    public static FragmentCreatingUserWithButtons newInstance(String text, int number){
        FragmentCreatingUserWithButtons fragment = new FragmentCreatingUserWithButtons();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        args.putInt(ARG_NUMBER, number);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creating_user_buttons, container, false);
        textViewQuestion = view.findViewById(R.id.textViewQuestion);
        btnOK = view.findViewById(R.id.btnYes);
        btnNO = view.findViewById(R.id.btnNo);


        btnNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onInputButtonsSent(false, questionIndex);
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onInputButtonsSent(true, questionIndex);
            }
        });

        if(getArguments() != null){
            text = getArguments().getString(ARG_TEXT);
            questionIndex = getArguments().getInt(ARG_NUMBER);
            textViewQuestion.setText(text);
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentListener){
            listener = (FragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement fragmentlistener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}