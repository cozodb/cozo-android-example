package org.cozodb.cozoexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import org.cozodb.CozoDb;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // In-memory database
            // CozoDb db = new CozoDb();

            // Persistent SQLite-based database
            // Must give a path writable by the app
            Context context = getApplicationContext();
            String filePath = context.getExternalFilesDir(null).toString()
                    + "/data.db";
            CozoDb db = new CozoDb("sqlite", filePath);
            // Usually you should store `db` somewhere.
            // All operations are thread-safe and synchronous.
            // For long-running queries, it is recommended to use a worker thread.

            String query =
                    "love[loving, loved] <- [['alice', 'eve'],\n" +
                            "                        ['bob', 'alice'],\n" +
                            "                        ['eve', 'alice'],\n" +
                            "                        ['eve', 'bob'],\n" +
                            "                        ['eve', 'charlie'],\n" +
                            "                        ['charlie', 'eve'],\n" +
                            "                        ['david', 'george'],\n" +
                            "                        ['george', 'george']]\n" +
                            "\n" +
                            "?[person, page_rank] <~ PageRank(love[])";

            String res = db.run(query).toString();
            ((TextView) findViewById(R.id.textField)).setText(res);

            // Close `db` when you no longer need it,
            // otherwise there will be resource leaks.
            // Deleting the variable is not enough since some resources
            // are not managed by the Android runtime.
            db.close();
        } catch (Throwable e) {
            ((TextView) findViewById(R.id.textField)).setText(e.toString());
        }
    }
}