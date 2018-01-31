package com.example.shahzaib.braintrainerapp;


/*               ************  what i want & How to achieve that functionality ****************

    -   sub sy pehly ak hi activity pr 2 layouts banani hain pehli layout pr sirf ak hi button(GO!) show hoga.
        aur jo dosri layout hy us ko invisible rakhna hy
        jb pehly layout ka go button press ho to us layout ko invisible kr dyna hy aur 2sri layout ko visible kr
        dyna hy

    -   dosri layout pr following chezain show krni hain
            -> countdown timer
            -> equation with 4 answers (1 will be correct)
            -> user score
            -> information about answer is correct or not


    -   ab is program main following functionality add krni hy
            -> Go button press hoty hi countdown timer shuru ho jaey.

            -> user ko ak random equation show ho aur sath hi 4 answers b jis main sy 1 correct ho

            -> jb user koi answer choose kry agr vo correct yan wrong ho to user ko inform kiya jaey, aur
               agr correct ho to score aur question ko increament kiya jaey aur agr wrong hoto
               sirf quesiton ki value increament ki jaye

            -> jb timer khatam ho jaey to user ko us ka score dekhaya jaey aur 2 button show hon playAgain & Quite.





            ************** Data we need & how to get it *************
For storing Equation:       int firstOperand,secondOperand; (both operands will be random)
For displaying answers:     array for storing 4 answers  (3 will be random but 1 will be corrent)
For storing score:          int correctAnswers, totalQuestions;
*/




import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    FrameLayout goBtnLayout;
    RelativeLayout questionLayout,countdownFinishLayout;
    TextView countdownTimerTV,equationTV,infoTV,scoreTV,userScoreTV;
    Button answer1,answer2,answer3,answer4; // 3 button will store random answer & 1 will store correct answer

    // data we need is as follow:
    int firstOperand,secondOperand,correctAnswer,answeredQuestions=0,totalQuestions=0;
    int []answers = new int[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goBtnLayout = findViewById(R.id.goBtnLayout);
        questionLayout = findViewById(R.id.questionLayout);
        countdownTimerTV = findViewById(R.id.countdownTimerTV);
        countdownFinishLayout = findViewById(R.id.countdownFinishLayout);
        equationTV = findViewById(R.id.equationTV);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        infoTV = findViewById(R.id.infoTV); // to inform user answer is correct or not
        scoreTV = findViewById(R.id.scoreTV);
        userScoreTV = findViewById(R.id.userScoreTV);

    }


    // when user press GO button playthegame
    public void playGame(View view)
    { // when go button press play the game
        //      -invisible goBtnLayout layout
        //      -visible questionLayout
        //      -start the game

        goBtnLayout.setVisibility(View.INVISIBLE);
        questionLayout.setVisibility(View.VISIBLE);
        startTheGame();

    }


    public void startTheGame()
    {
        //--> count down timer ko start krna hy

        //--> user ko ak random equation show krni hy aur sath hi 4 answers b jis main sy 1 correct ho

        //--> jb user koi answer choose kry to vo correct yan wrong hoga ,
        //    user ko inform kiya jaey k answer wrong hy yan write,
        //    agr correct ho to score aur question ko increament kiya jaey aur agr wrong hoto
        //    sirf quesiton ki value increament ki jaye


        //--> jb timer khatam ho jaey to user ko us ka score dekhaya jaey aur 2 button show hon playAgain & Quite.

        startCountdown();
        generateRandomEquationAndAnswers();

    }


    public void startCountdown()
    {
    //start countdonwTimer from 30 seconds & before passing to countdowntimer convert seconds into milliseconds
        new CountDownTimer(30*1000,1000)
        {
            @Override
            public void onTick(long millisecondsLeft) {
                // 1 second is equal to 1000 milliseconds
                if((millisecondsLeft/1000)>9)
                {
                    countdownTimerTV.setText("00:"+ (millisecondsLeft/1000));
                }
                else // if seconds <=9
                {
                    countdownTimerTV.setText("00:0"+(millisecondsLeft/1000));
                }

            }

            @Override
            public void onFinish() {
                //--> jb timer khatam ho jaey to user ko us ka score dekhaya jaey aur
                //--> 2 button show hon playAgain & Quite.

                questionLayout.setVisibility(View.INVISIBLE);
                countdownFinishLayout.setVisibility(View.VISIBLE);
                userScoreTV.setText("Your Score is "+answeredQuestions+"/"+totalQuestions);
            }
        }.start();
    }


    public int generateRandomNumber(int min,int max)
    {
        return min + (int)(Math.random()*max);
    }


    public void generateRandomEquationAndAnswers()
    {
        // generate random equation and also show to the user
        firstOperand = generateRandomNumber(1,99);
        secondOperand = generateRandomNumber(1,99);
        equationTV.setText(firstOperand+"+"+secondOperand);

        // now generate 3 random answers & 1 correct answer and store them in array of 4 elements &
        // then display to the user
        for(int i=0; i<4; i++)
        {
            answers[i] = generateRandomNumber(2,198);
        }
        // now place 1 correct answer randomly in array of 4 elements
        correctAnswer = firstOperand + secondOperand;
        answers[generateRandomNumber(0,3)] = correctAnswer;

        // now its time to display answers to the user
        answer1.setText(Integer.toString(answers[0]));
        answer2.setText(Integer.toString(answers[1]));
        answer3.setText(Integer.toString(answers[2]));
        answer4.setText(Integer.toString(answers[3]));

        //we set tag to check answer
        answer1.setTag(Integer.toString(answers[0]));
        answer2.setTag(Integer.toString(answers[1]));
        answer3.setTag(Integer.toString(answers[2]));
        answer4.setTag(Integer.toString(answers[3]));
    }


    public void checkAnswer(View view)
    {
        int userAnswer =  Integer.parseInt(view.getTag().toString());

        // jb user koi answer choose kry to vo correct yan wrong hoga so do following:
        //    --> first user ko inform kiya jaey k answer correct hy yan wrong,
        //    --> second agr correct ho to score aur question ko increament kiya jaey agr wrong hoto
        //        sirf quesiton ki value increament ki jaye aur user ko us ka score display krna hy
        //    --> aur new equation generate ho jaey

        if(userAnswer == correctAnswer)
        {
            infoTV.setText("Correct!");
            answeredQuestions++;

        }
        else
        {
            infoTV.setText("Wrong!");
        }
        totalQuestions++;

        scoreTV.setText(answeredQuestions+"/"+totalQuestions);
        generateRandomEquationAndAnswers();
    }



    public void playAgain(View view)
    {   // reset all values
        // visible the question layout
        // generate new equations ans answers
        answeredQuestions = 0;
        totalQuestions = 0;
        infoTV.setText("");
        scoreTV.setText(answeredQuestions+"/"+totalQuestions);
        countdownFinishLayout.setVisibility(View.INVISIBLE);
        questionLayout.setVisibility(View.VISIBLE);
        startCountdown();
        generateRandomEquationAndAnswers();
    }


    public void quiteGame(View view)
    {
        finish();
    }




}
