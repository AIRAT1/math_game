package de.java.MathGame;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import de.java.mymathgame.R;

import java.util.Random;

public class GameActivity extends Activity implements View.OnClickListener{

    private TextView textPartA;
    private TextView textPartB;
    private int correctAnswer;
    private Button buttonChoice1;
    private Button buttonChoice2;
    private Button buttonChoice3;
    private TextView txtScore;
    private TextView txtLevel;
    private int currentScore = 0;
    private int currentLevel = 1;
    private MediaPlayer mediaPlayer;
    private Random random;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mediaPlayer = null;

        random = new Random();
        int song = 0;
        switch (random.nextInt(4)) {
            case 0:
                song = R.raw.bit_utinie_istorii;
                break;
            case 1:
                song = R.raw.bit_what_ive_done;
                break;
            case 2:
                song = R.raw.chip_and_dale;
                break;
            case 3:
                song = R.raw.schnappi;
                break;
        }
        mediaPlayer = MediaPlayer.create(this, song);
        mediaPlayer.start();

        textPartA = (TextView)findViewById(R.id.textPartA);
        textPartB = (TextView)findViewById(R.id.textPartB);
        buttonChoice1 = (Button)findViewById(R.id.buttonChoice1);
        buttonChoice2 = (Button)findViewById(R.id.buttonChoice2);
        buttonChoice3 = (Button)findViewById(R.id.buttonChoice3);
        txtScore = (TextView)findViewById(R.id.txtScore);
        txtLevel = (TextView)findViewById(R.id.txtLevel);

        buttonChoice1.setOnClickListener(this);
        buttonChoice2.setOnClickListener(this);
        buttonChoice3.setOnClickListener(this);

        relativeLayout = (RelativeLayout)findViewById(R.id.myScreen);

        setQuestion();
    }

    @Override
    public void onClick(View v) {
        int answerGiven = 0;
        switch (v.getId()) {
            case R.id.buttonChoice1:
                answerGiven = Integer.parseInt(buttonChoice1.getText().toString());
                break;
            case R.id.buttonChoice2:
                answerGiven = Integer.parseInt(buttonChoice2.getText().toString());
                break;
            case R.id.buttonChoice3:
                answerGiven = Integer.parseInt(buttonChoice3.getText().toString());
                break;
        }
        updateScoreAndLevel(answerGiven);
        setQuestion();
    }

    public void setQuestion() {
        relativeLayout.setBackgroundColor(-random.nextInt(0xFFFFFF));
        int numberRange = currentLevel * 3;
        Random randInt = new Random();
        int partA = randInt.nextInt(numberRange);
        partA++;
        int partB = randInt.nextInt(numberRange);
        partB++;
        correctAnswer = partA + partB;
        int wrongAnswer1 = correctAnswer - 2;
        int wrongAnswer2 = correctAnswer + 2;
        textPartA.setText(partA + "");
        textPartB.setText(partB + "");
        int buttonLayout = randInt.nextInt(3);
        switch (buttonLayout) {
            case 0:
                buttonChoice1.setText(correctAnswer + "");
                buttonChoice2.setText(wrongAnswer1 + "");
                buttonChoice3.setText(wrongAnswer2 + "");
                break;
            case 1:
                buttonChoice2.setText(correctAnswer + "");
                buttonChoice3.setText(wrongAnswer1 + "");
                buttonChoice1.setText(wrongAnswer2 + "");
                break;
            case 2:
                buttonChoice3.setText(correctAnswer + "");
                buttonChoice1.setText(wrongAnswer1 + "");
                buttonChoice2.setText(wrongAnswer2 + "");
                break;
        }
    }

    public void updateScoreAndLevel(int answerGiven) {
        if (isCorrect(answerGiven)) {
            for (int i = 1; i <= currentLevel; i++) {
                currentScore += i;
            }
            currentLevel++;
        }else {
            currentScore = 0;
            currentLevel = 1;
        }
        txtScore.setText("Счёт: " + currentScore);
        txtLevel.setText("Уровень: " + currentLevel);
        if (currentScore >= 200) {
            mediaPlayer.stop();
            Intent intent = new Intent(this, FinishActivity.class);
            startActivity(intent);
        }
    }

    public boolean isCorrect(int answerGiven) {
        boolean correctTrueOrFalse;
        if (answerGiven == correctAnswer) {
//            Toast.makeText(GameActivity.this, "Правильно", Toast.LENGTH_SHORT).show();
            correctTrueOrFalse = true;
        }else {
            Toast toast = Toast.makeText(GameActivity.this, "ОШИБКА", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            correctTrueOrFalse = false;
        }
        return correctTrueOrFalse;
    }
}
