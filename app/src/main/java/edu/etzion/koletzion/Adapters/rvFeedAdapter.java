package edu.etzion.koletzion.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import edu.etzion.koletzion.MainActivity;
import edu.etzion.koletzion.R;
import edu.etzion.koletzion.comments.CommentFragment;
import edu.etzion.koletzion.comments.LikeFragment;
import edu.etzion.koletzion.models.BroadcastPost;
import edu.etzion.koletzion.models.Comment;
import edu.etzion.koletzion.models.Profile;


public class rvFeedAdapter extends RecyclerView.Adapter<rvFeedAdapter.ViewHolder> {
	private final String POSTS_API_KEY = "mitereeneringledituriess";
	private final String POSTS_API_SECRET = "7a76edb293ad60dbef1a92be96248116b74d9ea3";
	private final String POSTS_DB = "posts";
	private final String DB_USER_NAME = "41c99d88-3264-4be5-b546-ff5a5be07dfb-bluemix";
	private final String PROFILES_API_KEY = "ctleyeaciedgedgessithurd";
	private final String PROFILES_API_SECRET = "a31366679368d7d26408f78ab1402a23485e061e";
	private final String PROFILES_DB = "profiles";
	
	private Context context;
	private List<BroadcastPost> broadcasts;
	private Profile profile;
	private int likesCounter = 0;
	private int commentsCounter = 0;
	
	public rvFeedAdapter(Context context, Profile profile) {
		this.context = context;
		this.profile = profile;
		broadcasts = profile.getRelatedPosts();
	}
	
	public rvFeedAdapter(Context context, List<BroadcastPost> broadcastPosts, Profile profile) {
		this.context = context;
		this.broadcasts = broadcastPosts;
		this.profile = profile;
		
	}
	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.broadcaster_post, parent, false);
		ViewHolder holder = new ViewHolder(view);
		return holder;
	}
	
	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		
		if (broadcasts == null || broadcasts.size() == 0) return;
		String name = broadcasts.get(position).getTitle().replaceAll("_", " ");
		name = name.substring(0, name.length() - 4);
		holder.tvPostHeader.setText(name);
		likesCounter = broadcasts.get(position).getLikesCount();
		commentsCounter = broadcasts.get(position).getCommentsCount();
		holder.tvPostDuration.setText(broadcasts.get(position).getDurationString());
		holder.tvLikesCount.setText(likesCounter + " לייקים");
		holder.tvCommentsCount.setText(commentsCounter + " תגובות");
		
		// playing the broadcast
		holder.imagePostPlayBtn.setOnClickListener(v -> {
			if (context instanceof MainActivity) ((MainActivity) context)
					.initPlayer(broadcasts.get(position).getStreamURL());
		});
		
		//adding a like to the broadcast(only once per user)
		likeToggleListener(holder, position);
		
		//showing Likers
		holder.tvLikesCount.setOnClickListener(v -> {
			MainActivity a = (MainActivity) context;
			LikeFragment.newInstance(broadcasts.get(position)).
					show(a.getSupportFragmentManager(), "tag");
		});
		
		commentListener(holder, position);
		
		//displaying all comments in fragment dialog
		holder.tvCommentsCount.setOnClickListener((v) -> {
			MainActivity a = (MainActivity) context;
			
			CommentFragment.newInstance(broadcasts.get(position)).
					show(a.getSupportFragmentManager(), "tag");
		});
		//todo fix favorite button
		//adding the broadcast to the user's favorites.
		if (profile.isBroadcaster())
			holder.ivFavorite.setVisibility(View.INVISIBLE);
		else {
			holder.ivFavorite.setVisibility(View.VISIBLE);
			favoritesToggleListener(holder, position);
		}

//			holder.ivFavorite.setOnClickListener((v -> {
//				List<BroadcastPost> favorites = profile.getRelatedPosts();
//				for (int i = 0; i < favorites.size(); i++) {
//					if (favorites.get(i).get_id().equals(broadcasts.get(position).get_id()))
//						return;
//				}
//				profile.addBroadcastPost(broadcasts.get(position));
//				DataDAO.getInstance().updateMyProfile(profile);
//			}));
//		}
	}
	
	
	@SuppressLint("StaticFieldLeak")
	private void commentListener(@NonNull ViewHolder holder, int position) {
		holder.ivComment.setOnClickListener((v) -> {
			if (holder.etComment.getText().toString().length() > 0) {
				holder.ivComment.setOnClickListener(null);
				holder.ivLike.setOnClickListener(null);
				holder.ivFavorite.setOnClickListener(null);
				new AsyncTask<Void, Void, BroadcastPost>() {
					
					@Override
					protected BroadcastPost doInBackground(Void... voids) {
						return readBroadcastPost(broadcasts.get(position).get_id());
					}
					
					@Override
					protected void onPostExecute(BroadcastPost broadcastPost) {
						broadcastPost.addComment(new Comment(profile, holder.etComment.getText().toString()));
						updateComment(broadcastPost, holder, position);
						holder.etComment.setText("");
					}
				}.execute();
				
			}
			
		});
	}
	
	@SuppressLint("StaticFieldLeak")
	private void favoritesToggleListener(ViewHolder holder, int position) {
		holder.ivFavorite.setOnClickListener((v) -> {
			holder.ivComment.setOnClickListener(null);
			holder.ivLike.setOnClickListener(null);
			holder.ivFavorite.setOnClickListener(null);
			new AsyncTask<Void, Void, Profile>() {
				
				@Override
				protected Profile doInBackground(Void... voids) {
					return readUpdatedProfile(profile);
				}
				
				@Override
				protected void onPostExecute(Profile profile) {
					List<BroadcastPost> post = profile.getRelatedPosts();
					for (int i = 0; i < post.size(); i++) {
						if (post.get(i).get_id().equals(broadcasts.get(position).get_id())) {
							removeFromFavorites(profile, holder, position, i);
							return;
						}
					}
					addToFavorites(profile, holder, position);
				}
			}.execute();
			
		});
	}
	
	@SuppressLint("StaticFieldLeak")
	private void removeFromFavorites(Profile profile, ViewHolder holder, int position, int broadCastPosition) {
		new AsyncTask<Void, Void, BroadcastPost>() {
			
			@Override
			protected BroadcastPost doInBackground(Void... voids) {
				return readBroadcastPost(broadcasts.get(position).get_id());
			}
			
			@Override
			protected void onPostExecute(BroadcastPost broadcastPost) {
				profile.removeBroadcastPost(broadCastPosition);
				Toast.makeText(context, "הוסר מהמועדפים", Toast.LENGTH_SHORT).show();
				holder.ivFavorite.setImageResource(R.drawable.star);
				updateFavorites(profile, holder, position);
			}
		}.execute();
	}
	
	@SuppressLint("StaticFieldLeak")
	private void addToFavorites(Profile profile, ViewHolder holder, int position) {
		new AsyncTask<Void, Void, BroadcastPost>() {
			
			@Override
			protected BroadcastPost doInBackground(Void... voids) {
				return readBroadcastPost(broadcasts.get(position).get_id());
			}
			
			@Override
			protected void onPostExecute(BroadcastPost broadcastPost) {
				profile.addBroadcastPost(broadcastPost);
				Toast.makeText(context, "הוסף למועדפים", Toast.LENGTH_SHORT).show();
				holder.ivFavorite.setImageResource(R.drawable.star1);
				updateFavorites(profile, holder, position);
			}
		}.execute();
	}
	
	@SuppressLint("StaticFieldLeak")
	private void updateFavorites(Profile profile, ViewHolder holder, int position) {
		new AsyncTask<Void, Void, Void>() {
			
			@Override
			protected Void doInBackground(Void... voids) {
				CloudantClient client = ClientBuilder.account(DB_USER_NAME)
						.username(PROFILES_API_KEY)
						.password(PROFILES_API_SECRET)
						.build();
				
				Database db = client.database(PROFILES_DB, false);
				db.update(profile);
				Log.e("TAG", "doInBackground: cloudant data was saved.... ");
				return null;
			}
			
			@Override
			protected void onPostExecute(Void aVoid) {
				new AsyncTask<Void, Void, Profile>() {
					
					@SuppressLint("WrongThread")
					@Override
					protected Profile doInBackground(Void... voids) {
						return readUpdatedProfile(profile);
					}
					
					@Override
					protected void onPostExecute(Profile updatedProfile) {
						favoritesToggleListener(holder, position);
						likeToggleListener(holder, position);
						commentListener(holder, position);
					}
				}.execute();
				
			}
		}.execute();
	}
	
	@SuppressLint("StaticFieldLeak")
	private void likeToggleListener(@NonNull ViewHolder holder, int position) {
		holder.ivLike.setOnClickListener((v) -> {
			holder.ivLike.setOnClickListener(null);
			holder.ivComment.setOnClickListener(null);
			holder.ivFavorite.setOnClickListener(null);
			new AsyncTask<Void, Void, BroadcastPost>() {
				
				@Override
				protected BroadcastPost doInBackground(Void... voids) {
					return readBroadcastPost(broadcasts.get(position).get_id());
				}
				
				@Override
				protected void onPostExecute(BroadcastPost broadcastPost) {
					List<Profile> likes = broadcastPost.getLikes();
					for (int i = 0; i < likes.size(); i++) {
						if (likes.get(i).getUsername().
								equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
							removeLike(broadcastPost, holder, position, i);
							//todo replace return with unLike() method.
							return;
						}
					}
					addLike(broadcastPost, holder, position);
				}
			}.execute();

//			holder.tvLikesCount.setText(likes.size() + " לייקים");
		});
	}
	
	//todo add 2 methods get updated post,updated profile.
	//this method reads updated post,removing like from it , updating to the server, then reading it back updated from the server.
	@SuppressLint("StaticFieldLeak")
	private void removeLike(BroadcastPost broadcastPost, @NonNull ViewHolder holder, int position, int likePosition) {
		new AsyncTask<Void, Void, Profile>() {
			
			@Override
			protected Profile doInBackground(Void... voids) {
				return readUpdatedProfile(profile);
			}
			
			@Override
			protected void onPostExecute(Profile profile) {
				broadcastPost.removeLike(likePosition);
				holder.ivLike.setImageResource(R.drawable.liky);
				updateLike(broadcastPost, holder, position);
			}
		}.execute();
	}
	
	@SuppressLint("StaticFieldLeak")
	//This method Reads updated post, adding a like to it, updating to the server, then reading it back updated from the server.
	private void addLike(BroadcastPost broadcastPost, @NonNull ViewHolder holder, int position) {
		new AsyncTask<Void, Void, Profile>() {
			
			@Override
			protected Profile doInBackground(Void... voids) {
				return readUpdatedProfile(profile);
			}
			
			@Override
			protected void onPostExecute(Profile profile) {
				broadcastPost.addLike(profile);
				holder.ivLike.setImageResource(R.drawable.share);
				updateLike(broadcastPost, holder, position);
			}
		}.execute();
	}
	
	@SuppressLint("StaticFieldLeak")
	private void updateLike(BroadcastPost broadcastPost, @NonNull ViewHolder holder, int position) {
		new AsyncTask<Void, Void, Void>() {
			
			@Override
			protected Void doInBackground(Void... voids) {
				CloudantClient client = ClientBuilder.account(DB_USER_NAME)
						.username(POSTS_API_KEY)
						.password(POSTS_API_SECRET)
						.build();
				Database db = client.database(POSTS_DB, false);
				
				db.update(broadcastPost);
				Log.e("TAG", "doInBackground: cloudant data was saved.... ");
				return null;
			}
			
			@Override
			protected void onPostExecute(Void aVoid) {
				new AsyncTask<Void, Void, BroadcastPost>() {
					
					@SuppressLint("WrongThread")
					@Override
					protected BroadcastPost doInBackground(Void... voids) {
						return readBroadcastPost(broadcastPost.get_id());
					}
					
					@Override
					protected void onPostExecute(BroadcastPost broadcastPost) {
						broadcasts.set(position, broadcastPost);
						likeToggleListener(holder, position);
						commentListener(holder, position);
						favoritesToggleListener(holder, position);
						
					}
				}.execute();
			}
		}.execute();
	}
	
	@SuppressLint("StaticFieldLeak")
	private void updateComment(BroadcastPost broadcastPost, @NonNull ViewHolder holder, int position) {
		new AsyncTask<Void, Void, Void>() {
			
			@Override
			protected Void doInBackground(Void... voids) {
				CloudantClient client = ClientBuilder.account(DB_USER_NAME)
						.username(POSTS_API_KEY)
						.password(POSTS_API_SECRET)
						.build();
				Database db = client.database(POSTS_DB, false);
				
				db.update(broadcastPost);
				Log.e("TAG", "doInBackground: cloudant data was saved.... ");
				return null;
			}
			
			@Override
			protected void onPostExecute(Void aVoid) {
				new AsyncTask<Void, Void, BroadcastPost>() {
					
					@SuppressLint("WrongThread")
					@Override
					protected BroadcastPost doInBackground(Void... voids) {
						
						return readBroadcastPost(broadcastPost.get_id());
					}
					
					@Override
					protected void onPostExecute(BroadcastPost broadcastPost) {
						broadcasts.set(position, broadcastPost);
						commentListener(holder, position);
						likeToggleListener(holder, position);
						favoritesToggleListener(holder, position);
						
					}
				}.execute();
			}
		}.execute();
	}
	
	public Profile readUpdatedProfile(Profile currentProfile) {
		Profile profile = null;
		CloudantClient client = ClientBuilder.account(DB_USER_NAME)
				.username(PROFILES_API_KEY)
				.password(PROFILES_API_SECRET)
				.build();
		
		Database db = client.database(PROFILES_DB, false);
		
		List<Profile> list = db.findByIndex("{\n" +
				"   \"selector\": {\n" +
				"      \"username\": \"" + currentProfile.getUsername() + "\"\n" +
				"   }\n" +
				"}", Profile.class);
		for (Profile item : list) {
			Log.e("check", "checkResult: " + item.toString());
			profile = item;
		}
		Log.e("check", list.toString());
		return profile;
		
	}
	
	public BroadcastPost readBroadcastPost(String id) {
		BroadcastPost broadcastPost = null;
		CloudantClient client = ClientBuilder.account(DB_USER_NAME)
				.username(POSTS_API_KEY)
				.password(POSTS_API_SECRET)
				.build();
		
		Database db = client.database(POSTS_DB, false);
		
		List<BroadcastPost> list = db.findByIndex("{\n" +
				"   \"selector\": {\n" +
				"      \"_id\": \"" + id + "\"\n" +
				"   }\n" +
				"}", BroadcastPost.class);
		for (BroadcastPost item : list) {
			Log.e("check", "checkResult: " + item.toString());
			broadcastPost = item;
		}
		Log.e("check", list.toString());
		return broadcastPost;
	}
	
	@Override
	public int getItemCount() {
		return broadcasts.size();
	}
	
	public class ViewHolder extends RecyclerView.ViewHolder {
		CardView broadcastPost;
		TextView tvPostHeader;
		TextView tvPostDuration;
		ImageView imagePostPlayBtn;
		TextView tvLikesCount;
		TextView tvCommentsCount;
		TextView tvListenersCount;
		ImageView ivLike;
		ImageView ivComment;
		ImageView ivFavorite;
		EditText etComment;
		
		public ViewHolder(View itemView) {
			super(itemView);
			broadcastPost = itemView.findViewById(R.id.broadcasterPostLayout);
			tvPostHeader = itemView.findViewById(R.id.tvPostHeader);
			tvPostDuration = itemView.findViewById(R.id.tvPostDuration);
			imagePostPlayBtn = itemView.findViewById(R.id.imagePostPlayClick);
			tvLikesCount = itemView.findViewById(R.id.tvLikesCount);
			tvLikesCount = itemView.findViewById(R.id.tvLikesCount);
			tvCommentsCount = itemView.findViewById(R.id.tvCommentsCount);
			tvListenersCount = itemView.findViewById(R.id.tvListenersCount);
			ivLike = itemView.findViewById(R.id.ivLike);
			ivComment = itemView.findViewById(R.id.ivComment);
			ivFavorite = itemView.findViewById(R.id.ivFavorite);
			etComment = itemView.findViewById(R.id.etComment);
			
		}
	}
}
