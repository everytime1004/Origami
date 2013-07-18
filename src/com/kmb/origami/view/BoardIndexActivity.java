package com.kmb.origami.view;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.kmb.origami.R;
import com.kmb.origami.lib.UrlJsonAsyncTask;
import com.kmb.origami.model.Post;
import com.kmb.origami.model.PostImageAdapter;

public class BoardIndexActivity extends SherlockActivity {

	private PullToRefreshGridView mPullRefreshGridView;
	private ArrayList<Post> tasksArray = new ArrayList<Post>();
	private PostImageAdapter postImageAdapter;

	private Button moreTaskBtn = null;

	private int offset_id = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_board_index);

		mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshGridView
				.setOnRefreshListener(new OnRefreshListener<GridView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<GridView> refreshView) {
						// TODO Auto-generated method stub

						if (postImageAdapter != null) {
							tasksArray.clear();
							postImageAdapter.notifyDataSetChanged();

							loadPostFromServer(com.kmb.origami.controller.NetworkInfo.TASKS_URL);
						}

					}

				});

		moreTaskBtn = (Button) findViewById(R.id.moreTaskBtn);

		mPullRefreshGridView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				moreTaskBtn.setVisibility(Button.INVISIBLE);
			}
		});

		// Add an end-of-list listener
		mPullRefreshGridView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						Button moreTaskBtn = (Button) findViewById(R.id.moreTaskBtn);
						moreTaskBtn.setVisibility(Button.VISIBLE);
					}
				});
	}

	public void moreTaskListener(View v) {
		GetMorePostsTask getMorePostsTask = new GetMorePostsTask(
				BoardIndexActivity.this);
		getMorePostsTask.setMessageLoading("글들을 불러오는 중입니다...");
		getMorePostsTask
				.execute(com.kmb.origami.controller.NetworkInfo.TASKS_URL
						+ "?offset_id=" + offset_id);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		if (postImageAdapter != null) {
			tasksArray.clear();
			postImageAdapter.notifyDataSetChanged();

			loadPostFromServer(com.kmb.origami.controller.NetworkInfo.TASKS_URL);
		} else {
			loadPostFromServer(com.kmb.origami.controller.NetworkInfo.TASKS_URL);
		}

		super.onResume();
	}

	private void loadPostFromServer(String url) {
		GetPostsTask getPostsTask = new GetPostsTask(BoardIndexActivity.this);
		getPostsTask.setMessageLoading("글들을 불러오는 중입니다...");
		getPostsTask.execute(url);
	}

	// Post 받아 오는 AsyncTask
	private class GetPostsTask extends UrlJsonAsyncTask {

		public GetPostsTask(Context context) {
			super(context);
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			try {
				JSONArray jsonTasks = json.getJSONObject("data").getJSONArray(
						"posts");
				JSONObject jsonTask = new JSONObject();
				int length = jsonTasks.length();

				for (int i = 0; i < length; i++) {
					jsonTask = jsonTasks.getJSONObject(i).getJSONObject("post");

					String author = jsonTask.getString("author");

					tasksArray.add(new Post(jsonTask.getInt("id"), jsonTask.getString("title"), "", "",
							author, jsonTask.getString("imageURL")));

					if (i == length - 1) {
						offset_id = jsonTask.getInt("id") - 1;
					}

				}

				PullToRefreshGridView tasksGridView = mPullRefreshGridView;
				if (tasksGridView != null) {
					postImageAdapter = new PostImageAdapter(
							BoardIndexActivity.this, tasksArray);
					tasksGridView.setAdapter(postImageAdapter);
				}
				tasksGridView.setOnItemClickListener(new TasklistListener());
			} catch (Exception e) {
				Toast.makeText(context, "게시물이 없습니다.", Toast.LENGTH_LONG).show();
			} finally {
				mPullRefreshGridView.onRefreshComplete();

				super.onPostExecute(json);
			}
		}
	}

	// Post 더 받아 오는 AsyncTask
	private class GetMorePostsTask extends UrlJsonAsyncTask {

		public GetMorePostsTask(Context context) {
			super(context);
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			try {
				JSONArray jsonTasks = json.getJSONObject("data").getJSONArray(
						"posts");
				JSONObject jsonTask = new JSONObject();
				int length = jsonTasks.length();

				for (int i = 0; i < length; i++) {
					jsonTask = jsonTasks.getJSONObject(i).getJSONObject("post");

					String author = jsonTask.getString("author");

					tasksArray.add(new Post(jsonTask.getInt("id"), "", "", "",
							author, jsonTask.getString("imageURL")));

					if (i == length - 1) {
						offset_id = jsonTask.getInt("id") - 1;
					}
				}

				PullToRefreshGridView tasksGridView = mPullRefreshGridView;
				if (tasksGridView != null) {
					postImageAdapter = new PostImageAdapter(
							BoardIndexActivity.this, tasksArray);
					tasksGridView.setAdapter(postImageAdapter);
				}
				tasksGridView.setOnItemClickListener(new TasklistListener());

			} catch (Exception e) {
				Toast.makeText(context, "게시물이 더 없습니다.", Toast.LENGTH_LONG)
						.show();
			} finally {
				mPullRefreshGridView.onRefreshComplete();

				super.onPostExecute(json);
			}
		}
	}

	private class TasklistListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parentView, View v,
				int position, long id) {
			Post post = (Post) parentView.getItemAtPosition(position);

			Intent intent = new Intent(parentView.getContext(),
					BoardShowActivity.class);
			intent.putExtra("title", post.getTitle());

			intent.putExtra("post_id", post.getId());

			intent.putExtra("author", post.getAuthor());
			startActivity(intent);

		}
	}

	private class GetPostsTaskBySearching extends UrlJsonAsyncTask {
		String mAuthor;

		public GetPostsTaskBySearching(Context context, String author) {
			super(context);
			this.mAuthor = author;
		}

		@Override
		protected JSONObject doInBackground(String... urls) {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(urls[0]);
			JSONObject holder = new JSONObject();
			JSONObject taskObj = new JSONObject();
			String response = null;
			JSONObject json = new JSONObject();

			try {
				try {
					json.put("success", false);
					json.put("info", "Something went wrong. Retry!");
					taskObj.put("searching", mAuthor);
					holder.put("post", taskObj);
					StringEntity se = new StringEntity(holder.toString(),
							"utf-8");
					post.setEntity(se);
					post.setHeader("Accept", "application/json");
					post.setHeader("Content-Type", "application/json");
					// post.setHeader("Authorization", "Token token="
					// + mPreferences.getString("AuthToken", ""));

					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					response = client.execute(post, responseHandler);
					json = new JSONObject(response);

				} catch (HttpResponseException e) {
					e.printStackTrace();
					Log.e("ClientProtocol", "" + e);
				} catch (IOException e) {
					e.printStackTrace();
					Log.e("IO", "" + e);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e("JSON", "" + e);
			}

			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			try {
				tasksArray.clear();
				postImageAdapter.notifyDataSetChanged();

				JSONArray jsonTasks = json.getJSONObject("data").getJSONArray(
						"posts");
				JSONObject jsonTask = new JSONObject();
				int length = jsonTasks.length();

				for (int i = 0; i < length; i++) {
					jsonTask = jsonTasks.getJSONObject(i).getJSONObject("post");

					String author = jsonTask.getString("author");

					tasksArray.add(new Post(jsonTask.getInt("id"), "", "", "",
							author, jsonTask.getString("imageURL")));

				}

				PullToRefreshGridView tasksGridView = mPullRefreshGridView;
				if (tasksGridView != null) {
					postImageAdapter = new PostImageAdapter(
							BoardIndexActivity.this, tasksArray);
					tasksGridView.setAdapter(postImageAdapter);
				}
				tasksGridView.setOnItemClickListener(new TasklistListener());
			} catch (Exception e) {
				Toast.makeText(context, "게시물이 더 없습니다.", Toast.LENGTH_LONG)
						.show();
			} finally {
				mPullRefreshGridView.onRefreshComplete();

				super.onPostExecute(json);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.show_index, menu);

		SearchView searchView = (SearchView) menu.findItem(
				R.id.action_search_task).getActionView();
		searchView
				.setQueryHint(getResources().getString(R.string.searchEtHint));

		SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
			public boolean onQueryTextChange(String newText) {
				return true;
			}

			public boolean onQueryTextSubmit(String query) {
				// this is your adapter that will be filtered
				GetPostsTaskBySearching getPostsSearchingTask = new GetPostsTaskBySearching(
						BoardIndexActivity.this, query);
				getPostsSearchingTask.setMessageLoading("글들을 불러오는 중입니다...");
				// getPostsSearchingTask.setAuthToken(mPreferences.getString(
				// "AuthToken", ""));
				getPostsSearchingTask
						.execute(com.kmb.origami.controller.NetworkInfo.TASKS_SEARCH_URL);
				return true;
			}
		};
		searchView.setOnQueryTextListener(queryTextListener);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		return super.onOptionsItemSelected(item);
	}

}
