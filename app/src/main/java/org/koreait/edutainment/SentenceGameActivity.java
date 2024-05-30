package org.koreait.edutainment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SentenceGameActivity extends AppCompatActivity {
    SentenceDBHelper dbHelper;
    List<String> sentences;
    private int currentSentenceIndex = 0;
    private List<String> words;
    private RecyclerView rvWords;
    private ImageView ivSentenceImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence_game);

        dbHelper = new SentenceDBHelper(this);
        sentences = dbHelper.getSentences();

        words = Arrays.asList(sentences.get(currentSentenceIndex).split(" "));
        Collections.shuffle(words);

        rvWords = findViewById(R.id.rv_words);
        rvWords.setLayoutManager(new LinearLayoutManager(this));
        rvWords.setAdapter(new WordAdapter());

        Button btnMain = findViewById(R.id.btn_main);
        btnMain.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                Collections.swap(words, fromPosition, toPosition);
                recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // 단어 카드는 스와이프되지 않아야 합니다.
            }
        });
        itemTouchHelper.attachToRecyclerView(rvWords);

        Button btnCheck = findViewById(R.id.btn_check);
        btnCheck.setOnClickListener(v -> checkAnswer());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("문장의 개수를 선택하세요");

        final NumberPicker numberPicker = new NumberPicker(this);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(dbHelper.getNumberOfSentences());
        builder.setView(numberPicker);

        builder.setPositiveButton("확인", (dialog, which) -> {
            int numSentences = numberPicker.getValue();
            startGame(numSentences);
        });
        builder.setNegativeButton("취소", (dialog, which) -> {
            // 게임 리스트 화면으로 돌아갑니다
            Intent intent = new Intent(SentenceGameActivity.this, GameListActivity.class);
            startActivity(intent);
        });

        builder.show();

        ivSentenceImage = findViewById(R.id.sentence_image);
    }

    private void startGame(int numSentences) {
        sentences = dbHelper.getSentences(numSentences);
        currentSentenceIndex = 0;
        words = Arrays.asList(sentences.get(currentSentenceIndex).split(" "));
        Collections.shuffle(words);
        rvWords.getAdapter().notifyDataSetChanged();

        // Load the image for the current sentence
        String imageName = dbHelper.getImageNameForSentence(sentences.get(currentSentenceIndex));
        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        ivSentenceImage.setImageResource(imageResId);
    }

    private void checkAnswer() {
        String userAnswer = String.join(" ", words);

        TextView tvResult = findViewById(R.id.tv_result);
        if (userAnswer.equals(sentences.get(currentSentenceIndex))) {
            tvResult.setText("정답입니다!");

            // 다음 문장으로 넘어갑니다
            currentSentenceIndex++;
            if (currentSentenceIndex < sentences.size()) {
                // 새로운 문장을 섞어서 표시합니다
                words = Arrays.asList(sentences.get(currentSentenceIndex).split(" "));
                Collections.shuffle(words);
                rvWords.getAdapter().notifyDataSetChanged();
            } else {
                // 모든 문장을 완료했습니다
                tvResult.setText("모든 문장을 완료했습니다! 축하합니다!");

                // 게임 리스트 화면으로 돌아갑니다
                Intent intent = new Intent(this, GameListActivity.class);
                startActivity(intent);
            }
        } else {
            tvResult.setText("아쉽습니다. 다시 시도해주세요.");
        }
    }

    private class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
        @NonNull
        @Override
        public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_card, parent, false);
            return new WordViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
            String word = words.get(position);
            holder.tvWord.setText(word);
        }

        @Override
        public int getItemCount() {
            return words.size();
        }

        class WordViewHolder extends RecyclerView.ViewHolder {
            TextView tvWord;

            WordViewHolder(@NonNull View itemView) {
                super(itemView);
                tvWord = itemView.findViewById(R.id.tv_word);
            }
        }
    }
}