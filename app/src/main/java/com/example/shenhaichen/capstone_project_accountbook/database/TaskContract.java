package com.example.shenhaichen.capstone_project_accountbook.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by shenhaichen on 23/12/2017.
 */

public class TaskContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.shenhaichen.capstone_project_accountbook";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    /* TaskEntry is an inner class that defines the contents of the task table */
    public static final class TaskEntry implements BaseColumns {

        public static final String ACCOUNT_TABLE = "accountbook";

        // TaskEntry content URI = base content URI + path
        public static final Uri ACCOUNT_BOOK_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(ACCOUNT_TABLE).build();


    }

}
