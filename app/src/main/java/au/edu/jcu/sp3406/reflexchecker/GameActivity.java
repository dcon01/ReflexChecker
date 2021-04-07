package au.edu.jcu.sp3406.reflexchecker;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity {
    private long startTime;
    private final Random random = new Random();
    private static final int[] drawables = {
            R.drawable.baseline_flutter_dash_black_48,
            R.drawable.baseline_stars_black_48,
            R.drawable.baseline_thumb_up_off_alt_black_48
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setupDescription(R.id.task1, R.array.task1_descriptions);
        setupDescription(R.id.task2, R.array.task2_descriptions);

        for (int i = 0; i < 5; ++i){
            addCheckBoxes(R.array.drinks);
            //addImage();
            addCheckBoxes(R.array.fruits);
        }
        startTime = System.nanoTime();

    }

    private void setupDescription (int taskID, int arrayID) {
        TextView task = findViewById(taskID);
        String[] descriptions = getResources().getStringArray(arrayID);
        int i = random.nextInt(descriptions.length);
        task.setText(descriptions[i]);
    }

    private void addImage() {
        ViewGroup gameRows = findViewById(R.id.game_rows);
        getLayoutInflater().inflate(R.layout.image, gameRows);

        View lastChild = gameRows.getChildAt(gameRows.getChildCount() - 1);
        ImageView image = lastChild.findViewById(R.id.image);

        int index = random.nextInt(drawables.length);
        image.setImageDrawable(getResources().getDrawableForDensity(drawables[index], 0));
    }

    private void addCheckBoxes (int arrayID) {
        TableLayout tableLayout = findViewById(R.id.game_rows);
        getLayoutInflater().inflate(R.layout.checkboxes, tableLayout);

        View lastChild = tableLayout.getChildAt(tableLayout.getChildCount() - 1);
        TableRow tableRow = lastChild.findViewById(R.id.checkboxes);

        String[] labels = getResources().getStringArray(arrayID);
        Set<String> used = new HashSet<>();

        for (int i = 0; i < tableRow.getChildCount(); ++i) {
            CheckBox checkBox = (CheckBox) tableRow.getChildAt(i);

            String label = chooseLabel(labels, used);
            checkBox.setText(label);
            checkBox.setChecked(random.nextBoolean());
        }
    }
    private String chooseLabel(String[] labels, Set<String> used) {
        while (true) {
            int j = random.nextInt(labels.length);
            String label = labels[j];
            if (!used.contains(label)) {
                used.add(label);
                return label;
            }
        }
    }

    public void donePressed(View view) {
        view.setEnabled(false);

        long endTime = System.nanoTime();
        long time = endTime - startTime;

        double duration = time / 1e9;

        String message = String.format(Locale.getDefault(),"Duration: %.5f seconds", duration);
        TextView result = findViewById(R.id.result);
        result.setText(message);
    }
}