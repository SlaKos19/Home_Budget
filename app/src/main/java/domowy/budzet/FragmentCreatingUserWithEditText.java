package domowy.budzet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentCreatingUserWithEditText extends Fragment {

    private static final String ARG_TEXT = "argText";
    private static final String ARG_NUMBER = "argNumber";

    private FragmentListener listener;

    private String text;
    private int questionIndex;

    private TextView textView;
    private EditText editText;
    private Button btnGo;

    public static FragmentCreatingUserWithEditText newInstance(String text, int number){
        FragmentCreatingUserWithEditText fragment = new FragmentCreatingUserWithEditText();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        args.putInt(ARG_NUMBER, number);
        fragment.setArguments(args);
        return fragment;
    }
    public interface FragmentListener{
        void onInputEditSent(String answer, int questionIndex);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creating_user_edittext, container, false);
        textView = view.findViewById(R.id.textViewQuestionEdit);
        editText = view.findViewById(R.id.editText);
        btnGo = view.findViewById(R.id.btnGo);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(questionIndex != 0){
                    if(Double.valueOf(editText.getText().toString()) < 0){
                        Toast.makeText(getContext(), "Musi byc wieksze od 0", Toast.LENGTH_LONG).show();
                    } else {
                        listener.onInputEditSent(editText.getText().toString(), questionIndex);
                    }
                } else {
                    listener.onInputEditSent(editText.getText().toString(), questionIndex);
                }

            }
        });
        if(getArguments() != null){
            text = getArguments().getString(ARG_TEXT);
            questionIndex = getArguments().getInt(ARG_NUMBER);
            textView.setText(text);
            if(questionIndex == 0){
                editText.setInputType(View.AUTOFILL_TYPE_TEXT);
            }
        }
        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentCreatingUserWithEditText.FragmentListener){
            listener = (FragmentCreatingUserWithEditText.FragmentListener) context;
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
