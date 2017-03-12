package com.footballmanager.admin_fragments.edit_dialogs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.footballmanager.R;
import com.footballmanager.db.DbHelper;
import com.footballmanager.db.RelPlayerTeamContract;
import com.footballmanager.db.RoleContract;
import com.footballmanager.db.TeamContract;
import com.footballmanager.db.UserContract;
import com.footballmanager.model.MappedItem;
import com.footballmanager.model.Role;
import com.footballmanager.model.Team;
import com.footballmanager.model.User;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andrew on 11-Mar-17.
 */

public class UserEditDialog extends EntityEditDialog {

    private static List<Pair<Long, Long>> relations;
    private static User user;
    public UserEditDialog() { super(); }

    public UserEditDialog(User user) {
        super();
        UserEditDialog.user = user;
    }

    @Override
    public String getTitle() {
        return "Edit User";
    }

    @Override
    public boolean saveInstance(DbHelper dbHelper) {
        EditText editName = (EditText) getDialog().findViewById(R.id.edit_user_name);
        EditText editPass = (EditText) getDialog().findViewById(R.id.edit_password);
        EditText repeatPass = (EditText) getDialog().findViewById(R.id.repeat_password);
        Spinner selectRole = (Spinner) getDialog().findViewById(R.id.roles_spinner);
        Spinner teamSpinner = (Spinner) getDialog().findViewById(R.id.team_spinner);

        if(!editPass.getText().toString().equals(repeatPass.getText().toString())) {
            repeatPass.setError(getString(R.string.not_equal_passwords_message));
            editPass.setError("");
            return false;
        }

        if (user.getId() == -1)
            UserContract.addNewUser(
                    editName.getText().toString(),
                    editPass.getText().toString(),
                    (Role) selectRole.getSelectedItem(),
                    dbHelper.getWritableDatabase()
            );
        else
            UserContract.updateUser(
                    user.getId(),
                    editName.getText().toString(),
                    editPass.getText().toString(),
                    (Role) selectRole.getSelectedItem(),
                    dbHelper.getWritableDatabase()
            );
        int selectionIndex = -1;
        for (int i = 0; i < relations.size(); i++) {
            if (relations.get(i).first == user.getId())
                selectionIndex = i;
        }
        long newTeamId = ((Team)teamSpinner.getSelectedItem()).getId();
        if (relations.get(selectionIndex).second != newTeamId && selectionIndex != -1 && newTeamId != -1) {
            ContentValues cv = new ContentValues();
            cv.put(RelPlayerTeamContract.RelPlayerTeam.PLAYER, user.getId());
            cv.put(RelPlayerTeamContract.RelPlayerTeam.TEAM, newTeamId);
            dbHelper.getWritableDatabase().insert(
                    RelPlayerTeamContract.RelPlayerTeam.TABLE_NAME,
                    null,
                    cv
            );
        }

        return true;
    }

    @Override
    public View getDialogLayoutView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View result = inflater.inflate(R.layout.fragment_edit_user, null);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] relationProjection = {
                RelPlayerTeamContract.RelPlayerTeam.PLAYER,
                RelPlayerTeamContract.RelPlayerTeam.TEAM,
        };
        Cursor cursor = db.query(
                RelPlayerTeamContract.RelPlayerTeam.TABLE_NAME,
                relationProjection,
                "",
                null,
                "",
                "",
                ""

        );
        while (cursor.moveToNext())
            relations.add(new Pair<>(
                    cursor.getLong(cursor.getColumnIndex(RelPlayerTeamContract.RelPlayerTeam.PLAYER)),
                    cursor.getLong(cursor.getColumnIndex(RelPlayerTeamContract.RelPlayerTeam.TEAM))
            ));

        EditText editName = (EditText) result.findViewById(R.id.edit_user_name);
        editName.setText(user.getName());
        Spinner rolesSpinner = (Spinner) result.findViewById(R.id.roles_spinner);
        List<MappedItem> roles = RoleContract.getRoles(db);
        rolesSpinner.setAdapter(new ItemsAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                roles
        ));
        int selectionIndex = 0;
        for (; selectionIndex < roles.size(); selectionIndex++) {
            if(roles.get(selectionIndex).getId() == user.getRole().getId())
                break;
        }
        rolesSpinner.setSelection(selectionIndex);

        Spinner teamSpinner = (Spinner) result.findViewById(R.id.team_spinner);
        List<MappedItem> teams = TeamContract.getTeams(db);
        teams.add(new Team(-1, "No team", new LinkedList<User>()));
        teamSpinner.setAdapter(new ItemsAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                teams
        ));
        selectionIndex = 0;
        for (; selectionIndex < relations.size(); selectionIndex++) {
            if (relations.get(selectionIndex).first == user.getId())
                break;
        }
        rolesSpinner.setSelection(selectionIndex);

        return result;
    }
}
