package com.numero.materiallistdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Item> list;
    private ItemAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionMode actionMode;

    private int selectedItemCount = 0;
    private int itemCreatedCount = 0;
    private boolean isActionMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isActionMode) {
                    actionMode.finish();
                }
                DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
                list.add(new Item(String.valueOf(itemCreatedCount), dateFormat.format(new Date())));
                adapter.notifyDataSetChanged();
                itemCreatedCount++;
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isActionMode) {
                    actionMode.finish();
                }
                list.clear();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new ItemAdapter(this, list);
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (isActionMode) {
                    if (adapter.isSelection(position)) {
                        selectedItemCount--;
                        adapter.removeSelection(position);
                    } else {
                        selectedItemCount++;
                        adapter.setSelection(position, true);
                    }
                    if (!adapter.isSelectedItem()) {
                        actionMode.finish();
                        return;
                    }
                    actionMode.setTitle(String.valueOf(selectedItemCount));
                } else {
                    Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(int position) {
                if (isActionMode) {
                    return;
                }
                isActionMode = true;
                toolbar.startActionMode(new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        actionMode = mode;
                        getMenuInflater().inflate(R.menu.menu_action, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                adapter.deleteSelection();
                                actionMode.finish();
                                break;
                        }
                        return true;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        adapter.clearSelection();
                        selectedItemCount = 0;
                        isActionMode = false;
                    }
                });

                selectedItemCount++;
                adapter.setSelection(position, true);
                actionMode.setTitle(String.valueOf(selectedItemCount));
            }
        });
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                list.remove(position);
                adapter.notifyItemRemoved(position);
                if (isActionMode){
                    actionMode.finish();
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
