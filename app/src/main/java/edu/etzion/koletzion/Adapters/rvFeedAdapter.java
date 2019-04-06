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
	private boolean isMainFeed;
	private List<Profile> likes;
	private List<BroadcastPost> favorites;
	
	public rvFeedAdapter(Context context, Profile profile) {
		this.context = context;
		this.profile = profile;
		broadcasts = profile.getRelatedPosts();
		isMainFeed = false;
		favorites = profile.getRelatedPosts();
		
		
	}
	
	public rvFeedAdapter(Context context, List<BroadcastPost> broadcastPosts, Profile profile) {
		this.context = context;
		this.broadcasts = broadcastPosts;
		this.profile = profile;
		favorites = profile.getRelatedPosts();
		isMainFeed = true;
		
	}
	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.broadcaster_post, parent, false);
		ViewHolder holder = new ViewHolder(view);
		return holder;
	}
	
	//todo
	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		new AsyncTask<Void, Void, BroadcastPost>() {
			
			@Override
			protected BroadcastPost doInBackground(Void... voids) {
				return readBroadcastPost(broadcasts.get(position).get_id());
			}
			
			@Override
			protected void onPostExecute(BroadcastPost broadcastPost) {
				likes = broadcastPost.getLikes();
				boolean isLiked = false;
				boolean isFavorite = false;
				for (int i = 0; i < likes.size(); i++) {
					if (likes.get(i).get_id().equals(profile.get_id()))
						isLiked = true;
				}
				for (int i = 0; i < favorites.size(); i++) {
					if (favorites.get(i).get_id().equals(broadcasts.get(position).get_id()))
						isFavorite = true;
				}
				if (isFavorite)
					holder.ivFavorite.setImageResource(R.drawable.star1);
				else
					holder.ivFavorite.setImageResource(R.drawable.star);
				if (isLiked)
					holder.ivLike.setImageResource(R.drawable.heart_like);
				else
					holder.ivLike.setImageResource(R.drawable.liky);
				if (broadcasts == null || broadcasts.size() == 0) return;
				String name = broadcasts.get(position).getTitle().replaceAll("_", " ").trim();
				name = name.substring(0, name.length() - 4);
				holder.tvPostHeader.setText(name);
				
				holder.tvPostDuration.setText(broadcastPost.getDurationString());
				holder.tvLikesCount.setText(broadcastPost.getLikes().size() + " לייקים");
				holder.tvCommentsCount.setText(broadcastPost.getComments().size() + " תגובות");
				holder.tvListenersCount.setText(String.valueOf(broadcastPost.getListeners().size()));
				// playing the broadcast
				
				
				likeToggleListener(holder, position);
				userListenListener(holder, position);
				commentListener(holder, position);
				//showing Likers
				if (broadcastPost.getLikes().size() > 0) {
					likesCounterListener(holder, position);
				}
				
				//displaying all comments in fragment dialog
				if (broadcastPost.getComments().size() > 0) {
					commentsCountListener(holder, position);
				}
				//adding the broadcast to the user's favorites.
				if (profile.isBroadcaster())
					holder.ivFavorite.setVisibility(View.INVISIBLE);
				else {
					holder.ivFavorite.setVisibility(View.VISIBLE);
					favoritesToggleListener(holder, position);
				}
				
			}
		}.execute();
		
		
	}
	
	private void likesCounterListener(@NonNull ViewHolder holder, int position) {
		holder.tvLikesCount.setOnClickListener(v -> {
			MainActivity a = (MainActivity) context;
			LikeFragment.newInstance(broadcasts.get(position)).
					show(a.getSupportFragmentManager(), "tag");
		});
	}
	
	
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
	
	private void removeFromFavorites(Profile profile, ViewHolder holder, int position, int broadCastPosition) {
		new AsyncTask<Void, Void, BroadcastPost>() {
			
			@Override
			protected BroadcastPost doInBackground(Void... voids) {
				return readBroadcastPost(broadcasts.get(position).get_id());
			}
			
			@Override
			protected void onPostExecute(BroadcastPost broadcastPost) {
				profile.removeBroadcastPost(broadCastPosition);
				if (!isMainFeed)//todo fix it since it hides itemview but not removing it
					holder.itemView.setVisibility(View.INVISIBLE);
				
				Toast.makeText(context, "הוסר מהמועדפים", Toast.LENGTH_SHORT).show();
				holder.ivFavorite.setImageResource(R.drawable.star);
				updateFavorites(profile, holder, position);
			}
		}.execute();
	}
	
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
							return;
						}
					}
					addLike(broadcastPost, holder, position);
				}
			}.execute();

//			holder.tvLikesCount.setText(likes.size() + " לייקים");
		});
	}
	
	private void userListenListener(@NonNull ViewHolder holder, int position) {
		holder.imagePostPlayBtn.setOnClickListener(v -> {
			if (context instanceof MainActivity) {
				((MainActivity) context)
						.initPlayer(broadcasts.get(position).getStreamURL());
				((MainActivity) context).frame.setVisibility(View.VISIBLE);
			}
			holder.imagePostPlayBtn.setOnClickListener(null);
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
					List<Profile> listeners = broadcastPost.getListeners();
					for (int i = 0; i < listeners.size(); i++) {
						if (listeners.get(i).getUsername().equals(profile.getUsername())) {
							userListenListener(holder, position);
							likeToggleListener(holder, position);
							commentListener(holder, position);
							favoritesToggleListener(holder, position);
							return;
						}
					}
					addListener(broadcastPost, holder, position);
				}
			}.execute();
			
		});
	}
	
	//todo add ivListener onclickListner that updateson listenting.
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
				if (broadcastPost.getLikes().size() == 0)
					holder.tvLikesCount.setOnClickListener(null);
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
				holder.ivLike.setImageResource(R.drawable.heart_like);
				updateLike(broadcastPost, holder, position);
			}
		}.execute();
	}
	
	private void addListener(BroadcastPost broadcastPost, @NonNull ViewHolder holder, int position) {
		new AsyncTask<Void, Void, Profile>() {
			
			@Override
			protected Profile doInBackground(Void... voids) {
				return readUpdatedProfile(profile);
			}
			
			@Override
			protected void onPostExecute(Profile profile) {
				broadcastPost.addListener(profile);
				updateListener(broadcastPost, holder, position);
			}
		}.execute();
	}
	
	private void updateListener(BroadcastPost broadcastPost, @NonNull ViewHolder holder, int position) {
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
						holder.tvListenersCount.setText(String.valueOf(broadcasts.get(position).getListeners().size()));
						userListenListener(holder, position);
						likeToggleListener(holder, position);
						commentListener(holder, position);
						favoritesToggleListener(holder, position);
						
					}
				}.execute();
			}
		}.execute();
	}
	
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
						holder.tvLikesCount.setText(broadcasts.get(position).getLikes().size() + " לייקים");
						if (broadcasts.get(position).getLikes().size() > 0)
							likesCounterListener(holder, position);
						likeToggleListener(holder, position);
						commentListener(holder, position);
						favoritesToggleListener(holder, position);
						
					}
				}.execute();
			}
		}.execute();
	}
	
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
						holder.tvCommentsCount.setText(broadcasts.get(position).getComments().size() + " תגובות");
						if (broadcasts.get(position).getComments().size() > 0) {
							commentsCountListener(holder, position);
						}
						commentListener(holder, position);
						likeToggleListener(holder, position);
						favoritesToggleListener(holder, position);
						
					}
				}.execute();
			}
		}.execute();
	}
	
	private void commentsCountListener(@NonNull ViewHolder holder, int position) {
		holder.tvCommentsCount.setOnClickListener((v) -> {
			MainActivity a = (MainActivity) context;
			
			CommentFragment.newInstance(broadcasts.get(position)).
					show(a.getSupportFragmentManager(), "tag");
		});
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
