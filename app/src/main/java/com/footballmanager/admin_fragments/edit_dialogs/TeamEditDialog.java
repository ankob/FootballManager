package com.footballmanager.admin_fragments.edit_dialogs;

import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.footballmanager.R;
import com.footballmanager.db.DbHelper;
import com.footballmanager.db.TeamContract;
import com.footballmanager.model.Team;

/**
 * Created by Andrew on 12-Mar-17.
 */

public class TeamEditDialog extends EntityEditDialog {

    private static Team team;

    public TeamEditDialog() {
    }

    public TeamEditDialog(Team team) {
        this.team = team;
    }

    @Override
    public String getTitle() {
        return "Edit Team";
    }

    @Override
    public boolean saveInstance(DbHelper sql) {

        if (team.getId() == -1) {
            TeamContract.saveTeam(
                    ((EditText) getDialog().findViewById(R.id.edit_name)).getText().toString(),
                    dbHelper.getWritableDatabase()
            );
        } else {
            TeamContract.updateTeam(
                    team.getId(),
                    ((EditText) getDialog().findViewById(R.id.edit_name)).getText().toString(),
                    dbHelper.getWritableDatabase()
            );
        }
        return true;
    }

    @Override
    public View getDialogLayoutView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View result = inflater.inflate(R.layout.fragment_edit_team, null);

        EditText editName = (EditText) result.findViewById(R.id.edit_name);
        editName.setText(team.getName());
        return result;
    }
}
