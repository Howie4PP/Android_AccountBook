package com.example.shenhaichen.capstone_project_accountbook.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.shenhaichen.capstone_project_accountbook.database.TaskContract.TaskEntry.ACCOUNT_TABLE;

/**
 * Created by shenhaichen on 23/12/2017.
 */

public class TaskContentProvider extends ContentProvider {

    //全部任务
    public static final int ACCOUNT_TASK = 100;
    //单个任务
    public static final int ACCOUNT_TASK_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DBOpenHelper mhelper;

    /**
     * 根据不同Uri进行任务区分
     *
     * @return
     */
    public static UriMatcher buildUriMatcher() {

        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //添加不同电影的uri
        uriMatcher.addURI(TaskContract.AUTHORITY, ACCOUNT_TABLE, ACCOUNT_TASK);
        uriMatcher.addURI(TaskContract.AUTHORITY, ACCOUNT_TABLE + "/#", ACCOUNT_TASK_WITH_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {

        mhelper = new DBOpenHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // 访问数据库，只写入
        final SQLiteDatabase db = mhelper.getReadableDatabase();

        // Write URI match code and set a variable to return a Cursor
//        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        // 查询所需要的数据
        retCursor = db.query(ACCOUNT_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        // 设定一个通知URI,并返回Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // 访问数据库，只写入
        final SQLiteDatabase db = mhelper.getWritableDatabase();

        // Write URI matching code to identify the match for the tasks directory
//        int match = sUriMatcher.match(uri);
        // 返回用的URI
        Uri returnUri = null;
        long id = db.insert(ACCOUNT_TABLE, null, values);
        if (id > 0) {
            returnUri = ContentUris.withAppendedId(TaskContract.TaskEntry.ACCOUNT_BOOK_URI, id);
        } else {
            throw new android.database.SQLException("Failed to insert row into " + uri);
        }

        //通知resolver，如果Uri已经改变，返回新的插入的uri
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mhelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        //起始为0
        int tasksDeleted;

        //删除单个记录
        switch (match) {
            // 运用ID去分辨这个任务
            case ACCOUNT_TASK_WITH_ID:
                //得到ID
                String id = uri.getPathSegments().get(1);
                // 使用 selections/selectionArgs 去过滤得到ID
                tasksDeleted = db.delete(ACCOUNT_TABLE, "movie_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

       //通知Uri的改变
        if (tasksDeleted != 0) {

            getContext().getContentResolver().notifyChange(uri, null);
        }

        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
