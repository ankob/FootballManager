package com.footballmanager.admin_fragments.edit_dialogs;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.footballmanager.R;
import com.footballmanager.db.DbHelper;
import com.footballmanager.db.StadiumContract;
import com.footballmanager.db.TeamContract;
import com.footballmanager.model.MappedItem;
import com.footballmanager.model.Match;
import com.footballmanager.model.Stadium;
import com.footballmanager.model.Team;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andrew on 12-Mar-17.
 */

public class MatchEditDialog extends EntityEditDialog {

    private static Match match;

    public MatchEditDialog() {
    }

    public MatchEditDialog(Match match) {
        this.match = match;
    }

    @Override
    public String getTitle() {
        return "Edit Game";
    }

    @Override
    public boolean saveInstance(DbHelper sql) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Spinner team1Spinner, team2Spinner, stadiumSpinner;
        stadiumSpinner = (Spinner) getDialog().findViewById(R.id.stadium_spinner);
        team1Spinner = (Spinner) getDialog().findViewById(R.id.team_1_spinner);
        team2Spinner = (Spinner) getDialog().findViewById(R.id.team_2_spinner);

        EditText editTime = (EditText) getDialog().findViewById(R.id.edit_match_time);
        EditText editDate = (EditText) getDialog().findViewById(R.id.edit_match_date);
        EditText editDuration = (EditText) getDialog().findViewById(R.id.edit_match_duration);
        EditText editResult = (EditText) getDialog().findViewById(R.id.edit_result);

        Match.saveMatch(
                editDate.getText().toString() + " " + editTime.getText().toString(),
                Integer.parseInt(editDuration.getText().toString()),
                (Stadium) stadiumSpinner.getSelectedItem(),
                editResult.getText().toString(),
                (Team) team1Spinner.getSelectedItem(),
                (Team) team2Spinner.getSelectedItem(),
                dbHelper.getWritableDatabase()
        );

        return true;
    }

    @Override
    public View getDialogLayoutView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View result = inflater.inflate(R.layout.fragment_edit_match, null);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Spinner team1Spinner, team2Spinner, stadiumSpinner;
        stadiumSpinner = (Spinner) result.findViewById(R.id.stadium_spinner);
        List<MappedItem> stadiums = StadiumContract.getStadiums(db);
        stadiumSpinner.setAdapter(new ItemsAdapter(
                getContext(),
                android.R.layout.simple_list_item_1,
                stadiums
        ));

        team1Spinner = (Spinner) result.findViewById(R.id.team_1_spinner);
        team2Spinner = (Spinner) result.findViewById(R.id.team_2_spinner);
        List<MappedItem> teams1 = TeamContract.getTeams(db);
        team1Spinner.setAdapter(new ItemsAdapter(
                getContext(),
                android.R.layout.simple_list_item_1,
                teams1
        ));

        List<MappedItem> teams2 = TeamContract.getTeams(db);
        team2Spinner.setAdapter(new ItemsAdapter(
                getContext(),
                android.R.layout.simple_list_item_2,
                teams2
        ));
        EditText editTime = (EditText) result.findViewById(R.id.edit_match_time);
        EditText editDate = (EditText) result.findViewById(R.id.edit_match_date);
        EditText editDuration = (EditText) result.findViewById(R.id.edit_match_duration);

        editDate.setText(Match.dateFormat.format(match.getDatetime()));
        editTime.setText(Match.timeFormat.format(match.getDatetime()));
        editDuration.setText(Integer.toString(match.getDuration()));

        if (match.getId() != -1) {
            int selection1 = 0;
            int selection2 = 0;
            for (int i = 0; i < teams1.size(); i++) {
                if (teams1.get(i).getId() == match.getTeams().get(0).getId())
                    selection1 = i;
                if (teams2.get(i).getId() == match.getTeams().get(1).getId())
                    selection2 = i;
            }
            team1Spinner.setSelection(selection1);
            team1Spinner.setSelection(selection2);

            for (int i = 0; i < stadiums.size(); i++) {
                if (stadiums.get(i).getId() == match.getStadium().getId())
                    selection1 = i;
            }
            stadiumSpinner.setSelection(selection1);
        }

        return result;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
