package guessinggame.coreymccown.com.guessinggame_cnm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import java.util.Random;


public class MainActivity extends ActionBarActivity implements OnClickListener {

    //Declare all of the needed variables
    private Button btnGuess;
    private Button btnAgain;
    private TextView lblGuessesRemaining;
    private TextView lblWinLoss;
    private EditText numberGuess;
    private int guessesRemaining;
    private Random rand = new Random();
    private boolean unlimitedTries;
    public static int maxNumber;
    private int win = 0;
    private int loss = 0;
    private Button btnReset;
    private int theNumber;
    private ImageView theFrown;
    public static final String PREFS_NAME = "Preferences";
    public static boolean needReset = false;

    @Override
    public void onClick(View v) {
        //Detect if the clicked button was the guess button
        if(v == btnGuess){
            //Who are we to tell them no? Do the guess!
            doGuess();
        }

        //Detect if the clicked button was the play again button
        if(v == btnAgain){
            //Play again? Make a new random number and reset the guesses.
            theNumber = rand.nextInt(maxNumber) + 1;
            guessesRemaining = (int)(Math.log(maxNumber) / Math.log(2))  + 1;
            updateGuesses();
            makeGuess();
        }

        //Detect if reset pressed
        if(v == btnReset){
            SharedPreferences prefs = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
            maxNumber = prefs.getInt("maxNumber", 100);
            theNumber = rand.nextInt(maxNumber) + 1;
            guessesRemaining = (int)(Math.log(maxNumber) / Math.log(2)) + 1;
            updateGuesses();
            makeGuess();
        }
    }

    protected void doGuess() {

        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(PREFS_NAME, 0).edit();
        //Guess it!
        boolean isNull = false;
        int thisGuess = 0;
        try {
            thisGuess = Integer.parseInt(numberGuess.getText().toString());
        } catch(NumberFormatException e){
            makeToast("You must enter a number 1-" + maxNumber + "! Don't try breaking me... :(", Toast.LENGTH_LONG);
            isNull = true;
        }
        if(!isNull)
        if((guessesRemaining > 1) || (unlimitedTries)){
            //There's enough guesses left, keep going!

            if(thisGuess == theNumber)
            {
                makeReset();
                MainActivity.needReset = true;
                if(!unlimitedTries) {
                    win++;
                    editor.putInt("wins", win);
                    editor.commit();
                }
                makeToast("You are correct! The number was " + theNumber + "!", Toast.LENGTH_LONG);
            } else {
                if(!unlimitedTries) guessesRemaining--;

                updateGuesses();

                if(thisGuess > theNumber){
                    if(!unlimitedTries)
                        makeToast("Too high - " + guessesRemaining + " guesses left...", Toast.LENGTH_SHORT);
                    else
                        makeToast("Too high - Incalculable guesses left...", Toast.LENGTH_SHORT);
                } else {
                    if(!unlimitedTries)
                        makeToast("Too low - " + guessesRemaining + " guesses left...", Toast.LENGTH_SHORT);
                    else
                        makeToast("Too low - Incalculable guesses left...", Toast.LENGTH_SHORT);
                }
            }
            numberGuess.requestFocus();
            numberGuess.selectAll();

        } else {
            //Ran out of luck, loser!
            makeReset();
            loss++;
            editor.putInt("loss", loss);
            editor.commit();
            makeToast("You lost. :( The number was " + theNumber + ".", Toast.LENGTH_LONG);
            theFrown.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(needReset) {
            SharedPreferences prefs = getApplicationContext().getSharedPreferences("Preferences", 0);
            btnReset.setVisibility(View.VISIBLE);
            lblGuessesRemaining.setVisibility(View.INVISIBLE);
            btnGuess.setVisibility(View.INVISIBLE);
            btnAgain.setVisibility(View.INVISIBLE);
            numberGuess.setVisibility(View.INVISIBLE);
            unlimitedTries = prefs.getBoolean("unlimited",false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get stored data
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("Preferences", 0);
        int storedNumber = prefs.getInt("maxNumber", 100);
        win = prefs.getInt("wins", 0);
        loss = prefs.getInt("loss", 0);
        unlimitedTries = prefs.getBoolean("unlimited",false);
        maxNumber = storedNumber;
        guessesRemaining = (int)(Math.log(maxNumber) / Math.log(2)) + 1;

        //Assign all variables their appropriate IDs
        btnGuess = (Button) findViewById(R.id.btn_Guess);
        btnAgain = (Button) findViewById(R.id.btn_Again);
        btnReset = (Button) findViewById(R.id.btn_Reload);
        numberGuess = (EditText) findViewById(R.id.txt_Guess);
        lblGuessesRemaining = (TextView) findViewById(R.id.lbl_RemGuesses);
        lblWinLoss  = (TextView) findViewById(R.id.lbl_WinLoss);
        lblWinLoss.setText("Wins: " + win + " - Losses: " + loss);
        theFrown = (ImageView) findViewById(R.id.sadFace);

        //Set the right values
        updateGuesses();
        theNumber = rand.nextInt(maxNumber) + 1;
        btnGuess.setOnClickListener( this );
        btnGuess.setVisibility(View.VISIBLE);
        btnAgain.setOnClickListener( this );
        btnAgain.setVisibility(View.INVISIBLE);
        btnReset.setOnClickListener( this );
        btnReset.setVisibility(View.INVISIBLE);
        theFrown.setVisibility(View.INVISIBLE);

        //Set focus to numberGuess
        numberGuess.setSelectAllOnFocus(true);
        numberGuess.requestFocus();

        //Make on screen keyboard appear, and stay after submitting (ACTION_DONE)
        numberGuess.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    doGuess();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            startActivity(new Intent(getApplicationContext(), Settings.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //This will update the label for the guesses remaining
    protected void updateGuesses()
    {
        if(!unlimitedTries)
            lblGuessesRemaining.setText("Guess 1-" + maxNumber + ", Guesses: " + guessesRemaining);
        else
            lblGuessesRemaining.setText("Guess 1-" + maxNumber + ", Guesses: Immeasurable");
    }

    //Toggle visibility for the reset/guess button.
    //This toggles the reset button ON and guess button OFF.
    protected void makeReset() {
        btnGuess.setVisibility(View.INVISIBLE);
        btnAgain.setVisibility(View.VISIBLE);
        lblGuessesRemaining.setVisibility(View.INVISIBLE);

        numberGuess.setVisibility(View.INVISIBLE);
    }

    //Toggle visibility for the reset/guess button.
    //This toggles the guess button ON and reset button OFF.
    protected void makeGuess() {
        lblWinLoss.setText("Wins: " + win + " - Losses: " + loss);
        btnGuess.setVisibility(View.VISIBLE);
        btnAgain.setVisibility(View.INVISIBLE);
        lblGuessesRemaining.setVisibility(View.VISIBLE);
        theFrown.setVisibility(View.INVISIBLE);
        numberGuess.setVisibility(View.VISIBLE);
        btnReset.setVisibility(View.INVISIBLE);
    }

    //Makes toast with given message and length. I'm lazy...
    protected void makeToast(String message, int length)
    {
        Toast.makeText(getApplicationContext(), message, length).show();
    }

}
