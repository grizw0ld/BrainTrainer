package com.example.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    CountDownTimer cdt;
    TextView questionTextView;
    TextView scoreTextView;
    ArrayList<Button> answerButtons = new ArrayList<Button>();
    Button startButton;
    Button startAgainButton;
    TextView finalScoreTextView;


    long timeRemaining;
    int answer;
    int questionsAsked;
    int answeredCorrect;

    String pad;

    public void setVisibility(boolean start){
        if(start){
            startAgainButton.setVisibility(View.INVISIBLE);
            finalScoreTextView.setVisibility(View.INVISIBLE);
            startButton.setVisibility(View.INVISIBLE);
            timerTextView.setVisibility(View.VISIBLE);
            questionTextView.setVisibility(View.VISIBLE);
            scoreTextView.setVisibility(View.VISIBLE);
            answerButtons.get(0).setVisibility(View.VISIBLE);
            answerButtons.get(1).setVisibility(View.VISIBLE);
            answerButtons.get(2).setVisibility(View.VISIBLE);
            answerButtons.get(3).setVisibility(View.VISIBLE);
        }else{
            finalScoreTextView.setText("Final Score: " + String.valueOf(answeredCorrect) + "/" + String.valueOf(questionsAsked));
            finalScoreTextView.setVisibility(View.VISIBLE);
            startAgainButton.setVisibility(View.VISIBLE);
            timerTextView.setVisibility(View.INVISIBLE);
            questionTextView.setVisibility(View.INVISIBLE);
            scoreTextView.setVisibility(View.INVISIBLE);
            answerButtons.get(0).setVisibility(View.INVISIBLE);
            answerButtons.get(1).setVisibility(View.INVISIBLE);
            answerButtons.get(2).setVisibility(View.INVISIBLE);
            answerButtons.get(3).setVisibility(View.INVISIBLE);
            //scoreTextView.animate().rotation(3600).scaleX(2).scaleY(2).translationY(-3).setDuration(2000);
        }
    }


    public void startTimer(){
        cdt = new CountDownTimer(30000,1000){
            public void onTick(long  l){
                timeRemaining = l;
                if(l/1000 < 10){
                    pad = "0";
                }else{
                    pad="";
                }
                timerTextView.setText(pad + String.valueOf(l/1000) + "s");
            }
            public void onFinish(){
                setVisibility(false);

            }
        }.start();
    }

    public int randomNumberGenerator(int min, int max){
        Random rand = new Random();
        int randomNumber = rand.nextInt((max - min)+1)+min;
        return  randomNumber;
    }

    public void generateQuestion(){
        int partOne = randomNumberGenerator(1,50);
        int partTwo = randomNumberGenerator(1,50);
        answer = partOne+partTwo;
        int correctAnswerBoxAssignment = randomNumberGenerator(0,3);
        for(int i = answerButtons.size()-1;i>-1;i--){
            if(i!=correctAnswerBoxAssignment){
                while(true){
                    int x = randomNumberGenerator(1,100);
                    if(x!=answer){
                        answerButtons.get(i).setText(String.valueOf(x));
                        break;
                    }
                }

            }else{
                answerButtons.get(i).setText(String.valueOf(answer));
            }
        }
        questionTextView.setText(String.valueOf(partOne)+ " + " + String.valueOf(partTwo));
    }

    public void getAnswer(View v){
        int sel = Integer.parseInt(((Button)v).getText().toString());
        questionsAsked +=1;
        if(sel == answer){
            answeredCorrect+=1;
            Toast.makeText(this,"Correct Answer",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,":( Sorry Incorrect",Toast.LENGTH_SHORT).show();
        }
        scoreTextView.setText(String.valueOf(answeredCorrect) + "/" + String.valueOf(questionsAsked));
        generateQuestion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        questionTextView = findViewById(R.id.questionTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        answerButtons.add((Button)findViewById(R.id.tlButton));
        answerButtons.add((Button)findViewById(R.id.trButton));
        answerButtons.add((Button)findViewById(R.id.blButton));
        answerButtons.add((Button)findViewById(R.id.brButton));
        startButton = findViewById(R.id.startButton);
        startAgainButton = findViewById(R.id.startAgainButton);
        finalScoreTextView = findViewById(R.id.finalScoreTextView);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(true);
                startTimer();
                generateQuestion();
            }
        });

        startAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionsAsked = 0;
                answeredCorrect = 0;
                scoreTextView.setText(String.valueOf(answeredCorrect) + "/" + String.valueOf(questionsAsked));
                setVisibility(true);
                startTimer();
                generateQuestion();
            }
        });


    }
}
